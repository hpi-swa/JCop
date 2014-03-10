package jcop.transformation.lookup;

import java.util.Hashtable;

import jcop.Globals.Msg;
import AST.BodyDecl;
import AST.ClassDecl;
import AST.ImportDecl;
import AST.LayerDecl;
import AST.LayerDeclaration;
import AST.List;
import AST.MethodDecl;
import AST.SimpleSet;
import AST.TypeDecl;

public class Lookup {

	private static Hashtable<MethodDecl, MethodDecl> baseMethods;

	static {
		baseMethods = new Hashtable<MethodDecl, MethodDecl>();
	}

	public static  void setBaseForPartialMethod(MethodDecl originalMethod, MethodDecl partialMethod) {
		baseMethods.put(partialMethod, originalMethod);
		baseMethods.put(originalMethod, originalMethod);
	}
	
	public static MethodDecl lookupMethodCorrespondingTo(MethodDecl pmd) {
		try {
			ClassDecl host = (ClassDecl)pmd.hostType();			
			return lookupMethodCorrespondingTo(host,  pmd);
		}
		catch (Exception e) {
			System.err.println("Error: cannot lookup method corresponding to " + pmd.getFullQualifiedName());
			return pmd;
		}
	}
	
	private static MethodDecl lookupMethodCorrespondingTo(ClassDecl host, MethodDecl pmd) {		
		MethodDecl baseMethod = findBaseMethod(host, pmd);
		/*
		 * if baseMethod == null, it is a layer local class and null should be
		 * returned
		 */
		if (!baseMethods.contains(pmd) && baseMethod != null)
			baseMethods.put(pmd, baseMethod);
		return baseMethods.get(pmd);
	}

	private static MethodDecl findBaseMethod(ClassDecl host, MethodDecl pmd) {
		// System.out.println("find base method, host: " + host.getID() +
		// ", method:" + pmd);
		;		
		
		String signatureOfPartialMethod = pmd.signature();
		for (BodyDecl bodyDecl : host.getBodyDeclListNoTransform()) {
			if (bodyDecl instanceof MethodDecl) {
				String signature = ((MethodDecl) bodyDecl).signature();
				if (signature.equals(signatureOfPartialMethod)) {					
					return (MethodDecl) bodyDecl;
				}
			}
		}
		if (host.superclass() != null)
			return lookupMethodCorrespondingTo(host.superclass(), pmd);
		return null;
	}
	
	
	
		
	public static ClassDecl lookupLayerClassDecl(LayerDeclaration layerDecl) {
		if(layerDecl.hostType() == layerDecl)
			return (LayerDecl)layerDecl.hostType();
		
		SimpleSet s = layerDecl.hostType().lookupType(layerDecl.getID());
		if (s.isEmpty())
			throw new RuntimeException(Msg.LayerDeclarationNotFound + layerDecl.getID());
		return (ClassDecl)s.iterator().next();
		
		
//		//layer.topLevelType().lookupType(name)		
//		ClassDecl classDecl = null ;	
//		List<ImportDecl> list = getImportsOfHostType(layerDecl);		
//		classDecl = findClassDeclInImports(list, layerDecl);		
//		return classDecl;
	}
	
	private static ClassDecl findClassDeclInImports(List<ImportDecl> list, LayerDeclaration decl) {
		for(ImportDecl imp : list) {
			TypeDecl importDecl = imp.getAccess().type();
			if (importDecl.name().equals(decl.getID())) 
				return  (ClassDecl)importDecl;					
		}	 
		throw new RuntimeException(Msg.LayerDeclarationNotFound + decl.getID());
	}

	private static List<ImportDecl> getImportsOfHostType(LayerDeclaration decl) {	
		return decl.hostType().topLevelType().compilationUnit().getImportDeclList();
	}
}
