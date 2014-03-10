/**
 * 
 */
package jcop.generation.jcopaspect;

import AST.LayerDeclaration;
import AST.MethodDecl;

public class PartialMethodAdviceGenerator extends AdviceGenerator {

		public PartialMethodAdviceGenerator(LayerDeclaration decl, MethodDecl  partialMethod) {
			super(JCopAspect.getInstance().getAdvice());
//			this.layerDecl= decl;
//			this.partialMethod = partialMethod;
		}		

		public Introduction genIntroductionFor(MethodDecl method) {			
			Introduction intro = new Introduction(
					method.getModifiers(),
					method.getTypeAccess(),
					method.hostType().getFullQualifiedName() + "." + method.name(),
					method.getParameterList(),
					method.getExceptionList(),
					method.getBlock());			
			return intro;			
		}

		public AroundAdvice genAroundAdviceFor(MethodDecl method) {
			 AroundAdvice advice = new AroundAdvice(
					method.getTypeAccess(),
					method.hostType().createQualifiedAccess(),
			 		method.getFullQualifiedName(),
			 		method.getParameterList(),
			 		method.getExceptionList(),
			 		method.getBlock(),
			 		"! cflow(execution(* jcop.lang.Layer.*(..)))");			 
			return advice;
		}
	}





//public void addPartialMethod() {
//	CharSequence[][] patternValues = createPatternValuesFor(partialMethod);
//
//    MethodDecl baseMethod = Lookup.lookupMethodCorrespondingTo(partialMethod);
//
//    VisitedNodes.secondVisit(baseMethod);
//
//    getAdvice().instantiatePatternWith(patternValues);						
//	addAdviceForPartialMethod(partialMethod.isStatic());			
//	addInterTypeDeclarationForHostClass(partialMethod, patternValues);			
//	MethodDecl baseMethod = layerDecl.lookupMethodCorrespondingTo(partialMethod);
//	
//	if (VisitedNodes.firstVisit(baseMethod)) {			
//	   PartialMethodTransformer t  = new PartialMethodTransformer(layerDecl, partialMethod);
//		t.createBaseMethodWrapper();				
//		createDelegationMethods(partialMethod);				
//		addLayeredMethodDeclaration();
//	}			
//	addInterTypeDeclatationForInstanceLayerClass(partialMethod);
//	getAdvice().instantiatePatternWith(patternValues);			
//}

//private void createDelegationMethods(PartialMethodDecl partialMethod) {
//	SourceCodeBuffer interTypeDeclarationBuffer = createBuffer(partialMethod);
//	addInterTypeDeclatationForLayerClass(partialMethod, interTypeDeclarationBuffer);				
//	addInterTypeDeclatationForConcreteLayerClass(partialMethod, interTypeDeclarationBuffer);
//	String[][] patternValues = getPatternValuesFor(partialMethod);
//	interTypeDeclarationBuffer.instantiatePatternWith(patternValues);
//}

//private SourceCodeBuffer createBuffer(PartialMethodDecl partialMethod) {
//	String signature = partialMethod.getFullQualifiedName();
//	if (!interTypeDeclarationBuffers.containsKey(signature)) 
//		interTypeDeclarationBuffers.put(signature, new SourceCodeBuffer());
//	return interTypeDeclarationBuffers.get(signature);		
//}
//
//public static SourceCodeBuffer getInterTypeDeclarationsFor(String signature) {
//	return interTypeDeclarationBuffers.get(signature);
//}

