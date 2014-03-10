package test.generation;

import jcop.generation.layers.ConcreteLayerClassGenerator;
import junit.framework.TestCase;
import AST.Access;
import AST.ClassDecl;
import AST.LayerDecl;
import AST.CompilationUnit;
import AST.FieldDeclaration;
import AST.List;
import AST.MethodDecl;
import AST.Modifiers;
import AST.Opt;
import AST.TypeAccess;


public class TestConcreteLayerClassGenerator extends TestCase {

	private ConcreteLayerClassGenerator gen;
	private LayerDecl layer;
	private FieldDeclaration aField;
	
//	private LayerDecl createLayer() {
//		
//	}

	@Override
	protected void setUp() throws Exception {
		layer = new LayerDecl(
				new Modifiers(), "Dummy",	new Opt<Access>(), new List(),new List());
		
		CompilationUnit u = new CompilationUnit("Compilation", new List(), new List());
		ClassDecl c = new ClassDecl(
				new Modifiers(), "C", new Opt(), new List(), new List().add(layer)); 
		
		
		u.addChild(c);
		
		aField = new FieldDeclaration(
				new Modifiers(), 
				new TypeAccess("java.lang", "String"), 
				"toBeWrapped");
		this.gen = new ConcreteLayerClassGenerator(layer);		
	}
	
	public void testGenDelegationMethod() {
		MethodDecl m = gen.genDelegationMethod(aField, "mName");
	}
	
}
