package jcop.generation.layermembers;

import jcop.Globals;
import jcop.Globals.ID;
import jcop.Globals.Msg;
import static jcop.Globals.Types.*;
import jcop.compiler.JCopTypes.JCopAccess;
import jcop.generation.RunTimeLoggingGenerator;
import AST.Access;
import AST.Annotation;
import AST.Block;
import AST.CatchClause;
import AST.ClassInstanceExpr;
import AST.Dot;
import AST.ElementValuePair;
import AST.Expr;
import AST.ExprStmt;
import AST.List;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.Modifiers;
import AST.Opt;
import AST.ReturnStmt;
import AST.Stmt;
import AST.StringLiteral;
import AST.SuperAccess;
import AST.ThisAccess;
import AST.ThrowStmt;
import AST.TryStmt;
import AST.TypeAccess;
import AST.TypeDecl;
import AST.VarAccess;
import AST.VariableDeclaration;

public class BaseMethodGenerator extends LayeredMethodGenerator {
	private RunTimeLoggingGenerator logGenerator;


	public BaseMethodGenerator(MethodDecl baseMethod) {
		super(null, baseMethod);		
		logGenerator = RunTimeLoggingGenerator.getInstance();
	}
	
	public BaseMethodGenerator(MethodDecl partialMethod, MethodDecl baseMethod) {
		super(partialMethod, baseMethod);
		logGenerator = RunTimeLoggingGenerator.getInstance();
	}

	public Block generateLayerActivationBlock() {		
		VariableDeclaration current = generateVarForCurrentCompostion(); //generateVarForOldCompostion();
		 //generateVarForOldCompostion();
		//VariableDeclaration current  = generateVarForCurrentCompostion();
//		ExprStmt activateContexts = activateContexts();		
		Block block = createStmtBlock(current, createTryBlock(createFirstLayerAccess()));
		 return logGenerator.genLayeredMethodBlock(block, baseMethod.getFullQualifiedName());
		//return block;
	}


	public MethodDecl generateBaseMethod(boolean requiresSuperCall) {		
		Stmt exception = generateBaseMethodBodyStatement(requiresSuperCall);		
		MethodDecl defaultMethod = 
			new MethodDecl(
				partialMethod.getModifiers().fullCopy(), 
				(Access) (partialMethod.getTypeAccess().fullCopy()),
				//baseMethod.type().createBoundAccess(),
				partialMethod.getID(), 
				partialMethod.getParameters().fullCopy(), 
				partialMethod.getExceptionList().fullCopy(), 
				genOptBlock(exception));
		return defaultMethod;		
	}

	private Stmt generateBaseMethodBodyStatement(boolean requiresSuperCall) {
		if (requiresSuperCall)
			return maybeGenerateReturnStmt(baseMethod, generateSuperCall());
		else
			return generateThrowsException(new TypeAccess(Globals.jcopPackage, InvalidMethodAccessException));
		
	}

	

	private Expr generateSuperCall() {
		return new SuperAccess().qualifiesAccess(
				createMethodAccess(baseMethod.getID(), generateArgs(partialMethod.getParameters())));
	}


	public MethodDecl generateWrapper() {
		Block loggedBlock = logGenerator.genBaseMethodBlock(baseMethod.getBlock(), baseMethod.getFullQualifiedName());
		Modifiers modif = createPublicModifierFor(baseMethod);
		modif.addModifier(genAnnotation(BASE_METHOD_ANNOTATION));		
		MethodDecl newMethod = 
			new MethodDecl(
					modif,			
					// .createBoundAccess won't work due to side effects
					(Access)baseMethod.getTypeAccess().fullCopy(),
					//baseMethod.type().createBoundAccess(),
					genWrapperIdentifier(),					
					baseMethod.getParameters().fullCopy(), 
					baseMethod.getExceptionList().fullCopy(), 
					new Opt<Block>(loggedBlock));
		//	System.err.println(baseMethod.getBlock());
		return newMethod;
	}
	
	private Stmt createTryBlock(Expr toBeWrapped) {		
		Block tryBlock = new Block();		
		tryBlock.addStmt(baseMethod.isVoid() 
				? new ExprStmt(toBeWrapped)
				: new ReturnStmt(toBeWrapped));
		TryStmt tryStmt = new TryStmt(
			tryBlock, 
			new List<CatchClause>(),
			createFinallyBlockForForwardingMethod());
		return tryStmt;
	}
	
	private Expr createFirstLayerAccess() {
		String delegationMethodName = generateDelegationMethodName(partialMethod);
		List<Expr> args = createArgsForCallToLayeredMethod();
		Dot concreteLayerWithParams =			
			new VarAccess(ID.composition).qualifiesAccess(
					createMethodAccess(ID.firstLayer).qualifiesAccess(
							createMethodAccess("get").qualifiesAccess(
									createMethodAccess(delegationMethodName, args))));	
		return concreteLayerWithParams;
	}

	private Opt<Block> createFinallyBlockForForwardingMethod() {		
		Access setComposition = 
			JCopAccess.get(JCOP).qualifiesAccess(					
					createMethodAccess(ID.setComposition, new VarAccess(ID.composition)));
		return genOptBlock(new ExprStmt(setComposition));		
	}

   	
//   	private VariableDeclaration generateVarForOldCompostion() {					
//		Expr lhs = createCurrentCompositionAccess().qualifiesAccess(createAddLayerAccess());		
//		return new VariableDeclaration(	JCopAccess.get(COMPOSITION), ID.oldComposition, lhs);						
//	}
   	
//	private Access createAddLayerAccess() {
//		return createMethodAccess(ID.addLayer, createImplicitActivationAccess());
//	}	
 
//	private Expr createImplicitActivationAccess() {
//		String baseMethodFQN =  createFullQualifiedSignature(baseMethod);
//		return JCopAccess.get(COMPOSITION).qualifiesAccess(
//				createMethodAccess(
//						ID.implicitlyActivatedLayers, 
//						new StringLiteral(baseMethodFQN), 
//						createTargetArgsForCallToLayeredMethod(baseMethod)));
//	}

	private VariableDeclaration generateVarForCurrentCompostion() {	
		return new VariableDeclaration(
				JCopAccess.get(COMPOSITION),
				ID.composition,
				createCurrentCompositionAccess());	
	}

   	private List<Expr> createArgsForCallToLayeredMethod() {   		
		List<Expr> args = generateArgs(baseMethod.getParameterList());				
		args.insertChild(createTargetArgsForCallToLayeredMethod(baseMethod), 0);
		args.insertChild(new VarAccess(ID.composition).qualifiesAccess(createMethodAccess(ID.firstLayer)),1);
		args.insertChild(new VarAccess(ID.composition), 2);
		return args;
	}  		
	
	private Expr createTargetArgsForCallToLayeredMethod(MethodDecl method) {		
		if (method.isStatic()) 
			return new ClassInstanceExpr(createHostTypeAccessFor(method), new List<Expr>());
		else
			return new ThisAccess();
	}

	private Access createHostTypeAccessFor(MethodDecl method) {	
		TypeDecl type = method.hostType();
		return new TypeAccess(type.packageName(),type.getID());	
	}

	public String genWrapperIdentifier() {
		return genWrapperIdentifierPrefix() + baseMethod.name();		
	}
	public String genWrapperSignature() {
		return genWrapperIdentifierPrefix() + baseMethod.signature();	
	}
	private String genWrapperIdentifierPrefix() {
		return ID.wrappedMethodPrefix + baseMethod.hostType().getID() + "$$$";		
	}
	
	
	

}
