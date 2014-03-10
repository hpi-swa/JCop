package jcop.generation.jcopaspect;

import AST.Access;
import AST.Block;
import AST.List;
import AST.Modifiers;
import AST.ParameterDeclaration;

public class Introduction extends AspectMember {

	private Modifiers modifiers;

	public Introduction(Modifiers modif, Access typeAccess, String id,	List<ParameterDeclaration> parameterList, List<Access> exceptions, Block block) {
		super(typeAccess, id, parameterList, exceptions, block);
		this.setModifiers(modif);
	}

	protected void toString(StringBuffer b) {
		b.append(getModifiers());
		b.append(" ");
		b.append(getTypeAccess());
		b.append(" ");
		b.append(getQualifiedName());
		b.append("( ");
		b.append(getParameterList());
		b.append(") ");
		b.append(getExceptions());
		b.append(getBlock());		
	}

	public void setModifiers(Modifiers modifiers) {
		this.modifiers = modifiers;
	}

	public Modifiers getModifiers() {
		return modifiers;
	}


	
}
