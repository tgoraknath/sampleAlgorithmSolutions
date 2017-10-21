package com.walmart.services.wls.options;

import static com.walmart.wls.util.JsonUtil.toJson;
import static com.walmart.wls.util.LuhnNumbers.isLuhnNumber;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.wls.dto.Bin;
import com.walmart.wls.dto.Pihash;
import com.walmart.wls.dto.Pihash.PihashBuilder;

/**
 * <p>PaymentOptions is a logical enum which represents the types of cards that
 * are supported by Walmart | Sams | ASDA | Canada etc.</p>
 * <p>Elements of PaymentOptions contains below attributes, 
 * PaymentOptions is a backward and forward compatible enum, we can add as many attributes as we want.</p>
 * Name(PMID , active, Card Type , Display Name , Lengths , starts with).
 * <p>PaymentOptions has 3 logical apis, which can be used to determine the PMIDs associated with the card</p>
 * @see SamplePaymentOptions#findByCardNbr(String)
 * @see SamplePaymentOptions#findByPiHash(String)
 * @see SamplePaymentOptions#find(String)
 * 
 * @author gtulla
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public enum PaymentOptions {
	//Name(PMID , active, Card Type , Display Name , Lengths , starts with).
	//Regular Cards
	Visa("VISA", true, "Visa", "Visa Card", "TBD", new Bin("4", 16)),
	Master("MASTER", true, "Master", "Master Card", "TBD", new Bin("5", 16)),
	Amex("AMEX", true, "Amex", "American Express", "TBD", new Bin("34", 15),new Bin("37", 15)),
	Discover("DISCOVER", true, "Discover", "Discover Credit Card", "TBD",
			new Bin("6011", 16), new Bin("6", 16)),
	//Walmart
	//WalmartGiftCard("FDCGC", true, "Gift Card", "Walmart Gift Card", new Bin("7777", 16)),
	WalmartPersonalCreditCard("WMUSGESTORECARD", true, "PLCC", "Walmart Personal Credit Card", 
			"TBD", new Bin("6032201", 16), new Bin("6032203", 16),new Bin("6032207", 16)),
	WalmartBusinessCreditCard("TBD", true, "PLCC", "Walmart Personal Credit Card", 
			"TBD", new Bin("6032202", 18)),
	WalmartPersonalMasterCard("WMMASTERCARD", true, "PLCC", "Walmart Personal Master Card", 
			"TBD", new Bin("523914", 16)),
	//Sam's Member Cards
	SamsPersonalCreditCard("SMGESTORECARDP", true, "PLCC", "Sam's Personal Credit Card", 
			"TBD", new Bin("604599", 16)),
	SamsBusinessCreditCard("SMGESTORECARDB", true, "PLCC", "Sam's Business Credit Card", 
			"TBD", new Bin("604600", 16)),
	SamsPersonalMasterCard("SMGEMASTERCARDP", true, "Master", "Sam's Personal Master Card", 
			"TBD", new Bin("521333", 16)),
	SamsBusinessMasterCard("SMGEMASTERCARDB", true, "Master", "Sam's Business Master Card", 
			"TBD", new Bin("556053", 16)),
	//DO NOT CHANGE THE ORDER CashRewards first and followed by Gift cards.
	CashRewards("CASHREWARDS", true, "Gift Card", "Cash Rewards", 
			"TBD", new Bin("7777", 16)),
	WalmartAndSamsGiftCard("FDCGC", true, "Gift Card","Gift Card", 
			"TBD", new Bin("7777", 16)),
	DirectLineOfCredit("SMGEDIRECT", true, "PLCC","Direct Line Of Credit", 
			"TBD", new Bin("603220504", 19, false));
	private static final Map<String, PaymentOptions> MAP = new HashMap<>();
	private static final Map<String, PaymentOptions> RANGE_MAP = new HashMap<>();
	private static final String DIGITS_PATTERN = "\\d+";
	static {
		for(PaymentOptions pymt : PaymentOptions.values()) {
			//first map the each bin with payment options.
			for(Bin bin : pymt.bins.values()) {
				MAP.put(bin.binRange(), pymt);
			}
			MAP.put(pymt.name().toUpperCase(), pymt);
			MAP.put(pymt.pmid.toUpperCase(), pymt);
			MAP.put(pymt.displayName.toUpperCase(), pymt);
		}
	}
	private String pmid;
	private boolean active;
	private String cardType;
	private String displayName;
	private String storage;
	private Map<String, Bin> bins = new HashMap<>();
	PaymentOptions(String pmid, boolean active, 
			String cardType, String displayName,
			String storage, Bin... bins) {
		this.pmid = pmid;
		this.active = active;
		this.cardType = cardType;
		this.displayName = displayName;
		this.storage = storage;
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
	
	/**
	 * 
	 * @param valid
	 * @return
	 */
	public Pihash buildPO(String pihash, Boolean valid) {
		return new PihashBuilder()
					.pihash(pihash)
			 		.pmid(pmid)
			 		.paymentOption(this)
			 		.displayName(displayName)
			 		.storage(storage)
			 		.active(active)
			 		.validCardNbr(valid)
			 		.cardType(cardType)
			 		.bins(bins)
			 		.build();
	}
	
	@Override
	public String toString() {
		return toJson(this);
	}
	/**
	 * find API return PaymentOptions associated for the given pmidOrNameOrDisplayNameOrBin.
	 * find may return null if the input argument is null or not a valid one as per Walmart standards.
	 * 
	 * @param pmidOrNameOrDisplayNameOrBinOrCard
	 * @return it can return null or a valid PaymentOption
	 */
	public static Pihash find(String pmidOrNameOrDisplayNameOrBinOrCard) {
		String lv = pmidOrNameOrDisplayNameOrBinOrCard.toUpperCase();
		PaymentOptions po = null;
		Boolean valid = null;
		if(lv.matches(DIGITS_PATTERN)) {
			int len = lv.length();
			String bin = null;
			int cnt = 0;
			int expectedLen = 0;
			while(cnt++ < len) {
				bin = lv.substring(0, cnt);
				PaymentOptions locVal = lookup(bin);
				if(nonNull(locVal)) {
					po = locVal;	
					expectedLen = po.bins.get(bin).getLength();
				}
			}
			valid = expectedLen == len ? isLuhnNumber(lv) : false;
		} else {
			po = MAP.get(lv);
		}
		return isNull(po) ? null : po.buildPO(null, valid);
	}
	/**
	 * 
	 * @param bin
	 * @return
	 */
	private static PaymentOptions lookup(String bin) {
		PaymentOptions locVal = MAP.get(bin);
		if(isNull(locVal)) {
			Set<String> keys = RANGE_MAP.keySet();
			for(String key : keys) {
				/**
				 * now check whether the bin is in the range of key.
				 * 2 options.. split the key int start and end and 
				 * see if the given bin is in the same range.
				 */
				
				if(bin.matches(key)) {
					locVal = RANGE_MAP.get(key);
					break;
				}	
			}
		}
		return locVal;
	}
	/**
	 * <p>findByPiHash splits the given piHash value by dot(.), 
	 * takes the pmid from 1st index and invokes find api by passing
	 * pmid and returns the PaymentOptions associated with pmid.</p>
	 * <p>findByPiHash supports non case sensivity. ex: <nl> 
	 * <p>PIH.pang.CASHREWARDS.GIFTCARD.aca46442-f3ee-4eec-8e99-b6da51733838.1178 </p>
	 * <p>PIH.volt.SamsPersonalCreditCard.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51733838.2200 </p>OR <p>
	 * PIH.pang.cashrewards.GIFTCARD.aca46442-f3ee-4eec-8e99-b6da51733838.1178</p>
	 * <p>PIH.volt.SMGESTORECARDP.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51733838.2200 </p></p>
	 * @param piHash
	 * @return PaymentOption associated with pi-hash
	 */
	public static Pihash findByPiHash(String piHash) {
		String[] phAttrs = piHash.split("\\.");
		Pihash retVal = null;
		if(phAttrs.length > 2) {
			//here the input piHash should contain the PMID in caps.
			PaymentOptions po = MAP.get(phAttrs[2]);
			retVal = po.buildPO(piHash, true);
		}
		return retVal;
	}
	/**
	 * <p>findByCardNbr determines the PaymentOptions associated with given card number.
	 * card number can vary from first digit to maximum length of the card. findByCardNbr
	 * api tries to determine the PaymentOptions starting at first digit till 6th digit. For Example:</p> 
	 * Master card starts with '5' where as Sams Master card starts with 54 and Sams Business Master Card with 55, 
	 * then based on first digit the card is considered as Master PaymentOptions 
	 * if the second digit is either 4 or 5 then it will override Master as Sams Master card and returns.</p>
	 * @param cardNbr
	 * @return either null or PaymentOptions associated with cardNbr
	 */
	public static PaymentOptions findByCardNbr(String cardNbr) {
		int len = cardNbr.length();
		PaymentOptions retVal = null;
		int cnt = 0;
		PaymentOptions locVal = null;
		String bin = null;
		int expectedLen = 0;
		while(cnt++ < len) {
			bin = cardNbr.substring(0, cnt);
			locVal = MAP.get(bin);
			if(nonNull(locVal)) {
				retVal = locVal;	
				expectedLen = retVal.bins.get(bin).getLength();
			}
		}
		if(expectedLen == len && isLuhnNumber(cardNbr)) {
			retVal = null;	
		}
		return retVal;
	}
}
