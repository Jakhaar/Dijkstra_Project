package com.company;

import org.apache.commons.cli.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		/*
		* User Input for the Document Path For example:
		* ./graph/small_graph.graphml
		* ./graph/medium_graph.graphml 
		* ./graph/large_graph.graphml
		*/
		Integer amountOfNodes, amountOfEdges;
		Integer nodeForCalculation = 0;
		Integer firstNode = 0;
		Integer secondNode = 0;
		Document xmlDocument = null;
		List<NodeGraph> nodes = new LinkedList<NodeGraph>();
		List<EdgeGraph> edges = new LinkedList<EdgeGraph>();
		Options options = new Options();
		CommandLine cmd = null;

		Option input = new Option("s", "startwith", true, "Calculation for specific Nodes");
		input.setRequired(true);
		options.addOption(input);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		if (args.length > 1) {
			try {
				cmd = parser.parse(options, args);
				firstNode = Integer.parseInt(cmd.getOptionValue("startwith"));
				secondNode = Integer.parseInt((args[3]));
				//FIXME: secondCode is not good coded
			} catch (Exception e) {
				System.out.println(e.getMessage());
				formatter.printHelp("Wrong Input", options);
				System.exit(1);
			}
		}

		if (args.length > 0) {
			xmlDocument = ReadXml.ConvertToDocument((ReadXml.fileSourceCheck(args[0]) ? args[0] : ReadXml.scanner())); //If there is any argument xml = the first argument;Else asking for Index
			// Prog. Start bei mind. einem argument
			NodeList listOfNodes = xmlDocument.getElementsByTagName("node");
			NodeList listOfEdges = xmlDocument.getElementsByTagName("edge");
			amountOfNodes = listOfNodes.getLength();
			amountOfEdges = listOfEdges.getLength();
			SavingOfResults.createFile("EndResult.txt");
			if (args.length > 3 && !args[3].isEmpty()){
				if(firstNode < amountOfNodes && secondNode < amountOfNodes){
					initializeObjectEdges(listOfEdges, amountOfEdges, edges);
					initializeObjectNodes(amountOfNodes, nodes);
					CalculatingOfNodes.dijkstra(nodes, edges, amountOfNodes, amountOfEdges, firstNode);//Just for one calculation
					SavingOfResults.savingResult(nodes, firstNode, secondNode);
				} else {
					System.out.println("Your entries do not correspond to the number of nodes in your File");
					System.exit(1);
				}

			} else{
				for(; nodeForCalculation < amountOfNodes; nodeForCalculation++){
					initializeObjectEdges(listOfEdges, amountOfEdges, edges);
					initializeObjectNodes(amountOfNodes, nodes);
					CalculatingOfNodes.dijkstra(nodes, edges, amountOfNodes, amountOfEdges, nodeForCalculation);
					SavingOfResults.savingResults(nodes, amountOfNodes, nodeForCalculation);
				}
			}
		System.out.println("<!--File was successfully created and saved as EndResult.txt-->");
	} else {
		//Normaler Prog. ablauf
		System.out.print("Please enter the file name or the path of your file: ");
		xmlDocument = ReadXml.ConvertToDocument(ReadXml.scanner()); //If there is any argument xml = the first argument;Else asking for Index
		NodeList listOfNodes = xmlDocument.getElementsByTagName("node");
		NodeList listOfEdges = xmlDocument.getElementsByTagName("edge");		
		amountOfNodes = listOfNodes.getLength();
		amountOfEdges = listOfEdges.getLength();
		System.out.println("Number of nodes " + amountOfNodes + "\nNumber of edges " + amountOfEdges);
		initializeObjectEdges(listOfEdges, amountOfEdges, edges);
		initializeObjectNodes(amountOfNodes, nodes);
		nodeForCalculation = askForNode(amountOfNodes);
		CalculatingOfNodes.dijkstra(nodes, edges, amountOfNodes, amountOfEdges, nodeForCalculation);
		giveOutSoltion(nodes, amountOfNodes, nodeForCalculation);					
	}

	}
	
	private static void initializeObjectEdges(NodeList listOfEdges, Integer amountOfNodes, List<EdgeGraph>edge) {
		edge.clear();
		
		for (int i = 0; i < amountOfNodes; i++) {					
			edge.add(new EdgeGraph(ReadXml.source(listOfEdges, i), ReadXml.target(listOfEdges, i), ReadXml.weight(listOfEdges, i)));
		}
	}

	
	private static void initializeObjectNodes(Integer amountOfNodes, List<NodeGraph>nodes) {
		nodes.clear();

		for (int i = 0; i < amountOfNodes; i++) {
			nodes.add(new NodeGraph(i));
		}
	}
	
	
	private static void giveOutSoltion(List<NodeGraph> nodes, Integer amountOfNodes, Integer nodeForCalculation) {
		for (int i = 0; i < amountOfNodes; i++) {
			System.out.println("The shortest path from Node " + nodes.get(nodeForCalculation).getNodeId() + " to Node " + 
			nodes.get(i).getNodeId() + " has the weight of: " + nodes.get(i).getWeight());
		}
	}
	
	public static Integer askForNode(Integer amountOfNodes) {
		Scanner sc = new Scanner(System.in);
		int startingNode;
		
		System.out.print("Please enter the Start Node: ");
		if (sc.hasNextLine()){
			do {
				startingNode = sc.nextInt();
				if (startingNode < 0 || startingNode > (amountOfNodes-1))
					System.out.print("\n<!--Die Eingegebene Zahl ist leider nicht gueltig.-->\nBitte Geben Sie eine Zahl zwischen 0-" + (amountOfNodes-1) + " ein: ");
				else break;	
			} while (true);
		}
		else startingNode = 1;
		sc.close();
		return startingNode;
		
	}

}