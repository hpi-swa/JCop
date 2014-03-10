
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantValueAttribute extends Attribute {
    // Declared in Attributes.jrag at line 51

    public ConstantValueAttribute(ConstantPool p, FieldDeclaration f) {
      super(p, "ConstantValue");
      int constantvalue_index = constantvalue_index = f.type().addConstant(p, f.getInit().constant());
      u2(constantvalue_index);
    }


}
