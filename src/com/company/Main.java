package com.company;

import org.apache.commons.cli.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.util.LinkedList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		/*
		* User Input for the Document Path For example:
		* ./graph/small_graph.graphml
		* ./graph/medium_graph.graphml 
		* ./graph/large_graph.graphml
		*/


		Integer amountOfNodes, amountOfEdges;
		Character answer = null;
		Integer nodeForCalculation = 0;
		int firstNode = 0;
		int secondNode = 0;
		Document xmlDocument = null;
		List<NodeGraph> nodes = new LinkedList<NodeGraph>();
		List<EdgeGraph> edges = new LinkedList<EdgeGraph>();
		Options options = new Options();
		CommandLine cmd = null;
		boolean saveAll = true;
		Thread calculationThread = new Thread(new CalculatingOfNodes());
		Thread savingFileThread = new Thread(new SavingOfResults());

		Option input = new Option("s", "start", true, "Calculation for specific Nodes");
		input.setRequired(true);
		options.addOption(input);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();

		//Check if there is any Console Input
		if (args.length > 3) {
			try {
				cmd = parser.parse(options, args);
				firstNode = Integer.parseInt(cmd.getOptionValue("start"));
				secondNode = Integer.parseInt((args[3]));
			} catch (Exception e) {
				System.out.println(e.getMessage());
				formatter.printHelp("Wrong Input", options);
				System.exit(1);
			}
		}

		if (args.length > 0) {
			if((!ReadXml.fileSourceCheck(args[0]))){
				xmlDocument = ReadXml.ConvertToDocument(ReadXml.scanner()); //If there is any argument xml = the first argument;Else asking for Index
			} else xmlDocument = ReadXml.ConvertToDocument(args[0]); //Triggers if the users input is wrong
		}else xmlDocument = ReadXml.ConvertToDocument(ReadXml.scanner()); //Triggers when

		NodeList listOfNodes = xmlDocument.getElementsByTagName("node");
		NodeList listOfEdges = xmlDocument.getElementsByTagName("edge");
		amountOfNodes = listOfNodes.getLength();
		amountOfEdges = listOfEdges.getLength();

		initializeObjectEdges(listOfEdges, amountOfEdges, edges);
		initializeObjectNodes(amountOfNodes, nodes);


		//Run with start point
		if (args.length > 3 && !args[3].isEmpty()) {
			if (firstNode < amountOfNodes && secondNode < amountOfNodes) {
				CalculatingOfNodes.setData(nodes, edges, amountOfNodes, amountOfEdges, nodeForCalculation);
				SavingOfResults.setData(nodes, firstNode, secondNode, false);
				calculationThread.run();

				//Giving out that one thing
				System.out.println("The shortest path from Node " + nodes.get(firstNode).getNodeId() + " to Node " +
						nodes.get(secondNode).getNodeId() + " has the weight of: " + nodes.get(secondNode).getWeight() + "\n");

				answer = SavingOfResults.askForFileCreation();
				if (answer.equals('y') || answer.equals('Y')) savingFileThread.run();
			} else {
				System.out.println("Your entries do not correspond to the number of nodes in your File\nNumber of nodes "
						+ amountOfNodes + "\nNumber of edges " + amountOfEdges);
				System.exit(1);
			}
		} else {
			//Normal Run
			CalculatingOfNodes.setData(nodes, edges, amountOfNodes, amountOfEdges, nodeForCalculation);
			SavingOfResults.setData(nodes, amountOfNodes, nodeForCalculation, true);

			System.out.println("Number of nodes " + amountOfNodes + "\nNumber of edges " + amountOfEdges);
			answer = SavingOfResults.askForFileCreation();
			for(; nodeForCalculation < amountOfNodes; nodeForCalculation++) {
				initializeObjectEdges(listOfEdges, amountOfEdges, edges);
				initializeObjectNodes(amountOfNodes, nodes);

				SavingOfResults.setData(nodes, amountOfNodes, nodeForCalculation, true);
				CalculatingOfNodes.setData(nodes, edges, amountOfNodes, amountOfEdges, nodeForCalculation);

				calculationThread.run();

				giveOutSolution(nodes, amountOfNodes, nodeForCalculation);

				if (answer.equals('y') || answer.equals('Y')) savingFileThread.run();
			}
		}
		endOfProgram(answer);
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
	
	
	private static void giveOutSolution(List<NodeGraph> nodes, Integer amountOfNodes, Integer nodeForCalculation) {
		System.out.println("\nCalculation for Node: " + nodes.get(nodeForCalculation).getNodeId());

		for (int i = 0; i < amountOfNodes; i++) {
			System.out.println("The shortest path from Node " + nodes.get(nodeForCalculation).getNodeId() + " to Node " +
					nodes.get(i).getNodeId() + " has the weight of: " + nodes.get(i).getWeight());
		}
	}

	private static void endOfProgram(Character answer){
		try{
			ReadXml.scanner.close();
			if(answer.equals('y') || answer.equals('Y')) SavingOfResults.endOfWriting();
			System.exit(1);
		}catch(Exception e){
			System.out.println("An Error Occurred");
		}
	}
}