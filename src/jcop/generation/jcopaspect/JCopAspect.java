package jcop.generation.jcopaspect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jcop.Globals;
import jcop.Globals.ID;
import jcop.Globals.Types;
import jcop.generation.jcopaspect.internal.SourceCodeBuffer;
import jcop.transformation.ASTTools.Generation;
import AST.BodyDecl;
import AST.ClassDecl;
import AST.ContextDecl;
import AST.FieldDeclaration;
import AST.ImportDecl;
import AST.LayerDecl;
import AST.LayerDeclaration;
import AST.MemberDecl;
import AST.ParseName;
import AST.PartialMethodDecl;
import AST.PointcutExpr;
import AST.TypeDecl;

public class JCopAspect  {	
	private static JCopAspect singleton;
	private HashSet<TypeDecl> layerImports;
	private HashSet<ImportDecl> imports;
	private SourceCodeBuffer advice;
	private SourceCodeBuffer context;
	
	public static JCopAspect getInstance() {
		if (singleton == null)
			singleton = new JCopAspect();
		return singleton;
	}	
	
	private JCopAspect() {			
		this.layerImports = new HashSet<TypeDecl>();
		this.imports = new HashSet<ImportDecl>();
		this.advice = new SourceCodeBuffer();
		this.context = new SourceCodeBuffer();
	}

	public void addWrapperForField(FieldDeclaration field, LayerDeclaration layer) {
		String fqnName = layer.hostType().topLevelType().fullName() + "." + field.name();
		String flattenedName = fqnName.replaceAll("\\.", "_");		
		PartialFieldAdviceGenerator gen = new PartialFieldAdviceGenerator(advice);
		gen.createFieldAccessorAdviceOnce(field, fqnName, flattenedName);		
	}
	
	public void addContextActivations(ContextDecl decl, PointcutExpr pointcutSignature, List<String> executionSignatures) {
		ContextClassAdviceGenerator gen = new ContextClassAdviceGenerator(this.context, decl, pointcutSignature);
		gen.genContextActivation(executionSignatures);
	}
	
	public void addStaticContextActivation(ContextDecl decl) {
		StaticContextObjectAdviceGenerator gen = new StaticContextObjectAdviceGenerator(advice, decl);
		gen.genContextActivation();
	}
	
	public void addLayerActivationStatement(List<ParseName> subjects, String layerComposition) {
		SubjectLayerAdviceGenerator gen = new SubjectLayerAdviceGenerator(advice);		
		gen.genLayerActivationStatement(subjects, layerComposition);			
	}
	
	public void addLayerImport(LayerDeclaration layer) {
		ClassDecl decl = jcop.transformation.lookup.Lookup.lookupLayerClassDecl(layer);
		layerImports.add(decl);		
	}		
		
	private SourceCodeBuffer genAspectHeader() {
		SourceCodeBuffer buffer = new SourceCodeBuffer();  
		buffer.putLine("package ", Globals.jcopPackage, ";");
//		for(TypeDecl importType : layerImports)
//			buffer.putLine("import ", importType.getFullQualifiedName(), ";");
		for(ImportDecl importDecl : imports)
			buffer.putLine(importDecl.toString());
		buffer.putLineOpenBracket(" public privileged aspect ", Types.ASPECT);
		return buffer;
	}
	
	private String [][] globalValues = {			
			{"jcopPckg", Globals.jcopPackage},
			{"composition", "$jcopPckg$." + Types.COMPOSITION},						
			{"layer", "$jcopPckg$." +Types.LAYER },
			{"concreteLayer", "$jcopPckg$." + Types.CONCRETE_LAYER},
			{"currentComposition", "$jcopPckg$." + Types.JCOP + ".current()"},
			{"outermostLayer", "$currentComposition$.firstLayer()"},
			{"finallyStmt", "finally {" + Types.JCOP + ".setComposition(oldComposition);}"},			
			{ "compVar", Globals.ID.composition},
			{ "targetVar", Globals.ID.targetParameterName},
			{ "notifyExecution", Globals.ID.notifyExecution},
	        {"compositionVarAssignment", "$composition$ oldComposition= $currentComposition$"}};	
	 	
