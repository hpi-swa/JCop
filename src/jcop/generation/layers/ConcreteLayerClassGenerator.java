/**
 * 
 */
package jcop.generation.layers;

import jcop.Globals.ID;
import AST.Expr;
import AST.FieldDeclaration;
import AST.LayerDeclaration;
import AST.List;
import AST.MethodDecl;
import AST.ParameterDeclaration;
import AST.ThisAccess;
import AST.VarAccess;

public class ConcreteLayerClassGenerator extends LayerClassGenerator {	

	public ConcreteLayerClassGenerator(LayerDeclaration layer) {
		super(layer);		
	}
	
	public MethodDecl genDelegationMethod(MethodDecl baseMethodDecl, String generatedMethodName) {
		Expr delegation = genDelegationExpr(baseMethodDecl.getParameterListNoTransform(), generatedMethodName);
		MethodDecl method = super.generateDelegationMethod(baseMethodDecl, generatedMethodName, delegation);		
		return method;
	}
	
	public MethodDecl genDelegationMethod(FieldDeclaration toBeWrapped, String generatedMethodName) {
		Expr delegation = genDelegationExpr(generatedMethodName);		
		MethodDecl method = super.createDelegationMethod(toBeWrapped, generatedMethodName, delegation);
		return method;			
	}

	private Expr genDelegationExpr(String genMethodName) {
		return genDelegationExpr(new List<ParameterDeclaration>(), genMethodName);
	}
	
	private Expr genDelegationExpr(List<ParameterDeclaration> originalParams, String genMethodName) {
		List<Expr> argsWithTarget = genLayerParams(generateArgs(originalParams));		
		Expr delegation = genCallToNextLayerExpression(genMethodName, argsWithTarget);
		return delegation;
	}
	
	private Expr genNextLayerProxyAccess() {
		return new VarAccess(ID.composition).qualifiesAccess(
				createMethodAccess(ID.nextLayer, new VarAccess(ID.layerProxyParameterName)));
	}
	
	private  Expr genCallToNextLayerExpression(String generatedMethodName, List<? extends Expr> argsWithTarget) {		
		return 				
				genNextLayerProxyAccess().qualifiesAccess(
						createMethodAccess("get").qualifiesAccess(
								createMethodAccess(generatedMethodName, (List<Expr>)argsWithTarget)));
	}
	protected List<Expr> genLayerParams(List<Expr> toBeExtended) {
		toBeExtended.insertChild(new VarAccess(ID.targetParameterName), 0);
		toBeExtended.insertChild(genNextLayerProxyAccess(), 1);
		toBeExtended.insertChild(new VarAccess(ID.composition), 2);
		return toBeExtended;
	}
	
}