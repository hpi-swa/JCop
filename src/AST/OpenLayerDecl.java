
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;
   


public class OpenLayerDecl extends MemberDecl implements Cloneable, LayerDeclaration {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public OpenLayerDecl clone() throws CloneNotSupportedException {
        OpenLayerDecl node = (OpenLayerDecl)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public OpenLayerDecl copy() {
      try {
          OpenLayerDecl node = (OpenLayerDecl)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public OpenLayerDecl fullCopy() {
        OpenLayerDecl res = (OpenLayerDecl)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in graphGeneration.jrag at line 13

  
  public jcop.output.graph.INode createGraphNode() {
	    return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);
 }

    // Declared in open_layer_decl.jrag at line 22

  
  public boolean layerIsDeclared() {				
	for (ImportDecl importDecl : getImportsOfEnclClass()) {
		String importId = importDecl.getAccess().type().name();
		if (importId.equals(getID()))
			return true;
	}					
	error("Layer " + getID()+ " is not declared in compilation unit");
	return false;
  }

    // Declared in open_layer_decl.jrag at line 32
	
  
	private List<ImportDecl> getImportsOfEnclClass() {
		 return hostType().compilationUnit().getImportDecls();
	}

    // Declared in open_layer_decl.jrag at line 36


	public String getID() {
		return getTypeName().toString();
	}

    // Declared in open_layer_decl.jrag at line 40

  	
	public ClassDecl hostLayer() {
		String pckg = getTypeName().packageName();		
		return (ClassDecl)lookupType(getID()).iterator().next();
	}

    // Declared in open_layer_decl.jrag at line 45

	
	public void toString(StringBuffer s) {
		s.append("layer ");
		s.append(getID());
		s.append(" {");		
		for (BodyDecl bodyDecl : getBodyDecls())
			bodyDecl.toString(s);
		s.append("}\n");  	
	}

    // Declared in xmlOutline.jrag at line 48

  
     public void printOutline(StringBuffer s) { 
    s.append("<layer name=\"" + getID() + "\" line=\"" + sourceLineNumber() + "\">\n");
	for (BodyDecl decl : getBodyDecls())
	  decl.printOutline(s);
    s.append("</layer>\n"); 
  }

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 21

    public OpenLayerDecl() {
        super();

        setChild(new List(), 1);

    }

    // Declared in jcop.ast at line 11


    // Declared in jcop.ast line 21
    public OpenLayerDecl(Access p0, List<BodyDecl> p1) {
        setChild(p0, 0);
        setChild(p1, 1);
    }

    // Declared in jcop.ast at line 16


  protected int numChildren() {
    return 2;
  }

    // Declared in jcop.ast at line 19

  public boolean mayHaveRewrite() { return true; }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 21
    public void setTypeName(Access node) {
        setChild(node, 0);
    }

    // Declared in jcop.ast at line 5

    public Access getTypeName() {
        return (Access)getChild(0);
    }

    // Declared in jcop.ast at line 9


    public Access getTypeNameNoTransform() {
        return (Access)getChildNoTransform(0);
    }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 21
    public void setBodyDeclList(List<BodyDecl> list) {
        setChild(list, 1);
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
        return (List<BodyDecl>)getChild(1);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<BodyDecl> getBodyDeclListNoTransform() {
        return (List<BodyDecl>)getChildNoTransform(1);
    }

    // Declared in open_layer_decl.jrag at line 5
 @SuppressWarnings({"unchecked", "cast"})     public boolean isStatic() {
        boolean isStatic_value = isStatic_compute();
        return isStatic_value;
    }

    private boolean isStatic_compute() {  return true;  }

public ASTNode rewriteTo() {
    // Declared in open_layer_decl.jrag at line 11
        duringopen_layer_decl++;
        ASTNode result = rewriteRule0();
        duringopen_layer_decl--;
        return result;
}

    // Declared in open_layer_decl.jrag at line 11
    private ASTNode rewriteRule0() {
{	  
	   jcop.compiler.JCopCompiler.setInstanceLayerExits(true);
	   boolean noTransformations = jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled();		
			if(noTransformations) 				
				return super.rewriteTo();			
			//if (!layerIsDeclared())
			//	return new InstanceInitializer(new Block(new List<Stmt>()));
			return new jcop.transformation.OpenLayerDeclTransformer(this).errorCheckAndTransform(hostType().compilationUnit());		
   }    }
}
