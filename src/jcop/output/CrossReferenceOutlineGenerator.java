package jcop.output;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import jcop.Globals.ID;
import AST.BodyDecl;
import AST.CompilationUnit;
import AST.LayerDecl;
import AST.LayerDeclaration;
import AST.MemberClassDecl;
import AST.OpenLayerDecl;
import AST.TypeDecl;

public class CrossReferenceOutlineGenerator extends XMLOutlineGenerator {
	private LayerDict layerDic;
	
	public CrossReferenceOutlineGenerator(File basePath) {
		super(basePath);		
		this.layerDic = new LayerDict();
	}
		
	public void add(CompilationUnit unit) {
		if (isAuxiliaryJCopClass(unit))
			return;
		
		List<LayerDeclaration> layers = getLayers(unit);
		for (LayerDeclaration _layer : layers) {
			String layerName = _layer.hostLayer().getFullQualifiedName();			
			for (BodyDecl decl : _layer.getBodyDecls()) {				
				StringBuffer s = layerDic.getMethodDict(layerName, decl.hostType());
				decl.printOutline(s);
			}
		}			
	}

	
	public void writeToFile() {
		fileDumper.writeToFile(ID.classInLayerFileName, toString());
	}
	
	@Override
	public String toString() {
		StringBuffer s = createBuffer();
		s.append("<class-in-layer>\n");
		for (String _layer: layerDic.getLayers()) {			
			s.append("<layer name=\"" + _layer + "\">\n");
			for (TypeDecl type : layerDic.getTypesForLayer(_layer)) {
				s.append("<class name=\"" + type.getFullQualifiedName() + "\" line=\"" + type.sourceLineNumber() + "\">\n");
				type.getModifiers().printOutline(s);
				s.append("<declarations>\n");
				s.append(layerDic.getMethodDict(_layer, type));
				s.append("</declarations>\n");
				s.append("</class>\n");			
			}
			s.append("</layer>\n");
		} 
		s.append("</class-in-layer>\n");
		return s.toString();
	}
	
	
	//TODO ugly!
	private List<LayerDeclaration> getLayers(CompilationUnit unit) {		
		List<LayerDeclaration> layers = new ArrayList<LayerDeclaration>();
		for (TypeDecl decl : unit.getTypeDecls()) {
			if (isLayerDecl(decl))
				layers.add((LayerDeclaration)decl);
			else if(decl.isClassDecl()) {			
				for (BodyDecl member : decl.getBodyDecls()) {
					if (member instanceof OpenLayerDecl) 
						layers.add((LayerDeclaration)member);					
				}			
			}
		}
		return layers;
	}

	private boolean isLayerDecl(TypeDecl decl) {
		return decl instanceof LayerDecl;		
	}

	class LayerDict extends Hashtable<String, Hashtable<TypeDecl, StringBuffer>> {
		
		private Hashtable<TypeDecl, StringBuffer> getDictForLayer(String _layer) {
			if (!containsKey(_layer))
				put(_layer, new Hashtable<TypeDecl, StringBuffer>());
			return get(_layer);
		}
		
		public StringBuffer getTypeDict(TypeDecl hostType, Hashtable<TypeDecl, StringBuffer> dict) {
			if (!dict.containsKey(hostType))
				dict.put(hostType, new StringBuffer());
			return dict.get(hostType);
		}

		public StringBuffer getMethodDict(String _layer, TypeDecl hostType) {
			Hashtable<TypeDecl, StringBuffer> layerDic = 
				getDictForLayer(_layer);
			return getTypeDict(hostType, layerDic);			
		}
		
		public String[] getLayers() {
			return keySet().toArray(new String[0]);
		}
		
		public TypeDecl[] getTypesForLayer(String _layer) {			
			return get(_layer).keySet().toArray(new TypeDecl[0]);
		}
		
		
	}
}
