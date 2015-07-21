package jcop.lang;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Composition implements Cloneable {

	public ArrayList<LayerProxy> tmpActualLayers;
	
	public LayerToProxyMap layerToProxyMap;
	// not sure if i need this list
	// public ArrayList<Layer> deactivatedLayers;
	public String deactivated = "";
	//public ContextComposition contexts;

	// reflective API

	public static List<Layer> getPartialMethodProvidersFor(String signature) {
		List<Layer> layers = new ArrayList<Layer>();
		for (Layer l : Layer.getLayers()) {
			if (l.providesPartialMethod(signature))
				layers.add(l);
		}
		return layers;
	}

	public Composition withLayer(Collection<Layer> layerList) {
		return this.withLayer(layerList.toArray(new Layer[0]));
	}
	
	public Composition withLayer(Layer... layerList) {		
		Composition old = this.clone();
		addLayer(layerList);
		return old;
	}

	public static Composition current() {
		return JCop.current();
	}

	// public static List<Layer> getImplicitlyActivatedLayers(Object target) {
	// return getImplicitlyActivatedLayers(target,
	// Arrays.asList(Layer.getLayers()));
	// }

	public static List<Layer> getImplicitlyActivatedLayers(String signature,
			Object target) {
		return getImplicitlyActivatedLayers(target,
				getPartialMethodProvidersFor(signature));
	}

	private boolean invalidated = true;

	protected void invalidate() {
		invalidated = true;
	}

	protected Composition() {
		super();		
		//activatedLayers = new ArrayList<LayerProxy>();
		layerToProxyMap = new  LayerToProxyMap();
		// deactivatedLayers = new ArrayList<Layer>();
		//contexts = new ContextComposition(this);
	}

	public void addLayer(Layer... layerList) {
		for (Layer _layer : layerList) {
			if (_layer != null) {
				// addLayer(_layer.getImplicitActivations());
				// for (Layer toBeDeactivated :
				// _layer.getImplicitDeActivations())
				// removeLayer(toBeDeactivated);
				// this.deactivatedLayers.remove(_layer);
				layerToProxyMap.addLayer(_layer);
				// System.out.println("before: " +activatedLayers);
				Composition c = _layer.onWith(this);
				
				this.layerToProxyMap = c.layerToProxyMap;
				// System.out.println("after: " +activatedLayers);
				invalidateComposition();
			}
		}
	}

	public Composition addLayerWithLogging(String loggerName,
			Collection<Layer> layerList) {
		Composition old = this.withLayer(layerList);
		Collection<String> layerNames = new ArrayList<String>();
		for (Layer _layer : layerList) {
			layerNames.add(_layer.toString());
		}
		LayerLogger.logLayerActivation(loggerName,
				layerNames.toArray(new String[layerNames.size()]));
		return old;
	}

	public Composition addLayerWithLogging(String loggerName,
			Layer... layerList) {
		Composition old = this.withLayer(layerList);
		String[] layerNames = new String[layerList.length];
		for (int i = 0; i < layerList.length; ++i) {
			layerNames[i] = layerList[i].toString();
		}

		LayerLogger.logLayerActivation(loggerName, layerNames);
		return old;
	}

	public LayerProxy firstLayer() {
		return ((LayerProxy)getTmpLayerComposition().get(0));
	}

	public Layer[] getLayer() {
		return getTmpLayerComposition().toArray(new Layer[0]);
	}

//	public ContextComposition getContexts() {
//		return contexts;
//	}
	
	
//
//	public void setContexts(ContextComposition comp) {
//		contexts = comp;
//	}

	public void setActiveFor(Object o) {
		for (Layer l : getLayer())
			l.setActiveFor(o);
	}

	public void setInactiveFor(Object o) {
		for (Layer l : getLayer())
			l.setInactiveFor(o);
	}

	public Composition removeAllSubLayer(Class toBeRemoved) {
		Composition old = this.clone();
		boolean removed = this.layerToProxyMap.removeAllSubLayer(toBeRemoved);
		return old;
	}
	
	public Composition removeLayer(Layer toBeRemoved) {
		Composition old = this.clone();
		boolean removed = this.layerToProxyMap.removeLayer(toBeRemoved);
//		while (removed)
//			removed = this.activatedLayers.remove(toBeRemoved);
		// this.deactivatedLayers.remove(_layer);
		// this.deactivatedLayers.add(0, _layer);
		// this.deactivated += _layer.toString();
		return old;
	}

	public Composition removeLayerWithLogging(String loggerName, Layer _layer) {
		Composition old = this.removeLayer(_layer);
		LayerLogger.logLayerDeactivation(loggerName,
				new java.lang.String[] { _layer.toString() });
		return old;
	}

	public LayerProxy next(LayerProxy current) {
		ArrayList<LayerProxy> composition = getTmpLayerComposition();
		
		for (int i = 0; i < composition.size(); i++) {
			LayerProxy _layer = composition.get(i);
			if (current.equals(_layer))
				return composition.get(i + 1);
		}
		return composition.get(0);
	}

	@Override()
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (LayerProxy _layer : getTmpLayerComposition()) {
			buffer.append(_layer);
			buffer.append(", ");
		}
		if (buffer.length() > 0)
			buffer.delete(buffer.length() - 2, buffer.length());
		buffer.append("]");
		return buffer.toString();
	}

	@Override()
	protected Composition clone() {
		Composition clone = new Composition();
		if (this.tmpActualLayers != null) {
			clone.tmpActualLayers = (ArrayList<LayerProxy>) tmpActualLayers.clone();
		}
		clone.deactivated = this.deactivated;
		clone.layerToProxyMap =  (LayerToProxyMap)layerToProxyMap.clone();
		// clone.deactivatedLayers.addAll(this.deactivatedLayers);
		// clone.activatedContextLayers.addAll(this.activatedContextLayers);
		//clone.contexts = this.contexts;
		// clone.parent_id = this.id;
		return clone;
	}

	public static List<Layer> getImplicitlyActivatedLayers(Object target,
			Iterable<Layer> layerList) {
		List<Layer> layers = new ArrayList<Layer>();
		for (Layer l : layerList) {
			if (l.isImplicitlyActive() || l.isActiveFor(target)) {
				layers.add(l);
			}
		}
		if (target instanceof LayerProvider)
			layers.addAll(((LayerProvider) target).getLayers());
		return layers;
	}

	public void invalidateComposition() {
		tmpActualLayers = null;
	}

	public ArrayList<LayerProxy> getTmpLayerComposition() {
		// if (tmpActualLayers == null)
		tmpActualLayers = buildTmpLayerComposition();
		return tmpActualLayers;
	}
 
	public ArrayList<LayerProxy> buildTmpLayerComposition() {
		ArrayList<LayerProxy> tmpList = new ArrayList<LayerProxy>();
		tmpList.addAll(Arrays.asList(Layer.getStaticActiveLayers()));
		tmpList.addAll(this.getDirectActivatedLayers());
		//tmpList.addAll(this.getContextActivatedLayers());
		tmpList.add(new LayerProxy(BaseLayer.getInstance()));
		return tmpList;
	}

	public List<LayerProxy> getDirectActivatedLayers() {
		return new ArrayList<LayerProxy>(this.layerToProxyMap.getLayers());
	}

