package jcop.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ContextComposition {
	// private final List<Layer> emptyList = new ArrayList<Layer>();
	public ContextList activeContexts;
	// public ContextList evaluatedContexts;
	public ContextClassInstanceList sortetActiveContexts;

	// private Composition comp;
	// protected ContextComposition(Composition comp) {
	// this.comp = comp;
	// activatedContextLayers = new ArrayList<Layer>();
	// sortetActivatedContexts = new Hashtable<Class,
	// LinkedList<InternalContext>>();
	// activeContextsInCF = new LinkedList<InternalContext>();
	// }

	protected ContextComposition() {
		sortetActiveContexts = new ContextClassInstanceList();
		activeContexts = new ContextList();
		// evaluatedContexts = new ContextList();
	}

	public void activate(InternalContext toBeAdded) {
		activeContexts.add(toBeAdded);
		sortetActiveContexts.add(toBeAdded);
	}

	public void deactivate(InternalContext toBeRemoved) {
		activeContexts.remove(toBeRemoved);
		// evaluatedContexts.remove(toBeRemoved);
		sortetActiveContexts.remove(toBeRemoved);
		// this.activeContextsInCF.remove(toBeRemoved);
		// updateActivatedLayers();
	}

	/***
	 * Notifies all active contexts that the method with signature signature has
	 * been executed. Returns a clone of the old context composition.
	 */
	// public ContextComposition notifyExecution(String... signatures) {
	// ContextComposition old = this.clone();
	// comp.invalidate();
	// updateActiveContexts(signatures);
	// return old;
	// }
	public LinkedList<Layer> notifyExecution(Class ctx, String... signatures) {
		LinkedList<Layer> toBeActivated = new LinkedList<Layer>();
		updateActiveContexts(toBeActivated, ctx, signatures);
		return toBeActivated;
	}
	
	@Override
	public int hashCode() {
		return activeContexts.hashCode();
	}

	@Override
	public String toString() {
		return sortetActiveContexts.toString();
	}

	// public static ContextComposition current() {
	// return JCop.current().getContexts();
	// }
	//
	// public InternalContext[] getContextClassObjects() {
	// return sortetActivatedContexts.toArray(new InternalContext[0]);
	// }

	// public List<Layer> getActivatedLayers() {
	// // activatedContextLayers = computeLayers();
	// return list;
	// }

	// private void updateActivatedLayers() {
	// // in der layerliste?
	// List<Layer> activatedLayers = new ArrayList<Layer>();
	// Set<Layer> deactivatedLayers = new HashSet<Layer>();
	//
	// for (InternalContext ctx : this.activeContextsInCF) {
	// lookupLayersInContext(ctx, activatedLayers, deactivatedLayers);
	// }
	// Collections.reverse(activatedLayers);
	// updateActivatedLayers(activatedLayers, deactivatedLayers);
	// }

	/***
	 * Notifies all active contexts that a method signature has been executed.
	 * 
	 * @param ctx
	 */
	public void updateActiveContexts(LinkedList<Layer> toBeActivated, Class ctx, String... signatures) {
		ContextList ctxInstances = sortetActiveContexts	.getUnevaluated(ctx);
		for (InternalContext ctxInstance : ctxInstances)
			collectLayersFromContext(toBeActivated, ctxInstance, signatures);

	}

	public void collectLayersFromContext(LinkedList<Layer> toBeActivated,
			InternalContext ctxInstance, String[] signatures) {
		for (String signature : signatures) {
			// updateActivatedLayers(signature);
			if (ctxInstance.isActiveFor(signature)) {
				toBeActivated.addAll(ctxInstance.getActivatedLayers());
				sortetActiveContexts.setEvaluated(ctxInstance);
			}
		}

	}

	// private void updateActivatedContexts(LinkedList<Layer> toBeActivated,
	// Class ctxClass, String signature) {
	// for (InternalContext ctx : this.sortetActivatedContexts.get(ctxClass)) {
	// if (ctx != null) {
	// if (ctx.isActiveFor(signature)) {
	// //System.out.println(ctx.getName() + " has been activated for" +
	// signature);
	// //this.activeContextsInCF.add(ctx);
	// toBeActivated.addAll(ctx.getActivatedLayers());
	// }
	// }
	// }
	// //updateActivatedLayers();
	// }

	public boolean contains(InternalContext ctx) {
		return this.sortetActiveContexts.containsContext(ctx);
	}

	// private void updateActivatedLayers(String signature) {
	// for (InternalContext ctx : this.sortetActivatedContexts) {
	//
	// if (ctx == null) return;
	// if (ctx.isActiveFor(signature)) {
	// //System.out.println(ctx.getName() + " has been activated for" +
	// signature);
	// this.activeContextsInCF.add(ctx);
	// }
	// }
	// updateActivatedLayers();
	// }

	// private void lookupLayersInContext(InternalContext ctx, List<Layer>
	// activatedLayers, Set<Layer> deactivatedLayers) {
	// List<Layer> activeContextLayers = createFilteredLayerList(ctx,
	// activatedLayers, deactivatedLayers);
	// activatedLayers.addAll(activeContextLayers);
	// deactivatedLayers.addAll(ctx.getDeactivatedLayers());
	// }

	public List<Layer> createFilteredLayerList(InternalContext currentContext,
			List<Layer> activatedLayers, Set<Layer> deactivatedLayers) {
		List<Layer> activeContextLayers = new ArrayList<Layer>(
				currentContext.getActivatedLayers());
		// remove already de-/activated layers
		activeContextLayers.removeAll(activatedLayers);
		activeContextLayers.removeAll(deactivatedLayers);
		return activeContextLayers;
	}

	// private void updateActivatedLayers(Collection<Layer> toBeActivated,
	// Collection<Layer> toBeDeactivated) {
	// List<Layer> active = list;
	// active.removeAll(toBeDeactivated);
	// active.removeAll(toBeActivated);
	// List<jcop.lang.Layer> oldList = new ArrayList<Layer>(active);
	// active.clear();
	// // add new layers
	// active.addAll(toBeActivated);
	// // append old layers
	// active.addAll(oldList);
	// list = active;
	// // return active;
	// }

	// @Override
	// public ContextComposition clone() {
	// ContextComposition clone;
	// try {
	// clone = (ContextComposition) super.clone();
	// }
	// catch (CloneNotSupportedException e) {
	// clone = new ContextComposition(comp);
	// }
	// clone.sortetActivatedContexts = new ContextList(sortetActivatedContexts);
	// clone.list = new ContextList(list);
	// return clone;
	// }
	@Override
	public ContextComposition clone() {
		ContextComposition clone = new ContextComposition();
		clone.activeContexts = (ContextList)this.activeContexts.clone();
		clone.sortetActiveContexts = (ContextClassInstanceList) this.sortetActiveContexts.clone();		
		return clone;
	}
	
	public boolean isEmpty() {
		return activeContexts.isEmpty();
	}

	@SuppressWarnings("serial")
	class ContextList extends LinkedList<InternalContext> {
		
		public ContextList() {
			super();
		}
		@Override
		public boolean add(InternalContext e) {
			remove(e);
			super.addFirst(e);
			return true;
		}

	}

	@SuppressWarnings("serial")
	class ContextClassInstanceList extends	LinkedHashtable<Class, InternalContext> {
		private ContextList evaluatedContexts;

		public ContextClassInstanceList() {
			evaluatedContexts = new ContextList();
		}
		
		public synchronized java.util.LinkedList<InternalContext> addValueAt(Class key, InternalContext toBeAdded, int pos) {
			java.util.LinkedList<InternalContext> list = get(key);
			if (!list.contains(toBeAdded))
				return super.addValueAt(key, toBeAdded, pos);
			return list;
		}
 
		public ContextList getUnevaluated(Class ctx) {
			ContextList unevaluatedContexts = new ContextList();
			for (InternalContext context : super.get(ctx)) {
				if (!evaluatedContexts.contains(context))
					unevaluatedContexts.add(context);
			}
			return unevaluatedContexts;
		}

		public void add(InternalContext toBeAdded) {
			super.prependValue(toBeAdded.getClass(), toBeAdded);
		}

		public void setEvaluated(InternalContext evaluated) {
			evaluatedContexts.add(evaluated);
		}

		public synchronized LinkedList<InternalContext> remove(InternalContext value) {
			return super.myremove(value.getClass(), value);
		}

		public boolean containsContext(InternalContext value) {
			return get(value.getClass()).contains(value);
		}

		@Override
		public synchronized Object clone() {
			ContextClassInstanceList clone = (ContextClassInstanceList) super	.clone();
			clone.evaluatedContexts = (ContextList) evaluatedContexts.clone();
			return clone;
		}
	}

	public ContextComposition merge(ContextComposition toBeMerged) {
		ContextComposition clone = clone(); 
		clone.activeContexts.addAll((ContextList)toBeMerged.activeContexts.clone());
		clone.sortetActiveContexts.merge((ContextClassInstanceList)toBeMerged.sortetActiveContexts.clone());
		return clone;
	}

	public int getSize() {		
		return activeContexts.size();		
	}

}
