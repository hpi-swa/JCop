
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class Modifiers extends ASTNode<ASTNode> implements Cloneable {
    public void flushCache() {
        super.flushCache();
        isPublic_computed = false;
        isPrivate_computed = false;
        isProtected_computed = false;
        isStatic_computed = false;
        isFinal_computed = false;
        isAbstract_computed = false;
        isVolatile_computed = false;
        isTransient_computed = false;
        isStrictfp_computed = false;
        isSynchronized_computed = false;
        isNative_computed = false;
        isSynthetic_computed = false;
        numModifier_String_values = null;
        isBefore_computed = false;
        isAfter_computed = false;
        isStaticActive_computed = false;
    }
     @SuppressWarnings({"unchecked", "cast"})  public Modifiers clone() throws CloneNotSupportedException {
        Modifiers node = (Modifiers)super.clone();
        node.isPublic_computed = false;
        node.isPrivate_computed = false;
        node.isProtected_computed = false;
        node.isStatic_computed = false;
        node.isFinal_computed = false;
        node.isAbstract_computed = false;
        node.isVolatile_computed = false;
        node.isTransient_computed = false;
        node.isStrictfp_computed = false;
        node.isSynchronized_computed = false;
        node.isNative_computed = false;
        node.isSynthetic_computed = false;
        node.numModifier_String_values = null;
        node.isBefore_computed = false;
        node.isAfter_computed = false;
        node.isStaticActive_computed = false;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public Modifiers copy() {
      try {
          Modifiers node = (Modifiers)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public Modifiers fullCopy() {
        Modifiers res = (Modifiers)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in PrettyPrint.jadd at line 451


  public void toString(StringBuffer s) {
    for(int i = 0; i < getNumModifier(); i++) {
      getModifier(i).toString(s);
      s.append(" ");
    }
  }

    // Declared in Flags.jrag at line 11

  public static final int ACC_PUBLIC       = 0x0001;

    // Declared in Flags.jrag at line 12
 // class field method
  public static final int ACC_PRIVATE      = 0x0002;

    // Declared in Flags.jrag at line 13
 //       field method
  public static final int ACC_PROTECTED    = 0x0004;

    // Declared in Flags.jrag at line 14
 //       field method
  public static final int ACC_STATIC       = 0x0008;

    // Declared in Flags.jrag at line 15
 //       field method
  public static final int ACC_FINAL        = 0x0010;

    // Declared in Flags.jrag at line 16
 // class field method
  public static final int ACC_SYNCHRONIZED = 0x0020;

    // Declared in Flags.jrag at line 17
 //             method
  public static final int ACC_SUPER        = 0x0020;

    // Declared in Flags.jrag at line 18
 // class
  public static final int ACC_VOLATILE     = 0x0040;

    // Declared in Flags.jrag at line 19
 //       field
  public static final int ACC_TRANSIENT    = 0x0080;

    // Declared in Flags.jrag at line 20
 //       field
  public static final int ACC_NATIVE       = 0x0100;

    // Declared in Flags.jrag at line 21
 //             method
  public static final int ACC_INTERFACE    = 0x0200;

    // Declared in Flags.jrag at line 22
 // class
  public static final int ACC_ABSTRACT     = 0x0400;

    // Declared in Flags.jrag at line 23
 // class       method
  public static final int ACC_SYNTHETIC    = 0x1000;

    // Declared in Flags.jrag at line 24
 //       field method
  public static final int ACC_STRICT       = 0x0800;

    // Declared in AnnotationsCodegen.jrag at line 39


  // 4.8.15
  public void addRuntimeVisibleAnnotationsAttribute(Collection c) {
    ConstantPool cp = hostType().constantPool();
    Collection annotations = runtimeVisibleAnnotations();
    if(!annotations.isEmpty())
      c.add(new AnnotationsAttribute(cp, annotations, "RuntimeVisibleAnnotations"));
  }

    // Declared in AnnotationsCodegen.jrag at line 47


  // 4.8.16
  public void addRuntimeInvisibleAnnotationsAttribute(Collection c) {
    ConstantPool cp = hostType().constantPool();
    Collection annotations = runtimeInvisibleAnnotations();
    if(!annotations.isEmpty())
      c.add(new AnnotationsAttribute(cp, annotations, "RuntimeInvisibleAnnotations"));
  }

    // Declared in AnnotationsCodegen.jrag at line 98


  public Collection runtimeVisibleAnnotations() {
    Collection annotations = new ArrayList();
    for(int i = 0; i < getNumModifier(); i++)
      if(getModifier(i).isRuntimeVisible())
        annotations.add(getModifier(i));
    return annotations;
  }

    // Declared in AnnotationsCodegen.jrag at line 119


  public Collection runtimeInvisibleAnnotations() {
    Collection annotations = new ArrayList();
    for(int i = 0; i < getNumModifier(); i++)
      if(getModifier(i).isRuntimeInvisible())
        annotations.add(getModifier(i));
    return annotations;
  }

    // Declared in AnnotationsCodegen.jrag at line 142


  // Add ACC_ANNOTATION flag to generated class file
  public static final int ACC_ANNOTATION = 0x2000;

    // Declared in EnumsCodegen.jrag at line 12

    // add flags to enums
  public static final int ACC_ENUM = 0x4000;

    // Declared in GenericsCodegen.jrag at line 214


  public static final int ACC_BRIDGE = 0x0040;

    // Declared in VariableArityParametersCodegen.jrag at line 79


  public static final int ACC_VARARGS = 0x0080;

    // Declared in auxiliary.jrag at line 54

	  
	  public boolean contains(String id) {
			for (Modifier modifier : getModifiers()) {
				if (modifier.getID().equals(id))
					return true;
			}
			return false;
		}

    // Declared in xmlOutline.jrag at line 118

	
			/*StringBuffer b = new StringBuffer();
			if (!isPrimitiveType() && !isVoid())
				b.append(packageName()).append('.');
			
			b.append(name());
			return b.toString();
		}*/

  public void printOutline(StringBuffer s) { 
	s.append("<modifiers>\n");
	for (Modifier m : getModifiers())
		m.printOutline(s);
	s.append("</modifiers>\n");
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 193

    public Modifiers() {
        super();

        setChild(new List(), 0);

    }

    // Declared in java.ast at line 11


    // Declared in java.ast line 193
    public Modifiers(List<Modifier> p0) {
        setChild(p0, 0);
    }

    // Declared in java.ast at line 15


  protected int numChildren() {
    return 1;
  }

    // Declared in java.ast at line 18

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 193
    public void setModifierList(List<Modifier> list) {
        setChild(list, 0);
    }

    // Declared in java.ast at line 6


    private int getNumModifier = 0;

    // Declared in java.ast at line 7

    public int getNumModifier() {
        return getModifierList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Modifier getModifier(int i) {
        return (Modifier)getModifierList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addModifier(Modifier node) {
        List<Modifier> list = getModifierList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setModifier(Modifier node, int i) {
        List<Modifier> list = getModifierList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<Modifier> getModifiers() {
        return getModifierList();
    }

    // Declared in java.ast at line 27

    public List<Modifier> getModifiersNoTransform() {
        return getModifierListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Modifier> getModifierList() {
        return (List<Modifier>)getChild(0);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Modifier> getModifierListNoTransform() {
        return (List<Modifier>)getChildNoTransform(0);
    }

    // Declared in layer_decl.jrag at line 105

	
	
	   public void checkModifiers() {
		if (isStaticActive() && !mayBeStaticActive())
			error("modifier staticactive not allowed in this context");
		if(numModifier("staticactive") > 1)
			error("only one static allowed");
	}

    protected boolean isPublic_computed = false;
    protected boolean isPublic_value;
    // Declared in Modifiers.jrag at line 368
 @SuppressWarnings({"unchecked", "cast"})     public boolean isPublic() {
        if(isPublic_computed)
            return isPublic_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isPublic_value = isPublic_compute();
        if(isFinal && num == boundariesCrossed)
            isPublic_computed = true;
        return isPublic_value;
    }

    private boolean isPublic_compute() {  return numModifier("public") != 0;  }

    protected boolean isPrivate_computed = false;
    protected boolean isPrivate_value;
    // Declared in Modifiers.jrag at line 369
 @SuppressWarnings({"unchecked", "cast"})     public boolean isPrivate() {
        if(isPrivate_computed)
            return isPrivate_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isPrivate_value = isPrivate_compute();
        if(isFinal && num == boundariesCrossed)
            isPrivate_computed = true;
        return isPrivate_value;
    }

    private boolean isPrivate_compute() {  return numModifier("private") != 0;  }

    protected boolean isProtected_computed = false;
    protected boolean isProtected_value;
    // Declared in Modifiers.jrag at line 370
 @SuppressWarnings({"unchecked", "cast"})     public boolean isProtected() {
        if(isProtected_computed)
            return isProtected_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isProtected_value = isProtected_compute();
        if(isFinal && num == boundariesCrossed)
            isProtected_computed = true;
        return isProtected_value;
    }

    private boolean isProtected_compute() {  return numModifier("protected") != 0;  }

    protected boolean isStatic_computed = false;
    protected boolean isStatic_value;
    // Declared in Modifiers.jrag at line 371
 @SuppressWarnings({"unchecked", "cast"})     public boolean isStatic() {
        if(isStatic_computed)
            return isStatic_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isStatic_value = isStatic_compute();
        if(isFinal && num == boundariesCrossed)
            isStatic_computed = true;
        return isStatic_value;
    }

    private boolean isStatic_compute() {  return numModifier("static") != 0;  }

    protected boolean isFinal_computed = false;
    protected boolean isFinal_value;
    // Declared in Modifiers.jrag at line 372
 @SuppressWarnings({"unchecked", "cast"})     public boolean isFinal() {
        if(isFinal_computed)
            return isFinal_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isFinal_value = isFinal_compute();
        if(isFinal && num == boundariesCrossed)
            isFinal_computed = true;
        return isFinal_value;
    }

    private boolean isFinal_compute() {  return numModifier("final") != 0;  }

    protected boolean isAbstract_computed = false;
    protected boolean isAbstract_value;
    // Declared in Modifiers.jrag at line 373
 @SuppressWarnings({"unchecked", "cast"})     public boolean isAbstract() {
        if(isAbstract_computed)
            return isAbstract_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isAbstract_value = isAbstract_compute();
        if(isFinal && num == boundariesCrossed)
            isAbstract_computed = true;
        return isAbstract_value;
    }

    private boolean isAbstract_compute() {  return numModifier("abstract") != 0;  }

    protected boolean isVolatile_computed = false;
    protected boolean isVolatile_value;
    // Declared in Modifiers.jrag at line 374
 @SuppressWarnings({"unchecked", "cast"})     public boolean isVolatile() {
        if(isVolatile_computed)
            return isVolatile_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isVolatile_value = isVolatile_compute();
        if(isFinal && num == boundariesCrossed)
            isVolatile_computed = true;
        return isVolatile_value;
    }

    private boolean isVolatile_compute() {  return numModifier("volatile") != 0;  }

    protected boolean isTransient_computed = false;
    protected boolean isTransient_value;
    // Declared in Modifiers.jrag at line 375
 @SuppressWarnings({"unchecked", "cast"})     public boolean isTransient() {
        if(isTransient_computed)
            return isTransient_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isTransient_value = isTransient_compute();
        if(isFinal && num == boundariesCrossed)
            isTransient_computed = true;
        return isTransient_value;
    }

    private boolean isTransient_compute() {  return numModifier("transient") != 0;  }

    protected boolean isStrictfp_computed = false;
    protected boolean isStrictfp_value;
    // Declared in Modifiers.jrag at line 376
 @SuppressWarnings({"unchecked", "cast"})     public boolean isStrictfp() {
        if(isStrictfp_computed)
            return isStrictfp_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isStrictfp_value = isStrictfp_compute();
        if(isFinal && num == boundariesCrossed)
            isStrictfp_computed = true;
        return isStrictfp_value;
    }

    private boolean isStrictfp_compute() {  return numModifier("strictfp") != 0;  }

    protected boolean isSynchronized_computed = false;
    protected boolean isSynchronized_value;
    // Declared in Modifiers.jrag at line 377
 @SuppressWarnings({"unchecked", "cast"})     public boolean isSynchronized() {
        if(isSynchronized_computed)
            return isSynchronized_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isSynchronized_value = isSynchronized_compute();
        if(isFinal && num == boundariesCrossed)
            isSynchronized_computed = true;
        return isSynchronized_value;
    }

    private boolean isSynchronized_compute() {  return numModifier("synchronized") != 0;  }

    protected boolean isNative_computed = false;
    protected boolean isNative_value;
    // Declared in Modifiers.jrag at line 378
 @SuppressWarnings({"unchecked", "cast"})     public boolean isNative() {
        if(isNative_computed)
            return isNative_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isNative_value = isNative_compute();
        if(isFinal && num == boundariesCrossed)
            isNative_computed = true;
        return isNative_value;
    }

    private boolean isNative_compute() {  return numModifier("native") != 0;  }

    protected boolean isSynthetic_computed = false;
    protected boolean isSynthetic_value;
    // Declared in Modifiers.jrag at line 380
 @SuppressWarnings({"unchecked", "cast"})     public boolean isSynthetic() {
        if(isSynthetic_computed)
            return isSynthetic_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isSynthetic_value = isSynthetic_compute();
        if(isFinal && num == boundariesCrossed)
            isSynthetic_computed = true;
        return isSynthetic_value;
    }

    private boolean isSynthetic_compute() {  return numModifier("synthetic") != 0;  }

    // Declared in Modifiers.jrag at line 382
 @SuppressWarnings({"unchecked", "cast"})     public int numProtectionModifiers() {
        int numProtectionModifiers_value = numProtectionModifiers_compute();
        return numProtectionModifiers_value;
    }

    private int numProtectionModifiers_compute() {  return numModifier("public") + numModifier("protected") + numModifier("private");  }

    // Declared in Modifiers.jrag at line 385
 @SuppressWarnings({"unchecked", "cast"})     public int numCompletenessModifiers() {
        int numCompletenessModifiers_value = numCompletenessModifiers_compute();
        return numCompletenessModifiers_value;
    }

    private int numCompletenessModifiers_compute() {  return numModifier("abstract") + numModifier("final") + numModifier("volatile");  }

    protected java.util.Map numModifier_String_values;
    // Declared in Modifiers.jrag at line 388
 @SuppressWarnings({"unchecked", "cast"})     public int numModifier(String name) {
        Object _parameters = name;
if(numModifier_String_values == null) numModifier_String_values = new java.util.HashMap(4);
        if(numModifier_String_values.containsKey(_parameters))
            return ((Integer)numModifier_String_values.get(_parameters)).intValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        int numModifier_String_value = numModifier_compute(name);
        if(isFinal && num == boundariesCrossed)
            numModifier_String_values.put(_parameters, new Integer(numModifier_String_value));
        return numModifier_String_value;
    }

    private int numModifier_compute(String name) {
    int n = 0;
    for(int i = 0; i < getNumModifier(); i++) {
      String s = getModifier(i).getID();
      if(s.equals(name))
        n++;
    }
    return n;
  }

    // Declared in Annotations.jrag at line 214
 @SuppressWarnings({"unchecked", "cast"})     public Annotation annotation(TypeDecl typeDecl) {
        Annotation annotation_TypeDecl_value = annotation_compute(typeDecl);
        return annotation_TypeDecl_value;
    }

    private Annotation annotation_compute(TypeDecl typeDecl) {
    for(int i = 0; i < getNumModifier(); i++) {
      if(getModifier(i) instanceof Annotation) {
        Annotation a = (Annotation)getModifier(i);
        if(a.type() == typeDecl)
          return a;
      }
    }
    return null;
  }

    // Declared in Annotations.jrag at line 289
 @SuppressWarnings({"unchecked", "cast"})     public boolean hasAnnotationSuppressWarnings(String s) {
        boolean hasAnnotationSuppressWarnings_String_value = hasAnnotationSuppressWarnings_compute(s);
        return hasAnnotationSuppressWarnings_String_value;
    }

    private boolean hasAnnotationSuppressWarnings_compute(String s) {
    Annotation a = annotation(lookupType("java.lang", "SuppressWarnings"));
    if(a != null && a.getNumElementValuePair() == 1 && a.getElementValuePair(0).getName().equals("value"))
      return a.getElementValuePair(0).getElementValue().hasValue(s);
    return false;
  }

    // Declared in Annotations.jrag at line 319
 @SuppressWarnings({"unchecked", "cast"})     public boolean hasDeprecatedAnnotation() {
        boolean hasDeprecatedAnnotation_value = hasDeprecatedAnnotation_compute();
        return hasDeprecatedAnnotation_value;
    }

    private boolean hasDeprecatedAnnotation_compute() {  return annotation(lookupType("java.lang", "Deprecated")) != null;  }

    protected boolean isBefore_computed = false;
    protected boolean isBefore_value;
    // Declared in JCopModifiers.jrag at line 14
 @SuppressWarnings({"unchecked", "cast"})     public boolean isBefore() {
        if(isBefore_computed)
            return isBefore_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isBefore_value = isBefore_compute();
        if(isFinal && num == boundariesCrossed)
            isBefore_computed = true;
        return isBefore_value;
    }

    private boolean isBefore_compute() {  return numModifier("before") != 0;  }

    protected boolean isAfter_computed = false;
    protected boolean isAfter_value;
    // Declared in JCopModifiers.jrag at line 15
 @SuppressWarnings({"unchecked", "cast"})     public boolean isAfter() {
        if(isAfter_computed)
            return isAfter_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isAfter_value = isAfter_compute();
        if(isFinal && num == boundariesCrossed)
            isAfter_computed = true;
        return isAfter_value;
    }

    private boolean isAfter_compute() {  return numModifier("after") != 0;  }

    protected boolean isStaticActive_computed = false;
    protected boolean isStaticActive_value;
    // Declared in layer_decl.jrag at line 88
 @SuppressWarnings({"unchecked", "cast"})     public boolean isStaticActive() {
        if(isStaticActive_computed)
            return isStaticActive_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        isStaticActive_value = isStaticActive_compute();
        if(isFinal && num == boundariesCrossed)
            isStaticActive_computed = true;
        return isStaticActive_value;
    }

    private boolean isStaticActive_compute() {  return numModifier("staticactive") != 0;  }

    // Declared in Modifiers.jrag at line 356
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBePublic() {
        boolean mayBePublic_value = getParent().Define_boolean_mayBePublic(this, null);
        return mayBePublic_value;
    }

    // Declared in Modifiers.jrag at line 357
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBePrivate() {
        boolean mayBePrivate_value = getParent().Define_boolean_mayBePrivate(this, null);
        return mayBePrivate_value;
    }

    // Declared in Modifiers.jrag at line 358
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeProtected() {
        boolean mayBeProtected_value = getParent().Define_boolean_mayBeProtected(this, null);
        return mayBeProtected_value;
    }

    // Declared in Modifiers.jrag at line 359
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeStatic() {
        boolean mayBeStatic_value = getParent().Define_boolean_mayBeStatic(this, null);
        return mayBeStatic_value;
    }

    // Declared in Modifiers.jrag at line 360
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeFinal() {
        boolean mayBeFinal_value = getParent().Define_boolean_mayBeFinal(this, null);
        return mayBeFinal_value;
    }

    // Declared in Modifiers.jrag at line 361
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeAbstract() {
        boolean mayBeAbstract_value = getParent().Define_boolean_mayBeAbstract(this, null);
        return mayBeAbstract_value;
    }

    // Declared in Modifiers.jrag at line 362
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeVolatile() {
        boolean mayBeVolatile_value = getParent().Define_boolean_mayBeVolatile(this, null);
        return mayBeVolatile_value;
    }

    // Declared in Modifiers.jrag at line 363
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeTransient() {
        boolean mayBeTransient_value = getParent().Define_boolean_mayBeTransient(this, null);
        return mayBeTransient_value;
    }

    // Declared in Modifiers.jrag at line 364
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeStrictfp() {
        boolean mayBeStrictfp_value = getParent().Define_boolean_mayBeStrictfp(this, null);
        return mayBeStrictfp_value;
    }

    // Declared in Modifiers.jrag at line 365
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeSynchronized() {
        boolean mayBeSynchronized_value = getParent().Define_boolean_mayBeSynchronized(this, null);
        return mayBeSynchronized_value;
    }

    // Declared in Modifiers.jrag at line 366
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeNative() {
        boolean mayBeNative_value = getParent().Define_boolean_mayBeNative(this, null);
        return mayBeNative_value;
    }

    // Declared in Annotations.jrag at line 56
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl lookupType(String packageName, String typeName) {
        TypeDecl lookupType_String_String_value = getParent().Define_TypeDecl_lookupType(this, null, packageName, typeName);
        return lookupType_String_String_value;
    }

    // Declared in AnnotationsCodegen.jrag at line 63
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl hostType() {
        TypeDecl hostType_value = getParent().Define_TypeDecl_hostType(this, null);
        return hostType_value;
    }

    // Declared in layer_decl.jrag at line 89
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayBeStaticActive() {
        boolean mayBeStaticActive_value = getParent().Define_boolean_mayBeStaticActive(this, null);
        return mayBeStaticActive_value;
    }

    // Declared in Annotations.jrag at line 424
    public Annotation Define_Annotation_lookupAnnotation(ASTNode caller, ASTNode child, TypeDecl typeDecl) {
        if(caller == getModifierListNoTransform()) { 
   int index = caller.getIndexOfChild(child);
{
    return annotation(typeDecl);
  }
}
        return getParent().Define_Annotation_lookupAnnotation(this, caller, typeDecl);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
