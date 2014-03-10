package jcop.generation;

import static jcop.Globals.Modifiers.FINAL;
import static jcop.Globals.Modifiers.PUBLIC;
import static jcop.Globals.Modifiers.STATIC;
import AST.Access;
import AST.CompilationUnit;
import AST.FieldDeclaration;
import AST.LayerImportDecl;
import AST.TypeAccess;
import AST.VarAccess;

public class LayerImportDeclGenerator extends Generator {	
	private LayerImportDecl importDecl;
	private TypeAccess layerTypeAccess;
	public LayerImportDeclGenerator(LayerImportDecl importDecl) {
		this.importDecl = importDecl;
	}

	public FieldDeclaration genStaticLayerReference() {
		TypeAccess type = genLayerTypeAccess();
		return new FieldDeclaration(
				genModifiers(PUBLIC, STATIC, FINAL),
				(Access) type,
				type.name(),
				
				genLayerTypeAccess().qualifiesAccess(
						new VarAccess(type.name()))
				);
	
	}
	
	public TypeAccess genLayerTypeAccess() {		
		String layerName = parseLayerName(importDecl.getAccess());
		String packageName = parsePackageName(importDecl.getAccess());
		return  new TypeAccess(packageName, layerName);	
	}
	
	private CompilationUnit getCompilationUnit() {
		return (CompilationUnit) importDecl.getParent().getParent();
	}
	
	private String parsePackageName(Access access) {
		String packageName = access.packageName();
		if (packageName.equals(""))
			packageName =  ((CompilationUnit)importDecl.getParent().getParent()).packageName();
		return packageName;
	}

	// malte: this is quite ugly 
	private String parseLayerName(Access a) {
		String str = a.toString();
		return str.substring(str.lastIndexOf(".") + 1);
	}

}
