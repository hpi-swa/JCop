package jcop.compiler.filecopy;

import java.io.File;
import java.io.FilenameFilter;
import jcop.Globals;

public class FileNameFilterGenerator {	
	public static final FilenameFilter Class =  getFileFilter("class");			
	public static final FilenameFilter JavaSource = getFileFilter("java", "aj");			
	public static final FilenameFilter AspectClass = getFileFilter(Globals.Files.jcopAspect);		
	public static final FilenameFilter JCopSource = getFileFilter(Globals.lang);	
	
	private  static FilenameFilter getFileFilter(final String... fileTypes) {
		return new FilenameFilter() {
			public boolean accept(File dir, String name) {
				for (String fileType : fileTypes) {
					if (name.endsWith(fileType))
						return true;
				}				
				return new File(dir, name).isDirectory();
			}
		};
	}
}
