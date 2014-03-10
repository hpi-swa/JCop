
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



//ContextDecl:TypeDecl ::= 
//   <ID:String> 
//   ContextDeclBlock;
//ContextDeclBlock:Block ::=
//   ContextConstraint* ;


public class ContextBodyBlock extends Block implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextBodyBlock clone() throws CloneNotSupportedException {
        ContextBodyBlock node = (ContextBodyBlock)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextBodyBlock copy() {
      try {
          ContextBodyBlock node = (ContextBodyBlock)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextBodyBlock fullCopy() {
        ContextBodyBlock res = (ContextBodyBlock)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 80

    public ContextBodyBlock() {
        super();

        setChild(new List(), 0);
        setChild(new List(), 1);
        setChild(new List(), 2);

    }

    // Declared in jcop.ast at line 13


    // Declared in jcop.ast line 80
    public ContextBodyBlock(List<Stmt> p0, List<ContextConstraint> p1, List<BodyDecl> p2) {
        setChild(p0, 0);
        setChild(p1, 1);
        setChild(p2, 2);
    }

    // Declared in jcop.ast at line 19


  protected int numChildren() {
    return 3;
  }

    // Declared in jcop.ast at line 22

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 200
    public void setStmtList(List<Stmt> list) {
        setChild(list, 0);
    }

    // Declared in java.ast at line 6


    private int getNumStmt = 0;

    // Declared in java.ast at line 7

    public int getNumStmt() {
        return getStmtList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Stmt getStmt(int i) {
        return (Stmt)getStmtList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addStmt(Stmt node) {
        List<Stmt> list = getStmtList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setStmt(Stmt node, int i) {
        List<Stmt> list = getStmtList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<Stmt> getStmts() {
        return getStmtList();
    }

    // Declared in java.ast at line 27

    public List<Stmt> getStmtsNoTransform() {
        return getStmtListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Stmt> getStmtList() {
        return (List<Stmt>)getChild(0);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Stmt> getStmtListNoTransform() {
        return (List<Stmt>)getChildNoTransform(0);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 80
    public void setConstraintList(List<ContextConstraint> list) {
        setChild(list, 1);
    }

    // Declared in jcop.ast at line 6


    private int getNumConstraint = 0;

    // Declared in jcop.ast at line 7

    public int getNumConstraint() {
        return getConstraintList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public ContextConstraint getConstraint(int i) {
        return (ContextConstraint)getConstraintList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addConstraint(ContextConstraint node) {
        List<ContextConstraint> list = getConstraintList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setConstraint(ContextConstraint node, int i) {
        List<ContextConstraint> list = getConstraintList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<ContextConstraint> getConstraints() {
        return getConstraintList();
    }

    // Declared in jcop.ast at line 27

    public List<ContextConstraint> getConstraintsNoTransform() {
        return getConstraintListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<ContextConstraint> getConstraintList() {
        return (List<ContextConstraint>)getChild(1);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<ContextConstraint> getConstraintListNoTransform() {
        return (List<ContextConstraint>)getChildNoTransform(1);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 80
    public void setBodyElelemtList(List<BodyDecl> list) {
        setChild(list, 2);
    }

    // Declared in jcop.ast at line 6


    private int getNumBodyElelemt = 0;

    // Declared in jcop.ast at line 7

    public int getNumBodyElelemt() {
        return getBodyElelemtList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public BodyDecl getBodyElelemt(int i) {
        return (BodyDecl)getBodyElelemtList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addBodyElelemt(BodyDecl node) {
        List<BodyDecl> list = getBodyElelemtList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setBodyElelemt(BodyDecl node, int i) {
        List<BodyDecl> list = getBodyElelemtList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<BodyDecl> getBodyElelemts() {
        return getBodyElelemtList();
    }

    // Declared in jcop.ast at line 27

    public List<BodyDecl> getBodyElelemtsNoTransform() {
        return getBodyElelemtListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyElelemtList() {
        return (List<BodyDecl>)getChild(2);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyElelemtListNoTransform() {
        return (List<BodyDecl>)getChildNoTransform(2);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
