
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class ObjectSpecificLayerActivation extends Stmt implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ObjectSpecificLayerActivation clone() throws CloneNotSupportedException {
        ObjectSpecificLayerActivation node = (ObjectSpecificLayerActivation)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ObjectSpecificLayerActivation copy() {
      try {
          ObjectSpecificLayerActivation node = (ObjectSpecificLayerActivation)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ObjectSpecificLayerActivation fullCopy() {
        ObjectSpecificLayerActivation res = (ObjectSpecificLayerActivation)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in auxiliary.jrag at line 38

	  
	   public void toString(StringBuffer s) {
		      getTarget().toString(s);   
			  s.append(" with (");
			  for(Expr arg : getArgs()) {
			    arg.toString(s);
			    s.append(", ");
			  }
			  if (getArgs().numChildren() > 0)
				  s.delete(s.length()-2, s.length());
			  s.append(")");		
			}

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 105

    public ObjectSpecificLayerActivation() {
        super();

        setChild(new List(), 1);

    }

    // Declared in jcop.ast at line 11


    // Declared in jcop.ast line 105
    public ObjectSpecificLayerActivation(Expr p0, List<Expr> p1, boolean p2) {
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
    // Declared in jcop.ast line 105
    public void setTarget(Expr node) {
        setChild(node, 0);
    }

    // Declared in jcop.ast at line 5

    public Expr getTarget() {
        return (Expr)getChild(0);
    }

    // Declared in jcop.ast at line 9


    public Expr getTargetNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 105
    public void setArgList(List<Expr> list) {
        setChild(list, 1);
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
        return (List<Expr>)getChild(1);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Expr> getArgListNoTransform() {
        return (List<Expr>)getChildNoTransform(1);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 105
    private boolean tokenboolean_Activation;

    // Declared in jcop.ast at line 3

    public void setActivation(boolean value) {
        tokenboolean_Activation = value;
    }

    // Declared in jcop.ast at line 6

    public boolean getActivation() {
        return tokenboolean_Activation;
    }

public ASTNode rewriteTo() {
    // Declared in activation_object_specific.jrag at line 2
        duringactivation_object_specific++;
        ASTNode result = rewriteRule0();
        duringactivation_object_specific--;
        return result;
}

    // Declared in activation_object_specific.jrag at line 2
    private ASTNode rewriteRule0() {
{
	  	if (Program.hasOption(jcop.Globals.CompilerOps.xmlOutlinePath)) 
			return super.rewriteTo();
		else
			return new jcop.transformation.ImplicitLayerActivationTransformer(this).errorCheckAndTransform(hostType().compilationUnit());
     }    }
}
