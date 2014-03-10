package jcop.output;

import java.io.File;

import jcop.Globals;
import jcop.output.internal.FileDumpUtil;
import AST.CompilationUnit;
import AST.TypeDecl;

public class XMLOutlineGenerator {
	protected FileDumpUtil fileDumper;
	//protected StringBuffer buffer;

	public XMLOutlineGenerator(File basePath) {		
		fileDumper = new FileDumpUtil(basePath, ".xml");
	}
	
	
	public void writeToFile(CompilationUnit unit) { 
		if (isAuxiliaryJCopClass(unit))
			return;
		
		StringBuffer buffer = createBuffer();
		unit.printOutline(buffer);
		 
		for (TypeDecl decl : unit.getTypeDecls()) {			
		   fileDumper.writeToFile(decl.constantPoolName(), buffer.toString());
		}	
		
		
	}
	
	protected StringBuffer createBuffer() {
		StringBuffer s = new StringBuffer();
		s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		return s;
	}

	
	protected boolean isAuxiliaryJCopClass(CompilationUnit unit) {
		return unit.getPackageDecl().equals(Globals.jcopPackage);		
	}	
	
	
}
