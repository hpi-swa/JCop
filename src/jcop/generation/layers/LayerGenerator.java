package jcop.generation.layers;

import jcop.generation.Generator;
import AST.Access;
import AST.ClassDecl;
import AST.TypeAccess;

public class LayerGenerator extends Generator {	
	private ClassDecl layerClassDecl;

	public LayerGenerator(ClassDecl layerClassDecl) {
		this.layerClassDecl = layerClassDecl;
	}
	
	public  Access createLayerTypeAccess() {
		//malte return new TypeAccess(layer.packageName(), layer.getID());
		return new TypeAccess(layerClassDecl.packageName(), layerClassDecl.getID());
	}
	
	public ClassDecl getLayerClass() {
		return layerClassDecl;
	}

}
