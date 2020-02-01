package com.company;

import java.util.List;

public class CalculatingOfNodes{


	protected static void dijkstra(List<NodeGraph> nodes, List<EdgeGraph> edge, Integer numbOfNodes, Integer numbOfEdges, Integer nodeForCalculation) {
		nodes.get(nodeForCalculation).setWeight(0);
		nodes.get(nodeForCalculation).setPreNode(null);
		nodes.get(nodeForCalculation).setReachable(true);
		
		for (int i = 0; i < numbOfNodes; i++) {
			int keyId = newKey(nodes, numbOfNodes);
			for (int j = 0; j < numbOfEdges; j++) {
				int sourceId = edge.get(j).getSourceId();
				int targetId = edge.get(j).getTargetId();
				if (nodes.get(keyId).getNodeId() == targetId) {
					if (nodes.get(keyId).getWeight() + edge.get(j).getWeight() < nodes.get(sourceId).getWeight()
							|| !nodes.get(sourceId).isReachable()) {
						nodes.get(sourceId).setWeight(nodes.get(keyId).getWeight() + edge.get(j).getWeight());
						nodes.get(sourceId).setPreNode(nodes.get(keyId).getNodeId());
						nodes.get(sourceId).setReachable(true);
					}
				}
				if (nodes.get(keyId).getNodeId() == sourceId) {
					if (nodes.get(keyId).getWeight() + edge.get(j).getWeight() < nodes.get(targetId).getWeight()
							|| !nodes.get(targetId).isReachable()) {
						nodes.get(targetId).setWeight(nodes.get(keyId).getWeight() + edge.get(j).getWeight());
						nodes.get(targetId).setPreNode(nodes.get(keyId).getNodeId());
						nodes.get(targetId).setReachable(true);	
					}
				}
			}
			nodes.get(keyId).setUnused(false);
		}
	}

	protected static void dijkstra(List<NodeGraph> nodes, List<EdgeGraph> edge,  Integer numbOfNodes, Integer numbOfEdges, Integer firstNode, Integer secondNode) {
		nodes.get(firstNode).setWeight(0);
		nodes.get(firstNode).setPreNode(null);
		nodes.get(firstNode).setReachable(true);

		for (int i = 0; i < numbOfNodes; i++) {
			int keyId = newKey(nodes, numbOfNodes);
			for (int j = 0; j < numbOfEdges; j++) {
				int sourceId = edge.get(j).getSourceId();
				int targetId = edge.get(j).getTargetId();
				if (nodes.get(keyId).getNodeId() == targetId) {
					if (nodes.get(keyId).getWeight() + edge.get(j).getWeight() < nodes.get(sourceId).getWeight()
							|| nodes.get(sourceId).isReachable() == false) {
						nodes.get(sourceId).setWeight(nodes.get(keyId).getWeight() + edge.get(j).getWeight());
						nodes.get(sourceId).setPreNode(nodes.get(keyId).getNodeId());
						nodes.get(sourceId).setReachable(true);
					}
				}
				if (nodes.get(keyId).getNodeId() == sourceId) {
					if (nodes.get(keyId).getWeight() + edge.get(j).getWeight() < nodes.get(targetId).getWeight()
							|| nodes.get(targetId).isReachable() == false) {
						nodes.get(targetId).setWeight(nodes.get(keyId).getWeight() + edge.get(j).getWeight());
						nodes.get(targetId).setPreNode(nodes.get(keyId).getNodeId());
						nodes.get(targetId).setReachable(true);
					}
				}
			}
			nodes.get(keyId).setUnused(false);
		}
	}

	private static int newKey(List<NodeGraph> nodes, Integer numbOfNodes) {
		int lowestWeight = Integer.MAX_VALUE;
		int keyId = 0;
		for (int i = 0; i < numbOfNodes; i++) {
			if (nodes.get(i).isReachable() && nodes.get(i).isUnused()) {
				if (nodes.get(i).getWeight() < lowestWeight) {
					lowestWeight = nodes.get(i).getWeight();
					keyId = nodes.get(i).getNodeId();
				}
			}
		}	
		return keyId;
	}
}