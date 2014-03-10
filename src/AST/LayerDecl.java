
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;
// ============================================================
// JCop Language Specification: Abstract Syntax
//
// Malte Appeltauer
// Software Architecture Group, Hasso-Plattner-Institute
// ============================================================
// ------------------------------------------------------------
// layer
// ------------------------------------------------------------
//LayerInClass:MemberDecl ::=
//   <ID:String> 
//   TypeAccess:Access 
//   BodyDecl* ;   
//ClassInLayer:TypeDecl ::= 
//   <ID:String> 
//   BodyDecl* ;
   

public class LayerDecl extends ClassDecl implements Cloneable, LayerDeclaration {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerDecl clone() throws CloneNotSupportedException {
        LayerDecl node = (LayerDecl)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerDecl copy() {
      try {
          LayerDecl node = (LayerDecl)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerDecl fullCopy() {
        LayerDecl res = (LayerDecl)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in graphGeneration.jrag at line 152
    
  
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in layer_decl.jrag at line 37
 
		 
	
	/*
	public boolean LayerDecl.checkLayerDeclaration() {
	   List<ImportDecl> imports = hostType().compilationUnit().getImportDecls();
	   for (ImportDecl importDecl : imports) {		
			String importId = importDecl.getAccess().type().typeName();		
		    if(importId.equals(jcop.Globals.jcopPackage + "." + getID()))
		    	return true;		
	   }	   
	   error("Layer " + getID() + " is not declared in compilation unit");
	   return false;
	}
	*/
	
	public MethodDecl lookupMethodCorrespondingTo(PartialMethodDecl partialMethodDecl) {
		String type = partialMethodDecl.getNamePatternNoTransform().toString();
		type = type.substring(0, type.lastIndexOf("."));		
		String pckg = type.substring(0, type.lastIndexOf("."));
		String name = type.substring(type.lastIndexOf(".") + 1);		
		TypeDecl decl = lookupType(pckg, name);		
		List<BodyDecl> bodyDecls = decl.getBodyDeclListNoTransform();
	
		for (BodyDecl bodyDecl : bodyDecls) {	
			if (bodyDecl instanceof MethodDecl) {
				String signature = ((MethodDecl) bodyDecl).signature();
				String pmSignature = partialMethodDecl.signature();
				if (signature.equals(pmSignature))				
					return (MethodDecl) bodyDecl;
			}
		}		
		return null;	
	}

    // Declared in layer_decl.jrag at line 56
  
	
	public ClassDecl hostLayer() {
		return this;
	}

    // Declared in layer_decl.jrag at line 60

	
	public boolean hasSubjectTypeDecl() {
		return getSubjectTypessNoTransform().getNumChildNoTransform() > 0;
	}

    // Declared in layer_decl.jrag at line 64

	
    @Override
    public void toString(StringBuffer s) {
    	s.append("layer ");
    	s.append(getID());
    	toString(s, "subject", getSubjectTypess());
     	if (hasSuperClassAccess()){
    		s.append("extends"); 	
    		getSuperClassAccess().toString(s);
    	}	
    	s.append(" {");    	
    	for (BodyDecl bodyDecl : getBodyDecls())
    		bodyDecl.toString(s);
    	s.append("}\n");  	
    }

    // Declared in layer_decl.jrag at line 79

    
    private void toString(StringBuffer s, String keyword, List<Access> typeList) {    	
    	if (typeList.getNumChild() > 0) {
    		s.append(' ').append(keyword).append(' ');
    		for (Access type : typeList)
    			typeList.toString(s);
    	}
    }

    // Declared in layer_decl.jrag at line 112

    
    private String getPointcutExpression(ParseName typeAccess, String typeName) {
    	String expr = String.format("cflow( call(* %s.*(..)) ) && execution(* %s.*(..) )",  typeAccess, typeName);
		return String.format( "( %s )  && !cflowbelow( %s )", expr);		
	}

    // Declared in xmlOutline.jrag at line 41


   public void printOutline(StringBuffer s) { 
    s.append("<layer name=\"" + getID() + "\" line=\"" + sourceLineNumber() + "\">\n");
	for (BodyDecl decl : getBodyDecls())
	  decl.printOutline(s);
    s.append("</layer>\n"); 
  }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 18

    public LayerDecl() {
        super();

        setChild(new List(), 0);
        setChild(new List(), 2);
        setChild(new Opt(), 3);
        setChild(new List(), 4);

    }

    // Declared in jcop.ast at line 14


    // Declared in jcop.ast line 18
    public LayerDecl(List<Access> p0, Modifiers p1, String p2, List<Access> p3, Opt<Access> p4, List<BodyDecl> p5) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setChild(p3, 2);
        setChild(p4, 3);
        setChild(p5, 4);
    }

    // Declared in jcop.ast at line 24


    // Declared in jcop.ast line 18
    public LayerDecl(List<Access> p0, Modifiers p1, beaver.Symbol p2, List<Access> p3, Opt<Access> p4, List<BodyDecl> p5) {
        setChild(p0, 0);
        setChild(p1, 1);
        setID(p2);
        setChild(p3, 2);
        setChild(p4, 3);
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
    public void setImplementsList(List<Access> list) {
        setChild(list, 0);
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
        return (List<Access>)getChild(0);
    }

    // Declared in java.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Access> getImplementsListNoTransform() {
        return (List<Access>)getChildNoTransform(0);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 18
    public void setModifiers(Modifiers node) {
        setChild(node, 1);
    }

    // Declared in jcop.ast at line 5

    public Modifiers getModifiers() {
        return (Modifiers)getChild(1);
    }

    // Declared in jcop.ast at line 9


    public Modifiers getModifiersNoTransform() {
        return (Modifiers)getChildNoTransform(1);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 18
    public void setSubjectTypesList(List<Access> list) {
        setChild(list, 2);
    }

    // Declared in jcop.ast at line 6


    private int getNumSubjectTypes = 0;

    // Declared in jcop.ast at line 7

    public int getNumSubjectTypes() {
        return getSubjectTypesList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Access getSubjectTypes(int i) {
        return (Access)getSubjectTypesList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addSubjectTypes(Access node) {
        List<Access> list = getSubjectTypesList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setSubjectTypes(Access node, int i) {
        List<Access> list = getSubjectTypesList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<Access> getSubjectTypess() {
        return getSubjectTypesList();
    }

    // Declared in jcop.ast at line 27

    public List<Access> getSubjectTypessNoTransform() {
        return getSubjectTypesListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Access> getSubjectTypesList() {
        return (List<Access>)getChild(2);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Access> getSubjectTypesListNoTransform() {
        return (List<Access>)getChildNoTransform(2);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 18
    public void setSuperClassAccessOpt(Opt<Access> opt) {
        setChild(opt, 3);
    }

    // Declared in jcop.ast at line 6


    public boolean hasSuperClassAccess() {
        return getSuperClassAccessOpt().getNumChild() != 0;
    }

    // Declared in jcop.ast at line 10


     @SuppressWarnings({"unchecked", "cast"})  public Access getSuperClassAccess() {
        return (Access)getSuperClassAccessOpt().getChild(0);
    }

    // Declared in jcop.ast at line 14


    public void setSuperClassAccess(Access node) {
        getSuperClassAccessOpt().setChild(node, 0);
    }

    // Declared in jcop.ast at line 17

     @SuppressWarnings({"unchecked", "cast"})  public Opt<Access> getSuperClassAccessOpt() {
        return (Opt<Access>)getChild(3);
    }

    // Declared in jcop.ast at line 21


     @SuppressWarnings({"unchecked", "cast"})  public Opt<Access> getSuperClassAccessOptNoTransform() {
        return (Opt<Access>)getChildNoTransform(3);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 18
    public void setBodyDeclList(List<BodyDecl> list) {
        setChild(list, 4);
    }

    // Declared in jcop.ast at line 6


    private int getNumBodyDecl = 0;

    // Declared in jcop.ast at line 7

    public int getNumBodyDecl() {
        return getBodyDeclList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public BodyDecl getBodyDecl(int i) {
        return (BodyDecl)getBodyDeclList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addBodyDecl(BodyDecl node) {
        List<BodyDecl> list = getBodyDeclList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setBodyDecl(BodyDecl node, int i) {
        List<BodyDecl> list = getBodyDeclList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<BodyDecl> getBodyDecls() {
        return getBodyDeclList();
    }

    // Declared in jcop.ast at line 27

    public List<BodyDecl> getBodyDeclsNoTransform() {
        return getBodyDeclListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclList() {
        return (List<BodyDecl>)getChild(4);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclListNoTransform() {
        return (List<BodyDecl>)getChildNoTransform(4);
    }

    // Declared in layer_decl.jrag at line 102
 @SuppressWarnings({"unchecked", "cast"})     public boolean isLayerDecl() {
        boolean isLayerDecl_value = isLayerDecl_compute();
        return isLayerDecl_value;
    }

    private boolean isLayerDecl_compute() {  return true;  }

    // Declared in layer_decl.jrag at line 99
    public boolean Define_boolean_mayBeStaticActive(ASTNode caller, ASTNode child) {
        if(caller == getBodyDeclListNoTransform()) {
      int childIndex = caller.getIndexOfChild(child);
            return true;
        }
        return super.Define_boolean_mayBeStaticActive(caller, child);
    }

public ASTNode rewriteTo() {
    // Declared in layer_decl.jrag at line 3
        duringlayer_decl++;
        ASTNode result = rewriteRule0();
        duringlayer_decl--;
        return result;
}

    // Declared in layer_decl.jrag at line 3
    private ASTNode rewriteRule0() {
{	 	 	
	   jcop.compiler.JCopCompiler.setInstanceLayerExits(true);
	   boolean noTransformations = jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled();						
		if (isTopLevelType()) {
			
			jcop.transformation.LayerDeclTransformer layerTransformer =
				new jcop.transformation.LayerDeclTransformer(this);
			
			if(noTransformations) {
				//layerTransformer.addLayerField();				
				return super.rewriteTo();
			}
			else
				return layerTransformer.errorCheckAndTransform(hostType().compilationUnit());
		}
		else 	
			throw new RuntimeException("Layer Decl is not Toplevel...");
    }    }
}
