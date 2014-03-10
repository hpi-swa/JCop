package jcop.output.graph;

import java.util.ArrayList;
import java.util.List;

import jcop.Globals;
import AST.ASTNode;
import AST.Access;
import AST.AssignExpr;
import AST.Binary;
import AST.Block;
import AST.BodyDecl;
import AST.BooleanLiteral;
import AST.ClassDecl;
import AST.ClassInstanceExpr;
import AST.CompilationUnit;
import AST.ConstructorDecl;
import AST.ContextConstraint;
import AST.ContextDecl;
import AST.Dot;
import AST.Expr;
import AST.ExprStmt;
import AST.FieldDeclaration;
import AST.IfStmt;
import AST.InstanceInitializer;
import AST.IntegerLiteral;
import AST.LayerActivation;
import AST.LayerDecl;
import AST.Literal;
import AST.MemberClassDecl;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.NullLiteral;
import AST.OpenLayerDecl;
import AST.ParameterDeclaration;
import AST.PartialMethodDecl;
import AST.ProceedExpr;
import AST.Program;
import AST.ReturnStmt;
import AST.StaticInitializer;
import AST.Stmt;
import AST.StringLiteral;
import AST.ThisAccess;
import AST.TypeAccess;
import AST.TypeDecl;
import AST.VarAccess;
import AST.VariableDeclaration;

public abstract class GraphGenerator {

	private INode programNode;
	private List<LayerDecl> layerDecls = new ArrayList<LayerDecl>();

	public abstract void addAttribute(INode node, String key, Object value);

	public abstract INode createNode(String typeName);

	public abstract void createEdge(INode src, String arc, INode tar);

	public INode visit(ASTNode<?> decl) {
		return null;
	}

	public void visit(TypeDecl decl) {
		System.out.println("[agg] add type");
	}

	public INode visit(ClassDecl decl) {
		return createClass(decl, INode.Class);
	}

	public INode visit(LayerDecl decl) {		
		return createClass(decl, INode.Layer);
	}
	
	public INode visit(OpenLayerDecl decl) {		
		return createClass(decl.hostLayer(), INode.Layer);
	}

	public INode visit(StaticInitializer init) {		
		return createInitializer(init.getBlock(), "<static_init>");
	}

	public INode visit(InstanceInitializer init) {
		return createInitializer(init.getBlock(), "<init>");
	}
	
	
	

	public INode visit(ContextDecl decl) {
		INode node = createClass(decl, INode.Context);
		createList(decl.getContextConstraintList(), node, IEdge.EdgeContains);
		return node;
	}

	public INode visit(Program decl) {
		return createNode(INode.Program);
	}

	public INode visit(MethodAccess access) {
		INode node = createNode(INode.MethodCall);
		INode method = createIdentifier(access.getID());
		createEdge(node, IEdge.NextMethod, method);
		createList(access.getArgList(), method, IEdge.NextParam);
		return node;
	}

	public INode visit(MemberClassDecl memberClass) {
		return memberClass.getClassDecl().createGraphNode();
	}

	public INode visit(ConstructorDecl decl) {
		INode node = createNode(INode.Constructor);
		createStmtList(decl.getBlock().getStmtList(), node);
		createParamList(decl.getParameterList(), node);
		return node;
	}

	public INode visit(MethodDecl decl) {
		if (decl.isPartialMethod())
			return createMethod(decl, INode.PartialMethod);
		else
			return createMethod(decl, INode.MethodDecl);
	}

	public INode visit(VarAccess decl) {
		INode node = decl.isFieldAccess() ? createFieldAccess(decl) : createIdentifier(decl.getID());
		if (decl.hasNextAccess()) {
			INode next = decl.nextAccess().createGraphNode();
			createEdge(next, IEdge.Receiver, node);
			return next;
		}
		return node;
	}

	public INode visit(ContextConstraint decl) {
		return createNode(INode.DeclarativeLayerActivation);
	}

	public INode visit(ProceedExpr decl) {
		INode node = createNode(INode.ProceedExpr);
		createList(decl.getArgList(), node, IEdge.NextArg);
		return node;
	}

