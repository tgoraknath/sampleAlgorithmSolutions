package com.walmart.services.wls.options;

import static com.walmart.services.wls.options.PaymentOptions.find;
import static com.walmart.services.wls.options.PaymentOptions.findByPiHash;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.walmart.wls.dto.Node;
import com.walmart.wls.dto.NodeHelper;
import com.walmart.wls.dto.Pihash;
import com.walmart.wls.util.LuhnNumbers;
public class PaymentOptionsTest {
	
	public static void main(String[] args) {
		/*
		for(int i = 0 ; i< 15; i++) {
		System.out.println(UUID.randomUUID());	
		}
		testLuhnNumbers();
		univalTest();
		new PaymentOptionsTest().testPaymentOption();*/
		new PaymentOptionsTest().testPalidrome();
	}
	public void testPalidrome() {
		isPalidrome("madam");
		isPalidrome("nitin");
		isPalidrome("good");
		isPalidrome("goog");
	}
	private boolean isPalidrome(String args) {
		char[] chars = args.toCharArray();
		int i = 0;
		int size = chars.length;
		boolean retFlag = true;
		while(retFlag && i < size/2) {
			if(chars[i++] != chars[--size]) {
				retFlag = false;	
			}
			
		}
		return retFlag;
	}
	public void univalTest() {
		Node node = new Node(10, null, null);
		boolean isTrue = NodeHelper.isUnival(node);
		System.out.println("node : "+isTrue);
		node = new Node(10, new Node(10), null);
		isTrue = NodeHelper.isUnival(node);
		System.out.println("node : "+isTrue);
		node = new Node(10, new Node(10, new Node(20), null), null);
		isTrue = NodeHelper.isUnival(node);
		System.out.println("node : "+isTrue);
		Integer[] values = new Integer[] {1, 2, 3};
		Arrays.sort(values);
		List<Integer> list = new ArrayList<>(Arrays.asList(values));
		list.sort(null);
		System.out.println("max value is : "+list.get(list.size() - 1));
		
		String pattern = "[ 	!]";
		String attr = "gorak Gorak !	";
		String[] strVals = attr.split(pattern);
		System.out.println("strVals is : "+strVals);
	}
	
	public void testLuhnNumbers() {
		String card1 = "4111111111111111";
		boolean isLuhnNbr = LuhnNumbers.isLuhnNumber(card1);
		System.out.println("given number "+card1+ " is a Luhn Number ?"+isLuhnNbr);
		card1 = "4111111111111110";
		isLuhnNbr = LuhnNumbers.isLuhnNumber(card1);
		System.out.println("given number "+card1+ " is a Luhn Number ?"+isLuhnNbr);
		card1 = "4100390517954935";
		//checksum = 5; 4+2+0+0+3+9+0+1+1+5+9+1+4+9+3 = 51
		isLuhnNbr = LuhnNumbers.isLuhnNumber(card1);
		System.out.println("given number "+card1+ " is a Luhn Number ?"+isLuhnNbr);
		card1 = "4744830005747939";
		//checksum = 9; 4+2+0+0+3+9+0+1+1+5+9+1+4+9+3 = 51
		isLuhnNbr = LuhnNumbers.isLuhnNumber(card1);
		System.out.println("given number "+card1+ " is a Luhn Number ?"+isLuhnNbr);
		card1 = "6032205040960123537";//603220|5|04|0960123537
		//checksum = 7; 3+0+3+4+2+0+5+0+4+0+9+3+0+2+2+6+5+6 =54
		isLuhnNbr = LuhnNumbers.isLuhnNumber(card1);
		System.out.println("given number "+card1+ " is a Luhn Number ?"+isLuhnNbr);
		card1 = "603220509460123537";
		//checksum = 7; 3+0+3+4+2+0+5+0+4+0+9+3+0+2+2+6+5+6 =54
		isLuhnNbr = LuhnNumbers.isLuhnNumber(card1);
		System.out.println("given number "+card1+ " is a Luhn Number ?"+isLuhnNbr);
		card1 = "603220504126175769";//603220504126175769
		//checksum = 7; 3+0+3+4+2+0+5+0+4+0+9+3+0+2+2+6+5+6 =54
		isLuhnNbr = LuhnNumbers.isLuhnNumber(card1);
		System.out.println("given number "+card1+ " is a Luhn Number ?"+isLuhnNbr);
	}

