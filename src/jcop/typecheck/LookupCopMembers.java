package jcop.typecheck;

import java.util.*;

import jcop.Globals.Msg;
import AST.*;

/*
  This class holds a layer table and a partial method table, and
  provides method/layer lookup functions.
*/
public class LookupCopMembers{
	private static Program program;
	private static LookupCopMembers instance = null;

	// Dummy base layer
	private LayerDecl baseLayer = new LayerDecl();

	// full qualified method name -> layer decl -> method decl
	private Hashtable<String, Hashtable<LayerDecl, MethodDecl>> methodTable;

	// all layer list
	private ArrayList<LayerDecl> alllayerlist;
	
	// type -> method name -> full qualified method name
	private Hashtable<TypeDecl,Hashtable<String, java.util.List<String>>> nametable;
	
	// staticactive layer list
	private ArrayList<LayerDecl> staticactivelist;
	

	private LookupCopMembers(){
		methodTable = new Hashtable<String, Hashtable<LayerDecl, MethodDecl>>();
		alllayerlist = new ArrayList<LayerDecl>();
		staticactivelist = new ArrayList<>();
		nametable = new Hashtable<>();
	}
	
	/* 
	   Register the program, in order to use its lookup functions.
	*/
	public void setupInstance(Program program){
		if(this.program == null){
			this.program = program;
			//instance = new LookupCopMembers(program);
			//program.createPartialMethodTable();
			// add all layer to reqtypes of jcop.lang.LLayer

			// LayerDecl llayer = (LayerDecl)program.lookupType("jcop.lang","LLayer");
			// for(LayerDecl l : alllayerlist)
			// 	llayer.addRequireTypes(new TypeAccess(l.packageName(),l.name()));
			// llayer.flushCache();
		}
	}

	/* 
	   Singleton.
	*/
	public static LookupCopMembers getInstance(){
		if(instance == null)
			instance = new LookupCopMembers(); 
		return instance;
	}
	
	/*
	  Add method to nametable.
	 */
	public void addMethodToNameTable(MethodDecl m){
		String name = m.getID();
		TypeDecl type = m.hostType();
		if(!nametable.containsKey(type)){
			nametable.put(type, new Hashtable<String,java.util.List<String>>());
		}
		Hashtable<String,java.util.List<String>> nt = nametable.get(type);
		if(!nt.containsKey(name)){
			nt.put(name, new ArrayList<String>());
		}
		if(!nt.containsValue(method2key(m))) 
			nt.get(name).add(method2key(m));
	}

	/*
	  Get method signatures from a method name and a type.
	*/
	private java.util.List<String> getFullsigList(String name, TypeDecl type){
		Hashtable<String,java.util.List<String>> nt = nametable.get(type);
		if(nt == null || !nt.containsKey(name))
			return new ArrayList<>();
		return nt.get(name);
	}

	/*
	  Get method signatures from a method name and a type.
	*/
	private java.util.List<String> getFullsigListWithInheritance(String name, TypeDecl type){
		ArrayList<String> slist = new ArrayList<>();
		TypeDecl buftype = type;
		Hashtable<String,java.util.List<String>> nt;
		while(buftype != null){
			nt = nametable.get(buftype);
			if(nt == null || !nt.containsKey(name)){
			}else{
				slist.addAll(nt.get(name));
			}
			if(buftype instanceof ClassDecl){
				buftype = ((ClassDecl)buftype).superclass();
			}else{
				break;
			}
		}
		return slist;
	}

	/*
	  Get a method declaration from layers and method signature.
	*/
	private MethodDecl getValidMethod(java.util.List<LayerDecl> layers, String fullsig){
		Hashtable<LayerDecl,MethodDecl> table = getSameSignatureMethodsTable(fullsig);
		if(table.containsKey(baseLayer)){
			return table.get(baseLayer);
		}

		for(Iterator<LayerDecl> iter = layers.iterator(); iter.hasNext();){
			LayerDecl bufl = iter.next();
			while(bufl != null){
				if(table.containsKey(bufl)){
					return table.get(bufl);
				}
				bufl = bufl.superlayer();
			}
		}
		return null;
	}