	public INode visit(PartialMethodDecl decl) {
		return createMethod(decl, INode.PartialMethod);
	}

	public INode visit(LayerActivation activation) {
		INode node = createNode(activation.getActivation() ? "With" : "Without");
		createList(activation.getArgList(), node, IEdge.NextArg);

		// we assume that with statement bodies only contain one statement
		AST.List<Stmt> stmts = activation.getBlock().getStmtList();
		if (stmts.getNumChild() > 0) {
			INode body = stmts.getChild(0).createGraphNode();
			createEdge(node, IEdge.WithBody, body);
		}
		return node;
	}

	public INode visit(ClassInstanceExpr expr) {
		INode init = createNode(INode.Instantiation);
		INode access = createIdentifier(expr.getAccess().type().name());
		createList(expr.getArgs(), init, IEdge.NextParam);
		createEdge(init, IEdge.EdgeContains, access);
		return init;
	}

	public INode visit(FieldDeclaration decl) {
		INode node = createNode(INode.Field);
		addAttribute(node, IAttr.Name, decl.getID());
		return node;
	}

	// public INode visit(ClassInLayer layer) {
	// INode node = createNode("Layer");
	// layer.super
	// return node;
	// }

	public INode visit(NullLiteral literal) {
		return createNode(INode.Null);
	}

	public INode visit(BooleanLiteral literal) {
		return createLiteral(literal, INode.Boolean);
	}

	public INode visit(StringLiteral literal) {
		return createLiteral(literal, INode.String);
	}

	public INode visit(IntegerLiteral literal) {
		return createLiteral(literal, INode.Integer);
	}

	public INode visit(Expr expr) {
		return createNode(INode.Expression);
	}

	public INode visit(Access access) {
		// System.out.println("reference: " + access.getClass() + "\n" +
		// access.toString());
		return createNode(INode.Reference);
	}

	public INode visit(TypeAccess access) {
		StringBuffer label = new StringBuffer(access.getID());
		if (access.getPackage().length() > 0)
			label.insert(0, access.getPackage()).insert(1, '.');
		return createIdentifier(label.toString());
	}

	public INode visit(ThisAccess access) {
		return createNode(INode.This);
	}

	public INode visit(Dot access) {
		return access.rightSide().createGraphNode();
	}

	public INode visit(Binary binary) {
		INode binaryOp = createNode(INode.BinaryOperation);
		INode lhs = binary.getLeftOperand().createGraphNode();
		INode rhs = binary.getRightOperand().createGraphNode();
		addAttribute(binaryOp, IAttr.Operator, binary.printOp().trim());
		createEdge(binaryOp, IEdge.Lhs, lhs);
		createEdge(binaryOp, IEdge.Rhs, rhs);
		return binaryOp;
	}

	public INode visit(VariableDeclaration decl) {
		INode lhs = createIdentifier(decl.getID());
		INode rhs = decl.getInit().createGraphNode();
		return createAssignment(lhs, rhs);
	}

	public INode visit(AssignExpr assign) {
		INode lhs = assign.getSource().createGraphNode();
		INode rhs = assign.getDest().createGraphNode();
		return createAssignment(lhs, rhs);
	}

	public INode visit(Stmt stmt) {		
		return createNode(INode.Statement);
	}
	
	


	public INode visit(IfStmt stmt) {
		INode node = createNode(INode.If);
		INode condition = stmt.getCondition().createGraphNode();
		createEdge(node, IEdge.Condition, condition);
		INode thenBlock = stmt.getThen().createGraphNode();
		createEdge(node, IEdge.IfBody, thenBlock);
		if (stmt.hasElse()) {
			INode elseBlock = stmt.getElse().createGraphNode();
			createEdge(node, IEdge.ElseBody, elseBlock);
		}
		return node;
	}

	public INode visit(ReturnStmt stmt) {
		INode returnNode = createNode(INode.Return);
		INode result = stmt.getResult().createGraphNode();
		createEdge(returnNode, IEdge.EdgeContains, result);
		return returnNode;
	}

	public INode visit(ExprStmt stmt) {
		return stmt.getExpr().createGraphNode();
	}

