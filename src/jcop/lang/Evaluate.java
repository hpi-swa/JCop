package jcop.lang;

public @interface Evaluate {
	 EvalPolicy value() default  EvalPolicy.OncePerCflow ;
	 
	 
	 
	 public enum EvalPolicy {
		 OncePerCflow, UntilTrue, UntilFalse, Always		 
	}

}
