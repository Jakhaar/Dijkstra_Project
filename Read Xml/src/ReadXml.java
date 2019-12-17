import org.xml.sax.*;							//read xml
import org.w3c.dom.*;							//creating docs
import javax.xml.parsers.*;
import java.util.Scanner;

public class ReadXml {

	public static void Read() {
		
		Scanner fileScanner = new Scanner(System.in);
		
		String docName = fileScanner.nextLine();		//./src/small_graph.graphml, ./src/medium_graph.graphml, ./src/large_graph.graphml
	
		Document xmlDocument = ConvertToDocument(docName);	//create doc
		
		NodeList listOfNodes = xmlDocument.getElementsByTagName("node");	//list of nodes
		
		System.out.println("Number of nodes " + listOfNodes.getLength());	
		
		
		
		NodeList listOfEdges = xmlDocument.getElementsByTagName("edge");	//list of edges
		
		System.out.println("Number of edges " + listOfEdges.getLength());
		
		
		connectedNodes(listOfEdges, "target", "source", "data", "key");		//main function
		
	}

	private static Document ConvertToDocument(String docString) {
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	//create doc factory
			
			DocumentBuilder builder = factory.newDocumentBuilder();					//create doc builder
			
			return builder.parse(new InputSource(docString));						//create doc and return
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());										//Errors
			
		}
		return null;
	}
		
	private static void connectedNodes(NodeList listOfEdges, String attrTarget, String attrSource,
			String elemData, String attrKey) {
		
		try {
			
			for (int i = 0; i < listOfEdges.getLength(); i++) {
				
				Node edgeNode = listOfEdges.item(i);							//first to last edge
				
				Element edgeElement = (Element)edgeNode;						//element edge with all objects
			
				
				NodeList dataList = edgeElement.getElementsByTagName(elemData);	//all data elements
				
				Element dataElement = (Element)dataList.item(1);				//second data Element 
				
				Element weightElement = (Element)dataElement.getChildNodes();	//element weight
				
				System.out.println("Edge " + i + " connects " + 
						edgeElement.getAttribute(attrTarget) + " with " +
						edgeElement.getAttribute(attrSource) + " with a weight of: " +
						weightElement.getFirstChild().getNodeValue());
			
				
			}
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			
		}
	}

}
