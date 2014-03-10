package jcop.tools;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import AST.CompilationUnit;

public class Map {
	
	
	public static <T> void map(T[] list, func<T> mapFunction) {
		for (T elem : list) 
			mapFunction.map(elem);							
	}
	
	
	public static <T> void map(List<T> list, func<T> mapFunction) {
		int size=list.size();
		   for (int i=0; i < size; i++) {
		      mapFunction.map(list.get(i));
		   }
		}
	
	public static <T> void map(Iterator<T> iter,func<T> mapFunction) {
		while (iter.hasNext())
			mapFunction.map((T) iter.next());	
	}
	
	
	
	

	
	static public interface func<T> {
		   public void map(T t);
	}

	public static void print(File[] sourceFiles) {
		map(sourceFiles, new func<Object>() {
			public void map(Object t) {
				System.out.println(t);		
			}});
	}
	

}
