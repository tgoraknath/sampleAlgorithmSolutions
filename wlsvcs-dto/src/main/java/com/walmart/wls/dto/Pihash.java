package com.walmart.wls.dto;

import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.services.dto.BaseDTO;
import com.walmart.services.wls.options.PaymentOptions;
import com.walmart.wls.util.CollectionUtil;
import com.walmart.wls.util.JsonUtil;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//TODO - rename something relative..
public class Pihash extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4483133688330864498L;
	//which is nothing but pihash
	@XmlElement public String pihash;
	@XmlElement private String pmid;
	@XmlElement private boolean active;
	@XmlElement private String cardType;//cc, bc or gc
	@XmlElement private String country = "TBD";//identify USA, CA, UK or IND CHN etc
	@XmlElement private String isBusinessCard = "TBD - true or false corp or individual";
	@XmlElement private String icon = "TBD - sams and walmart";
	/** 
	 * indicates whether the given card is valid or not.
	 *  value is populated by Luhn Algorithm - Modulus 10.
	 */
	@XmlElement private Boolean valid;
	@XmlElement private String displayName;
	@XmlElement private String storage;
	@XmlElement private Map<String, Bin> binMap;
	@XmlElement private PaymentOptions paymentOptions;
	public Pihash() {
	}
	public String getPihash() {
		return pihash;
	}
	public PaymentOptions paymentOptions() {
		return paymentOptions;
	}
	public String getPmid() {
		return pmid;
	}
	public boolean isActive() {
		return active;
	}
	public boolean isValid() {
		return isNull(valid) ? false : valid;
	}
	public String getCardType() {
		return cardType;
	}
	public String getDisplayName() {
		return displayName;
	}
	public Map<String, Bin> getBinMap() {
		return CollectionUtil.unmodifiableMap(binMap);
	}
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
	
	public static class PihashBuilder {
		private String pihash;
		private PaymentOptions paymentOptions;
		private String pmid;
		private String cardType;
		private String displayName;
		private String storage;
		private boolean active;
		private Boolean valid;
		private Map<String, Bin> bins;
		public PihashBuilder() {
			bins = new HashMap<>();
		}
		public PihashBuilder pihash(String pihash) {
			this.pihash = pihash;
			return this;
		}
		public PihashBuilder pmid(String pmid) {
			this.pmid = pmid;
			return this;
		}
		public PihashBuilder active(boolean active) {
			this.active = active;
			return this;
		}
		public PihashBuilder validCardNbr(Boolean valid) {
			this.valid = valid;
			return this;
		}
		
		public PihashBuilder cardType(String cardType) {
			this.cardType = cardType;
			return this;
		}
		
		public PihashBuilder displayName(String displayName) {
			this.displayName = displayName;
			return this;
		}
		
		public PihashBuilder storage(String storage) {
			this.storage = storage;
			return this;
		}
		
		public PihashBuilder bins(Map<String, Bin> bins) {
			this.bins.putAll(bins);
			return this;
		}
		public PihashBuilder paymentOption(PaymentOptions paymentOptions) {
			this.paymentOptions = paymentOptions;
			return this;
		}
		/*
		public PaymentOptionBuilder bin(Bin bin) {
			bins.put(bin.getBin(), bin);
			return this;
		}*/
		
		public Pihash build() {
			Pihash po = new Pihash();
			po.pihash = pihash;
			po.paymentOptions = paymentOptions;
			po.pmid = pmid;
			po.cardType = cardType;
			po.displayName = displayName;
			po.storage = storage;
			po.active = active;
			po.valid = valid;
			po.binMap = bins;
			return po;
		}
	}
}
