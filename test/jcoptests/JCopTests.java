package jcoptests;
import generation.InternalContextTest;
import junit.framework.TestSuite;

public class JCopTests extends TestSuite {
	
	
	
	public JCopTests() {
		addTestSuite(generation.InternalContextTest.class);
		addTest(new InternalContextTest());
	}

}
