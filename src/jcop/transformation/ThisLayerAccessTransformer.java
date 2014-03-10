package jcop.transformation;

import jcop.Globals.ID;
import AST.ASTNode;
import AST.ClassDecl;
import AST.LayerDecl;
import AST.MethodDecl;
import AST.ParameterDeclaration;
import AST.ThisLayerAccess;
import AST.TypeAccess;
import AST.TypeDecl;
import AST.VarAccess;

public class ThisLayerAccessTransformer extends Transformer {
	private TypeDecl layerDecl;

	public ThisLayerAccessTransformer(ThisLayerAccess thisLayerAccess) {
		MethodDecl pmd = (MethodDecl) thisLayerAccess.enclosingBodyDecl();		
		ClassDecl hostType = (ClassDecl)pmd.getParent().getParent();	
		
		if(hostType instanceof LayerDecl) 		
			layerDecl = hostType;    	
		else 
			layerDecl = getFirstParamOf(pmd);		
	}
	
	private TypeDecl getFirstParamOf(MethodDecl pmd) {		
		ParameterDeclaration firstParam = 
			pmd.getParameterListNoTransform().getChild(0);
		return firstParam.type();		
	}

	protected ASTNode transform() {
		return new VarAccess(ID.layerParameterName);
	}

}
