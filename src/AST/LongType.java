
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class LongType extends IntegralType implements Cloneable {
    public void flushCache() {
        super.flushCache();
        typeDescriptor_computed = false;
        typeDescriptor_value = null;
        jvmName_computed = false;
        jvmName_value = null;
        boxed_computed = false;
        boxed_value = null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LongType clone() throws CloneNotSupportedException {
        LongType node = (LongType)super.clone();
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
     @SuppressWarnings({"unchecked", "cast"})  public LongType copy() {
      try {
          LongType node = (LongType)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LongType fullCopy() {
        LongType res = (LongType)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in Attributes.jrag at line 64

  public int addConstant(ConstantPool p, Constant c)     { return p.addConstant(c.longValue()); }

    // Declared in CodeGeneration.jrag at line 549

  public void emitPushConstant(CodeGeneration gen, int value) { LongLiteral.push(gen, value); }

    // Declared in CodeGeneration.jrag at line 620

  public void emitReturn(CodeGeneration gen)      { gen.emit(Bytecode.LRETURN);}

    // Declared in CodeGeneration.jrag at line 649

  public void emitLoadLocal(CodeGeneration gen, int pos) {
    gen.maxLocals = Math.max(gen.maxLocals, pos+2);
    if(pos == 0) gen.emit(Bytecode.LLOAD_0);
    else if(pos == 1) gen.emit(Bytecode.LLOAD_1);
    else if(pos == 2) gen.emit(Bytecode.LLOAD_2);
    else if(pos == 3) gen.emit(Bytecode.LLOAD_3);
    else if(pos < 256) gen.emit(Bytecode.LLOAD).add(pos);
    else gen.emit(Bytecode.WIDE).emit(Bytecode.LLOAD).add2(pos);
  }

    // Declared in CodeGeneration.jrag at line 762

  public void emitStoreLocal(CodeGeneration gen, int pos) {
    gen.maxLocals = Math.max(gen.maxLocals, pos+2);
    if(pos == 0) gen.emit(Bytecode.LSTORE_0);
    else if(pos == 1) gen.emit(Bytecode.LSTORE_1);
    else if(pos == 2) gen.emit(Bytecode.LSTORE_2);
    else if(pos == 3) gen.emit(Bytecode.LSTORE_3);
    else if(pos < 256) gen.emit(Bytecode.LSTORE).add(pos);
    else gen.emit(Bytecode.WIDE).emit(Bytecode.LSTORE).add2(pos);
  }

    // Declared in CodeGeneration.jrag at line 852

  public void emitDup(CodeGeneration gen)      { gen.emit(Bytecode.DUP2); }

    // Declared in CodeGeneration.jrag at line 857

  public void emitDup_x1(CodeGeneration gen)   { gen.emit(Bytecode.DUP2_X1); }

    // Declared in CodeGeneration.jrag at line 862

  public void emitDup_x2(CodeGeneration gen)   { gen.emit(Bytecode.DUP2_X2); }

    // Declared in CodeGeneration.jrag at line 867

  public void emitPop(CodeGeneration gen)      { gen.emit(Bytecode.POP2); }

    // Declared in CodeGeneration.jrag at line 951

  void emitCastTo(CodeGeneration gen, TypeDecl type)     { type.longToThis(gen); }

    // Declared in CodeGeneration.jrag at line 960

  void intToThis(CodeGeneration gen)   { gen.emit(Bytecode.I2L); }

    // Declared in CodeGeneration.jrag at line 973

  void floatToThis(CodeGeneration gen)   { gen.emit(Bytecode.F2L); }

    // Declared in CodeGeneration.jrag at line 982

  void doubleToThis(CodeGeneration gen)   { gen.emit(Bytecode.D2L); }

    // Declared in CodeGeneration.jrag at line 991

  void longToThis(CodeGeneration gen)   { }

    // Declared in CodeGeneration.jrag at line 998

  void byteToThis(CodeGeneration gen)     { gen.emit(Bytecode.I2L); }

    // Declared in CodeGeneration.jrag at line 1006

  void charToThis(CodeGeneration gen)     { gen.emit(Bytecode.I2L); }

    // Declared in CodeGeneration.jrag at line 1014

  void shortToThis(CodeGeneration gen)     { gen.emit(Bytecode.I2L); }

    // Declared in CodeGeneration.jrag at line 1044

  void neg(CodeGeneration gen)     { gen.emit(Bytecode.LNEG); }

    // Declared in CodeGeneration.jrag at line 1050

  void bitNot(CodeGeneration gen)     { emitPushConstant(gen, -1); gen.emit(Bytecode.LXOR); }

    // Declared in CodeGeneration.jrag at line 1056

  void add(CodeGeneration gen) {gen.emit(Bytecode.LADD);}

    // Declared in CodeGeneration.jrag at line 1062

  void sub(CodeGeneration gen) {gen.emit(Bytecode.LSUB);}

    // Declared in CodeGeneration.jrag at line 1068

  void mul(CodeGeneration gen) {gen.emit(Bytecode.LMUL);}

    // Declared in CodeGeneration.jrag at line 1074

  void div(CodeGeneration gen) {gen.emit(Bytecode.LDIV);}

    // Declared in CodeGeneration.jrag at line 1080

  void rem(CodeGeneration gen) {gen.emit(Bytecode.LREM);}

    // Declared in CodeGeneration.jrag at line 1086

  void shl(CodeGeneration gen) {gen.emit(Bytecode.LSHL);}

    // Declared in CodeGeneration.jrag at line 1090

  void shr(CodeGeneration gen) {gen.emit(Bytecode.LSHR);}

    // Declared in CodeGeneration.jrag at line 1094

  void ushr(CodeGeneration gen) {gen.emit(Bytecode.LUSHR);}

    // Declared in CodeGeneration.jrag at line 1098

  void bitand(CodeGeneration gen) {gen.emit(Bytecode.LAND);}

    // Declared in CodeGeneration.jrag at line 1103

  void bitor(CodeGeneration gen) {gen.emit(Bytecode.LOR);}

    // Declared in CodeGeneration.jrag at line 1108

  void bitxor(CodeGeneration gen) {gen.emit(Bytecode.LXOR);}

    // Declared in CodeGeneration.jrag at line 1117

  public void branchLT(CodeGeneration gen, int label)     { gen.emit(Bytecode.LCMP).emitCompare(Bytecode.IFLT, label); }

    // Declared in CodeGeneration.jrag at line 1123

  public void branchLE(CodeGeneration gen, int label)     { gen.emit(Bytecode.LCMP).emitCompare(Bytecode.IFLE, label); }

    // Declared in CodeGeneration.jrag at line 1129

  public void branchGE(CodeGeneration gen, int label)     { gen.emit(Bytecode.LCMP).emitCompare(Bytecode.IFGE, label); }

    // Declared in CodeGeneration.jrag at line 1135

  public void branchGT(CodeGeneration gen, int label)     { gen.emit(Bytecode.LCMP).emitCompare(Bytecode.IFGT, label); }

    // Declared in CodeGeneration.jrag at line 1141

  public void branchEQ(CodeGeneration gen, int label)      { gen.emit(Bytecode.LCMP).emitCompare(Bytecode.IFEQ, label); }

    // Declared in CodeGeneration.jrag at line 1150

  public void branchNE(CodeGeneration gen, int label)      { gen.emit(Bytecode.LCMP).emitCompare(Bytecode.IFNE, label); }

    // Declared in AnnotationsCodegen.jrag at line 184

  public int addAnnotConstant(ConstantPool p, Constant c) {
    return addConstant(p, c);
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 56

    public LongType() {
        super();

        setChild(new Opt(), 1);
        setChild(new List(), 2);

    }

    // Declared in java.ast at line 12


    // Declared in java.ast line 56
    public LongType(Modifiers p0, String p1, Opt<Access> p2, List<BodyDecl> p3) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
        setChild(p3, 2);
    }

    // Declared in java.ast at line 20


    // Declared in java.ast line 56
    public LongType(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<BodyDecl> p3) {
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

    // Declared in ConstantExpression.jrag at line 298
 @SuppressWarnings({"unchecked", "cast"})     public Constant cast(Constant c) {
        Constant cast_Constant_value = cast_compute(c);
        return cast_Constant_value;
    }

    private Constant cast_compute(Constant c) {  return Constant.create(c.longValue());  }

    // Declared in ConstantExpression.jrag at line 309
 @SuppressWarnings({"unchecked", "cast"})     public Constant plus(Constant c) {
        Constant plus_Constant_value = plus_compute(c);
        return plus_Constant_value;
    }

    private Constant plus_compute(Constant c) {  return c;  }

    // Declared in ConstantExpression.jrag at line 318
 @SuppressWarnings({"unchecked", "cast"})     public Constant minus(Constant c) {
        Constant minus_Constant_value = minus_compute(c);
        return minus_Constant_value;
    }

    private Constant minus_compute(Constant c) {  return Constant.create(-c.longValue());  }

    // Declared in ConstantExpression.jrag at line 327
 @SuppressWarnings({"unchecked", "cast"})     public Constant bitNot(Constant c) {
        Constant bitNot_Constant_value = bitNot_compute(c);
        return bitNot_Constant_value;
    }

    private Constant bitNot_compute(Constant c) {  return Constant.create(~c.longValue());  }

    // Declared in ConstantExpression.jrag at line 334
 @SuppressWarnings({"unchecked", "cast"})     public Constant mul(Constant c1, Constant c2) {
        Constant mul_Constant_Constant_value = mul_compute(c1, c2);
        return mul_Constant_Constant_value;
    }

    private Constant mul_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() * c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 343
 @SuppressWarnings({"unchecked", "cast"})     public Constant div(Constant c1, Constant c2) {
        Constant div_Constant_Constant_value = div_compute(c1, c2);
        return div_Constant_Constant_value;
    }

    private Constant div_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() / c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 352
 @SuppressWarnings({"unchecked", "cast"})     public Constant mod(Constant c1, Constant c2) {
        Constant mod_Constant_Constant_value = mod_compute(c1, c2);
        return mod_Constant_Constant_value;
    }

    private Constant mod_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() % c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 361
 @SuppressWarnings({"unchecked", "cast"})     public Constant add(Constant c1, Constant c2) {
        Constant add_Constant_Constant_value = add_compute(c1, c2);
        return add_Constant_Constant_value;
    }

    private Constant add_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() + c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 371
 @SuppressWarnings({"unchecked", "cast"})     public Constant sub(Constant c1, Constant c2) {
        Constant sub_Constant_Constant_value = sub_compute(c1, c2);
        return sub_Constant_Constant_value;
    }

    private Constant sub_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() - c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 380
 @SuppressWarnings({"unchecked", "cast"})     public Constant lshift(Constant c1, Constant c2) {
        Constant lshift_Constant_Constant_value = lshift_compute(c1, c2);
        return lshift_Constant_Constant_value;
    }

    private Constant lshift_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() << c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 387
 @SuppressWarnings({"unchecked", "cast"})     public Constant rshift(Constant c1, Constant c2) {
        Constant rshift_Constant_Constant_value = rshift_compute(c1, c2);
        return rshift_Constant_Constant_value;
    }

    private Constant rshift_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() >> c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 394
 @SuppressWarnings({"unchecked", "cast"})     public Constant urshift(Constant c1, Constant c2) {
        Constant urshift_Constant_Constant_value = urshift_compute(c1, c2);
        return urshift_Constant_Constant_value;
    }

    private Constant urshift_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() >>> c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 401
 @SuppressWarnings({"unchecked", "cast"})     public Constant andBitwise(Constant c1, Constant c2) {
        Constant andBitwise_Constant_Constant_value = andBitwise_compute(c1, c2);
        return andBitwise_Constant_Constant_value;
    }

    private Constant andBitwise_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() & c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 409
 @SuppressWarnings({"unchecked", "cast"})     public Constant xorBitwise(Constant c1, Constant c2) {
        Constant xorBitwise_Constant_Constant_value = xorBitwise_compute(c1, c2);
        return xorBitwise_Constant_Constant_value;
    }

    private Constant xorBitwise_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() ^ c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 417
 @SuppressWarnings({"unchecked", "cast"})     public Constant orBitwise(Constant c1, Constant c2) {
        Constant orBitwise_Constant_Constant_value = orBitwise_compute(c1, c2);
        return orBitwise_Constant_Constant_value;
    }

    private Constant orBitwise_compute(Constant c1, Constant c2) {  return Constant.create(c1.longValue() | c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 425
 @SuppressWarnings({"unchecked", "cast"})     public Constant questionColon(Constant cond, Constant c1, Constant c2) {
        Constant questionColon_Constant_Constant_Constant_value = questionColon_compute(cond, c1, c2);
        return questionColon_Constant_Constant_Constant_value;
    }

    private Constant questionColon_compute(Constant cond, Constant c1, Constant c2) {  return Constant.create(cond.booleanValue() ? c1.longValue() : c2.longValue());  }

    // Declared in ConstantExpression.jrag at line 529
 @SuppressWarnings({"unchecked", "cast"})     public boolean eqIsTrue(Expr left, Expr right) {
        boolean eqIsTrue_Expr_Expr_value = eqIsTrue_compute(left, right);
        return eqIsTrue_Expr_Expr_value;
    }

    private boolean eqIsTrue_compute(Expr left, Expr right) {  return left.constant().longValue() == right.constant().longValue();  }

    // Declared in ConstantExpression.jrag at line 537
 @SuppressWarnings({"unchecked", "cast"})     public boolean ltIsTrue(Expr left, Expr right) {
        boolean ltIsTrue_Expr_Expr_value = ltIsTrue_compute(left, right);
        return ltIsTrue_Expr_Expr_value;
    }

    private boolean ltIsTrue_compute(Expr left, Expr right) {  return left.constant().longValue() < right.constant().longValue();  }

    // Declared in ConstantExpression.jrag at line 543
 @SuppressWarnings({"unchecked", "cast"})     public boolean leIsTrue(Expr left, Expr right) {
        boolean leIsTrue_Expr_Expr_value = leIsTrue_compute(left, right);
        return leIsTrue_Expr_Expr_value;
    }

    private boolean leIsTrue_compute(Expr left, Expr right) {  return left.constant().longValue() <= right.constant().longValue();  }

    // Declared in NameCheck.jrag at line 424
 @SuppressWarnings({"unchecked", "cast"})     public boolean assignableToInt() {
        boolean assignableToInt_value = assignableToInt_compute();
        return assignableToInt_value;
    }

    private boolean assignableToInt_compute() {  return false;  }

    // Declared in TypeAnalysis.jrag at line 199
 @SuppressWarnings({"unchecked", "cast"})     public boolean isLong() {
        boolean isLong_value = isLong_compute();
        return isLong_value;
    }

    private boolean isLong_compute() {  return true;  }

    // Declared in CodeGeneration.jrag at line 631
 @SuppressWarnings({"unchecked", "cast"})     public byte arrayLoad() {
        byte arrayLoad_value = arrayLoad_compute();
        return arrayLoad_value;
    }

    private byte arrayLoad_compute() {  return Bytecode.LALOAD;  }

    // Declared in CodeGeneration.jrag at line 733
 @SuppressWarnings({"unchecked", "cast"})     public byte arrayStore() {
        byte arrayStore_value = arrayStore_compute();
        return arrayStore_value;
    }

    private byte arrayStore_compute() {  return Bytecode.LASTORE;  }

    // Declared in ConstantPoolNames.jrag at line 21
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

    private String typeDescriptor_compute() {  return "J";  }

    // Declared in CreateBCode.jrag at line 813
 @SuppressWarnings({"unchecked", "cast"})     public int arrayPrimitiveTypeDescriptor() {
        int arrayPrimitiveTypeDescriptor_value = arrayPrimitiveTypeDescriptor_compute();
        return arrayPrimitiveTypeDescriptor_value;
    }

    private int arrayPrimitiveTypeDescriptor_compute() {  return 11;  }

    // Declared in Java2Rewrites.jrag at line 39
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

    private String jvmName_compute() {  return "J";  }

    // Declared in Java2Rewrites.jrag at line 51
 @SuppressWarnings({"unchecked", "cast"})     public String primitiveClassName() {
        String primitiveClassName_value = primitiveClassName_compute();
        return primitiveClassName_value;
    }

    private String primitiveClassName_compute() {  return "Long";  }

    // Declared in LocalNum.jrag at line 127
 @SuppressWarnings({"unchecked", "cast"})     public int variableSize() {
        int variableSize_value = variableSize_compute();
        return variableSize_value;
    }

    private int variableSize_compute() {  return 2;  }

    // Declared in AutoBoxing.jrag at line 41
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

    private TypeDecl boxed_compute() {  return lookupType("java.lang", "Long");  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
