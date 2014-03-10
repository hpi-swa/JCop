
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public abstract class IntegralType extends NumericType implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public IntegralType clone() throws CloneNotSupportedException {
        IntegralType node = (IntegralType)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
    // Declared in Attributes.jrag at line 63
 
  public int addConstant(ConstantPool p, Constant c) { return p.addConstant(c.intValue()); }

    // Declared in CodeGeneration.jrag at line 548

  public void emitPushConstant(CodeGeneration gen, int value) { IntegerLiteral.push(gen, value); }

    // Declared in CodeGeneration.jrag at line 995

  void byteToThis(CodeGeneration gen) { }

    // Declared in CodeGeneration.jrag at line 1002

  void charToThis(CodeGeneration gen) { }

    // Declared in CodeGeneration.jrag at line 1010

  void shortToThis(CodeGeneration gen) { }

    // Declared in CodeGeneration.jrag at line 1043

  void neg(CodeGeneration gen) { gen.emit(Bytecode.INEG); }

    // Declared in CodeGeneration.jrag at line 1049

  void bitNot(CodeGeneration gen) { gen.emit(Bytecode.ICONST_M1).emit(Bytecode.IXOR); }

    // Declared in CodeGeneration.jrag at line 1059

  void add(CodeGeneration gen) {gen.emit(Bytecode.IADD);}

    // Declared in CodeGeneration.jrag at line 1065

  void sub(CodeGeneration gen) {gen.emit(Bytecode.ISUB);}

    // Declared in CodeGeneration.jrag at line 1071

  void mul(CodeGeneration gen) {gen.emit(Bytecode.IMUL);}

    // Declared in CodeGeneration.jrag at line 1077

  void div(CodeGeneration gen) {gen.emit(Bytecode.IDIV);}

    // Declared in CodeGeneration.jrag at line 1083

  void rem(CodeGeneration gen) {gen.emit(Bytecode.IREM);}

    // Declared in CodeGeneration.jrag at line 1087

  void shl(CodeGeneration gen) {gen.emit(Bytecode.ISHL);}

    // Declared in CodeGeneration.jrag at line 1091

  void shr(CodeGeneration gen) {gen.emit(Bytecode.ISHR);}

    // Declared in CodeGeneration.jrag at line 1095

  void ushr(CodeGeneration gen) {gen.emit(Bytecode.IUSHR);}

    // Declared in CodeGeneration.jrag at line 1099

  void bitand(CodeGeneration gen) {gen.emit(Bytecode.IAND);}

    // Declared in CodeGeneration.jrag at line 1104

  void bitor(CodeGeneration gen) {gen.emit(Bytecode.IOR);}

    // Declared in CodeGeneration.jrag at line 1109

  void bitxor(CodeGeneration gen) {gen.emit(Bytecode.IXOR);}

    // Declared in CodeGeneration.jrag at line 1118

  public void branchLT(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ICMPLT, label); }

    // Declared in CodeGeneration.jrag at line 1124

  public void branchLE(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ICMPLE, label); }

    // Declared in CodeGeneration.jrag at line 1130

  public void branchGE(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ICMPGE, label); }

    // Declared in CodeGeneration.jrag at line 1136

  public void branchGT(CodeGeneration gen, int label) { gen.emitCompare(Bytecode.IF_ICMPGT, label); }

    // Declared in CodeGeneration.jrag at line 1142

  public void branchEQ(CodeGeneration gen, int label)  { gen.emitCompare(Bytecode.IF_ICMPEQ, label); }

    // Declared in CodeGeneration.jrag at line 1151

  public void branchNE(CodeGeneration gen, int label)  { gen.emitCompare(Bytecode.IF_ICMPNE, label); }

    // Declared in AnnotationsCodegen.jrag at line 181
 
  public int addAnnotConstant(ConstantPool p, Constant c) {
    return addConstant(p, c);
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 52

    public IntegralType() {
        super();

        setChild(new Opt(), 1);
        setChild(new List(), 2);

    }

    // Declared in java.ast at line 12


    // Declared in java.ast line 52
    public IntegralType(Modifiers p0, String p1, Opt<Access> p2, List<BodyDecl> p3) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
        setChild(p3, 2);
    }

    // Declared in java.ast at line 20


    // Declared in java.ast line 52
    public IntegralType(Modifiers p0, beaver.Symbol p1, Opt<Access> p2, List<BodyDecl> p3) {
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

    // Declared in ConstantExpression.jrag at line 294
 @SuppressWarnings({"unchecked", "cast"})     public Constant cast(Constant c) {
        Constant cast_Constant_value = cast_compute(c);
        return cast_Constant_value;
    }

    private Constant cast_compute(Constant c) {  return Constant.create(c.intValue());  }

    // Declared in ConstantExpression.jrag at line 308
 @SuppressWarnings({"unchecked", "cast"})     public Constant plus(Constant c) {
        Constant plus_Constant_value = plus_compute(c);
        return plus_Constant_value;
    }

    private Constant plus_compute(Constant c) {  return c;  }

    // Declared in ConstantExpression.jrag at line 317
 @SuppressWarnings({"unchecked", "cast"})     public Constant minus(Constant c) {
        Constant minus_Constant_value = minus_compute(c);
        return minus_Constant_value;
    }

    private Constant minus_compute(Constant c) {  return Constant.create(-c.intValue());  }

    // Declared in ConstantExpression.jrag at line 326
 @SuppressWarnings({"unchecked", "cast"})     public Constant bitNot(Constant c) {
        Constant bitNot_Constant_value = bitNot_compute(c);
        return bitNot_Constant_value;
    }

    private Constant bitNot_compute(Constant c) {  return Constant.create(~c.intValue());  }

    // Declared in ConstantExpression.jrag at line 333
 @SuppressWarnings({"unchecked", "cast"})     public Constant mul(Constant c1, Constant c2) {
        Constant mul_Constant_Constant_value = mul_compute(c1, c2);
        return mul_Constant_Constant_value;
    }

    private Constant mul_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() * c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 342
 @SuppressWarnings({"unchecked", "cast"})     public Constant div(Constant c1, Constant c2) {
        Constant div_Constant_Constant_value = div_compute(c1, c2);
        return div_Constant_Constant_value;
    }

    private Constant div_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() / c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 351
 @SuppressWarnings({"unchecked", "cast"})     public Constant mod(Constant c1, Constant c2) {
        Constant mod_Constant_Constant_value = mod_compute(c1, c2);
        return mod_Constant_Constant_value;
    }

    private Constant mod_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() % c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 360
 @SuppressWarnings({"unchecked", "cast"})     public Constant add(Constant c1, Constant c2) {
        Constant add_Constant_Constant_value = add_compute(c1, c2);
        return add_Constant_Constant_value;
    }

    private Constant add_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() + c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 370
 @SuppressWarnings({"unchecked", "cast"})     public Constant sub(Constant c1, Constant c2) {
        Constant sub_Constant_Constant_value = sub_compute(c1, c2);
        return sub_Constant_Constant_value;
    }

    private Constant sub_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() - c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 379
 @SuppressWarnings({"unchecked", "cast"})     public Constant lshift(Constant c1, Constant c2) {
        Constant lshift_Constant_Constant_value = lshift_compute(c1, c2);
        return lshift_Constant_Constant_value;
    }

    private Constant lshift_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() << c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 386
 @SuppressWarnings({"unchecked", "cast"})     public Constant rshift(Constant c1, Constant c2) {
        Constant rshift_Constant_Constant_value = rshift_compute(c1, c2);
        return rshift_Constant_Constant_value;
    }

    private Constant rshift_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() >> c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 393
 @SuppressWarnings({"unchecked", "cast"})     public Constant urshift(Constant c1, Constant c2) {
        Constant urshift_Constant_Constant_value = urshift_compute(c1, c2);
        return urshift_Constant_Constant_value;
    }

    private Constant urshift_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() >>> c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 400
 @SuppressWarnings({"unchecked", "cast"})     public Constant andBitwise(Constant c1, Constant c2) {
        Constant andBitwise_Constant_Constant_value = andBitwise_compute(c1, c2);
        return andBitwise_Constant_Constant_value;
    }

    private Constant andBitwise_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() & c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 408
 @SuppressWarnings({"unchecked", "cast"})     public Constant xorBitwise(Constant c1, Constant c2) {
        Constant xorBitwise_Constant_Constant_value = xorBitwise_compute(c1, c2);
        return xorBitwise_Constant_Constant_value;
    }

    private Constant xorBitwise_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() ^ c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 416
 @SuppressWarnings({"unchecked", "cast"})     public Constant orBitwise(Constant c1, Constant c2) {
        Constant orBitwise_Constant_Constant_value = orBitwise_compute(c1, c2);
        return orBitwise_Constant_Constant_value;
    }

    private Constant orBitwise_compute(Constant c1, Constant c2) {  return Constant.create(c1.intValue() | c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 424
 @SuppressWarnings({"unchecked", "cast"})     public Constant questionColon(Constant cond, Constant c1, Constant c2) {
        Constant questionColon_Constant_Constant_Constant_value = questionColon_compute(cond, c1, c2);
        return questionColon_Constant_Constant_Constant_value;
    }

    private Constant questionColon_compute(Constant cond, Constant c1, Constant c2) {  return Constant.create(cond.booleanValue() ? c1.intValue() : c2.intValue());  }

    // Declared in ConstantExpression.jrag at line 528
 @SuppressWarnings({"unchecked", "cast"})     public boolean eqIsTrue(Expr left, Expr right) {
        boolean eqIsTrue_Expr_Expr_value = eqIsTrue_compute(left, right);
        return eqIsTrue_Expr_Expr_value;
    }

    private boolean eqIsTrue_compute(Expr left, Expr right) {  return left.constant().intValue() == right.constant().intValue();  }

    // Declared in ConstantExpression.jrag at line 536
 @SuppressWarnings({"unchecked", "cast"})     public boolean ltIsTrue(Expr left, Expr right) {
        boolean ltIsTrue_Expr_Expr_value = ltIsTrue_compute(left, right);
        return ltIsTrue_Expr_Expr_value;
    }

    private boolean ltIsTrue_compute(Expr left, Expr right) {  return left.constant().intValue() < right.constant().intValue();  }

    // Declared in ConstantExpression.jrag at line 542
 @SuppressWarnings({"unchecked", "cast"})     public boolean leIsTrue(Expr left, Expr right) {
        boolean leIsTrue_Expr_Expr_value = leIsTrue_compute(left, right);
        return leIsTrue_Expr_Expr_value;
    }

    private boolean leIsTrue_compute(Expr left, Expr right) {  return left.constant().intValue() <= right.constant().intValue();  }

    // Declared in NameCheck.jrag at line 423
 @SuppressWarnings({"unchecked", "cast"})     public boolean assignableToInt() {
        boolean assignableToInt_value = assignableToInt_compute();
        return assignableToInt_value;
    }

    private boolean assignableToInt_compute() {  return true;  }

    // Declared in TypeAnalysis.jrag at line 179
 @SuppressWarnings({"unchecked", "cast"})     public boolean isIntegralType() {
        boolean isIntegralType_value = isIntegralType_compute();
        return isIntegralType_value;
    }

    private boolean isIntegralType_compute() {  return true;  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
