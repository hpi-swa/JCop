
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


// ------------------------------------------------------------
// layer import declaration
// ------------------------------------------------------------

public class LayerImportDecl extends SingleTypeImportDecl implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerImportDecl clone() throws CloneNotSupportedException {
        LayerImportDecl node = (LayerImportDecl)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerImportDecl copy() {
      try {
          LayerImportDecl node = (LayerImportDecl)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public LayerImportDecl fullCopy() {
        LayerImportDecl res = (LayerImportDecl)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in layer_import_decl.jrag at line 10


    public void toString(StringBuffer b) {
       	b.append("import layer ");
       	b.append(getAccess().toString());
       	b.append(";");
    }

    // Declared in layer_import_decl.jrag at line 16


    public TypeDecl type() {
      	return getAccess().type();
    }

    // Declared in layer_import_decl.jrag at line 20

	
    private void addImport(String typename) {
       ((List)getParent()).add(
          new SingleTypeImportDecl(
	     	new TypeAccess(jcop.Globals.jcopPackage, typename)));
    }

    // Declared in layer_import_decl.jrag at line 26

    
    private static HashSet<CompilationUnit> visitedUnits = 
       new HashSet<CompilationUnit>();

    // Declared in jcop.ast at line 3
    // Declared in jcop.ast line 61

    public LayerImportDecl() {
        super();


    }

    // Declared in jcop.ast at line 10


    // Declared in jcop.ast line 61
    public LayerImportDecl(Access p0) {
        setChild(p0, 0);
    }

    // Declared in jcop.ast at line 14


  protected int numChildren() {
    return 1;
  }

    // Declared in jcop.ast at line 17

  public boolean mayHaveRewrite() { return true; }

    // Declared in java.ast at line 2
    // Declared in java.ast line 7
    public void setAccess(Access node) {
        setChild(node, 0);
    }

    // Declared in java.ast at line 5

    public Access getAccess() {
        return (Access)getChild(0);
    }

    // Declared in java.ast at line 9


    public Access getAccessNoTransform() {
        return (Access)getChildNoTransform(0);
    }

public ASTNode rewriteTo() {
    // Declared in layer_import_decl.jrag at line 2
        duringlayer_import_decl++;
        ASTNode result = rewriteRule0();
        duringlayer_import_decl--;
        return result;
}

    // Declared in layer_import_decl.jrag at line 2
    private ASTNode rewriteRule0() {
{	
		
	jcop.transformation.LayerImportDeclTransformer transformer = 
	   	new jcop.transformation.LayerImportDeclTransformer(this);	
	return transformer.errorCheckAndTransform((CompilationUnit)parent.parent);
    }    }
}
