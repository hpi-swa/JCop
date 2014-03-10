
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class ElementConstantValue extends ElementValue implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ElementConstantValue clone() throws CloneNotSupportedException {
        ElementConstantValue node = (ElementConstantValue)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ElementConstantValue copy() {
      try {
          ElementConstantValue node = (ElementConstantValue)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ElementConstantValue fullCopy() {
        ElementConstantValue res = (ElementConstantValue)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in Annotations.jrag at line 169


  // 9.6.1 Predefined Annotation Types

  // 9.6.1.1 Target
  public void nameCheck() {
    if(enclosingAnnotationDecl().fullName().equals("java.lang.annotation.Target")) {
      Variable v = getExpr().varDecl();
      if(v != null && v.hostType().fullName().equals("java.lang.annotation.ElementType"))
        if(lookupElementTypeValue(v.name()) != this)
          error("repeated annotation target");
    }
  }

    // Declared in Annotations.jrag at line 595

  public void toString(StringBuffer s) {
    getExpr().toString(s);
  }

    // Declared in AnnotationsCodegen.jrag at line 197

  public void appendAsAttributeTo(Attribute buf) {
    if(getExpr().isConstant() && !getExpr().type().isEnumDecl()) {
      char tag = getExpr().type().isString() ? 's' : getExpr().type().typeDescriptor().charAt(0);
      int const_value_index = getExpr().type().addAnnotConstant(hostType().constantPool(), getExpr().constant());
      buf.u1(tag);
      buf.u2(const_value_index);
    }
    else if(getExpr().isClassAccess()) {
      int const_class_index = hostType().constantPool().addUtf8(getExpr().type().typeDescriptor());
      buf.u1('c');
      buf.u2(const_class_index);
    }
    else {
      Variable v = getExpr().varDecl();
      if(v == null) throw new Error("Expected Enumeration constant");

      int type_name_index = hostType().constantPool().addUtf8(v.type().typeDescriptor());
      int const_name_index = hostType().constantPool().addUtf8(v.name());
      buf.u1('e');
      buf.u2(type_name_index);
      buf.u2(const_name_index);
    }
  }

    // Declared in Annotations.ast at line 3
    // Declared in Annotations.ast line 11

    public ElementConstantValue() {
        super();


    }

    // Declared in Annotations.ast at line 10


    // Declared in Annotations.ast line 11
    public ElementConstantValue(Expr p0) {
        setChild(p0, 0);
    }

    // Declared in Annotations.ast at line 14


  protected int numChildren() {
    return 1;
  }

    // Declared in Annotations.ast at line 17

  public boolean mayHaveRewrite() { return false; }

    // Declared in Annotations.ast at line 2
    // Declared in Annotations.ast line 11
    public void setExpr(Expr node) {
        setChild(node, 0);
    }

    // Declared in Annotations.ast at line 5

    public Expr getExpr() {
        return (Expr)getChild(0);
    }

    // Declared in Annotations.ast at line 9


    public Expr getExprNoTransform() {
        return (Expr)getChildNoTransform(0);
    }

    // Declared in Annotations.jrag at line 58
 @SuppressWarnings({"unchecked", "cast"})     public boolean validTarget(Annotation a) {
        boolean validTarget_Annotation_value = validTarget_compute(a);
        return validTarget_Annotation_value;
    }

    private boolean validTarget_compute(Annotation a) {
    Variable v = getExpr().varDecl();
    if(v == null) return true;
    return v.hostType().fullName().equals("java.lang.annotation.ElementType") && a.mayUseAnnotationTarget(v.name());
  }

    // Declared in Annotations.jrag at line 182
 @SuppressWarnings({"unchecked", "cast"})     public ElementValue definesElementTypeValue(String name) {
        ElementValue definesElementTypeValue_String_value = definesElementTypeValue_compute(name);
        return definesElementTypeValue_String_value;
    }

    private ElementValue definesElementTypeValue_compute(String name) {
    Variable v = getExpr().varDecl();
    if(v != null && v.hostType().fullName().equals("java.lang.annotation.ElementType") && v.name().equals(name))
      return this;
    return null;
  }

    // Declared in Annotations.jrag at line 296
 @SuppressWarnings({"unchecked", "cast"})     public boolean hasValue(String s) {
        boolean hasValue_String_value = hasValue_compute(s);
        return hasValue_String_value;
    }

    private boolean hasValue_compute(String s) {  return getExpr().type().isString() &&
    getExpr().isConstant() && 
    getExpr().constant().stringValue().equals(s);  }

    // Declared in Annotations.jrag at line 474
 @SuppressWarnings({"unchecked", "cast"})     public boolean commensurateWithTypeDecl(TypeDecl type) {
        boolean commensurateWithTypeDecl_TypeDecl_value = commensurateWithTypeDecl_compute(type);
        return commensurateWithTypeDecl_TypeDecl_value;
    }

    private boolean commensurateWithTypeDecl_compute(TypeDecl type) {
    Expr v = getExpr();
    if(!v.type().assignConversionTo(type, v))
      return false;
    if((type.isPrimitive() || type.isString()) && !v.isConstant())
      return false;
    if(v.type().isNull())
      return false;
    if(type.fullName().equals("java.lang.Class") && !v.isClassAccess())
      return false;
    if(type.isEnumDecl() && (v.varDecl() == null || !(v.varDecl() instanceof EnumConstant)))
      return false;
    return true;
  }

    // Declared in Annotations.jrag at line 507
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl type() {
        TypeDecl type_value = type_compute();
        return type_value;
    }

    private TypeDecl type_compute() {  return getExpr().type();  }

    // Declared in Annotations.jrag at line 177
 @SuppressWarnings({"unchecked", "cast"})     public ElementValue lookupElementTypeValue(String name) {
        ElementValue lookupElementTypeValue_String_value = getParent().Define_ElementValue_lookupElementTypeValue(this, null, name);
        return lookupElementTypeValue_String_value;
    }

    // Declared in Annotations.jrag at line 551
    public String Define_String_methodHost(ASTNode caller, ASTNode child) {
        if(caller == getExprNoTransform()) {
            return enclosingAnnotationDecl().typeName();
        }
        return getParent().Define_String_methodHost(this, caller);
    }

    // Declared in Annotations.jrag at line 546
    public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
        if(caller == getExprNoTransform()) {
            return NameType.AMBIGUOUS_NAME;
        }
        return getParent().Define_NameType_nameType(this, caller);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
