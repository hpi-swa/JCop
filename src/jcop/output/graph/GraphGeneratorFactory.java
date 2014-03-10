package jcop.output.graph;

import jcop.output.graph.agg.AggGraphGenerator;
import jcop.output.graph.groove.GrooveGraphGenerator;

public class GraphGeneratorFactory {
	public static enum Type { AGG,GROOVE	}
	private static Type type;
	
	public static void setGraph(Type t) {		
		type = t;
	}
	
	public static GraphGenerator getInstance() {		
		if (type == null)
			throw new RuntimeException("choose graph type first");
		if (type == Type.AGG) 
			return AggGraphGenerator.getInstance();		
		else 			
			return GrooveGraphGenerator.getInstance();		
	}

}
