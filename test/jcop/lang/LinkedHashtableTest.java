package jcop.lang;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.TestCase;

public class LinkedHashtableTest  extends TestCase{
	private LinkedHashtable<String, TestObject> table;
	private TestObject a = new TestObject("a");
	private TestObject b = new TestObject("b");
	private TestObject c = new TestObject("c");
	private String falseKey = "somethingThatsNotStored";
	private String correctKey = "somethingThatShouldBeStored";
	private String correctKey2 = "somethingElseThatShouldBeStored";
	
	@Override
	protected void setUp() throws Exception {
		table = new LinkedHashtable<String, TestObject>();	
	}
	
	public void testInitialization()  {
		
		assertFalse(table.containsKey(falseKey));
		 
		LinkedList<TestObject> result = table.get(falseKey);
		assertTrue(result.isEmpty());
		assertFalse("should be empty ", table.containsKey(falseKey));
		

		table.appendValue(correctKey, a);
		assertTrue(table.containsKey(correctKey));
		result = table.get(correctKey);
		assertTrue(!result.isEmpty());
		assertEquals(result.iterator().next(), a );
		
		
		table.appendValue(correctKey, b);
		table.appendValue(correctKey2, c);		
		result = table.get(correctKey);
		assertTrue(result.size() == 2);
		
		Iterator iter =  result.iterator();		
		assertEquals(iter.next(), a );
		assertEquals(iter.next(), b );
		
		result = table.get(correctKey2);
		assertTrue(result.size() == 1);
		iter =  table.get(correctKey2).iterator();
		assertEquals(iter.next(), c );
	}
	
	
	public void testMerging() {
		table.appendValue(correctKey, a);
		table.appendValue(correctKey, b);
		LinkedHashtable t2 = new LinkedHashtable<String, TestObject>();
		t2.appendValue(correctKey2, c);
		table.merge(t2);
		LinkedList<TestObject> result = table.get(correctKey);
		Iterator iter =  result.iterator();		
		assertEquals(iter.next(), a );
		assertEquals(iter.next(), b );
		
		result = table.get(correctKey2);
		iter =  result.iterator();
		assertEquals(iter.next(), c );
		assertFalse(iter.hasNext() );
	}
	
	class TestObject {
		private String name;
		public TestObject(String s) {
			this.name = s;
		}
		@Override
		public String toString() {
			return name;
		}
	}

}
