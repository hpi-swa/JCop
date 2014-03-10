
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public abstract class Unary extends Expr implements Cloneable {
    public void flushCache() {
        super.flushCache();
        type_computed = false;
        type_value = null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public Unary clone() throws CloneNotSupportedException {
        Unary node = (Unary)super.clone();
        node.type_computed = false;
        node.type_value = null;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
    // Declared in PrettyPrint.jadd at line 382


  // Pre and post operations for unary expression
  
  public void toString(StringBuffer s) {
    s.append(printPreOp());
    getOperand().toString(s);
    s.append(printPostOp());
  }

    // Declared in CreateBCode.jrag at line 815
 // T_LONG

  public void createBCode(CodeGeneration gen) {   
    super.createBCode(gen);
    emitOperation(gen);
  }

    // Declared in AutoBoxingCodegen.jrag at line 295



  protected void boxingGen(CodeGeneration gen) {
    getOperand().createBCode(gen);
    TypeDecl type = getOperand().type();
    if(type.isReferenceType())
      type.emitCastTo(gen, type());
    emitOperation(gen);
  }

    // Declared in graphGeneration.jrag at line 85


  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 139

    public Unary() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 139
    public Unary(Expr p0) {
        setChild(p0, 0);
    }

    // Declared in java.ast at line 14


  protected int numChildren() {
    return 1;
  }

    // Declared in java.ast at line 17

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 139
    public void setOperand(Expr node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Expr getOperand() {
        return (Expr)getChild(0);
    }

    // Declared in java.ast at line 9


    public Expr getOperandNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

    // Declared in AutoBoxingCodegen.jrag at line 201


  // 15.12.2 Determine Method Signature

 
  // 15.14.2 Postix Increment Operator ++
  // 15.14.3 Postix Decrement Operator --
    public void emitPostfix(CodeGeneration gen, int constant) {
    Expr operand = getOperand();
    while(operand instanceof ParExpr)
      operand = ((ParExpr)operand).getExpr();
    Access access = ((Access)operand).lastAccess();
    access.createAssignLoadDest(gen);
    if(needsPush())
      access.createPushAssignmentResult(gen);
    TypeDecl type = access.type().binaryNumericPromotion(typeInt());
    access.type().emitCastTo(gen, type); // Added for AutoBoxing
    type.emitPushConstant(gen, constant);
    type.add(gen);
    type.emitCastTo(gen, access.type());
    access.emitStore(gen);
  }

    // Declared in AutoBoxingCodegen.jrag at line 219


  // 15.15.1 Prefix Increment Operator ++
  // 15.15.2 Prefix Decrement Operator --
    public void emitPrefix(CodeGeneration gen, int constant) {
    Expr operand = getOperand();
    while(operand instanceof ParExpr)
      operand = ((ParExpr)operand).getExpr();
    Access access = ((Access)operand).lastAccess();
    access.createAssignLoadDest(gen);
    TypeDecl type = access.type().binaryNumericPromotion(typeInt());
    access.type().emitCastTo(gen, type); // Added for AutoBoxing
    type.emitPushConstant(gen, constant);
    type.add(gen);
    type.emitCastTo(gen, access.type());
    if(needsPush())
      access.createPushAssignmentResult(gen);
    access.emitStore(gen);
  }

    // Declared in DefiniteAssignment.jrag at line 402
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDAafter(Variable v) {
        boolean isDAafter_Variable_value = isDAafter_compute(v);
        return isDAafter_Variable_value;
    }

    private boolean isDAafter_compute(Variable v) {  return getOperand().isDAafter(v);  }

    // Declared in DefiniteAssignment.jrag at line 846
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDUafter(Variable v) {
        boolean isDUafter_Variable_value = isDUafter_compute(v);
        return isDUafter_Variable_value;
    }

    private boolean isDUafter_compute(Variable v) {  return getOperand().isDUafter(v);  }

    // Declared in PrettyPrint.jadd at line 388
 @SuppressWarnings({"unchecked", "cast"})     public String printPostOp() {
        String printPostOp_value = printPostOp_compute();
        return printPostOp_value;
    }

    private String printPostOp_compute() {  return "";  }

    // Declared in PrettyPrint.jadd at line 392
 @SuppressWarnings({"unchecked", "cast"})     public String printPreOp() {
        String printPreOp_value = printPreOp_compute();
        return printPreOp_value;
    }

    private String printPreOp_compute() {  return "";  }

    protected boolean type_computed = false;
    protected TypeDecl type_value;
    // Declared in TypeAnalysis.jrag at line 315
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

    private TypeDecl type_compute() {  return getOperand().type();  }

    // Declared in DefiniteAssignment.jrag at line 44
    public boolean Define_boolean_isSource(ASTNode caller, ASTNode child) {
        if(caller == getOperandNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_isSource(this, caller);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
