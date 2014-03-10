/**
 * Copyright (c) 2009 Software Architecture Group
 *                    Hasso-Plattner-Institute, Potsdam, Germany
 *  
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package jcop.compiler;

import static jcop.tools.Map.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import jcop.Globals.Msg;
import jcop.compiler.filecopy.AuxiliaryCompilerFilesHandler;
import jcop.output.CrossReferenceOutlineGenerator;
import jcop.output.SourceCodeGenerator;
import jcop.output.XMLOutlineGenerator;
import jcop.output.graph.GraphGeneratorFactory;
import jcop.output.graph.GraphGeneratorFactory.Type;
import jcop.tools.Map.*;
import AST.BytecodeParser;
import AST.BytecodeReader;
import AST.CompilationUnit;
import AST.JavaParser;
import AST.Program;
import AST.TypeDecl;

public class JCopCompiler extends JCopFrontend  {
	private CompilerConfiguration config;
	public static CompilerMessageStream msg;
	public AuxiliaryCompilerFilesHandler fileHandler; 	
	private SourceCodeGenerator fileDumper;
	private XMLOutlineGenerator xmlOutlineGenerator;
	boolean layerClassGenerated = false;
	private CompilationUnit firstUnit; 
	private HashSet<CompilationUnit> processedUnits;
	private CrossReferenceOutlineGenerator crossReferenceGenerator;

	static {
		msg = CompilerMessageStream.getInstance();
	}
	public static void main(String args[]) {		
		if (!new JCopCompiler().compile(args)) 	
			abort();			
		System.exit(0);					
	}   
	    	
	public JCopCompiler() {		
		//initFields();
		initOptions();		
	}	
	
	public boolean compile(String... args) {		
		if (args.length == 0) {
			printUsage();
			return false;
		}
		else 
			return timeAndCompile(args);		
	}

	private boolean timeAndCompile(String[] args) {		
		Timer timer = new Timer();		
		timer.start();		
		args = setUp(args);		
		boolean hasCompiled =  compileAndCollectErrors(args);
		tearDown(hasCompiled);
		timer.stop();
		msg.maybeLogTask("compiled in ", Long.toString(timer.getTime()), "millis\n");
		return hasCompiled;
	}	
	
	private boolean compileAndCollectErrors(String[] args) {		
		try {			
			return compileInt(args);
		}
		catch (Exception e) {			
			e.printStackTrace();
			return false;			 
		}		
	}

	private String[] setUp(String[] args) {			
		config = CompilerConfiguration.getInstance();			
		args = initArgs(args);				 
		msg.maybeLogTask("arguments: " + Arrays.toString(args));
		processedUnits = new HashSet<CompilationUnit>();
		outlineBuffer = new StringBuffer();
		 fileHandler = AuxiliaryCompilerFilesHandler.getInstance(config.isJCopCompiler());
		 fileHandler.createFiles();
		fileHandler.removeJCopAspectClassFile();
		return args;
	}

	private boolean compileInt(String[] args) {		
			if (process(args) && !config.astTransformationsDisabled()) 		
				return compileLayersAndAspects();
			return config.astTransformationsDisabled();		
	}
	
	private boolean compileLayersAndAspects() {		
		processLayers();
		if (config.isJCopCompiler())
			fileHandler.writeAspectsToDisc();
		
//		for(TypeDecl t : classFilesForRegeneration) {			
//			if (config.generateSources())
//				fileDumper.writeToFile(t.compilationUnit());						
//			t.resetCache();
//			t.generateClassfile();
//		}
		
		if (config.generateAspects())   
			return new AspectWeaver().weave();
		return true;		
	}

	private void tearDown(boolean compiled) {
		fileHandler.cleanup();
		if (config.generateSources()) {				 
			fileDumper.dumpAspect();
			fileHandler.copyIllformedClassesToSourcedumpFolder(fileDumper.getSourcedumpPath());
		}
		if (compiled) {	
			if (config.generateGraph()) 
				GraphGeneratorFactory.getInstance().save();			
			if (config.generateOutline())
				crossReferenceGenerator.writeToFile();				
		}
		else {					
			fileHandler.removeJCopSpecificClassFiles();
			if (config.generateSources())
				fileDumper.deleteAllFiles();
		}	
		
	}
	

	private String[] initArgs(String[] args) {
		ArgumentParser argumentParser = new ArgumentParser(args);		
		String[] parsedArgs = argumentParser.getParsedArgs();				
		processArgs(parsedArgs);				
		if(config.generateGraph())
			initGraphGeneration();
		if (config.generateSources())
			fileDumper = new SourceCodeGenerator();
		if (config.generateOutline())
			initOutlineGenerator();
		return parsedArgs;
	}

	private void initGraphGeneration() {		
		if (config.generateAggGraph()) 			
			GraphGeneratorFactory.setGraph(Type.AGG);		
		if (config.generateGrooveGraph()) 		
			GraphGeneratorFactory.setGraph(Type.GROOVE);			
	}

	private void initOutlineGenerator() {
		String outlinePath = Program.getValueForOption(CompilerOps.xmlOutlinePath);
		if (!isAbsolutePath(outlinePath) && Program.hasValueForOption(CompilerOps.sourcepath))
			outlinePath = Program.getValueForOption(CompilerOps.sourcepath) + File.separator + outlinePath;
		File outlineDir = new File(outlinePath);
		xmlOutlineGenerator = new XMLOutlineGenerator(outlineDir);
		crossReferenceGenerator = new CrossReferenceOutlineGenerator(outlineDir);
	}

	private boolean isAbsolutePath(String outlinePath) {
		File path = new File(outlinePath);
		return path.isAbsolute();
		//return outlinePath.contains(":");		
	}

	private boolean process(String[] args) {
		try {
			return process(args, new BytecodeParser(), new JavaParser() {
				public CompilationUnit parse(java.io.InputStream is, String fileName) 
					throws java.io.IOException, beaver.Parser.Exception {
					return new JCopParser().parse(is, fileName);
				}
			}); 
		}
		catch (Error e) {
			System.err.println(e.getLocalizedMessage());
			return false;
		}					
	}

	@Override
	protected void processErrors(Collection errors, CompilationUnit unit) {		
		maybeGenerateAlternativeOutput(unit);		
		super.processErrors(errors, unit);	
	}
	private static StringBuffer outlineBuffer;
	private static boolean hasInstanceLayer;
	private static ArrayList<TypeDecl> classFilesForRegeneration = new ArrayList<TypeDecl>();

	public static StringBuffer getOutlineBuffer() {
		return outlineBuffer;
	}

	
	// malte: ugly! if a layer only contains partial methods with primitive return type,
	// no forwarding methods will be generated in the concrete layer class. touching 
	// all classes once prevents that - and is f*** ugly...
	private boolean touched = false;
	private void maybeTouch(CompilationUnit unit) {
		if (!touched) {
			unit.getParent().getParent().toString();
			touched = true;
		}	
	}
	
	
	protected void processNoErrors(CompilationUnit unit) {		
		if (unvisitedApplicationUnit(unit)) {
			maybeTouch(unit);
			maybeGenerateAlternativeOutput(unit);			
			unit.transformation();			
			msg.maybeLogTask(Msg.compiling, unit.relativeName());
			if (firstUnit == null)
				firstUnit = unit;
			if (config.generateSources())
				fileDumper.writeToFile(unit);
			unit.generateClassfile();
			msg.closeTask();			
			unit.clearOnExit();
			processedUnits.add(unit);
		}
	}	

	private void maybeGenerateAlternativeOutput(CompilationUnit unit) {
		maybeGenerateXmlOutline(unit);
		maybeGenerateGraph(unit);		
	}
	
	private void maybeGenerateXmlOutline(CompilationUnit unit) {
		if (config.generateOutline()) {
			xmlOutlineGenerator.writeToFile(unit);
			crossReferenceGenerator.add(unit);
//			if (config.generateCILOutline())  {
//			
//			}
		}		
	}

	private void maybeGenerateGraph(CompilationUnit unit) {
		if (config.generateGraph()) {			
			msg.maybeLog(Msg.aggGeneration, unit.packageName());
			unit.createGraphNode();		
		}	
	}
	
	private boolean unvisitedApplicationUnit(CompilationUnit unit) {
		TypeDecl _layer = JCopTypes.getLayer(unit);
		TypeDecl concreteLayer = JCopTypes.getConcreteLayer(unit);		
		boolean jcopUnit = 
			(unit == _layer.compilationUnit()) || 
			(unit == concreteLayer.compilationUnit());
		boolean visited = processedUnits.contains(unit);
		return !visited && !jcopUnit;
	}
	
	private void processLayer(TypeDecl type) {
		CompilationUnit compilationUnit = type.compilationUnit();
		if (!processedUnits.contains(compilationUnit) && firstUnit.fromSource()) {
			compilationUnit.generateClassfile();

			if (config.generateSources())
				fileDumper.writeToFile(compilationUnit);

			for (TypeDecl decl : compilationUnit.getTypeDecls()) {
				//msg.maybeLog(" " + decl.getID());
				decl.transformation();
				decl.generateClassfile();
			}
			processedUnits.add(compilationUnit);
		}
	}

	protected void processLayers() {
		//if (existsConcreteLayer()) {
			msg.maybeLogTask("compiling layers: ");
			processLayer(JCopTypes.getLayer(firstUnit));
			processLayer(JCopTypes.getConcreteLayer(firstUnit));
			//processLayer(JCopTypes.get(firstUnit, concreteLayerName));
			msg.closeTask();
		//}
	}
	
	public boolean process(String[] args, BytecodeReader reader, JavaParser parser) {		
		initProgram(reader, parser, args);
		Collection<String> files = program.files();

		if (!sourceFilesExist(files)) 
			return false;
		if (config.printUsage() || files.isEmpty()) {			
			printUsage();
			return false;
		}	
		addSourceFiles(files);
		return processCompilationUnits();
	}

	
		
	private boolean processCompilationUnits() {
		List<CompilationUnit> layers = new ArrayList<CompilationUnit>();
		
//		map(program.compilationUnitIterator(), new func<CompilationUnit>() {
//			public void map(CompilationUnit t) {
//				System.out.println(t.toString());
//			};
//		});
		
		
		for (Iterator<CompilationUnit> iter = program.compilationUnitIterator(); iter.hasNext();) {			
			CompilationUnit unit = iter.next();					
		     if (!processCompilationUnit(unit))
				return false;			
		}			
		return true;
	}

	private boolean processCompilationUnit(CompilationUnit unit) {		
		if (unit.fromSource()) {				
			//unit.resetCache();			
			Collection errors = unit.parseErrors();			
			Collection warnings = new LinkedList();
			// compute static semantic errors when there are no parse
			// errors or the recover from parse errors option is specified			
			if (errors.isEmpty() || program.hasOption("-recover")) {
				try {
					unit.errorCheck(errors, warnings);
				}
				catch(NullPointerException e) {
					abort();
				}
			}
			if (!errors.isEmpty() && !config.generateOutline()) {
				processErrors(errors, unit);
				return false;					
			}
			else {
				processWarnings(warnings, unit);
				processNoErrors(unit);
			}	
		}
		return true;
	}
	
	private boolean sourceFilesExist(Collection<String> files) {
		for (Iterator<String> sourceFileNames = files.iterator(); sourceFileNames.hasNext();) {
			File sourceFile = new File(sourceFileNames.next());	
			
			if (!sourceFile.exists()) {
				msg.log(Globals.Msg.fileNotExist, sourceFile.getAbsoluteFile());
				return false; 
			}
		}
		return true;
	}

	private void addSourceFiles(Collection<String> files) {		
		for (Iterator<String> iter = files.iterator(); iter.hasNext();) {
			String name = iter.next();		
			program.addSourceFile(name);
		}		
	}

	private void initProgram(BytecodeReader reader, JavaParser parser, String[] args) {
		program.initBytecodeReader(reader);
		program.initJavaParser(parser);
		initOptions();
		processArgs(args);
	}
	
	class Timer {
		private long timestamp;
		private long time;

		public void start() {
			timestamp = System.currentTimeMillis();
		}

		public long getTime() {
			return time;
		}

		public void stop() {
			time = System.currentTimeMillis() - timestamp;			
		}
	}
  
	public static void setInstanceLayerExits(boolean b) {
		hasInstanceLayer = true;		
	}

	public static void addClassFileForRegeneration(TypeDecl host) {
		classFilesForRegeneration .add(host);		
	}
	
	public static void abort(String file, int line, String message, Object... vars) {
		msg.log("[abort]", file, ":", line, "\n", String.format(message, vars));		
		abort();
	}
		
	public static void abort() {
		msg.log(Msg.compileErrors, "terminated.");
		System.exit(1);	
	}
}