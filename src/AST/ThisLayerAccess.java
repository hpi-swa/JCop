
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

   
   
// ------------------------------------------------------------
// fancy stuff
// ------------------------------------------------------------   

public class ThisLayerAccess extends Access implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ThisLayerAccess clone() throws CloneNotSupportedException {
        ThisLayerAccess node = (ThisLayerAccess)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ThisLayerAccess copy() {
      try {
          ThisLayerAccess node = (ThisLayerAccess)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ThisLayerAccess fullCopy() {
        ThisLayerAccess res = (ThisLayerAccess)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in auxiliary.jrag at line 10

	  
	  public void toString(StringBuffer s) {
	       s.append("thislayer");
	    }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 114

    public ThisLayerAccess() {
        super();


    }

    // Declared in jcop.ast at line 10


    // Declared in jcop.ast line 114
    public ThisLayerAccess(String p0) {
        setID(p0);
    }

    // Declared in jcop.ast at line 15


    // Declared in jcop.ast line 114
    public ThisLayerAccess(beaver.Symbol p0) {
        setID(p0);
    }

    // Declared in jcop.ast at line 19


  protected int numChildren() {
    return 0;
  }

    // Declared in jcop.ast at line 22

  public boolean mayHaveRewrite() { return true; }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 114
    private String tokenString_ID;

    // Declared in jcop.ast at line 3

    public void setID(String value) {
        tokenString_ID = value;
    }

    // Declared in jcop.ast at line 6

    public int IDstart;

    // Declared in jcop.ast at line 7

    public int IDend;

    // Declared in jcop.ast at line 8

    public void setID(beaver.Symbol symbol) {
        if(symbol.value != null && !(symbol.value instanceof String))
          throw new UnsupportedOperationException("setID is only valid for String lexemes");
        tokenString_ID = (String)symbol.value;
        IDstart = symbol.getStart();
        IDend = symbol.getEnd();
    }

    // Declared in jcop.ast at line 15

    public String getID() {
        return tokenString_ID != null ? tokenString_ID : "";
    }

public ASTNode rewriteTo() {
    // Declared in thislayer.jrag at line 3
        duringthislayer++;
        ASTNode result = rewriteRule0();
        duringthislayer--;
        return result;
}

    // Declared in thislayer.jrag at line 3
    private ASTNode rewriteRule0() {
{
    	if(jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled())
			return super.rewriteTo();
    	return new VarAccess(getID());
  }    }
}
