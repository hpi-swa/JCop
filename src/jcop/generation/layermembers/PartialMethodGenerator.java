package jcop.generation.layermembers;

import static jcop.Globals.Modifiers.AFTER;
import static jcop.Globals.Modifiers.BEFORE;
import static jcop.Globals.Modifiers.AFTER_ANNOT;
import static jcop.Globals.Modifiers.BEFORE_ANNOT;
import static jcop.Globals.Types.*;

import static jcop.Globals.Types.PARTIAL_METHOD;
import static jcop.Globals.Types.PARTIAL_METHOD_ANNOTATION;
import jcop.Globals;
import jcop.Globals.ID;
import jcop.compiler.JCopTypes.JCopAccess;
import jcop.generation.RunTimeLoggingGenerator;
import jcop.generation.layers.LayerClassGenerator;
import AST.Access;
import AST.ArrayCreationExpr;
import AST.ArrayInit;
import AST.ArrayTypeAccess;
import AST.Block;
import AST.CatchClause;
import AST.ClassInstanceExpr;
import AST.Expr;
import AST.ExprStmt;
import AST.LayerDeclaration;
import AST.List;
import AST.MethodDecl;
import AST.Modifiers;
import AST.Opt;
import AST.ParameterDeclaration;
import AST.ProceedExpr;
import AST.ReturnStmt;
import AST.Stmt;
import AST.StringLiteral;
import AST.TryStmt;
import AST.TypeAccess;
import AST.Typed;
import AST.VarAccess;
import AST.VariableDeclaration;

public class PartialMethodGenerator extends LayeredMethodGenerator {		
	public LayerClassGenerator layerGenerator;
	//private LayerDecl layer;	
	 
	public PartialMethodGenerator(LayerDeclaration layer, MethodDecl partialMethod) {
		super(partialMethod);
		//this.layer = layer;
	}
			
	public  MethodDecl generatePartialMethod() {		
		MethodDecl partialMethod = this.partialMethod;
		Modifiers modif = createPublicModifierFor(partialMethod);	
		modif.addModifier(genAnnotation(PARTIAL_METHOD_ANNOTATION));
//		partialMethod.setModifiers(modif);
//		partialMethod.setParameterList(genLayerParams(partialMethod));
//		partialMethod.setID("ffff");
//		partialMethod.hostType().resetCache();
//		partialMethod.setBlockOpt(genPartialMethodBlock());
//		((PartialMethodDecl)partialMethod).setNamePattern(new SimpleNamePattern(partialMethod.getID()));		
		return new MethodDecl(
			modif,		
			(Access)partialMethod.getTypeAccess().fullCopy(),
			partialMethod.getID(),
			genLayerParams(partialMethod),
			partialMethod.getExceptionList().fullCopy(),			
			genPartialMethodBlock()
		);

	}

	
	/**
	 *  try {
	 *    BLOCK
	 *   }
	 *   finally {
	 *     PROCEED
	 *   }
	 */
	private Block generateBlockForBeforeMethod() {
		Block block = partialMethod.getBlock().fullCopy();		
		TryStmt tryStmt = new TryStmt(
				block, 
				new List<CatchClause>(),
				genOptBlock(genProceedExprStmt()));
		return createStmtBlock(tryStmt);
	}

	private Stmt maybeSurroundWithReturn(Expr expr) {
		if (!partialMethod.isVoid())
			return new ReturnStmt(expr);
		return new ExprStmt(expr);
	}

	/**
	 *  try {
	 *    X = PROCEED
	 *   }
	 *   finally {
	 *     BLOCK
	 *     return X
	 *   }
	 */
	private Block generateBlockForAfterMethod() {		
		Block block = partialMethod.getBlock().fullCopy();
		List<Stmt> stmts = block.getStmtList();		

		if (!partialMethod.isVoid()) {
			String var = "return$type";
			Stmt s1 = genProceedVar(var);
			Stmt s2 = new ReturnStmt(new VarAccess(var));
			stmts.insertChild(s1, 0);
			stmts.addChild(s2);
		} 
		else 
			stmts.insertChild(genProceedExprStmt(), 0);		
		return block;
	}
	
	private Stmt genProceedVar(String var) {
		Access type = partialMethod.getTypeAccess();
		return new VariableDeclaration(type, var, genProceedExpr());		
	}

	private Stmt genProceedExprStmt() {
		return maybeSurroundWithReturn(genProceedExpr());
	}
	
