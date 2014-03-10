
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;
      
      
//SubjectLayerDecl : LayerDecl ::= 
   //Modifiers <ID:String> [SuperClassAccess:Access] Implements:Access* SubjectTypeAccess:Access BodyDecl* ;

// ------------------------------------------------------------
// behavioral variations
// ------------------------------------------------------------
//abstract PartialMethodDecl:MethodDecl;


//Modifiers TypeAccess:Access <ID:String> Parameter:ParameterDeclaration* Exception:Access* [Block];


public class PartialMethodDecl extends MethodDecl implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public PartialMethodDecl clone() throws CloneNotSupportedException {
        PartialMethodDecl node = (PartialMethodDecl)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public PartialMethodDecl copy() {
      try {
          PartialMethodDecl node = (PartialMethodDecl)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public PartialMethodDecl fullCopy() {
        PartialMethodDecl res = (PartialMethodDecl)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in graphGeneration.jrag at line 160
    

  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in partial_method_decl.jrag at line 27


//  rewrite PartialMethodDecl { 
//    to ASTNode { 
//    	if(jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled())
//			return super.rewriteTo();
//	 List<Expr> args = new jcop.generation.Generator().generateArgs(getParameterList());
//      //String proceedOrder = getProceedOrder();
//      Block block = null;
////      if (proceedOrder.equals(jcop.Globals.Keywords.beforeMethod))
////    	  block = jcop.transformation.PartialMethodTransformer.getBlockForBeforeMethod(this);
////      else if (proceedOrder.equals(jcop.Globals.Keywords.afterMethod))
////    	  block = jcop.transformation.PartialMethodTransformer.getBlockForAfterMethod(this);
////      else
////    	  System.out.println("wrong proceed keyword: '" + proceedOrder + "'");  
//	
//	  return new MethodDecl(
//			  getModifiers().fullCopy(),
//			  (Access)getTypeAccess().fullCopy(),
//			  getID(),
//			  getParameterList().fullCopy(),
//			  getExceptionList().fullCopy(),
//			  new Opt<Block>(block)		 
//	  );
//    }}
	  
	   public TypeDecl hostType() {
		 NamePattern p = getNamePatternNoTransform();		
		 if (p instanceof DotNamePattern) {	 
			  NamePattern typePattern = ((DotNamePattern)p).getLhs();			
			 TypeDecl found =  lookupHostType(typePattern);		
			 return found;
		 }
		 jcop.compiler.JCopCompiler.abort(sourceFile(), sourceLineNumber(), "Cannot find base declaration for partial method: %s in %s", p , getEnclosingLayer().getFullQualifiedName() );
		 return null;
	  }

    // Declared in partial_method_decl.jrag at line 38
  
	  
    private LayerDecl getEnclosingLayer() {
		try {
			return ((LayerDecl)parent.parent);
		}
		catch(ClassCastException e) {    			
			jcop.compiler.JCopCompiler.abort(sourceFile(), sourceLineNumber(), "Enclosing type declaration of partial method '%s' is not a layer declaration!", getID() );
			return null;
		}
   }

    // Declared in partial_method_decl.jrag at line 48

   
    private ClassDecl lookupHostType(NamePattern typePattern) {    	
    	if (typePattern instanceof DotNamePattern) {    		
			 DotNamePattern dtp = (DotNamePattern)typePattern;			 
			 String pckg = dtp.getLhs().toString();			
			 String typeName = dtp.getRhs().toString();			 
			 ClassDecl type = (ClassDecl)lookupType(pckg, typeName);			 
			 if (type != null)  {			
				 return type; 
			 }
								
			 ClassDecl host = lookupHostType(dtp.getLhs());
			 if (host != null) {		
				 for (BodyDecl element : host.getBodyDecls()) {
					 if(element instanceof MemberClassDecl) {					 
						 ClassDecl nestedType = ((MemberClassDecl)element).getClassDecl();					
						 if (nestedType.getID().equals(typeName)) {							 
							 return nestedType;
						 }
					 }			 
				 }
			 }
    	}
		jcop.compiler.JCopCompiler.abort(sourceFile(), sourceLineNumber(), jcop.Globals.Msg.pmdHostNotFound, getNamePattern() );
		return null;    	
			
		// TODO Auto-generated method stub
		
	}

    // Declared in partial_method_decl.jrag at line 77

  
  	public String getProceedOrder() {
		for (Modifier modifier : getModifiers().getModifierList())
			if (isPreceedenceModifier(modifier))
				return modifier.getID();
		return "";
	}

    // Declared in partial_method_decl.jrag at line 84


	private boolean isPreceedenceModifier(Modifier modifier) {
		String id = modifier.getID();
		return id.equals("before") || id.equals("after");
	}

    // Declared in partial_method_decl.jrag at line 89


    public String signature() {    	
    	StringBuffer s = new StringBuffer();
    	String name = getNamePattern().toString();    	
    	name = name.substring(name.lastIndexOf(".") + 1);    	
    	s.append(name);
    	s.append("(");
    	for(int i = 0; i < getNumParameter(); i++) {
    		if(i != 0) s.append(", ");
    	    s.append(getParameter(i).type().erasure().typeName());
    	}
        s.append(")");    	  			
  		return s.toString();
  	}

    // Declared in partial_method_decl.jrag at line 103


	public String getID() {	
	    	String fqn = getNamePattern().toString();
	    	String id = fqn.substring(fqn.lastIndexOf(".") + 1);
	    	return id;	      	
	    }

    // Declared in partial_method_decl.jrag at line 110

	

	    public String name() {
	      	return getNamePattern().toString();
	    }

    // Declared in partial_method_decl.jrag at line 115



	public void toString(StringBuffer s) {
		s.append(indent());
		getModifiers().toString(s);
		getTypeAccess().toString(s);
		s.append(" ");
		getNamePattern().toString(s);
		s.append("("); 
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

    // Declared in partial_method_decl.jrag at line 168

  
   public boolean isPartialMethod() {
   		return true;
   }

    // Declared in xmlOutline.jrag at line 28

  
//	public void LayerImportDecl.printOutline(StringBuffer s) { 
//        s.append("<import kind=\"layer\" path=\"" + getAccess().toString() + "\" />\n");
//	}
  
  public void printOutline(StringBuffer s) { 
	s.append("<partial_method name=\"" + getNamePattern() + "\"" +  
			" proceed=\"" + getID() + "\"" +
     			" type=\"" + type().name() + "\"" +
     			" line=\"" + sourceLineNumber() + "\" >\n");
	getModifiers().printOutline(s);	
	s.append("<params>\n"); 
	for (ParameterDeclaration decl : getParameters())
	  decl.printOutline(s);
	s.append("</params>\n"); 
	s.append("</partial_method>\n"); 
   }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 35

    public PartialMethodDecl() {
        super();

        setChild(new List(), 2);
        setChild(new List(), 3);
        setChild(new Opt(), 4);

    }

    // Declared in jcop.ast at line 13


    // Declared in jcop.ast line 35
    public PartialMethodDecl(Modifiers p0, Access p1, String p2, List<ParameterDeclaration> p3, List<Access> p4, Opt<Block> p5, NamePattern p6) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setChild(p3, 2);
        setChild(p4, 3);
        setChild(p5, 4);
        setChild(p6, 5);
    }

    // Declared in jcop.ast at line 24


    // Declared in jcop.ast line 35
    public PartialMethodDecl(Modifiers p0, Access p1, beaver.Symbol p2, List<ParameterDeclaration> p3, List<Access> p4, Opt<Block> p5, NamePattern p6) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setChild(p3, 2);
        setChild(p4, 3);
        setChild(p5, 4);
        setChild(p6, 5);
    }

    // Declared in jcop.ast at line 34


  protected int numChildren() {
    return 6;
  }

    // Declared in jcop.ast at line 37

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

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 35
    public void setNamePattern(NamePattern node) {
        setChild(node, 5);
    }

    // Declared in jcop.ast at line 5

    public NamePattern getNamePattern() {
        return (NamePattern)getChild(5);
    }

    // Declared in jcop.ast at line 9


    public NamePattern getNamePatternNoTransform() {
        return (NamePattern)getChildNoTransform(5);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
