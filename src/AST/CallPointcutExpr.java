
package AST;



public class CallPointcutExpr extends PointcutExpr implements Cloneable {
    public void flushCache() {
        super.flushCache();
    }
     @SuppressWarnings({"unchecked", "cast"})  public CallPointcutExpr clone() throws CloneNotSupportedException {
        CallPointcutExpr node = (CallPointcutExpr)super.clone();
        node.in$Circle(false);
        node.is$Final(false);
    return node;
    }
     @SuppressWarnings({"unchecked", "cast"})  public CallPointcutExpr copy() {
      try {
          CallPointcutExpr node = (CallPointcutExpr)clone();
          if(children != null) node.children = (ASTNode[])children.clone();
          return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
    }
     @SuppressWarnings({"unchecked", "cast"})  public CallPointcutExpr fullCopy() {
        CallPointcutExpr res = (CallPointcutExpr)copy();
        for(int i = 0; i < getNumChildNoTransform(); i++) {
          ASTNode node = getChildNoTransform(i);
          if(node != null) node = node.fullCopy();
          res.setChild(node, i);
        }
        return res;
    }
    // Declared in declarative_layer_activation.jrag at line 69

	
	 public void toString(StringBuffer s) {
    	s.append("execution(");
    	getPattern().toString(s);
    	s.append(")");
    }

    // Declared in AspectJ.ast at line 3
    // Declared in AspectJ.ast line 10

    public CallPointcutExpr() {
        super();


    }

    // Declared in AspectJ.ast at line 10


    // Declared in AspectJ.ast line 10
    public CallPointcutExpr(MemberPattern p0) {
        setChild(p0, 0);
    }

    // Declared in AspectJ.ast at line 14


  protected int numChildren() {
    return 1;
  }

    // Declared in AspectJ.ast at line 17

  public boolean mayHaveRewrite() { return false; }

    // Declared in AspectJ.ast at line 2
    // Declared in AspectJ.ast line 10
    public void setPattern(MemberPattern node) {
        setChild(node, 0);
    }

    // Declared in AspectJ.ast at line 5

    public MemberPattern getPattern() {
        return (MemberPattern)getChild(0);
    }

    // Declared in AspectJ.ast at line 9


    public MemberPattern getPatternNoTransform() {
        return (MemberPattern)getChildNoTransform(0);
    }

public ASTNode rewriteTo() {
    return super.rewriteTo();
}

}
