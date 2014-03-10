
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class CodeAttribute extends Attribute {
    // Declared in Attributes.jrag at line 122

    public CodeAttribute(CodeGeneration codeGen, MethodDecl m) {
      super(codeGen.constantPool(), "Code");
      u2(codeGen.maxStackDepth());
      u2(codeGen.maxLocals());
      u4(codeGen.pos()); // code_length
      append(codeGen.toArray());
      u2(codeGen.exceptions.size());
      for(Iterator iter = codeGen.exceptions.iterator(); iter.hasNext(); ) {
        CodeGeneration.ExceptionEntry e = (CodeGeneration.ExceptionEntry)iter.next();
        u2(e.start_pc);
        u2(e.end_pc);
        u2(e.handler_pc);
        u2(e.catch_type);
      }

      if(m == null || !m.getModifiers().isSynthetic()) {
        u2(2); // Attribute count
        append(new LineNumberTableAttribute(codeGen));
        append(new LocalVariableTableAttribute(codeGen));
      }
      else {
        u2(0); // Attribute count
      }
    }


}