//private CharSequence[][] createPatternValuesFor(MethodDecl method)		  {
//    String namePattern = method.hostType().getFullQualifiedName() + "." + method.getID();
//
//    List params = method.getParameterList();
//    StringBuffer paramsAsString = genFqList(params);
//
//    List args = new Generator().generateArgs(params);
//    String argsAsString = args.toString();
//    boolean hasArgs = argsAsString.length() > 0;
//    boolean hasParams = paramsAsString.length() > 0;
//    
//	advice.putLine("$composition$ $compVar$ =  $composition$.current();");			
//	advice.putLine("$returnKeyword$ $compVar$.firstLayer().$delegationMethodName$($argsWithTarget$);");
//	
//	CharSequence[][] values = { 
//		      { "compDef", "$composition$ $compVar$" }, 
//		      { "hostType", method.hostType().getFullQualifiedName() }, 
//		      { "namePattern", namePattern }, 
//		      { "targetDef", "$hostType$ $targetVar$" }, 
//		      { "targetObject", method.isStatic() ? "$hostType$" : "$targetVar$" }, 
//		      { "rootMethod", namePattern.substring(namePattern.lastIndexOf(".") + 1) }, 
//		      { "wrappedRootMethod", "$rootMethod$" }, 
//		      { "delegationMethodName", namePattern.replaceAll("\\.", "_") }, 
//		      { "layerIdentifier", Lookup.lookupLayerClassDecl(this.layerDecl).getFullQualifiedName() }, 
//		      { "returnType", method.getTypeAccess().type().getFullQualifiedName() }, 
//		      { "returnKeyword", method.isVoid() ? "" : "return" }, 
//		      { "params", paramsAsString }, 
//		      { "compositionParams", hasParams ? "$compDef$, $params$" : "$compDef$" }, 
//		      { "adviceTarget", method.isStatic() ? "" : "$targetDef$" }, 
//		      { "targetDefComma", method.isStatic() ? "" : "," }, 
//		      { "maybeStatic", method.isStatic() ? "static " : "" }, 
//		      { "adviceParams", hasParams ? "$adviceTarget$ $targetDefComma$ $params$" : "$adviceTarget$" }, 
//		      { "paramsWithTarget", "$targetDef$, $compositionParams$" }, 
//		      { "paramsWithLayer", "$layerIdentifier$ _layer, $compositionParams$" }, 
//		      { "paramTypes", transformParamsToTypes(params) }, 
//		      { "args", argsAsString }, 
//		      { "thisArg", method.isStatic() ? "null" : "this" }, 
//		      { "argsWithComp", hasArgs ? "$compVar$, $args$" : "$compVar$" }, 
//		      { "argsWithTarget", method.isStatic() ? "null, $argsWithComp$" : "$targetVar$,  $argsWithComp$" }, 
//		      { "modifier", filterAdaptationModifiers(method.getModifiers()) }, 
//		      { "dispatchMethodCall", "$namePattern$_dispatch($paramTypes$)" }, 
//		      { "signature", "$returnType$ $namePattern$($paramTypes$)" }, 
//		      { "dispatchMethodSignature", "$returnType$ $dispatchMethodCall$" }, 
//		      { "methodBlock", method.getBlock().toString() }, 
//		      { "methodName", method.getID() } };
//
//		    return values;
//}

//private StringBuffer genFqList(List<ParameterDeclaration> params)
//  {
//    StringBuffer b = new StringBuffer();
//    for (int i = 0; i < params.getNumChild(); i++) {
//      ParameterDeclaration p = (ParameterDeclaration)params.getChild(i);
//      if (i > 0) b.append(", ");
//      b.append(p.type().getFullQualifiedName());
//      b.append(" ");
//      b.append(p.getID());
//    }
//    return b;
//  }
//public void createBaseMethodDelegate(MethodDecl baseMethod) {		
//	MethodDecl baseMethodDelegate = 
//		new BaseMethodGenerator(baseMethod).generateWrapper();
//	baseMethod.hostType().addMemberMethod(baseMethodDelegate);
//}



