/**
 * 
 */
package jcop.generation.jcopaspect;

import jcop.Globals;
import jcop.VisitedNodes;
import jcop.compiler.CompilerMessageStream;
import jcop.generation.jcopaspect.internal.SourceCodeBuffer;
import AST.FieldDeclaration;
import AST.Program;
import AST.TypeDecl;

public class PartialFieldAdviceGenerator extends AdviceGenerator {
	private CompilerMessageStream msg =	CompilerMessageStream.getInstance();
	public PartialFieldAdviceGenerator(SourceCodeBuffer advice) {
		super(advice);		
	}

	public void createFieldAccessorAdviceOnce(FieldDeclaration field, String fqnName, String flattenedName) {
		if (VisitedNodes.firstVisit(field)) {
			String type = genReturnTypeAsString(field);
			
			msg.compileTimeLog("[PartialFieldAdviceGenerator]", "create around advice for field ", type);
			
			createWriteFieldAccessorAdvice();
			createReadFieldAccessorAdvice();
			String[][] values = {
					{ "target", field.hostType().topLevelType().getFullQualifiedName() },
					{ "fqn", fqnName }, 
					{ "type", type },
					{ "flattenedName", flattenedName } };
			getAdvice().instantiatePatternWith(values);			
		}		
	}

	private void createReadFieldAccessorAdvice() {
		SourceCodeBuffer advice = getAdvice();
		advice.putLine("//wrapper for reading field access ");
		advice.putLineOpenBracket("Object around($target$ thisObj) : get($type$ $fqn$) &&  target(thisObj)");		

		if (msg.logMode())
			advice.putLine("System.out.println(\"begin: around advice for get $type$ $fqn$\");");

		surroundWithImplicitLayerActivation("return $outermostLayer$.$flattenedName$(thisObj, $composition$.current()).get();");		
		advice.closeBracket();		
	}

	private void createWriteFieldAccessorAdvice() {
		SourceCodeBuffer advice = getAdvice();		
		advice.putLine("//wrapper for writing field access ");
		advice.putLineOpenBracket("void around($type$ obj, $target$ thisObj) : " +
				             "set($type$ $fqn$) && args(obj) && target(thisObj)");		

		if (msg.logMode())
			advice.putLine("System.out.println(\"begin: around advice for set $type$ $fqn$\");");

		surroundWithImplicitLayerActivation("$outermostLayer$.$flattenedName$(thisObj, $currentComposition$).set(obj);");
		advice.closeBracket();				
	}
	
	
	private String genReturnTypeAsString(FieldDeclaration field) {
		TypeDecl type = field.type();
		if (type.isPrimitiveType())
			return type.name();
		else
			return type.fullName();
	}

}