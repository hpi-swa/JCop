package jcop.transformation;

import AST.LayerDeclaration;
import AST.MethodDecl;

abstract public class PartialMethodTransformer extends LayerMemberTransformer {

	public PartialMethodTransformer(LayerDeclaration openLayer) {
		super(openLayer);
	}
	
	abstract protected MethodDecl transform();

}
