package jcop.compiler;

import java.io.File;

public class PathList implements CharSequence {
	private StringBuffer content;
	
	public PathList() {
		content = new StringBuffer();
	}

	public PathList(CharSequence workingDir) {
		this();
		add(workingDir);
	}

	public PathList add(CharSequence path) {
		if (!isEmpty()) 
			content.append(File.pathSeparator);			
		content.append(path);
		return this;
	}

	private boolean isEmpty() {
		return content.length() == 0;
	}

	@Override
	public char charAt(int index) {
		return content.charAt(index);		
	}

	@Override
	public int length() {
		return content.length();		
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return content.subSequence(start, end);		
	}

	public PathList create(CharSequence path) {
		return new PathList(this).add(path);		
	}
	
	@Override
	public String toString() {
		return content.toString();
	}

}
