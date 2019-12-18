public class EdgeGraph {
	
	private int sourceId;
	private int targetId;
	private int weight;
	
	public EdgeGraph(int targedId, int sourceId, int weight) {
		
		this.sourceId = sourceId;
		this.targetId = targedId;
		this.weight = weight;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
