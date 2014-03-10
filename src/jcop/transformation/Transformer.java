package jcop.transformation;

import jcop.compiler.CompilerMessageStream;
import jcop.compiler.ErrorChecker;
import jcop.lang.JCopException;
import AST.ASTNode;
import AST.BodyDecl;
import AST.CompilationUnit;
import AST.NamedMember;
import AST.TypeDecl;


abstract public class Transformer {
	
	public ASTNode<?> errorCheckAndTransform(CompilationUnit unit) {
		try {
			return transform();
		}
		catch (JCopException e) {
			System.err.println("Error Transforming " + unit.packageName());
			throw e;
		}
		catch(Exception e) {
			if (new ErrorChecker(unit).foundErrors())    			
    			System.exit(0);
    		return null;
		}
	}	
		
	abstract protected ASTNode  transform();
	
	protected void addBodyDeclTo(NamedMember member, TypeDecl classDecl) {
		CompilerMessageStream.getInstance().maybeLog("Generate member %s in class %s", member.getID(), classDecl.getFullQualifiedName());
		classDecl.resetCache();		
		classDecl.addBodyDecl((BodyDecl)member);		
		classDecl.resetCache();
	}	
	
	

}
