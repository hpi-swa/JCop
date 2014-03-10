package jcop.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Layers extends ArrayList<jcop.lang.Layer> {

	public void add(Layer... toBeAdded) {
		for (Layer l : toBeAdded) {
			add(l);
		}
	}

	@Override
	public boolean addAll(Collection<? extends Layer> toBeAdded) {
		for (Layer l : toBeAdded) 
			add(l);		
		return true;
	}

	@Override
	public boolean add(Layer l) {
		return super.add(l);
	}

	@Override
	public void add(int pos, Layer l) {
		super.add(pos, l);
	}

	  public boolean remove(Layer toBeRemoved) {
		    boolean removed = false;   
		    
			for  (int i = 0; i < size(); i++) {   
				LayerProxy  proxy = (LayerProxy)super.get(i);
				if(proxy.get() ==  toBeRemoved) 
					removed = super.remove(proxy);
			}
		    return removed;
		  }

	@Override
	public Layer get(int arg0) {
		return  super.get(arg0);		
	}

}
