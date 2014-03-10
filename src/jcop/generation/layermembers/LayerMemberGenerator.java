package jcop.generation.layermembers;

import jcop.Globals.ID;
import jcop.generation.Generator;
import jcop.transformation.ASTTools.Generation;
import AST.Expr;
import AST.FieldDeclaration;
import AST.IntegerLiteral;
import AST.List;
import AST.MemberDecl;
import AST.MethodDecl;
import AST.Modifiers;
import AST.PartialMethodDecl;
import AST.StringLiteral;
import AST.ThisAccess;
import AST.TypeDecl;

public class LayerMemberGenerator extends Generator {
	

	
	public LayerMemberGenerator() {		
	}
	
	protected List<Expr> createMetaClassInstantiationArgs(MemberDecl member, String id,  Modifiers modifiers) {		
		String fqn = member.hostType().fullName();
		return genList(
				new ThisAccess(), 
				new StringLiteral(id), 
				new StringLiteral(fqn),
				new IntegerLiteral(Generation.modifierAsInt(modifiers)),
				new StringLiteral(fqn));
	}

	public String generateDelegationMethodName(MethodDecl method) {
		return generateDelegationMethodName(method, method.getID());
	}
	


	public String generateDelegationMethodName(FieldDeclaration field) {
		return generateDelegationMethodName(field, field.getID());
	}
		
	private String generateDelegationMethodName(MemberDecl member, String id) {
		TypeDecl host = member.hostType().topLevelType();
		String delimiter = ID.generatedMethodNameDelimiter;
		StringBuffer generatedMethodName = new StringBuffer()
			.append(host.packageName().replace(".", delimiter))
			.append(delimiter)
			.append(host.getID())
			.append(delimiter)
			.append(id);
		return generatedMethodName.toString();
	}
	
	
}
