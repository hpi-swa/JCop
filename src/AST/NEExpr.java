
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class NEExpr extends EqualityExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public NEExpr clone() throws CloneNotSupportedException {
        NEExpr node = (NEExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public NEExpr copy() {
      try {
          NEExpr node = (NEExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public NEExpr fullCopy() {
        NEExpr res = (NEExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in CreateBCode.jrag at line 1137

  public void compareBranch(CodeGeneration gen, int label, TypeDecl typeDecl) { typeDecl.branchNE(gen, label); }

    // Declared in CreateBCode.jrag at line 1147

  public void compareNotBranch(CodeGeneration gen, int label, TypeDecl typeDecl) { typeDecl.branchEQ(gen, label); }

    // Declared in java.ast at line 3
    // Declared in java.ast line 186

    public NEExpr() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 186
    public NEExpr(Expr p0, Expr p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    // Declared in java.ast at line 15


  protected int numChildren() {
    return 2;
  }

    // Declared in java.ast at line 18

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 153
    public void setLeftOperand(Expr node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Expr getLeftOperand() {
        return (Expr)getChild(0);
    }

    // Declared in java.ast at line 9


    public Expr getLeftOperandNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 153
    public void setRightOperand(Expr node) {
        setChild(node, 1);
    }

    // Declared in java.ast at line 5

    public Expr getRightOperand() {
        return (Expr)getChild(1);
    }

    // Declared in java.ast at line 9


    public Expr getRightOperandNoTransform() {
        return (Expr)getChildNoTransform(1);
    }

    // Declared in ConstantExpression.jrag at line 516
 @SuppressWarnings({"unchecked", "cast"})     public Constant constant() {
        Constant constant_value = constant_compute();
        return constant_value;
    }

    private Constant constant_compute() {  return Constant.create(!binaryNumericPromotedType().eqIsTrue(left(), right()));  }

    // Declared in PrettyPrint.jadd at line 435
 @SuppressWarnings({"unchecked", "cast"})     public String printOp() {
        String printOp_value = printOp_compute();
        return printOp_value;
    }

    private String printOp_compute() {  return " != ";  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
