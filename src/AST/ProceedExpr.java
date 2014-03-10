
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


/*LocalPartialMethodDecl:PartialMethodDecl ::=   
   Modifiers 
   TypeAccess:Access 
   <ID:String> 
   Parameter:ParameterDeclaration* 
   Exception:Access* 
   [Block] ;*/
   

public class ProceedExpr extends Access implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public ProceedExpr clone() throws CloneNotSupportedException {
        ProceedExpr node = (ProceedExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ProceedExpr copy() {
      try {
          ProceedExpr node = (ProceedExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public ProceedExpr fullCopy() {
        ProceedExpr res = (ProceedExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in graphGeneration.jrag at line 136
 
  
  // JCOP
  public jcop.output.graph.INode createGraphNode() {
  	return jcop.output.graph.GraphGeneratorFactory.getInstance().visit(this);	 
  }

    // Declared in proceed.jrag at line 15
	
   
   public void toString(StringBuffer s) {   
	  s.append("proceed(");
	  for(Expr arg : getArgs()) {
	    arg.toString(s);
	    s.append(", ");
	  }
	  if (getArgs().numChildren() > 0)
		  s.delete(s.length()-2, s.length());
	  s.append(")");		
	}

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 52

    public ProceedExpr() {
        super();

        setChild(new List(), 0);

    }

    // Declared in jcop.ast at line 11


    // Declared in jcop.ast line 52
    public ProceedExpr(List<Expr> p0) {
        setChild(p0, 0);
    }

    // Declared in jcop.ast at line 15


  protected int numChildren() {
    return 1;
  }

    // Declared in jcop.ast at line 18

  public boolean mayHaveRewrite() { return true; }

    // Declared in jcop.ast at line 2
    // Declared in jcop.ast line 52
    public void setArgList(List<Expr> list) {
        setChild(list, 0);
    }

    // Declared in jcop.ast at line 6


    private int getNumArg = 0;

    // Declared in jcop.ast at line 7

    public int getNumArg() {
        return getArgList().getNumChild();
    }

    // Declared in jcop.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Expr getArg(int i) {
        return (Expr)getArgList().getChild(i);
    }

    // Declared in jcop.ast at line 15


    public void addArg(Expr node) {
        List<Expr> list = getArgList();
        list.addChild(node);
    }

    // Declared in jcop.ast at line 20


    public void setArg(Expr node, int i) {
        List<Expr> list = getArgList();
        list.setChild(node, i);
    }

    // Declared in jcop.ast at line 24

    public List<Expr> getArgs() {
        return getArgList();
    }

    // Declared in jcop.ast at line 27

    public List<Expr> getArgsNoTransform() {
        return getArgListNoTransform();
    }

    // Declared in jcop.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Expr> getArgList() {
        return (List<Expr>)getChild(0);
    }

    // Declared in jcop.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Expr> getArgListNoTransform() {
        return (List<Expr>)getChildNoTransform(0);
    }

    // Declared in proceed.jrag at line 2
 @SuppressWarnings({"unchecked", "cast"})     public TypeDecl type() {
        TypeDecl type_value = type_compute();
        return type_value;
    }

    private TypeDecl type_compute() {  return ((MethodDecl)enclosingBodyDecl()).type();  }

public ASTNode rewriteTo() {
    // Declared in proceed.jrag at line 4
        duringproceed++;
        ASTNode result = rewriteRule0();
        duringproceed--;
        return result;
}

    // Declared in proceed.jrag at line 4
    private ASTNode rewriteRule0() {
{
    	  if(jcop.compiler.CompilerConfiguration.getInstance().astTransformationsDisabled())
			return super.rewriteTo();
		else if (jcop.VisitedNodes.firstVisit(this))
			return new jcop.transformation.ProceedTransformer(this).errorCheckAndTransform(hostType().compilationUnit());	
	  	else 
	  		return super.rewriteTo();
     }    }
}
