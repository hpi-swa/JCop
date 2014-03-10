package jcop.generation.layermembers;

import jcop.transformation.lookup.Lookup;
import AST.LayerDeclaration;
import AST.MethodDecl;
import AST.OpenLayerDecl;

public class LayeredMethodGenerator extends LayerMemberGenerator {
	protected MethodDecl partialMethod;
	protected MethodDecl baseMethod;
	private LayerDeclaration layerDecl;
	
	public LayeredMethodGenerator(MethodDecl partialMethod) {
		this.partialMethod = partialMethod;
		this.baseMethod = Lookup.lookupMethodCorrespondingTo(partialMethod);
	}
	
	public LayeredMethodGenerator(MethodDecl partialMethod, MethodDecl baseMethod) {
		this.partialMethod = partialMethod;
		this.baseMethod = baseMethod;				
	}
	
	public void setBaseMethod(MethodDecl baseMethod) {
		this.baseMethod = baseMethod;	
	}
	
	  public LayerDeclaration getLayer() {
		    if (this.layerDecl == null)
		      this.layerDecl = ((LayerDeclaration)this.partialMethod.getParent().getParent());
		    return this.layerDecl;
		  }
}
