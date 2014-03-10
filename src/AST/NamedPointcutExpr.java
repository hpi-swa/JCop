
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class NamedPointcutExpr extends PointcutExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public NamedPointcutExpr clone() throws CloneNotSupportedException {
        NamedPointcutExpr node = (NamedPointcutExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public NamedPointcutExpr copy() {
      try {
          NamedPointcutExpr node = (NamedPointcutExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public NamedPointcutExpr fullCopy() {
        NamedPointcutExpr res = (NamedPointcutExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 34

    public NamedPointcutExpr() {
        super();

        setChild(new List(), 1);

    }

    // Declared in AspectJ.ast at line 11


    // Declared in AspectJ.ast line 34
    public NamedPointcutExpr(Access p0, List<BindingPattern> p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    // Declared in AspectJ.ast at line 16


  protected int numChildren() {
    return 2;
  }

    // Declared in AspectJ.ast at line 19

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 34
    public void setName(Access node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public Access getName() {
        return (Access)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public Access getNameNoTransform() {
        return (Access)getChildNoTransform(0);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 34
    public void setPatternList(List<BindingPattern> list) {
        setChild(list, 1);
    }

    // Declared in AspectJ.ast at line 6


    private int getNumPattern = 0;

    // Declared in AspectJ.ast at line 7

    public int getNumPattern() {
        return getPatternList().getNumChild();
    }

    // Declared in AspectJ.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public BindingPattern getPattern(int i) {
        return (BindingPattern)getPatternList().getChild(i);
    }

    // Declared in AspectJ.ast at line 15


    public void addPattern(BindingPattern node) {
        List<BindingPattern> list = getPatternList();
        list.addChild(node);
    }

    // Declared in AspectJ.ast at line 20


    public void setPattern(BindingPattern node, int i) {
        List<BindingPattern> list = getPatternList();
        list.setChild(node, i);
    }

    // Declared in AspectJ.ast at line 24

    public List<BindingPattern> getPatterns() {
        return getPatternList();
    }

    // Declared in AspectJ.ast at line 27

    public List<BindingPattern> getPatternsNoTransform() {
        return getPatternListNoTransform();
    }

    // Declared in AspectJ.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<BindingPattern> getPatternList() {
        return (List<BindingPattern>)getChild(1);
    }

    // Declared in AspectJ.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<BindingPattern> getPatternListNoTransform() {
        return (List<BindingPattern>)getChildNoTransform(1);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
