package com.company;

import org.xml.sax.*;							//read xml
import org.w3c.dom.Document;
import org.w3c.dom.*;							//creating docs
import javax.xml.parsers.*;						//parses doc
import java.util.Scanner;						//scanner

public class ReadXml {
	//Global Scanner
	final static Scanner scanner = new Scanner(System.in);


	public static String scanner() {
		String fileSource;

		boolean isValidSrc;
		
		//Testing the Input
		//Avoiding NullPointerExceptions
		System.out.print("Please enter the file name or the path of your file: ");

		do {

			fileSource = scanner.nextLine();

			isValidSrc = fileSourceCheck(fileSource);

		} while (!isValidSrc);

		return fileSource;	
	}

	protected static Boolean fileSourceCheck(String fileSource){

		if(ReadXml.ConvertToDocument(fileSource) != null) return true;

		else System.out.print("\n<!--The entered path or file name is wrong.-->\nPlease enter correct your input (z.B. ..\\graph\\medium_graph.graphml or medium_graph.graphml): ");

		return false;
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
		int targetId;

		try {
			
				Node edgeNode =  listOfEdges.item(i);							
				
				Element edgeElement = (Element)edgeNode;						
			
				String target = edgeElement.getAttribute("target");
				
				target = target.replace("n", "");
				
				targetId = Integer.parseInt(target);
			
				return targetId;
			
			
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return 0;
			
		}
	}
	
	public static int source(NodeList listOfEdges, int i) {
		int sourceId;

		try {
			
			Node edgeNode =  listOfEdges.item(i);							
			
			Element edgeElement = (Element)edgeNode;						
		
			String source = edgeElement.getAttribute("source");
			
			source = source.replace("n", "");
			
			sourceId = Integer.parseInt(source);
		
			return sourceId;
		
		
		} catch (Exception e) {
		
		System.out.println(e.getMessage());
		return 0;
		
		}
	}
	
	public static int weight(NodeList listOfEdges, int i) {
		int weight;

		try {
			
			Node edgeNode =  listOfEdges.item(i);							
			
			Element edgeElement = (Element)edgeNode;						
		
			NodeList dataList = edgeElement.getElementsByTagName("data");	
			
			Element dataElement = (Element)dataList.item(1);				
			
			Element weightElement = (Element)dataElement.getChildNodes();
			
			String weightString = weightElement.getFirstChild().getNodeValue();
			
			weight = Integer.parseInt(weightString);
		
			return weight;
		
		
		} catch (Exception e) {
		
		System.out.println(e.getMessage());
		return 0;
		
		}
	}
	
}
