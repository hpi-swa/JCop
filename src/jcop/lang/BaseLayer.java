package jcop.lang;
public class BaseLayer extends Layer {
		
   private static BaseLayer instance;

   public BaseLayer() {}
		
	public static BaseLayer getInstance() {
		if (instance == null)
			instance = new BaseLayer();
		return instance;
	}
	
	public String getName() {
		return "Base";
	}
}