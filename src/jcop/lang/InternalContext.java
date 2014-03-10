package jcop.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class InternalContext {
//	private List<jcop.lang.Layer> activedLayers, deactivatedLayers;
	protected boolean isActive;

	protected InternalContext() {		
//		activedLayers = new ArrayList<jcop.lang.Layer>();
//		deactivatedLayers = new ArrayList<jcop.lang.Layer>();
//		
	}
	
	
	
	
	
	protected InternalContext(boolean active) {	  
	    isActive=true;
	  }
	
	public abstract boolean isActiveFor(String signature);
	public  String jcop_name() {
		return null;
	}
	
	protected final List<jcop.lang.Layer> createList(jcop.lang.Layer... layers) {
		return this.createList(Arrays.asList(layers));
	}

	protected List<jcop.lang.Layer>  createList(List<jcop.lang.Layer> layers) {
		return layers;
	}

//	protected final void deactivate(jcop.lang.Layer... layers) {
//		this.deactivate(Arrays.asList(layers));
//	}

//	protected final void deactivate(List<jcop.lang.Layer> layers) {
//		this.deactivatedLayers.addAll(layers);
//	}

	public abstract List<jcop.lang.Layer> getActivatedLayers();	
	public abstract List<jcop.lang.Layer> getDeactivatedLayers();
		
	
	
	public final boolean isActive() {
		return isActive;
	}
	
	
	public final boolean check(String signature, String pointcutExpr) {
		// TODO optimize
		// replace n whitespaces by one space
		final String pcExpr = createRegEx(pointcutExpr);
		final String sign = normalize(signature);
		//return sign.matches(pcExpr);
		return pcExpr.equals(sign);
	}
	
	public String createRegEx(String pointcutExpr) {
		pointcutExpr = normalize(pointcutExpr);
//		pointcutExpr = pointcutExpr.replaceAll("\\.\\.", "[a-zA-Z,]\\\\\\.\\\\\\.)");
//		pointcutExpr = pointcutExpr.replaceAll("\\.", "\\\\\\.");
//		pointcutExpr = pointcutExpr.replaceAll("\\(", "\\\\\\(");
//		pointcutExpr = pointcutExpr.replaceAll("\\)", "\\\\\\)");		
		return pointcutExpr;
	}



	//fixme: move to advice and transformer
	public String normalize(String signature) {
		signature = signature.trim().replaceAll("\\s+", " ");
		for(String modifier : new String[]{"public", "static", "final", "private", "protected"})
			signature = signature.replaceAll(modifier, "");
		return signature.trim();
	}

	public final void activateFor(ContextComposition c) {
		    isActive = true;	    
		    c.activate(this);		    
	  }
	  public final void deactivateFor(ContextComposition c) {
		    isActive = false;	    
		    c.deactivate(this);		      
	  }
	  
	  public final void activate() {
		activateFor(JCop.thread().getContextComposition());
//		JCop.thread().compositionChange();
	  }
	  public final void activateGlobal() {
	    activateFor(JCop.global().getContextComposition());
//	    JCop.thread().compositionChange();
	  }
	  public final void deactivate() {
		deactivateFor(JCop.thread().getContextComposition());
//		JCop.thread().compositionChange();
	  }

	public final String getName() {
		return jcop_name();
	}
	
	@Override
	public String toString() {
		return jcop_name();		
	}
}