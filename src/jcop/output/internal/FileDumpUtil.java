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
package jcop.output.internal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import jcop.compiler.filecopy.FileGenerationHandler;

import org.eclipse.core.internal.resources.Folder;

public class FileDumpUtil {
	private String fileExtension;
	private File basePath;
	private FileGenerationHandler fileGeneration;
	
	public FileDumpUtil(File basePath, String fileExtension, FileGenerationHandler auxFileGeneration) {
		this.basePath = basePath;
		this.fileExtension = fileExtension;
		this.fileGeneration = auxFileGeneration;
		fileGeneration.generateDir(basePath);	
	} 
	
	
	public FileDumpUtil(File basePath, String fileExtension) {
		this(basePath, fileExtension, new FileGenerationHandler() );			
	} 

	public String getFileExtension() {
		return fileExtension;
	}

	private File createFileFor(String path) {
		File f = //new File(basePath + File.separator + path + getFileExtension());
		//createdFiles.add(f);
		fileGeneration.generateFile(basePath, path + getFileExtension());
		return f;
	} 

	public void writeToFile(String relativeOutputFilePath, String content) {		
		writeToFile(createFileFor(relativeOutputFilePath), content);		
	}

	public void writeToFile(File outputFile, String content) {		
		try {
			content = content.replaceAll("synthetic", "synchronized");
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
			Writer wr;
			wr = new BufferedWriter(new FileWriter(outputFile));
			wr.write(content);
			wr.flush();
			wr.close();			
			//System.out.println("write file " + outputFile.getAbsolutePath());
		} catch (IOException e) {			
			e.printStackTrace();
			throw new RuntimeException("error:" + outputFile.getPath());			
		}
	}
}