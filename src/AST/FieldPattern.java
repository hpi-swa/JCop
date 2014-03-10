
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;



public class FieldPattern extends MemberPattern implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public FieldPattern clone() throws CloneNotSupportedException {
        FieldPattern node = (FieldPattern)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public FieldPattern copy() {
      try {
          FieldPattern node = (FieldPattern)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public FieldPattern fullCopy() {
        FieldPattern res = (FieldPattern)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 69

    public FieldPattern() {
        super();

        setChild(new List(), 0);

    }

    // Declared in AspectJ.ast at line 11


    // Declared in AspectJ.ast line 69
    public FieldPattern(List<ModifierPattern> p0, Pattern p1, NamePattern p2) {
        setChild(p0, 0);
        setChild(p1, 1);
        setChild(p2, 2);
    }

    // Declared in AspectJ.ast at line 17


  protected int numChildren() {
    return 3;
  }

    // Declared in AspectJ.ast at line 20

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 69
    public void setModifierPatternList(List<ModifierPattern> list) {
        setChild(list, 0);
    }

    // Declared in AspectJ.ast at line 6


    private int getNumModifierPattern = 0;

    // Declared in AspectJ.ast at line 7

    public int getNumModifierPattern() {
        return getModifierPatternList().getNumChild();
    }

    // Declared in AspectJ.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public ModifierPattern getModifierPattern(int i) {
        return (ModifierPattern)getModifierPatternList().getChild(i);
    }

    // Declared in AspectJ.ast at line 15


    public void addModifierPattern(ModifierPattern node) {
        List<ModifierPattern> list = getModifierPatternList();
        list.addChild(node);
    }

    // Declared in AspectJ.ast at line 20


    public void setModifierPattern(ModifierPattern node, int i) {
        List<ModifierPattern> list = getModifierPatternList();
        list.setChild(node, i);
    }

    // Declared in AspectJ.ast at line 24

    public List<ModifierPattern> getModifierPatterns() {
        return getModifierPatternList();
    }

    // Declared in AspectJ.ast at line 27

    public List<ModifierPattern> getModifierPatternsNoTransform() {
        return getModifierPatternListNoTransform();
    }

    // Declared in AspectJ.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<ModifierPattern> getModifierPatternList() {
        return (List<ModifierPattern>)getChild(0);
    }

    // Declared in AspectJ.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<ModifierPattern> getModifierPatternListNoTransform() {
        return (List<ModifierPattern>)getChildNoTransform(0);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 69
    public void setTypePattern(Pattern node) {
        setChild(node, 1);
    }

    // Declared in AspectJ.ast at line 5

    public Pattern getTypePattern() {
        return (Pattern)getChild(1);
    }

    // Declared in AspectJ.ast at line 9


    public Pattern getTypePatternNoTransform() {
        return (Pattern)getChildNoTransform(1);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 69
    public void setMemberNamePattern(NamePattern node) {
        setChild(node, 2);
    }

    // Declared in AspectJ.ast at line 5

    public NamePattern getMemberNamePattern() {
        return (NamePattern)getChild(2);
    }

    // Declared in AspectJ.ast at line 9


    public NamePattern getMemberNamePatternNoTransform() {
        return (NamePattern)getChildNoTransform(2);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
