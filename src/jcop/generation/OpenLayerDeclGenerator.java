package jcop.generation;

import static jcop.Globals.Modifiers.FINAL;
import static jcop.Globals.Modifiers.PUBLIC;
import static jcop.Globals.Modifiers.STATIC;
import jcop.generation.layers.LayerGenerator;
import AST.ASTNode;
import AST.Block;
import AST.ClassDecl;
import AST.ClassInstanceExpr;
import AST.Expr;
import AST.FieldDeclaration;
import AST.InstanceInitializer;
import AST.LayerDeclaration;
import AST.List;
import AST.Opt;
import AST.Stmt;
import AST.TypeAccess;

public class OpenLayerDeclGenerator extends LayerGenerator {
	private LayerDeclaration openLayerDecl;

	public OpenLayerDeclGenerator(LayerDeclaration openLayerDecl) {
		super(openLayerDecl.hostLayer());
		this.openLayerDecl = openLayerDecl;
	}

//	public ClassDecl createDummyClass() {		
//		return new ClassDecl(
//				new Modifiers(),
//				createDummyClassName(layer.getID()), 
//				new Opt<Access>(), 
//				new AST.List<Access>(), 
//				new AST.List<BodyDecl>());
//	}
	
//
//	private  String createDummyClassName(String layerID) {
//		int millis = (int)(System.currentTimeMillis() + Math.random()) / 100;
//		return "DummyForLayer$" +layerID + millis;		
//	}

	public ASTNode<ASTNode> createDummyNode() {		
		return new InstanceInitializer(new Block(new List<Stmt>()));
	}

	public FieldDeclaration generateSingletonReference() {
		ClassDecl layerDecl = this.openLayerDecl.hostLayer();
		return new FieldDeclaration(
				genModifiers(PUBLIC, STATIC, FINAL), 
				createLayerTypeAccess(),  
				openLayerDecl.getID(), 				
				new Opt<Expr>(
						new ClassInstanceExpr(
								createLayerTypeAccess(), 
								new List<Expr>())));
	}

}
