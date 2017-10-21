package com.walmart.wls.dto;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class NodeHelper {
	public static boolean isUnival(Node node) {
		if(isNull(node)) {
			return true;
		}
		Node right = node.getRight();
		Node left = node.getLeft();
		int value = node.getValue();
		return check(value, right) && check(value, left)
					 && isUnival(right) && isUnival(left);
	}
	private static boolean check(int value, Node node) {
		return isNull(node) || value == node.getValue() ? true : false;
	}
	
	public static Node reverseIterative(Node node) {
		Node next = null;
		Node previous = null;
		Node currNode = node;
		while(nonNull(currNode)) {
			next = currNode.getRight();	
			currNode.right = previous;
			previous = currNode;
			currNode = next;
		}
		return previous;
	}
	
	public static Node reverseRecursive(Node node) {
		Node next = null;
		Node previous = null;
		Node currNode = node;
		while(nonNull(currNode)) {
			next = currNode.getRight();	
			currNode.right = previous;
			previous = currNode;
			currNode = next;
		}
		return previous;
	}
}
