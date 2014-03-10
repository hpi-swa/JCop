package jcop.compiler.filecopy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jcop.Globals.Msg;
import jcop.compiler.CompilerMessageStream;

public class FileGenerationHandler {
	//private  FileGenerationHandler singleton;
	private List<File> createdFiles;
	private CompilerMessageStream log; 
	
	public FileGenerationHandler() {
		this.createdFiles = new ArrayList<File>();
		log = CompilerMessageStream.getInstance();
	}

//	public static FileGenerationHandler getInstance() {
//		if (singleton == null)
//			singleton = new FileGenerationHandler();
//		return singleton;
//	}


	public boolean generateDir(File dir) {
		if (!dir.isFile()) {			
			collectEmptyParentDirs(dir);
			if(!dir.exists()) {					
				return dir.mkdirs();
			}
		}		
		return false;
	}
	
	private void collectEmptyParentDirs(File dir){	
		if (dir != null && !dir.exists()) {
			createdFiles.add(dir);			
			collectEmptyParentDirs(dir.getParentFile());
		}
	}

	public void generateDir(String basePath) {
		generateDir(new File(basePath));		
	}

	public File generateFile(File folder, String fileName) {		
		File f = new File(folder, fileName);		
		createdFiles.add(0, f);
		return f;
	}

	public void deleteAllFiles() { 
		for (File f : createdFiles) {			
			boolean b = f.delete();						
			log.maybeLog(f.isFile() ? Msg.deleteFile : Msg.deleteFolder, f.toString(), b ? "" : "FAIL!");				
		}		
		
	}

	public File generateFile(File outputFolder, File sourceFile, String fileName) {
		File parent = new File(outputFolder.getAbsoluteFile(), fileName);
		File f = new File(parent, sourceFile.getName());
		createdFiles.add(0, f);
		return f;		
	}

}
