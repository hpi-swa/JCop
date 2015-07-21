package jcop.compiler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import AST.Program;

public class CompilerMessageStream {
	private static CompilerMessageStream singleton;
	private int indent;
	private CompilerMessageStream() {
		indent = 0;	
		//if (!CompilerConfiguration.getInstance().debugMode()) {
			//System.out.println("not debug");
			//PrintStream nirvana = new PrintStream(new ByteArrayOutputStream());
			//System.setErr(nirvana);
		//}
	} 
	
	public static CompilerMessageStream getInstance() {
		if (singleton == null)
			singleton = new CompilerMessageStream();
		return singleton;
	}
	
	
	public static void print(String s) {
		if (CompilerConfiguration.getInstance().debugMode())
			System.out.println("[debug message] " + s);
	}
	
	public boolean logMode() {
		return CompilerConfiguration.getInstance().hasOption(CompilerOps.compiletimeLogging);
	}
	
	public boolean compileTimeLogMode() {	
		return CompilerConfiguration.getInstance().hasOption(Globals.CompilerOps.compiletimeLogging);
	}

	
	public void maybeLogTask(String s, String... args) {		
		if(logMode()) {
			logTask(s, args);
		}
	}
	
	public void closeTask() {
		indent--;
		if(logMode()) {		
			log("..done");
		}
	}
	
	private String indent() {
		String ind = "";
		for (int i = 0; i < indent; i++) {
			ind += " ";		
		}
		return ind;
	}

	public void maybeLog(String msg, String... args) {
		if(logMode()) {
			log(msg, args);
		}
	}
	
	public void logTask(String msg, String... args) {
		StringBuffer b = new StringBuffer(indent());
		b.append(msg).append(' ');
		for(String arg : args)
			b.append(arg).append(' ');
		System.out.print(b.toString() + "\n");
		indent++;
	}	
	
	public void log(String msg, Object... args) {
		StringBuffer b = new StringBuffer(indent());
		b.append(msg).append(' ');
		for(Object arg : args)
			b.append(arg.toString().trim()).append(' ');		
		System.out.println(b.toString());		
	}

	public void compileTimeLog(String msg, Object... args) {
		if(compileTimeLogMode()) 
			log(msg, args);		
	}
	
}
