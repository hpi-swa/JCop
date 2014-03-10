package jcop.output.graph.groove;

import jcop.output.graph.INode;


public class GrooveNode implements INode {	
	private String name;

	public GrooveNode(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
