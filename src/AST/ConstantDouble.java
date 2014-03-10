
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantDouble extends CPInfo {
    // Declared in ConstantPool.jrag at line 430

    private double val;

    // Declared in ConstantPool.jrag at line 431

    public ConstantDouble(double val) {
      this.val = val;
    }

    // Declared in ConstantPool.jrag at line 434

    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Double);
      out.writeDouble(val);
    }

    // Declared in ConstantPool.jrag at line 438

    public int size() {
      return 2;
    }

    // Declared in ConstantPool.jrag at line 441

    public String toString() {
      return pos + " ConstantDouble: tag " + ConstantPool.CONSTANT_Double + ", bytes: " + val;
    }


}