//private String[][] getPatternValuesFor(PartialMethodDecl method) {
//	String namePattern = method.getNamePattern().toString();
//	List<ParameterDeclaration> params = method.getParameterList();
//	String paramsAsString = params.toString();
//	
//	List<Expr> args = new Generator().generateArgs(params);
//	String argsAsString = args.toString();
//	boolean hasArgs = argsAsString.length() > 0;
//	boolean hasParams = paramsAsString.length() > 0;
//	
//	String[][] values = {						
//			{ "compDef", 							"$composition$ $compVar$"},					
//			{ "hostType", 							namePattern.substring(0, namePattern.lastIndexOf(".")) },
//			{ "namePattern", 					namePattern },					
//			{ "targetDef", 						"$hostType$ $targetVar$"},
//			// type access for static method, _targed otherwise
//			{ "targetObject", 					method.isStatic() ? "$hostType$" :  "$targetVar$"},
//			{ "rootMethod", 					namePattern.substring(namePattern.lastIndexOf(".") + 1) }, 
//			{ "wrappedRootMethod", 		ID.wrappedMethodPrefix + "$hostType$" +"$$$"+  "$rootMethod$" },
//			{ "delegationMethodName", 	namePattern.replaceAll("\\.", "_")  }, 
//			{ "layerIdentifier", 					layerDecl.getFullQualifiedName() },
//			{ "returnType", 						method.getTypeAccess().type().getFullQualifiedName() },
//			{ "adviceReturnValue", 			method.isVoid() ? "void" : "$returnType$" },
//			{ "returnKeyword", 					method.isVoid() ? "" : "return" },
//			{ "params", 							paramsAsString  },
//			{ "compositionParams", 			hasParams ? "$compDef$, $params$" : "$compDef$" },
//			{ "adviceTarget", 					method.isStatic() ? "" : "$targetDef$" },
//			{ "targetDefComma", 				method.isStatic() ? "" : "," },					
//			{ "adviceParams", 					hasParams ?  "$adviceTarget$ $targetDefComma$ $params$" : "$adviceTarget$"},
//			{ "paramsWithTarget", 			/*method.isStatic()?  "$compositionParams$" : */"$targetDef$, $compositionParams$" },
//			{ "paramsWithLayer", 				"$layerIdentifier$ _layer, $compositionParams$" }, 
//			{ "paramTypes", 					transformParamsToTypes(params) },
//			{ "args", 								argsAsString },
//			{ "argsWithComp",  				hasArgs ? "$compVar$, $args$" : "$compVar$" },
//			{ "argsWithTarget",  				method.isStatic() ? "null, $argsWithComp$" : "$targetVar$,  $argsWithComp$" },
//			{ "argsWithThis",					"this, $argsWithComp$" },
//			{ "signature", 							filterAdaptationModifiers(method.getModifiers()) + " $returnType$ $namePattern$($paramTypes$)" },
//			{ "methodBlock", 					method.getBlock().toString() }
//
//	};
//	return values;
//}
		
//private String filterAdaptationModifiers(Modifiers modifiers) {			
//	StringBuffer b = new StringBuffer();
//	for (Modifier m : modifiers.getModifierList()) {
//		String id = m.getID();
//		if (!id.equals(BEFORE) && !id.equals(AFTER))
//			b.append(id).append(' ');					
//	}	
//	return b.toString().trim();
//}
//
//private String transformParamsToTypes(List<ParameterDeclaration> params){			
//	StringBuffer b = new StringBuffer();		
//	for (ParameterDeclaration parameterDeclaration : params) {
//		  if (b.length() > 0) b.append(", ");
//		  b.append(parameterDeclaration.type().getFullQualifiedName());			
//    }		
//	return b.toString();		 
//}

