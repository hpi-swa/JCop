package jcop.generation.jcopaspect;

import jcop.compiler.CompilerMessageStream;
import jcop.generation.jcopaspect.internal.SourceCodeBuffer;
import AST.ParseName;


public class SubjectLayerAdviceGenerator extends AdviceGenerator {

	public SubjectLayerAdviceGenerator(SourceCodeBuffer advice) {
		super(advice);
	}

	public void genLayerActivationStatement(java.util.List<ParseName> subjects, String layerComposition) {
		SourceCodeBuffer advice = getAdvice();
		advice.putCommentLine("advice for subject-specific layer activation (SubjectLayerAdviceGenerator.genLayerActivationStatement)");
		advice.putLine("Object around():");
		advice.indent(true);
		StringBuffer ptc = getPointcutExpressions(subjects);
		getAdvice().putLine(ptc);
		//writeFormattedPointcutExpression(pointcutExpression, layerComposition);		
		writeAdviceBody();		
		advice.instantiatePatternWith("layerComposition", layerComposition);
	}
	
//	private void writeFormattedPointcutExpression(String pointcut, String layers) {
//		pointcut = pointcut.replaceAll("\\&\\&", "REPLACETHIS&& ");
//		pointcut = pointcut.replaceAll("\\|\\|", "REPLACETHIS|| ");
//		String[] parts = pointcut.split("REPLACETHIS");
//		for (String part : parts) {
//			getAdvice().putLine(part);
//		}
//	}
	
	private StringBuffer getPointcutExpressions(java.util.List<ParseName> subjects ) {
		StringBuffer pct = new StringBuffer("(");
		for(ParseName subject : subjects)
			pct.append(getPointcutExpression(subject));
		pct.append(')');    		
		return pct;
}

private String getPointcutExpression(ParseName typeAccess) {		
	String expr = String.format("cflowbelow(call(* *.%s+.*(..) )) && call(@jcop.lang.LayeredMethod * *.*(..))",  typeAccess);
	return expr;		
}

	

	
	private void writeAdviceBody() {		
		SourceCodeBuffer advice = getAdvice();
		advice.putLineOpenBracket();		
		advice.putLine("$compositionVarAssignment$.addLayer($layerComposition$);");
		
		if (CompilerMessageStream.getInstance().logMode())
			advice.putSysoutStmt("advice body, current composition: $currentComposition$");
		
		advice.putLine("Object return$$value = null;");		
		advice.putLine("try { return$$value = proceed(); }");
		advice.putLine("$finallyStmt$");		
		advice.putLine("return return$$value;");
		advice.closeBracket();	
	}	
	

}
