package kepco.cms.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 *
 */
public class MenuTree {
	private Node root;

	public MenuTree(MenuVo rootData) {
		root = new Node();
		root.data = rootData;
		root.children = new ArrayList<Node>();
		root.parent = null;
	}

	public static class Node {
		private MenuVo data;
		private Node parent;
		private List<Node> children;
		/** 트리에서 자신 또는 부모인지 여부 */
		private boolean active = false;
		
		private Node() {
		}
		private Node(MenuVo data) {
			this.data = data;
			this.children = new ArrayList<Node>();
		}
		private Node(MenuVo data, Node parent) {
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
		public MenuVo getData() {
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
	 * <code>data</code>내의 <code>parentMenuIdx</code>로 부모를 찾아서 추가
	 * @param data 필수입력 값이 등록된 온전한? 데이터
	 * @return 등록되면 <code>true</code>, 부모를 찾을 수 없다면 <code>false</code>
	 */
	public boolean addChildByData(MenuVo data) {
		Node parent = findNode(root, data.getParentMenuIdx());
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
	public Node findNode(Node node, int menuIdx) {
		if (node.data.getMenuIdx() == menuIdx) {
			return node;
		}
		
		for (Node child : node.children) {
			Node node2 = findNode(child, menuIdx);
			if (node2 != null) {
				return node2;
			}
		}
		
		return null;
	}
	
	public Node findNodeByMn(Node node, String mn) {
		if (node.data.getMn() != null && node.data.getMn().equals(mn)) {
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
			Node node2 = findNodeByMn(child, mn);
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
	public List<MenuVo> getParentMenuList(Node node) {
		if(node == null) {
			return null;
		}
		
		List<MenuVo> menuVoList = new ArrayList<MenuVo>();
		
		Node myNode = node;
		do {
			menuVoList.add(myNode.data);
			myNode = myNode.parent;
		}
		while(myNode != null && myNode != root);
		
		Collections.reverse(menuVoList);
		return menuVoList;
	}
	
	public List<Node> getDepth2NodeList() {
		
		for (Node child : root.children) {
			if (child.active) {
				return child.children;
			}
		}
		
		return null;
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