package jcop.compiler.filecopy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jcop.compiler.CompilerConfiguration;
import AST.Program;

public class LayerFolderLookup {		

	public List<File> getCompilationUnits() {	
		File layerFolder = getLayerFolder();
		List<File> fileList = new ArrayList<File>();
		return collectFiles(fileList, layerFolder);
		
		
	}

	private List<File> collectFiles(List<File> fileList, File layerFolder) {
		for (File cu : layerFolder.listFiles(FileNameFilterGenerator.JCopSource)) {
			if (cu.isFile())
				fileList.add(cu);
			else
				collectFiles(fileList, cu);
		}
		return fileList;
	}

	private File getLayerFolder() {
		String layerPath =  CompilerConfiguration.getInstance().getValueForOption(jcop.Globals.CompilerOps.layerpath);		
		File layerFolder = new File(layerPath);
		// currently only support for one dir:				
		if (!layerFolder.isAbsolute() && CompilerConfiguration.getInstance().hasSourcePath()) {
			String sourcePath = CompilerConfiguration.getInstance().getValueForOption(jcop.Globals.CompilerOps.sourcepath);
			layerFolder = new File(sourcePath, layerFolder.getPath());
		}
		return layerFolder;
	}
}