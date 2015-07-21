package jcop.compiler;

import jcop.Globals;
import jcop.Globals.Types;
import jcop.transformation.lookup.Lookup;
import AST.Access;
import AST.ClassDecl;
import AST.CompilationUnit;
import AST.LayerDeclaration;
import AST.TypeAccess;
import AST.TypeDecl;

public class JCopTypes {
		public static TypeDecl getLayer(CompilationUnit unit) {
				return get(unit, Types.LAYER);
		}
		public static ClassDecl getConcreteLayer(CompilationUnit unit) {
				return (ClassDecl)get(unit, Types.CONCRETE_LAYER);
		}
		public static TypeDecl getComposition(CompilationUnit unit) {
				return get(unit, Types.COMPOSITION);		
		}
		public static TypeDecl getPartialMethod(CompilationUnit unit) {
				return get(unit, Types.PARTIAL_METHOD);
		}
		public static TypeDecl getPartialField(CompilationUnit unit) {
				return get(unit, Types.PARTIAL_FIELD);
		}
		public static TypeDecl getLayerProvider(CompilationUnit unit) {
				return get(unit, Types.LAYER_PROVIDER);
		}
		public static TypeDecl getLayered(CompilationUnit unit) {
				return get(unit, Types.LAYERED);
		}
		public static TypeDecl getLayeredState(CompilationUnit unit) {
				return get(unit, Types.LAYERED_STATE);
		}

		private static TypeDecl get(CompilationUnit unit, String type) {		
				return unit.lookupType(Globals.jcopPackage, type); 
		}
	
	
		public static class JCopAccess {
				//		public static TypeAccess getLayer() {
				//			return get(Types.Layer);
				//		}
				//		public static TypeAccess getContext() {
				//			return get(Types.Context);
				//		}
				//		public static TypeAccess getConcreteLayer() {
				//			return get(Types.ConcreteLayer);
				//		}
				//		public static TypeAccess getComposition() {
				//			return get(Types.Composition);		
				//		}
				//		public static TypeAccess getPartialMethod() {
				//			return get(Types.PartialMethod);
				//		}
				//		public static TypeAccess getPartialField() {
				//			return get(Types.PartialField);
				//		}
				//		public static TypeAccess getLayerProvider() {
				//			return get(Types.LayerProvider);
				//		}
				//		public static TypeAccess getLayered() {
				//			return get(Types.Layered);
				//		}
				//		public static TypeAccess getIntContext() {
				//			return get(Types.InternalContext);
				//		}
				//		public static TypeAccess getContextManager() {
				//			return get(Types.ContextManager);
				//		}
				//		public static TypeAccess getLayeredState() {
				//			return get(Types.LayeredState);
				//		}
				//		public static TypeAccess getLayerList() {		
				//			return get(Types.LayerList);
				//		}
				//		
				//		public static TypeAccess getLayeredMethodAnnotation() {
				//			return get(Types.LayeredMethodAnnotation);
				//		}
				//		public static TypeAccess getBaseMethodAnnotation() {
				//			return get(Types.LayeredMethodAnnotation);
				//		}
		
				public static TypeAccess getLayerType(LayerDeclaration layer) {
						String pckg = Lookup.lookupLayerClassDecl(layer).packageName();		
						return new TypeAccess(pckg, layer.getID());
			
						//			
						//			return jcop.transformation.lookup.Lookup.lookupLayerClassDecl(layer).createBoundAccess();
						//			//return new TypeAccess(ASTlayer.enclosing().hostPackage(), layer.name());
				}
		
				public  static TypeAccess get(String type) {
						return new TypeAccess(Globals.jcopPackage, type);
				}
				public static TypeAccess getStringAccess() {
						return new TypeAccess("java.lang", "String");
				}
		
		}
}
