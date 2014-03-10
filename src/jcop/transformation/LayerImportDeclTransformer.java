package jcop.transformation;

import static jcop.Globals.Types.*;
import static jcop.Globals.Types.COMPOSITION;
import static jcop.Globals.Types.CONCRETE_LAYER;
import static jcop.Globals.Types.DELEGATION_METHOD_ANNOTATION;
import static jcop.Globals.Types.LAYER;
import static jcop.Globals.Types.LAYERED_METHOD_ANNOTATION;
import static jcop.Globals.Types.LAYER_LIST;
import static jcop.Globals.Types.LAYER_PROVIDER;
import static jcop.Globals.Types.PARTIAL_FIELD;
import static jcop.Globals.Types.PARTIAL_METHOD_ANNOTATION;

import java.util.HashSet;

import jcop.compiler.CompilerConfiguration;
import jcop.compiler.JCopTypes.JCopAccess;
import jcop.generation.LayerImportDeclGenerator;
import AST.ASTNode;
import AST.Access;
import AST.CompilationUnit;
import AST.FieldDeclaration;
import AST.ImportDecl;
import AST.LayerImportDecl;
import AST.List;
import AST.SingleStaticImportDecl;
import AST.SingleTypeImportDecl;
import AST.TypeAccess;
import AST.TypeDecl;

public class LayerImportDeclTransformer extends Transformer {
	private static HashSet<CompilationUnit> visitedUnits = new HashSet<CompilationUnit>();
	private LayerImportDecl importDecl;
	private LayerImportDeclGenerator gen;
	 
	public LayerImportDeclTransformer(LayerImportDecl layerImportDecl) {
	  this.importDecl = layerImportDecl;	
	  this.gen = new LayerImportDeclGenerator(importDecl);
	}

	protected ASTNode<ASTNode> transform() {	
		TypeAccess layerTypeAccess = gen.genLayerTypeAccess();
		if(!CompilerConfiguration.getInstance().astTransformationsDisabled()) 			
			addImports();			
		
		//addStaticLayerReferenceToTypeDecls(layerTypeAccess);		
		addImportToCompilationUnit(layerTypeAccess);
		
		return new SingleTypeImportDecl(layerTypeAccess);	
		//return new SingleStaticImportDecl(layerTypeAccess, layerTypeAccess.name()); 		
	} 
	
//	private void addStaticLayerReferenceToTypeDecls(TypeAccess layerTypeAccess) {		
//		for(TypeDecl decl : getCompilationUnit().getTypeDeclList())
//			addStaticLayerReferenceToTypeDecl(decl);		
//	}
	
	private void addStaticLayerReferenceToTypeDecl(TypeDecl decl) {
		FieldDeclaration f = gen.genStaticLayerReference();
		decl.addBodyDecl(f);
		decl.resetCache();		
	}



	private void addImports() {
		if (!jcopTypesImported()) {
			addImportToCompilationUnit(JCopAccess.get(CONCRETE_LAYER));			
			addImportToCompilationUnit(JCopAccess.get(LAYER));
			addImportToCompilationUnit(JCopAccess.get(JCOP));
			addImportToCompilationUnit(JCopAccess.get(COMPOSITION));		
			addImportToCompilationUnit(JCopAccess.get(PARTIAL_FIELD));
			addImportToCompilationUnit(JCopAccess.get(LAYER_PROVIDER));
			addImportToCompilationUnit(JCopAccess.get(LAYER_LIST));
			addImportToCompilationUnit(JCopAccess.get(LAYERED_METHOD_ANNOTATION));
			addImportToCompilationUnit(JCopAccess.get(PARTIAL_METHOD_ANNOTATION));
			addImportToCompilationUnit(JCopAccess.get(BASE_METHOD_ANNOTATION));
			addImportToCompilationUnit(JCopAccess.get(DELEGATION_METHOD_ANNOTATION));
			visitedUnits.add(getCompilationUnit());
		}		
	}


	
	private boolean jcopTypesImported() {
		return visitedUnits.contains(getCompilationUnit());
	}

	private void addImportToCompilationUnit(Access type) {
		List<ImportDecl> imports = getImports();
		imports.add(new SingleTypeImportDecl(type));
				//new TypeAccess(jcop.Globals.jcopPackage, typename)));
	}

	private List<ImportDecl> getImports() {
		return (List<ImportDecl>) importDecl.getParent();		
	}
		
	private CompilationUnit getCompilationUnit() {
		return (CompilationUnit) getImports().getParent();
	}
}
