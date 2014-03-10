package jcop.transformation;

import static jcop.Globals.Types.LAYER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import jcop.Globals;
import jcop.Globals.ID;
import jcop.generation.jcopaspect.JCopAspect;
import jcop.generation.layers.InstanceLayerClassGenerator;
import AST.Access;
import AST.ArrayInit;
import AST.BodyDecl;
import AST.ClassAccess;
import AST.ClassDecl;
import AST.ClassInstanceExpr;
import AST.FieldDeclaration;
import AST.ImportDecl;
import AST.LayerDecl;
import AST.List;
import AST.MethodDecl;
import AST.ParseName;
import AST.TypeDecl;

public class LayerDeclTransformer extends Transformer {
	private LayerDecl layerDecl;
	private InstanceLayerClassGenerator gen;
	
	public LayerDeclTransformer(LayerDecl layer) {
		this.layerDecl = layer;		
		gen = new InstanceLayerClassGenerator(layer);					
	}
	
	protected ClassDecl transform() {		
		maybeTransformSubjectTypes();
		LayerMemberSets layerMembers  = collectPartialMethods();		
		transformPartialMethods(layerMembers.getPartialMethods());		
		addLayerToStaticLayerClassList();	
		maybeAddToStaticActiveLayerList();
		addImportsToAspect();
		return  this.gen.generateClassDecl();		
	}
	
	private void addImportsToAspect() {
		for (ImportDecl im : layerDecl.compilationUnit().getImportDeclList())
		JCopAspect.getInstance().addImport(im);
		
	}

	private void transformPartialMethods(Set<MethodDecl> partialMethods) {				
		for (MethodDecl partialMethod : partialMethods)  {		
			transformPartialMethod(partialMethod);			
		}
	}

	private void transformPartialMethod(MethodDecl partialMethod) {		 			
		PartialMethodTransformer pmdTransformer;
		try {
		if (isSourceFile(partialMethod)) {			
			pmdTransformer = new PartialMethodSourceTransformer(layerDecl, partialMethod);
		}
		else
			pmdTransformer = new PartialMethodClassTransformer(layerDecl, partialMethod);
		
		MethodDecl m = pmdTransformer.transform();
		
		TypeDecl host = partialMethod.hostType();
		host.addBodyDecl(m);		
		host.resetCache();
		}catch (Exception e) {System.out.println("error in layer decl");}
	}
	

	
	private void addLayerToStaticLayerClassList() {
		ArrayInit i = lookupStaticLayerClassesInit(ID.allLayerClasses);		
		i.addInit(gen.createLayerTypeAccess().qualifiesAccess(new ClassAccess()));
	}
	
	private void addLayerToStaticLayerList() {
		ArrayInit i = lookupStaticLayerClassesInit(ID.staticActiveLayers);			
		i.addInit(gen.createLayerInit());			
	}

	private ArrayInit lookupStaticLayerClassesInit(String name) {
		FieldDeclaration layerClasses = lookupStaticLayerClassesField(name);
		return (ArrayInit)layerClasses.getInit();		
	}

	private FieldDeclaration lookupStaticLayerClassesField(String name) {
		TypeDecl decl = layerDecl.lookupType(Globals.jcopPackage, LAYER);
		return (FieldDeclaration)decl.memberFields(name).iterator().next();		
	}

	protected  void addDelegationMethodToLayer(MethodDecl delegationMethod, String layername) {		 
		TypeDecl decl = layerDecl.lookupType(Globals.jcopPackage, LAYER);
		addBodyDeclTo(delegationMethod, decl);
	}	
	    
    private void maybeTransformSubjectTypes() {
    	if (layerDecl.hasSubjectTypeDecl()) {
  	      java.util.List<ParseName> subjects = getSubjects(layerDecl.getSubjectTypess());
  	      JCopAspect.getInstance().addLayerActivationStatement(subjects, layerDecl.getFullQualifiedName() + "." + layerDecl.getID());
  	    }		
	}
    
	private void maybeAddToStaticActiveLayerList() {
		if(layerDecl.isStaticActive()) 			
			addLayerToStaticLayerList();		
	}

	private LayerMemberSets collectPartialMethods() {
        Set<MethodDecl> partialMethods = new HashSet<MethodDecl>();
        Set<BodyDecl> otherMembers = new HashSet<BodyDecl>();
        for (BodyDecl decl : this.layerDecl.getBodyDeclList()) {
          if ((decl instanceof MethodDecl)) {
            MethodDecl layerMethod = (MethodDecl)decl;
            if (layerMethod.isPartialMethod()) {         
              partialMethods.add(layerMethod);
            }
            else {
              layerMethod.setModifiers(this.gen.createPublicModifierFor(layerMethod));
              otherMembers.add(decl);
            }
          }
          else
        	  otherMembers.add(decl);
        }
        return new LayerMemberSets(partialMethods,otherMembers);
      }
	
	private boolean isSourceFile(MethodDecl partialMethod) {
		return partialMethod.hostType().compilationUnit().fromSource();
	}
	
    private java.util.List<ParseName> getSubjects(List<Access> subjectTypes) {
    		java.util.List<ParseName> types = new ArrayList();
    		for(int i = 0; i < subjectTypes.getNumChildNoTransform(); i++)
    			types.add( (ParseName)subjectTypes.getChildNoTransform(i));    		
    		return types;
	}
    
//	public void addLayerField() {		
//		layerDecl.addMemberField(gen.generateSingletonReference());			
//	}
	
	
	class LayerMemberSets {

		private Set<MethodDecl> partialMethods;
		private Set<BodyDecl> otherMembers;

		public LayerMemberSets(Set<MethodDecl> partialMethods, Set<BodyDecl> otherMembers) {
			this.partialMethods = partialMethods;
			this.otherMembers = otherMembers;
		}
	
		public Set<MethodDecl> getPartialMethods() {
			return partialMethods;
		}

	
		public Set<BodyDecl> getOtherMembers() {
			return otherMembers;
		}
		
	}
}