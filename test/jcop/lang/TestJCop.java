package jcop.lang;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;


public class TestJCop extends TestCase {
	
	
	
	public void testCompositionCaching() {
		ContextComposition comp = JCop.currentContexts();
		assertTrue(comp.isEmpty());
		
		Compositions compositions = JCop.thread(); 
		comp = compositions.getContextComposition();
		DummyContext ctx1 =  new DummyContext("ctx1");
		ctx1.activate();		
		assertEquals(1,comp.getSize());
		
		// should return true as the cache update hasnt been applied yet
		assertTrue(compositions.contextCompositionChanged());		
		//now the cache should be updated
		assertEquals(1, JCop.currentContexts().getSize(), 1);
		assertFalse(compositions.contextCompositionChanged());
		
		assertTrue(JCop.currentContexts().contains(ctx1));
		assertFalse(compositions.contextCompositionChanged());
		
		comp = JCop.thread().getContextComposition();
		assertFalse(compositions.contextCompositionChanged());
		
		comp.activate(new DummyContext("anonymous"));
		assertTrue(compositions.contextCompositionChanged());
		assertEquals(2, comp.getSize());	
		assertEquals(2, JCop.currentContexts().getSize());
		assertFalse(compositions.contextCompositionChanged());
		//comp = JCop.thread().getContextComposition();
		DummyContext2 ctx = new DummyContext2("ctx");
		ctx.activate();
		//comp.activate(new DummyContext2());			
		assertEquals(3, JCop.currentContexts().getSize());
		assertTrue(JCop.currentContexts().contains(ctx));
		
		DummyContext2 ctx3 = new DummyContext2("ctx3");
		System.err.println(JCop.currentContexts().activeContexts);
		JCop.global().getContextComposition().activate(ctx3);
		assertEquals(4, JCop.currentContexts().getSize());
		System.err.println(JCop.currentContexts().activeContexts);
		assertTrue(JCop.currentContexts().contains(ctx3));
		
	}
	class DummyContext2 extends InternalContext {

		
		private String id;

		public DummyContext2(String id) {
			this.id = id;
		}
		
		@Override
		public String jcop_name() {
			return id;
		}
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
	
	class DummyContext extends InternalContext {			
		private String id;

		@Override
		public boolean isActiveFor(String signature) {
			// TODO Auto-generated method stub
			return false;
		}
		
		
		public DummyContext(String id) {
			this.id = id;
		}
		
		@Override
		public String jcop_name() {
			return id;
		}
		

		
		@Override
		public List<Layer> getDeactivatedLayers() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<Layer> getActivatedLayers() {
			return createList(createList(new jcop.lang.Layer[]{ new Layer() } ));
			
		}
	}
	

}
