package test;

import jcop.VisitedNodes;
import AST.ASTNode;
import junit.framework.TestCase;


public class NodeVisitorTest extends TestCase {
	
	public void testVisitor() {
		ASTNode n = new ASTNode<ASTNode>();
		assertTrue(VisitedNodes.firstVisit(n));
		assertFalse(VisitedNodes.firstVisit(n));
		assertFalse(VisitedNodes.firstVisit(n));
		assertTrue(VisitedNodes.secondVisit(n));
		assertFalse(VisitedNodes.secondVisit(n));
		assertFalse(VisitedNodes.firstVisit(n));
	}
	
	

}
