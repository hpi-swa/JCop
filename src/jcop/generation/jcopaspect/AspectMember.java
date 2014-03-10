package jcop.generation.jcopaspect;

import AST.Access;
import AST.Block;
import AST.List;
import AST.ParameterDeclaration;

public abstract class AspectMember {

	private Access typeAccess;
	private Block block;

	private String id;
	private List<ParameterDeclaration> parameters;
	private List<Access> exceptions;
	

	public AspectMember(Access typeAccess, String fullQualifiedName, List<ParameterDeclaration> parameters, List<Access> exceptions, Block block) {
		this.setTypeAccess(typeAccess);
		this.setBlock(block);
		this.setQualifiedName(fullQualifiedName);
		this.setParameters(parameters);
		this.setExceptions(exceptions);
	}



	public void setTypeAccess(Access typeAccess) {
		this.typeAccess = typeAccess;
	}

	public Access getTypeAccess() {
		return typeAccess;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}
	
	public void setQualifiedName(String id) {
		this.id = id;
	}


	public String getQualifiedName() {
		return id;
	}


	public void setParameters(List<ParameterDeclaration> parameterList) {
		this.parameters = parameterList;
	}


	public List<ParameterDeclaration> getParameterList() {
		return parameters;
	}
	
	
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		toString(b);
		return b.toString();
	}



	protected abstract void toString(StringBuffer stringBuffer) ;



	public void setExceptions(List<Access> exceptions) {
		this.exceptions = exceptions;
	}



	public String getExceptions() {
		if (exceptions.getNumChild() > 0)
			return "throws " + exceptions;
		else 
			return "";		
	}




}
