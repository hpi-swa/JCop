package contextj.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import contextj.lang.Layer;

public class Composition extends ArrayList<Layer> {	
	private static Composition comp = new Composition();
	
	public static final ThreadLocal<Composition> current = 
		new ThreadLocal<Composition>() {
			protected Composition initialValue() {
				comp.addLayer(new Layer("base"));
				return comp;
			}
		}; 		  
	
//	public Composition activateLayer(Layer _layer) {
//		Composition old = (Composition)this.clone(); 
//		addLayer(_layer);		
//		return old;
//	}	
		
	
	public Composition addLayer(Iterable<Layer> layerList) {
		Composition old = (Composition)this.clone();		
		for (Layer _layer : layerList) 
			addLayer(_layer);	
		return old;
	}
	
	public Composition addLayer(Layer... layerList) {
		Composition old = (Composition)this.clone();
		for (Layer _layer : layerList) 
		  addLayer(_layer);
		return old;
	}
	
	private void addLayer(Layer _layer) {
		if (_layer != null) {
			if(contains(_layer)) 
				remove(_layer);
			add(0, _layer);
		}		
	}
	
	/**
	 * Returns a list of all layers that provide a partial method specified by
	 * signature.
	 * 
	 * @param signature, the signature of the layered method
	 * @return List of layers that provides a partial method for signature 
	 */
	public static List<Layer> getPartialMethodProvidersFor(String signature) {
		List<Layer> layers = new ArrayList<Layer>();
		for (Layer l : Layer.getLayers()) {
			if (l.providesPartialMethod(signature))
			  layers.add(l);
		}
		return layers;
	}
	
	private static List<Layer> getImplicitlyActivatedLayers(Object target, Iterable<Layer> layerList) {
		List<Layer> layers = new ArrayList<Layer>();
		 
		return layers;
	}
	
	public static List<Layer> getImplicitlyActivatedLayers(Object target) {
		return getImplicitlyActivatedLayers(target, Arrays.asList(Layer.getLayers()));
	}
	public static List<Layer> getImplicitlyActivatedLayers(String signature, Object target) {
		return getImplicitlyActivatedLayers(target, getPartialMethodProvidersFor(signature));
	}	
	
//	public static Composition current(String signature, Object o) {
//	    Composition comp = 
//	    	current().activateLayer(getImplicitlyActivatedLayers(signature, o)); 
//		return comp; 
//	}
	
	public static Composition current() {	
		return current.get();
	}	

	
	
	public Composition(Layer...layers) {
		addLayer(layers);
	}	
	
	public Composition() {
		//addLayer(new Layer("root"));
	}	

	public Layer firstLayer() {
		return get(0);
	}
	
	public void setActiveFor(Object o) {
		for (Layer l : getLayer()) 
			l.setActiveFor(o);
	}
	
	public void setInactiveFor(Object o) {
		for (Layer l : getLayer()) 
			l.setInactiveFor(o);
	}
	
	
	 
	
	
	
		
	//deactivates the last activated layer
	public Composition removeLayer(Layer _layer) {
		Composition old = (Composition)this.clone();
		remove(_layer);
		return old;
	}
	
	public static void setComposition(Composition comp) {
		current.set(comp);
	}
	
    public Layer[] getLayer() {
    	return (Layer[])this.toArray(new Layer[]{});
    }
	
	
	public Layer next(Layer current) {
		int pos = indexOf(current);
		return get(pos + 1);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (Layer _layer : this) {
			buffer.append(_layer);
			buffer.append(", ");
		}
		if (buffer.length() > 0)
			buffer.delete(buffer.length() - 2, buffer.length());
		buffer.append("]");
		return buffer.toString();
	}
}
