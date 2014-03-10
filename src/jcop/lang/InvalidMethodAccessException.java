package jcop.lang;

public class InvalidMethodAccessException extends RuntimeException {
	public static  final String INVALID_METHOD_ACCESS = "[JCop] invalid method access. %s,%s";
	
	public InvalidMethodAccessException() {
		super(INVALID_METHOD_ACCESS);
	}

}
