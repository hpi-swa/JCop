
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public interface NamedMember {
    // Declared in interfaces.jrag at line 24
		    
		    public TypeDecl type();

    // Declared in interfaces.jrag at line 25

		    public Modifiers getModifiersNoTransform();

    // Declared in interfaces.jrag at line 26

		    public Modifiers getModifiers();

    // Declared in interfaces.jrag at line 27

		    public String getID();

}
