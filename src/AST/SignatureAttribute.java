
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class SignatureAttribute extends Attribute {
    // Declared in GenericsCodegen.jrag at line 321

    public SignatureAttribute(ConstantPool cp, String signature) {
      super(cp, "Signature");
      u2(cp.addUtf8(signature));
    }


}
