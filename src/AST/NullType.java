
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;
 // placeholder for ; in compilation unit

public class NullType extends TypeDecl implements Cloneable {
    public void flushCache() {
        super.flushCache();
        instanceOf_TypeDecl_values = null;
        subtype_TypeDecl_visited = new java.util.HashMap(4);
    }
     @SuppressWarnings({"unchecked", "cast"})  public NullType clone() throws CloneNotSupportedException {
        NullType node = (NullType)super.clone();
        node.instanceOf_TypeDecl_values = null;
        node.subtype_TypeDecl_visited = new java.util.HashMap(4);
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public NullType copy() {
      try {
          NullType node = (NullType)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public NullType fullCopy() {
        NullType res = (NullType)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in CodeGeneration.jrag at line 624

  public void emitReturn(CodeGeneration gen)      { gen.emit(Bytecode.ARETURN);}

    // Declared in CodeGeneration.jrag at line 679

  public void emitLoadLocal(CodeGeneration gen, int pos) {
    gen.emitLoadReference(pos);
  }

    // Declared in CodeGeneration.jrag at line 792

  public void emitStoreLocal(CodeGeneration gen, int pos) {
    gen.emitStoreReference(pos);
  }

    // Declared in CodeGeneration.jrag at line 956

  void emitCastTo(CodeGeneration gen, TypeDecl type)     { }

    // Declared in CodeGeneration.jrag at line 1145

  public void branchEQ(CodeGeneration gen, int label)      { gen.emitCompare(Bytecode.IF_ACMPEQ, label); }

    // Declared in CodeGeneration.jrag at line 1154

  public void branchNE(CodeGeneration gen, int label)      { gen.emitCompare(Bytecode.IF_ACMPNE, label); }

    // Declared in java.ast at line 3
    // Declared in java.ast line 44

    public NullType() {
        super();

        setChild(new List(), 1);

    }

    // Declared in java.ast at line 11


    // Declared in java.ast line 44
    public NullType(Modifiers p0, String p1, List<BodyDecl> p2) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
    }

    // Declared in java.ast at line 18


    // Declared in java.ast line 44
    public NullType(Modifiers p0, beaver.Symbol p1, List<BodyDecl> p2) {
        setChild(p0, 0);
        setID(p1);
        setChild(p2, 1);
    }

    // Declared in java.ast at line 24


  protected int numChildren() {
    return 2;
  }

    // Declared in java.ast at line 27

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

    // Declared in TypeAnalysis.jrag at line 207
 @SuppressWarnings({"unchecked", "cast"})     public boolean isNull() {
        boolean isNull_value = isNull_compute();
        return isNull_value;
    }

    private boolean isNull_compute() {  return true;  }

    // Declared in GenericsSubtype.jrag at line 391
 @SuppressWarnings({"unchecked", "cast"})     public boolean instanceOf(TypeDecl type) {
        Object _parameters = type;
if(instanceOf_TypeDecl_values == null) instanceOf_TypeDecl_values = new java.util.HashMap(4);
        if(instanceOf_TypeDecl_values.containsKey(_parameters))
            return ((Boolean)instanceOf_TypeDecl_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean instanceOf_TypeDecl_value = instanceOf_compute(type);
        if(isFinal && num == boundariesCrossed)
            instanceOf_TypeDecl_values.put(_parameters, Boolean.valueOf(instanceOf_TypeDecl_value));
        return instanceOf_TypeDecl_value;
    }

    private boolean instanceOf_compute(TypeDecl type) {  return subtype(type);  }

    // Declared in TypeAnalysis.jrag at line 485
 @SuppressWarnings({"unchecked", "cast"})     public boolean isSupertypeOfNullType(NullType type) {
        boolean isSupertypeOfNullType_NullType_value = isSupertypeOfNullType_compute(type);
        return isSupertypeOfNullType_NullType_value;
    }

    private boolean isSupertypeOfNullType_compute(NullType type) {  return true;  }

    // Declared in InnerClasses.jrag at line 81
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl stringPromotion() {
        TypeDecl stringPromotion_value = stringPromotion_compute();
        return stringPromotion_value;
    }

    private TypeDecl stringPromotion_compute() {  return typeObject();  }

    // Declared in LocalNum.jrag at line 129
 @SuppressWarnings({"unchecked", "cast"})     public int variableSize() {
        int variableSize_value = variableSize_compute();
        return variableSize_value;
    }

    private int variableSize_compute() {  return 1;  }

    protected java.util.Map subtype_TypeDecl_visited;
    protected java.util.Set subtype_TypeDecl_computed = new java.util.HashSet(4);
    protected java.util.Set subtype_TypeDecl_initialized = new java.util.HashSet(4);
    protected java.util.Map subtype_TypeDecl_values = new java.util.HashMap(4);
 @SuppressWarnings({"unchecked", "cast"})     public boolean subtype(TypeDecl type) {
        Object _parameters = type;
if(subtype_TypeDecl_visited == null) subtype_TypeDecl_visited = new java.util.HashMap(4);
if(subtype_TypeDecl_values == null) subtype_TypeDecl_values = new java.util.HashMap(4);
        if(subtype_TypeDecl_computed.contains(_parameters))
            return ((Boolean)subtype_TypeDecl_values.get(_parameters)).booleanValue();
        if (!subtype_TypeDecl_initialized.contains(_parameters)) {
            subtype_TypeDecl_initialized.add(_parameters);
            subtype_TypeDecl_values.put(_parameters, Boolean.valueOf(true));
        }
        if (!IN_CIRCLE) {
            IN_CIRCLE = true;
            int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
            CIRCLE_INDEX = 1;
            boolean new_subtype_TypeDecl_value;
            do {
                subtype_TypeDecl_visited.put(_parameters, new Integer(CIRCLE_INDEX));
                CHANGE = false;
                new_subtype_TypeDecl_value = subtype_compute(type);
                if (new_subtype_TypeDecl_value!=((Boolean)subtype_TypeDecl_values.get(_parameters)).booleanValue())
                    CHANGE = true;
                subtype_TypeDecl_values.put(_parameters, Boolean.valueOf(new_subtype_TypeDecl_value));
                CIRCLE_INDEX++;
            } while (CHANGE);
            if(isFinal && num == boundariesCrossed)
{
            subtype_TypeDecl_computed.add(_parameters);
            }
            else {
            RESET_CYCLE = true;
            subtype_compute(type);
            RESET_CYCLE = false;
            subtype_TypeDecl_computed.remove(_parameters);
            subtype_TypeDecl_initialized.remove(_parameters);
            }
            IN_CIRCLE = false; 
            return new_subtype_TypeDecl_value;
        }
        if(!new Integer(CIRCLE_INDEX).equals(subtype_TypeDecl_visited.get(_parameters))) {
            subtype_TypeDecl_visited.put(_parameters, new Integer(CIRCLE_INDEX));
            if (RESET_CYCLE) {
                subtype_TypeDecl_computed.remove(_parameters);
                subtype_TypeDecl_initialized.remove(_parameters);
                return ((Boolean)subtype_TypeDecl_values.get(_parameters)).booleanValue();
            }
            boolean new_subtype_TypeDecl_value = subtype_compute(type);
            if (new_subtype_TypeDecl_value!=((Boolean)subtype_TypeDecl_values.get(_parameters)).booleanValue())
                CHANGE = true;
            subtype_TypeDecl_values.put(_parameters, Boolean.valueOf(new_subtype_TypeDecl_value));
            return new_subtype_TypeDecl_value;
        }
        return ((Boolean)subtype_TypeDecl_values.get(_parameters)).booleanValue();
    }

    private boolean subtype_compute(TypeDecl type) {  return type.supertypeNullType(this);  }

    // Declared in GenericsSubtype.jrag at line 481
 @SuppressWarnings({"unchecked", "cast"})     public boolean supertypeNullType(NullType type) {
        boolean supertypeNullType_NullType_value = supertypeNullType_compute(type);
        return supertypeNullType_NullType_value;
    }

    private boolean supertypeNullType_compute(NullType type) {  return true;  }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
