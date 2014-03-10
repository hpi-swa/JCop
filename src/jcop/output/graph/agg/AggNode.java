package jcop.output.graph.agg;

import jcop.output.graph.INode;
import agg.xt_basis.Node;

public class AggNode implements INode {
	
	private Node decoratee;



	public AggNode(Node decoratee) {
		this.decoratee = decoratee;
	}
	
	

	public Node getAggNode() {
		return decoratee;
	}



	@Override
	public java.lang.String getName() {		
		return decoratee.getType().getName();
	}

}
