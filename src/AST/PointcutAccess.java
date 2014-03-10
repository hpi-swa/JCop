
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class PointcutAccess extends Access implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public PointcutAccess clone() throws CloneNotSupportedException {
        PointcutAccess node = (PointcutAccess)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public PointcutAccess copy() {
      try {
          PointcutAccess node = (PointcutAccess)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public PointcutAccess fullCopy() {
        PointcutAccess res = (PointcutAccess)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 1

    public PointcutAccess() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 1
    public PointcutAccess(String p0) {
        setID(p0);
    }

    // Declared in AspectJ.ast at line 15


    // Declared in AspectJ.ast line 1
    public PointcutAccess(beaver.Symbol p0) {
        setID(p0);
    }

    // Declared in AspectJ.ast at line 19


  protected int numChildren() {
    return 0;
  }

    // Declared in AspectJ.ast at line 22

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 1
    private String tokenString_ID;

    // Declared in AspectJ.ast at line 3

    public void setID(String value) {
        tokenString_ID = value;
    }

    // Declared in AspectJ.ast at line 6

    public int IDstart;

    // Declared in AspectJ.ast at line 7

    public int IDend;

    // Declared in AspectJ.ast at line 8

    public void setID(beaver.Symbol symbol) {
        if(symbol.value != null && !(symbol.value instanceof String))
          throw new UnsupportedOperationException("setID is only valid for String lexemes");
        tokenString_ID = (String)symbol.value;
        IDstart = symbol.getStart();
        IDend = symbol.getEnd();
    }

    // Declared in AspectJ.ast at line 15

    public String getID() {
        return tokenString_ID != null ? tokenString_ID : "";
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
