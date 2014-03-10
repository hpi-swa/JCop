
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantInterfaceMethodref extends CPInfo {
    // Declared in ConstantPool.jrag at line 323

    private int classname;

    // Declared in ConstantPool.jrag at line 324

    private int nameandtype;

    // Declared in ConstantPool.jrag at line 325

    public ConstantInterfaceMethodref(int classname, int nameandtype) {
      this.classname = classname;
      this.nameandtype = nameandtype;
    }

    // Declared in ConstantPool.jrag at line 329

    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_InterfaceMethodref);
      out.writeChar(classname);
      out.writeChar(nameandtype);
    }

    // Declared in ConstantPool.jrag at line 334

    public String toString() {
      return pos + " ConstantInterfaceMethodref: tag " + ConstantPool.CONSTANT_InterfaceMethodref + ", class_index: " + classname + ", name_and_type_index: " + nameandtype;
    }


}
