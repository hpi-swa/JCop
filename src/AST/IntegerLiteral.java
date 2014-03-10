
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class IntegerLiteral extends Literal implements Cloneable {
    public void flushCache() {
        super.flushCache();
        constant_computed = false;
        constant_value = null;
        type_computed = false;
        type_value = null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public IntegerLiteral clone() throws CloneNotSupportedException {
        IntegerLiteral node = (IntegerLiteral)super.clone();
        node.constant_computed = false;
        node.constant_value = null;
        node.type_computed = false;
        node.type_value = null;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public IntegerLiteral copy() {
      try {
          IntegerLiteral node = (IntegerLiteral)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public IntegerLiteral fullCopy() {
        IntegerLiteral res = (IntegerLiteral)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in NodeConstructors.jrag at line 48


  public IntegerLiteral(int i) {
    this(Integer.toString(i));
  }

    // Declared in TypeCheck.jrag at line 570


 public void typeCheck() {
   if(constant().error)
     error("The value of an int literal must be a decimal value in the range -2147483648..2147483647 or a hexadecimal or octal literal that fits in 32 bits.");

 }

    // Declared in CodeGeneration.jrag at line 461


  /*************************************************************
   * Emit methods
   *************************************************************/

  // push constants

  public static void push(CodeGeneration gen, int value) {
    switch(value) {
      case -1:
        gen.emit(Bytecode.ICONST_M1);
        break;
      case 0:
        gen.emit(Bytecode.ICONST_0);
        break;
      case 1:
        gen.emit(Bytecode.ICONST_1);
        break;
      case 2:
        gen.emit(Bytecode.ICONST_2);
        break;
      case 3:
        gen.emit(Bytecode.ICONST_3);
        break;
      case 4:
        gen.emit(Bytecode.ICONST_4);
        break;
      case 5:
        gen.emit(Bytecode.ICONST_5);
        break;
      default:
        if(value >= -128 && value <= 127) {
          gen.emit(Bytecode.BIPUSH).add(value);
        }
        else if(value >= -32768 && value <= 32767) {
          gen.emit(Bytecode.SIPUSH).add2(value);
        }
        else {
          int index = gen.constantPool().addConstant(value);
          if(index < 256)
            gen.emit(Bytecode.LDC).add(index);
          else 
            gen.emit(Bytecode.LDC_W).add2(index);
        }
    }
  }

    // Declared in CodeGeneration.jrag at line 559


  public void emitPushConstant(CodeGeneration gen) {
    type().emitPushConstant(gen, constant().intValue());
  }

    // Declared in graphGeneration.jrag at line 123

  
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 125

    public IntegerLiteral() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 125
    public IntegerLiteral(String p0) {
        setLITERAL(p0);
    }

    // Declared in java.ast at line 15


    // Declared in java.ast line 125
    public IntegerLiteral(beaver.Symbol p0) {
        setLITERAL(p0);
    }

    // Declared in java.ast at line 19


  protected int numChildren() {
    return 0;
  }

    // Declared in java.ast at line 22

  public boolean mayHaveRewrite() { return false; }

    // Declared in ConstantExpression.jrag at line 220
 @SuppressWarnings({"unchecked", "cast"})     public boolean isHex() {
        boolean isHex_value = isHex_compute();
        return isHex_value;
    }

    private boolean isHex_compute() {  return getLITERAL().toLowerCase().startsWith("0x");  }

    // Declared in ConstantExpression.jrag at line 221
 @SuppressWarnings({"unchecked", "cast"})     public boolean isOctal() {
        boolean isOctal_value = isOctal_compute();
        return isOctal_value;
    }

    private boolean isOctal_compute() {  return getLITERAL().startsWith("0");  }

    // Declared in ConstantExpression.jrag at line 222
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDecimal() {
        boolean isDecimal_value = isDecimal_compute();
        return isDecimal_value;
    }

    private boolean isDecimal_compute() {  return !isHex() && !isOctal();  }

    // Declared in ConstantExpression.jrag at line 229
 @SuppressWarnings({"unchecked", "cast"})     public boolean isPositive() {
        boolean isPositive_value = isPositive_compute();
        return isPositive_value;
    }

    private boolean isPositive_compute() {  return !getLITERAL().startsWith("-");  }

    // Declared in ConstantExpression.jrag at line 242
 @SuppressWarnings({"unchecked", "cast"})     public Constant constant() {
        if(constant_computed)
            return constant_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        constant_value = constant_compute();
        if(isFinal && num == boundariesCrossed)
            constant_computed = true;
        return constant_value;
    }

    private Constant constant_compute() {
    long l = 0;
    try {
      l = Literal.parseLong(getLITERAL());
    } catch (NumberFormatException e) {
    }
    Constant c = Constant.create((int)l);
    if(isDecimal() && l != (int)l)
      c.error = true;
    if(isOctal() && l > 037777777777L)
      c.error = true;
    if(isHex() && l > 0xffffffffL)
      c.error = true;
    return c;
  }

    protected boolean type_computed = false;
    protected TypeDecl type_value;
    // Declared in TypeAnalysis.jrag at line 301
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

    private TypeDecl type_compute() {  return typeInt();  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
