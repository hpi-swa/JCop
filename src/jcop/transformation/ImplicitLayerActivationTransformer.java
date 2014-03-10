package jcop.transformation;

import jcop.generation.layeractivation.ImplicitLayerActivationGenerator;
import AST.Block;
import AST.ObjectSpecificLayerActivation;

public class ImplicitLayerActivationTransformer extends Transformer {
	private ObjectSpecificLayerActivation stmt;
	 
	
	public ImplicitLayerActivationTransformer(ObjectSpecificLayerActivation stmt) {
		this.stmt = stmt;
	}

	protected Block transform() {
		ImplicitLayerActivationGenerator gen = new ImplicitLayerActivationGenerator(stmt);
		Block b = gen.generateLayerActivationStmtBlock();		
		if (isDeactivation()) 			
			b.addStmt(gen.createDeactivatonStmt());		
		return b;
	}
	
	private boolean isDeactivation() {
		return stmt.getNumArg() == 0 && !stmt.getActivation();
	}	
}  
