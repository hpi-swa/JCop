
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

 

public class LayerActivationBlock extends Block implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerActivationBlock clone() throws CloneNotSupportedException {
        LayerActivationBlock node = (LayerActivationBlock)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerActivationBlock copy() {
      try {
          LayerActivationBlock node = (LayerActivationBlock)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerActivationBlock fullCopy() {
        LayerActivationBlock res = (LayerActivationBlock)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 149

	 
 /* rewrite DeclarativeLayerActivation {
     to EmptyStmt {
	 jcop.JCopAspect.getInstance().addLayerActivationStatement(this);
	 return new EmptyStmt(); 
    }
  } */     		 


   public void toString(StringBuffer s) {
      s.append("{ \n");
	  indent++;
	  for (DLALayerActivation dla : getDLALayerActivations()) {
	     dla.toString(s);
	  }
	  indent--;
	  s.append(indent() + "} \n");
   }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 88

    public LayerActivationBlock() {
        super();

        setChild(new List(), 0);
        setChild(new List(), 1);

    }

    // Declared in jcop.ast at line 12


    // Declared in jcop.ast line 88
    public LayerActivationBlock(List<Stmt> p0, List<DLALayerActivation> p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    // Declared in jcop.ast at line 17


  protected int numChildren() {
    return 2;
  }

    // Declared in jcop.ast at line 20

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 200
    public void setStmtList(List<Stmt> list) {
        setChild(list, 0);
    }

    // Declared in java.ast at line 6


    private int getNumStmt = 0;

    // Declared in java.ast at line 7

    public int getNumStmt() {
        return getStmtList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Stmt getStmt(int i) {
        return (Stmt)getStmtList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addStmt(Stmt node) {
        List<Stmt> list = getStmtList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setStmt(Stmt node, int i) {
        List<Stmt> list = getStmtList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<Stmt> getStmts() {
        return getStmtList();
    }

    // Declared in java.ast at line 27

    public List<Stmt> getStmtsNoTransform() {
        return getStmtListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Stmt> getStmtList() {
        return (List<Stmt>)getChild(0);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Stmt> getStmtListNoTransform() {
        return (List<Stmt>)getChildNoTransform(0);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 88
    public void setDLALayerActivationList(List<DLALayerActivation> list) {
        setChild(list, 1);
    }

    // Declared in jcop.ast at line 6


    private int getNumDLALayerActivation = 0;

    // Declared in jcop.ast at line 7

    public int getNumDLALayerActivation() {
        return getDLALayerActivationList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public DLALayerActivation getDLALayerActivation(int i) {
        return (DLALayerActivation)getDLALayerActivationList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addDLALayerActivation(DLALayerActivation node) {
        List<DLALayerActivation> list = getDLALayerActivationList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setDLALayerActivation(DLALayerActivation node, int i) {
        List<DLALayerActivation> list = getDLALayerActivationList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<DLALayerActivation> getDLALayerActivations() {
        return getDLALayerActivationList();
    }

    // Declared in jcop.ast at line 27

    public List<DLALayerActivation> getDLALayerActivationsNoTransform() {
        return getDLALayerActivationListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<DLALayerActivation> getDLALayerActivationList() {
        return (List<DLALayerActivation>)getChild(1);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<DLALayerActivation> getDLALayerActivationListNoTransform() {
        return (List<DLALayerActivation>)getChildNoTransform(1);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
