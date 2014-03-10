
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public abstract class RelationalExpr extends Binary implements Cloneable {
    public void flushCache() {
        super.flushCache();
        type_computed = false;
        type_value = null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public RelationalExpr clone() throws CloneNotSupportedException {
        RelationalExpr node = (RelationalExpr)super.clone();
        node.type_computed = false;
        node.type_value = null;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
    // Declared in TypeCheck.jrag at line 204


  // 15.20
  public void typeCheck() {
    if(!getLeftOperand().type().isNumericType())
      error(getLeftOperand().type().typeName() + " is not numeric");
    if(!getRightOperand().type().isNumericType())
      error(getRightOperand().type().typeName() + " is not numeric");
  }

    // Declared in CreateBCode.jrag at line 1007

  
  public void createBCode(CodeGeneration gen) { emitBooleanCondition(gen); }

    // Declared in CreateBCode.jrag at line 1103

  
  public void emitEvalBranch(CodeGeneration gen) {
    if(isTrue())
      gen.emitGoto(true_label());
    else if(isFalse())
      gen.emitGoto(false_label());
    else {
      TypeDecl type = getLeftOperand().type();
      if(type.isNumericType()) {
        type = binaryNumericPromotedType();
        getLeftOperand().createBCode(gen);
        getLeftOperand().type().emitCastTo(gen, type); // Binary numeric promotion
        getRightOperand().createBCode(gen);
        getRightOperand().type().emitCastTo(gen, type); // Binary numeric promotion
      }
      else {
        getLeftOperand().createBCode(gen);
        getRightOperand().createBCode(gen);
      }
      compareBranch(gen, true_label(), type);
      gen.emitGoto(false_label());
      // compareNotBranch does not work for float comparison with NaN
      //compareNotBranch(gen, false_label(), type);
      //gen.emitGoto(true_label());
    }
  }

    // Declared in CreateBCode.jrag at line 1129


  public void compareBranch(CodeGeneration gen, int label, TypeDecl typeDecl) {
    throw new Error("compareBranch not supported for " + getClass().getName());
  }

    // Declared in CreateBCode.jrag at line 1139


  public void compareNotBranch(CodeGeneration gen, int label, TypeDecl typeDecl) {
    throw new Error("compareBranch not supported for " + getClass().getName());
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 178

    public RelationalExpr() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 178
    public RelationalExpr(Expr p0, Expr p1) {
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

    protected boolean type_computed = false;
    protected TypeDecl type_value;
    // Declared in TypeAnalysis.jrag at line 345
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl type() {
        if(type_computed)
            return type_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        type_value = type_compute();
        if(isFinal && num == boundariesCrossed)
            type_computed = true;
        return type_value;
    }

    private TypeDecl type_compute() {  return typeBoolean();  }

    // Declared in CreateBCode.jrag at line 934
 @SuppressWarnings({"unchecked", "cast"})     public boolean definesLabel() {
        boolean definesLabel_value = definesLabel_compute();
        return definesLabel_value;
    }

    private boolean definesLabel_compute() {  return false;  }

    // Declared in CreateBCode.jrag at line 975
    public int Define_int_condition_true_label(ASTNode caller, ASTNode child) {
        if(caller == getRightOperandNoTransform()) {
            return true_label();
        }
        if(caller == getLeftOperandNoTransform()) {
            return true_label();
        }
        return getParent().Define_int_condition_true_label(this, caller);
    }

    // Declared in CreateBCode.jrag at line 974
    public int Define_int_condition_false_label(ASTNode caller, ASTNode child) {
        if(caller == getRightOperandNoTransform()) {
            return false_label();
        }
        if(caller == getLeftOperandNoTransform()) {
            return false_label();
        }
        return getParent().Define_int_condition_false_label(this, caller);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