	public void testPaymentOption() {
		testVisaCard();
		testMasterCard();
		testAmexCard();
		testDiscoveryCard();
		testSamsPersonalCard();
		testSamsBusinessCard();
		testSamsMasterCard();
		testSamsBusinessMasterCard();
		testSamsCashRewards();
		testSamsDirectLineOfCredit();
	}
	private void testVisaCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDB", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("VISA");
		Pihash byName = find("Visa");
		Pihash byPiHash = findByPiHash("PIH.pang.VISA.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("47780619397");
		Pihash byFirstDigits2 = find("4111111111111111");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2);		
	}
	private void testMasterCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDB", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("MASTER");
		Pihash byName = find("Master");
		Pihash byPiHash = findByPiHash("PIH.pang.MASTER.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("57780619397");
		Pihash byFirstDigits2 = find("5778061939777777");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2);		
	}
	private void testAmexCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDB", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("AMEX");
		Pihash byName = find("Amex");
		Pihash byPiHash = findByPiHash("PIH.pang.AMEX.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("37780619397");
		Pihash byFirstDigits2 = find("377806193977777");
		Pihash byFirstDigits3 = find("34780619397");
		Pihash byFirstDigits4 = find("347806193977777");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2,
				byFirstDigits3,byFirstDigits4);		
	}
	private void testDiscoveryCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDB", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("DISCOVER");
		Pihash byName = find("Discover");
		Pihash byPiHash = findByPiHash("PIH.pang.DISCOVER.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("60110619397");
		Pihash byFirstDigits2 = find("6011061939777777");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2);		
	}
	private void testSamsPersonalCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGESTORECARDP", "PLCC", "Sam's Personal Credit Card", new Integer[]{16}, "7714", "604599", "6032204", "521333"
		Pihash byPMID = find("SMGESTORECARDP");
		Pihash byName = find("SamsPersonalCreditCard");
		Pihash byPiHash = findByPiHash("PIH.pang.SMGESTORECARDP.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		//PaymentOption byFirstDigits1 = find("7714");
		Pihash byFirstDigits2 = find("604599");
		Pihash byFirstDigits3 = find("6032204");
		Pihash byFirstDigits4 = find("521333");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits2,
				byFirstDigits3,byFirstDigits4);
	}
	private void testSamsBusinessCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGESTORECARDB", "PLCC", "Sam's Business Credit Card", new Integer[]{16}, "040","7715", "604600", "6032205", "526053"
				Pihash byPMID = find("SMGESTORECARDB");
				Pihash byName = find("SamsBusinessCreditCard");
				Pihash byPiHash = findByPiHash("PIH.pang.SMGESTORECARDB.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
				//PaymentOption byFirstDigits1 = find("7715");
				Pihash byFirstDigits2 = find("604600");
				Pihash byFirstDigits3 = find("6032205");
				Pihash byFirstDigits4 = find("526053");
				//PaymentOption byFirstDigits5 = find("040");
				testPaymentOption(byName, byPMID, byPiHash, 
						byFirstDigits2,
						byFirstDigits3,byFirstDigits4);
	}
	private void testSamsMasterCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDP", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("SMGEMASTERCARDP");
		Pihash byName = find("SamsPersonalMasterCard");
		Pihash byPiHash = findByPiHash("PIH.pang.SMGEMASTERCARDP.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("521333");
		Pihash byFirstDigits2 = find("5213331111111111");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2);	
	}
	private void testSamsBusinessMasterCard() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDB", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("SMGEMASTERCARDB");
		Pihash byName = find("SamsBusinessMasterCard");
		Pihash byPiHash = findByPiHash("PIH.pang.SMGEMASTERCARDB.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("556053");
		Pihash byFirstDigits2 = find("5560531111111111");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2);		
	}
	
	private void testSamsCashRewards() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDB", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("CASHREWARDS");
		Pihash byName = find("CashRewards");
		Pihash byPiHash = findByPiHash("PIH.pang.CASHREWARDS.GIFTCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("77770619397");
		Pihash byFirstDigits2 = find("7777061939777777");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2);		
	}
	
	private void testSamsDirectLineOfCredit() {
		//ByName
		//byPMID
		//bypihash
		//bycardNbr(first 1-6 digits, full card)
		//"SMGEMASTERCARDB", "Master", "Sam's Personal Master Card", new Integer[]{16}, "54"
		Pihash byPMID = find("DIRECTLINEOFCREDIT");
		Pihash byName = find("DirectLineOfCredit");
		Pihash byPiHash = findByPiHash("PIH.pang.DIRECTLINEOFCREDIT.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202");
		Pihash byFirstDigits1 = find("603220509");
		Pihash byFirstDigits2 = find("6032205091111111111");
		testPaymentOption(byName, byPMID, byPiHash, 
				byFirstDigits1,byFirstDigits2);		
	}
	
	private void testPaymentOption(Pihash... options) {
		for(Pihash po : options) {
			assertPaymentOption(po);
		}
	}
	private void assertPaymentOption(Pihash option) {
		//Regular
		SamplePaymentOptions visa = SamplePaymentOptions.Visa;
		SamplePaymentOptions master = SamplePaymentOptions.Master;
		SamplePaymentOptions amex = SamplePaymentOptions.Amex;
		SamplePaymentOptions discovery = SamplePaymentOptions.Discover;
		//Sams Cards
		SamplePaymentOptions samsPersonalCreditCard = SamplePaymentOptions.SamsPersonalCreditCard;
		SamplePaymentOptions samsBusinessCreditCard = SamplePaymentOptions.SamsBusinessCreditCard;
		SamplePaymentOptions samsPersonalMasterCard = SamplePaymentOptions.SamsPersonalMasterCard;
		SamplePaymentOptions samsBusinessMasterCard = SamplePaymentOptions.SamsBusinessMasterCard;
		SamplePaymentOptions cashRewards = SamplePaymentOptions.CashRewards;
		SamplePaymentOptions giftCard = SamplePaymentOptions.WalmartAndSamsGiftCard;
		SamplePaymentOptions directLineOfCredit = SamplePaymentOptions.DirectLineOfCredit;
		//TODO Gift Cards to be added...
		if(isNull(option)) {
			System.out.println("Asserted result: "+false);
			return;
		}else {
			System.out.println("Asserted result: "+true);
		}
		/*
		switch (option) {
		//Regular cards
		case Visa:
			assertValues(option, visa);
			break;
		case Master:
			assertValues(option, master);
			break;
		case Amex:
			assertValues(option, amex);
			break;
		case Discover:
			assertValues(option, discovery);
			break;
		case SamsPersonalCreditCard:
			assertValues(option, samsPersonalCreditCard);
			break;
		case SamsBusinessCreditCard:
			assertValues(option, samsBusinessCreditCard);
			break;
		case SamsPersonalMasterCard:
			assertValues(option, samsPersonalMasterCard);
			break;
		case SamsBusinessMasterCard:
			assertValues(option, samsBusinessMasterCard);
			break;
		case CashRewards :
			assertValues(option, cashRewards);
			break;
		case WalmartAndSamsGiftCard :
			assertValues(option, giftCard);
			break;
		case DirectLineOfCredit:
			assertValues(option, directLineOfCredit);
			break;		
		default:
			//TODO assert fail for null
			System.out.println("Asserted result: "+false);
			break;
		}*/
	}
	/*
	private void assertValues(PaymentOptions option, SamplePaymentOptions testData) {
		String optionToString = option.toString();
		String testDataToString = testData.toString();
		boolean flag = testDataToString.equals(optionToString);
		System.out.println("Asserted result: "+flag);
	}*/
}
