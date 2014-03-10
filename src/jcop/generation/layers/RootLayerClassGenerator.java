/**
 * 
 */
package jcop.generation.layers;

import static jcop.Globals.Types.InvalidMethodAccessException;
import jcop.Globals;
import jcop.Globals.ID;
import AST.Access;
import AST.ArrayInit;
import AST.ArrayTypeAccess;
import AST.Expr;
import AST.FieldDeclaration;
import AST.LayerDeclaration;
import AST.List;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.NullLiteral;
import AST.ParameterDeclaration;
import AST.ReturnStmt;
import AST.SingleTypeImportDecl;
import AST.SuperAccess;
import AST.TypeAccess;
import AST.VarAccess;
import AST.VariableDeclaration;

	
public class RootLayerClassGenerator extends LayerClassGenerator{
	public RootLayerClassGenerator(LayerDeclaration layer) {
		super(layer);		
	}

	public MethodDecl genDelegationMethod(MethodDecl originalMethodDecl, String generatedMethodName, String originalMethodWrapperName) {		
		List<ParameterDeclaration> originalParams = originalMethodDecl.getParameterList();		
		Expr callToOriginalMethod = generateCallToBaseMethod(originalMethodWrapperName, originalParams);
					MethodDecl method = super.generateDelegationMethod(originalMethodDecl, generatedMethodName, callToOriginalMethod);
		return method;
	}
	private Expr generateCallToBaseMethod(String id, List<ParameterDeclaration> originalParams) {
		return new VarAccess(ID.targetParameterName).qualifiesAccess(
				new MethodAccess(id, generateArgs(originalParams)));
	}
	
	
	public MethodDecl generateDelegationMethod(FieldDeclaration baseFieldDecl, String wrapperMethodName, String wrapperFieldID) {
		//String originalMethodWrapperName = Identifiers.wrappedMethodPrefix + baseFieldDecl.name();
		Expr callToOriginalMethod =generateCallToBaseMethod(wrapperFieldID); 
			
		MethodDecl method = super.createDelegationMethod(baseFieldDecl, wrapperMethodName, callToOriginalMethod);
		//addDelegationMethodToLayer(method, Identifiers.Layer);cr
		return method;
	}	
	private Expr generateCallToBaseMethod(String id) {
		return new VarAccess(ID.targetParameterName).qualifiesAccess(createMethodAccess(id));
	}

	public MethodDecl genDelegationMethodToSuperLayer(MethodDecl originalMethodDecl, String genMethodName) {
		//List<Expr> args = generateArgsFromLayeredMethod(originalMethodDecl.getParameterList());		
		String methodName = Globals.ID.superlayer + genMethodName;
		Expr methodBody = generateCallToBaseMethod(genMethodName, originalMethodDecl.getParameterList());
		MethodDecl method = generateDelegationMethod(originalMethodDecl, methodName, methodBody);
		return method;
	}  

	
//	protected List<ParameterDeclaration> getTransformedParamsFor(MethodDecl baseMethodDecl) {
//			return baseMethodDecl.getParameterList();
//	}
	

	
}