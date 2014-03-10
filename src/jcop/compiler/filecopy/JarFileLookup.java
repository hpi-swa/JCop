package jcop.compiler.filecopy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

import jcop.compiler.CompilerMessageStream;
import jcop.compiler.JarAccess;

class JarFileLookup extends FileLookup {	
	private CompilerMessageStream msg =	CompilerMessageStream.getInstance();
	
	JarFileLookup(boolean isJCop) {
		super(isJCop);	
	}	

	@Override
	InputStream createInputStream(String fileName) {
		String entryName = createJarEntryName(fileName);
		return JarAccess.getInstance().getJarInputStream(entryName);
	}
	
	@Override
	protected List<String> collectFileNames(String src, String folderName, String fileType) {		
		return getFileNamesFromJar(src, folderName, fileType);	 
	}
	
	private List<String> getFileNamesFromJar(String srcPrefix, String folderName, String fileType) {		
		String jarfilePathSeparator = "/";
		String prefix =  createJarEntryName(folderName ) + jarfilePathSeparator;
		java.util.List<String> fileNames = new ArrayList<String>();
		Enumeration<JarEntry> entries = getJarFileEntries();
		
		while (entries.hasMoreElements()) {			
			JarEntry jarEntry = entries.nextElement();
			String entryName = jarEntry.toString();
			
			if (machesFilter(entryName, prefix, fileType)) {			
				//String truncatedFileName = entryName.replaceFirst(srcPrefix + jarfilePathSeparator, "");				
				fileNames.add(createFileName(entryName));
			}
		}
		
		return fileNames;
	}

	private Enumeration<JarEntry> getJarFileEntries() {
		 try {
			 return JarAccess.getInstance().getJarFile().entries();
			}
			catch (URISyntaxException e) {
				msg.log("[File Generation]", "cannot find JAR file");
				e.printStackTrace();
			}
			catch (IOException e) {
				msg.log("[File Generation]", "cannot read JAR file");		
				e.printStackTrace();
			}	
			return null; 
	}

	private String createJarEntryName(String fileName) {
		return fileName.replaceAll("\\" + File.separator, "/");
	}
	
	private String createFileName(String entryName) {
		return entryName.replaceAll("/", "\\" + File.separator);
	}
	
//	protected File getFolder(String folderName) {
//		return new File(folderName);		
//	}
//
//	protected String getFileName(File file) {
//		return file.toString();		
//	}
//	
	private boolean machesFilter(String entryName, String prefix, String postfix) {
		return entryName.startsWith(prefix) &&
				   entryName.endsWith(postfix) && 
				   !entryName.equals(prefix);			
	}	
	
}
