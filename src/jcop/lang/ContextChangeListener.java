package jcop.lang;

import java.util.Observer;

public interface ContextChangeListener extends Observer {	
	public void onContextChanged();
}
