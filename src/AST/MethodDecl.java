
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class MethodDecl extends MemberDecl implements Cloneable, SimpleSet, Iterator, ExceptionHolder, NamedMember {
    public void flushCache() {
        super.flushCache();
        accessibleFrom_TypeDecl_values = null;
        throwsException_TypeDecl_values = null;
        signature_computed = false;
        signature_value = null;
        moreSpecificThan_MethodDecl_values = null;
        overrides_MethodDecl_values = null;
        hides_MethodDecl_values = null;
        parameterDeclaration_String_values = null;
        type_computed = false;
        type_value = null;
        attributes_computed = false;
        attributes_value = null;
        descName_computed = false;
        descName_value = null;
        bytecodes_ConstantPool_values = null;
        flags_computed = false;
        offsetBeforeParameters_computed = false;
        offsetAfterParameters_computed = false;
        resultOffset_computed = false;
        usesTypeVariable_computed = false;
        sourceMethodDecl_computed = false;
        sourceMethodDecl_value = null;
        handlesException_TypeDecl_values = null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public MethodDecl clone() throws CloneNotSupportedException {
        MethodDecl node = (MethodDecl)super.clone();
        node.accessibleFrom_TypeDecl_values = null;
        node.throwsException_TypeDecl_values = null;
        node.signature_computed = false;
        node.signature_value = null;
        node.moreSpecificThan_MethodDecl_values = null;
        node.overrides_MethodDecl_values = null;
        node.hides_MethodDecl_values = null;
        node.parameterDeclaration_String_values = null;
        node.type_computed = false;
        node.type_value = null;
        node.attributes_computed = false;
        node.attributes_value = null;
        node.descName_computed = false;
        node.descName_value = null;
        node.bytecodes_ConstantPool_values = null;
        node.flags_computed = false;
        node.offsetBeforeParameters_computed = false;
        node.offsetAfterParameters_computed = false;
        node.resultOffset_computed = false;
        node.usesTypeVariable_computed = false;
        node.sourceMethodDecl_computed = false;
        node.sourceMethodDecl_value = null;
        node.handlesException_TypeDecl_values = null;
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public MethodDecl copy() {
      try {
          MethodDecl node = (MethodDecl)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public MethodDecl fullCopy() {
        MethodDecl res = (MethodDecl)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in BoundNames.jrag at line 77


  public Access createBoundAccess(List args) {
    if(isStatic()) {
      return hostType().createQualifiedAccess().qualifiesAccess(
        new BoundMethodAccess(name(), args, this)
      );
    }
    return new BoundMethodAccess(name(), args, this);
  }

    // Declared in DataStructures.jrag at line 134

  public SimpleSet add(Object o) {
    return new SimpleSetImpl().add(this).add(o);
  }

    // Declared in DataStructures.jrag at line 140

  private MethodDecl iterElem;

    // Declared in DataStructures.jrag at line 141

  public Iterator iterator() { iterElem = this; return this; }

    // Declared in DataStructures.jrag at line 142

  public boolean hasNext() { return iterElem != null; }

    // Declared in DataStructures.jrag at line 143

  public Object next() { Object o = iterElem; iterElem = null; return o; }

    // Declared in DataStructures.jrag at line 144

  public void remove() { throw new UnsupportedOperationException(); }

    // Declared in PrettyPrint.jadd at line 194


  public void toString(StringBuffer s) {
    s.append(indent());
    getModifiers().toString(s);
    getTypeAccess().toString(s);
    s.append(" " + name() + "(");
    if(getNumParameter() > 0) {
      getParameter(0).toString(s);
      for(int i = 1; i < getNumParameter(); i++) {
        s.append(", ");
        getParameter(i).toString(s);
      }
    }
    s.append(")");
    if(getNumException() > 0) {
      s.append(" throws ");
      getException(0).toString(s);
      for(int i = 1; i < getNumException(); i++) {
        s.append(", ");
        getException(i).toString(s);
      }
    }
    if(hasBlock()) {
      s.append(" ");
      getBlock().toString(s);
    }
    else {
      s.append(";\n");
    }
  }

    // Declared in TypeCheck.jrag at line 386


  public void typeCheck() {
    // Thrown vs super class method see MethodDecl.nameCheck
    // 8.4.4
    TypeDecl exceptionType = typeThrowable();
    for(int i = 0; i < getNumException(); i++) {
      TypeDecl typeDecl = getException(i).type();
      if(!typeDecl.instanceOf(exceptionType))
        error(signature() + " throws non throwable type " + typeDecl.fullName());
    }

    // check returns
    if(!isVoid() && hasBlock() && getBlock().canCompleteNormally())
      error("the body of a non void method may not complete normally " + getBlock()) ;

  }

    // Declared in CodeGeneration.jrag at line 872



  // emitInvoke

  public void emitInvokeMethod(CodeGeneration gen, TypeDecl hostType) {
    if(hostType.isInterfaceDecl()) {
      int size = type().variableSize() - 1;
      for(int i = 0; i < getNumParameter(); i++)
        size -= getParameter(i).type().variableSize();
      String classname = hostType.constantPoolName();
      String      desc = descName();
      String      name = name();
      int index = gen.constantPool().addInterfaceMethodref(classname, name, desc);
      int numArg = 1; // instance
      for(int i = 0; i < getNumParameter(); i++)
        numArg += getParameter(i).type().variableSize();
      gen.emit(Bytecode.INVOKEINTERFACE, size).add2(index).add(numArg).add(0);
    }
    else {
      String classname = hostType.constantPoolName();
      String      desc = descName();
      String      name = name();
      int index = gen.constantPool().addMethodref(classname, name, desc);
      if(isStatic()) {
        int size = type().variableSize();
        for(int i = 0; i < getNumParameter(); i++)
          size -= getParameter(i).type().variableSize();
        gen.emit(Bytecode.INVOKESTATIC, size).add2(index);
      }
      else {
        int size = type().variableSize() - 1;
        for(int i = 0; i < getNumParameter(); i++)
          size -= getParameter(i).type().variableSize();
        gen.emit(Bytecode.INVOKEVIRTUAL, size).add2(index);
      }
    }
  }

    // Declared in CodeGeneration.jrag at line 906

  
  public void emitInvokeSpecialMethod(CodeGeneration gen, TypeDecl hostType) {
    String classname = hostType.constantPoolName();
    String      desc = descName();
    String      name = name();
    int index = gen.constantPool().addMethodref(classname, name, desc);
    int size = type().variableSize() - 1;
    for(int i = 0; i < getNumParameter(); i++)
      size -= getParameter(i).type().variableSize();
    gen.emit(Bytecode.INVOKESPECIAL, size).add2(index);
  }

    // Declared in CreateBCode.jrag at line 73

  private void generateBytecodes(CodeGeneration gen) {
    int label = gen.variableScopeLabel();
    if(!isStatic())
      gen.addLocalVariableEntryAtCurrentPC("this", hostType().typeDescriptor(), 0, label);
    for(int i = 0; i < getNumParameter(); i++) {
      ParameterDeclaration p = (ParameterDeclaration)getParameter(i);
      gen.addLocalVariableEntryAtCurrentPC(
        p.name(), p.type().typeDescriptor(), p.localNum(), label
      );
    }
    createBCode(gen);
    if(type() instanceof VoidType) // TODO: canCompleteNormally check as well
      gen.emitReturn();
    gen.addVariableScopeLabel(label);
  }

    // Declared in CreateBCode.jrag at line 114


  public void createBCode(CodeGeneration gen) {
    try {
    if(hasBlock()) {
      gen.maxLocals = Math.max(gen.maxLocals, getBlock().localNum());
      getBlock().createBCode(gen);
    }
    } catch (Error e) {
      System.err.println(hostType().typeName() + ": " + this);
      throw e;
    }
  }

    // Declared in GenerateClassfile.jrag at line 244

  public void generateMethod(DataOutputStream out, ConstantPool cp) throws IOException {
    out.writeChar(flags());
    out.writeChar(cp.addUtf8(name()));
    out.writeChar(cp.addUtf8(descName()));
    out.writeChar(attributes().size());
    for(Iterator itera = attributes().iterator(); itera.hasNext();)
      ((Attribute)itera.next()).emit(out);
  }

    // Declared in GenerateClassfile.jrag at line 263

  public void touchMethod(ConstantPool cp) {
    cp.addUtf8(name());
    cp.addUtf8(descName());
    attributes();
  }

    // Declared in GenerateClassfile.jrag at line 363


  public boolean clear() {
    if(hasBlock()) {
      getBlock().clear();
      setBlock(new Block(new List()));
    }
    bytecodes_ConstantPool_values = null;
    return false;
  }

    // Declared in InnerClasses.jrag at line 196


  public MethodDecl createAccessor(TypeDecl methodQualifier) {
    MethodDecl m = (MethodDecl)methodQualifier.getAccessor(this, "method");
    if(m != null) return m;

    int accessorIndex = methodQualifier.accessorCounter++;
    // add synthetic flag to modifiers
    Modifiers modifiers = new Modifiers(new List());
    if(getModifiers().isStatic())
      modifiers.addModifier(new Modifier("static"));
    modifiers.addModifier(new Modifier("synthetic"));
    modifiers.addModifier(new Modifier("public"));
    // build accessor declaration
    m = new MethodDecl(
      modifiers,
      type().createQualifiedAccess(),
      name() + "$access$" + accessorIndex,
      (List)getParameterList().fullCopy(),
      (List)getExceptionList().fullCopy(),
      new Opt(
        new Block(
          new List().add(
            createAccessorStmt()
          )
        )
      )
    );
    m = methodQualifier.addMemberMethod(m);
    methodQualifier.addAccessor(this, "method", m);
    return m;
  }

    // Declared in InnerClasses.jrag at line 227

  
  private Stmt createAccessorStmt() {
    List argumentList = new List();
    for(int i = 0; i < getNumParameter(); i++)
      argumentList.add(new VarAccess(getParameter(i).name()));
    Access access = new MethodAccess(name(), argumentList);
    if(!isStatic())
      access = new ThisAccess("this").qualifiesAccess(access);
    return isVoid() ? (Stmt) new ExprStmt(access) : new ReturnStmt(new Opt(access));
  }

    // Declared in InnerClasses.jrag at line 237


  public MethodDecl createSuperAccessor(TypeDecl methodQualifier) {
    MethodDecl m = (MethodDecl)methodQualifier.getAccessor(this, "method_super");
    if(m != null) return m;

    int accessorIndex = methodQualifier.accessorCounter++;
    List parameters = new List();
    List args = new List();
    for(int i = 0; i < getNumParameter(); i++) {
      parameters.add(getParameter(i).fullCopy());
      args.add(new VarAccess(getParameter(i).name()));
    }
    Stmt stmt;
    if(type().isVoid())
      stmt = new ExprStmt(new SuperAccess("super").qualifiesAccess(new MethodAccess(name(), args)));
    else 
      stmt = new ReturnStmt(new Opt(new SuperAccess("super").qualifiesAccess(new MethodAccess(name(), args))));
    m = new MethodDecl(
      new Modifiers(new List().add(new Modifier("synthetic"))),
      type().createQualifiedAccess(),
      name() + "$access$" + accessorIndex,
      parameters,
      new List(),
      new Opt(
        new Block(
          new List().add(stmt)
        )
      )
    );
    m = methodQualifier.addMemberMethod(m);
    methodQualifier.addAccessor(this, "method_super", m);
    return m;
  }

    // Declared in Generics.jrag at line 1032


  public BodyDecl p(Parameterization parTypeDecl) {
    //System.out.println("Begin substituting " + signature() + " in " + hostType().typeName() + " with " + parTypeDecl.typeSignature());
    MethodDecl m = new MethodDeclSubstituted(
      (Modifiers)getModifiers().fullCopy(),
      getTypeAccess().type().substituteReturnType(parTypeDecl),
      getID(),
      getParameterList().substitute(parTypeDecl),
      getExceptionList().substitute(parTypeDecl),
      new Opt(),
      this
    );
    //System.out.println("End substituting " + signature());
    return m;
  }

    // Declared in AnnotationsCodegen.jrag at line 86


  // 4.8.17
  public void addRuntimeVisibleParameterAnnotationsAttribute(Collection c) {
    boolean foundVisibleAnnotations = false;
    Collection annotations = new ArrayList(getNumParameter());
    for(int i = 0; i < getNumParameter(); i++) {
      Collection a = getParameter(i).getModifiers().runtimeVisibleAnnotations();
      if(!a.isEmpty()) foundVisibleAnnotations = true;
      annotations.add(a);
    }
    if(foundVisibleAnnotations)
      c.add(new ParameterAnnotationsAttribute(hostType().constantPool(), annotations, "RuntimeVisibleParameterAnnotations"));
  }

    // Declared in AnnotationsCodegen.jrag at line 107


  // 4.8.18
  public void addRuntimeInvisibleParameterAnnotationsAttribute(Collection c) {
    boolean foundInvisibleAnnotations = false;
    Collection annotations = new ArrayList(getNumParameter());
    for(int i = 0; i < getNumParameter(); i++) {
      Collection a = getParameter(i).getModifiers().runtimeInvisibleAnnotations();
      if(!a.isEmpty()) foundInvisibleAnnotations = true;
      annotations.add(a);
    }
    if(foundInvisibleAnnotations)
      c.add(new ParameterAnnotationsAttribute(hostType().constantPool(), annotations, "RuntimeInvisibleParameterAnnotations"));
  }

    // Declared in GenericsCodegen.jrag at line 232

  
  public void transformation() {
    super.transformation();
    HashSet processed = new HashSet();
    for(Iterator iter = hostType().bridgeCandidates(signature()).iterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl)iter.next();
      if(this.overrides(m)) {
        MethodDecl erased = m.erasedMethod();
        if(!erased.signature().equals(signature()) || erased.type().erasure() != type().erasure()) {
          StringBuffer keyBuffer = new StringBuffer();
          for(int i = 0; i < getNumParameter(); i++) {
            keyBuffer.append(erased.getParameter(i).type().erasure().fullName());
          }
          keyBuffer.append(erased.type().erasure().fullName());
          String key = keyBuffer.toString();
          if(!processed.contains(key)) {
            processed.add(key);

            List args = new List();
            List parameters = new List();
            for(int i = 0; i < getNumParameter(); i++) {
              args.add(new CastExpr(getParameter(i).type().erasure().createBoundAccess(), new VarAccess("p" + i)));
              parameters.add(new ParameterDeclaration(erased.getParameter(i).type().erasure(), "p" + i));
            }
            Stmt stmt;
            if(type().isVoid()) {
              stmt = new ExprStmt(
                  createBoundAccess(
                    args
                    )
                  );
            }
            else {
              stmt = new ReturnStmt(
                  createBoundAccess(
                    args
                    )
                  );
            }
            MethodDecl bridge = new BridgeMethodDecl(
                (Modifiers)getModifiers().fullCopy(),
                erased.type().erasure().createBoundAccess(),
                erased.name(),
                parameters,
                (List)getExceptionList().fullCopy(),
                new Opt(
                  new Block(
                    new List().add(stmt)
                    )
                  )
                );
            hostType().addBodyDecl(bridge);
          }
        }
      }
    }
  }

    // Declared in graphGeneration.jrag at line 25

  
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in partial_method_decl.jrag at line 152

	
//	 public boolean MethodDecl.isPartialMethod() {
//		  return (parent.parent instanceof LayerDecl);	  		
//	  }


  public boolean isPartialMethod() {
	   // is it a layer local method?
	   if(parent.parent instanceof LayerDecl)
		   return false;
	   
	  return (parent.parent instanceof LayerDeclaration) &&
	  !getModifiers().contains(jcop.Globals.Types.DELEGATION_METHOD_ANNOTATION);	  		
  }

    // Declared in partial_method_decl.jrag at line 161

  
    public TypeDecl declaringType() {	  
	  if (isPartialMethod()) 		  
		  return jcop.transformation.lookup.Lookup.lookupLayerClassDecl((LayerDeclaration)parent.parent);	  
	  else 
		  return hostType();		  
  }

    // Declared in partial_method_decl.jrag at line 178

      
   
   public String getFullQualifiedName() {    
   	return super.getFullQualifiedName() + "." + signature();
   }

    // Declared in xmlOutline.jrag at line 83

  
  
  public void printOutline(StringBuffer s) { 
	s.append("<method name=\"" + getID() + "\" type=\"" + type().name() + "\" line=\"" + sourceLineNumber() + "\" encl_type=\"" + hostType().getFullQualifiedName()+ "\">\n");	
	
				
	
	getModifiers().printOutline(s);	
	s.append("<params>\n"); 
	for (ParameterDeclaration decl : getParameters())
	  decl.printOutline(s);
	s.append("</params>\n"); 
	s.append("</method>\n"); 
  }

    // Declared in java.ast at line 3
    // Declared in java.ast line 88

    public MethodDecl() {
        super();

        setChild(new List(), 2);
        setChild(new List(), 3);
        setChild(new Opt(), 4);

    }

    // Declared in java.ast at line 13


    // Declared in java.ast line 88
    public MethodDecl(Modifiers p0, Access p1, String p2, List<ParameterDeclaration> p3, List<Access> p4, Opt<Block> p5) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setChild(p3, 2);
        setChild(p4, 3);
        setChild(p5, 4);
    }

    // Declared in java.ast at line 23


    // Declared in java.ast line 88
    public MethodDecl(Modifiers p0, Access p1, beaver.Symbol p2, List<ParameterDeclaration> p3, List<Access> p4, Opt<Block> p5) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setChild(p3, 2);
        setChild(p4, 3);
        setChild(p5, 4);
    }

    // Declared in java.ast at line 32


  protected int numChildren() {
    return 5;
  }

    // Declared in java.ast at line 35

  public boolean mayHaveRewrite() { return false; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 88
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
    // Declared in java.ast line 88
    public void setTypeAccess(Access node) {
        setChild(node, 1);
    }

    // Declared in java.ast at line 5

    public Access getTypeAccess() {
        return (Access)getChild(1);
    }

    // Declared in java.ast at line 9


    public Access getTypeAccessNoTransform() {
        return (Access)getChildNoTransform(1);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 88
    private String tokenString_ID;

    // Declared in java.ast at line 3

    public void setID(String value) {
        tokenString_ID = value;
    }

    // Declared in java.ast at line 6

    public int IDstart;

    // Declared in java.ast at line 7

    public int IDend;

    // Declared in java.ast at line 8

    public void setID(beaver.Symbol symbol) {
        if(symbol.value != null && !(symbol.value instanceof String))
          throw new UnsupportedOperationException("setID is only valid for String lexemes");
        tokenString_ID = (String)symbol.value;
        IDstart = symbol.getStart();
        IDend = symbol.getEnd();
    }

    // Declared in java.ast at line 15

    public String getID() {
        return tokenString_ID != null ? tokenString_ID : "";
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 88
    public void setParameterList(List<ParameterDeclaration> list) {
        setChild(list, 2);
    }

    // Declared in java.ast at line 6


    private int getNumParameter = 0;

    // Declared in java.ast at line 7

    public int getNumParameter() {
        return getParameterList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public ParameterDeclaration getParameter(int i) {
        return (ParameterDeclaration)getParameterList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addParameter(ParameterDeclaration node) {
        List<ParameterDeclaration> list = getParameterList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setParameter(ParameterDeclaration node, int i) {
        List<ParameterDeclaration> list = getParameterList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<ParameterDeclaration> getParameters() {
        return getParameterList();
    }

    // Declared in java.ast at line 27

    public List<ParameterDeclaration> getParametersNoTransform() {
        return getParameterListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<ParameterDeclaration> getParameterList() {
        return (List<ParameterDeclaration>)getChild(2);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<ParameterDeclaration> getParameterListNoTransform() {
        return (List<ParameterDeclaration>)getChildNoTransform(2);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 88
    public void setExceptionList(List<Access> list) {
        setChild(list, 3);
    }

    // Declared in java.ast at line 6


    private int getNumException = 0;

    // Declared in java.ast at line 7

    public int getNumException() {
        return getExceptionList().getNumChild();
    }

    // Declared in java.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Access getException(int i) {
        return (Access)getExceptionList().getChild(i);
    }

    // Declared in java.ast at line 15


    public void addException(Access node) {
        List<Access> list = getExceptionList();
        list.addChild(node);
    }

    // Declared in java.ast at line 20


    public void setException(Access node, int i) {
        List<Access> list = getExceptionList();
        list.setChild(node, i);
    }

    // Declared in java.ast at line 24

    public List<Access> getExceptions() {
        return getExceptionList();
    }

    // Declared in java.ast at line 27

    public List<Access> getExceptionsNoTransform() {
        return getExceptionListNoTransform();
    }

    // Declared in java.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Access> getExceptionList() {
        return (List<Access>)getChild(3);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Access> getExceptionListNoTransform() {
        return (List<Access>)getChildNoTransform(3);
    }

    // Declared in java.ast at line 2
    // Declared in java.ast line 88
    public void setBlockOpt(Opt<Block> opt) {
        setChild(opt, 4);
    }

    // Declared in java.ast at line 6


    public boolean hasBlock() {
        return getBlockOpt().getNumChild() != 0;
    }

    // Declared in java.ast at line 10


     @SuppressWarnings({"unchecked", "cast"})  public Block getBlock() {
        return (Block)getBlockOpt().getChild(0);
    }

    // Declared in java.ast at line 14


    public void setBlock(Block node) {
        getBlockOpt().setChild(node, 0);
    }

    // Declared in java.ast at line 17

     @SuppressWarnings({"unchecked", "cast"})  public Opt<Block> getBlockOpt() {
        return (Opt<Block>)getChild(4);
    }

    // Declared in java.ast at line 21


     @SuppressWarnings({"unchecked", "cast"})  public Opt<Block> getBlockOptNoTransform() {
        return (Opt<Block>)getChildNoTransform(4);
    }

    // Declared in JCopModifiers.jrag at line 24

  
    public void checkModifiers() {
     if(isBefore() && !isPartialMethod())
      error("before pertains only to partial method declarations");
	 if(isAfter() && !isPartialMethod())
      error("after pertains only to partial method declarations");
  }

    // Declared in partial_method_decl.jrag at line 187

   
   
    public void nameCheck() {
    // 8.4
    // 8.4.2
    if(!hostType().methodsSignature(signature()).contains(this) && !isPartialMethod())
      error("method with signature " + signature() + " is multiply declared in type " + hostType().typeName());
    // 8.4.3.4
    if(isNative() && hasBlock())
      error("native methods must have an empty semicolon body");
    // 8.4.5
    if(isAbstract() && hasBlock())
      error("abstract methods must have an empty semicolon body");
    // 8.4.5
    if(!hasBlock() && !(isNative() || isAbstract()))
      error("only abstract and native methods may have an empty semicolon body");
  }

    // Declared in LookupMethod.jrag at line 142
private boolean refined_LookupMethod_moreSpecificThan_MethodDecl(MethodDecl m)
{
    if(getNumParameter() == 0)
      return false;
    for(int i = 0; i < getNumParameter(); i++) {
      if(!getParameter(i).type().instanceOf(m.getParameter(i).type()))
        return false;
    }
    return true;
  }

    // Declared in Attributes.jrag at line 189
private Collection refined_Attributes_attributes()
{
    ArrayList l = new ArrayList();
    l.add(new ExceptionsAttribute(bytecodes(hostType().constantPool()), this));
    if(isAbstract() || isNative()) return l;
    l.add(new CodeAttribute(bytecodes(hostType().constantPool()), this));
    if(getModifiers().isSynthetic())
      l.add(new SyntheticAttribute(hostType().constantPool()));
    return l;
  }

    // Declared in Flags.jrag at line 40
private int refined_Flags_flags()
{
    int res = 0;
    if(isPublic()) res |= Modifiers.ACC_PUBLIC;
    if(isPrivate()) res |= Modifiers.ACC_PRIVATE;
    if(isProtected()) res |= Modifiers.ACC_PROTECTED;
    if(isStatic()) res |= Modifiers.ACC_STATIC;
    if(isFinal()) res |= Modifiers.ACC_FINAL;
    if(isSynchronized()) res |= Modifiers.ACC_SYNCHRONIZED;
    if(isNative()) res |= Modifiers.ACC_NATIVE;
    if(isAbstract()) res |= Modifiers.ACC_ABSTRACT;
    if(isStrictfp() || (hostType().isStrictfp() && !hostType().isInterfaceDecl())) res |= Modifiers.ACC_STRICT;
    return res;
  }

    // Declared in AnnotationsCodegen.jrag at line 23
private Collection refined_AnnotationsCodegen_attributes()
{
    Collection c = refined_Attributes_attributes();
    getModifiers().addRuntimeVisibleAnnotationsAttribute(c);
    getModifiers().addRuntimeInvisibleAnnotationsAttribute(c);
    addRuntimeVisibleParameterAnnotationsAttribute(c);
    addRuntimeInvisibleParameterAnnotationsAttribute(c);
    return c;
  }

    protected java.util.Map accessibleFrom_TypeDecl_values;
    // Declared in AccessControl.jrag at line 77
 @SuppressWarnings({"unchecked", "cast"})     public boolean accessibleFrom(TypeDecl type) {
        Object _parameters = type;
if(accessibleFrom_TypeDecl_values == null) accessibleFrom_TypeDecl_values = new java.util.HashMap(4);
        if(accessibleFrom_TypeDecl_values.containsKey(_parameters))
            return ((Boolean)accessibleFrom_TypeDecl_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean accessibleFrom_TypeDecl_value = accessibleFrom_compute(type);
        if(isFinal && num == boundariesCrossed)
            accessibleFrom_TypeDecl_values.put(_parameters, Boolean.valueOf(accessibleFrom_TypeDecl_value));
        return accessibleFrom_TypeDecl_value;
    }

    private boolean accessibleFrom_compute(TypeDecl type) {
    if(isPublic()) {
      return true;
    }
    else if(isProtected()) {
      if(hostPackage().equals(type.hostPackage()))
        return true;
      if(type.withinBodyThatSubclasses(hostType()) != null)
        return true;
      return false;
    }
    else if(isPrivate())
      return hostType().topLevelType() == type.topLevelType();
    else
      return hostPackage().equals(type.hostPackage());
  }

    // Declared in DataStructures.jrag at line 132
 @SuppressWarnings({"unchecked", "cast"})     public int size() {
        int size_value = size_compute();
        return size_value;
    }

    private int size_compute() {  return 1;  }

    // Declared in DataStructures.jrag at line 133
 @SuppressWarnings({"unchecked", "cast"})     public boolean isEmpty() {
        boolean isEmpty_value = isEmpty_compute();
        return isEmpty_value;
    }

    private boolean isEmpty_compute() {  return false;  }

    // Declared in DataStructures.jrag at line 137
 @SuppressWarnings({"unchecked", "cast"})     public boolean contains(Object o) {
        boolean contains_Object_value = contains_compute(o);
        return contains_Object_value;
    }

    private boolean contains_compute(Object o) {  return this == o;  }

    // Declared in ErrorCheck.jrag at line 31
 @SuppressWarnings({"unchecked", "cast"})     public int lineNumber() {
        int lineNumber_value = lineNumber_compute();
        return lineNumber_value;
    }

    private int lineNumber_compute() {  return getLine(IDstart);  }

    protected java.util.Map throwsException_TypeDecl_values;
    // Declared in ExceptionHandling.jrag at line 123
 @SuppressWarnings({"unchecked", "cast"})     public boolean throwsException(TypeDecl exceptionType) {
        Object _parameters = exceptionType;
if(throwsException_TypeDecl_values == null) throwsException_TypeDecl_values = new java.util.HashMap(4);
        if(throwsException_TypeDecl_values.containsKey(_parameters))
            return ((Boolean)throwsException_TypeDecl_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean throwsException_TypeDecl_value = throwsException_compute(exceptionType);
        if(isFinal && num == boundariesCrossed)
            throwsException_TypeDecl_values.put(_parameters, Boolean.valueOf(throwsException_TypeDecl_value));
        return throwsException_TypeDecl_value;
    }

    private boolean throwsException_compute(TypeDecl exceptionType) {
    for(int i = 0; i < getNumException(); i++)
      if(exceptionType.instanceOf(getException(i).type()))
        return true;
    return false;
  }

    // Declared in LookupMethod.jrag at line 125
 @SuppressWarnings({"unchecked", "cast"})     public String name() {
        String name_value = name_compute();
        return name_value;
    }

    private String name_compute() {  return getID();  }

    protected boolean signature_computed = false;
    protected String signature_value;
    // Declared in MethodSignature.jrag at line 328
 @SuppressWarnings({"unchecked", "cast"})     public String signature() {
        if(signature_computed)
            return signature_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        signature_value = signature_compute();
        if(isFinal && num == boundariesCrossed)
            signature_computed = true;
        return signature_value;
    }

    private String signature_compute() {
    StringBuffer s = new StringBuffer();
    s.append(name() + "(");
    for(int i = 0; i < getNumParameter(); i++) {
      if(i != 0) s.append(", ");
      s.append(getParameter(i).type().erasure().typeName());
    }
    s.append(")");
    return s.toString();

  }

    // Declared in LookupMethod.jrag at line 140
 @SuppressWarnings({"unchecked", "cast"})     public boolean sameSignature(MethodDecl m) {
        boolean sameSignature_MethodDecl_value = sameSignature_compute(m);
        return sameSignature_MethodDecl_value;
    }

    private boolean sameSignature_compute(MethodDecl m) {  return signature().equals(m.signature());  }

    protected java.util.Map moreSpecificThan_MethodDecl_values;
    // Declared in MethodSignature.jrag at line 140
 @SuppressWarnings({"unchecked", "cast"})     public boolean moreSpecificThan(MethodDecl m) {
        Object _parameters = m;
if(moreSpecificThan_MethodDecl_values == null) moreSpecificThan_MethodDecl_values = new java.util.HashMap(4);
        if(moreSpecificThan_MethodDecl_values.containsKey(_parameters))
            return ((Boolean)moreSpecificThan_MethodDecl_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean moreSpecificThan_MethodDecl_value = moreSpecificThan_compute(m);
        if(isFinal && num == boundariesCrossed)
            moreSpecificThan_MethodDecl_values.put(_parameters, Boolean.valueOf(moreSpecificThan_MethodDecl_value));
        return moreSpecificThan_MethodDecl_value;
    }

    private boolean moreSpecificThan_compute(MethodDecl m) {
    if(!isVariableArity() && !m.isVariableArity())
      return refined_LookupMethod_moreSpecificThan_MethodDecl(m);
    int num = Math.max(getNumParameter(), m.getNumParameter());
    for(int i = 0; i < num; i++) {
      TypeDecl t1 = i < getNumParameter() - 1 ? getParameter(i).type() : getParameter(getNumParameter()-1).type().componentType();
      TypeDecl t2 = i < m.getNumParameter() - 1 ? m.getParameter(i).type() : m.getParameter(m.getNumParameter()-1).type().componentType();
      if(!t1.instanceOf(t2))
        return false;
    }
    return true;
  }

    protected java.util.Map overrides_MethodDecl_values;
    // Declared in LookupMethod.jrag at line 183
 @SuppressWarnings({"unchecked", "cast"})     public boolean overrides(MethodDecl m) {
        Object _parameters = m;
if(overrides_MethodDecl_values == null) overrides_MethodDecl_values = new java.util.HashMap(4);
        if(overrides_MethodDecl_values.containsKey(_parameters))
            return ((Boolean)overrides_MethodDecl_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean overrides_MethodDecl_value = overrides_compute(m);
        if(isFinal && num == boundariesCrossed)
            overrides_MethodDecl_values.put(_parameters, Boolean.valueOf(overrides_MethodDecl_value));
        return overrides_MethodDecl_value;
    }

    private boolean overrides_compute(MethodDecl m) {  return !isStatic() && !m.isPrivate() && m.accessibleFrom(hostType()) && 
     hostType().instanceOf(m.hostType()) && m.signature().equals(signature());  }

    protected java.util.Map hides_MethodDecl_values;
    // Declared in LookupMethod.jrag at line 187
 @SuppressWarnings({"unchecked", "cast"})     public boolean hides(MethodDecl m) {
        Object _parameters = m;
if(hides_MethodDecl_values == null) hides_MethodDecl_values = new java.util.HashMap(4);
        if(hides_MethodDecl_values.containsKey(_parameters))
            return ((Boolean)hides_MethodDecl_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean hides_MethodDecl_value = hides_compute(m);
        if(isFinal && num == boundariesCrossed)
            hides_MethodDecl_values.put(_parameters, Boolean.valueOf(hides_MethodDecl_value));
        return hides_MethodDecl_value;
    }

    private boolean hides_compute(MethodDecl m) {  return isStatic() && !m.isPrivate() && m.accessibleFrom(hostType()) && 
     hostType().instanceOf(m.hostType()) && m.signature().equals(signature());  }

    protected java.util.Map parameterDeclaration_String_values;
    // Declared in LookupVariable.jrag at line 99
 @SuppressWarnings({"unchecked", "cast"})     public SimpleSet parameterDeclaration(String name) {
        Object _parameters = name;
if(parameterDeclaration_String_values == null) parameterDeclaration_String_values = new java.util.HashMap(4);
        if(parameterDeclaration_String_values.containsKey(_parameters))
            return (SimpleSet)parameterDeclaration_String_values.get(_parameters);
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        SimpleSet parameterDeclaration_String_value = parameterDeclaration_compute(name);
        if(isFinal && num == boundariesCrossed)
            parameterDeclaration_String_values.put(_parameters, parameterDeclaration_String_value);
        return parameterDeclaration_String_value;
    }

    private SimpleSet parameterDeclaration_compute(String name) {
    for(int i = 0; i < getNumParameter(); i++)
      if(getParameter(i).name().equals(name))
        return (ParameterDeclaration)getParameter(i);
    return SimpleSet.emptySet;
  }

    // Declared in Modifiers.jrag at line 213
 @SuppressWarnings({"unchecked", "cast"})     public boolean isSynthetic() {
        boolean isSynthetic_value = isSynthetic_compute();
        return isSynthetic_value;
    }

    private boolean isSynthetic_compute() {  return getModifiers().isSynthetic();  }

    // Declared in Modifiers.jrag at line 222
 @SuppressWarnings({"unchecked", "cast"})     public boolean isPublic() {
        boolean isPublic_value = isPublic_compute();
        return isPublic_value;
    }

    private boolean isPublic_compute() {  return getModifiers().isPublic() || hostType().isInterfaceDecl();  }

    // Declared in Modifiers.jrag at line 223
 @SuppressWarnings({"unchecked", "cast"})     public boolean isPrivate() {
        boolean isPrivate_value = isPrivate_compute();
        return isPrivate_value;
    }

    private boolean isPrivate_compute() {  return getModifiers().isPrivate();  }

    // Declared in Modifiers.jrag at line 224
 @SuppressWarnings({"unchecked", "cast"})     public boolean isProtected() {
        boolean isProtected_value = isProtected_compute();
        return isProtected_value;
    }

    private boolean isProtected_compute() {  return getModifiers().isProtected();  }

    // Declared in Modifiers.jrag at line 225
 @SuppressWarnings({"unchecked", "cast"})     public boolean isAbstract() {
        boolean isAbstract_value = isAbstract_compute();
        return isAbstract_value;
    }

    private boolean isAbstract_compute() {  return getModifiers().isAbstract() || hostType().isInterfaceDecl();  }

    // Declared in Modifiers.jrag at line 226
 @SuppressWarnings({"unchecked", "cast"})     public boolean isStatic() {
        boolean isStatic_value = isStatic_compute();
        return isStatic_value;
    }

    private boolean isStatic_compute() {  return getModifiers().isStatic();  }

    // Declared in Modifiers.jrag at line 228
 @SuppressWarnings({"unchecked", "cast"})     public boolean isFinal() {
        boolean isFinal_value = isFinal_compute();
        return isFinal_value;
    }

    private boolean isFinal_compute() {  return getModifiers().isFinal() || hostType().isFinal() || isPrivate();  }

    // Declared in Modifiers.jrag at line 229
 @SuppressWarnings({"unchecked", "cast"})     public boolean isSynchronized() {
        boolean isSynchronized_value = isSynchronized_compute();
        return isSynchronized_value;
    }

    private boolean isSynchronized_compute() {  return getModifiers().isSynchronized();  }

    // Declared in Modifiers.jrag at line 230
 @SuppressWarnings({"unchecked", "cast"})     public boolean isNative() {
        boolean isNative_value = isNative_compute();
        return isNative_value;
    }

    private boolean isNative_compute() {  return getModifiers().isNative();  }

    // Declared in Modifiers.jrag at line 231
 @SuppressWarnings({"unchecked", "cast"})     public boolean isStrictfp() {
        boolean isStrictfp_value = isStrictfp_compute();
        return isStrictfp_value;
    }

    private boolean isStrictfp_compute() {  return getModifiers().isStrictfp();  }

    // Declared in PrettyPrint.jadd at line 793
 @SuppressWarnings({"unchecked", "cast"})     public String dumpString() {
        String dumpString_value = dumpString_compute();
        return dumpString_value;
    }

    private String dumpString_compute() {  return getClass().getName() + " [" + getID() + "]";  }

    protected boolean type_computed = false;
    protected TypeDecl type_value;
    // Declared in TypeAnalysis.jrag at line 270
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl type() {
        if(type_computed)
            return type_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        type_value = type_compute();
        if(isFinal && num == boundariesCrossed)
            type_computed = true;
        return type_value;
    }

    private TypeDecl type_compute() {  return getTypeAccess().type();  }

    // Declared in TypeAnalysis.jrag at line 273
 @SuppressWarnings({"unchecked", "cast"})     public boolean isVoid() {
        boolean isVoid_value = isVoid_compute();
        return isVoid_value;
    }

    private boolean isVoid_compute() {  return type().isVoid();  }

    // Declared in GenericMethods.jrag at line 73
 @SuppressWarnings({"unchecked", "cast"})     public boolean mayOverrideReturn(MethodDecl m) {
        boolean mayOverrideReturn_MethodDecl_value = mayOverrideReturn_compute(m);
        return mayOverrideReturn_MethodDecl_value;
    }

    private boolean mayOverrideReturn_compute(MethodDecl m) {  return type().instanceOf(m.type());  }

    // Declared in GenericsCodegen.jrag at line 306
 @SuppressWarnings({"unchecked", "cast"})     public Collection attributes() {
        if(attributes_computed)
            return attributes_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        attributes_value = attributes_compute();
        if(isFinal && num == boundariesCrossed)
            attributes_computed = true;
        return attributes_value;
    }

    private Collection attributes_compute() {
    Collection c = refined_AnnotationsCodegen_attributes();
    if(needsSignatureAttribute())
      c.add(new SignatureAttribute(hostType().constantPool(), methodTypeSignature()));
    return c;
  }

    protected boolean descName_computed = false;
    protected String descName_value;
    // Declared in ConstantPoolNames.jrag at line 34
 @SuppressWarnings({"unchecked", "cast"})     public String descName() {
        if(descName_computed)
            return descName_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        descName_value = descName_compute();
        if(isFinal && num == boundariesCrossed)
            descName_computed = true;
        return descName_value;
    }

    private String descName_compute() {
    StringBuffer b = new StringBuffer();
    b.append("(");
    for (int i=0; i<getNumParameter(); i++)
      b.append(getParameter(i).type().typeDescriptor());
    b.append(")");
    if(type().elementType().isUnknown()) {
      System.out.println(getTypeAccess().dumpTree());
      throw new Error("Error generating descName for " + signature() + ", did not expect unknown return type");
    }
    b.append(type().typeDescriptor());
    return b.toString();
  }

    protected java.util.Map bytecodes_ConstantPool_values;
    // Declared in CreateBCode.jrag at line 60
 @SuppressWarnings({"unchecked", "cast"})     public CodeGeneration bytecodes(ConstantPool constantPool) {
        Object _parameters = constantPool;
if(bytecodes_ConstantPool_values == null) bytecodes_ConstantPool_values = new java.util.HashMap(4);
        if(bytecodes_ConstantPool_values.containsKey(_parameters))
            return (CodeGeneration)bytecodes_ConstantPool_values.get(_parameters);
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        CodeGeneration bytecodes_ConstantPool_value = bytecodes_compute(constantPool);
        if(isFinal && num == boundariesCrossed)
            bytecodes_ConstantPool_values.put(_parameters, bytecodes_ConstantPool_value);
        return bytecodes_ConstantPool_value;
    }

    private CodeGeneration bytecodes_compute(ConstantPool constantPool) {
    //if(Program.verbose())
    //  System.out.println("Generating bytecodes for " + signature() + " in " + hostType().fullName());
    CodeGeneration gen = new CodeGeneration(constantPool);
    generateBytecodes(gen);
    if(!gen.numberFormatError())
      return gen;
    gen = new CodeGeneration(constantPool, true);
    generateBytecodes(gen);
    if(!gen.numberFormatError())
      return gen;
    throw new Error("Could not generate code for " + signature() + " in " + hostType().typeName());
  }

    protected boolean flags_computed = false;
    protected int flags_value;
    // Declared in VariableArityParametersCodegen.jrag at line 81
 @SuppressWarnings({"unchecked", "cast"})     public int flags() {
        if(flags_computed)
            return flags_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        flags_value = flags_compute();
        if(isFinal && num == boundariesCrossed)
            flags_computed = true;
        return flags_value;
    }

    private int flags_compute() {
    int res = refined_Flags_flags();
    if(isVariableArity())
      res |= Modifiers.ACC_VARARGS;
    return res;
  }

    // Declared in GenerateClassfile.jrag at line 297
 @SuppressWarnings({"unchecked", "cast"})     public boolean isBytecodeMethod() {
        boolean isBytecodeMethod_value = isBytecodeMethod_compute();
        return isBytecodeMethod_value;
    }

    private boolean isBytecodeMethod_compute() {  return true;  }

    // Declared in GenerateClassfile.jrag at line 331
 @SuppressWarnings({"unchecked", "cast"})     public boolean flush() {
        boolean flush_value = flush_compute();
        return flush_value;
    }

    private boolean flush_compute() {  return false;  }

    protected boolean offsetBeforeParameters_computed = false;
    protected int offsetBeforeParameters_value;
    // Declared in LocalNum.jrag at line 17
 @SuppressWarnings({"unchecked", "cast"})     public int offsetBeforeParameters() {
        if(offsetBeforeParameters_computed)
            return offsetBeforeParameters_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        offsetBeforeParameters_value = offsetBeforeParameters_compute();
        if(isFinal && num == boundariesCrossed)
            offsetBeforeParameters_computed = true;
        return offsetBeforeParameters_value;
    }

    private int offsetBeforeParameters_compute() {  return isStatic() ? 0 : 1;  }

    protected boolean offsetAfterParameters_computed = false;
    protected int offsetAfterParameters_value;
    // Declared in LocalNum.jrag at line 19
 @SuppressWarnings({"unchecked", "cast"})     public int offsetAfterParameters() {
        if(offsetAfterParameters_computed)
            return offsetAfterParameters_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        offsetAfterParameters_value = offsetAfterParameters_compute();
        if(isFinal && num == boundariesCrossed)
            offsetAfterParameters_computed = true;
        return offsetAfterParameters_value;
    }

    private int offsetAfterParameters_compute() {
    if(getNumParameter() == 0)
      return offsetBeforeParameters();
    return getParameter(getNumParameter()-1).localNum() + 
           getParameter(getNumParameter()-1).type().variableSize();
  }

    protected boolean resultOffset_computed = false;
    protected int resultOffset_value;
    // Declared in LocalNum.jrag at line 50
 @SuppressWarnings({"unchecked", "cast"})     public int resultOffset() {
        if(resultOffset_computed)
            return resultOffset_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        resultOffset_value = resultOffset_compute();
        if(isFinal && num == boundariesCrossed)
            resultOffset_computed = true;
        return resultOffset_value;
    }

    private int resultOffset_compute() {  return type().isVoid() ? 0 : type().variableSize();  }

    // Declared in Annotations.jrag at line 139
 @SuppressWarnings({"unchecked", "cast"})     public boolean annotationMethodOverride() {
        boolean annotationMethodOverride_value = annotationMethodOverride_compute();
        return annotationMethodOverride_value;
    }

    private boolean annotationMethodOverride_compute() {  return !hostType().ancestorMethods(signature()).isEmpty();  }

    // Declared in Annotations.jrag at line 285
 @SuppressWarnings({"unchecked", "cast"})     public boolean hasAnnotationSuppressWarnings(String s) {
        boolean hasAnnotationSuppressWarnings_String_value = hasAnnotationSuppressWarnings_compute(s);
        return hasAnnotationSuppressWarnings_String_value;
    }

    private boolean hasAnnotationSuppressWarnings_compute(String s) {  return getModifiers().hasAnnotationSuppressWarnings(s);  }

    // Declared in Annotations.jrag at line 323
 @SuppressWarnings({"unchecked", "cast"})     public boolean isDeprecated() {
        boolean isDeprecated_value = isDeprecated_compute();
        return isDeprecated_value;
    }

    private boolean isDeprecated_compute() {  return getModifiers().hasDeprecatedAnnotation();  }

    protected boolean usesTypeVariable_computed = false;
    protected boolean usesTypeVariable_value;
    // Declared in Generics.jrag at line 900
 @SuppressWarnings({"unchecked", "cast"})     public boolean usesTypeVariable() {
        if(usesTypeVariable_computed)
            return usesTypeVariable_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        usesTypeVariable_value = usesTypeVariable_compute();
        if(isFinal && num == boundariesCrossed)
            usesTypeVariable_computed = true;
        return usesTypeVariable_value;
    }

    private boolean usesTypeVariable_compute() {  return getModifiers().usesTypeVariable() || getTypeAccess().usesTypeVariable() ||
    getParameterList().usesTypeVariable() || getExceptionList().usesTypeVariable();  }

    protected boolean sourceMethodDecl_computed = false;
    protected MethodDecl sourceMethodDecl_value;
    // Declared in Generics.jrag at line 1296
 @SuppressWarnings({"unchecked", "cast"})     public MethodDecl sourceMethodDecl() {
        if(sourceMethodDecl_computed)
            return sourceMethodDecl_value;
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        sourceMethodDecl_value = sourceMethodDecl_compute();
        if(isFinal && num == boundariesCrossed)
            sourceMethodDecl_computed = true;
        return sourceMethodDecl_value;
    }

    private MethodDecl sourceMethodDecl_compute() {  return this;  }

    // Declared in GenericsParTypeDecl.jrag at line 65
 @SuppressWarnings({"unchecked", "cast"})     public boolean visibleTypeParameters() {
        boolean visibleTypeParameters_value = visibleTypeParameters_compute();
        return visibleTypeParameters_value;
    }

    private boolean visibleTypeParameters_compute() {  return !isStatic();  }

    // Declared in MethodSignature.jrag at line 267
 @SuppressWarnings({"unchecked", "cast"})     public int arity() {
        int arity_value = arity_compute();
        return arity_value;
    }

    private int arity_compute() {  return getNumParameter();  }

    // Declared in VariableArityParameters.jrag at line 33
 @SuppressWarnings({"unchecked", "cast"})     public boolean isVariableArity() {
        boolean isVariableArity_value = isVariableArity_compute();
        return isVariableArity_value;
    }

    private boolean isVariableArity_compute() {  return getNumParameter() == 0 ? false : getParameter(getNumParameter()-1).isVariableArity();  }

    // Declared in VariableArityParameters.jrag at line 38
 @SuppressWarnings({"unchecked", "cast"})     public ParameterDeclaration lastParameter() {
        ParameterDeclaration lastParameter_value = lastParameter_compute();
        return lastParameter_value;
    }

    private ParameterDeclaration lastParameter_compute() {  return getParameter(getNumParameter() - 1);  }

    // Declared in GenericsCodegen.jrag at line 54
 @SuppressWarnings({"unchecked", "cast"})     public MethodDecl erasedMethod() {
        MethodDecl erasedMethod_value = erasedMethod_compute();
        return erasedMethod_value;
    }

    private MethodDecl erasedMethod_compute() {  return this;  }

    // Declared in GenericsCodegen.jrag at line 352
 @SuppressWarnings({"unchecked", "cast"})     public boolean needsSignatureAttribute() {
        boolean needsSignatureAttribute_value = needsSignatureAttribute_compute();
        return needsSignatureAttribute_value;
    }

    private boolean needsSignatureAttribute_compute() {
    if(type().needsSignatureAttribute())
      return true;
    for(int i = 0; i < getNumParameter(); i++)
      if(getParameter(i).type().needsSignatureAttribute())
        return true;
    return false;
  }

    // Declared in GenericsCodegen.jrag at line 471
 @SuppressWarnings({"unchecked", "cast"})     public String methodTypeSignature() {
        String methodTypeSignature_value = methodTypeSignature_compute();
        return methodTypeSignature_value;
    }

    private String methodTypeSignature_compute() {
    StringBuffer buf = new StringBuffer();
    buf.append("(");
    for(int i = 0; i < getNumParameter(); i++)
      buf.append(getParameter(i).type().classTypeSignature());
    buf.append(")");
    buf.append(type().classTypeSignature());
    for(int i = 0; i < getNumException(); i++)
      buf.append("^" + getException(i).type().classTypeSignature());
    return buf.toString();
  }

    // Declared in JCopModifiers.jrag at line 6
 @SuppressWarnings({"unchecked", "cast"})     public boolean isBefore() {
        boolean isBefore_value = isBefore_compute();
        return isBefore_value;
    }

    private boolean isBefore_compute() {  return getModifiers().isBefore();  }

    // Declared in JCopModifiers.jrag at line 7
 @SuppressWarnings({"unchecked", "cast"})     public boolean isAfter() {
        boolean isAfter_value = isAfter_compute();
        return isAfter_value;
    }

    private boolean isAfter_compute() {  return getModifiers().isAfter();  }

    protected java.util.Map handlesException_TypeDecl_values;
    // Declared in ExceptionHandling.jrag at line 37
 @SuppressWarnings({"unchecked", "cast"})     public boolean handlesException(TypeDecl exceptionType) {
        Object _parameters = exceptionType;
if(handlesException_TypeDecl_values == null) handlesException_TypeDecl_values = new java.util.HashMap(4);
        if(handlesException_TypeDecl_values.containsKey(_parameters))
            return ((Boolean)handlesException_TypeDecl_values.get(_parameters)).booleanValue();
        int num = boundariesCrossed;
        boolean isFinal = this.is$Final();
        boolean handlesException_TypeDecl_value = getParent().Define_boolean_handlesException(this, null, exceptionType);
        if(isFinal && num == boundariesCrossed)
            handlesException_TypeDecl_values.put(_parameters, Boolean.valueOf(handlesException_TypeDecl_value));
        return handlesException_TypeDecl_value;
    }

    // Declared in LookupMethod.jrag at line 14
 @SuppressWarnings({"unchecked", "cast"})     public MethodDecl unknownMethod() {
        MethodDecl unknownMethod_value = getParent().Define_MethodDecl_unknownMethod(this, null);
        return unknownMethod_value;
    }

    // Declared in Modifiers.jrag at line 271
    public boolean Define_boolean_mayBePrivate(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBePrivate(this, caller);
    }

    // Declared in ExceptionHandling.jrag at line 120
    public boolean Define_boolean_handlesException(ASTNode caller, ASTNode child, TypeDecl exceptionType) {
        if(caller == getBlockOptNoTransform()) {
            return throwsException(exceptionType) || handlesException(exceptionType);
        }
        return getParent().Define_boolean_handlesException(this, caller, exceptionType);
    }

    // Declared in Modifiers.jrag at line 274
    public boolean Define_boolean_mayBeFinal(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBeFinal(this, caller);
    }

    // Declared in Annotations.jrag at line 86
    public boolean Define_boolean_mayUseAnnotationTarget(ASTNode caller, ASTNode child, String name) {
        if(caller == getModifiersNoTransform()) {
            return name.equals("METHOD");
        }
        return getParent().Define_boolean_mayUseAnnotationTarget(this, caller, name);
    }

    // Declared in Modifiers.jrag at line 269
    public boolean Define_boolean_mayBePublic(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBePublic(this, caller);
    }

    // Declared in VariableDeclaration.jrag at line 80
    public boolean Define_boolean_isMethodParameter(ASTNode caller, ASTNode child) {
        if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return true;
        }
        return getParent().Define_boolean_isMethodParameter(this, caller);
    }

    // Declared in Modifiers.jrag at line 270
    public boolean Define_boolean_mayBeProtected(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBeProtected(this, caller);
    }

    // Declared in VariableArityParameters.jrag at line 22
    public boolean Define_boolean_variableArityValid(ASTNode caller, ASTNode child) {
        if(caller == getParameterListNoTransform()) {
      int i = caller.getIndexOfChild(child);
            return i == getNumParameter() - 1;
        }
        return getParent().Define_boolean_variableArityValid(this, caller);
    }

    // Declared in Modifiers.jrag at line 276
    public boolean Define_boolean_mayBeNative(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBeNative(this, caller);
    }

    // Declared in VariableDeclaration.jrag at line 82
    public boolean Define_boolean_isExceptionHandlerParameter(ASTNode caller, ASTNode child) {
        if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return false;
        }
        return getParent().Define_boolean_isExceptionHandlerParameter(this, caller);
    }

    // Declared in SyntacticClassification.jrag at line 82
    public NameType Define_NameType_nameType(ASTNode caller, ASTNode child) {
        if(caller == getExceptionListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return NameType.TYPE_NAME;
        }
        if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return NameType.TYPE_NAME;
        }
        if(caller == getTypeAccessNoTransform()) {
            return NameType.TYPE_NAME;
        }
        return getParent().Define_NameType_nameType(this, caller);
    }

    // Declared in TypeHierarchyCheck.jrag at line 142
    public boolean Define_boolean_inStaticContext(ASTNode caller, ASTNode child) {
        if(caller == getBlockOptNoTransform()) {
            return isStatic();
        }
        return getParent().Define_boolean_inStaticContext(this, caller);
    }

    // Declared in DefiniteAssignment.jrag at line 872
    public boolean Define_boolean_isDUbefore(ASTNode caller, ASTNode child, Variable v) {
        if(caller == getBlockOptNoTransform()) {
            return v.isFinal() && (v.isClassVariable() || v.isInstanceVariable()) ? false : true;
        }
        return getParent().Define_boolean_isDUbefore(this, caller, v);
    }

    // Declared in VariableDeclaration.jrag at line 81
    public boolean Define_boolean_isConstructorParameter(ASTNode caller, ASTNode child) {
        if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return false;
        }
        return getParent().Define_boolean_isConstructorParameter(this, caller);
    }

    // Declared in LookupVariable.jrag at line 46
    public SimpleSet Define_SimpleSet_lookupVariable(ASTNode caller, ASTNode child, String name) {
        if(caller == getParameterListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return parameterDeclaration(name);
        }
        if(caller == getBlockOptNoTransform()){
    SimpleSet set = parameterDeclaration(name);
    // A declaration of a method parameter name shadows any other variable declarations
    if(!set.isEmpty()) return set;
    // Delegate to other declarations in scope
    return lookupVariable(name);
  }
        return getParent().Define_SimpleSet_lookupVariable(this, caller, name);
    }

    // Declared in LocalNum.jrag at line 45
    public int Define_int_resultSaveLocalNum(ASTNode caller, ASTNode child) {
        if(caller == getBlockOptNoTransform()) {
            return offsetAfterParameters();
        }
        return getParent().Define_int_resultSaveLocalNum(this, caller);
    }

    // Declared in layer_decl.jrag at line 94
    public boolean Define_boolean_mayBeStaticActive(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return false;
        }
        return getParent().Define_boolean_mayBeStaticActive(this, caller);
    }

    // Declared in Modifiers.jrag at line 277
    public boolean Define_boolean_mayBeStrictfp(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBeStrictfp(this, caller);
    }

    // Declared in Modifiers.jrag at line 272
    public boolean Define_boolean_mayBeAbstract(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBeAbstract(this, caller);
    }

    // Declared in UnreachableStatements.jrag at line 33
    public boolean Define_boolean_reachable(ASTNode caller, ASTNode child) {
        if(caller == getBlockOptNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_reachable(this, caller);
    }

    // Declared in DefiniteAssignment.jrag at line 438
    public boolean Define_boolean_isDAbefore(ASTNode caller, ASTNode child, Variable v) {
        if(caller == getBlockOptNoTransform()) {
            return v.isFinal() && (v.isClassVariable() || v.isInstanceVariable()) ? true : isDAbefore(v);
        }
        return getParent().Define_boolean_isDAbefore(this, caller, v);
    }

    // Declared in TypeCheck.jrag at line 405
    public TypeDecl Define_TypeDecl_returnType(ASTNode caller, ASTNode child) {
        if(caller == getBlockOptNoTransform()) {
            return type();
        }
        return getParent().Define_TypeDecl_returnType(this, caller);
    }

    // Declared in LocalNum.jrag at line 52
    public int Define_int_localNum(ASTNode caller, ASTNode child) {
        if(caller == getBlockOptNoTransform()) {
            return offsetAfterParameters() + 
      resultOffset();
        }
        if(caller == getParameterListNoTransform()) { 
   int index = caller.getIndexOfChild(child);
{
    if(index == 0)
      return offsetBeforeParameters();
    return getParameter(index-1).localNum() + getParameter(index-1).type().variableSize();
  }
}
        return getParent().Define_int_localNum(this, caller);
    }

    // Declared in Modifiers.jrag at line 275
    public boolean Define_boolean_mayBeSynchronized(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBeSynchronized(this, caller);
    }

    // Declared in NameCheck.jrag at line 241
    public ASTNode Define_ASTNode_enclosingBlock(ASTNode caller, ASTNode child) {
        if(caller == getBlockOptNoTransform()) {
            return this;
        }
        return getParent().Define_ASTNode_enclosingBlock(this, caller);
    }

    // Declared in Modifiers.jrag at line 273
    public boolean Define_boolean_mayBeStatic(ASTNode caller, ASTNode child) {
        if(caller == getModifiersNoTransform()) {
            return true;
        }
        return getParent().Define_boolean_mayBeStatic(this, caller);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
