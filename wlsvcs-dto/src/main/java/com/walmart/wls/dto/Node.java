package com.walmart.wls.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.wls.util.JsonUtil;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2469737969587297664L;
	@XmlElement private int value;
	@XmlElement public Node right;
	@XmlElement private Node left;
	public Node(int value, Node right, Node left) {
		this.value = value;
		this.right = right;
		this.left = left;
	}
	public Node(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public Node getRight() {
		return right;
	}
	public Node getLeft() {
		return left;
	}
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
