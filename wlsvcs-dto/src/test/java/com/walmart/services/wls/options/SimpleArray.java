package com.walmart.services.wls.options;

public class SimpleArray {
	public static int[] findMinAndMax(int[] array) {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(int i : array) {
			if(i > max) {
				max = i;
			}else if(i < min) {
				min = i;
			}
			
		}
		return new int[]{min, max};
	}
	public static void main(String[] args) {
		findMinAndMax(new int[]{-20, 34, 21, -87, 92, Integer.MAX_VALUE}); 
		findMinAndMax(new int[]{10, Integer.MIN_VALUE, -2}); 
		findMinAndMax(new int[]{Integer.MAX_VALUE, 40, Integer.MAX_VALUE}); 
		findMinAndMax(new int[]{1, -1, 0});
	}

}
