
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public interface LayerDeclaration {
    // Declared in interfaces.jrag at line 11

		  public String getID();

    // Declared in interfaces.jrag at line 12

		  public TypeDecl hostType();

    // Declared in interfaces.jrag at line 13

		  public ClassDecl hostLayer();

    // Declared in interfaces.jrag at line 14

		  public List<BodyDecl> getBodyDecls();

    // Declared in interfaces.jrag at line 15

		  public String getFullQualifiedName();

}
