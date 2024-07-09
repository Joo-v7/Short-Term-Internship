package kepco.cms.board;

public class BoardCateVo {
	
	private int categoryIdx;
	private int boardIdx;
	private String categoryKey;
	private String categoryValue;
	private String categoryParent;
	private int categoryOrder;
	private int categoryDepth;
	private String nodeId;
	private String parentNodeId;
	
	public int getCategoryIdx() {
		return categoryIdx;
	}
	public void setCategoryIdx(int categoryIdx) {
		this.categoryIdx = categoryIdx;
	}
	
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	
	public String getCategoryKey() {
		return categoryKey;
	}
	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}
	
	public String getCategoryValue() {
		return categoryValue;
	}
	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}
	
	public String getCategoryParent() {
		return categoryParent;
	}
	public void setCategoryParent(String categoryParent) {
		this.categoryParent = categoryParent;
	}
	
	public int getCategoryOrder() {
		return categoryOrder;
	}
	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}
	
	public int getCategoryDepth() {
		return categoryDepth;
	}
	public void setCategoryDepth(int categoryDepth) {
		this.categoryDepth = categoryDepth;
	}
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	public String getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
}










