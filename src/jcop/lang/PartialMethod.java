package jcop.lang;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public final class PartialMethod  extends AccessibleObject implements Member{
	
	private String declaringClass;
	private String returnType;
	private Layer _layer;
	private String name;
	private int modifier;	
	private String[] exceptionTypes;
	private String[] params;
	private String internalSignature;
	
	public PartialMethod(
			Layer _layer,
			String name,
			String declaringClass,
			int modifier,
			String returnType,	
			String[] params,
			String[] exceptions,
			String internalSignature
			) {
		this._layer = _layer;
		this.name = name;
		this.declaringClass = declaringClass;
		this.modifier = modifier;
		this.returnType = returnType;	
		this.params = params;
		this.exceptionTypes = exceptions;
		this.internalSignature = internalSignature;
	}

	
	public Class getDeclaringClass() {		
		return getClassFor(declaringClass);		
	}

	public Layer getDefiningLayer() {		
		return _layer;		
	}

	public int getModifiers() {
		return modifier;
	}

	
	public String getName() {		
		return name;
	}


	public boolean isSynthetic() {		
		return false;
	}
	
	public Class[] getExceptionTypes()  {
		Class[] exceptions = new Class[exceptionTypes.length];
		for (int i = 0; i < exceptionTypes.length; i++) {
			String exception = exceptionTypes[i];			
			exceptions[i] = getClassFor(exception);
		}
		return exceptions;
	}
	
	public Class getReturnType() {
		return getClassFor(returnType);
	}
	
	public Class getClassFor(String className) {		
		try {
			return Class.forName(className);
		} 
		catch (ClassNotFoundException e) {			
			return null;
		}
	}

	public Class[] getParamTypes(Object... args) {
		 List<Class> paramTypes = new ArrayList<Class>();
		 paramTypes.add(getDeclaringClass());
		 paramTypes.add(Composition.class);
		 
		 for (String arg : params) {
			 if (arg.startsWith("@primitive.")) {
				 String primType = arg.substring("@primitive.".length());
				 paramTypes.add(getPrimitiveType(primType));
				 
			 }
			 else {
			  try {				 
				 paramTypes.add(Class.forName(arg));
			  }
			  catch(Exception e) { }
			 }
		 }
			
		return paramTypes.toArray(new Class[]{});   
   }
		 
   public Class getPrimitiveType(String name) {
		 if (name.equals("byte")) return byte.class;
		 if (name.equals("short")) return short.class;
		 if (name.equals("int")) return int.class;
		 if (name.equals("long")) return long.class;
		 if (name.equals("char")) return char.class;
		 if (name.equals("float")) return float.class;
		 if (name.equals("double")) return double.class;
		 if (name.equals("boolean")) return boolean.class;
		 if (name.equals("void")) return void.class;
		 throw new IllegalArgumentException();
	}
   
   public Object[] getInternalArgs(Object target, Object[] args) {
	   Object[] newArgs = new Object[args.length + 2];
	   newArgs[0] = target;	   
	   newArgs[1] = JCop.current();
	   System.arraycopy(args, 0, newArgs, 2, args.length);
	   return newArgs;
   }
   
   public Object invoke(Object target, Object... args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
	  Method partialMethod = 
		  _layer.getClass().getMethod(internalSignature, getParamTypes(args));
	  return partialMethod.invoke(_layer, getInternalArgs(target, args));
   }
	
	
	
	

}
