
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class LocalVariableTableAttribute extends Attribute {
    // Declared in Attributes.jrag at line 95

    public LocalVariableTableAttribute(CodeGeneration gen) {
      super(gen.constantPool(), "LocalVariableTable");
      u2(gen.localVariableTable.size());
      for(Iterator iter = gen.localVariableTable.iterator(); iter.hasNext(); ) {
        CodeGeneration.LocalVariableEntry e = (CodeGeneration.LocalVariableEntry)iter.next();
        u2(e.start_pc);
        u2(e.length);
        u2(e.name_index);
        u2(e.descriptor_index);
        u2(e.index);
      }
    }


}
