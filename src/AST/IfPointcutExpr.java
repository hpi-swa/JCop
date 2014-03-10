
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class IfPointcutExpr extends PointcutExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public IfPointcutExpr clone() throws CloneNotSupportedException {
        IfPointcutExpr node = (IfPointcutExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public IfPointcutExpr copy() {
      try {
          IfPointcutExpr node = (IfPointcutExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public IfPointcutExpr fullCopy() {
        IfPointcutExpr res = (IfPointcutExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 97

    
    public void toString(StringBuffer s) {
		s.append("if(true)");
		// malte: evaluating the real expression causes exceptions
		// since the three has not been completely constructed at this time 		  
//		s.append("true");//getExprNoTransform().toString(s);
//		s.append(")");
	}

    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 28

    public IfPointcutExpr() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 28
    public IfPointcutExpr(Expr p0) {
        setChild(p0, 0);
    }

    // Declared in AspectJ.ast at line 14


  protected int numChildren() {
    return 1;
  }

    // Declared in AspectJ.ast at line 17

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 28
    public void setExpr(Expr node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public Expr getExpr() {
        return (Expr)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public Expr getExprNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
