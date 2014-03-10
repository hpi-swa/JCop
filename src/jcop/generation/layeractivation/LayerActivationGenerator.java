package jcop.generation.layeractivation;

import jcop.Globals.CompilerOps;
import  jcop.Globals.ID;
import  static jcop.Globals.Types.*;
import jcop.Globals.LoggingProperties;
import jcop.compiler.JCopTypes.JCopAccess;
import jcop.generation.Generator;
import AST.Access;
import AST.Block;
import AST.CatchClause;
import AST.Expr;
import AST.ExprStmt;
import AST.LayerActivation;
import AST.List;
import AST.Opt;
import AST.Program;
import AST.Stmt;
import AST.StringLiteral;
import AST.TryStmt;
import AST.VarAccess;
import AST.VariableDeclaration;


public class LayerActivationGenerator extends Generator {

	private LayerActivation activation;
	private List<Expr> layers;
	private String activationMethodName;
	private static int activeLayerCounter = 0;

	public LayerActivationGenerator(LayerActivation activation, String activationMethodName, List<Expr> layers) {
		this.activation = activation;
		this.layers = layers;
		this.activationMethodName = activationMethodName;
	}

	public Block generateActivationBlock() {
		Block activationBlock = generateLayerActivationBlock();
		TryStmt tryStmt = 
			new TryStmt(
					activation.getBlock().fullCopy(),
					new List<CatchClause>(), 
					getLayerDeActivationMethodAccess(layers));
		activationBlock.addStmt(tryStmt);
		return activationBlock;
	}
	
	private Block generateLayerActivationBlock() {
		Block block = new Block();
		for (Expr layerAccess : layers) 	
			block.addStmt(generateLayerAssignment(layerAccess));		
		return block;
	}

	private Stmt generateLayerAssignment(Expr layerAccess) {					
		String varName = generateVarName();
		Access abstractLayerAccess = 
			getLayerMethodAccess(activationMethodName,	createLayerActivationArgs(layerAccess));		
		VariableDeclaration layerAssignment = 
			new VariableDeclaration(
				JCopAccess.get(COMPOSITION), 
				varName, 
				abstractLayerAccess);
		return layerAssignment;
	}
	
	private List<Expr> createLayerActivationArgs(Expr layer) {
		List<Expr> args = new List<Expr>();
		if (Program.hasOption(CompilerOps.runtimeLogging)) 
			args.add(new StringLiteral(LoggingProperties.loggerName));		
		args.add(layer);
		return args;
	}


	private String generateVarName() {
		return ID.layerCompositionIdentifierPrefix + activeLayerCounter++;		
	}
	
	private String generateVarName(int pos) {
		return ID.layerCompositionIdentifierPrefix + (activeLayerCounter - 1 - pos);
	}
	
	private Access getLayerMethodAccess(String methodname, List<Expr> args) {
		return JCopAccess.get(JCOP).qualifiesAccess(createMethodAccess(ID.current))
			.qualifiesAccess(	createMethodAccess(methodname, args));
	}
	
	private Opt<Block> getLayerDeActivationMethodAccess(List<Expr> args) {
		Block block = new Block();
		for (int i = 0; i < args.getNumChild(); i++) {
			String varName = generateVarName(i);
			VarAccess layerAssignment =	new VarAccess(varName);
			Access abstractLayerAccess = JCopAccess.get(JCOP).qualifiesAccess(createMethodAccess(ID.setComposition, layerAssignment));
			block.addStmt(new ExprStmt(abstractLayerAccess));
		}
		return new Opt<Block>(block);
	}

}
