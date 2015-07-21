package jcop.compiler;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import AST.Frontend;

public class JCopFrontend extends Frontend {
	
	@Override
	protected void initOptions() {
		super.initOptions();
		// program.addKeyValueOption(CompilerOps.rootDir);
		program.options().addKeyOption(CompilerOps.runtimeLogging);		
		program.options().addKeyValueOption(CompilerOps.xmlOutlinePath);
		program.options().addKeyOption(CompilerOps.xmlCILOutline);
		program.options().addKeyOption(CompilerOps.compiletimeLogging);
		program.options().addKeyValueOption(CompilerOps.dumpSources);
		program.options().addKeyOption(CompilerOps.noAspects);
		program.options().addKeyOption(CompilerOps.aspectInfo);
		program.options().addKeyValueOption(CompilerOps.agg);
		program.options().addKeyValueOption(CompilerOps.groove);
		program.options().addKeyOption(CompilerOps.debug);
		program.options().addKeyValueOption(CompilerOps.layerpath);
		program.options().addKeyValueOption(CompilerOps.inpath);
		program.options().addKeyValueOption(CompilerOps.staticactive);
	} 
	
	@Override
	protected String url() {
		return Globals.url;
	}

	@Override
	protected String name() {
		return Globals.lang + " Compiler";
	}
	
	@Override
	protected String version() {
		return Globals.version;
	}

	
	
	@Override
	protected void printUsage() {
		UsageMessage usage = UsageMessage.getInstance();
		System.out.println(usage.toString());
	}


}
