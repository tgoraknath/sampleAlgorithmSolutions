package com.walmart.wls.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7773080285600735857L;
	@XmlElement private String binStart;
	@XmlElement private String binEnd;
	@XmlElement private int length;
	@XmlElement private String defaultCVV = "TBD";
	@XmlElement private String pattern = "TBD";
	@XmlElement private String icon = "TBD";
	@XmlElement private Boolean cvvRequired = true;
	
	
	public Bin(String binStart, int length) {
		this.binStart = binStart;
		this.binEnd = binStart;
		this.length = length;
	}
	public Bin(String binStart, int length, boolean cvvRequired) {
		this.binStart = binStart;
		this.binEnd = binStart;
		this.length = length;
		this.cvvRequired = cvvRequired;
	}
	public Bin(String binStart, String binEnd, int length, boolean cvvRequired) {
		this.binStart = binStart;
		this.binEnd = binEnd;
		this.length = length;
		this.cvvRequired = cvvRequired;
	}
	
	public String getBinStart() {
		return binStart;
	}

	public String getBinEnd() {
		return binEnd;
	}

	public String getIcon() {
		return icon;
	}

	public String binRange() {
		return binStart.equals(binEnd) ? binStart : binStart+"_"+binEnd;
	}
	
	public int getLength() {
		return length;
	}
	
	public String getPattern() {
		return pattern;
	}
	public String getDefaultCVV() {
		return defaultCVV;
	}
	public boolean isCvvRequired() {
		return cvvRequired;
	}
	public boolean hasRange() {
		return binStart.equals(binEnd);
	}
}
