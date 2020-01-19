import org.xml.sax.*;							//read xml
import org.w3c.dom.Document;
import org.w3c.dom.*;							//creating docs
import javax.xml.parsers.*;						//parses doc
import java.util.Scanner;						//scanner

public class ReadXml {

	public static String scanner() {
		
		Scanner scanner = new Scanner(System.in);
		String fileSource;
		boolean isValidSrc = false;

		System.out.println("Bitte geben Sie den Pfad ihrer datei ein: ");
		
		//Testing the Input before returning it
		//Avoiding NullPointerExceptions
		do {
			fileSource = scanner.nextLine();
			if(ReadXml.ConvertToDocument(fileSource) != null)isValidSrc = true;
			else System.out.println("\n<!--Der Eingegebene Pfad ist leider Falsch.-->\nBitte Geben Sie einen gueltigen Pfad ein (z.B. ..\\graph\\medium_graph.graphml): ");
		} while (isValidSrc == false);

		return fileSource;	
	}

	public static Document ConvertToDocument(String docString) {
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	//create doc factory
			
			DocumentBuilder builder = factory.newDocumentBuilder();					//create doc builder
			
			return builder.parse(new InputSource(docString));						//create doc and return
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());										//Errors
			
		}
		return null;
	}
	
	public static int target(NodeList listOfEdges, int i) {
		
		try {
			
				Node edgeNode =  listOfEdges.item(i);							
				
				Element edgeElement = (Element)edgeNode;						
			
				String target = edgeElement.getAttribute("target");
				
				target = target.replace("n", "");
				
				int targetId = Integer.parseInt(target);
			
				return targetId;
			
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return 0;
			
		}
	}
	
	public static int source(NodeList listOfEdges, int i) {
		
		try {
			
			Node edgeNode =  listOfEdges.item(i);							
			
			Element edgeElement = (Element)edgeNode;						
		
			String source = edgeElement.getAttribute("source");
			
			source = source.replace("n", "");
			
			int sourceId = Integer.parseInt(source);
		
			return sourceId;
		
		
		} catch (Exception e) {
		
		System.out.println(e.getMessage());
		return 0;
		
		}
	}
	
	public static int weight(NodeList listOfEdges, int i) {
		try {
			
			Node edgeNode =  listOfEdges.item(i);							
			
			Element edgeElement = (Element)edgeNode;						
		
			NodeList dataList = edgeElement.getElementsByTagName("data");	
			
			Element dataElement = (Element)dataList.item(1);				
			
			Element weightElement = (Element)dataElement.getChildNodes();
			
			String weightString = weightElement.getFirstChild().getNodeValue();
			
			int weight = Integer.parseInt(weightString);
		
			return weight;
		
		
		} catch (Exception e) {
		
		System.out.println(e.getMessage());
		return 0;
		
		}
	}
	
}
