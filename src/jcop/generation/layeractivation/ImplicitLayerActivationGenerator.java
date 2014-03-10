package jcop.generation.layeractivation;

import AST.Block;
import AST.Expr;
import AST.ExprStmt;
import AST.MethodAccess;
import AST.ObjectSpecificLayerActivation;
import AST.Stmt;
import jcop.compiler.JCopTypes.JCopAccess;
import jcop.generation.Generator;
import static jcop.Globals.Types.*;

public class ImplicitLayerActivationGenerator extends Generator {

	private ObjectSpecificLayerActivation activationStmt;

	public ImplicitLayerActivationGenerator(ObjectSpecificLayerActivation activationStmt) {
		this.activationStmt = activationStmt;
	}

	public  Block generateLayerActivationStmtBlock() {
		Block b = new Block();
		for (Expr layerID  : activationStmt.getArgList()) {
			Stmt s = new ExprStmt(
				layerID.qualifiesAccess(
					createMethodAccess(
						getMethodName(activationStmt.getActivation()))));				
			b.addStmt(s);
		}		
		return b;
	}
	
	public Stmt createDeactivatonStmt() {
		return new ExprStmt(
				JCopAccess.get(LAYER).qualifiesAccess(
						createMethodAccess("removeLayersForObject")));		
	}
	
	private String getMethodName(boolean activation) {
		if (activation)
			return "setActiveFor";
		else
			return "setInactiveFor";
	}
	
	private MethodAccess createMethodAccess(String methodName) {
		return createMethodAccess(methodName, activationStmt.getTarget());
	}

}
