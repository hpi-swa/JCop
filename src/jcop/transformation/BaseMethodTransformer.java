package jcop.transformation;

import jcop.generation.layermembers.BaseMethodGenerator;
import AST.Block;
import AST.MethodDecl;

public class BaseMethodTransformer extends Transformer {	
	private MethodDecl baseMethod;
	//private MethodDecl partialMethod;		
	private BaseMethodGenerator gen;
	
	public BaseMethodTransformer(MethodDecl partialMethod, MethodDecl baseMethod) {
		this.baseMethod = baseMethod;		
		//this.partialMethod = partialMethod;
		this.gen = new BaseMethodGenerator(partialMethod, baseMethod);
	}
	
	protected MethodDecl transform() {		
		Block activationBlock = gen.generateLayerActivationBlock();
		baseMethod.setBlock(activationBlock);		
		return baseMethod;
	}	
	
//	public static MethodDecl parseMethodDecl(String declaration) {
//		return (MethodDecl) parse(declaration, parser.JavaParser.AltGoals.method_declaration);
//	}
//
//	private static ASTNode<?> parse(String declaration, short goal) {
//		InputStream is = new ByteArrayInputStream(declaration.getBytes());
//		scanner.JavaScanner scanner = new scanner.JavaScanner(new scanner.Unicode(is));
//		parser.JavaParser theParser = new parser.JavaParser();
//		try {
//			if (goal == 0) {
//				return (ASTNode<?>) theParser.parse(scanner);
//			} else {
//				return (ASTNode<?>) theParser.parse(scanner, goal);
//			}
//		} catch (java.lang.Exception e) {
//			throw new java.lang.RuntimeException(e);
//		}
//	}

}

