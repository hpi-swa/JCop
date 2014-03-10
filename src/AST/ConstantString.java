
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantString extends CPInfo {
    // Declared in ConstantPool.jrag at line 371

    private int name;

    // Declared in ConstantPool.jrag at line 372

    public ConstantString(int name) {
      this.name = name;
    }

    // Declared in ConstantPool.jrag at line 375

    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_String);
      out.writeChar(name);
    }

    // Declared in ConstantPool.jrag at line 379

    public String toString() {
      return pos + " ConstantString: tag " + ConstantPool.CONSTANT_String + ", string_index: " + name;
    }


}
