
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantInteger extends CPInfo {
    // Declared in ConstantPool.jrag at line 385

    private int val;

    // Declared in ConstantPool.jrag at line 386

    public ConstantInteger(int val) {
      this.val = val;
    }

    // Declared in ConstantPool.jrag at line 389

    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Integer);
      out.writeInt(val);
    }

    // Declared in ConstantPool.jrag at line 393

    public String toString() {
      return pos + " ConstantInteger: tag " + ConstantPool.CONSTANT_Integer + ", bytes: " + val;
    }


}
