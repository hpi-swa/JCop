
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;
 //::= <ID:String> ;
   
   

// ------------------------------------------------------------
// context type
// ------------------------------------------------------------ 


public class ContextDecl extends ClassDecl implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextDecl clone() throws CloneNotSupportedException {
        ContextDecl node = (ContextDecl)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextDecl copy() {
      try {
          ContextDecl node = (ContextDecl)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ContextDecl fullCopy() {
        ContextDecl res = (ContextDecl)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in context_declaration.jrag at line 4


  public void toString(StringBuffer s) {
     s.append("context ");
     s.append(getID());
     s.append(" { \n");
     indent++;
     getContextConstraintList().toString(s);
     for (BodyDecl bodyDecl : getBodyDecls()) {
        bodyDecl.toString(s);
     }
     indent--;
     s.append(indent() + "}\n");
  }

    // Declared in context_declaration.jrag at line 17


  public  List<DLALayerActivation> getLayerActivations() {
	  return getContextConstraint().
		getLayerActivationBlockNoTransform().
			getDLALayerActivationListNoTransform();
  }

    // Declared in context_declaration.jrag at line 23


  public  ContextConstraint getContextConstraint() {
	  return getContextConstraintListNoTransform().getChild(0);
  }

    // Declared in graphGeneration.jrag at line 140

  
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 69

    public ContextDecl() {
        super();

        setChild(new Opt(), 1);
        setChild(new List(), 2);
        setChild(new List(), 3);
        setChild(new List(), 4);

    }

    // Declared in jcop.ast at line 14


    // Declared in jcop.ast line 69
    public ContextDecl(Modifiers p0, Opt<Access> p1, List<Access> p2, List<BodyDecl> p3, String p4, List<ContextConstraint> p5) {
        setChild(p0, 0);
        setChild(p1, 1);
        setChild(p2, 2);
        setChild(p3, 3);
        setID(p4);
        setChild(p5, 4);
    }

    // Declared in jcop.ast at line 24


    // Declared in jcop.ast line 69
    public ContextDecl(Modifiers p0, Opt<Access> p1, List<Access> p2, List<BodyDecl> p3, beaver.Symbol p4, List<ContextConstraint> p5) {
        setChild(p0, 0);
        setChild(p1, 1);
        setChild(p2, 2);
        setChild(p3, 3);
        setID(p4);
        setChild(p5, 4);
    }

    // Declared in jcop.ast at line 33


  protected int numChildren() {
    return 5;
  }

    // Declared in jcop.ast at line 36

  public boolean mayHaveRewrite() { return true; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 63
    public void setModifiers(Modifiers node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Modifiers getModifiers() {
        return (Modifiers)getChild(0);
    }

    // Declared in java.ast at line 9


    public Modifiers getModifiersNoTransform() {
        return (Modifiers)getChildNoTransform(0);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 63
    public void setSuperClassAccessOpt(Opt<Access> opt) {
        setChild(opt, 1);
    }

    // Declared in java.ast at line 6


    public boolean hasSuperClassAccess() {
        return getSuperClassAccessOpt().getNumChild() != 0;
    }

    // Declared in java.ast at line 10


     @SuppressWarnings({"unchecked", "cast"})  public Access getSuperClassAccess() {
        return (Access)getSuperClassAccessOpt().getChild(0);
    }

    // Declared in java.ast at line 14


    public void setSuperClassAccess(Access node) {
        getSuperClassAccessOpt().setChild(node, 0);
    }

    // Declared in java.ast at line 17

     @SuppressWarnings({"unchecked", "cast"})  public Opt<Access> getSuperClassAccessOpt() {
        return (Opt<Access>)getChild(1);
    }

    // Declared in java.ast at line 21


     @SuppressWarnings({"unchecked", "cast"})  public Opt<Access> getSuperClassAccessOptNoTransform() {
        return (Opt<Access>)getChildNoTransform(1);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 63
    public void setImplementsList(List<Access> list) {
        setChild(list, 2);
    }

    // Declared in java.ast at line 6


    private int getNumImplements = 0;

    // Declared in java.ast at line 7

    public int getNumImplements() {
        return getImplementsList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Access getImplements(int i) {
        return (Access)getImplementsList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addImplements(Access node) {
        List<Access> list = getImplementsList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setImplements(Access node, int i) {
        List<Access> list = getImplementsList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<Access> getImplementss() {
        return getImplementsList();
    }

    // Declared in java.ast at line 27

    public List<Access> getImplementssNoTransform() {
        return getImplementsListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Access> getImplementsList() {
        return (List<Access>)getChild(2);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Access> getImplementsListNoTransform() {
        return (List<Access>)getChildNoTransform(2);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 63
    public void setBodyDeclList(List<BodyDecl> list) {
        setChild(list, 3);
    }

    // Declared in java.ast at line 6


    private int getNumBodyDecl = 0;

    // Declared in java.ast at line 7

    public int getNumBodyDecl() {
        return getBodyDeclList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public BodyDecl getBodyDecl(int i) {
        return (BodyDecl)getBodyDeclList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addBodyDecl(BodyDecl node) {
        List<BodyDecl> list = getBodyDeclList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setBodyDecl(BodyDecl node, int i) {
        List<BodyDecl> list = getBodyDeclList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<BodyDecl> getBodyDecls() {
        return getBodyDeclList();
    }

    // Declared in java.ast at line 27

    public List<BodyDecl> getBodyDeclsNoTransform() {
        return getBodyDeclListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclList() {
        return (List<BodyDecl>)getChild(3);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclListNoTransform() {
        return (List<BodyDecl>)getChildNoTransform(3);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 69
    public void setContextConstraintList(List<ContextConstraint> list) {
        setChild(list, 4);
    }

    // Declared in jcop.ast at line 6


    private int getNumContextConstraint = 0;

    // Declared in jcop.ast at line 7

    public int getNumContextConstraint() {
        return getContextConstraintList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public ContextConstraint getContextConstraint(int i) {
        return (ContextConstraint)getContextConstraintList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addContextConstraint(ContextConstraint node) {
        List<ContextConstraint> list = getContextConstraintList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setContextConstraint(ContextConstraint node, int i) {
        List<ContextConstraint> list = getContextConstraintList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<ContextConstraint> getContextConstraints() {
        return getContextConstraintList();
    }

    // Declared in jcop.ast at line 27

    public List<ContextConstraint> getContextConstraintsNoTransform() {
        return getContextConstraintListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<ContextConstraint> getContextConstraintList() {
        return (List<ContextConstraint>)getChild(4);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<ContextConstraint> getContextConstraintListNoTransform() {
        return (List<ContextConstraint>)getChildNoTransform(4);
    }

    // Declared in context_declaration.jrag at line 2
 @SuppressWarnings({"unchecked", "cast"})     public boolean isPublic() {
        boolean isPublic_value = isPublic_compute();
        return isPublic_value;
    }

    private boolean isPublic_compute() {  return true;  }

public ASTNode rewriteTo() {
    // Declared in context_declaration.jrag at line 28
        duringcontext_declaration++;
        ASTNode result = rewriteRule0();
        duringcontext_declaration--;
        return result;
}

    // Declared in context_declaration.jrag at line 28
    private ASTNode rewriteRule0() {
{
//    	jcop.jcopaspect.AspectProvider.getInstance().
//    		add(new jcop.jcopaspect.ContextAspect(this));
    	if(jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled())
			return super.rewriteTo();
    	return new jcop.transformation.ContextTransformer(this).errorCheckAndTransform(compilationUnit());
  }    }
}
