package jcop.output;

import static jcop.Globals.Types.*;
import java.io.File;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import jcop.compiler.JCopTypes;
import jcop.compiler.JCopTypes.JCopAccess;
import jcop.compiler.filecopy.FileGenerationHandler;
import jcop.generation.jcopaspect.JCopAspect;
import jcop.output.internal.FileDumpUtil;
import AST.CompilationUnit;
import AST.ImportDecl;
import AST.PackageAccess;
import AST.Program;
import AST.TypeDecl;

public class SourceCodeGenerator {
	private FileDumpUtil fileDumper;
	private FileGenerationHandler fileHandler;
	private File basePath;
	
	public SourceCodeGenerator() {
		this.basePath = createBasePath();		
		fileHandler = new FileGenerationHandler();
		fileDumper = new FileDumpUtil(basePath, ".java", fileHandler);
	}
	
	private File createBasePath() {
		String path = 
			Program.hasValueForOption(Globals.CompilerOps.dumpSources)
				? Program.getValueForOption(Globals.CompilerOps.dumpSources)
				:  "*dump*";
		return new File(path); 
	}
	
	public File getSourcedumpPath() {
		return basePath;
	}

//	public void writeToFile(TypeDecl decl){
//		writeToFile(decl, decl.toString());
//	}	
	
	public void deleteAllFiles() {
		fileHandler.deleteAllFiles();
	}
	
	public void writeToFile(CompilationUnit unit) {	
		
		for (TypeDecl decl : unit.getTypeDecls()) {
		   String body = decl.toString();		  
		   body = body.replaceAll("synthetic", "synchronized");
//		   if (decl.getID().equals(JCopAccess.get(COMPOSITION))) {
//			   body = body.replaceAll("protected Object initialValue\\(\\)", ""); 
//			   body = body.replace("return initialValue();", "");	   
//		   } 		   
		   writeToFile(decl, createPackageDecl(unit) + createImportDecls(unit) + body);
		}		
    }
	
	private void writeToFile(TypeDecl decl, String content){
		fileDumper.writeToFile(decl.constantPoolName(), content);
	}
	
	private String createPackageDecl(CompilationUnit unit) {		
		if (unit.getPackageDecl().toString().equals(""))
			return "";
		else
			 return "package " +  unit.getPackageDecl().toString() + ";\n";		
	}
	
	private String createImportDecls(CompilationUnit unit) {
		StringBuffer b = new StringBuffer();
		for (ImportDecl decl : unit.getImportDeclList())
			b.append(decl.toString()).append("\n");
		return b.toString();
	}

	public void dumpAspect() {		
		new AspectSourceCodeGeneration(new File(basePath, Globals.jcopFolder), fileHandler)
			.writeToFile(JCopAspect.getInstance());		
	}


}