//	public List<LayerProxy> getContextActivatedLayers() {
//		ArrayList<LayerProxy> resultList = new ArrayList<LayerProxy>();
//		//resultList.addAll(contexts.getActivatedLayers());
//		resultList.removeAll(this.activatedLayers);
//		// resultList.removeAll(this.deactivatedLayers);
//		return resultList;
//	}

	static class LayerLogger {

		public static void logLayerActivation(String loggerName,
				String... layers) {
			logLayer(loggerName, "activating layer", layers);
		}

		public static void logLayerDeactivation(String loggerName,
				String... layers) {
			logLayer(loggerName, "deactivating layer", layers);
		}

		public static void logLayer(String loggerName, String msg,
				String... layers) {
			StringBuilder strBuilder = new StringBuilder(msg);
			if (layers.length > 1)
				strBuilder.append("s");
			strBuilder.append(createLayerList(layers));
			strBuilder.append("...");
			log(loggerName, strBuilder.toString());
		}

		public static StringBuffer createLayerList(String[] layers) {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < layers.length; ++i) {
				if (i > 0)
					buffer.append(",");
				buffer.append(" ").append(layers[i]);
			}
			return buffer;
		}

		public static void log(String loggerName, String msg) {
			getLogger(loggerName).info(msg);
		}

		public static Logger getLogger(String loggerName) {
			return isEmptyString(loggerName) ? Logger.getAnonymousLogger()
					: Logger.getLogger(loggerName);
		}

		public static boolean isEmptyString(String str) {
			return str == null || "".equals(str);
		}
	}

	public boolean contains(Layer l) {
		return getTmpLayerComposition().contains(l);
		
	}
	
	class LayerToProxyMap  {
		private ArrayList<LayerProxy> activatedLayers;
		private LinkedHashtable<Layer,LayerProxy> activatedProxies;

		private LayerToProxyMap(ArrayList<LayerProxy> activatedLayers, LinkedHashtable<Layer,LayerProxy> activatedProxies) {
			this.activatedLayers = activatedLayers;
			this.activatedProxies = activatedProxies;
		}
		
		
		LayerToProxyMap() {
			activatedLayers = new ArrayList<LayerProxy>();
			activatedProxies = new LinkedHashtable<Layer, LayerProxy>();
		}
		
		public Collection<? extends LayerProxy> getLayers() {
			return activatedLayers;
		}

		public boolean removeLayer(Layer toBeRemoved) {
			for (LayerProxy proxy : activatedProxies.get(toBeRemoved))
				activatedLayers.remove(proxy);
			activatedProxies.remove(toBeRemoved);
			return true;
		}

		public boolean removeAllSubLayer(Class toBeRemoved){
			for(Layer _layer : Collections.list(activatedProxies.keys())){
				if(toBeRemoved.isAssignableFrom(_layer.getClass())){
					for(LayerProxy proxy : activatedProxies.get(_layer))
						activatedLayers.remove(proxy);
					activatedProxies.remove(_layer);
				}
			}
			return true;
		}

		void addLayer(Layer toBeAdded) {
			removeLayer(toBeAdded); // "with" semantics of ContextFJ
			LayerProxy proxy = new LayerProxy(toBeAdded);
			activatedLayers.add(0, proxy);
			activatedProxies.appendValue(toBeAdded, proxy);
		}
		
		@Override
		public LayerToProxyMap clone() {			
			return new LayerToProxyMap(
					(ArrayList<LayerProxy>)activatedLayers.clone(), 
					(LinkedHashtable<Layer,LayerProxy>) activatedProxies.clone());			
		}
		

	}


}
