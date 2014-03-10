package jcop.output.graph.groove.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jcop.Globals;
import jcop.compiler.CompilerMessageStream;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class XMLBuilder {
	private final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
	private final String xmlns = "http://www.gupro.de/GXL/gxl-1.0.dtd";
	
	private StringBuffer buffer;	
	private Document doc;
	private Element root;

	public XMLBuilder() {
		try {
			buffer = new StringBuffer();
			buffer.append(header);	
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			doc = documentBuilder.newDocument();
			Element header = createHeader();
			root = createRootElement(header);						
		}
		catch (ParserConfigurationException e) {			
			e.printStackTrace();
		}		
	}	
	
    private Element createHeader() {
    	Element rootElement = doc.createElement("gxl");
    	rootElement.setAttribute("xmlns", xmlns);
    	doc.appendChild(rootElement);
    	return rootElement;
	}


	private Element createRootElement(Element header) {
		Element graph = createElement("graph", header);    	
    	graph.setAttribute("edgemode", "directed");
		graph.setAttribute("edgeids", "false");
		graph.setAttribute("role", "graph");
		graph.setAttribute("id", "start");		
		
		Element attr = createElement("attr", graph);
		attr.setAttribute("name", "$version");		
		
		Element string = createElement("string", attr);
		string.setTextContent("curly");
		return graph;	    	
    }
    
    public Element createElement(String tagName) {
    	Element newElement = doc.createElement(tagName);    	
    	root.appendChild(newElement);    	
		return newElement;
	}
    
    public Element createElement(String tagName, Element parent) {    	
    	Element newElement = doc.createElement(tagName);
    	parent.appendChild(newElement);
		return newElement;
	}
    
    public void createElement(String tagName, String key, String value) {    	
		Element e = createElement(tagName);
		e.setAttribute(key, value);		
	}
	
    


	public void save(String file) {	
			try {				
				System.out.println(new File(file).getAbsolutePath());
				FileWriter f = new FileWriter(new File(file));
				f.write(prettyPrintWithDOM3LS());
				f.flush();
				f.close();
			}
			catch (IOException e) {				
				CompilerMessageStream.getInstance().log(Globals.Msg.grooveSaveError, file);
			}	
	}

	private String prettyPrintWithDOM3LS() {
		// Pretty-prints a DOM document to XML using DOM Load and Save's
		// LSSerializer.
		// Note that the "format-pretty-print" DOM configuration parameter can
		// only be set in JDK 1.6+.
		DOMImplementation domImplementation = doc.getImplementation();
		if (domImplementation.hasFeature("LS", "3.0") && domImplementation.hasFeature("Core", "2.0")) {
			DOMImplementationLS domImplementationLS = (DOMImplementationLS) domImplementation.getFeature("LS", "3.0");
			LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
			DOMConfiguration domConfiguration = lsSerializer.getDomConfig();
			if (domConfiguration.canSetParameter("format-pretty-print", Boolean.TRUE)) {
				lsSerializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
				LSOutput lsOutput = domImplementationLS.createLSOutput();
				lsOutput.setEncoding("UTF-8");
				StringWriter stringWriter = new StringWriter();
				lsOutput.setCharacterStream(stringWriter);
				lsSerializer.write(doc, lsOutput);
				return stringWriter.toString();
			}
			else {
				CompilerMessageStream.getInstance().log("DOMConfiguration 'format-pretty-print' parameter isn't settable.");
			}
		}
		else {
			CompilerMessageStream.getInstance().log("DOM 3.0 LS and/or DOM 2.0 Core not supported.");
		}
		return "";
	}

	
//	
//	public void createHeader() {		  
//	      //	<gxl xmlns="http://www.gupro.de/GXL/gxl-1.0.dtd">
//		  Element gxl = new Element("gxl");
//		  gxl.addAttribute("xmlns", "http://www.gupro.de/GXL/gxl-1.0.dtd");
// 		  //    <graph edgemode="directed" edgeids="false" role="graph" id="start">
//		  Element graph = new Element("graph");
//		  graph.addAttribute("edgemode", "directed");
//		  graph.addAttribute("edgeids", "false");
//		  graph.addAttribute("role", "graph");
//		  graph.addAttribute("id", "start");
//		  // <attr name="$version">
//		  // <string>curly</string>
//		  // </attr>
//		  Element attr = new Element(graph, "attr");
//		  attr.addAttribute("name", "$version");
//		  attr.addElement(new Element("string", "curly"));
//		  
//	
//	}
	
//	class Element{
//		private String name;
//		private List<Attribute> attr;
//		private ArrayList<Element> children;
//		private String text;
//		
//		Element(String name) {
//			this.name = name;
//			this.attr = new ArrayList<Attribute>();
//			this.children = new ArrayList<Element>();
//		}			
//		
//		public Element(String name, String text) {
//			this(name);
//			addText(text);
//		}
//
//		public Element(Element parent, String string) {
//			this(string);
//			parent.addElement(this);
//		}
//
//		private void addText(String text) {
//			this.text = text;
//		}
//
//		void addElement(Element child) {
//			this.children.add(child);
//		}
//		
//		void addAttribute(String key, String value) {
//			this.attr.add(new Attribute(key, value));
//		}
//	}
//	
//	class Attribute {
//		private String key;
//		private String vaue;
//
//		public Attribute(String key, String value) {
//			this.key = key;
//			this.vaue = value;
//		}
//	}

}
