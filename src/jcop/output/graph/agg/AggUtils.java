package jcop.output.graph.agg;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import jcop.Globals;
import jcop.Globals.GraphFiles;
import jcop.Globals.CompilerOps;
import jcop.Globals.Msg;
import jcop.compiler.CompilerConfiguration;
import AST.Program;
import agg.editor.impl.EdGraGra;
import agg.util.XMLHelper;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Rule;


public class AggUtils {
	
	private static AggUtils singleton = new AggUtils();
	private GraGra graphGrammar;
	private EdGraGra edGG;
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		log(new File(".").getAbsolutePath());
		singleton=new AggUtils();
		try {
			singleton.combineGrammarTypes();
			singleton.migrateRules(new File(GraphFiles.jcop2delMDSOC2), new File(GraphFiles.jcop2delMDSOC));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static AggUtils getInstance() {
		return singleton;
	}
	/**
	 * Adds the types of the delMDSOC grammar to the JCOP AST types grammar
	 * and saves the result to jcop2delmdsoc.ggx
	 * @throws Exception
	 */
	private  void combineGrammarTypes() throws Exception {
		EdGraGra edGG = loadGraphGrammar(new File(Globals.GraphFiles.javaTypes)); 
		GraGra gg = edGG.getBasisGraGra();
		
		GraGra delmdsocGG = new GraGra(false);
		delmdsocGG.load(GraphFiles.delMDSOC);
		
		gg.adoptTypes(delmdsocGG.getTypes());
		edGG.save(GraphFiles.jcop2delMDSOC);
		
		log("JCOP and delMDSOC node types combined");
	}
	
	/**
	 * Migrates the rules from one grammar to another
	 * @param source	get the rules from this file
	 * @param target	move the rules to this file
	 */
	private  void migrateRules(File source, File target) {
		EdGraGra edSourceGG = loadGraphGrammar(source);
		GraGra sourceGG = edSourceGG.getBasisGraGra();
		
		EdGraGra edTargetGG = loadGraphGrammar(target);
		GraGra targetGG = edTargetGG.getBasisGraGra();
		
		Enumeration<Rule> theRules = sourceGG.getRules();
		while(theRules.hasMoreElements()) {
			targetGG.addImportRule(theRules.nextElement());
		}
		edTargetGG.save();
		log("Rules migrated");
	}
	
	/**
	 * Loads a graph grammar file, including its layout data
	 * @param ggxFile	the graph grammar file
	 * @return			the graph grammar as an EdGraGra instance
	 */
	private  EdGraGra loadGraphGrammar(File ggxFile) {		
		XMLHelper h = new XMLHelper();
		h.read_from_xml(ggxFile.getAbsolutePath());		
		GraGra bgra = BaseFactory.theFactory().createGraGra();
		h.getTopObject(bgra);
		EdGraGra loadedGraGra = new EdGraGra(bgra);
		loadedGraGra.setDirName(new File(ggxFile.getAbsolutePath()).getParent());
		loadedGraGra.setFileName(ggxFile.getName());
		loadedGraGra.getTypeSet().setResourcesPath(loadedGraGra.getDirName());
		h.enrichObject(loadedGraGra);		
		return loadedGraGra;
	}
	
	public void save(String filename) {
		// move the file from <root>/jcop/graph/agg to: <root>
		edGG.save("..\\..\\..\\" + filename);
	}

	
	/**
	 * Print something to the console
	 */
	public static void log(String msg) {
		System.out.println("> " + msg);
	}
	
	public GraGra createGraGraGraph() {
		BaseFactory bf = BaseFactory.theFactory();
		graphGrammar = bf.createGraGra();
		graphGrammar.setName(GraphFiles.JCOP_GRAMMAR);
		loadTypeGraph();
		// Start graph
		Graph graph = graphGrammar.getGraph();
		graph.setName("JCopAST");
		return graphGrammar;
	}
	
	private void loadTypeGraph() {
		String fileName = createWorkingDir() + File.separator + GraphFiles.jcopTypes;
		try {
			File f = new File(fileName);			
			edGG = loadGraphGrammar(f);
			graphGrammar = edGG.getBasisGraGra();
		}
		catch (Exception e) {
			System.out.printf(Msg.aggFileLoadFail, fileName);
		}
	}
	
	private String createWorkingDir() {
		return CompilerConfiguration.getInstance().hasValueForOption(CompilerOps.sourcepath)
						? CompilerConfiguration.getInstance().getValueForOption(CompilerOps.sourcepath)
						:  System.getProperty("user.dir");
	}
}