	/*
	  Get candidates from layer list, type, and method name.  This
	  corresponds to the mtype(m,C,Lambda) function of ContextFJ.  
	*/
	public java.util.List<MethodDecl> memberMethodsInLayers(java.util.List<LayerDecl> layers, TypeDecl type, String method_id){
		java.util.List<String> fslist = getFullsigListWithInheritance(method_id, type);
		ArrayList<MethodDecl> mlist = new ArrayList<>();
		for(Iterator<String> iter = fslist.iterator(); iter.hasNext();){
			String fullsig = iter.next();
			MethodDecl mbuf = getValidMethod(layers,fullsig);
			if(mbuf != null) mlist.add(mbuf);
		}
		return mlist;
	}

	/*
	  Get candidates from layer list, type, and method name.  This
	  corresponds to the mtype(m,C,Lambda1,Lambda2) function of ContextFJ.  
	*/
	public java.util.List<MethodDecl> memberMethodsInLayers(java.util.List<LayerDecl> layers, java.util.List<LayerDecl> layers_plus_l, TypeDecl type, String method_id){
		java.util.List<String> fslist = getFullsigList(method_id, type);
		ArrayList<MethodDecl> mlist = new ArrayList<>();
		for(Iterator<String> iter = fslist.iterator(); iter.hasNext();){
			String fullsig = iter.next();
			MethodDecl mbuf = getValidMethod(layers,fullsig);
			if(mbuf != null) mlist.add(mbuf);
		}
		mlist.addAll(memberMethodsInLayers(layers_plus_l, ((ClassDecl)type).superclass(), method_id));
		return mlist;
	}

	/*
	  Add a layer declaration to layerlist.
	*/
	public void addLayer(LayerDecl l){
		if(!alllayerlist.contains(l)) alllayerlist.add(l);
	}

	/*
	  Get full qualified method name from MethodDecl.
	*/
	private String method2key(MethodDecl m){
		return m.getFullQualifiedName();
	}

	/*
	  Get base layer.
	*/
	public LayerDecl getBaseLayer(){
		return baseLayer;
	}
	
	/*
	  Get full qualified method name of the super method of method m.
	*/
	public String getSuperMethodSignature(MethodDecl m){
		return getSuperMethodSignature((ClassDecl)m.declaringType(), m.getFullQualifiedName());
	}

	/*
	  Get full qualified method name of the super method identified by
	  the class c and the signature.
	*/
	public String getSuperMethodSignature(ClassDecl c, String fullsig){
		if(!c.hasSuperclass()) return null;
		int p = fullsig.indexOf(c.typeName());
		if(p < 0){
			throw new RuntimeException("[JCopTypeCheck]unknown method signature");
		}
		return c.superclass().typeName() + fullsig.substring(p + c.typeName().length());
	}

	/*
	  Get base method from the partial method.
	*/
	public MethodDecl getBaseMethod(PartialMethodDecl pmd){
		return getBaseMethod((ClassDecl)pmd.hostType(), method2key(pmd));
	}

	/*
	  Get base method from the partial method identified by the class
	  c and the signature.
	*/	
	public MethodDecl getBaseMethod(ClassDecl c, String pmdsig){
		if(getSameSignatureMethodsTable(pmdsig).containsKey(baseLayer)){
			return getSameSignatureMethodsTable(pmdsig).get(baseLayer);
		}
		
		if(c.superclass() != null)
			return getBaseMethod(c.superclass(), getSuperMethodSignature(c, pmdsig));
			
		return null;
	}

	/*
	  Get hashtable that contains all partial methods as values, and
	  corresponding layers as keys.
	*/
	public Hashtable<LayerDecl, MethodDecl> getSameSignatureMethodsTable(MethodDecl m){
		return methodTable.get(method2key(m));
	}

	public Hashtable<LayerDecl, MethodDecl> getSameSignatureMethodsTable(String sig){
		return methodTable.get(sig);
	}

