package com.walmart.services.wls.options;

public class SimpleRecursion {
	
	public static int factorial(int nbr) {
		if(nbr == 1) {
			return 1;
		}
		return nbr * factorial(nbr - 1);
	}
	public static double power(double n , int m) {
		if(m == 0) {
			return 1;
		}
		return n * power(n, m - 1);
	}
	/**
	 * fibonacci(3) - 1,1,2 - 2
	 * fibonacci(4) - 1,1,2,3 - 3
	 * fibonacci(5) - 1,1,2,3,5 - 5
	 * @param n
	 * @return
	 */
	public static int fibonacci(int n) {
		/**if it requires 0,1,1 then split if into 2 
		 * one for 0-return0 and another 1-return1
		 */
		if(n == 0 || n == 1) {
			return 1;
		}
		return fibonacci(n-1)+fibonacci(n-2);
	}
	public static int sumOfDigits(int nbr) {
		if(nbr == 0) {
			return 0;
		}
		return nbr%10 + sumOfDigits(nbr/10);
	}
	public static int binarySearch(int[] ordered, int value) {
		return recursiveSearch(ordered, value, 0, ordered.length);
	}
	private static int recursiveSearch(int[] ordered, int value, int start,
			int end) {
		if(start > end) {
			return -1;
		}
		int mid = (start + end)/2;
		if(ordered[mid] == value) {
			return mid;
		}else if(ordered[mid] < ordered[start]) {
			return recursiveSearch(ordered, value, start, mid-1);
		}else {
			return recursiveSearch(ordered, value, mid+1, end);	
		}
	}
	/**
	 * leftRotation("12345", 1) - 23451
	 * leftRotation("12345", 4) - 51234
	 * @param arg
	 * @param by
	 * @return
	 */
	public static String leftRotation(String arg, int by) {
		char[] chars = arg.toCharArray();
		recursiveLR(chars, by, 0, chars[0]);
		return String.copyValueOf(chars);
		
	}
	public static String righttRotation(String arg, int by) {
		char[] chars = arg.toCharArray();
		recursiveRR(chars, by, 0, chars[0]);
		return String.copyValueOf(chars);
		
	}
	private static void recursiveLR(char[] chars, int by, int p, char v) {
		int tbp = p - by;
		int l = chars.length;
		if(tbp == 0 || l >= by) {
			chars[0] = v;
			return;
		}
		if(tbp < 0) {
			tbp = l + tbp;	
		}
		char tbv = chars[tbp];
		chars[tbp] = v;
		recursiveLR(chars, by, tbp, tbv);
	}
	private static void recursiveRR(char[] chars, int by, int p, char v) {
		int tbp = p + by;
		int l = chars.length;
		if(tbp == 0 || l >= by) {
			chars[0] = v;
			return;
		}
		if(tbp >= l) {
			tbp = l - tbp;	
		}
		char tbv = chars[tbp];
		chars[tbp] = v;
		recursiveLR(chars, by, tbp, tbv);
	}

}
