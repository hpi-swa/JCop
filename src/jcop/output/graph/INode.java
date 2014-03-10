package jcop.output.graph;

 public interface INode {	
	 public static final String Program = "Program";
	 public static final String Parameter = "Parameter";
	 public static final String Return = "Return";
	 public static final String If = "If";
	 public static final String Statement = "Statement";
	 public static final String Assignment = "Assignment";
	 public static final String BinaryOperation = "BinaryOperation";
	 public static final String FieldReference = "FieldReference";
	 public static final String This = "This";
	 public static final String Null = "Null";
	 public static final String Boolean = "Boolean";
	 public static final String String = "String";
	 public static final String Integer = "Integer";
	 public static final String Expression = "Expression";
	 public static final String Context = "Context";
	 public static final String Layer = "Layer";
	 public static final String Class = "Class";
	 public static final String MethodCall = "MethodCall";
	 public static final String Constructor = "Constructor";
	 public static final String ProceedExpr = "ProceedExpr";
	 public static final String Instantiation = "Instantiation";
	 public static final String Field = "Field";
	 public static final String Reference = "Reference";
	 public static final String Identifier = "Identifier";
	 public static final String PartialMethod = "PartialMethod";
	 public static final String MethodDecl = "Method";
	 public static final String DeclarativeLayerActivation = "DeclarativeLayerActivation";
	
	
	//public String getNodeType();
	public String getName();
}

