package jcop.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jcop.Globals.CompilerOps;
import jcop.Globals.Msg;

import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.IMessage.Kind;
import org.aspectj.bridge.MessageHandler;
import org.aspectj.tools.ajc.Main;

import AST.Program;

public class AspectWeaver {
	private final String aspectFile = "JCopAspect.aj";
	private CompilerMessageStream msg =	CompilerMessageStream.getInstance();
	
	public boolean weave() {		
		try {				
			msg.maybeLogTask(Msg.compileAndWeaveAspect);						
			boolean result = run(new File(new File("jcop", "lang"), aspectFile));			
			msg.closeTask();			    
			return result;	
		}
		catch (Exception e) {
			e.printStackTrace(); 
			System.exit(1);
			return false;
		}
	} 
	
	private boolean run(File aspect) {
		PathList workingDir = new PathList(getWorkingDir());
		CompilerMessageStream.getInstance().maybeLog(workingDir + File.separator + aspect);
		MessageHandler m = new MessageHandler();
		CompilerArgs args = initCompilerArgs(workingDir, aspect);
		 
		Main compiler = new Main();
		msg.maybeLogTask(Arrays.asList(args.toArray()).toString());		
		compiler.run(args.toArray(), m);
		printMessages(m);		
		return !containsErrors(m);
	}
  
	private CompilerArgs  initCompilerArgs(PathList workingDir, File aspect) {
		/* the following lines make trouble in bmi, since aj will weave also into referenced libraries
		 *  on the other hand, this might be necessary 
		 */
		CompilerArgs args = new CompilerArgs();
		addCP(args);
		addInpath(workingDir, args);	 		
		args.addKeyValueArg("-d", workingDir);
		args.addArg("-1.6");			
		args.addArg("-Xmx64M");		 
//		args.addArg("-Xbootclasspath/p:" + workingDir);	
		args.addArg(getDebug());
        args.addArg(getWarnings());
		args.addArg(new File(workingDir.toString(), aspect.toString()));
		return args;
	}


	private void addInpath(CharSequence workingDir, CompilerArgs args) {
		PathList inpath = new PathList(workingDir);	
		if (Program.hasValueForOption(CompilerOps.inpath)) 
			inpath.add(Program.getValueForOption(CompilerOps.inpath));		
		args.addKeyValueArg(CompilerOps.inpath, inpath);		
	}

	private void addCP(CompilerArgs args) {		
		//instead, I try this
		if (Program.hasValueForOption(CompilerOps.classpath)) {
			PathList classpath = new PathList(System.getProperties().getProperty("java.class.path", null));
			classpath.add(Program.getValueForOption(CompilerOps.classpath));
			args.addKeyValueArg(CompilerOps.classpath, classpath);		
			args.addKeyValueArg("-aspectpath", classpath);
		}			
	} 

	private boolean containsErrors(MessageHandler m) {
		return m.getErrors().length > 0;
	} 

	private void printMessages(MessageHandler msgHandler) {		
			IMessage[] messages = msgHandler.getMessages(getInfoKind(), true);			
			for (IMessage m : messages)
				msg.log(Msg.aspectInfo, m.toString());		
	}


	private Kind getInfoKind() {
		return 
		    Program.hasOption(CompilerOps.aspectInfo)
		    ? null
		    : IMessage.WARNING;			
	}

	private String getDebug() {
		if (Program.hasOption(CompilerOps.aspectInfo)) 
			return "-g";			
		else 
			return "-g:none";				
	}	
	
	private String getWarnings() {
		if (Program.hasOption(CompilerOps.aspectInfo)) 
			return "-Xlint";			
		else 
			return "-Xlint:ignore";				
	}

	private CharSequence getWorkingDir() {			
		if (Program.hasOption(CompilerOps.destinationPath))
			return Program.getValueForOption(CompilerOps.destinationPath);
		if (Program.hasOption(CompilerOps.sourcepath))
			return Program.getValueForOption(CompilerOps.sourcepath);
		else
			return ".";			
	}
	
	
	class CompilerArgs {
		private List<CharSequence> args;
		
		CompilerArgs() {
			args = new ArrayList<CharSequence>();
		}
		
		public void addKeyValueArg(String key, PathList value) {
			addArg(key);					
			addArg(value);
		}
		
		void addArg(File value) {
			addArg(value.toString());
		}
	
		void addArg(CharSequence key) {
			args.add(key);
		}
			
		String[] toArray() {
			CharSequence[] charArr = 	args.toArray(new CharSequence[0]);
			String[] strArr = new String[charArr.length];
			for (int i = 0; i < strArr.length; i++) 
				strArr[i] = charArr[i].toString();
			return strArr;
		}		
	}
}