package jcop.compiler;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import AST.Program;

public class CompilerConfiguration {
	private static CompilerConfiguration instance;
	
	private boolean isAstTransform = true;
	
	private static Program program;
	
	private CompilerConfiguration() { }
	
	public static CompilerConfiguration getInstance() {
		if (instance == null) 
			instance = new CompilerConfiguration();
		return instance;
	}
	
	public void setProgram(Program prg){
		if(program == null)
			this.program = prg;
	}
	
	public boolean isJCopCompiler() {
		return Globals.lang.equals("jcop");
	}		

	public  boolean astTransformationsDisabled() {
		return 	generateOutline() || generateGraph() || !isAstTransform;
	}
	
	public void setAstTransform(boolean value){
		isAstTransform = value;
	}

	public boolean generateAggGraph() {
		return program.options().hasValueForOption(CompilerOps.agg);	
	}

	public boolean generateGrooveGraph() {
		return program.options().hasValueForOption(CompilerOps.groove);	
	} 
	
	public boolean generateGraph() {
		return generateAggGraph() || generateGrooveGraph();	
	}
	
	public boolean debugMode() {		
		return program.options().hasOption(CompilerOps.debug);
	}
	
	public boolean generateOutline() {
		return program.options().hasOption(CompilerOps.xmlOutlinePath);
	}

	public boolean generateCILOutline() {
		return program.options().hasOption(CompilerOps.xmlCILOutline);
	}

	public boolean generateAspects() {
		return !program.options().hasOption(CompilerOps.noAspects) && isJCopCompiler();
	}

	public boolean hasSourcePath() {
		return program.options().hasOption(CompilerOps.sourcepath);
	}
	
	public boolean generateSources() {
		return program.options().hasOption(CompilerOps.dumpSources);
	}

	public boolean printUsage() {
		return program.options().hasOption("-version") || program.options().hasOption("-help");		
	}
	
	public boolean hasOption(String name) {
	    return program.options().hasOption(name);
	}
	
	public boolean hasValueForOption(String name) {
	    return program.options().hasValueForOption(name);
	}
	
	public String getValueForOption(String name) {
		return program.options().getValueForOption(name);
	}
}
