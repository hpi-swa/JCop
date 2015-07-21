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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jcop.Globals;
import jcop.Globals.CompilerOps;

public class ArgumentParser {

	private String rootDir;
	private List<String> args;
	private final String quote = "\"";
	
	public ArgumentParser(String[] args) {		
		this.rootDir = System.getProperty("user.dir");
		this.args= new ArrayList<String>(Arrays.asList(args));		
		joinQuotedValues();	
		splitRootDirFromClassPath();
		removeFileEnding();
	}

	private void removeFileEnding() {
		int lastIndex = args.size()-1;
		String file = args.get(lastIndex);		
		file = file.replaceAll(Globals.fileExtension,"");
		file = file.replaceAll(Globals.javaFileExtension,"");
		args.set(lastIndex, file);		
	}

	private void splitRootDirFromClassPath() {
		String filePath = readFilePath();		
		String classPath = filePath;
		int lastFolderSeparator = filePath.lastIndexOf(File.separator) + 1;		
		if(lastFolderSeparator > 1) {			
			rootDir = filePath.substring(0, lastFolderSeparator - 1);
			classPath = filePath.substring(lastFolderSeparator);		
			addSourcepath(args, rootDir);		
		}				
		args.add(classPath);
		
	}


	private void addSourcepath(List<String> args, String string) {
		args.add(CompilerOps.sourcepath);
		args.add(string);
		//args.add(".");		
	}

	private String readFilePath() {
		return args.remove(args.size()-1);
	}

	private void joinQuotedValues() {
		for (int i = 0; i < args.size(); i++) {
			String arg = args.get(i);
			if (beginsButNotEndsWithQuotation(arg)) {
				joinArgsAt(i);				
				joinQuotedValues();				
			}	
			args.set(i, removeQuotes(args.get(i)));			
		}		
	}

	private void joinArgsAt(int pos) {		
	    String jointVar = joinArgs(pos);
	    replaceOldArgsWithJointVar(pos, jointVar);	    
	} 

	private String removeQuotes(String jointVar) {		
		if (jointVar.startsWith(quote) && jointVar.endsWith(quote))
			return jointVar.substring(1, jointVar.length()-1);
		return jointVar;
	}

	private String joinArgs(int pos) {
		String arg = args.get(pos);
		String nextArg = args.get(pos + 1);
	    return arg + " " + nextArg;
	}

	private void replaceOldArgsWithJointVar(int pos, String newArg) {
		args.remove(pos);	   	    
	    args.remove(pos);
	    args.add(pos, newArg);		
	}

	private boolean beginsButNotEndsWithQuotation(String arg) {		 
		return (arg.startsWith(quote) && !arg.endsWith(quote));		
	}


	public String[] getParsedArgs() {		
		addDefaultValues();		
		return args.toArray(new String[]{});		
//		String classPath = parseClassPath();		
//		
//		
//		
//		if(lastFolderSeparator > 1) {
//			rootDir = classPath.substring(0, lastFolderSeparator - 1);
//		    classPath = classPath.substring(lastFolderSeparator);		
//			newArgs = new String[args.length + 2];			
//			System.arraycopy(args, 0, newArgs, 0, args.length);
//			newArgs[newArgs.length - 3] = "-rootDir";
//			newArgs[newArgs.length - 2] = rootDir;
//			newArgs[newArgs.length - 1] = classPath;
//		}
//		else {
//			newArgs = args;
//		}	
//		return newArgs;				
	}
	
	private void addDefaultValues() {
		if (!hasOption(CompilerOps.sourcepath))
			addOption(CompilerOps.sourcepath, ".");
	}

	private void addOption(String key, String value) {
		args.add(0, key);
		args.add(1, value);
	}

	private boolean hasOption(String key) {
		return args.contains(key);
	}

//	private String parseClassPath() {	
//		return args.get(args.size()-1);
////		String pathPart = args.get(args.size() - 1);
////		StringBuffer parts = new StringBuffer();
////		parts.append(pathPart);
////		
////		if (isLastPart(pathPart)) {			
////			for (int i = args.length - 2; i >= 0; i++) {
////				parts.append(args[i]);
////				if (args[i].startsWith("\""))
////					break;
////			}			
////		}		
////		return parts.toString();
//	}	
	
//	private boolean isLastPart(String pathPart) {
//		return (pathPart.endsWith("\"") && !pathPart.startsWith("\""));		
//	}
}
