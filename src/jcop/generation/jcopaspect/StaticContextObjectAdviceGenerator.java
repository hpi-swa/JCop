/**
 * 
 */
package jcop.generation.jcopaspect;

import jcop.generation.jcopaspect.internal.SourceCodeBuffer;
import jcop.lang.InternalContext;
import AST.ContextDecl;

class StaticContextObjectAdviceGenerator extends AdviceGenerator {
	private ContextDecl decl;

	public StaticContextObjectAdviceGenerator(SourceCodeBuffer advice, ContextDecl decl) {
		super(advice);
		this.decl = decl;		
	}

	public void genContextActivation() {
		SourceCodeBuffer advice = getAdvice();
		advice.putLine("// static context activation for $context$");				
		//advice.putLine("$compositionVarAssignment$.contexts().activateFor(\"", signature, "\");");				
		advice.putLine(" after(jcop.lang.ContextComposition comp) returning: ");
		advice.putLine("execution(jcop.lang.ContextComposition.new(..)) && ");
		advice.putLineOpenBracket(" this(comp) ");
		advice.putLine("comp.activate($context$.getSingleton());");
		advice.closeBracket();
		advice.instantiatePatternWith(new String[][] {
				{"context", decl.getFullQualifiedName()}});		
	}
}
 