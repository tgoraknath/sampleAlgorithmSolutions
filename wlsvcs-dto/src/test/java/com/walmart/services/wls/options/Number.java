package com.walmart.services.wls.options;

public class Number {
	//need to check..
	public static boolean isPrimeOrNot(int num) {
		if (num <= 0 || num == 1) {
			return false;
		}
		if (num == 2 || num == 3) {
			return true;
		}
		if ((num * num - 1) % 24 == 0) {
			return true;
		} else {
			return false;
		}
	}

}
