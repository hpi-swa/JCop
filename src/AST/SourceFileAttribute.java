
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class SourceFileAttribute extends Attribute {
    // Declared in Attributes.jrag at line 38

    public SourceFileAttribute(ConstantPool p, String sourcefile) {
      super(p, "SourceFile");
      u2(p.addUtf8(sourcefile));
    }


}
