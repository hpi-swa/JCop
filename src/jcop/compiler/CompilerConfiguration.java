package jcop.compiler;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import AST.Program;

public class CompilerConfiguration {
	private static CompilerConfiguration instance;
	
	private CompilerConfiguration() { }
	
	public static CompilerConfiguration getInstance() {
		if (instance == null) 
			instance = new CompilerConfiguration();
		return instance;
	}
	
	public boolean isJCopCompiler() {
		return Globals.lang.equals("jcop");
	}		

	public  boolean astTransformationsDisabled() {
		return 	generateOutline() || generateGraph();
	}

	public boolean generateAggGraph() {
		return Program.hasValueForOption(CompilerOps.agg);	
	}

	public boolean generateGrooveGraph() {
		return Program.hasValueForOption(CompilerOps.groove);	
	} 
	
	public boolean generateGraph() {
		return generateAggGraph() || generateGrooveGraph();	
	}
	
	public boolean debugMode() {		
		return Program.hasOption(CompilerOps.debug);
	}
	
	public boolean generateOutline() {
		return Program.hasOption(CompilerOps.xmlOutlinePath);
	}

	public boolean generateCILOutline() {
		return Program.hasOption(CompilerOps.xmlCILOutline);
	}

	public boolean generateAspects() {
		return !Program.hasOption(CompilerOps.noAspects) && isJCopCompiler();
	}

	public boolean hasSourcePath() {
		return Program.hasOption(CompilerOps.sourcepath);
	}
	
	public boolean generateSources() {
		return Program.hasOption(CompilerOps.dumpSources);
	}

	public boolean printUsage() {
		return Program.hasOption("-version") || Program.hasOption("-help");		
	}
	
}
