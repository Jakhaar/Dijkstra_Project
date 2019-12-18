
public class NodeGraph {
	
	private int nodeId;
	private int weight;
	private int preNodeId;
	private boolean unused;
	private boolean reachable;
	private Integer preNode;
	
	public NodeGraph(int nodeId) {
		this.nodeId = nodeId;
		this.unused = true;
		this.reachable = false;
		this.preNode = -1;
		
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getPreNodeId() {
		return preNodeId;
	}

	public void setPreNodeId(int preNodeId) {
		this.preNodeId = preNodeId;
	}

	public boolean isUnused() {
		return unused;
	}

	public void setUnused(boolean unused) {
		this.unused = unused;
	}

	public boolean isReachable() {
		return reachable;
	}

	public void setReachable(boolean reachable) {
		this.reachable = reachable;
	}

	public Integer getPreNode() {
		return preNode;
	}

	public void setPreNode(Integer preNode) {
		this.preNode = preNode;
	}
}
