
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class SynchronizedStmt extends Stmt implements Cloneable, FinallyHost {
    public void flushCache() {
        super.flushCache();
        isDAafter_Variable_values = null;
        isDUafter_Variable_values = null;
        canCompleteNormally_computed = false;
        label_begin_computed = false;
        label_end_computed = false;
        label_finally_computed = false;
        label_finally_block_computed = false;
        label_exception_handler_computed = false;
    }
     @SuppressWarnings({"unchecked", "cast"})  public SynchronizedStmt clone() throws CloneNotSupportedException {
        SynchronizedStmt node = (SynchronizedStmt)super.clone();
        node.isDAafter_Variable_values = null;
        node.isDUafter_Variable_values = null;
        node.canCompleteNormally_computed = false;
        node.label_begin_computed = false;
        node.label_end_computed = false;
        node.label_finally_computed = false;
        node.label_finally_block_computed = false;
        node.label_exception_handler_computed = false;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public SynchronizedStmt copy() {
      try {
          SynchronizedStmt node = (SynchronizedStmt)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public SynchronizedStmt fullCopy() {
        SynchronizedStmt res = (SynchronizedStmt)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in BranchTarget.jrag at line 207

  public void collectFinally(Stmt branchStmt, ArrayList list) {
    list.add(this);
    super.collectFinally(branchStmt, list);
  }

    // Declared in PrettyPrint.jadd at line 705


  public void toString(StringBuffer s) {
    s.append("synchronized(");
    getExpr().toString(s);
    s.append(") ");
    getBlock().toString(s);
  }

    // Declared in TypeCheck.jrag at line 362


  public void typeCheck() {
    TypeDecl type = getExpr().type();
    if(!type.isReferenceType() || type.isNull())
      error("*** The type of the expression must be a reference");
  }

    // Declared in CodeGeneration.jrag at line 822


  public void emitMonitorEnter(CodeGeneration gen) {
    gen.emitDup();
    int num = localNum();
    gen.emitStoreReference(num);
    gen.emit(Bytecode.MONITORENTER);
  }

    // Declared in CodeGeneration.jrag at line 828

  public void emitExceptionHandler(CodeGeneration gen) {
    // add 1 to stack depth
    gen.changeStackDepth(1);
    int num = localNum() + 1;
    gen.emitStoreReference(num);
    gen.emitJsr(label_finally_block());
    gen.emitLoadReference(num);
    gen.emit(Bytecode.ATHROW);
  }

    // Declared in CodeGeneration.jrag at line 837

  public void emitFinallyBlock(CodeGeneration gen) {
    // add 1 to stack depth
    gen.changeStackDepth(1);
    int num = localNum() + 2;
    gen.emitStoreReference(num);
    gen.emitLoadReference(localNum()); // monitor
    gen.emit(Bytecode.MONITOREXIT);
    gen.emit(Bytecode.RET).add(num);
  }

    // Declared in CreateBCode.jrag at line 1519


  public void createBCode(CodeGeneration gen) {
    super.createBCode(gen);
    getExpr().createBCode(gen);
    emitMonitorEnter(gen);
    gen.addLabel(label_begin());
    getBlock().createBCode(gen);
    gen.addLabel(label_finally());
    if(getBlock().canCompleteNormally()) {
      gen.emitJsr(label_finally_block());
      gen.emitGoto(label_end());
    }
    gen.addLabel(label_exception_handler());
    emitExceptionHandler(gen);
    gen.addLabel(label_finally_block());
    emitFinallyBlock(gen);
    gen.addLabel(label_end());
    gen.createExceptionTable(this);
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 220

    public SynchronizedStmt() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 220
    public SynchronizedStmt(Expr p0, Block p1) {
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
    // Declared in java.ast line 220
    public void setExpr(Expr node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Expr getExpr() {
        return (Expr)getChild(0);
    }

    // Declared in java.ast at line 9


    public Expr getExprNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 220
    public void setBlock(Block node) {
        setChild(node, 1);
    }

    // Declared in java.ast at line 5

    public Block getBlock() {
        return (Block)getChild(1);
    }

    // Declared in java.ast at line 9


    public Block getBlockNoTransform() {
        return (Block)getChildNoTransform(1);
    }

    // Declared in DefiniteAssignment.jrag at line 656
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDAafter(Variable v) {
        Object _parameters = v;
if(isDAafter_Variable_values == null) isDAafter_Variable_values = new java.util.HashMap(4);
        if(isDAafter_Variable_values.containsKey(_parameters))
            return ((Boolean)isDAafter_Variable_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean isDAafter_Variable_value = isDAafter_compute(v);
        if(isFinal && num == boundariesCrossed)
            isDAafter_Variable_values.put(_parameters, Boolean.valueOf(isDAafter_Variable_value));
        return isDAafter_Variable_value;
    }

    private boolean isDAafter_compute(Variable v) {  return getBlock().isDAafter(v);  }

    // Declared in DefiniteAssignment.jrag at line 919
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDUafterFinally(Variable v) {
        boolean isDUafterFinally_Variable_value = isDUafterFinally_compute(v);
        return isDUafterFinally_Variable_value;
    }

    private boolean isDUafterFinally_compute(Variable v) {  return true;  }

    // Declared in DefiniteAssignment.jrag at line 922
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDAafterFinally(Variable v) {
        boolean isDAafterFinally_Variable_value = isDAafterFinally_compute(v);
        return isDAafterFinally_Variable_value;
    }

    private boolean isDAafterFinally_compute(Variable v) {  return false;  }

    // Declared in DefiniteAssignment.jrag at line 1182
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDUafter(Variable v) {
        Object _parameters = v;
if(isDUafter_Variable_values == null) isDUafter_Variable_values = new java.util.HashMap(4);
        if(isDUafter_Variable_values.containsKey(_parameters))
            return ((Boolean)isDUafter_Variable_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean isDUafter_Variable_value = isDUafter_compute(v);
        if(isFinal && num == boundariesCrossed)
            isDUafter_Variable_values.put(_parameters, Boolean.valueOf(isDUafter_Variable_value));
        return isDUafter_Variable_value;
    }

    private boolean isDUafter_compute(Variable v) {  return getBlock().isDUafter(v);  }

    // Declared in UnreachableStatements.jrag at line 110
 @SuppressWarnings({"unchecked", "cast"})     public boolean canCompleteNormally() {
        if(canCompleteNormally_computed)
            return canCompleteNormally_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        canCompleteNormally_value = canCompleteNormally_compute();
        if(isFinal && num == boundariesCrossed)
            canCompleteNormally_computed = true;
        return canCompleteNormally_value;
    }

    private boolean canCompleteNormally_compute() {  return getBlock().canCompleteNormally();  }

    protected boolean label_begin_computed = false;
    protected int label_begin_value;
    // Declared in CreateBCode.jrag at line 1513
 @SuppressWarnings({"unchecked", "cast"})     public int label_begin() {
        if(label_begin_computed)
            return label_begin_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        label_begin_value = label_begin_compute();
        if(isFinal && num == boundariesCrossed)
            label_begin_computed = true;
        return label_begin_value;
    }

    private int label_begin_compute() {  return hostType().constantPool().newLabel();  }

    protected boolean label_end_computed = false;
    protected int label_end_value;
    // Declared in CreateBCode.jrag at line 1514
 @SuppressWarnings({"unchecked", "cast"})     public int label_end() {
        if(label_end_computed)
            return label_end_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        label_end_value = label_end_compute();
        if(isFinal && num == boundariesCrossed)
            label_end_computed = true;
        return label_end_value;
    }

    private int label_end_compute() {  return hostType().constantPool().newLabel();  }

    protected boolean label_finally_computed = false;
    protected int label_finally_value;
    // Declared in CreateBCode.jrag at line 1515
 @SuppressWarnings({"unchecked", "cast"})     public int label_finally() {
        if(label_finally_computed)
            return label_finally_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        label_finally_value = label_finally_compute();
        if(isFinal && num == boundariesCrossed)
            label_finally_computed = true;
        return label_finally_value;
    }

    private int label_finally_compute() {  return hostType().constantPool().newLabel();  }

    protected boolean label_finally_block_computed = false;
    protected int label_finally_block_value;
    // Declared in CreateBCode.jrag at line 1516
 @SuppressWarnings({"unchecked", "cast"})     public int label_finally_block() {
        if(label_finally_block_computed)
            return label_finally_block_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        label_finally_block_value = label_finally_block_compute();
        if(isFinal && num == boundariesCrossed)
            label_finally_block_computed = true;
        return label_finally_block_value;
    }

    private int label_finally_block_compute() {  return hostType().constantPool().newLabel();  }

    protected boolean label_exception_handler_computed = false;
    protected int label_exception_handler_value;
    // Declared in CreateBCode.jrag at line 1517
 @SuppressWarnings({"unchecked", "cast"})     public int label_exception_handler() {
        if(label_exception_handler_computed)
            return label_exception_handler_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        label_exception_handler_value = label_exception_handler_compute();
        if(isFinal && num == boundariesCrossed)
            label_exception_handler_computed = true;
        return label_exception_handler_value;
    }

    private int label_exception_handler_compute() {  return hostType().constantPool().newLabel();  }

    // Declared in LocalNum.jrag at line 122
    public int Define_int_localNum(ASTNode caller, ASTNode child) {
        if(caller == getBlockNoTransform()) {
            return localNum() + 3;
        }
        return getParent().Define_int_localNum(this, caller);
    }

    // Declared in UnreachableStatements.jrag at line 155
    public boolean Define_boolean_reportUnreachable(ASTNode caller, ASTNode child) {
        if(caller == getBlockNoTransform()) {
            return reachable();
        }
        return getParent().Define_boolean_reportUnreachable(this, caller);
    }

    // Declared in UnreachableStatements.jrag at line 111
    public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
        if(caller == getBlockNoTransform()) {
            return reachable();
        }
        return getParent().Define_boolean_reachable(this, caller);
    }

    // Declared in DefiniteAssignment.jrag at line 658
    public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
        if(caller == getBlockNoTransform()) {
            return getExpr().isDAafter(v);
        }
        if(caller == getExprNoTransform()) {
            return isDAbefore(v);
        }
        return getParent().Define_boolean_isDAbefore(this, caller, v);
    }

    // Declared in DefiniteAssignment.jrag at line 1184
    public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
        if(caller == getBlockNoTransform()) {
            return getExpr().isDUafter(v);
        }
        if(caller == getExprNoTransform()) {
            return isDUbefore(v);
        }
        return getParent().Define_boolean_isDUbefore(this, caller, v);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