	/*
	  Add the method to the methodtable.
	*/
	public void addMethod(MethodDecl m){
		if(m == null) return;
		String sig = method2key(m);
		if(!methodTable.containsKey(sig)){
			methodTable.put(sig, new Hashtable<LayerDecl, MethodDecl>());
		}
		Hashtable<LayerDecl,MethodDecl> table = methodTable.get(sig);
		
		if(!table.containsValue(m))
			table.put(m.hostLayerDecl(), m);
		
		addMethodToNameTable(m);
	}

	/*
	  Get layers that holds a method of same signature of the method
	  m.
	*/
	public Set<LayerDecl> getLayers(MethodDecl m){
		return getSameSignatureMethodsTable(m).keySet();
	}

	// public boolean someExcludeProceed(ClassDecl c, String fullsig, java.util.List<LayerDecl> layerlist){
	// 	Hashtable<LayerDecl, MethodDecl> table = getSameSignatureMethodsTable(fullsig);
	// 	if(table != null){
	// 		if(table.containsKey(baseLayer))
	// 			return true;
	// 		for(LayerDecl layer : layerlist){
	// 			if(table.get(layer) != null && !table.get(layer).haveProceed())
	// 				return true;
	// 		}
	// 	}
	// 	if(c.superclass() != null)
	// 		return someExcludeProceed(c.superclass(), getSuperMethodSignature(c,fullsig), layerlist);
	// 	return false;
	// }

	// public boolean someExcludeProceed(PartialMethodDecl m, java.util.List<LayerDecl> layerlist){
	// 	return someExcludeProceed(m.hostType(), method2key(m), layerlist);
	// }

	// public boolean isNewMethod(PartialMethodDecl m){
	// 	try{
	// 		return getBaseMethod(m) == null;
	// 	}catch(NullPointerException e){
	// 		return false;	
	// 	}
	// }

	// public Set<Pair<String, ClassDecl>> getInterfaceWithLayers(java.util.List<LayerDecl> layerlist){
	// 	Set<Pair<String,ClassDecl>> sigpairs = new HashSet<Pair<String,ClassDecl>>();
	// 	if(layerlist == null) return sigpairs;
	// 	for(LayerDecl layer : layerlist){
	// 		sigpairs.addAll(layer.partialInterface());
	// 	}
	// 	return sigpairs;
	// }
	
	// public boolean isSafeLayers(java.util.List<LayerDecl> layerlist){
	// 	if(layerlist == null) return true;
	// 	for(Pair<String,ClassDecl> sigpair : getInterfaceWithLayers(layerlist)){
	// 		if(!someExcludeProceed(sigpair.getR(), sigpair.getL(), layerlist))
	// 			return false;
	// 	}
	// 	return true;
	// }
	
	// public boolean canWithout(java.util.List<LayerDecl> withoutlayerlist){
	// 	if(withoutlayerlist == null) return true;
	// 	for(LayerDecl otherl : alllayerlist){
	// 		if(!withoutlayerlist.contains(otherl)){
	// 			for(LayerDecl withoutl : withoutlayerlist){
	// 				if(otherl.requireLayers().contains(withoutl))
	// 					return false;
	// 			}
	// 		}
	// 	}
	// 	return true;
	// }
	
	public void addStaticActiveLayers(String fullname){
		TypeDecl decl = program.lookupType(fullname.substring(0, fullname.lastIndexOf('.')), fullname.substring(fullname.lastIndexOf('.') + 1));
		if(decl.isLayerDecl()){
			staticactivelist.add((LayerDecl)decl);
		}
	}
	
	public java.util.List<LayerDecl> getStaticActiveLayers(){
		ArrayList<LayerDecl> list = new ArrayList<LayerDecl>();
		list.addAll(staticactivelist);
		return list;
	}

	public static String layersToString(java.util.List<LayerDecl> llist){
		java.util.List<String> ret = new ArrayList<>();
		for(LayerDecl l : llist){
			ret.add(l.name());
		}
		return ret.toString();
	}
}
