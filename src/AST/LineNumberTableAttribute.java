
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class LineNumberTableAttribute extends Attribute {
    // Declared in Attributes.jrag at line 110

    public LineNumberTableAttribute(CodeGeneration gen) {
      super(gen.constantPool(), "LineNumberTable");
      u2(gen.lineNumberTable.size());
      for(Iterator iter = gen.lineNumberTable.iterator(); iter.hasNext(); ) {
        CodeGeneration.LineNumberEntry e = (CodeGeneration.LineNumberEntry)iter.next();
        u2(e.start_pc);
        u2(e.line_number);
      }
    }


}