//private void addLayeredMethodDeclaration() {
//	SourceCodeBuffer advice = getAdvice();
//	advice.putLine("declare @method: $signature$ : @jcop.lang.LayeredMethod();");
//}
//
//private void addInterTypeDeclarationForHostClass(PartialMethodDecl partialMethod, String[][] patternValues) {
//	SourceCodeBuffer advice = getAdvice();
//	advice.putGeneratedReference("PartialMethodAdviceGenerator.addInterTypeDeclarationForHostClass()");
//	if (partialMethod.isStatic())
//		advice.putLine("static ");
//	advice.putLineOpenBracket("public $returnType$ $namePattern$($paramsWithLayer$)");
//	//advice.indent++;
//	String proceedExpr = "$currentComposition$.next(_layer).$delegationMethodName$($argsWithThis$);";
//	
//	if (partialMethod.getModifiers().contains(BEFORE)) {
//		advice.putLineOpenBracket("try");			
//		advice.putLine("$methodBlock$");
//		advice.closeBracket();
//		advice.putLineOpenBracket("finally");
//		advice.putLine("$returnKeyword$", proceedExpr);
//		advice.closeBracket();
//	}
//	else if (partialMethod.getModifiers().contains(AFTER)) {
//		
//		advice.putLineOpenBracket("try");
//		if (!partialMethod.isVoid())
//			advice.putLine("$returnType$ returnValueOfProceed = ");
//		advice.putLine(proceedExpr);
//		advice.closeBracket();
//		advice.putLineOpenBracket("finally");
//		advice.putLine("$methodBlock$");
//		if (!partialMethod.isVoid())
//			advice.putLine("$returnKeyword$ returnValueOfProceed;");
//		advice.closeBracket();
//		
//	}
//	else
//		advice.putLine("$methodBlock$");
//	advice.closeBracket();	
//	getAdvice().instantiatePatternWith(patternValues);
////	advice.indent--;
//}
//
//private  void addInterTypeDeclatationForLayerClass(PartialMethodDecl partialMethod, SourceCodeBuffer buffer) {
////	RootLayerClassGenerator layerGen = new RootLayerClassGenerator(layerDecl);
////	String namePattern = partialMethod.getNamePattern().toString();
////	layerGen.genDelegationMethod(partialMethod, namePattern);
//	
//	//LayerClassGenerator gen = new LayerClassGenerator(layerDecl);			
//	SourceCodeBuffer advice = buffer;
//	advice.putGeneratedReference("PartialMethodAdviceGenerator..addInterTypeDeclatationForLayerClass()");
//	advice.putLineOpenBracket("public $returnType$ $layer$.$delegationMethodName$($paramsWithTarget$)");			
//	advice.putLine("$returnKeyword$ $targetObject$.$wrappedRootMethod$($args$);");
//	advice.closeBracket();		 	
//}
//
//private void addInterTypeDeclatationForConcreteLayerClass(PartialMethodDecl partialMethod, SourceCodeBuffer buffer) {
////	ConcreteLayerClassGenerator layerGen = new ConcreteLayerClassGenerator(layerDecl);
////	String namePattern = partialMethod.getNamePattern().toString();
////	layerGen.genDelegationMethod(partialMethod, namePattern); 
//	SourceCodeBuffer advice = buffer;			
//	advice.putGeneratedReference(".PartialMethodAdviceGenerator.addInterTypeDeclatationForConcreteLayerClass()");
//	advice.putLineOpenBracket("public $returnType$ $concreteLayer$.$delegationMethodName$($paramsWithTarget$)");			
//	advice.putLine("$returnKeyword$ $compVar$.next(this).$delegationMethodName$($argsWithTarget$);");			
//	advice.closeBracket();			
//}
//
//private void addInterTypeDeclatationForInstanceLayerClass(PartialMethodDecl partialMethod) {
////	InstanceLayerClassGenerator layerGen = new InstanceLayerClassGenerator(layerDecl);
////	String namePattern = partialMethod.getNamePattern().toString();
////	layerGen.genDelegationMethod(partialMethod, namePattern);			
//	SourceCodeBuffer advice = getAdvice();
//	advice.putGeneratedReference("PartialMethodAdviceGenerator.addInterTypeDeclatationForInstanceLayerClass()");
//	advice.putLineOpenBracket("public $returnType$ $layerIdentifier$.$delegationMethodName$($paramsWithTarget$)");			
//	advice.putLine("$returnKeyword$ $targetObject$.$rootMethod$($argsWithThis$);");			 
//	advice.closeBracket();
//}