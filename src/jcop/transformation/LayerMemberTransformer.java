package jcop.transformation;

import java.util.HashSet;

import jcop.Globals;
import jcop.compiler.CompilerMessageStream;
import jcop.generation.jcopaspect.JCopAspect;
import jcop.transformation.lookup.Lookup;
import AST.Block;
import AST.BodyDecl;
import AST.FieldDeclaration;
import AST.InstanceInitializer;
import AST.LayerDecl;
import AST.LayerDeclaration;
import AST.MemberDecl;
import AST.MethodDecl;
import AST.NamedMember;
import AST.OpenLayerDecl;
import AST.Stmt;
import AST.TypeDecl;

abstract public class LayerMemberTransformer extends Transformer {
	protected LayerDeclaration openLayer;
	protected TypeDecl layerClass;
	
	public LayerMemberTransformer(LayerDeclaration openLayer) {
		this.openLayer = openLayer;	
		this.layerClass = Lookup.lookupLayerClassDecl(openLayer);		
	}

	protected  void addDelegationMethodToLayer(MethodDecl delegationMethod, String layername) {		 
		TypeDecl decl = openLayer.hostType().lookupType(Globals.jcopPackage, layername);
		addBodyDeclTo(delegationMethod, decl);
	}
	
	protected void addMemberToHostType(NamedMember field) {
		TypeDecl topLevelType = openLayer.hostType().topLevelType();
		addBodyDeclTo(field, topLevelType);
	}
	
	
	protected void addToInit(Stmt metaInit) {
		layerClass.resetCache();
		InstanceInitializer init = getInitializerOfLayerDec();
		init.getBlock().addStmt(metaInit);
		layerClass.resetCache();
	}
	
	protected InstanceInitializer getInitializerOf(TypeDecl decl) {
		for (BodyDecl bodyDecl : decl.getBodyDecls()) {
			if (bodyDecl instanceof InstanceInitializer) 
				return (InstanceInitializer) bodyDecl;			
		}
		InstanceInitializer init = new InstanceInitializer(new Block());
		decl.addBodyDecl(init);
		return init;
	}
		
	protected InstanceInitializer getInitializerOfLayerDec() {
		return getInitializerOf(layerClass);		
	}
	
	private static HashSet<Object> delegationMethodGeneration = new HashSet<Object>();	
	
	protected boolean firstDelegationMethodGeneration(MemberDecl member) {
		JCopAspect.getInstance().addVisitedMemberForDelegation(member);		
		return(firstVisit(delegationMethodGeneration, member));
	}
		
	private boolean firstVisit(HashSet<Object> set, MemberDecl field) {
		if (set.contains(field))			
			return false;
		else {
			set.add(field);
			return true;
		}		
	}	
	
	private static HashSet<Object> wrapperMethodGeneration = new HashSet<Object>();	
	protected boolean firstwrapperMethodGeneration(FieldDeclaration field) {
		return(firstVisit(wrapperMethodGeneration, field));
	}
}
