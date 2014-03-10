package jcop.lang;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

import sun.security.jca.GetInstance;

public class Layer  {
	public static List<Layer> layers = new ArrayList<Layer>();
	public static final Layer BASE = BaseLayer.getInstance();	
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

	
	public jcop.lang.Layer[] getImplicitActivations() {
		return new Layer[0];
	}
	public jcop.lang.Layer[] getImplicitDeActivations() {
		return new Layer[0];
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
		return initLayerClass(identifier);
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
		if(isImplicitlyActive())
			return true;
		
		for(Layer currentLayer : JCop.current().getLayer())
			if(this.equals(currentLayer))
				return true;
		
		return false;
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
		Layer[] layerComposition = JCop.current().getLayer();
		int myIndex = -1;
		int lIndex = -1;
		for(int i=0;i<layerComposition.length;i++) {
			if(this.equals(layerComposition[i]))
				myIndex = i;
			if(l.equals(layerComposition[i]))
				lIndex = i;
			
			if(myIndex>=0&&lIndex>=0)
				break;
		}

		return myIndex<lIndex;	
	}
	
	public static Layer initLayerClass(String className) {		 
	    try {
	    	Class c = Class.forName(className);			
			return ((Layer)c.newInstance()).getSingleton();
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
	

	public jcop.lang.Layer getSingleton() {		
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
		return JCop.current();
	}
	
	/*
	 * Reflective API
	 */
	public PartialMethod[] getPartialMethods() {
		return partialMethodSignatures.values().toArray(new PartialMethod[]{});
	}
	
	public java.util.Hashtable<String, AccessibleObject> partialMethodSignatures;

	private Class[] allLayerClasses = { } ; 
	private static LayerProxy[] staticActiveLayers = { } ;
	public Class[] getAllLayerClasses() {		    
		return allLayerClasses;
	}

	public static  LayerProxy[] getStaticActiveLayers() {		
		return staticActiveLayers;
	}

	
	private   ArrayList<Layer> weakExcludes = new ArrayList<Layer>();
	public void weakExcludes(Layer c) {
		//System.out.println(name + ": add exclude" + c);
		weakExcludes.add(c);
	}	

		
	public String toString() {
		return getName();
	}
	
	public  String getName() {		
		return "Layer";
	}

	public Layer(){
		
		this.partialMethodSignatures = new java.util.Hashtable<String, AccessibleObject>();
		layers.add(this);
		this.objects = new ArrayList<Object>();
	}

	public final Layer _thislayer = this;
	public final Layer _superlayer = this;




	public Composition onWith(Composition composition) {
		checkExcludes(composition);
		Composition comp = checkWeakExcludes(composition);
		return comp;
		
	}

	public Composition checkWeakExcludes(Composition composition) {
		//System.out.println(name + ": check weak excludes" + weakExcludes);
		for(Layer c : weakExcludes) {
			if (composition.contains(c)) {
			//System.out.println(name + ": found conflict and remove " +  c);	
				composition.removeLayer(c);
			}
		}
		return composition;
	}

	public void checkExcludes(Composition composition) {		
		//...		
	}
	
	
	
	
}
