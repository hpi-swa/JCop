/**
 * 
 */
package jcop.generation.jcopaspect;

import jcop.generation.jcopaspect.internal.SourceCodeBuffer;

public class AdviceGenerator {
	protected SourceCodeBuffer advice;
		
	public AdviceGenerator (SourceCodeBuffer advice) {
		this.advice = advice;
	}
	
	public SourceCodeBuffer getAdvice() {
		return this.advice;
	}
	
	protected void surroundWithImplicitLayerActivation(String string) {
		SourceCodeBuffer advice = getAdvice();
		advice.putLine("$compositionVarAssignment$.addLayer($composition$.getImplicitlyActivatedLayers(\"$type$ $fqn$\",thisObj));");
		surroundWithLayerActivation(string);
	}

	private void surroundWithLayerActivation(String string) {
		SourceCodeBuffer advice = getAdvice();
		advice.putLine("try {", string, "}");
		advice.putLine("$finallyStmt$");
	}
	
	 public SourceCodeBuffer tryFinally(CharSequence tryBlock, CharSequence finallyBlock) {
		    SourceCodeBuffer buffer = new SourceCodeBuffer();
		    buffer.putTryFinally(tryBlock, finallyBlock);
		    return buffer;
		  }
	
	public SourceCodeBuffer syserr(CharSequence[] arg) {
	    SourceCodeBuffer buffer = new SourceCodeBuffer();
	    buffer.put(new CharSequence[] { "System.err.println(" });
	    for (int i = 0; i < arg.length; i++) {
	      String parsedArg = arg[i].toString().replaceAll("'", "\"");
	      if (i > 0) buffer.put(new CharSequence[] { " + " });
	      buffer.put(new CharSequence[] { parsedArg });
	    }
	    buffer.putLine(new CharSequence[] { ");" });
	    return buffer;
	  }

	  public SourceCodeBuffer pointcut(CharSequence[] ptcs) {
	    SourceCodeBuffer pc = new SourceCodeBuffer();
	    pc.indent(true);
	    pc.indent(true);
	    for (CharSequence ptc : ptcs) {
	      pc.putLine(new CharSequence[] { ptc });
	    }
	    return pc;
	  }

	  public SourceCodeBuffer pointcutDecl(CharSequence header, SourceCodeBuffer pointcut) {
	    SourceCodeBuffer pc = new SourceCodeBuffer();
	    pc.indent(true);
	    pc.indent(true);
	    pc.putLine(new CharSequence[] { "pointcut ", header });
	    pc.indent(true);
	    pc.indent(true);
	    pc.add(pointcut);
	    pc.putLine(new CharSequence[] { ";" });	    
	    return pc;
	  }

	  public SourceCodeBuffer block(CharSequence[] stmts)
	  {
	    SourceCodeBuffer buffer = new SourceCodeBuffer();
	    buffer.putBlock(stmts);
	    return buffer;
	  }

	
}