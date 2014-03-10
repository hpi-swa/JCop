
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class ArraytypeNamePattern extends NamePattern implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ArraytypeNamePattern clone() throws CloneNotSupportedException {
        ArraytypeNamePattern node = (ArraytypeNamePattern)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ArraytypeNamePattern copy() {
      try {
          ArraytypeNamePattern node = (ArraytypeNamePattern)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ArraytypeNamePattern fullCopy() {
        ArraytypeNamePattern res = (ArraytypeNamePattern)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 56

    public ArraytypeNamePattern() {
        super();

        setChild(new List(), 1);

    }

    // Declared in AspectJ.ast at line 11


    // Declared in AspectJ.ast line 56
    public ArraytypeNamePattern(NamePattern p0, List<Dims> p1) {
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
    // Declared in AspectJ.ast line 56
    public void setPattern(NamePattern node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public NamePattern getPattern() {
        return (NamePattern)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public NamePattern getPatternNoTransform() {
        return (NamePattern)getChildNoTransform(0);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 56
    public void setDimsList(List<Dims> list) {
        setChild(list, 1);
    }

    // Declared in AspectJ.ast at line 6


    private int getNumDims = 0;

    // Declared in AspectJ.ast at line 7

    public int getNumDims() {
        return getDimsList().getNumChild();
    }

    // Declared in AspectJ.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Dims getDims(int i) {
        return (Dims)getDimsList().getChild(i);
    }

    // Declared in AspectJ.ast at line 15


    public void addDims(Dims node) {
        List<Dims> list = getDimsList();
        list.addChild(node);
    }

    // Declared in AspectJ.ast at line 20


    public void setDims(Dims node, int i) {
        List<Dims> list = getDimsList();
        list.setChild(node, i);
    }

    // Declared in AspectJ.ast at line 24

    public List<Dims> getDimss() {
        return getDimsList();
    }

    // Declared in AspectJ.ast at line 27

    public List<Dims> getDimssNoTransform() {
        return getDimsListNoTransform();
    }

    // Declared in AspectJ.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Dims> getDimsList() {
        return (List<Dims>)getChild(1);
    }

    // Declared in AspectJ.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Dims> getDimsListNoTransform() {
        return (List<Dims>)getChildNoTransform(1);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
