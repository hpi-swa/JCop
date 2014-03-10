package jcop.transformation;

import static jcop.Globals.Types.LAYERED_METHOD_ANNOTATION;
import jcop.Globals.Types;
import jcop.Globals;
import jcop.VisitedNodes;
import jcop.generation.layermembers.BaseMethodGenerator;
import jcop.generation.layermembers.PartialMethodGenerator;
import jcop.generation.layers.ConcreteLayerClassGenerator;
import jcop.generation.layers.InstanceLayerClassGenerator;
import jcop.generation.layers.RootLayerClassGenerator;
import jcop.transformation.lookup.Lookup;
import AST.Block;
import AST.Expr;
import AST.ImportDecl;
import AST.LayerDeclaration;
import AST.List;
import AST.MethodDecl;
import AST.Modifiers;
import AST.SimpleSet;
import AST.Stmt;
import AST.TypeDecl;



public class PartialMethodSourceTransformer extends PartialMethodTransformer {	
	protected PartialMethodGenerator partialGen;
	protected BaseMethodGenerator baseGen;	
	protected MethodDecl partialMethod;
	protected MethodDecl baseMethod;	
		
		public PartialMethodSourceTransformer(LayerDeclaration openLayer, MethodDecl partialMethod) {		
			super(openLayer);		
			this.partialMethod = partialMethod;			
			this.baseMethod = Lookup.lookupMethodCorrespondingTo(partialMethod);				
			this.partialGen = new PartialMethodGenerator(openLayer, partialMethod);			
			this.baseGen = new BaseMethodGenerator(partialMethod, baseMethod);			
		}  
		
		protected MethodDecl transform() {
			
			
			if (isLayerLocalMethod() || baseMethodIsDeclaredInSuperClass()) {					
				createBaseMethod();					
			}
			if (!hasLayeredMethod() )
				createLayeredMethod();
			if (VisitedNodes.firstVisit(baseMethod) ) 
				transformBaseMethod();			
			//malte: visitedBaseMemberForCodeGeneration.add(pmd);
			//visitedBaseMemberForDelegationMethodGeneration.add(baseMethod);
			
			TypeDecl hostType = partialMethod.hostType();
			if (VisitedNodes.firstVisit(hostType)) { 
				transformHostTypeModifier(hostType);
				addImportsOfLayerDeclToHost(hostType);
			}			
			createDelegationMethods();				
			    //new MethodDecl(new Modifiers(), new VoidType().createBoundAccess(),"dummy", new List<ParameterDeclaration>(), new List<Access>(), new Opt<Block>(new Block()) );// 
			MethodDecl m = createPartialMethod();			
			layerClass.resetCache();	
			return m;
		}

		protected MethodDecl createPartialMethod() {
			return partialGen.generatePartialMethod();			
		}

		private void addImportsOfLayerDeclToHost(TypeDecl hostType) {		
			for(ImportDecl im : getImportsofHostLayer()) {
				if (!importOfThatType(im, hostType))
					hostType.compilationUnit().addImportDecl(im);	
			}							
		}
		
		
		
		private boolean importOfThatType(ImportDecl im, TypeDecl thatType) {
			return im.getAccess().type() == thatType;		
		}

		private List<ImportDecl> getImportsofHostLayer() {
			return openLayer.hostLayer().compilationUnit().getImportDeclList();
		}

		private void transformHostTypeModifier(TypeDecl hostType) {
			if (!hostType.isPublic())
				hostType.setModifiers(partialGen.createPublicModifierFor(hostType));
			
		}

		private boolean isLayerLocalMethod() {			 
			return baseMethod == null ;		
		}
		
		private boolean baseMethodIsDeclaredInSuperClass() {
			if (!isLayerLocalMethod())
				return  (baseMethod.hostType() != partialMethod.hostType());
			else
				return false;			
		}

		protected void createBaseMethod() {			
			baseMethod = baseGen.generateBaseMethod(baseMethodIsDeclaredInSuperClass());			
			baseGen.setBaseMethod(baseMethod);
			partialGen.setBaseMethod(baseMethod);
			//malte: brauche ich das?
			//setBaseForPartialMethod(baseMethod, partialMethod);
			addBodyDeclToEnclosing(baseMethod);			
		}

		public MethodDecl createLayeredMethod() {			
			MethodDecl wrapper = baseGen.generateWrapper();			
			addBodyDeclToEnclosing(wrapper);
			return wrapper;
		}
		
		public void transformBaseMethod() {
			MethodDecl baseMethod = 	this.baseMethod;				
			Block activationBlock = baseGen.generateLayerActivationBlock();
			Modifiers m = this.baseGen.createPublicModifierFor(baseMethod);
			m.addModifier(baseGen.genAnnotation(LAYERED_METHOD_ANNOTATION));
			baseMethod.setModifiers(m);			 
			baseMethod.setBlock(activationBlock);					
		}		
			
		private TypeDecl getHostType() {		
			return  partialMethod.hostType();//.topLevelType();		
		}	
		
		protected void addBodyDeclToEnclosing(MethodDecl decl) {
			TypeDecl host = getHostType();
			addBodyDeclTo(decl, host);		
		}	

		public void createDelegationMethods() {		
			String genMethodName = partialGen.genDelegationMethodName(partialMethod);			
			if (firstDelegationMethodGeneration(baseMethod)) {
				generateDelegationMethodInConcreteLayer(genMethodName);
				generateDelegationMethodInLayer(genMethodName, baseGen.genWrapperIdentifier());			
			}		
			generateDelegationMethodsInInstanceLayer(genMethodName);	 	
		}
		
		protected void generateDelegationMethodInLayer(String genMethodName, String methodToBeCalled) {			
			RootLayerClassGenerator gen = new RootLayerClassGenerator(openLayer);
			
			MethodDecl delegatee = gen.genDelegationMethod(partialMethod, genMethodName, methodToBeCalled);
			addDelegationMethodToLayer(delegatee, Types.LAYER);
			 
			MethodDecl superLayerMethod = gen.genDelegationMethodToSuperLayer(partialMethod, methodToBeCalled);
			addDelegationMethodToLayer(superLayerMethod, Types.LAYER);			
		}

		private void generateDelegationMethodInConcreteLayer(String genMethodName) {			
			ConcreteLayerClassGenerator gen = new ConcreteLayerClassGenerator(openLayer);			
			MethodDecl method = gen.genDelegationMethod(partialMethod, genMethodName);
			addDelegationMethodToLayer(method, Types.CONCRETE_LAYER);		
		}

		private void generateDelegationMethodsInInstanceLayer(String genMethodName) {		
			InstanceLayerClassGenerator gen = new InstanceLayerClassGenerator(openLayer);
			MethodDecl method = gen.genDelegationMethod(partialMethod, genMethodName);		
			addBodyDeclTo(method, layerClass);
			MethodDecl superLayerMethod = gen.genDelegationMethodToSuperLayer(partialMethod, genMethodName);		
			addBodyDeclTo(superLayerMethod, layerClass);	
			Stmt metaInit = partialGen.genPartialMethodMetaClassInstantiation();
			addToInit(metaInit);			
		}
		
	
		
		
		
		private boolean hasLayeredMethod() {
			MethodDecl method = baseMethod;

			String sig = baseGen.genWrapperSignature();			
		    SimpleSet candidates = method.hostType().methodsSignature(sig);
		    
		    return (!candidates.isEmpty());	    	
		}
}
