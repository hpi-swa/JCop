package jcop.generation;

import static jcop.Globals.Modifiers.PUBLIC;
import static jcop.Globals.Modifiers.STATIC;
import static jcop.Globals.Modifiers.PRIVATE;
import static jcop.Globals.Types.INTERNAL_CONTEXT;
import jcop.Globals;
import jcop.Globals.ID;
import jcop.compiler.JCopTypes;
import jcop.compiler.JCopTypes.JCopAccess;
import AST.Access;
import AST.AndLogicalExpr;
import AST.AssignExpr;
import AST.AssignSimpleExpr;
import AST.Block;
import AST.BooleanLiteral;
import AST.ClassDecl;
import AST.ClassInstanceExpr;
import AST.ConstructorAccess;
import AST.ConstructorDecl;
import AST.ContextDecl;
import AST.DLALayerActivation;
import AST.EQExpr;
import AST.Expr;
import AST.ExprStmt;
import AST.FieldDeclaration;
import AST.IfStmt;
import AST.List;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.NullLiteral;
import AST.Opt;
import AST.ParameterDeclaration;
import AST.PrimitiveTypeAccess;
import AST.ReturnStmt;
import AST.Stmt;
import AST.StringLiteral;
import AST.SuperConstructorAccess;
import AST.TypeAccess;
import AST.VarAccess;

public class ContextGenerator extends Generator {
	private ContextDecl contextDecl;

	public ContextGenerator(ContextDecl contextDecl) {
		this.contextDecl = contextDecl;
	}
	
	public ClassDecl generateContextClass() {
		ClassDecl contextClass = generateClass(this.contextDecl);
		Stmt activations = generateLayerActivationStmts(true);	    
	    Stmt deactivations = generateLayerActivationStmts(false);
	    MethodDecl activeLayerAccessor = createInitLayersMethod(activations, Globals.ID.layersActivatedByContext);
	    MethodDecl deactiveLayerAccessor = createInitLayersMethod(deactivations,  Globals.ID.layersDeactivatedByContext);
		//MethodDecl layerInitMethod = createInitLayersMethod(activations);		
		MethodDecl nameAccess = generateNameAccess();
		contextClass.addMemberMethod(nameAccess);
		contextClass.addConstructor(generateDefaultConstructor());
		//contextClass.addConstructor(generateParameterizedConstructor());		
		contextClass.addMemberMethod(activeLayerAccessor);		
	    contextClass.addMemberMethod(deactiveLayerAccessor);
	    contextClass.addMemberField(new FieldDeclaration(genModifiers(PRIVATE, STATIC), genTypeAccess(),"singleton"));
	    contextClass.addMemberMethod(generateSingletonAccess());
		contextClass.resetCache();
		return contextClass;	
	}
	
		

	

	private ClassDecl generateClass(ContextDecl contextDecl) {		
		return new ClassDecl(
				contextDecl.getModifiersNoTransform().fullCopy(), 
				contextDecl.getID(),
				new Opt<Access>(JCopTypes.JCopAccess.get(INTERNAL_CONTEXT)),				      				      
				contextDecl.getImplementsListNoTransform().fullCopy(),					      
				contextDecl.getBodyDeclListNoTransform().fullCopy());
	}
	
	public  MethodDecl createIsActiveForMethod(Expr transformedPointcut) {		
		MethodDecl isActiveFor = new MethodDecl(
				genModifiers(PUBLIC),
				new PrimitiveTypeAccess("boolean"),
				ID.isActiveFor, 
				genList(new ParameterDeclaration(JCopAccess.getStringAccess(), ID.signature)),
				new List<Access>(),
				genOptBlock(new ReturnStmt(new AndLogicalExpr(new VarAccess(ID.isActive),transformedPointcut))));
		return isActiveFor;
	}
	
	private MethodDecl createInitLayersMethod(Stmt layerActivations, String name) {
		 MethodDecl initLayers = new MethodDecl(
			  genModifiers(PUBLIC), 
			  new TypeAccess("java.util", "List"), 
			  name, 
			  new List<ParameterDeclaration>(),
			  new List<Access>(),
			  genOptBlock(layerActivations));
		return initLayers;	
	}
	
