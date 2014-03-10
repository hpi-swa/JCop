
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;

public class ConstantPool extends java.lang.Object {
    // Declared in ConstantPool.jrag at line 39

    public TypeDecl typeDecl;

    // Declared in ConstantPool.jrag at line 40

    public ConstantPool(TypeDecl typeDecl) {
      this.typeDecl = typeDecl;
      //if(!typeDecl.isFinal)
      //  System.out.println("Warning: trying to create constant pool for non final type decl " + typeDecl.fullName());
    }

    // Declared in ConstantPool.jrag at line 46

    
    public static final byte CONSTANT_Class              = 7;

    // Declared in ConstantPool.jrag at line 47

    public static final byte CONSTANT_Fieldref           = 9;

    // Declared in ConstantPool.jrag at line 48

    public static final byte CONSTANT_Methodref          = 10;

    // Declared in ConstantPool.jrag at line 49

    public static final byte CONSTANT_InterfaceMethodref = 11;

    // Declared in ConstantPool.jrag at line 50

    public static final byte CONSTANT_String             = 8;

    // Declared in ConstantPool.jrag at line 51

    public static final byte CONSTANT_Integer            = 3;

    // Declared in ConstantPool.jrag at line 52

    public static final byte CONSTANT_Float              = 4;

    // Declared in ConstantPool.jrag at line 53

    public static final byte CONSTANT_Long               = 5;

    // Declared in ConstantPool.jrag at line 54

    public static final byte CONSTANT_Double             = 6;

    // Declared in ConstantPool.jrag at line 55

    public static final byte CONSTANT_NameAndType        = 12;

    // Declared in ConstantPool.jrag at line 56

    public static final byte CONSTANT_Utf8               = 1;

    // Declared in ConstantPool.jrag at line 58

 
    private int posCounter = 1;

    // Declared in ConstantPool.jrag at line 60

 
    private ArrayList list = new ArrayList();

    // Declared in ConstantPool.jrag at line 61

    private void addCPInfo(CPInfo info) {
      list.add(info);
    }

    // Declared in ConstantPool.jrag at line 66

 
    // for debugging purposes
    public int numElements() {
      return list.size();
    }

    // Declared in ConstantPool.jrag at line 69

    public String toString() {
      StringBuffer s = new StringBuffer();
      for(Iterator iter = list.iterator(); iter.hasNext(); ) {
        s.append(iter.next().toString());
        s.append("\n");
      }
      return s.toString();
    }

    // Declared in ConstantPool.jrag at line 78

 
    public void emit(DataOutputStream out) throws IOException {
      out.writeChar(posCounter);
      for(Iterator iter = list.iterator(); iter.hasNext(); ) {
        CPInfo info = (CPInfo)iter.next();
        info.emit(out);
      }
    }

    // Declared in ConstantPool.jrag at line 86


    private int labelCounter = 1;

    // Declared in ConstantPool.jrag at line 87

    public int newLabel() {
      return labelCounter++;
    }

    // Declared in ConstantPool.jrag at line 91

 
    private HashMap classConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 92

    public int addClass(String name) {
      Map map = classConstants;
      Object key = name;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantClass(addUtf8(name.replace('.', '/')));
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 107

 
    private HashMap fieldrefConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 108

    public int addFieldref(String classname, String name, String type) {
      Map map = fieldrefConstants;
      Object key = classname + name + type;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantFieldref(addClass(classname), addNameAndType(name, type));
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 123

 
    private HashMap methodrefConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 124

    public int addMethodref(String classname, String name, String desc) {
      Map map = methodrefConstants;
      Object key = classname + name + desc;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantMethodref(addClass(classname), addNameAndType(name, desc));
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 139

 
    private HashMap interfaceMethodrefConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 140

    public int addInterfaceMethodref(String classname, String name, String desc) {
      Map map = interfaceMethodrefConstants;
      Object key = classname + name + desc;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantInterfaceMethodref(addClass(classname), addNameAndType(name, desc));
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 155

 
    private HashMap nameAndTypeConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 156

    public int addNameAndType(String name, String type) {
      Map map = nameAndTypeConstants;
      Object key = name + type;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantNameAndType(addUtf8(name), addUtf8(type));
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 171

 
    private HashMap utf8Constants = new HashMap();

    // Declared in ConstantPool.jrag at line 172

    public int addUtf8(String name) {
      Map map = utf8Constants;
      Object key = name;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantUtf8(name);
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 187

 
    private HashMap stringConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 188

    public int addConstant(String val) {
      Map map = stringConstants;
      Object key = val;
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantString(addUtf8(val));
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        String s = info.toString();
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 203

 
    private HashMap intConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 204

    public int addConstant(int val) {
      Map map = intConstants;
      Object key = new Integer(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantInteger(val);
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 218

 
    private HashMap floatConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 219

    public int addConstant(float val) {
      Map map = floatConstants;
      Object key = new Float(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantFloat(val);
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 233

 
    private HashMap longConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 234

    public int addConstant(long val) {
      Map map = longConstants;
      Object key = new Long(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantLong(val);
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }

    // Declared in ConstantPool.jrag at line 248

 
    private HashMap doubleConstants = new HashMap();

    // Declared in ConstantPool.jrag at line 249

    public int addConstant(double val) {
      Map map = doubleConstants;
      Object key = new Double(val);
      if(!map.containsKey(key)) {
        CPInfo info = new ConstantDouble(val);
        info.pos = posCounter; posCounter += info.size();
        addCPInfo(info);
        map.put(key, info);
        return info.pos;
      }
      CPInfo info = (CPInfo)map.get(key);
      return info.pos;
    }


}
