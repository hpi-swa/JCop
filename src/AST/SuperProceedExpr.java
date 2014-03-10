
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;
 
   

public class SuperProceedExpr extends ProceedExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public SuperProceedExpr clone() throws CloneNotSupportedException {
        SuperProceedExpr node = (SuperProceedExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public SuperProceedExpr copy() {
      try {
          SuperProceedExpr node = (SuperProceedExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public SuperProceedExpr fullCopy() {
        SuperProceedExpr res = (SuperProceedExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 55

    public SuperProceedExpr() {
        super();

        setChild(new List(), 0);

    }

    // Declared in jcop.ast at line 11


    // Declared in jcop.ast line 55
    public SuperProceedExpr(List<Expr> p0) {
        setChild(p0, 0);
    }

    // Declared in jcop.ast at line 15


  protected int numChildren() {
    return 1;
  }

    // Declared in jcop.ast at line 18

  public boolean mayHaveRewrite() { return true; }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 55
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

public ASTNode rewriteTo() {
    // Declared in thislayer.jrag at line 11
        duringthislayer++;
        ASTNode result = rewriteRule0();
        duringthislayer--;
        return result;
}

    // Declared in thislayer.jrag at line 11
    private ASTNode rewriteRule0() {
{
	    	if(jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled())
				return super.rewriteTo();
	    	return new jcop.transformation.SuperProceedTransformer(this).errorCheckAndTransform(hostType().compilationUnit());
	  }    }
}