	public INode visit(ParameterDeclaration param) {
		INode node = createNode(INode.Parameter);
		addAttribute(node, IAttr.Name, param.getID());
		return node;
	}

	public INode visit(CompilationUnit unit) {
		// for the moment we assume only one type decl per compilation unit
		if (!isJcopPackage(unit)) {
			assert unit.getTypeDeclListNoTransform() != null : "unit" + unit.packageName() + " is empty";
			INode program = getProgramNode();
			INode node = unit.getTypeDecl(0).createGraphNode();
			createEdge(program, IEdge.EdgeContains, node);
			return node;
		}
		return null;
	}

	private INode getProgramNode() {
		if (programNode == null)
				programNode = createNode(INode.Program);
		return programNode;		
	}

	private boolean isJcopPackage(CompilationUnit unit) {
		return unit.packageName().equals(Globals.jcopPackage);
	}

	private INode createAssignment(INode lhs, INode rhs) {
		INode assignment = createNode(INode.Assignment);
		createEdge(assignment, IEdge.Lhs, lhs);
		createEdge(assignment, IEdge.Rhs, rhs);
		return assignment;
	}

	private INode createLiteral(Literal literall, String type) {
		INode node = createNode(type);
		addAttribute(node, IAttr.Value, literall.getLITERAL());
		return node;
	}

	private INode createClass(ClassDecl decl, String nodeType) {		
		INode node = createNode(nodeType);
		addAttribute(node, IAttr.Name, decl.getID());		
		for (BodyDecl member : decl.getBodyDecls()) {
			INode n = member.createGraphNode();
			createEdge(node, IEdge.EdgeContains, n);
		}
		return node;
	}
	
	
	private INode createInitializer(Block block, String name) {
		INode node = createNode(INode.MethodDecl); 
		addAttribute(node, IAttr.Name, name);
		createStmtList(block.getStmtList(), node);		
		return node;
		
	}

	private INode createMethod(MethodDecl decl, String nodeName) {
		INode node = createNode(nodeName);
		addAttribute(node, IAttr.Name, decl.getID());
		if (decl.hasBlock())
			createStmtList(decl.getBlock().getStmtList(), node);
		createParamList(decl.getParameterList(), node);
		return node;
	}

	private void createStmtList(AST.List<Stmt> stmts, INode node) {
		createList(stmts, node, IEdge.NextStmt);
	}

	private void createParamList(AST.List<ParameterDeclaration> params, INode node) {
		createList(params, node, IEdge.NextParam);
	}

	private void createList(AST.List<? extends ASTNode> elements, INode previous, String label) {
		for (ASTNode element : elements) {
			INode next = element.createGraphNode();
			createEdge(previous, label, next);
			previous = next;
		}
	}

	private INode createFieldAccess(VarAccess decl) {
		INode node = createNode(INode.FieldReference);
		INode lhs = decl.hasPrevExpr() ? decl.prevExpr().createGraphNode() : createNode(INode.This);
		INode rhs = createIdentifier(decl.getID());
		createEdge(node, IEdge.Lhs, lhs);
		createEdge(node, IEdge.Rhs, rhs);
		return node;
	}

	private INode createIdentifier(String id) {
		INode node = createNode(INode.Identifier);
		addAttribute(node, IAttr.Name, id);
		return node;
	}

	abstract public void save();

	public void addLayer(LayerDecl layerDecl) {
		INode layerNode = visit(layerDecl);
		createEdge(getProgramNode(), IEdge.EdgeContains, layerNode);		
	}

	// private void addAttribute(AggNode node, String key, Object value) {
	// ValueTuple vt = (ValueTuple) node.getAggNode().getAttribute();
	// ValueMember vm = (ValueMember) vt.getMemberAt(key);
	// assert vm != null :
	// String.format("attribute '%s' not found%n in node %s%n", key,
	// node.getAggNode().getType().getName());
	// vm.setExprAsObject(value);
	// vm.checkValidity();
	// }

	// private Type getNodeType(String typeName) {
	// Type nodeType =
	// graphGrammar.getTypeGraph().getTypeSet().getTypeByName(typeName);
	// assert nodeType != null :"[agg] cannot finde node type " + typeName;
	// return nodeType;
	// }

}
