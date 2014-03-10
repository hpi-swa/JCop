package jcop.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jcop.lang.Evaluate.EvalPolicy;



public class Context extends Observable implements LayerProvider {
	  
	  public Context() {		  
	  }  
	  
	  public synchronized void addObserver(ContextChangeListener o) {
		// TODO Auto-generated method stub
		super.addObserver(o);
	  }
	  
	  
	  
	  @Override	  
	  public void notifyObservers() {
		// TODO Auto-generated method stub
		super.notifyObservers();
	  }
	
	  //with X
	  public List<Layer> getLayers() {
		  return new ArrayList<Layer>();
	  }
	  
	  private boolean active = false;
	  
	  //enter(xxx.getCurrentNode() == isComment )
	  public boolean isActive() {
		  return entered() || (active && !exited());
	  }	  
	  
	  public boolean entered() {		  
		  active = true;
		  return active;
	  }
	  
	 public boolean exited() {
		 active = false;
		 return active;		 
	  }  	
}
