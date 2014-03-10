package jcop.transformation;

import AST.Access;

import AST.SuperProceedExpr;

public class SuperProceedTransformer extends ProceedTransformer {



	
	public SuperProceedTransformer(SuperProceedExpr superProceedExpr) {
		super(superProceedExpr);
	}

	@Override
	protected Access transform() {
		return getGenerator().generateSuperAccess();
	}
	
	
}
