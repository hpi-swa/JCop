
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantLong extends CPInfo {
    // Declared in ConstantPool.jrag at line 413

    private long val;

    // Declared in ConstantPool.jrag at line 414

    public ConstantLong(long val) {
      this.val = val;
    }

    // Declared in ConstantPool.jrag at line 417

    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Long);
      out.writeLong(val);
    }

    // Declared in ConstantPool.jrag at line 421

    public int size() {
      return 2;
    }

    // Declared in ConstantPool.jrag at line 424

    public String toString() {
      return pos + " ConstantLong: tag " + ConstantPool.CONSTANT_Long + ", bytes: " + val;
    }


}
