package test.generation;

import jcop.generation.Generator;
import junit.framework.TestCase;
import AST.Block;
import AST.Modifiers;
import AST.NullLiteral;
import AST.Opt;
import AST.ReturnStmt;
import AST.Stmt;


public class TestGenerator extends TestCase{
	
	private Generator gen;

	@Override
	protected void setUp() throws Exception {
		this.gen = new Generator();		
	}
	
	public void testGenModifiers() {
		Modifiers m = gen.genModifiers("public", "static");
		assertTrue(m.isPublic());
		assertTrue(m.isStatic());
		assertFalse(m.isPrivate());
		assertFalse(m.isStrictfp());
	}
	
	public void testGenOptBlock() {
		Stmt stmt = new ReturnStmt(new NullLiteral("null"));
		Opt<Block>  opt = gen.genOptBlock(stmt);
		assertEquals(opt.getNumChild(), 1);
		assertEquals(opt.getChild(0).getStmt(0), stmt );
	}
	
	

}
