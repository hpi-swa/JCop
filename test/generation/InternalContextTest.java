package generation;


import java.util.List;

import jcop.lang.InternalContext;
import jcop.lang.Layer;

import junit.framework.TestCase;

public class InternalContextTest extends TestCase {
	

	public void testRegexGeneration() {
		InternalContext c = new MyInternalContext();
		System.out.println(c.createRegEx("dg.test.C.m(int, String)"));
		assertEquals(c.createRegEx("dg.test.C.m(int, String)"), "dg\\.test\\.C\\.m\\(int, String\\)");	
//		assertEquals(c.createRegEx("dg.test.C.m(int, String)"), ".*\\.m\\(int, String\\)");
//		assertEquals(c.createRegEx("dg.test.C.m(int, String)"), "dg\\.test\\.C\\.m\\([a-zA-Z]*, String\\)");
		
//		assertTrue(c.check("dg.test.C.m(int, String)", "dg.test.C.m(int, String)"));
//		assertTrue(c.check("dg.test.C.m(int, String)", "dg.test.C.m(int, String)"));
		}

	


	
	
//	public void testPatternMaching() {
//		InternalContext c = new MyInternalContext();
//		assertTrue(c.check("dg.test.C.m(int, String)", "dg\\.test\\.C\\.m\\(int, String\\)"));	
//		assertTrue(c.check("dg.test.C.m(int, String)", ".*\\.m\\(int, String\\)"));
//		assertTrue(c.check("dg.test.C.m(int, String)", "dg\\.test\\.C\\.m\\([a-zA-Z]*, String\\)"));
//		assertFalse(c.check("dg.test.C.m(int, int, String)", "dg\\.test\\.C\\.m\\([a-zA-Z]*, String\\)"));
////		assertTrue(c.check("dg.test.C.m(int, String)", "dg.test.C.m(int, String)"));
////		assertTrue(c.check("dg.test.C.m(int, String)", "dg.test.C.m(int, String)"));
//		}
	}


	class MyInternalContext extends InternalContext {

		@Override
		public boolean isActiveFor(String signature) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<Layer> getActivatedLayers() { 
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Layer> getDeactivatedLayers() {
			// TODO Auto-generated method stub
			return null;
		}
	}


