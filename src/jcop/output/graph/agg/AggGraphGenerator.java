package jcop.output.graph.agg;

import jcop.Globals.CompilerOps;
import jcop.output.graph.GraphGenerator;
import jcop.output.graph.INode;
import AST.Program;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.xt_basis.GraGra;
import agg.xt_basis.Node;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;

public class AggGraphGenerator extends GraphGenerator { 
	private GraGra graphGrammar;	
	private static AggGraphGenerator instance;	

	public static AggGraphGenerator getInstance() { 
		if (instance == null)
			instance = new AggGraphGenerator();
		return instance;
	}
	
	private AggGraphGenerator() {				
		graphGrammar = AggUtils.getInstance().createGraGraGraph();		
	}
		
	@Override
	public void addAttribute(INode node, String key, Object value) {
		Node n =  ((AggNode) node).getAggNode();	
		ValueTuple vt = (ValueTuple) n.getAttribute();				
		ValueMember vm = (ValueMember) vt.getMemberAt(key);
		assert vm != null : String.format("attribute '%s' not found%n in node %s%n", key, n.getType().getName());
		vm.setExprAsObject(value);
		vm.checkValidity();
	}
	
	@Override
	public INode createNode(String typeName) {
		AggNode node =  null;
		try {	
			node = new AggNode(graphGrammar.getGraph().createNode(getNodeType(typeName)));			
		}
		catch (TypeException e) {
			System.out.println("cannot find AGG node type for " + typeName);
			e.printStackTrace();			
		}
		assert node != null : "[agg] cannot create node for type name"  + typeName;
		return node;
	}
	
	@Override
	public void createEdge(INode src, String arc, INode tar) {
		Node srcNode = ((AggNode) src).getAggNode();
		Node tarNode = ((AggNode) tar).getAggNode();
		try {
			if (tar != null)
				graphGrammar.getGraph().createArc(getNodeType(arc), srcNode, tarNode);
		}
		catch (TypeException e) {			
			System.out.printf("[agg] cannot create edge %s  -- %s --> %s%n", src.getName(), arc, tar.getName());
			e.printStackTrace();
		}		
		catch (NullPointerException e) {
			System.out.printf("[agg] cannot create edge %s  -- %s --> %s%n", src.getName(), arc, tar.getName());
		}
	}
	
	private Type getNodeType(String typeName) {
		Type nodeType = graphGrammar.getTypeGraph().getTypeSet().getTypeByName(typeName);
		assert nodeType != null :"[agg] cannot finde node type "  + typeName;
		return nodeType;
	}

