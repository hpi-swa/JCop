package jcop.lang;

public class LayerProxy extends Layer {
   private Layer l;

	public LayerProxy(Layer l) {
		super();		
		this.l = l;
	}
	
	public String getName() {
		return l.getName();
	}
	
	public Layer get() {
		return l;
	}
	
	@Override
		public String toString() {
			return getName();
			//return String.format("%s(%s)", getName(), get().toString());		
		
		}

}
