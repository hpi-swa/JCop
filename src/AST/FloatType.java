
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class FloatType extends FloatingPointType implements Cloneable {
    public void flushCache() {
        super.flushCache();
        typeDescriptor_computed = false;
        typeDescriptor_value = null;
        jvmName_computed = false;
        jvmName_value = null;
        boxed_computed = false;
        boxed_value = null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public FloatType clone() throws CloneNotSupportedException {
        FloatType node = (FloatType)super.clone();
        node.typeDescriptor_computed = false;
        node.typeDescriptor_value = null;
        node.jvmName_computed = false;
        node.jvmName_value = null;
        node.boxed_computed = false;
        node.boxed_value = null;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public FloatType copy() {
      try {
          FloatType node = (FloatType)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public FloatType fullCopy() {
        FloatType res = (FloatType)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in Attributes.jrag at line 65

  public int addConstant(ConstantPool p, Constant c)    { return p.addConstant(c.floatValue()); }

    // Declared in CodeGeneration.jrag at line 551

  public void emitPushConstant(CodeGeneration gen, int value) { FloatingPointLiteral.push(gen, value); }

    // Declared in CodeGeneration.jrag at line 621

  public void emitReturn(CodeGeneration gen)     { gen.emit(Bytecode.FRETURN);}

    // Declared in CodeGeneration.jrag at line 658

  public void emitLoadLocal(CodeGeneration gen, int pos) {
    gen.maxLocals = Math.max(gen.maxLocals, pos+1);
    if(pos == 0) gen.emit(Bytecode.FLOAD_0);
    else if(pos == 1) gen.emit(Bytecode.FLOAD_1);
    else if(pos == 2) gen.emit(Bytecode.FLOAD_2);
    else if(pos == 3) gen.emit(Bytecode.FLOAD_3);
    else if(pos < 256) gen.emit(Bytecode.FLOAD).add(pos);
    else gen.emit(Bytecode.WIDE).emit(Bytecode.FLOAD).add2(pos);
  }

    // Declared in CodeGeneration.jrag at line 771

  public void emitStoreLocal(CodeGeneration gen, int pos) {
    gen.maxLocals = Math.max(gen.maxLocals, pos+1);
    if(pos == 0) gen.emit(Bytecode.FSTORE_0);
    else if(pos == 1) gen.emit(Bytecode.FSTORE_1);
    else if(pos == 2) gen.emit(Bytecode.FSTORE_2);
    else if(pos == 3) gen.emit(Bytecode.FSTORE_3);
    else if(pos < 256) gen.emit(Bytecode.FSTORE).add(pos);
    else gen.emit(Bytecode.WIDE).emit(Bytecode.FSTORE).add2(pos);
  }

    // Declared in CodeGeneration.jrag at line 949

  void emitCastTo(CodeGeneration gen, TypeDecl type)    { type.floatToThis(gen); }

    // Declared in CodeGeneration.jrag at line 961

  void intToThis(CodeGeneration gen)  { gen.emit(Bytecode.I2F); }

    // Declared in CodeGeneration.jrag at line 972

  void floatToThis(CodeGeneration gen)  { }

    // Declared in CodeGeneration.jrag at line 981

  void doubleToThis(CodeGeneration gen)  { gen.emit(Bytecode.D2F); }

    // Declared in CodeGeneration.jrag at line 990

  void longToThis(CodeGeneration gen)  { gen.emit(Bytecode.L2F); }

    // Declared in CodeGeneration.jrag at line 997

  void byteToThis(CodeGeneration gen)    { gen.emit(Bytecode.I2F); }

    // Declared in CodeGeneration.jrag at line 1005

  void charToThis(CodeGeneration gen)    { gen.emit(Bytecode.I2F); }

    // Declared in CodeGeneration.jrag at line 1013

  void shortToThis(CodeGeneration gen)    { gen.emit(Bytecode.I2F); }

    // Declared in CodeGeneration.jrag at line 1045

  void neg(CodeGeneration gen)    { gen.emit(Bytecode.FNEG); }

    // Declared in CodeGeneration.jrag at line 1057

  void add(CodeGeneration gen) {gen.emit(Bytecode.FADD);}

    // Declared in CodeGeneration.jrag at line 1063

  void sub(CodeGeneration gen) {gen.emit(Bytecode.FSUB);}

    // Declared in CodeGeneration.jrag at line 1069

  void mul(CodeGeneration gen) {gen.emit(Bytecode.FMUL);}

    // Declared in CodeGeneration.jrag at line 1075

  void div(CodeGeneration gen) {gen.emit(Bytecode.FDIV);}

    // Declared in CodeGeneration.jrag at line 1081

  void rem(CodeGeneration gen) {gen.emit(Bytecode.FREM);}

    // Declared in CodeGeneration.jrag at line 1116

  public void branchLT(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPG).emitCompare(Bytecode.IFLT, label); }

    // Declared in CodeGeneration.jrag at line 1122

  public void branchLE(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPG).emitCompare(Bytecode.IFLE, label); }

    // Declared in CodeGeneration.jrag at line 1128

  public void branchGE(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFGE, label); }

    // Declared in CodeGeneration.jrag at line 1134

  public void branchGT(CodeGeneration gen, int label)    { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFGT, label); }

    // Declared in CodeGeneration.jrag at line 1140

  public void branchEQ(CodeGeneration gen, int label)     { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFEQ, label); }

    // Declared in CodeGeneration.jrag at line 1149

  public void branchNE(CodeGeneration gen, int label)     { gen.emit(Bytecode.FCMPL).emitCompare(Bytecode.IFNE, label); }

    // Declared in AnnotationsCodegen.jrag at line 187

  public int addAnnotConstant(ConstantPool p, Constant c) {
    return addConstant(p, c);
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 59

    public FloatType() {
        super();

        setChild(new Opt(), 1);
        setChild(new List(), 2);

    }

    // Declared in java.ast at line 12


    // Declared in java.ast line 59
    public FloatType(Modifiers p0, String p1, Opt<Access> p2, List<BodyDecl> p3) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
        setChild(p3, 2);
    }

    // Declared in java.ast at line 20


    // Declared in java.ast line 59
    public FloatType(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<BodyDecl> p3) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
        setChild(p3, 2);
    }

    // Declared in java.ast at line 27


  protected int numChildren() {
    return 3;
  }

    // Declared in java.ast at line 30

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 42
    public void setModifiers(Modifiers node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Modifiers getModifiers() {
        return (Modifiers)getChild(0);
    }

    // Declared in java.ast at line 9


    public Modifiers getModifiersNoTransform() {
        return (Modifiers)getChildNoTransform(0);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 42
    public void setSuperClassAccessOpt(Opt<Access> opt) {
        setChild(opt, 1);
    }

    // Declared in java.ast at line 6


    public boolean hasSuperClassAccess() {
        return getSuperClassAccessOpt().getNumChild() != 0;
    }

    // Declared in java.ast at line 10


     @SuppressWarnings({"unchecked", "cast"})  public Access getSuperClassAccess() {
        return (Access)getSuperClassAccessOpt().getChild(0);
    }

    // Declared in java.ast at line 14


    public void setSuperClassAccess(Access node) {
        getSuperClassAccessOpt().setChild(node, 0);
    }

    // Declared in java.ast at line 17

     @SuppressWarnings({"unchecked", "cast"})  public Opt<Access> getSuperClassAccessOpt() {
        return (Opt<Access>)getChild(1);
    }

    // Declared in java.ast at line 21


     @SuppressWarnings({"unchecked", "cast"})  public Opt<Access> getSuperClassAccessOptNoTransform() {
        return (Opt<Access>)getChildNoTransform(1);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 42
    public void setBodyDeclList(List<BodyDecl> list) {
        setChild(list, 2);
    }

    // Declared in java.ast at line 6


    private int getNumBodyDecl = 0;

    // Declared in java.ast at line 7

    public int getNumBodyDecl() {
        return getBodyDeclList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public BodyDecl getBodyDecl(int i) {
        return (BodyDecl)getBodyDeclList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addBodyDecl(BodyDecl node) {
        List<BodyDecl> list = getBodyDeclList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setBodyDecl(BodyDecl node, int i) {
        List<BodyDecl> list = getBodyDeclList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<BodyDecl> getBodyDecls() {
        return getBodyDeclList();
    }

    // Declared in java.ast at line 27

    public List<BodyDecl> getBodyDeclsNoTransform() {
        return getBodyDeclListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclList() {
        return (List<BodyDecl>)getChild(2);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclListNoTransform() {
        return (List<BodyDecl>)getChildNoTransform(2);
    }

    // Declared in ConstantExpression.jrag at line 299
 @SuppressWarnings({"unchecked", "cast"})     public Constant cast(Constant c) {
        Constant cast_Constant_value = cast_compute(c);
        return cast_Constant_value;
    }

    private Constant cast_compute(Constant c) {  return Constant.create(c.floatValue());  }

    // Declared in ConstantExpression.jrag at line 310
 @SuppressWarnings({"unchecked", "cast"})     public Constant plus(Constant c) {
        Constant plus_Constant_value = plus_compute(c);
        return plus_Constant_value;
    }

    private Constant plus_compute(Constant c) {  return c;  }

    // Declared in ConstantExpression.jrag at line 319
 @SuppressWarnings({"unchecked", "cast"})     public Constant minus(Constant c) {
        Constant minus_Constant_value = minus_compute(c);
        return minus_Constant_value;
    }

    private Constant minus_compute(Constant c) {  return Constant.create(-c.floatValue());  }

    // Declared in ConstantExpression.jrag at line 335
 @SuppressWarnings({"unchecked", "cast"})     public Constant mul(Constant c1, Constant c2) {
        Constant mul_Constant_Constant_value = mul_compute(c1, c2);
        return mul_Constant_Constant_value;
    }

    private Constant mul_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() * c2.floatValue());  }

    // Declared in ConstantExpression.jrag at line 344
 @SuppressWarnings({"unchecked", "cast"})     public Constant div(Constant c1, Constant c2) {
        Constant div_Constant_Constant_value = div_compute(c1, c2);
        return div_Constant_Constant_value;
    }

    private Constant div_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() / c2.floatValue());  }

    // Declared in ConstantExpression.jrag at line 353
 @SuppressWarnings({"unchecked", "cast"})     public Constant mod(Constant c1, Constant c2) {
        Constant mod_Constant_Constant_value = mod_compute(c1, c2);
        return mod_Constant_Constant_value;
    }

    private Constant mod_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() % c2.floatValue());  }

    // Declared in ConstantExpression.jrag at line 362
 @SuppressWarnings({"unchecked", "cast"})     public Constant add(Constant c1, Constant c2) {
        Constant add_Constant_Constant_value = add_compute(c1, c2);
        return add_Constant_Constant_value;
    }

    private Constant add_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() + c2.floatValue());  }

    // Declared in ConstantExpression.jrag at line 372
 @SuppressWarnings({"unchecked", "cast"})     public Constant sub(Constant c1, Constant c2) {
        Constant sub_Constant_Constant_value = sub_compute(c1, c2);
        return sub_Constant_Constant_value;
    }

    private Constant sub_compute(Constant c1, Constant c2) {  return Constant.create(c1.floatValue() - c2.floatValue());  }

    // Declared in ConstantExpression.jrag at line 426
 @SuppressWarnings({"unchecked", "cast"})     public Constant questionColon(Constant cond, Constant c1, Constant c2) {
        Constant questionColon_Constant_Constant_Constant_value = questionColon_compute(cond, c1, c2);
        return questionColon_Constant_Constant_Constant_value;
    }

    private Constant questionColon_compute(Constant cond, Constant c1, Constant c2) {  return Constant.create(cond.booleanValue() ? c1.floatValue() : c2.floatValue());  }

    // Declared in ConstantExpression.jrag at line 530
 @SuppressWarnings({"unchecked", "cast"})     public boolean eqIsTrue(Expr left, Expr right) {
        boolean eqIsTrue_Expr_Expr_value = eqIsTrue_compute(left, right);
        return eqIsTrue_Expr_Expr_value;
    }

    private boolean eqIsTrue_compute(Expr left, Expr right) {  return left.constant().floatValue() == right.constant().floatValue();  }

    // Declared in ConstantExpression.jrag at line 538
 @SuppressWarnings({"unchecked", "cast"})     public boolean ltIsTrue(Expr left, Expr right) {
        boolean ltIsTrue_Expr_Expr_value = ltIsTrue_compute(left, right);
        return ltIsTrue_Expr_Expr_value;
    }

    private boolean ltIsTrue_compute(Expr left, Expr right) {  return left.constant().floatValue() < right.constant().floatValue();  }

    // Declared in ConstantExpression.jrag at line 544
 @SuppressWarnings({"unchecked", "cast"})     public boolean leIsTrue(Expr left, Expr right) {
        boolean leIsTrue_Expr_Expr_value = leIsTrue_compute(left, right);
        return leIsTrue_Expr_Expr_value;
    }

    private boolean leIsTrue_compute(Expr left, Expr right) {  return left.constant().floatValue() <= right.constant().floatValue();  }

    // Declared in TypeAnalysis.jrag at line 197
 @SuppressWarnings({"unchecked", "cast"})     public boolean isFloat() {
        boolean isFloat_value = isFloat_compute();
        return isFloat_value;
    }

    private boolean isFloat_compute() {  return true;  }

    // Declared in CodeGeneration.jrag at line 632
 @SuppressWarnings({"unchecked", "cast"})     public byte arrayLoad() {
        byte arrayLoad_value = arrayLoad_compute();
        return arrayLoad_value;
    }

    private byte arrayLoad_compute() {  return Bytecode.FALOAD;  }

    // Declared in CodeGeneration.jrag at line 734
 @SuppressWarnings({"unchecked", "cast"})     public byte arrayStore() {
        byte arrayStore_value = arrayStore_compute();
        return arrayStore_value;
    }

    private byte arrayStore_compute() {  return Bytecode.FASTORE;  }

    // Declared in ConstantPoolNames.jrag at line 23
 @SuppressWarnings({"unchecked", "cast"})     public String typeDescriptor() {
        if(typeDescriptor_computed)
            return typeDescriptor_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        typeDescriptor_value = typeDescriptor_compute();
        if(isFinal && num == boundariesCrossed)
            typeDescriptor_computed = true;
        return typeDescriptor_value;
    }

    private String typeDescriptor_compute() {  return "F";  }

    // Declared in CreateBCode.jrag at line 808
 @SuppressWarnings({"unchecked", "cast"})     public int arrayPrimitiveTypeDescriptor() {
        int arrayPrimitiveTypeDescriptor_value = arrayPrimitiveTypeDescriptor_compute();
        return arrayPrimitiveTypeDescriptor_value;
    }

    private int arrayPrimitiveTypeDescriptor_compute() {  return 6;  }

    // Declared in Java2Rewrites.jrag at line 40
 @SuppressWarnings({"unchecked", "cast"})     public String jvmName() {
        if(jvmName_computed)
            return jvmName_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        jvmName_value = jvmName_compute();
        if(isFinal && num == boundariesCrossed)
            jvmName_computed = true;
        return jvmName_value;
    }

    private String jvmName_compute() {  return "F";  }

    // Declared in Java2Rewrites.jrag at line 52
 @SuppressWarnings({"unchecked", "cast"})     public String primitiveClassName() {
        String primitiveClassName_value = primitiveClassName_compute();
        return primitiveClassName_value;
    }

    private String primitiveClassName_compute() {  return "Float";  }

    // Declared in AutoBoxing.jrag at line 42
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl boxed() {
        if(boxed_computed)
            return boxed_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boxed_value = boxed_compute();
        if(isFinal && num == boundariesCrossed)
            boxed_computed = true;
        return boxed_value;
    }

    private TypeDecl boxed_compute() {  return lookupType("java.lang", "Float");  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
