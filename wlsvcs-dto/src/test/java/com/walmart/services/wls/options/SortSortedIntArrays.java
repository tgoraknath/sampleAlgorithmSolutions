package com.walmart.services.wls.options;

//This is the text editor interface. 
//Anything you type or change here will be seen by the other person in real time.

//1) Given two sorted lists of integers, merge them into one sorted list.
//Ex: merge([1, 3, 5], [2, 4, 6]) -> [1, 2, 3, 4, 5, 6]
//both sorted.. 
//step1 : if the last element of the first array is <= to first element of the second array then arr1 + arr2 is the ans.
//step2: if the last element of the second array is <= to first element of the first array then arr2 + arr1 is the ans.
//step3: {1, 2, 5} {3, 4, 6}

public class SortSortedIntArrays {

	public static void main(String[] args) {
		int[] arr2 = new int[] { 1, 3, 5 };
		int[] arr1 = new int[] { 2, 5, 6 };
		int[] retArr = merge(arr1, arr2);
		for(int i = 0 ; i < retArr.length ; i++) {
		    System.out.print(retArr[i]);
		}
		System.out.println("");
		retArr = merge1(arr1, arr2);
		for(int i = 0 ; i < retArr.length ; i++) {
		    System.out.print(retArr[i]);
		}
	}

	public static int[] merge(int[] arr1, int[] arr2) {
		// duplicates in arr1 and arr2
		// retArr = arr1.length + arr2.length;
		int[] retArr = new int[arr1.length + arr2.length];
		int temp = 0;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr2.length; j++) {
				if (arr1[i] > arr2[j]) {
					// swap();
					temp = arr2[j];
					arr2[j] = arr1[i];
					arr1[i] = temp;
				}
			}
		}
		for (int j = 0; j < arr2.length - 1; j++) {
			temp = arr2[j];
			if (arr2[j] > arr2[j + 1]) {
				arr2[j] = arr2[j + 1];
				arr2[j + 1] = temp;
			}
		}
		System.arraycopy(arr1, 0, retArr, 0, arr1.length);
		System.arraycopy(arr2, 0, retArr, arr1.length, arr2.length);
		return retArr;
	}
	
	public static int[] merge1(int[] a, int[] b) {

	    int[] answer = new int[a.length + b.length];
	    int i = 0, j = 0, k = 0;

	    while (i < a.length && j < b.length)
	    {
	        if (a[i] < b[j])       
	            answer[k++] = a[i++];

	        else        
	            answer[k++] = b[j++];               
	    }

	    while (i < a.length)  
	        answer[k++] = a[i++];


	    while (j < b.length)    
	        answer[k++] = b[j++];

	    return answer;
	}

}
