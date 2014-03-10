
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class ExplicitTypeNamePattern extends NamePattern implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ExplicitTypeNamePattern clone() throws CloneNotSupportedException {
        ExplicitTypeNamePattern node = (ExplicitTypeNamePattern)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ExplicitTypeNamePattern copy() {
      try {
          ExplicitTypeNamePattern node = (ExplicitTypeNamePattern)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ExplicitTypeNamePattern fullCopy() {
        ExplicitTypeNamePattern res = (ExplicitTypeNamePattern)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 25

    
	public void toString(StringBuffer s) {
    	getAccess().toString(s);
    }

    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 54

    public ExplicitTypeNamePattern() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 54
    public ExplicitTypeNamePattern(Access p0) {
        setChild(p0, 0);
    }

    // Declared in AspectJ.ast at line 14


  protected int numChildren() {
    return 1;
  }

    // Declared in AspectJ.ast at line 17

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 54
    public void setAccess(Access node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public Access getAccess() {
        return (Access)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public Access getAccessNoTransform() {
        return (Access)getChildNoTransform(0);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
