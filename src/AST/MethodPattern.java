
package AST;
import java.util.HashSet;import java.util.LinkedHashSet;import java.io.File;import java.util.*;import beaver.*;import java.util.ArrayList;import java.util.zip.*;import java.io.*;import java.io.FileNotFoundException;import java.util.Collection;


public class MethodPattern extends MemberPattern implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public MethodPattern clone() throws CloneNotSupportedException {
        MethodPattern node = (MethodPattern)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public MethodPattern copy() {
      try {
          MethodPattern node = (MethodPattern)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public MethodPattern fullCopy() {
        MethodPattern res = (MethodPattern)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 119

	
	public void toString(StringBuffer s) {
 		for (ModifierPattern modifierPattern : getModifierPatterns()) {
			modifierPattern.toString(s);
 			s.append(" ");
		}
 		s.append(" ");
 		getReturnTypePattern().toString(s);
 		s.append(" ");
 		getMemberNamePattern().toString(s);
 		s.append("(");
 		for (int i = 0; i < getFormalPatterns().getNumChild(); i++) {
 			FormalPattern formalPattern =  getFormalPatterns().getChild(i);
 			if (i > 0) s.append(" ");
 			formalPattern.toString(s); 	 			
		}
 		 s.append(")");
     }

    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 65

    public MethodPattern() {
        super();

        setChild(new List(), 0);
        setChild(new List(), 3);
        setChild(new List(), 4);

    }

    // Declared in AspectJ.ast at line 13


    // Declared in AspectJ.ast line 65
    public MethodPattern(List<ModifierPattern> p0, Pattern p1, NamePattern p2, List<FormalPattern> p3, List<Pattern> p4) {
        setChild(p0, 0);
        setChild(p1, 1);
        setChild(p2, 2);
        setChild(p3, 3);
        setChild(p4, 4);
    }

    // Declared in AspectJ.ast at line 21


  protected int numChildren() {
    return 5;
  }

    // Declared in AspectJ.ast at line 24

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 65
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
    // Declared in AspectJ.ast line 65
    public void setReturnTypePattern(Pattern node) {
        setChild(node, 1);
    }

    // Declared in AspectJ.ast at line 5

    public Pattern getReturnTypePattern() {
        return (Pattern)getChild(1);
    }

    // Declared in AspectJ.ast at line 9


    public Pattern getReturnTypePatternNoTransform() {
        return (Pattern)getChildNoTransform(1);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 65
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

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 65
    public void setFormalPatternList(List<FormalPattern> list) {
        setChild(list, 3);
    }

    // Declared in AspectJ.ast at line 6


    private int getNumFormalPattern = 0;

    // Declared in AspectJ.ast at line 7

    public int getNumFormalPattern() {
        return getFormalPatternList().getNumChild();
    }

    // Declared in AspectJ.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public FormalPattern getFormalPattern(int i) {
        return (FormalPattern)getFormalPatternList().getChild(i);
    }

    // Declared in AspectJ.ast at line 15


    public void addFormalPattern(FormalPattern node) {
        List<FormalPattern> list = getFormalPatternList();
        list.addChild(node);
    }

    // Declared in AspectJ.ast at line 20


    public void setFormalPattern(FormalPattern node, int i) {
        List<FormalPattern> list = getFormalPatternList();
        list.setChild(node, i);
    }

    // Declared in AspectJ.ast at line 24

    public List<FormalPattern> getFormalPatterns() {
        return getFormalPatternList();
    }

    // Declared in AspectJ.ast at line 27

    public List<FormalPattern> getFormalPatternsNoTransform() {
        return getFormalPatternListNoTransform();
    }

    // Declared in AspectJ.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<FormalPattern> getFormalPatternList() {
        return (List<FormalPattern>)getChild(3);
    }

    // Declared in AspectJ.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<FormalPattern> getFormalPatternListNoTransform() {
        return (List<FormalPattern>)getChildNoTransform(3);
    }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 65
    public void setThrowsPatternList(List<Pattern> list) {
        setChild(list, 4);
    }

    // Declared in AspectJ.ast at line 6


    private int getNumThrowsPattern = 0;

    // Declared in AspectJ.ast at line 7

    public int getNumThrowsPattern() {
        return getThrowsPatternList().getNumChild();
    }

    // Declared in AspectJ.ast at line 11


     @SuppressWarnings({"unchecked", "cast"})  public Pattern getThrowsPattern(int i) {
        return (Pattern)getThrowsPatternList().getChild(i);
    }

    // Declared in AspectJ.ast at line 15


    public void addThrowsPattern(Pattern node) {
        List<Pattern> list = getThrowsPatternList();
        list.addChild(node);
    }

    // Declared in AspectJ.ast at line 20


    public void setThrowsPattern(Pattern node, int i) {
        List<Pattern> list = getThrowsPatternList();
        list.setChild(node, i);
    }

    // Declared in AspectJ.ast at line 24

    public List<Pattern> getThrowsPatterns() {
        return getThrowsPatternList();
    }

    // Declared in AspectJ.ast at line 27

    public List<Pattern> getThrowsPatternsNoTransform() {
        return getThrowsPatternListNoTransform();
    }

    // Declared in AspectJ.ast at line 31


     @SuppressWarnings({"unchecked", "cast"})  public List<Pattern> getThrowsPatternList() {
        return (List<Pattern>)getChild(4);
    }

    // Declared in AspectJ.ast at line 35


     @SuppressWarnings({"unchecked", "cast"})  public List<Pattern> getThrowsPatternListNoTransform() {
        return (List<Pattern>)getChildNoTransform(4);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
