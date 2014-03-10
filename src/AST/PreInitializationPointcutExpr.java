
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class PreInitializationPointcutExpr extends PointcutExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public PreInitializationPointcutExpr clone() throws CloneNotSupportedException {
        PreInitializationPointcutExpr node = (PreInitializationPointcutExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public PreInitializationPointcutExpr copy() {
      try {
          PreInitializationPointcutExpr node = (PreInitializationPointcutExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public PreInitializationPointcutExpr fullCopy() {
        PreInitializationPointcutExpr res = (PreInitializationPointcutExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 14

    public PreInitializationPointcutExpr() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 14
    public PreInitializationPointcutExpr(ConstructorPattern p0) {
        setChild(p0, 0);
    }

    // Declared in AspectJ.ast at line 14


  protected int numChildren() {
    return 1;
  }

    // Declared in AspectJ.ast at line 17

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 14
    public void setPattern(ConstructorPattern node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public ConstructorPattern getPattern() {
        return (ConstructorPattern)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public ConstructorPattern getPatternNoTransform() {
        return (ConstructorPattern)getChildNoTransform(0);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
