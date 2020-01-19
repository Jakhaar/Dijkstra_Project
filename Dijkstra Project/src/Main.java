import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		/* User Input for the Document Path 
		* For example: 
		* ./graph/small_graph.graphml
		* ./graph/medium_graph.graphml 
		* ./graph/large_graph.graphml
		*/
		int startNode, amountOfNodes, amountOfEdges;
		Document xmlDocument = ReadXml.ConvertToDocument(ReadXml.scanner());
		
		NodeList listOfNodes = xmlDocument.getElementsByTagName("node");	//list of nodes
		NodeList listOfEdges = xmlDocument.getElementsByTagName("edge");	//list of edges		
		
		amountOfNodes = listOfNodes.getLength();
		amountOfEdges = listOfEdges.getLength();
		
		System.out.println("Number of nodes " + amountOfNodes + "\nNumber of edges " + amountOfEdges);
			
		EdgeGraph edge[] = initializeObjectEdges(listOfEdges, amountOfEdges);
		NodeGraph nodes[] = initializeObjectNodes(amountOfNodes);

		startNode = askStartNode(amountOfNodes);

		nodes[startNode].setWeight(0);
		nodes[startNode].setPreNode(null);
		nodes[startNode].setReachable(true);			
		dijkstra(nodes, edge, amountOfNodes, amountOfEdges);
		giveOutSoltion(nodes, amountOfNodes, startNode);		
	}
	
	private static EdgeGraph[] initializeObjectEdges(NodeList listOfEdges, int numbOfEdges) {
		
		EdgeGraph edge[] = new EdgeGraph[numbOfEdges];
		
		for (int i = 0; i < numbOfEdges; i++) {					
			edge[i] = new EdgeGraph(ReadXml.source(listOfEdges, i), ReadXml.target(listOfEdges, i), ReadXml.weight(listOfEdges, i));
		
		}
		return edge;
	}
	
	private static NodeGraph[] initializeObjectNodes(int numbOfNodes) {
		
		NodeGraph nodes[] = new NodeGraph[numbOfNodes];
		for (int i = 0; i < numbOfNodes; i++) {
			nodes[i] = new NodeGraph(i);
		}
		return nodes;
	}
	
	private static void dijkstra(NodeGraph nodes[], EdgeGraph edge[], int numbOfNodes, int numbOfEdges) {
		
		for (int i = 0; i < numbOfNodes; i++) {
			int keyId = newKey(nodes, numbOfNodes);
			
			for (int j = 0; j < numbOfEdges; j++) {
				
				int sourceId = edge[j].getSourceId();
				int targetId = edge[j].getTargetId();
				
				if (nodes[keyId].getNodeId() == targetId) {
					
					if (nodes[keyId].getWeight() + edge[j].getWeight() < nodes[sourceId].getWeight()
							|| nodes[sourceId].isReachable() == false) {
						
						nodes[sourceId].setWeight(nodes[keyId].getWeight() + edge[j].getWeight());
						nodes[sourceId].setPreNode(nodes[keyId].getNodeId());
						nodes[sourceId].setReachable(true);
						
						
					}
				}
				
				if (nodes[keyId].getNodeId() == sourceId) {
				
					if (nodes[keyId].getWeight() + edge[j].getWeight() < nodes[targetId].getWeight()
							|| nodes[targetId].isReachable() == false) {
						
						nodes[targetId].setWeight(nodes[keyId].getWeight() + edge[j].getWeight());
						nodes[targetId].setPreNode(nodes[keyId].getNodeId());
						nodes[targetId].setReachable(true);
						
					}
				}
			}
			
			nodes[keyId].setUnused(false);
		}
	}
	
	private static void giveOutSoltion(NodeGraph nodes[], int numbOfNodes, int startNode) {
		for (int i = 0; i < numbOfNodes; i++) {
			System.out.println("Shortest path from Node " + nodes[startNode].getNodeId() + " to Node " + 
								nodes[i].getNodeId() + " has the weight of: " + nodes[i].getWeight());
		}
	}
	
	public static int askStartNode(int amountOfNodes) {
		Scanner sc = new Scanner(System.in);
		int startingNode;
		
		System.out.println("Bitte Geben Sie den Start Node ein: ");
		if (sc.hasNextLine()){
			do {
				startingNode = sc.nextInt();
				if (startingNode < 0 || startingNode > (amountOfNodes-1))
					System.out.println("\n<!--Die Eingegebene Zahl ist leider nicht gueltig.-->\nBitte Geben Sie eine Zahl zwischen 0-" + (amountOfNodes-1) + " ein: ");
				else break;	
			} while (true);
		}
		else startingNode = 1;
	
		sc.close();

		return startingNode;
		
	}

	private static int newKey(NodeGraph nodes[], int numbOfNodes) {
		
		Integer lowestWeight = Integer.MAX_VALUE;
		int keyId = 0;
		
		for (int i = 0; i < numbOfNodes; i++) {
			
			if (nodes[i].isReachable() == true && nodes[i].isUnused() == true) {
				
				if (nodes[i].getWeight() < lowestWeight) {
					
					lowestWeight = nodes[i].getWeight();
					keyId = nodes[i].getNodeId();
					
				}
			}
		}	
		return keyId;
	}
}