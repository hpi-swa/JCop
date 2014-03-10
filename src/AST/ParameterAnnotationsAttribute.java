
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ParameterAnnotationsAttribute extends Attribute {
    // Declared in AnnotationsCodegen.jrag at line 128

    public ParameterAnnotationsAttribute(ConstantPool cp, Collection annotations, String name) {
      super(cp, name);
      u1(annotations.size());
      for(Iterator iter = annotations.iterator(); iter.hasNext(); ) {
        Collection c = (Collection)iter.next();
        for(Iterator inner = c.iterator(); inner.hasNext(); ) {
          Annotation a = (Annotation)inner.next();
          a.appendAsAttributeTo(this);
        }
      }
    }


}
