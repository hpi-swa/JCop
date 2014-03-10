
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class EnclosingMethod extends Attribute {
    // Declared in EnclosingMethodAttribute.jrag at line 21

    public EnclosingMethod(ConstantPool cp, TypeDecl typeDecl) {
      super(cp, "EnclosingMethod");
      u2(cp.addClass(typeDecl.enclosingType().constantPoolName()));
      BodyDecl b = typeDecl.enclosingBodyDecl();
      if(b instanceof MethodDecl) {
        MethodDecl m = (MethodDecl)b;
        u2(cp.addNameAndType(m.name(), m.descName()));
      }
      else if(b instanceof ConstructorDecl) {
        ConstructorDecl m = (ConstructorDecl)b;
        u2(cp.addNameAndType(m.name(), m.descName()));
      }
      else {
        u2(0);
      }
    }


}
