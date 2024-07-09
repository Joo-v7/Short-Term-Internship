package kepco.cms.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 *
 */
public class CodeTree {
	private Node root;

	public CodeTree(CodeVo rootData) {
		root = new Node();
		root.data = rootData;
		root.children = new ArrayList<Node>();
		root.parent = null;
	}

	public static class Node {
		private CodeVo data;
		private Node parent;
		private List<Node> children;
		/** 트리에서 자신 또는 부모인지 여부 */
		private boolean active = false;
		
		private Node() {
		}
		private Node(CodeVo data) {
			this.data = data;
			this.children = new ArrayList<Node>();
		}
		private Node(CodeVo data, Node parent) {
			this.data = data;
			this.parent = parent;
			this.children = new ArrayList<Node>();
		}
		public List<Node> getChildren() {
			return this.children;
		}
		public Node getParent() {
			return this.parent;
		}
		public CodeVo getData() {
			return this.data;
		}
		public boolean getActive() {
			return this.active;
		}
	}
	
	public Node getRoot() {
		return root;
	}
	
	/**
	 * <code>data</code>내의 <code>parentCodeIdx</code>로 부모를 찾아서 추가
	 * @param data 필수입력 값이 등록된 온전한? 데이터
	 * @return 등록되면 <code>true</code>, 부모를 찾을 수 없다면 <code>false</code>
	 */
	public boolean addChildByData(CodeVo data) {
		Node parent = findNode(root, data.getCodeParent());
		if(parent == null) {
//			throw new NullPointerException("부모 노드를 찾을 수 없습니다.");
			return false;
		}
		
		parent.children.add(new Node(data, parent));
		return true;
	}
	
	/**
	 * 노드 찾기
	 * @param node 찾기 시작할 노드
	 * @param menuIdx
	 * @return
	 */
	public Node findNode(Node node, String code) {
		if (node.data.getCode() == code) {
			return node;
		}
		
		for (Node child : node.children) {
			Node node2 = findNode(child, code);
			if (node2 != null) {
				return node2;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param node
	 * @param code
	 * @return
	 */
	public Node findNodeByCode(Node node, String code) {
		// 상위 노드도 주소값을 가지므로 마지막 노드가 아니면 무시
		if (node.data.getCode() != null && code.startsWith(node.data.getCode()) && node.children.size() == 0) {
			
			// 부모 노드까지 모드 활성화 처리
			Node myNode = node;
			do {
				myNode.active = true;
				myNode = myNode.parent;
			}
			while(myNode != null && myNode != root);
			
			return node;
		}
		
		for (Node child : node.children) {
			Node node2 = findNodeByCode(child, code);
			if (node2 != null) {
				return node2;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public List<CodeVo> getParentCodeList(Node node) {
		if(node == null) {
			return null;
		}
		
		List<CodeVo> menuVoList = new ArrayList<CodeVo>();
		
		Node myNode = node;
		do {
			menuVoList.add(myNode.data);
			myNode = myNode.parent;
		}
		while(myNode != null && myNode != root);
		
		Collections.reverse(menuVoList);
		return menuVoList;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public List<Node> getSiblingList(Node node) {
		if(node == null || node.parent == null) {
			return null;
		}
		
		return node.parent.children;
	}
}