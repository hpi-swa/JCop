package jcop.output.graph.groove;

import java.io.File;
import java.util.regex.Matcher;

import jcop.Globals.CompilerOps;
import jcop.compiler.CompilerConfiguration;
import jcop.compiler.filecopy.AuxiliaryCompilerFilesHandler;
import jcop.output.graph.GraphGenerator;
import jcop.output.graph.INode;
import jcop.output.graph.groove.xml.XMLBuilder;

import org.w3c.dom.Element;

import AST.Program;

public class GrooveGraphGenerator extends GraphGenerator {
	//private XMLStreamWriter xsw;
	private XMLBuilder xsw;
	private int idCounter;
	private String grooveFileEnding = ".gst";
	private static GrooveGraphGenerator singleton;
	
	public static GrooveGraphGenerator getInstance() {		
		if (singleton == null)
			singleton = new GrooveGraphGenerator();
		return singleton;
	}
	
	private GrooveGraphGenerator() {
	
			//xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
			xsw = new XMLBuilder();			
		
	
	    
//	    xsw.writeAttribute("Name", "Value");
//	    xsw.writeEmptyElement("Child");
//	    xsw.writeEndElement();
//	    xsw.writeEndDocument();
//	    xsw.close();
	
	}
	
	
	private String getUniqeNodeId() {
		return "n" + ++idCounter;
	}
	
	


	@Override
	public void addAttribute(INode node, String key, Object value) {
		INode valueNode = createNode();
		createEdge(node, key, valueNode);		
		createEdge(valueNode , createAttributeString("string", value), valueNode );		
	}
	
	private String createAttributeString(String key, Object value) {
		return String.format("%s:%s", key, quote(value));		
	}

	private String quote(Object val) {
		String quote = "\"";
		String valstr = val.toString();
		if(valstr.length() > quote.length())
			valstr = valstr.replaceAll("\"", Matcher.quoteReplacement("\\\""));
		return quote + valstr + quote;
	}

	private INode createNode() {
		String nodeID = getUniqeNodeId();		
		GrooveNode node = new GrooveNode(nodeID);
		xsw.createElement("node", "id", nodeID);
		return node;
	}

	
	@Override
	public INode createNode(String typeName) {
		INode node = createNode();
		createEdge(node, "type:" + typeName, node);		
		return node;
	}

	@Override
	public void createEdge(INode src, String arc, INode tar) {	;
		Element edge = xsw.createElement("edge");		
		edge.setAttribute("from", src.getName());
		edge.setAttribute("to", tar.getName());		
		Element attr = xsw.createElement("attr", edge);			
		attr.setAttribute("name", "label");
		
		Element string = xsw.createElement("string", attr);			
		string.setTextContent(arc);			
	}

	public void save() {		
		String file = CompilerConfiguration.getInstance().getValueForOption(CompilerOps.groove);
		xsw.save(AuxiliaryCompilerFilesHandler.getInstance().getWorkingDir() + File.separator + file);		
	}
	
}
