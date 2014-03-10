package jcop.transformation;

import jcop.generation.ProceedGenerator;
import AST.Access;
import AST.ProceedExpr;

public class ProceedTransformer  extends Transformer  {
	//private ProceedExpr proceed;	
	private Access transformedAccess;
	private ProceedGenerator gen;
	
	public ProceedTransformer(ProceedExpr proceed)  {		
		//this.proceed = proceed;		
		//this.proceedArgs = getArgsWithObjectAndCompositionReference();
		this.gen = new ProceedGenerator(proceed);
	} 
	
	protected Access transform() {		
		transformedAccess = gen.generateAccess();				
		return transformedAccess;
	}
	
	protected ProceedGenerator getGenerator() {
		return this.gen;
	}
	
//	private List<Expr> addCompositionReferenceToList(List<Expr> args) {
//		args.insertChild(new VarAccess(Identifiers.compositionParam),1);
//		return args;
//	}
}
 