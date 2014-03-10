package jcop.output.graph;

import AST.ASTNode;
import AST.Access;
import AST.AssignExpr;
import AST.Binary;
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
import AST.IntegerLiteral;
import AST.LayerActivation;
import AST.LayerDecl;
import AST.MemberClassDecl;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.NullLiteral;
import AST.ParameterDeclaration;
import AST.PartialMethodDecl;
import AST.ProceedExpr;
import AST.Program;
import AST.ReturnStmt;
import AST.Stmt;
import AST.StringLiteral;
import AST.ThisAccess;
import AST.TypeAccess;
import AST.TypeDecl;
import AST.VarAccess;
import AST.VariableDeclaration;
import agg.xt_basis.Node;

public interface IAstVisitor {

	public Node visit(ASTNode<?> decl);

	public void visit(TypeDecl decl);

	public Node visit(ClassDecl decl);

	public Node visit(LayerDecl decl);

	public Node visit(ContextDecl decl);

	public Node visit(Program decl);

	public Node visit(MethodAccess access);

	public Node visit(MemberClassDecl memberClass);

	public Node visit(ConstructorDecl decl);

	public Node visit(MethodDecl decl);

	public Node visit(VarAccess decl);

	public Node visit(ContextConstraint decl);

	public Node visit(ProceedExpr decl);

	public Node visit(PartialMethodDecl decl);

	public Node visit(LayerActivation activation);

	public Node visit(ClassInstanceExpr expr);

	public Node visit(FieldDeclaration decl);

	public Node visit(NullLiteral literal);

	public Node visit(BooleanLiteral literal);

	public Node visit(StringLiteral literal);

	public Node visit(IntegerLiteral literal);

	public Node visit(Expr expr);

	public Node visit(Access access);

	public Node visit(TypeAccess access);

	public Node visit(ThisAccess access);

	public Node visit(Dot access);

	public Node visit(Binary binary);

	public Node visit(VariableDeclaration decl);

	public Node visit(AssignExpr assign);

	public Node visit(Stmt stmt);

	public Node visit(IfStmt stmt);

	public Node visit(ReturnStmt stmt);

	public Node visit(ExprStmt stmt);

	public Node visit(ParameterDeclaration param);

	public Node visit(CompilationUnit unit);

}