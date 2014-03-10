package contextj.lang;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
 
public class Layer implements Annotation  {
	public String name;
	public static List<Layer> layers = new ArrayList<Layer>();
	private List<Object> objects;
	
	
	public static Layer[] getLayerForObject(Object o) {
		List<Layer> lfo = new ArrayList<Layer>();
		for(Layer l : layers) {
			if (l.isActiveFor(o))
				lfo.add(l);
		}
		return lfo.toArray(new Layer[0]);
	}
	
	public void setActiveFor(Object o) {
		objects.add(o);
	}
	
	public void setInactiveFor(Object o) {
		objects.remove(o);
	}
	
	public static void removeLayersForObject(Object o) {
		for (Layer l : getLayerForObject(o))
			l.objects.remove(o);
	}
	
	
		/*
	 * Reflective API
	 */
	public static Layer forName(String identifier) {		
		if (identifier == null || identifier.equals("null")) 
			return null;		
		String className = "jcop.lang." + identifier;		
		return initLayerClass(className);
	}
	
	/*
	 * Reflective API
	 */
	public static Layer[] getLayers() {		
		return layers.toArray(new Layer[]{});
	}

		
	/*
	 * Reflective API
	 */
	public boolean isActive() {
		return Composition.current().contains(this) ||
		isImplicitlyActive();
	}
	
	public boolean isImplicitlyActive() {
		return false;
	}	
	
	public boolean isActiveFor(Object target) {
		return objects.contains(target);		
	}

	/*
	 * Reflective API
	 */
	public boolean isAccessedBefore(Layer l) {
		Composition current = Composition.current();
		if (!current.contains(this))
			return false;
		if (!current.contains(l))
			return true;
		return (current.indexOf(this) < current.indexOf(l));	
	}
	
	private static Layer initLayerClass(String className) {		 
	    try {
	    	Class c = Class.forName(className);			
			return (Layer)c.newInstance();
		} 
		catch (ClassNotFoundException e) {
			System.err.println("cannot instantiate layer '" + className + "'");
		}
		catch (IllegalAccessException e) {
			System.err.println("cannot access layer '" + className + "'");
		}
		catch (InstantiationException e) {
			System.err.println("cannot instantiate layer '" + className + "'");
		}	
		return null;
	}
	

	/*
	 * Reflective API
	 */
	public boolean providesPartialMethod(String signature) {	
		return partialMethodSignatures.containsKey(signature);
	}

	/*
	 * Reflective API
	 */
	public PartialMethod getPartialMethod(String signature) {
		return (PartialMethod)partialMethodSignatures.get(signature);
	}

	/*
	 * Reflective API
	 */
	//	public PartialMethod getPartialMethod(Method m) {
	//		return partialMethodSignatures.get(<get signature of m>);
	//	}	
	
	/*
	 * Reflective API
	 */
	public Composition getComposition() {
		return Composition.current();
	}
	
	/*
	 * Reflective API
	 */
	public PartialMethod[] getPartialMethods() {
		return partialMethodSignatures.values().toArray(new PartialMethod[]{});
	}
	
	public java.util.Hashtable<String, Object> partialMethodSignatures;

	
	public String toString() {
		return name;
	}
	public Layer(String name){
		this.name = name;
		this.partialMethodSignatures = new java.util.Hashtable<String, Object>();
		layers.add(this);
		this.objects = new ArrayList<Object>();
	}
	
	
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}
}
