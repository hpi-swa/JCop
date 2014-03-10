
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantMethodref extends CPInfo {
    // Declared in ConstantPool.jrag at line 305

    private int classname;

    // Declared in ConstantPool.jrag at line 306

    private int nameandtype;

    // Declared in ConstantPool.jrag at line 307

    public ConstantMethodref(int classname, int nameandtype) {
      this.classname = classname;
      this.nameandtype = nameandtype;
    }

    // Declared in ConstantPool.jrag at line 312


    public void emit(DataOutputStream out) throws IOException {
      out.writeByte(ConstantPool.CONSTANT_Methodref);
      out.writeChar(classname);
      out.writeChar(nameandtype);
    }

    // Declared in ConstantPool.jrag at line 317

    public String toString() {
      return pos + " ConstantMethodref: tag " + ConstantPool.CONSTANT_Methodref + ", class_index: " + classname + ", name_and_type_index: " + nameandtype;
    }


}
