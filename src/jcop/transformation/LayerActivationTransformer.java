package jcop.transformation;

import jcop.Globals;
import jcop.Globals.ID;
import jcop.generation.layeractivation.LayerActivationGenerator;
import AST.Block;
import AST.Expr;
import AST.LayerActivation;
import AST.List;
import AST.Program;

public class LayerActivationTransformer extends Transformer {
	private LayerActivation activation;
	private List<Expr> layers;
	private LayerActivationGenerator gen;
	

	public LayerActivationTransformer(LayerActivation activation) {		
		this.activation = activation;
		this.layers = activation.getArgsNoTransform().fullCopy();
		this.gen =	new LayerActivationGenerator(activation, getMethodName(), layers);
	}

	protected Block transform() {		
		Block activationBlock = gen.generateActivationBlock();		
		return activationBlock;
	}
		
	private String getMethodName() {
		final String logging = 
			Program.hasOption(Globals.CompilerOps.runtimeLogging) 
			? "WithLogging" 
			: "";
		return activation.getActivation() 
			? ID.addLayer + logging 
			: ID.removeLayer + logging;
	}
}
