/**
 * 
 */
package jcop.generation.jcopaspect;

import java.util.List;

import jcop.generation.jcopaspect.internal.SourceCodeBuffer;

import org.aspectj.weaver.patterns.IfPointcut;

import AST.ContextDecl;
import AST.IfPointcutExpr;
import AST.PointcutExpr;
import AST.Program;

class ContextClassAdviceGenerator extends AdviceGenerator {
	private ContextDecl decl;
	private PointcutExpr pointcut;
	private static boolean pointcutGenerated = false;
	private static final String layeredMethodExecution = "execution(@jcop.lang.LayeredMethod * *.*(..))";

	public ContextClassAdviceGenerator(SourceCodeBuffer advice, ContextDecl decl, PointcutExpr pointcut) {
		super(advice);
		this.decl = decl;		
		this.pointcut = pointcut;
	}

	private void addPointcut()
	  {
	    getAdvice().put(new CharSequence[] { 
	      pointcutDecl(
	      "jcopLookup() :", 
	      pointcut(new CharSequence[] { 
	      "call(* jcop.lang.Layer.*(..)) ||", 
	      "call(* jcop.lang.Composition.*(..)) ||",
	      "call(* jcop.lang.JCop.*(..)) ||",
	       "call(* jcop.lang.LayerProxy.*(..)) ||",
	      "within(jcop.lang.*) ||", 
	      "within(jcop.lang.InternalContext+) ||", 
	      "withincode(jcop.lang.Layer+.new(..) ) ||", 
	      "withincode(@jcop.lang.DelegationMethod * *.*(..)) ||",
	      "withincode(@jcop.lang.LayeredMethod * *.*(..)) ||",
	      "call(@jcop.lang.DelegationMethod * *.*(..)) ||", 
	      "call(boolean InternalContext+.isActive()) ||", 
	      "call(void InternalContext+.activate()) ||", 
	      "call(void InternalContext+.deactivate()) ||", 
	      "(cflow(  call(* InternalContext+.isActiveFor(..))))" })) });  

	    pointcutGenerated = true;
	  }
	
	public void addContextActivation(String signature) {	
//			SourceCodeBuffer advice = getAdvice();
//			advice.putLine("// context ", decl.getFullQualifiedName(), " activation for ", signature);
//			advice.putLineOpenBracket("Object around() : execution(", signature, ")");		
//			advice.putLine("$compositionVarAssignment$.contexts().activateFor(\"", signature, "\");");
//			advice.putLine("try {return proceed();}");
//			advice.putLine("$finallyStmt$");
//			advice.closeBracket();		
		}
	

	public void genContextActivation(List<String> executionSignatures) {
		 if (!pointcutGenerated)
		      addPointcut();
		    SourceCodeBuffer loggingBlock = new SourceCodeBuffer();
		    String loggingStmt = "";
		    if (Program.hasOption("-rtl")) {
		      loggingBlock = 
		        block(new CharSequence[] { 
		        syserr(new CharSequence[] { "'maybe activate context for method' + thisJoinPoint.getSignature().toLongString()" }), 
		        syserr(new CharSequence[] { "'  old context ' + old" }), 
		        syserr(new CharSequence[] { "'  new context ' + jcop.lang.JCop.currentContexts()" }) });

		      loggingStmt = " System.err.println(\"  active contexts \"  + jcop.lang.JCop.currentContexts());";
		    } 

		    SourceCodeBuffer advice = getAdvice();
		    advice.putAdvice(
		      "context activation for $context$", 
		      "Object around() : ", 
		      pointcut(new CharSequence[] {"$pointcut$ &&  !jcopLookup()" }),		      //"(( $pointcut$ && call( * *.*(..)) ) && !cflowbelow($pointcut$ && call( * *.*(..)))) &&",		       
		      block(new CharSequence[] {
		      "ContextComposition ctx = JCop.currentContexts();",
		      "Composition old = JCop.current().withLayer(JCop.currentContexts().$notifyExecution$( $context$.class,thisJoinPoint.getSignature().toLongString()));", 
		      loggingBlock, 
		      tryFinally(
		      "return proceed();", 
		      block(new CharSequence[] { 
		      "JCop.setComposition(old);",
		      //"JCop.setContextComposition(ctx);",
		      loggingStmt })) }));

		    advice.instantiatePatternWith(new CharSequence[][] { 
		      { "context", this.decl.getFullQualifiedName() }, 
		      { "pointcut", generatePointcut(pointcut) } });
		
		
		
	}

	private CharSequence generatePointcut(PointcutExpr pointcut) {
		if(pointcut.getClass() == IfPointcutExpr.class)
			return layeredMethodExecution;
		else
			return pointcut.toString();
	}
}

 