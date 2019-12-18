import org.xml.sax.*;							//read xml
import org.w3c.dom.*;							//creating docs
import javax.xml.parsers.*;						//parses doc
import java.util.Scanner;						//scanner

public class ReadXml {

	public static String scan() {
		
		Scanner fileScanner = new Scanner(System.in);
		String docName = fileScanner.nextLine();
		fileScanner.close();
		return docName;
		
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