	private Expr genProceedExpr() {
		List<Expr> args = generateArgs(partialMethod.getParameterList());
		return new ProceedExpr(args);
	}

	public Opt<Block> genPartialMethodBlock() {
		RunTimeLoggingGenerator log = RunTimeLoggingGenerator.getInstance();
		Block block = log.createBlockWithLoggingMessage(partialMethod, getLayer().getID());		
		return new Opt<Block>(genBlockWithBeforeAfterSemantics(partialMethod, block));	 
	}
	 

	private List<ParameterDeclaration> genLayerParams(MethodDecl decl) {
		TypeAccess concreteLayerAccess = JCopAccess.getLayerType(getLayer());
		List<ParameterDeclaration> params =  decl.getParameterList().fullCopy();
		params.insertChild(new ParameterDeclaration(concreteLayerAccess, ID.layerParameterName),0);		
		params.insertChild(new ParameterDeclaration(JCopAccess.get(LAYER_PROXY), ID.layerProxyParameterName),1);
		params.insertChild(new ParameterDeclaration(JCopAccess.get(COMPOSITION), ID.composition),2);
		return params;
	}
	
	private  Block genBlockWithBeforeAfterSemantics(MethodDecl partialMethod, Block block) {		
		Modifiers m = partialMethod.getModifiers();		 
		if (containsBeforeModifier(m))
			block = generateBlockForBeforeMethod();
		else if (containsAfterModifier(m))
			block = generateBlockForAfterMethod();
		
		
		return block;
	}	
	
	private boolean containsAfterModifier(Modifiers m) {				
		return m.contains(AFTER)  || m.contains(AFTER_ANNOT);		
	}
	
	private boolean containsBeforeModifier(Modifiers m) {
		return m.contains(BEFORE) || m.contains(BEFORE_ANNOT);		
	}

	public Stmt genPartialMethodMetaClassInstantiation() {		
		Expr metaClass =  genMetaClassInit();
		return new ExprStmt(
				new VarAccess(Globals.ID.partialMethodSignatures).qualifiesAccess(
						createMethodAccess("put",
								new StringLiteral(genFullSignature(baseMethod)), 
								metaClass)));		
	}
	
	private String genFullSignature(MethodDecl decl) {
		StringBuffer sig = new StringBuffer();				
		sig.append(decl.getTypeAccess().type().getFullQualifiedName());
		sig.append(' ');
		sig.append(decl.getFullQualifiedName());		
		return sig.toString();
	}
	
	private Expr genMetaClassInit() {
		String methodName = generateDelegationMethodName(baseMethod);
		return new ClassInstanceExpr(
				JCopAccess.get(PARTIAL_METHOD),
				createMetaClassInstantiationArgs(baseMethod, baseMethod.getID(), baseMethod.getModifiers())					
					.add(createStringArray(getParamTypes(baseMethod)))
					.add(createStringArray(createExceptionTypeStrings(baseMethod)))
					.add(new StringLiteral(methodName)));
	}

	private List<Expr> getParamTypes(MethodDecl method) {
		List<ParameterDeclaration> params = method.getParameters();
		return createTypeStrings(params);		
	}

//	private List<Expr> createParamTypeStrings(List<ParameterDeclaration> types) {		
//		List<Expr> literals = new List<Expr>();
//		for (int i = 0; i < types.getNumChild(); i++) 
//			literals.add(new StringLiteral(types.getChild(i).type().getFullQualifiedName()));		
//		return literals;
//	}
	
	private List<Expr> createTypeStrings(List<? extends Typed> types) {		
		List<Expr> literals = new List<Expr>();
		for (int i = 0; i < types.getNumChild(); i++) 
			literals.add(new StringLiteral(types.getChild(i).type().getFullQualifiedName()));		
		return literals;
	}
	
	
	private List<Expr> createExceptionTypeStrings(MethodDecl method) {
		List<Access> exceptions = method.getExceptions();
		return createTypeStrings(exceptions);		
	}
	
	private Expr createStringArray(List<Expr> initValues) {
		return createArray(JCopAccess.getStringAccess(), initValues);		
	}
	
	private Expr createArray(TypeAccess type, List<Expr> initValues) {
		return new ArrayCreationExpr(
				createArrayTypeAccess(type),				
				new Opt<ArrayInit>(new ArrayInit(initValues)));
	}

	private Access createArrayTypeAccess(TypeAccess type) {
		return new ArrayTypeAccess(type);		
	}

	public String genDelegationMethodName(MethodDecl method) {
		return generateDelegationMethodName(method);
	}	 
}