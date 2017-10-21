package com.walmart.services.wls.options;

import static com.walmart.wls.util.JsonUtil.toJson;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.wls.dto.Bin;

/**
 * SamplePaymentOptions
 * @author gtulla
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public enum SamplePaymentOptions {
	//Name(PMID , active, Card Type , Display Name , Lengths , starts with).
	//Regular Cards
	Visa("VISA", true, "Visa", "Visa Card", new Bin("4", 16)),
	Master("MASTER", true, "Master", "Master Card", new Bin("5", 16)),
	Amex("AMEX", true, "Amex", "American Express", new Bin("34", 15),new Bin("37", 15)),
	Discover("DISCOVER", true, "Discover", "Discover Credit Card", new Bin("6011", 16), new Bin("6", 16)),
	//Walmart
	//WalmartGiftCard("FDCGC", true, "Gift Card", "Walmart Gift Card", new Bin("7777", 16)),
	WalmartPersonalCreditCard("WMUSGESTORECARD", true, "PLCC", "Walmart Personal Credit Card", 
			new Bin("6032201", 16), new Bin("6032203", 16),new Bin("6032207", 16)),
	WalmartBusinessCreditCard("TBD", true, "PLCC", "Walmart Personal Credit Card", new Bin("6032202", 18)),
	WalmartPersonalMasterCard("WMMASTERCARD", true, "PLCC", "Walmart Personal Master Card", new Bin("523914", 16)),
	//Sam's Member Cards
	SamsPersonalCreditCard("SMGESTORECARDP", true, "PLCC", "Sam's Personal Credit Card", 
			new Bin("604599", 16), new Bin("6032204", 16),new Bin("521333", 16)),
	SamsBusinessCreditCard("SMGESTORECARDB", true, "PLCC", "Sam's Business Credit Card", 
			new Bin("604600", 16), new Bin("6032205", 16),new Bin("526053", 16)),
	SamsPersonalMasterCard("SMGEMASTERCARDP", true, "Master", "Sam's Personal Master Card", new Bin("521333", 16)),
	SamsBusinessMasterCard("SMGEMASTERCARDB", true, "Master", "Sam's Business Master Card", new Bin("556053", 16)),
	//DO NOT CHANGE THE ORDER CashRewards first and followed by Gift cards.
	CashRewards("CASHREWARDS", true, "Gift Card", "Cash Rewards", new Bin("7777", 16)),
	WalmartAndSamsGiftCard("FDCGC", true, "Gift Card","Gift Card", new Bin("7777", 16)),
	DirectLineOfCredit("SMGEDIRECT", true, "PLCC","Direct Line Of Credit", new Bin("603220509", 19));
	@XmlElement private String pmid;
	@XmlElement private boolean active;
	@XmlElement private String cardType;
	@XmlElement private String displayName;
	@XmlElement private Map<String, Bin> bins = new HashMap<>();
	SamplePaymentOptions(String pmid, boolean active, 
			String cardType, String displayName,
			Bin... bins) {
		this.pmid = pmid;
		this.active = active;
		this.cardType = cardType;
		this.displayName = displayName;
		for(Bin b : bins) {
			this.bins.put(b.binRange(), b);
		}
	}
	public String pmid() {
		return pmid;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String cardType() {
		return cardType;
	}
	
	public String displayName() {
		return displayName;
	}
	
	
	public Map<String, Bin> bins() {
		return bins;
	}
	
	@Override
	public String toString() {
		return toJson(this);
	}
}
