package jcop.compiler;

import java.util.ArrayList;

import AST.CompilationUnit;
import AST.Problem;
import beaver.Parser;
import parser.JavaParser;

public class JCopParser extends JavaParser {
	public CompilationUnit parse(java.io.InputStream is, String fileName) throws java.io.IOException, beaver.Parser.Exception {
	     CompilationUnit cu;
	     errors = new ArrayList();
	     try {
	       scanner.JavaScanner scanner = new scanner.JavaScanner(new scanner.Unicode(is));
	       cu = (CompilationUnit)parse(scanner);
	     } catch(Parser.Exception e) {
	       // build empty compilation unit for failed error recovery    	 
	       cu = new CompilationUnit();
	     } catch(Error e) {
	       cu = new CompilationUnit();
	       errors.add(new Problem(null, e.getMessage(), 0, 0, Problem.Severity.ERROR, Problem.Kind.LEXICAL));
	     }
	     for(java.util.Iterator iter = errors.iterator(); iter.hasNext(); ) {
	       Problem p = (Problem)iter.next();
	       p.setFileName(fileName);
	       cu.addParseError(p);	       	
	       System.err.println("parse error " + p);
	     }
	     return cu;
	   }
}
