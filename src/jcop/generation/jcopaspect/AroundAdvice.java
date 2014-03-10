package jcop.generation.jcopaspect;

import AST.Access;
import AST.Block;
import AST.List;
import AST.ParameterDeclaration;

public class AroundAdvice extends AspectMember {

	private String pointcuts;
	private Access hostType;

	public AroundAdvice(Access typeAccess, Access hostType, String fullQualifiedName,	List<ParameterDeclaration> parameters, List<Access> exceptions, Block block, String pointcuts) {
		super(typeAccess, fullQualifiedName, parameters, exceptions, block);		
		this.setPointcuts(pointcuts);
		this.setHostType(hostType);
	}
	
	protected void toString(StringBuffer b) {
		b.append(getTypeAccess());
		b.append(" around(");
		b.append(getHostType());
		b.append(" _this) ");
		b.append(getExceptions());
		b.append(": execution (");
		b.append(getTypeAccess());
		b.append(" ");
		b.append(getQualifiedName());
		b.append(getParameterList());
		b.append(") && this(_this)");
		b.append(getPointcuts());
		b.append(getBlock().toString().replaceAll("this", "_this"));		
	}

	public void setPointcuts(String pointcuts) {
		this.pointcuts = pointcuts;
	}

	public String getPointcuts() {
		if (pointcuts != null && pointcuts.length() > 0) 
			return "&& " + pointcuts;		
		return pointcuts;
	}

	public void setHostType(Access hostType) {
		this.hostType = hostType;
	}

	public Access getHostType() {
		return hostType;
	}

	

}
