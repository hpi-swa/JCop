/**
 * 
 */
package jcop.compiler.filecopy;

import java.io.File;
import java.io.FilenameFilter;

import jcop.Globals;
import jcop.Globals.Msg;
import jcop.compiler.CompilerMessageStream;

class FileDeletion {
	private CompilerMessageStream msg;	
	
	FileDeletion() {
		msg = CompilerMessageStream.getInstance();	
	}
	
	boolean deleteAspectClassfilesInDir(File dir) {		
		return deleteFilesInDir(dir, FileNameFilterGenerator.AspectClass);			
	}
	
	boolean deleteClassfilesInDir(File dir) {		
		return deleteFilesInDir(dir, FileNameFilterGenerator.Class);			
	}
	
	boolean deleteSourceFilesInDir(File dir) {		
		return deleteFilesInDir(dir, FileNameFilterGenerator.JavaSource);
	}
	 
	boolean deleteFolder(File folder) {
		if (folder.isDirectory()) {
			boolean deleted = folder.delete();
			msg.maybeLog(Msg.deleteFolder, folder.toString(), deleted ? "" : "FAIL!");
			return deleted;
		}
			
		return false;
	}
	
	private boolean deleteFilesInDir(File toBeDeleted, FilenameFilter filter) {		
		if (toBeDeleted.isDirectory() ) 
			return deleteFiles(toBeDeleted, filter);			
		else {			
			boolean deleted = toBeDeleted.delete();
			msg.maybeLog(Msg.deleteFile, toBeDeleted.toString(), deleted ? "" : "FAIL!");		
			return deleted;
		}
					
	}

	private boolean deleteFiles(File dir, FilenameFilter filter) {		 
		File[] children = getFiles(dir, filter);		
		for (File child : children) { 
			boolean success = deleteFilesInDir(child, filter);
			if (!success) 
				return false;				
		} 
		return true;
	}

	private File[] getFiles(File dir, FilenameFilter filter) {		
		return dir.listFiles(filter);
	}
		
}