
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class AnnotationsAttribute extends Attribute {
    // Declared in AnnotationsCodegen.jrag at line 55

    public AnnotationsAttribute(ConstantPool cp, Collection annotations, String name) {
      super(cp, name);
      u2(annotations.size());
      for(Iterator iter = annotations.iterator(); iter.hasNext(); )
        ((Annotation)iter.next()).appendAsAttributeTo(this);
    }


}
