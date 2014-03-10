
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class BooleanLiteral extends Literal implements Cloneable {
    public void flushCache() {
        super.flushCache();
        constant_computed = false;
        constant_value = null;
        type_computed = false;
        type_value = null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public BooleanLiteral clone() throws CloneNotSupportedException {
        BooleanLiteral node = (BooleanLiteral)super.clone();
        node.constant_computed = false;
        node.constant_value = null;
        node.type_computed = false;
        node.type_value = null;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public BooleanLiteral copy() {
      try {
          BooleanLiteral node = (BooleanLiteral)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public BooleanLiteral fullCopy() {
        BooleanLiteral res = (BooleanLiteral)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in NodeConstructors.jrag at line 52


  public BooleanLiteral(boolean b) {
    this(b ? "true" : "false");
  }

    // Declared in CodeGeneration.jrag at line 543


  public static void push(CodeGeneration gen, boolean value) {
    gen.emit(value ? Bytecode.ICONST_1 : Bytecode.ICONST_0);
  }

    // Declared in CodeGeneration.jrag at line 587


  public void emitPushConstant(CodeGeneration gen) {
    BooleanLiteral.push(gen, constant().booleanValue());
  }

    // Declared in graphGeneration.jrag at line 127
  
  
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 129

    public BooleanLiteral() {
        super();


    }

    // Declared in java.ast at line 10


    // Declared in java.ast line 129
    public BooleanLiteral(String p0) {
        setLITERAL(p0);
    }

    // Declared in java.ast at line 15


    // Declared in java.ast line 129
    public BooleanLiteral(beaver.Symbol p0) {
        setLITERAL(p0);
    }

    // Declared in java.ast at line 19


  protected int numChildren() {
    return 0;
  }

    // Declared in java.ast at line 22

  public boolean mayHaveRewrite() { return false; }

    // Declared in ConstantExpression.jrag at line 286
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

    private Constant constant_compute() {  return Constant.create(Boolean.valueOf(getLITERAL()).booleanValue());  }

    protected boolean type_computed = false;
    protected TypeDecl type_value;
    // Declared in TypeAnalysis.jrag at line 305
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

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