	@Override
	public void save() {
		String file = Program.getValueForOption(CompilerOps.agg);
		AggUtils.getInstance().save(file);		
	}
	
	

//	public INode visit(ASTNode<?> decl) {
//		return null;
//	}
//
//	public void visit(TypeDecl decl) {
//		System.out.println("[agg] add type");
//	}
//
//	public INode visit(ClassDecl decl) {	
//		return createClass(decl, INode.Class);		
//	}
//	
//	public INode visit(LayerDecl decl) {
//		return createClass(decl, INode.Layer);		
//	}
//	
//	public INode visit(ContextDecl decl) {
//		INode node = createClass(decl, INode.Context);
//		createList(decl.getContextConstraintList(), node, IEdge.EdgeContains); 
//		return node; 
//	}	
//		
//	public INode visit(Program decl) {
//		AggNode node = createNode(INode.Program);
//		return node;
//	}
//	
//	public INode visit(MethodAccess access) {
//		AggNode node =  createNode(INode.MethodCall);
//		Node method = createIdentifier(access.getID());
//		createEdge(node, IEdge.NextMethod, method);
//		createList(access.getArgList(), method, IEdge.NextParam);
//		return node;
//	}	
//
//	public INode visit(MemberClassDecl memberClass) {
//		return memberClass.getClassDecl().createAggNode();
//	}
//	
//	public INode visit(ConstructorDecl decl) {		
//		AggNode node =  createNode(INode.Constructor);
//		createStmtList(decl.getBlock().getStmtList(), node);
//		createParamList(decl.getParameterList(), node);
//		return node;
//	}
//	
//	public INode visit(MethodDecl decl) {	
//		return createMethod(decl, INode.MethodDecl);		
//	}
//	
//	public INode visit(VarAccess decl) {	
//		Node node  =  decl.isFieldAccess() 
//			? createFieldAccess(decl)
//			:  createIdentifier(decl.getID());
//		if (decl.hasNextAccess()) {
//			Node next = decl.nextAccess().createAggNode();
//			createEdge(next, IEdge.Receiver, node );
//			return next;
//		}			
//		return node; 
//	}	
//
//
//	public INode visit(ContextConstraint decl) {	
//		Node constraint = createNode(INode.DeclarativeLayerActivation);
//		return constraint;		
//	}	
//	 
//	public INode visit(ProceedExpr decl) {
//		AggNode node =  createNode(INode.ProceedExpr);
//		createList(decl.getArgList(),node, IEdge.NextArg);
//		return node;
//	}
//	
//	public INode visit(PartialMethodDecl decl) {		
//		return createMethod(decl, INode.PartialMethod);
//	}
//	
//	public INode visit(LayerActivation activation) {
//		AggNode node =  createNode(activation.getActivation() ? "With" : "Without");
//		createList(activation.getArgList(), node, IEdge.NextArg);
//		
//		
//		//we assume that with statement bodies only contain one statement
//		AST.List<Stmt> stmts = activation.getBlock().getStmtList();
//		if (stmts.getNumChild() > 0) {
//			Node body = stmts.getChild(0).createAggNode();
//			createEdge(node, IEdge.WithBody, body);
//		}		
//		return node;
//	}
//	
//	public INode visit(ClassInstanceExpr expr) {
//		Node init = createNode(INode.Instantiation);
//		Node access = createIdentifier(expr.getAccess().type().name());
//		createList(expr.getArgs(), init, IEdge.NextParam);
//		createEdge(init, IEdge.EdgeContains, access);
//		return init;
//	}
//	
//	public INode visit(FieldDeclaration decl) {			
//		AggNode node =  createNode(INode.Field);
//		addAttribute(node, IAttr.Name, decl.getID());
//		return node;
//	}
//	
//	
////	public INode visit(ClassInLayer layer) {
////		AggNode node =  createNode("Layer");
////		layer.super
////		return node;
////	}
//	
//	public INode visit(NullLiteral literal) {
//		return createNode(INode.Null);
//	}
//	
//	public INode visit(BooleanLiteral literal) {
//		return createLiteral(literal, INode.Boolean);
//	}
//	
//	public INode visit(StringLiteral literal) {
//		return createLiteral(literal, INode.String);
//	}
//	
//	public INode visit(IntegerLiteral literal) {		
//		return createLiteral(literal, INode.Integer);
//	}
//	
//	public INode visit(Expr expr) {	
//		return createNode(INode.Expression);		
//	}
//	
//	public INode visit(Access access) {
//		//System.out.println("reference: " + access.getClass() + "\n" + access.toString());
//		return createNode(INode.Reference);
//	}			
//		
//	public INode visit(TypeAccess access) {
//		StringBuffer label = new StringBuffer(access.getID());
//		if (access.getPackage().length() > 0)
//			label.insert(0, access.getPackage()).insert(1, '.');
//		return createIdentifier(label.toString());
//	}
//	
//	public INode visit(ThisAccess access) {		
//		return createNode(INode.This);
//	}
//	
//	public INode visit(Dot access) {		
//		return  access.rightSide().createAggNode();		
//	}
//	
//	public INode visit(Binary binary) {		
//		Node binaryOp = createNode(INode.BinaryOperation);
//		Node lhs = binary.getLeftOperand().createAggNode();
//		Node rhs = binary.getRightOperand().createAggNode();
//		addAttribute(binaryOp, IAttr.Operator, binary.printOp().trim());
//		createEdge(binaryOp, IEdge.Lhs, lhs);
//		createEdge(binaryOp, IEdge.Rhs, rhs);
//		return binaryOp;
//	}
//	
//	public INode visit(VariableDeclaration decl) {
//		Node lhs = createIdentifier(decl.getID());
//		Node rhs = decl.getInit().createAggNode();
//		return createAssignment(lhs, rhs);
//	}
//	
//	public INode visit(AssignExpr assign) {
//		Node lhs = assign.getSource().createAggNode();
//		Node rhs = assign.getDest().createAggNode();
//		return createAssignment(lhs, rhs);
//	}	
//		
//	public INode visit(Stmt stmt) {			
//		System.out.println("stmt: " + stmt.getClass());
//		return createNode(INode.Statement);		
//	}
//
//	public INode visit(IfStmt stmt) {	
//		AggNode node =   createNode(INode.If);
//		Node condition = stmt.getCondition().createAggNode();		
//		createEdge(node, IEdge.Condition, condition);
//		Node thenBlock = stmt.getThen().createAggNode();  
//		createEdge(node, IEdge.IfBody, thenBlock);
//		if (stmt.hasElse()) {
//			Node elseBlock = stmt.getElse().createAggNode();
//			createEdge(node, IEdge.ElseBody, elseBlock);
//		}
//		return node;
//	}
//
//	public INode visit(ReturnStmt stmt) {				
//		Node returnNode = createNode(INode.Return);
//		Node result = stmt.getResult().createAggNode();
//		createEdge(returnNode, IEdge.EdgeContains, result);
//		return returnNode;
//	}
//
//	public INode visit(ExprStmt stmt) {				
//		return stmt.getExpr().createAggNode();		
//	}
//
//	public INode visit(ParameterDeclaration param) {				
//		AggNode node =  createNode(INode.Parameter);
//		addAttribute(node, IAttr.Name, param.getID());
//		return node;
//	}
//
//	public INode visit(CompilationUnit unit) {	
//		// for the moment we assume only one type decl per compilation unit
//		if (!isJcopPackage(unit)) {
//			assert unit.getTypeDeclListNoTransform() != null : "unit" + unit.packageName() + " is empty";
//			AggNode node =  unit.getTypeDecl(0).createAggNode();
//			createEdge(program, IEdge.EdgeContains, node);
//			return node;
//		}
//		return null;
//	}
//	

	

//	private INode createAssignment(Node lhs, Node rhs) {
//		INode assignment = createNode(INode.Assignment);		
//		createEdge(assignment, IEdge.Lhs, lhs);
//		createEdge(assignment, IEdge.Rhs, rhs);
//		return assignment;		
//	}
//
//	private INode createLiteral(Literal literall, String type) {
//		AggNode node =  createNode(type);
//		addAttribute(node, IAttr.Value, literall.getLITERAL());		
//		return node;	
//	}
//
//	private AggNode createClass(ClassDecl decl, String nodeName) {
//		AggNode node =  createNode(nodeName);
//		addAttribute(node, IAttr.Name, decl.getID());
//		
//		for (BodyDecl member : decl.getBodyDecls()) {
//			Node n = member.createAggNode();			
//			createEdge(node, IEdge.EdgeContains, n);
//		}		
//		return node;
//	}
//
//	private INode createMethod(MethodDecl decl, String nodeName) {		
//		AggNode node =  createNode(nodeName);
//		addAttribute(node, IAttr.Name, decl.getID());		
//		if (decl.hasBlock())
//			createStmtList(decl.getBlock().getStmtList(), node);					
//		createParamList(decl.getParameterList(), node);		
//		return node;
//	}
//
//	private void createStmtList(AST.List<Stmt> stmts, Node node) {
//		createList(stmts, node,  IEdge.NextStmt);		
//	}
//
//	private void createParamList(AST.List<ParameterDeclaration> params, Node node) {
//		createList(params, node, IEdge.NextParam);
//	}
//
//	private void createList(AST.List<? extends ASTNode> elements, Node previous, String label) {		
//		for(ASTNode element : elements) {
//			Node next = element.createAggNode();		
//			createEdge(previous, label, next);
//			previous = next;
//		}		 
//	}
//
//	private INode createFieldAccess(VarAccess decl) {
//		AggNode node =  createNode(INode.FieldReference);
//		Node lhs = decl.hasPrevExpr()
//			? decl.prevExpr().createAggNode()
//			: createNode(INode.This);
//		Node rhs = createIdentifier(decl.getID());
//		createEdge(node, IEdge.Lhs, lhs);
//		createEdge(node, IEdge.Rhs, rhs);
//		return node;
//	}
//
//	private INode createIdentifier(String id) {
//		AggNode node =  createNode(INode.Identifier);
//		addAttribute(node, IAttr.Name, id);
//		return node;
//	}
	

//
//	private Type getNodeType(String typeName) {		
//		Type nodeType = graphGrammar.getTypeGraph().getTypeSet().getTypeByName(typeName);
//		assert nodeType != null :"[agg] cannot finde node type "  + typeName;
//		return nodeType;
//	}
//	
//	
//	public INode createNode(String typeName) {
//		AggNode node =  null;
//		try {
//			node = new AggNode(graphGrammar.getGraph().createNode(getNodeType(typeName)));			
//		}
//		catch (TypeException e) {
//			System.out.println("cannot find AGG node type for " + typeName);
//			e.printStackTrace();			
//		}
//		assert node != null : "[agg] cannot create node for type name"  + typeName;
//		return node;
//	}
//
//	public void createEdge(Node src, String arc, Node tar) {
//		try {
//			if (tar != null)
//				graphGrammar.getGraph().createArc(getNodeType(arc), src, tar);
//		}
//		catch (TypeException e) {			
//			System.out.printf("[agg] cannot create edge %s  -- %s --> %s%n", src.getType().getName(), arc, tar.getType().getName());
//			e.printStackTrace();
//		}		
//		catch (NullPointerException e) {
//			System.out.printf("[agg] cannot create edge %s  -- %s --> %s%n", src.getType().getName(), arc, tar.getType().getName());
//		}
//	}
}
	
