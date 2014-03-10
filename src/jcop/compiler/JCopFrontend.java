package jcop.compiler;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import AST.Frontend;

public class JCopFrontend extends Frontend {
	
	@Override
	protected void initOptions() {
		super.initOptions();
		// program.addKeyValueOption(CompilerOps.rootDir);
		program.addKeyOption(CompilerOps.runtimeLogging);		
		program.addKeyValueOption(CompilerOps.xmlOutlinePath);
		program.addKeyOption(CompilerOps.xmlCILOutline);
		program.addKeyOption(CompilerOps.compiletimeLogging);
		program.addKeyValueOption(CompilerOps.dumpSources);
		program.addKeyOption(CompilerOps.noAspects);
		program.addKeyOption(CompilerOps.aspectInfo);
		program.addKeyValueOption(CompilerOps.agg);
		program.addKeyValueOption(CompilerOps.groove);
		program.addKeyOption(CompilerOps.debug);
		program.addKeyValueOption(CompilerOps.layerpath);
		program.addKeyValueOption(CompilerOps.inpath);
	} 
	

	protected String url() {
		return Globals.url;
	}

	protected String name() {
		return Globals.lang + " Compiler";
	}

	protected String version() {
		return Globals.version;
	}

	
	
	@Override
	protected void printUsage() {
		UsageMessage usage = UsageMessage.getInstance();
		System.out.println(usage.toString());
	}


}
