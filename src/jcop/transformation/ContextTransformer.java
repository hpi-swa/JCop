package jcop.transformation;

import java.util.ArrayList;

import jcop.Globals.ID;
import jcop.Globals.Modifiers;
import jcop.generation.ContextGenerator;
import jcop.generation.jcopaspect.JCopAspect;
import AST.ASTNode;
import AST.AndLogicalExpr;
import AST.AndPointcutExpr;
import AST.BooleanLiteral;
import AST.ClassDecl;
import AST.ContextConstraint;
import AST.ContextDecl;
import AST.ExecutionPointcutExpr;
import AST.Expr;
import AST.IfPointcutExpr;
import AST.List;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.Modifier;
import AST.OrLogicalExpr;
import AST.OrPointcutExpr;
import AST.ParExpr;
import AST.PointcutExpr;
import AST.StringLiteral;
import AST.ThisAccess;
import AST.VarAccess;

public class ContextTransformer extends Transformer {
	private ContextDecl contextDecl;
	private java.util.List<String> executionSignatures;
	private ContextGenerator gen;
	private boolean isStatic;
	
	public ContextTransformer(ContextDecl context) {				
		this.contextDecl = context;
		this.executionSignatures = new ArrayList<String>();
		this.gen = new ContextGenerator(contextDecl);
		parseModifiers(context);
	}

	private void parseModifiers(ContextDecl context) {
		this.isStatic = context.getModifiers().contains(Modifiers.STATIC_ACTIVE);		
		AST.Modifiers old = context.getModifiersNoTransform();
		AST.Modifiers newModifiers = gen.removeModifiers(old, Modifiers.STATIC_ACTIVE);
		old.setModifierList(newModifiers.getModifierList());		
	}

	protected ASTNode<?> transform() {		
		ClassDecl contextClass = gen.generateContextClass(); 				
		ContextConstraint contextConstraint = contextDecl.getContextConstraint();
		Expr transformedPointcut = 
			transformPointcutExpr(contextConstraint.getConstraintDefinitionNoTransform());
		PointcutExpr pointcutSignature = contextConstraint.getConstraintDefinitionNoTransform();//.toString();// "";
//		System.out.println(transformedPointcut);
		MethodDecl isActiveMethod = gen.createIsActiveForMethod(transformedPointcut);
		contextClass.addMemberMethod(isActiveMethod);
		
		JCopAspect.getInstance().addContextActivations(contextDecl, pointcutSignature, executionSignatures);
		
		if (isStatic) {
			JCopAspect.getInstance().addStaticContextActivation(contextDecl);
		}
		return contextClass;
	}

	private Expr transformPointcutExpr(PointcutExpr pointcutExpr) {
		Class c = pointcutExpr.getClass();
		if (c.equals(IfPointcutExpr.class)) {
			return transformPointcutExpr((IfPointcutExpr) pointcutExpr);
		}
		else if (c.equals(AndPointcutExpr.class)) {
			return transformPointcutExpr((AndPointcutExpr) pointcutExpr);
		}
		else if (c.equals(OrPointcutExpr.class)) {
			return transformPointcutExpr((OrPointcutExpr) pointcutExpr);
		}
		else if (c.equals(ExecutionPointcutExpr.class)) {
			return transformPointcutExpr((ExecutionPointcutExpr) pointcutExpr);
		}
		return new BooleanLiteral(true);
	}
	
	private Expr transformPointcutExpr(IfPointcutExpr pointcutExpr) {		
		return  (Expr) pointcutExpr.getExpr().fullCopy();
	}

	 private Expr transformPointcutExpr(AndPointcutExpr pointcutExpr) {
		    return new ParExpr(
		      new AndLogicalExpr(
		      transformPointcutExpr(pointcutExpr.getLhsNoTransform()), 
		      transformPointcutExpr(pointcutExpr.getRhsNoTransform())));
		  }

		  private Expr transformPointcutExpr(OrPointcutExpr pointcutExpr) {
		    return new ParExpr(
		      new OrLogicalExpr(
		      transformPointcutExpr(pointcutExpr.getLhsNoTransform()), 
		      transformPointcutExpr(pointcutExpr.getRhsNoTransform())));
		  }

	private Expr transformPointcutExpr(ExecutionPointcutExpr pointcutExpr) {
		// FIXME add method call this.check(signature, resultOf --> pointcutExpr.getPattern()???		
		// return expr: this.check(signature, "<pattern>");		
		VarAccess signature = new VarAccess("signature");		
		List<Expr> checkParameters = new List<Expr>();
		// get signature from parameter
		checkParameters.add(signature);
		// get the Pattern from Pointcut expr
		String sig = pointcutExpr.getPatternNoTransform().toString();
		sig = sig.replaceAll("  ", " ");		
		//malte: mieees!		
		executionSignatures.add(sig);
		checkParameters.add(new StringLiteral(sig));		
		MethodAccess checkMethod = new MethodAccess("check", checkParameters);		
		Expr checkMethodAccess = new ThisAccess().qualifiesAccess(checkMethod);		
		return checkMethodAccess;
	}
	
	private java.util.List<String> getExecutionSignatures() {
		return executionSignatures;
	}
	

	
	
}
