package jcop;

import java.util.Hashtable;

import AST.ASTNode;

public  class VisitedNodes  {
	//private static HashSet<MemberDecl> visitedBaseMemberForCodeGeneration = new HashSet<MemberDecl>();	
	//private  static HashSet<MemberDecl> visitedBaseMemberForDelegationMethodGeneration = new HashSet<MemberDecl>();
	private static Hashtable<ASTNode, VisitCounter> visited = new Hashtable<ASTNode, VisitCounter>();
	
	public static void add(ASTNode<ASTNode> decl) {
		visited.put(decl, new VisitCounter());
	}
	
	private static VisitCounter get(ASTNode<ASTNode> decl) {
		if (!visited.containsKey(decl)) 
			add(decl);		
		return visited.get(decl);
	}

	public static boolean firstVisit(ASTNode<ASTNode> decl) {		
		return (decl == null) || numberOfVisits(decl, 0);				
	}
	
	public static boolean secondVisit(ASTNode<ASTNode> decl) {
		return numberOfVisits(decl, 1);		
	}
	
	private static boolean numberOfVisits(ASTNode<ASTNode> decl, int nr) {		
		VisitCounter v = get(decl);
		if (v.getVisits() <= nr) {
			v.visit();
			return true;
		}
		else
			return false;				
	}
	 
	
	static class VisitCounter {
		private int numVisits;
				
		VisitCounter() {
			numVisits = 0;			
		}
		
		int getVisits() {
			return numVisits;
		}
		
		void visit() {
			numVisits++;
		}		
	}
}
