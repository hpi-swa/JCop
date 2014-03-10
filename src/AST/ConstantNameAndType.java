
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantNameAndType extends CPInfo {
    // Declared in ConstantPool.jrag at line 340

    private int name;

    // Declared in ConstantPool.jrag at line 341

    private int type;

    // Declared in ConstantPool.jrag at line 342

    public ConstantNameAndType(int name, int type) {
      this.name = name;
      this.type = type;
    }

    // Declared in ConstantPool.jrag at line 346

    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_NameAndType);
      out.writeChar(name);
      out.writeChar(type);
    }

    // Declared in ConstantPool.jrag at line 351

    public String toString() {
      return pos + " NameAndType: tag " + ConstantPool.CONSTANT_NameAndType + ", name_index: " + name + ", descriptor_index: " + type;
    }


}
