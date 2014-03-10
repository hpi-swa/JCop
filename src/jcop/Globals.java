/**
 * Copyright (c) 2009 Software Architecture Group
 *                    Hasso-Plattner-Institute, Potsdam, Germany
 *  
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package jcop;

import java.io.File;

public class Globals {
	
	public static final String lang = "jcop";
	public static final String fileExtension =  ".jcop";
	public static final String javaFileExtension =  ".java";
	
	public static final String jcopFolder = lang + File.separator + "lang";	
	public static final String jcopPackage = lang +".lang";
	public static final String version = "0.3";	
	public static final String jcopHome = "%JCOP_HOME%";
	public static final String jcopJar = "jcop.jar";
 
	public static String jcopFilesSrcFolder = "src";
	public static final String jcopFilesDestFolder = "jcop" +File.separator + "lang";
	public static final String contextjFilesFolder = "contextj_classes";
	
	public static final String url = "[http://hpi.uni-potsdam.de/swa]";
	 
	public interface GraphFiles {
		public  static final String JCOP_GRAMMAR = "JCopASTGrammar";
		public static final String aggFolder = "graph" + File.separator + "agg" + File.separator;
		public static final String fullAggFolder = "jcop" + File.separator + aggFolder;
		public static final String javaTypes = fullAggFolder + "miniJavaAstTypes.ggx";
		public static final String delMDSOC = fullAggFolder + "delMDSOC.ggx";
		public static final String jcop2delMDSOC = fullAggFolder + "jcop2delmdsoc_original.ggx";
		public static final String jcop2delMDSOC2 = fullAggFolder + "jcop2delmdsoc_2.ggx";
		public static final String jcopTypes = fullAggFolder + "jCopAstTypes.ggx";
	}
	
	public interface Files {
		public static final String aspectjRT = "aspectjrt.jar";
		public static final String jcopAspect = "JCopAspect.class";
	}
	
	static public interface Modifiers {
		public static final String PUBLIC = "public";
		public static final String FINAL = "final";
		public static final String STATIC = "static";		
		public static final String PROTECTED = "protected";
		public static final String PRIVATE = "private";
		public static final String BEFORE = "before";
		public static final String BEFORE_ANNOT = "Before";
		public static final String AFTER_ANNOT = "After";
		public static final String AFTER = "after";
		public static final String STATIC_ACTIVE = "staticactive";
	}
	
	public interface Keywords {
		public static final String aroundMethod = "";
	} 
	public static interface CompilerOps {
		public static final String destinationPath = "-d";
		public static final String runtimeLogging = "-rtl";
		public static final String dumpSources = "-sourcedump";
		public static final String xmlOutlinePath = "-xml-outline-path";
		public static final String verbose = "-verbose";
		public static final String compiletimeLogging = "-ctl";
		
		//public static final String rootDir = "-rootDir";
		public static final String sourcepath = "-sourcepath";
		public static final String classpath = "-classpath";
		public static final String noAspects = "-noaspects";
		public static final String aspectInfo = "-aspectinfo";
		public static final String xmlCILOutline = "-class-in-layer-outline";
		public static final String help = "-help";
		public static final String version = "-version";
		public static final String agg = "-agg";
		public static final String groove = "-groove";
		public static final String debug = "-log";
		public static final String layerpath = "-layerpath";
		public static final String inpath = "-inpath";
	}
	
	public static interface LoggingProperties {
		public static final String loggerName = "jcop";
		public static final boolean serviceEnabled = true;
	}	
	
	public static class Types {
		public static final String CONTEXT = "Context";
		public static final String JCOP = "JCop";
		public static final String CONTEXT_COMPOSITION = "ContextComposition";
		public static final String LAYER = "Layer";
		public static final String CONCRETE_LAYER = "ConcreteLayer";
		public static final String COMPOSITION = "Composition";
		public static final String LAYER_PROXY = "LayerProxy";
		public static final String PARTIAL_METHOD = "PartialMethod";
		public static final String GENERATED = "Generated";
		public static final String PARTIAL_FIELD = "PartialField";
		public static final String LAYER_PROVIDER = "LayerProvider";
		public static final String LAYERED = "Layered";
		public static final String LINKED_HASHTABLE = "LinkedHashtable";
		public static final String INTERNAL_CONTEXT = "InternalContext";
		public static final String CONTEXT_MANAGER = "ContextManager";
		public static final String LAYERED_STATE = "LayeredState";
		public static final String InvalidMethodAccessException = "InvalidMethodAccessException";		
		public static final String ASPECT = "JCopAspect";
		public static final String LayerFQN = jcopPackage + ".Layer";
		//public static final String PartialMethodFQN = jcopPackage + ".PartialMethod";
		//public static final String ConcreteLayerFQN = jcopPackage + ".ConcreteLayer";
		//public static final String CompositionFQN = jcopPackage + ".Composition";
		//public static final String LayerProviderFQN = jcopPackage + ".LayerProvider";		
		public static final String LAYER_LIST = "Layers";
		public static final String LAYERED_METHOD_ANNOTATION = "LayeredMethod";
		public static final String BASE_METHOD_ANNOTATION = "BaseMethod";
		public static final String PARTIAL_METHOD_ANNOTATION = "ParitalMethodA";
		public static final String DELEGATION_METHOD_ANNOTATION = "DelegationMethod";
		
	}
		
	public interface Msg {
		public static final String InvalidMethodAccess 		= "[Compiler] This method is only accessible within a layer.";
		public static final String WrongProceedModifier 		= "[Compiler] Wrong partial method modifier:";
		public static final String LayerDeclarationNotFound = "[Compiler] Cannot find top level layer declaration for layer ";
		public static final String compileAndWeaveAspect 	= "[Compiler] Compile and weave auxilliary aspect";
		public static  final String INVALID_METHOD_ACCESS = "[JCop] invalid method access.";
		public static final String LAYER_NOT_IMPORTED   = "[JCop] layer not imported.";
		
		
		public static final String aspectInfo 						= "[JCop Aspect Weaving]\n";
		
		public static final String compileErrors 					= "Program has not been sucessfully compiled";
		
		public static final String compiling 						= "[Compiler] Compiling:";
		public static final String fileNotExist 						= "[Compiler] File does not exist:";
		public static final String pmdHostNotFound				= "[Compiler] Cannot find host type of partial method %s";
		
		public static final String deleteFile 						= "[File Generation] Delete file:";
		public static final String deleteFolder 					= "[File Generation] Delete folder:";
		public static final String fileCreationFailed 				= "[File Generation] Cannot create file:";
		public static final String fileCreation 						= "[File Generation] Copy file:";
		
		public static final String aggGeneration 					= "[Graph Generation] Generate graph for";		
		public static final String aggFileLoadFail 				= "[AGG Graph Generation] cannot load file %s%n";
		public static final String grooveSaveError 				= "[Groove Graph Generation] cannot load file %s%n";
		public static final String launchError 						= "[JCop] cannot launch program";
		
		//runtime-logging messages
		public static final String logAccessBaseMethod 		= "accessing base method of ";
		public static final String logAccessLayeredMethod 	= "accessing layered method of ";
		public static final String logAccessPartialMethod 	= "accessing partial method ";
		
		
	}
	
	public interface ID {
		public static final String proceedVarname = lang + "__return__value";
		public static final String wrappedMethodPrefix = "__wrapped__";
		// no ___ for this variable since it is directly used as the thislayer keyword
		public static final String layerParameterName = "thislayer";
		public static final String targetParameterName = "__target__";
		public static final String layerProxyParameterName = "__proxy__";
		//public static final String compositionParam = "$composition$";
		public static final String composition = "__composition__";
		
		public static final String activeLayerList = "activeLayers";
		
		public static final String layerCompositionIdentifierPrefix = "jcop_old_composition_";		
		public static final String notifyExecution = "notifyExecution";
		public static final String generatedMethodNameDelimiter = "_";
		//public static final String implicitActivationMethodSignature = "isImplicitlyActive()";
		public static final String implicitActivationMethodName = "isImplicitlyActive";
		public static final String classInLayerFileName = "class-in-layer-view";
		public static final String partialMethodSignatures = "partialMethodSignatures";
		public static final String isActiveFor = "isActiveFor";
		public static final String signature = "signature";
		public static final String jcopNameMethod = "jcop_name";
		public static final String activationMethod = "activate";
		public static final String deactivationMethod = "deactivate";
		public static final String initLayersMethod = "initLayers";
		public static final String isActive = "isActive";
		public static final String current = "current";
		public static final String nextLayer = "next";
		public static final String implicitlyActivatedLayers = "getImplicitlyActivatedLayers";
		
		public static final String implicitActivationList = "getImplicitActivations";
		public static final String implicitDeactivationList = "getImplicitDeActivations";
		
		public static final String layersActivatedByContext = "getActivatedLayers";
	    public static final String layersDeactivatedByContext = "getDeactivatedLayers";
		
		public static final String firstLayer = "firstLayer";
		public static final String setComposition = "setComposition";
		public static final String oldComposition = "oldComposition";
		public static final String addLayer = "withLayer";
		public static final String removeLayer = "removeLayer";
		public static final String thislayer = "_thislayer";
		public static final String superlayer = "_superlayer_";
		public static final String setName = "setName";
		public static final String staticActiveLayers = "staticActiveLayers";
		public static final String allLayerClasses = "allLayerClasses";
		public static final String getLayerInstance = "getInstance";
		
	}
	
}
