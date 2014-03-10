
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class ContextConstraint extends ASTNode<ASTNode> implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextConstraint clone() throws CloneNotSupportedException {
        ContextConstraint node = (ContextConstraint)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextConstraint copy() {
      try {
          ContextConstraint node = (ContextConstraint)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextConstraint fullCopy() {
        ContextConstraint res = (ContextConstraint)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 174

  
   public void toString(StringBuffer s) {
	  getConstraintDefinition().toString(s);	
	  getLayerActivationBlock().toString(s);	
   }

    // Declared in graphGeneration.jrag at line 144

  
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 84

    public ContextConstraint() {
        super();


    }

    // Declared in jcop.ast at line 10


    // Declared in jcop.ast line 84
    public ContextConstraint(PointcutExpr p0, LayerActivationBlock p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    // Declared in jcop.ast at line 15


  protected int numChildren() {
    return 2;
  }

    // Declared in jcop.ast at line 18

  public boolean mayHaveRewrite() { return false; }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 84
    public void setConstraintDefinition(PointcutExpr node) {
        setChild(node, 0);
    }

    // Declared in jcop.ast at line 5

    public PointcutExpr getConstraintDefinition() {
        return (PointcutExpr)getChild(0);
    }

    // Declared in jcop.ast at line 9


    public PointcutExpr getConstraintDefinitionNoTransform() {
        return (PointcutExpr)getChildNoTransform(0);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 84
    public void setLayerActivationBlock(LayerActivationBlock node) {
        setChild(node, 1);
    }

    // Declared in jcop.ast at line 5

    public LayerActivationBlock getLayerActivationBlock() {
        return (LayerActivationBlock)getChild(1);
    }

    // Declared in jcop.ast at line 9


    public LayerActivationBlock getLayerActivationBlockNoTransform() {
        return (LayerActivationBlock)getChildNoTransform(1);
    }

    // Declared in declarative_layer_activation.jrag at line 6
    public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
        if(caller == getLayerActivationBlockNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_reachable(this, caller);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
