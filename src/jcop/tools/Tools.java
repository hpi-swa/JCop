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
package jcop.tools;

import java.io.File;
import java.io.FilenameFilter;

import AST.Expr;
import AST.List;

public class Tools {

	public static CharSequence instantiatePattern(CharSequence pattern, CharSequence[][] val) {
		CharSequence[][] instantiatedPatternValues = instantiatePatternValues(val);
		for (int i = 0; i < instantiatedPatternValues.length; i++) {
			pattern = pattern.toString().replace("''$" + instantiatedPatternValues[i][0] + "$", quote(instantiatedPatternValues[i][1]));
			pattern = pattern.toString().replace("$" + instantiatedPatternValues[i][0] + "$", instantiatedPatternValues[i][1]);
		}
		return pattern;
	}
	
	private static CharSequence quote(CharSequence toBeQuoted) {
		char quote = '\"';
		return new StringBuffer(quote).append(toBeQuoted).append(quote);
	}

	private static CharSequence[][] instantiatePatternValues(CharSequence[][] val){
		for (int i = 0; i < val.length; i++) {					
			for (int j = 0; j < val.length; j++) {				
				val[i][1] = val[i][1].toString().replace("$" + val[j][0] + "$", val[j][1]);
			}
		} 		
		return val;
	}

	public static String getPackageFromFilePath(String file) {
		// my\path\class.java
		file = file.replace("/", ".");
		file = file.replace("\\", ".");
		// my.path.class.java
		file = file.substring(0, file.lastIndexOf("."));
		// my.path.class
		file = file.substring(0, file.lastIndexOf("."));
		// my.path
		file = "package " + file + ";";
		// package my.path;
		return file;
	}

	public static String appendToList(String firstElement, String list) {
		if (list.length() > 0)
			firstElement += ",";
		return firstElement + list;
	}

	public static int getArityOfInteger(int number) {
		return Integer.toString(number).length();
	}

	public static String getReturnTypeIfNotVoid(String type) {
		if (type.equals("void"))
			return "";
		else
			return "return";
	}

	

//	public static String listToString(List<Expr> argsWithThis) {
//		String listAsString = " ";
//		for (int i = 0; i < argsWithThis.getNumChild(); i++) {
//			listAsString = argsWithThis.getChild(i) + ", ";
//		}
//		return listAsString.substring(0, listAsString.lastIndexOf(","));
//
//	}

//	public static String getStringWithLineNumbers(String string) {
//		String[] buffer = string.toString().split("\n");
//		String result = "";
//		int maxLengthOfLineNumbers = Tools.getArityOfInteger(buffer.length);
//
//		for (int lineNumber = 1; lineNumber <= buffer.length; lineNumber++) {
//			int lengthOfCurrentLineNumber = Tools.getArityOfInteger(lineNumber);
//
//			String leadingBlanks = "";
//			for (int i = 0; i < maxLengthOfLineNumbers
//					- lengthOfCurrentLineNumber; i++)
//				leadingBlanks += " ";
//
//			result += leadingBlanks + "" + lineNumber + " "
//					+ buffer[lineNumber - 1] + "\n";
//		}
//		return result;
//	}

}
