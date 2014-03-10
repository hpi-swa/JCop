
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public interface VariableScope {
    // Declared in LookupVariable.jrag at line 220

    public SimpleSet lookupVariable(String name);

}
