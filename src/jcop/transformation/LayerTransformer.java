package jcop.transformation;

import java.util.HashSet;

import jcop.Globals.ID;
import jcop.generation.layers.LayerClassGenerator;
import AST.BodyDecl;
import AST.LayerDecl;
import AST.FieldDeclaration;
import AST.List;
import AST.MemberDecl;
import AST.MethodDecl;

public abstract class LayerTransformer extends Transformer {
	public static HashSet<MemberDecl> visitedBaseMemberForCodeGeneration = new HashSet<MemberDecl>();  
    public static HashSet<MemberDecl> visitedBaseMemberForDelegationMethodGeneration = new HashSet<MemberDecl>();
    private LayerClassGenerator layerGenerator;
	//private LayerInClass layer;
	protected LayerDecl layer;
	private List<BodyDecl> bodyDeclListOfHostType;
	
	public LayerTransformer(LayerDecl layer) {
		this.layer = layer;
		bodyDeclListOfHostType = layer.enclosingType().getBodyDeclListNoTransform();
		layerGenerator = new LayerClassGenerator(this.layer);
	}

	protected List<BodyDecl> getBodyDeclListOfHostType() {		
		return bodyDeclListOfHostType;
	}
	
	protected boolean exitsWrapperMethod(MemberDecl member, String id){
		return findWrapperMethod(member, id) != null;
	}
	
	protected  MethodDecl findWrapperMethod(MemberDecl member, String id){
		List<BodyDecl> bodyList = getBodyDeclListOfHostType();
		   for (BodyDecl bodyDecl : bodyList) {
			   if ((bodyDecl instanceof FieldDeclaration) && bodyDecl.toString().equals(ID.wrappedMethodPrefix + bodyDecl.hostType().getID() + "$$$"+ id))
				   return (MethodDecl) bodyDecl;
		   }
		   return null;
	}
	
	protected boolean wrapperCreatedFor(MemberDecl member){
		return visitedBaseMemberForDelegationMethodGeneration.contains(member);
	}
	
	protected LayerClassGenerator getLayerGenerator() {
		return this.layerGenerator;
	}
	
	public LayerDecl getLayer() {
		return layer;
	}
		
	abstract public void generateDelegationMethods() ;
}
