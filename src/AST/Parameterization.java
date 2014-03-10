
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public interface Parameterization {
    // Declared in Generics.jrag at line 670

    boolean isRawType();

    // Declared in Generics.jrag at line 671

    TypeDecl substitute(TypeVariable typeVariable);

}
