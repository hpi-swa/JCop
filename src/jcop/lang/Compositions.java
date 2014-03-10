package jcop.lang;
public class Compositions {
		
		
		private Composition comp;
		private ContextComposition ctx;
		private ContextComposition cachedComposition;
		private int currentContextCompositionHash;
		
		Compositions() {
			this.comp = new Composition();
			this.ctx = new ContextComposition();
			this.cachedComposition = ctx;
			currentContextCompositionHash = 0;
		}
		 
		public void setContextComposition(ContextComposition comp2) {
			this.ctx = comp2;
			
		}

		void setComposition(Composition comp) {
			this.comp = comp;
		}

		Composition getComposition()  {
			return comp;
		}
		
		ContextComposition getContextComposition() {
			return ctx;
		}
		
		ContextComposition getCashedContextComposition(Compositions global) {			
			if (contextCompositionChanged() || global.contextCompositionChanged()) {				
				cachedComposition = global.getContextComposition().merge(getContextComposition());
				
			}
			return cachedComposition;
			
		}
		
				
		public boolean contextCompositionChanged() {			
			boolean changed =  currentContextCompositionHash != getContextComposition().hashCode() ;
			if (changed)
				currentContextCompositionHash = getContextComposition().hashCode();
			return changed;
		}

	
	}

