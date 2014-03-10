
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


   
// ------------------------------------------------------------
// layer activation
// ------------------------------------------------------------


public class LayerActivation extends Stmt implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerActivation clone() throws CloneNotSupportedException {
        LayerActivation node = (LayerActivation)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerActivation copy() {
      try {
          LayerActivation node = (LayerActivation)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerActivation fullCopy() {
        LayerActivation res = (LayerActivation)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in graphGeneration.jrag at line 156
    
  
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in layer_activation.jrag at line 4

  
  	public void toString(StringBuffer s) {
		if(getActivation())
			s.append("with (");
		else
			s.append("without (");
		for (Expr arg : getArgs()) {
			arg.toString(s);
			s.append(", ");
		}
		if (getArgs().numChildren() > 0)
			s.delete(s.length()-2, s.length());
		s.append(") ");		
		getBlock().toString(s);		
	}

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 100

    public LayerActivation() {
        super();

        setChild(new List(), 0);

    }

    // Declared in jcop.ast at line 11


    // Declared in jcop.ast line 100
    public LayerActivation(List<Expr> p0, Block p1, boolean p2) {
        setChild(p0, 0);
        setChild(p1, 1);
        setActivation(p2);
    }

    // Declared in jcop.ast at line 17


  protected int numChildren() {
    return 2;
  }

    // Declared in jcop.ast at line 20

  public boolean mayHaveRewrite() { return true; }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 100
    public void setArgList(List<Expr> list) {
        setChild(list, 0);
    }

    // Declared in jcop.ast at line 6


    private int getNumArg = 0;

    // Declared in jcop.ast at line 7

    public int getNumArg() {
        return getArgList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Expr getArg(int i) {
        return (Expr)getArgList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addArg(Expr node) {
        List<Expr> list = getArgList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setArg(Expr node, int i) {
        List<Expr> list = getArgList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<Expr> getArgs() {
        return getArgList();
    }

    // Declared in jcop.ast at line 27

    public List<Expr> getArgsNoTransform() {
        return getArgListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Expr> getArgList() {
        return (List<Expr>)getChild(0);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Expr> getArgListNoTransform() {
        return (List<Expr>)getChildNoTransform(0);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 100
    public void setBlock(Block node) {
        setChild(node, 1);
    }

    // Declared in jcop.ast at line 5

    public Block getBlock() {
        return (Block)getChild(1);
    }

    // Declared in jcop.ast at line 9


    public Block getBlockNoTransform() {
        return (Block)getChildNoTransform(1);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 100
    private boolean tokenboolean_Activation;

    // Declared in jcop.ast at line 3

    public void setActivation(boolean value) {
        tokenboolean_Activation = value;
    }

    // Declared in jcop.ast at line 6

    public boolean getActivation() {
        return tokenboolean_Activation;
    }

    // Declared in layer_activation.jrag at line 2
 @SuppressWarnings({"unchecked", "cast"})     public boolean isMethodParameter() {
        boolean isMethodParameter_value = isMethodParameter_compute();
        return isMethodParameter_value;
    }

    private boolean isMethodParameter_compute() {  return false;  }

public ASTNode rewriteTo() {
    // Declared in layer_activation.jrag at line 19
        duringlayer_activation++;
        ASTNode result = rewriteRule0();
        duringlayer_activation--;
        return result;
}

    // Declared in layer_activation.jrag at line 19
    private ASTNode rewriteRule0() {
{
	   if(jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled())
			return super.rewriteTo();
		else
			return new jcop.transformation.LayerActivationTransformer(this).errorCheckAndTransform(hostType().compilationUnit());		
    }    }
}
