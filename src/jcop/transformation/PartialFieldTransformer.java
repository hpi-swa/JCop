package jcop.transformation;

import static jcop.Globals.Types.*;
import jcop.Globals.Types;
import jcop.generation.jcopaspect.JCopAspect;
import jcop.generation.layermembers.PartialFieldGenerator;
import jcop.generation.layers.ConcreteLayerClassGenerator;
import jcop.generation.layers.InstanceLayerClassGenerator;
import jcop.generation.layers.RootLayerClassGenerator;
import AST.ASTNode;
import AST.Expr;
import AST.FieldDeclaration;
import AST.LayerDeclaration;
import AST.MethodDecl;
import AST.OpenLayerDecl;
import AST.Opt;
import AST.Stmt;
import AST.TypeDecl;

public class PartialFieldTransformer extends LayerMemberTransformer {
	private FieldDeclaration partialField;
	private FieldDeclaration baseField;
	private PartialFieldGenerator fieldGen;

	public PartialFieldTransformer(LayerDeclaration openLayer, FieldDeclaration field) {
		super(openLayer);
		this.partialField = field;
		this.baseField = lookupBaseDeclaration();		
		fieldGen = new PartialFieldGenerator(field, baseField, openLayer);
	}

	public FieldDeclaration getField() {
		return partialField;
	}

	protected ASTNode<?> transform() {
		createAroundAdvice();
		
		if(!hasBaseDeclaration())
			createBaseDeclaration();
		
		createWrapperOnce(baseField);
		createWrapperOnce(partialField);
		createDelegationMethods();
		return partialField;
	}
	
	private void createWrapperOnce(FieldDeclaration toBeWrapped) {
		if (firstwrapperMethodGeneration(toBeWrapped))
			createWrapper(toBeWrapped);		
	}

	private boolean hasBaseDeclaration() {
		return baseField != null;		
	}

	private void createBaseDeclaration() {		
		baseField = fieldGen.createBaseField();
		fieldGen.setBaseField(baseField);
		addMemberToHostType(baseField);
	}



//	if (VisitedNodes.secondVisit(baseMethod)) 
//		transformBaseMethod();		
//	//malte: visitedBaseMemberForCodeGeneration.add(pmd);
//	//visitedBaseMemberForDelegationMethodGeneration.add(baseMethod);
//	generateDelegationMethods();
//	return partialGen.generateLayeredMethod();

	public void createDelegationMethods() {			
		String wrapperMethodID = fieldGen.generateDelegationMethodName(partialField);	
		if (firstDelegationMethodGeneration(baseField)) {			
			addDelegationMethodInRootLayer(wrapperMethodID);
			addDelegationMethodInConcreteLayer(wrapperMethodID);
		}
		generateDelegationMethodsInInstanceLayer(wrapperMethodID);		
	}

	private void generateDelegationMethodsInInstanceLayer(String wrapperMethodID) {
		InstanceLayerClassGenerator gen = new InstanceLayerClassGenerator(openLayer);
		String wrapperFieldID = fieldGen.getWrapperFieldName(partialField);
		MethodDecl method = gen.generateDelegationMethod(partialField, wrapperMethodID, wrapperFieldID);		
		addBodyDeclTo(method, openLayer.hostLayer());		
		Stmt metaInit = fieldGen.generatePartialFieldMetaClassInstantiation();
		addToInit(metaInit);		
	}

	private void addDelegationMethodInRootLayer(String wrapperMethodID) {
		LayerDeclaration layerDecl = this.openLayer;
		RootLayerClassGenerator gen = new RootLayerClassGenerator(layerDecl);
		//String wrapperFieldID = fieldGen.getWrapperFieldName(baseField);
		String wrapperFieldID = fieldGen.getWrapperFieldName(baseField);
		MethodDecl d =  gen.generateDelegationMethod(baseField, wrapperMethodID, wrapperFieldID);
		addDelegationMethodToLayer(d, Types.LAYER);
	}

	private void addDelegationMethodInConcreteLayer(String generatedMethodName) {
		LayerDeclaration layerDecl = this.openLayer;
		MethodDecl d = new ConcreteLayerClassGenerator(layerDecl)
			.genDelegationMethod(baseField, generatedMethodName);
		addDelegationMethodToLayer(d, Types.CONCRETE_LAYER);
	}

//	private String getDelegationMethodName(FieldDeclaration layeredState) {
//		String id = layeredState.getID();
//		return createDelegationMethodName(layeredState, id);
//	}	
//	private void createWrapperForBaseField() {
//		FieldDeclaration wrapperField = fieldGen.createLayeredStateField(baseField);
//		addFieldToHostType(wrapperField);		
//		createWrapperMethod(wrapperField);
//		moveFieldInitToInitializer(baseField, wrapperField);
//	}
//	
	
	private void createWrapper(FieldDeclaration toBeWrapped) {	
		FieldDeclaration wrapperField = fieldGen.createLayeredStateField(toBeWrapped);
		addMemberToHostType(wrapperField);		
		createWrapperMethod(wrapperField);
		moveFieldInitToInitializer(toBeWrapped, wrapperField);
		//lookupBaseDeclaration().setInitOpt(new Opt<Expr>());			
	}

//	private void createWrapperMethodForBaseField() {
//		MethodDecl wrapper = fieldGen.createWrapperMethodForBaseField();				
//		addMethodToHostType(wrapper);
//	}
	private void createWrapperMethod(FieldDeclaration wrappee) {		
		MethodDecl wrapper = fieldGen.createWrapperMethodFor(wrappee);				
		addMemberToHostType(wrapper);				
	}

	void removeInitFromField(FieldDeclaration originalField) {		
		originalField.setInitOpt(new Opt<Expr>());
	}

	private void moveFieldInitToInitializer(FieldDeclaration initSource, FieldDeclaration initTarget) {		
		addToInitializer(fieldGen.createLayeredStateInit(initSource, initTarget));
		removeInitFromField(initSource);
	}

	private void addToInitializer(Stmt stmt) {
		getInitializerOf(baseField.hostType()).getBlock().addStmt(stmt);		
	}

//	public String getBaseFieldWrapperName() {
//		return Identifiers.wrappedMethodPrefix + getField().getID();
//	}
	
//	private MethodDecl generateDefaultWrapperFor(FieldDeclaration fieldDecl) {
//		// FieldDeclaration fieldDecl = getField();
//		return new MethodDecl(jcop.transformation.ASTTools.Generation.createPublicModifierFor(fieldDecl), (Access) (fieldDecl.getTypeAccess().fullCopy()), getWrapperFieldID(),
//				new List<ParameterDeclaration>(), new List<Access>(), createOptBlock(new ReturnStmt(new VarAccess(fieldDecl.name()))));
//	}

	private FieldDeclaration lookupBaseDeclaration() {
		TypeDecl enclType = openLayer.hostType().topLevelType();
		enclType.resetCache();
		return (FieldDeclaration) enclType.localFieldsMap().get(getField().getID());
		// for (BodyDecl bodyDecl : getBodyDeclListOfHostType()) {
		// if((bodyDecl instanceof FieldDeclaration) &&
		// ((FieldDeclaration) bodyDecl).getID().equals(getField().getID()))
		// //if (bodyDecl.getIDString().equals(getField().toString()))
		// return (FieldDeclaration) bodyDecl;
		// }
		// return null;
	}

	public void createAroundAdvice() {
		JCopAspect.getInstance().addWrapperForField(partialField, openLayer);
	}
}