package jcop.compiler;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import jcop.lang.JCopException;

import AST.CompilationUnit;

public class ErrorChecker {
	private CompilationUnit unit;
	private CompilerMessageStream msg =	CompilerMessageStream.getInstance();
	
	public ErrorChecker(CompilationUnit compilationUnit) {
		this.unit = compilationUnit;		
	}

	
	protected void processErrors(String type, Collection errors) {
	    msg.logTask("ErrorChecker reports", type, ":");
	    for(Iterator iter2 = errors.iterator(); iter2.hasNext(); ) {
	    	msg.log("",iter2.next().toString());
	    }
	    msg.closeTask();
	  }
	
	
	private void processCompilationUnit() {		
			Collection errors = unit.parseErrors();			
			Collection warnings = new LinkedList();
			// compute static semantic errors when there are no parse
			// errors or the recover from parse errors option is specified
			try {
				if (errors.isEmpty() ) {					
					unit.errorCheck(errors, warnings);					
				}
				if (!errors.isEmpty() ) {
					processErrors("errors", errors);				
				}
				else {
					processErrors("w", warnings);			
				}
			}
			finally {
				processErrors("errors", errors);
			}	
	}
	
	public boolean foundErrors() {
		try {
			processCompilationUnit();
			return false;
		}
		catch (JCopException e) {
			System.err.println(e.getLocalizedMessage());			
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();			
			return true;
		}
	}

}
