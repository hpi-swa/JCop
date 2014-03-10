/**
 * 
 */
package jcop.compiler.filecopy;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jcop.Globals;
import jcop.Globals.Msg;
import jcop.compiler.CompilerMessageStream;

class FileLookup {
	private boolean isJCop;
	private List<String> fileNames;
	private CompilerMessageStream msg =	CompilerMessageStream.getInstance();
	
	FileLookup(boolean isJCop) {
		this.isJCop = isJCop;
	}
	
//	List<String> getFileNames() {
//		if (fileNames == null)
//			fileNames = collectFileNames();
//		return fileNames;
//	}
	
	InputStream createInputStream(String fileName) {
		try {
			File sourceLayerFile = new File(fileName);
			return new FileInputStream(sourceLayerFile);
		} 
		catch (IOException e) {
			msg.log("[FileGeneration]", "cannot create FileInputStream for file ", fileName);
			e.printStackTrace();
		}
		return null; 		
	}
		
	protected List<String> collectFileNames(String sourcepath, String folderName, String fileType) {
		return getFileNamesFromDir(sourcepath + File.separator + folderName, fileType);
	}
	

	
	protected boolean isJCop() {
		return isJCop;
	}
		
	private List<String> getFileNamesFromDir(String folderName, String fileType) {
		File folder = new File(folderName);//getFolder(/*System.getProperty("user.dir")  +File.separator + */folderName).getAbsoluteFile();
		File[] files = folder.listFiles(createFilter(fileType));

		java.util.List<String> fileNames = new ArrayList<String>();
		for(File file : files)
			fileNames.add(file.getAbsolutePath());
		return fileNames;
	}

//	protected String getFileName(File file) {
//		String prefix = "src" + File.separator;
//		String fileName = file.toString();		
//		String trimmedFileName = fileName.substring(prefix.length());		
//		 return trimmedFileName;//new File(trimmedFileName).getAbsolutePath();
//	}
//
//	protected File getFolder( String folderName) {
//		//String prefix = "src" + File.separator;	
//		return new File(src, folderName);		
//	}

	private FileFilter createFilter(final String fileType) {
		return new FileFilter() {					
					public boolean accept(File f) {
						return f.getName().endsWith(fileType);				
					}
				};				
	}
	



}