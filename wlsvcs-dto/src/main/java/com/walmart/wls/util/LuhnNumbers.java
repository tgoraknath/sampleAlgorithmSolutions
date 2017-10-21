package com.walmart.wls.util;

import java.util.HashMap;
import java.util.Map;

/**
 * LuhnNumbers enum is an implementation of Luhn Algorithm and 
 * the purpose is to determine whether the given card number
 * is a valid Luhn Number or not.
 * 
 * @author gtulla
 *
 */
public enum LuhnNumbers {
	//value, modulus10, checksum
	Zero(0, 0, 0), One(1, 9, 2), Two(2, 8, 4), Three(3, 7, 6), Four(4, 6, 8), Five(5, 5, 1),
	Six(6, 4, 3), Seven(7, 3, 5), Eight(8, 2, 7), Nine(9, 1, 9);
	private int value;
	private int modulus10;
	private int checksum;
	private static Map<Integer, LuhnNumbers> MAP = new HashMap<>();
	static {
		for(LuhnNumbers ln : LuhnNumbers.values()) {
			MAP.put(ln.value, ln);
		}
	}
	LuhnNumbers(int value, int modulus10, int checksum) {
		this.value = value;
		this.modulus10 = modulus10;
		this.checksum = checksum;
	}
	
	public static LuhnNumbers find(String int0to9) {
		return MAP.get(Integer.valueOf(int0to9));
	}
	
	public static LuhnNumbers find(char int0to9) {
		return find(""+int0to9);
	}
	public static LuhnNumbers findChecksum(String luhnNbr) {
		return find(luhnNbr.charAt(luhnNbr.length() - One.value));
	}
	
	/**
	 * <p>isLuhnNumber api is to evaluate given number is a valid number
	 * based on the Luhn Algorithm.</p>
	 * <p>return true, if the given number is a valid based on Luhn principles otherwise false.
	 * 
	 * @param luhnNumber
	 * @return true/false
	 */
	public static boolean isLuhnNumber(String luhnNumber) {
		int checksumIndex = luhnNumber.length() - One.value;
		Integer total = Zero.value;
		boolean even = false;
		LuhnNumbers checksum = findChecksum(luhnNumber);
		while(checksumIndex-- > Zero.value) {
			LuhnNumbers luhnNbr = find(luhnNumber.charAt(checksumIndex));
			total += even == true ? luhnNbr.value : luhnNbr.checksum;
			even = !even;
		}
		return findChecksum(total.toString()).modulus10 == checksum.value ? true : false;
	}
}
