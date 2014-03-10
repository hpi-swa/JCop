
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class SimpleNamePattern extends NamePattern implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public SimpleNamePattern clone() throws CloneNotSupportedException {
        SimpleNamePattern node = (SimpleNamePattern)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public SimpleNamePattern copy() {
      try {
          SimpleNamePattern node = (SimpleNamePattern)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public SimpleNamePattern fullCopy() {
        SimpleNamePattern res = (SimpleNamePattern)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 33

	
	public void toString(StringBuffer s) {
		s.append(getPattern());	
	}

    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 49

    public SimpleNamePattern() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 49
    public SimpleNamePattern(String p0) {
        setPattern(p0);
    }

    // Declared in AspectJ.ast at line 15


    // Declared in AspectJ.ast line 49
    public SimpleNamePattern(beaver.Symbol p0) {
        setPattern(p0);
    }

    // Declared in AspectJ.ast at line 19


  protected int numChildren() {
    return 0;
  }

    // Declared in AspectJ.ast at line 22

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 49
    private String tokenString_Pattern;

    // Declared in AspectJ.ast at line 3

    public void setPattern(String value) {
        tokenString_Pattern = value;
    }

    // Declared in AspectJ.ast at line 6

    public int Patternstart;

    // Declared in AspectJ.ast at line 7

    public int Patternend;

    // Declared in AspectJ.ast at line 8

    public void setPattern(beaver.Symbol symbol) {
        if(symbol.value != null && !(symbol.value instanceof String))
          throw new UnsupportedOperationException("setPattern is only valid for String lexemes");
        tokenString_Pattern = (String)symbol.value;
        Patternstart = symbol.getStart();
        Patternend = symbol.getEnd();
    }

    // Declared in AspectJ.ast at line 15

    public String getPattern() {
        return tokenString_Pattern != null ? tokenString_Pattern : "";
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