	private Stmt generateLayerActivationStmts(boolean isActivation) {
		List<Expr> layerActivations = new List<Expr>();
		List<DLALayerActivation> activations = contextDecl.getLayerActivations();		
		for(DLALayerActivation activation : activations) {	
			if (activation.getActivation() == isActivation) {
				layerActivations = mergeLists(layerActivations , activation);
					//					.set(generateLayerActivationStmt(activation));
				//System.out.println(layerActivations);
			}
		}
//		
//		MethodAccess activationMethod =  new MethodAccess(methodName, activationParams);				
//		Expr activationMethodAccess = new ThisAccess().qualifiesAccess(activationMethod);		
//		return new ExprStmt(activationMethodAccess);
//		
//		String methodName = generateActivationMethodName(activation);
		MethodAccess activationMethod = new MethodAccess("createList", layerActivations);
	    Stmt activationMethodAccess = 
	      new ReturnStmt(activationMethod);
	    return activationMethodAccess;				
	}
	
	private List<Expr> mergeLists(List<Expr> layerActivations, DLALayerActivation activation) {
		List <Expr> activationParams = activation.getArgListNoTransform().fullCopy();
	    MethodAccess activationMethod = new MethodAccess("createList", activationParams);
		
		layerActivations.add(activationMethod);
		return layerActivations;
	}

	private List<Expr> generateLayerActivationStmt(DLALayerActivation activation) {	
		return  activation.getArgListNoTransform().fullCopy();		
	}
	
	
	
	private String generateActivationMethodName(DLALayerActivation activation) {
		return activation.getActivation() ?  ID.activationMethod : ID.deactivationMethod;
	}
	
	private MethodDecl generateNameAccess() {
		return new MethodDecl (
				genModifiers("public"),
				JCopAccess.getStringAccess(),
				ID.jcopNameMethod,
				new List<ParameterDeclaration>(),
				new List<Access>(),
				genOptBlock(new ReturnStmt(new StringLiteral(contextDecl.getFullQualifiedName())))
				);	
	}	
	
	private ConstructorDecl generateDefaultConstructor() {
		return new ConstructorDecl(
				genModifiers("public"),
				contextDecl.getID(),
				new List<ParameterDeclaration>(),				
				new List<Access>(),
				new Opt<Stmt>(),
				new Block());					
	}
	
//	private ConstructorDecl generateParameterizedConstructor() {
//		ConstructorDecl decl = generateDefaultConstructor();
//		decl.setConstructorInvocation(new ExprStmt(
//				new SuperConstructorAccess("super", 
//						genList(new VarAccess(Identifiers.isActive)))));
//		decl.addParameter(new ParameterDeclaration(new PrimitiveTypeAccess("boolean"), Identifiers.isActive));
//		return decl;		
//	}
//	
	
	private MethodDecl generateSingletonAccess() {
		return new MethodDecl (
				genModifiers(PUBLIC, STATIC),
				genTypeAccess(),
				"getSingleton",
				new List<ParameterDeclaration>(),
				new List<Access>(),
				genOptBlock(
						new IfStmt(
								new EQExpr(genSingletonAccess(), new NullLiteral("null")),
								createStmtBlock(
										new ExprStmt(
												new AssignSimpleExpr (
														genSingletonAccess(), 
														new ClassInstanceExpr(
																genTypeAccess(),
																new List())))),
										new ExprStmt(
												new AssignSimpleExpr(
														genSingletonAccess().qualifiesAccess(new VarAccess("isActive")), 
														new BooleanLiteral(true)))
						),						
						new ReturnStmt(genSingletonAccess()))
//						
//												
				);			
	}
	private VarAccess genSingletonAccess() {
		return new VarAccess("singleton");
	}
	
	private TypeAccess genTypeAccess() {
		return new TypeAccess(contextDecl.getID());
	}

	
	
}
