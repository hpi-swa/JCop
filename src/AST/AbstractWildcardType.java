
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public abstract class AbstractWildcardType extends TypeDecl implements Cloneable {
    public void flushCache() {
        super.flushCache();
        needsSignatureAttribute_computed = false;
    }
     @SuppressWarnings({"unchecked", "cast"})  public AbstractWildcardType clone() throws CloneNotSupportedException {
        AbstractWildcardType node = (AbstractWildcardType)super.clone();
        node.needsSignatureAttribute_computed = false;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
    // Declared in Generics.ast at line 3
    // Declared in Generics.ast line 22

    public AbstractWildcardType() {
        super();

        setChild(new List(), 1);

    }

    // Declared in Generics.ast at line 11


    // Declared in Generics.ast line 22
    public AbstractWildcardType(Modifiers p0, String p1, List<BodyDecl> p2) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
    }

    // Declared in Generics.ast at line 18


    // Declared in Generics.ast line 22
    public AbstractWildcardType(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
    }

    // Declared in Generics.ast at line 24


  protected int numChildren() {
    return 2;
  }

    // Declared in Generics.ast at line 27

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 38
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
    // Declared in java.ast line 38
    public void setBodyDeclList(List<BodyDecl> list) {
        setChild(list, 1);
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
        return (List<BodyDecl>)getChild(1);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclListNoTransform() {
        return (List<BodyDecl>)getChildNoTransform(1);
    }

    // Declared in GenericsSubtype.jrag at line 103
 @SuppressWarnings({"unchecked", "cast"})     public boolean isWildcard() {
        boolean isWildcard_value = isWildcard_compute();
        return isWildcard_value;
    }

    private boolean isWildcard_compute() {  return true;  }

    // Declared in GenericsCodegen.jrag at line 333
 @SuppressWarnings({"unchecked", "cast"})     public boolean needsSignatureAttribute() {
        if(needsSignatureAttribute_computed)
            return needsSignatureAttribute_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        needsSignatureAttribute_value = needsSignatureAttribute_compute();
        if(isFinal && num == boundariesCrossed)
            needsSignatureAttribute_computed = true;
        return needsSignatureAttribute_value;
    }

    private boolean needsSignatureAttribute_compute() {  return true;  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
