package jcop.output;

import java.io.File;

import jcop.Globals.Types;
import jcop.compiler.filecopy.FileGenerationHandler;
import jcop.generation.jcopaspect.JCopAspect;
import jcop.output.internal.FileDumpUtil;

public class AspectSourceCodeGeneration  {
	private FileDumpUtil fileDumper;
	
	
	public AspectSourceCodeGeneration(File basePath, FileGenerationHandler auxFileGeneration) {
		fileDumper = new FileDumpUtil(basePath, ".aj", auxFileGeneration);		
	}
	
	public void writeToFile(JCopAspect jcopAspect) {
		writeToFile(Types.ASPECT, jcopAspect);		
	}
	
	public void writeToFile(String path, JCopAspect jcopAspect) {
		 fileDumper.writeToFile(Types.ASPECT, jcopAspect.generateSource());		
	}
}
 