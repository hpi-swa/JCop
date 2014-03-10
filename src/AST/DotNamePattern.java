
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class DotNamePattern extends NamePattern implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public DotNamePattern clone() throws CloneNotSupportedException {
        DotNamePattern node = (DotNamePattern)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public DotNamePattern copy() {
      try {
          DotNamePattern node = (DotNamePattern)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public DotNamePattern fullCopy() {
        DotNamePattern res = (DotNamePattern)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 53

	
	public void toString(StringBuffer s) {
		getLhs().toString(s);
		s.append(".");
		getRhs().toString(s);	
	}

    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 50

    public DotNamePattern() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 50
    public DotNamePattern(NamePattern p0, SimpleNamePattern p1) {
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
    // Declared in AspectJ.ast line 50
    public void setLhs(NamePattern node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public NamePattern getLhs() {
        return (NamePattern)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public NamePattern getLhsNoTransform() {
        return (NamePattern)getChildNoTransform(0);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 50
    public void setRhs(SimpleNamePattern node) {
        setChild(node, 1);
    }

    // Declared in AspectJ.ast at line 5

    public SimpleNamePattern getRhs() {
        return (SimpleNamePattern)getChild(1);
    }

    // Declared in AspectJ.ast at line 9


    public SimpleNamePattern getRhsNoTransform() {
        return (SimpleNamePattern)getChildNoTransform(1);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
