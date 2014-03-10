package jcop.transformation;

import jcop.Globals.Types;
import jcop.generation.jcopaspect.AroundAdvice;
import jcop.generation.jcopaspect.Introduction;
import jcop.generation.jcopaspect.JCopAspect;
import jcop.generation.jcopaspect.PartialMethodAdviceGenerator;
import jcop.generation.layers.RootLayerClassGenerator;
import AST.LayerDeclaration;
import AST.MethodDecl;

public class PartialMethodClassTransformer extends PartialMethodSourceTransformer {
	private PartialMethodAdviceGenerator partialAdviceGen;
	private JCopAspect aspect;	
	
	public PartialMethodClassTransformer(LayerDeclaration openLayer, MethodDecl partialMethod) {
		super(openLayer, partialMethod);	
		
		this.partialAdviceGen = new PartialMethodAdviceGenerator(openLayer, partialMethod);
		this.aspect = JCopAspect.getInstance();
	
	}  

	protected void generateDelegationMethodInLayer(String genMethodName, String methodToBeCalled) {		
		super.generateDelegationMethodInLayer(genMethodName, baseMethod.getID());
	}
	
////	private void addElementsToAspect() {
////		LayerDecl layerDecl = this.layerDecl;
////		JCopAspect.getInstance().addLayerDecls(layerDecl);
////		JCopAspect.getInstance().addLayerImport(layerDecl);		
////	}
////	
//		protected MethodDecl transform() {						
//			if (isLayerLocalMethod())
//				createBaseMethod();					
//			
//	System.out.println("transform");
////			//malte: visitedBaseMemberForCodeGeneration.add(pmd);
////			//visitedBaseMemberForDelegationMethodGeneration.add(baseMethod);
////			TypeDecl hostType = partialMethod.hostType();
////			if (VisitedNodes.firstVisit(hostType)) {
////				transformHostTypeModifier(hostType);
////				addIm
//			
////			}				
////			createDelegationMethods();			
//			return  new MethodDecl(new Modifiers(), new VoidType().createBoundAccess(),"dummy", new List<ParameterDeclaration>(), new List<Access>(), new Opt<Block>(new Block()) );// 
//			//partialGen.generateLayeredMethod();			
//		}
//
//		
//
//		private boolean isLayerLocalMethod() {
//			return baseMethod == null;		
//		}
		

		protected void createBaseMethod() {
			super.createBaseMethod();
			Introduction intro = partialAdviceGen.genIntroductionFor(baseMethod);				
			aspect.addMember(intro);			
		}

		public MethodDecl createLayeredMethod() {			
			MethodDecl wrapper = super.createLayeredMethod();			
		//	Introduction intro = partialAdviceGen.genIntroductionFor(wrapper);			
		//	aspect.addMember(intro);
			return wrapper;
		}
		
		public void transformBaseMethod() {
			super.transformBaseMethod();
			AroundAdvice intro = partialAdviceGen.genAroundAdviceFor(baseMethod);			
			aspect.addMember(intro);
		}		
		
		protected MethodDecl createPartialMethod() {			
			MethodDecl m =  partialGen.generatePartialMethod();
			addBodyDeclTo(m, partialMethod.hostType());			
			Introduction intro = partialAdviceGen.genIntroductionFor(m);		
			aspect.addMember(intro);
			System.out.println(intro);
			return m;
		}
			
//		private TypeDecl getHostType() {		
//			return  partialMethod.hostType();//.topLevelType();		
//		}	
//		
//		private void addBodyDeclToEnclosing(MethodDecl decl) {
//			TypeDecl host = getHostType();
//			addBodyDeclTo(decl, host);		
//		}	
//
////		public void createDelegationMethods() {		
////			String genMethodName = partialGen.genDelegationMethodName(partialMethod);			
////			if (firstDelegationMethodGeneration(baseMethod)) {
////				generateDelegationMethodInConcreteLayer(genMethodName);
////				generateDelegationMethodInLayer(genMethodName);			
////			}		
////			generateDelegationMethodsInInstanceLayer(genMethodName);	 	
////		}
//		
//		private void generateDelegationMethodInLayer(String genMethodName) {			
//			RootLayerClassGenerator gen = new RootLayerClassGenerator(openLayer);
//			MethodDecl method = gen.genDelegationMethod(partialMethod, genMethodName, baseGen.genWrapperIdentifier());
//			addDelegationMethodToLayer(method, Types.LAYER);		
//		}
//
//		private void generateDelegationMethodInConcreteLayer(String genMethodName) {			
//			ConcreteLayerClassGenerator gen = new ConcreteLayerClassGenerator(openLayer);
//			MethodDecl method = gen.genDelegationMethod(partialMethod, genMethodName);
//			addDelegationMethodToLayer(method, Types.CONCRETE_LAYER);		
//		}

//		private void generateDelegationMethodsInInstanceLayer(String genMethodName) {		
//			InstanceLayerClassGenerator gen = new InstanceLayerClassGenerator(openLayer);
//			MethodDecl method = gen.genDelegationMethod(partialMethod, genMethodName);		
//			addBodyDeclTo(method, openLayer.hostLayer());	
//			Stmt metaInit = partialGen.genPartialMethodMetaClassInstantiation();
//			addToInit(metaInit);
//		}
		
		
}
