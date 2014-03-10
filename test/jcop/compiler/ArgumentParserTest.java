package jcop.compiler;

import java.util.Arrays;

import junit.framework.TestCase;

public class ArgumentParserTest extends TestCase {
	
	
	public void testArgumentJoin() {
		String sample = "-sourcepath \"test\\my folder\\with blanks\" \"../Test Project/src\" Test";
		String[] expected= {"-sourcepath", "\"test\\my folder\\with blanks\"",  "\"../Test Project/src\"",  "Test"};
		String [] args =sample.split(" ");		
		ArgumentParser p = new ArgumentParser(args);
		assertEquals(Arrays.asList(expected), Arrays.asList(p.getParsedArgs()));		
	}
	
	public void testDefaultSourcepath() {
		String sample = "-d test Test";
		String[] expected= {"-sourcepath", ".", "-d", "test", "Test"};
		String [] args =sample.split(" ");		
		ArgumentParser p = new ArgumentParser(args);
		assertEquals(Arrays.asList(expected), Arrays.asList(p.getParsedArgs()));		
	}	
	
	public void testFileEnding() {		
		String[] sample1= {"-sourcepath", "src",  "Test.java"};
		String[] sample2= {"-sourcepath", "src",  "Test.java"};
		String[] expected= {"-sourcepath", "src",  "Test"};		
		ArgumentParser p = new ArgumentParser(sample1);
		assertEquals(Arrays.asList(expected), Arrays.asList(p.getParsedArgs()));		
		p = new ArgumentParser(sample2);
		assertEquals(Arrays.asList(expected), Arrays.asList(p.getParsedArgs()));
	}

}