	public String generateSource() {
		this.advice.instantiatePatternWith(this.globalValues);
	    this.context.instantiatePatternWith(this.globalValues);
	    SourceCodeBuffer buffer = genAspectHeader();
	    buffer.indent(true);
	    buffer.add(this.context);
	    buffer.add(this.advice);
	 

	    buffer.closeBracket();
	    return buffer.toString();
	}
		
//	private SourceCodeBuffer genDelegationMethods() {
//		Set<String> candidatesSignatures = new HashSet<String>(unvisitedMemberForDelegation);				
//		candidatesSignatures.removeAll(visitedMemberForDelegation);
//		 SourceCodeBuffer buffer = new SourceCodeBuffer();
//		for (String candidateSignature : candidatesSignatures) {			
//			SourceCodeBuffer b = PartialMethodAdviceGenerator.getInterTypeDeclarationsFor(candidateSignature);			
//			buffer.add(b);
//		}
//		buffer.instantiatePatternWith(globalValues);
//		return buffer;
//	}

//	public void addLayerDecls(LayerDecl decl) {		
//		PartialMethodAdviceGenerator gen = new PartialMethodAdviceGenerator(advice, decl);		 
//		AST.List<BodyDecl> bodyDecls = decl.getBodyDeclList();		
//		addLayerDecls(bodyDecls, decl, gen); 
//		addImportsFromLayerDecl(decl);		
//	}

//	private void addImportsFromLayerDecl(LayerDecl decl) {
//		for (ImportDecl imp : decl.compilationUnit().getImportDeclList())
//			imports.add((ImportDecl)imp.fullCopy());	
//	}

//	private void addLayerDecls(AST.List<BodyDecl> bodyDecls, LayerDecl decl, PartialMethodAdviceGenerator gen) {
//		for (int i = 0; i < bodyDecls.getNumChild(); i++) {
//			BodyDecl bodyDecl = bodyDecls.getChild(i);
//			//TODO: ugly!
//			if (bodyDecl instanceof PartialMethodDecl) {				
//				PartialMethodDecl partialMethodDecl = (PartialMethodDecl) bodyDecl;				
//				addLayerDecl(decl, partialMethodDecl, gen);
//				bodyDecls.removeChild(i);				
//				addLayerDecls(bodyDecls, decl, gen);			
//			}
//		}
//	}	

	
	private List<String> unvisitedMemberForDelegation = new ArrayList<String>();
	
	private void addUnvisitedMemberForDelegation(MemberDecl visited) {
		unvisitedMemberForDelegation.add(visited.getFullQualifiedName());
	}
	
	private List<String> visitedMemberForDelegation = new ArrayList<String>();
	
	public void addVisitedMemberForDelegation(MemberDecl visited) {
		visitedMemberForDelegation.add(visited.getFullQualifiedName());		
	}
	
//	private void addLayerDecl(LayerDecl layerDecl, PartialMethodDecl partialMethod, PartialMethodAdviceGenerator gen) {		
//		if (isInstanceActivationMethod(partialMethod))
//			addMemberToLayerClass(layerDecl, partialMethod);
//		else {			
//			gen.addPartialMethod(partialMethod);
//			addUnvisitedMemberForDelegation(partialMethod);
//		}
//	}

	private boolean isInstanceActivationMethod(PartialMethodDecl m) {
		String name = m.getNamePatternNoTransform().toString();
		return name.equals(ID.implicitActivationMethodName);
	}	

	private void addMemberToLayerClass(LayerDeclaration decl, PartialMethodDecl method) {
		// LayerClassGenerator gen = new LayerClassGenerator(LayerDecl);		
		Generation.removeStaticModifier(method.getModifiers());
		jcop.transformation.lookup.Lookup.lookupLayerClassDecl(decl).addMemberMethod(method);
	}
	
	public SourceCodeBuffer getAdvice() {
		return advice;
	}

	public void addMember(AspectMember intro) {
		getAdvice().add(intro.toString());
		
	}

	public void addImport(ImportDecl im) {
		imports.add(im);
		
	}
}
