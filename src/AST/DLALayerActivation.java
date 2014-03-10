
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

   

public class DLALayerActivation extends ASTNode<ASTNode> implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public DLALayerActivation clone() throws CloneNotSupportedException {
        DLALayerActivation node = (DLALayerActivation)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public DLALayerActivation copy() {
      try {
          DLALayerActivation node = (DLALayerActivation)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public DLALayerActivation fullCopy() {
        DLALayerActivation res = (DLALayerActivation)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 159


   public void toString(StringBuffer s) {
	  if (getActivation())
	     s.append("with");
	  else
		 s.append("without");
	  s.append(" (");
	  for (Expr arg : getArgs()) {
		s.append(arg);
		s.append(", ");	
	  }
	  if(getNumArg() > 0)
		s.deleteCharAt(s.length()-1);
	  s.append(") ;");
   }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 91

    public DLALayerActivation() {
        super();

        setChild(new List(), 0);

    }

    // Declared in jcop.ast at line 11


    // Declared in jcop.ast line 91
    public DLALayerActivation(List<Expr> p0, boolean p1) {
        setChild(p0, 0);
        setActivation(p1);
    }

    // Declared in jcop.ast at line 16


  protected int numChildren() {
    return 1;
  }

    // Declared in jcop.ast at line 19

  public boolean mayHaveRewrite() { return false; }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 91
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
    // Declared in jcop.ast line 91
    private boolean tokenboolean_Activation;

    // Declared in jcop.ast at line 3

    public void setActivation(boolean value) {
        tokenboolean_Activation = value;
    }

    // Declared in jcop.ast at line 6

    public boolean getActivation() {
        return tokenboolean_Activation;
    }

    // Declared in declarative_layer_activation.jrag at line 11
    public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
        if(caller == getArgListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return NameType.EXPRESSION_NAME;
        }
        return getParent().Define_NameType_nameType(this, caller);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
