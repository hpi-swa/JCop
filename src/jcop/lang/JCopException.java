package jcop.lang;

public  class JCopException extends RuntimeException  {
	
	public static final String LAYER_NOT_IMPORTED = "[JCop] layer not imported. %s,%s";
	
	public JCopException(String msg, String file, int line) {		
		super(String.format(msg, file, line));
		
	}
} 