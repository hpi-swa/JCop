package jcop.transformation;

import java.util.ArrayList;
import java.util.List;

import jcop.generation.OpenLayerDeclGenerator;
import jcop.generation.jcopaspect.JCopAspect;
import AST.ASTNode;
import AST.BodyDecl;
import AST.ClassDecl;
import AST.FieldDeclaration;
import AST.MethodDecl;
import AST.OpenLayerDecl;

public class OpenLayerDeclTransformer extends Transformer {
	private OpenLayerDecl openLayer;
	private List<MethodDecl> methods;
	private List<FieldDeclaration> fields;
	private OpenLayerDeclGenerator gen;

	public OpenLayerDeclTransformer(OpenLayerDecl openLayer) {
		this.openLayer = openLayer; 
		this.gen = new OpenLayerDeclGenerator(openLayer); 
		initLayerMembers();
	}
	
	protected ASTNode<ASTNode> transform() {
		JCopAspect.getInstance().addLayerImport(openLayer);
		//Lookup.lookupProgram(layer).resetCache();	
		 
		for (MethodDecl pmd : methods) {						
			PartialMethodSourceTransformer methodTransformer = new PartialMethodSourceTransformer(openLayer, pmd);
			MethodDecl decl = methodTransformer.transform();
			addLayerMemberToEnclosingClass(decl);			
		}
		for (FieldDeclaration partialField : fields) {
			PartialFieldTransformer fieldTransformer = new PartialFieldTransformer(openLayer, partialField);
			fieldTransformer.transform();
		}
		openLayer.setBodyDeclList(new AST.List<BodyDecl>());		
		return gen.createDummyNode();
	}
 
	private void initLayerMembers() {
		AST.List<BodyDecl> members = openLayer.getBodyDeclListNoTransform();
		methods = new ArrayList<MethodDecl>();
		fields = new ArrayList<FieldDeclaration>();  
		for (BodyDecl member : members) {			
			if (member instanceof MethodDecl) 			
				methods.add((MethodDecl) member);			
			if (member instanceof FieldDeclaration)
				fields.add((FieldDeclaration) member);
		}
	}

	public void addLayerField() {		
		openLayer.hostType().addMemberField(gen.generateSingletonReference());			
	}
	
	private void addLayerMemberToEnclosingClass(MethodDecl decl) {
		// toplevel removed for nested class support
		ClassDecl host = (ClassDecl) openLayer.hostType();//.topLevelType();
		host.resetCache();
		host.addMemberMethod(decl);
		host.resetCache();

		// System.out.println("after adding \n" + decl + "\n-->\n" + host);
		// layerMethod.setParent(bodyList);
		// bodyList.addChild(layerMethod);
		// pmd.setParent(bodyList);
		// bodyList.addChild(pmd);
	}
}
