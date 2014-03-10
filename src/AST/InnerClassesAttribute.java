
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class InnerClassesAttribute extends Attribute {
    // Declared in Attributes.jrag at line 69

    public InnerClassesAttribute(TypeDecl typeDecl) {
      super(typeDecl.constantPool(), "InnerClasses");
      ConstantPool c = typeDecl.constantPool();
      Collection list = typeDecl.innerClassesAttributeEntries();
      u2(list.size());
      for(Iterator iter = list.iterator(); iter.hasNext(); ) {
        TypeDecl type = (TypeDecl)iter.next();
        u2(c.addClass(type.constantPoolName())); // inner_class_info_index
        u2(type.isMemberType() ? c.addClass(type.enclosingType().constantPoolName()) : 0); // outer_class_info_index
        u2(type.isAnonymous() ? 0 : c.addUtf8(type.name())); // inner_name_index
        u2(type.isInterfaceDecl() ? (type.flags() | Modifiers.ACC_INTERFACE) : type.flags()); // inner_class_access_flags
      }
    }


}
