
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class AndPointcutExpr extends BinaryPointcutExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public AndPointcutExpr clone() throws CloneNotSupportedException {
        AndPointcutExpr node = (AndPointcutExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public AndPointcutExpr copy() {
      try {
          AndPointcutExpr node = (AndPointcutExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public AndPointcutExpr fullCopy() {
        AndPointcutExpr res = (AndPointcutExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 37

	
	public void toString(StringBuffer s) {
		s.append("(");
		getLhs().toString(s);
		s.append(" && ");
		getRhs().toString(s);
		s.append(")");	
	}

    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 6

    public AndPointcutExpr() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 6
    public AndPointcutExpr(PointcutExpr p0, PointcutExpr p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    // Declared in AspectJ.ast at line 15


  protected int numChildren() {
    return 2;
  }

    // Declared in AspectJ.ast at line 18

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 4
    public void setLhs(PointcutExpr node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public PointcutExpr getLhs() {
        return (PointcutExpr)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public PointcutExpr getLhsNoTransform() {
        return (PointcutExpr)getChildNoTransform(0);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 4
    public void setRhs(PointcutExpr node) {
        setChild(node, 1);
    }

    // Declared in AspectJ.ast at line 5

    public PointcutExpr getRhs() {
        return (PointcutExpr)getChild(1);
    }

    // Declared in AspectJ.ast at line 9


    public PointcutExpr getRhsNoTransform() {
        return (PointcutExpr)getChildNoTransform(1);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
