package com.walmart.services.wls.options;

public class AlterValue {
	private char[] values = null;
	private int alterCnt;
	private int size;
	public AlterValue(String values, int alterCnt) {
		this.values = values.toCharArray();
		this.alterCnt = alterCnt;
		size = values.length();
	}

	public String alter() {
		alter(0, values[0]);
		return String.copyValueOf(values);
	}

	//
	private void alter(int p, char v) {
		int tbp = p - alterCnt;
		if(tbp == 0 || size == alterCnt) {
			values[0] = v;
			return;
		}
		if(tbp < 0) {
			tbp = size + tbp;
		}
		char tbv = values[tbp];
		values[tbp] = v;
		alter(tbp, tbv);
	}
	public static void main(String[] args) {
		AlterValue av = new AlterValue("12345", 1);
		String alter = av.alter();
		System.out.println("expected 23451 and actual: "+alter);
		av = new AlterValue("12345", 2);
		alter = av.alter();
		System.out.println("expected 34512 and actual: "+alter);
		av = new AlterValue("12345", 3);
		alter = av.alter();
		System.out.println("expected 45123 and actual: "+alter);
		av = new AlterValue("12345", 4);
		alter = av.alter();
		System.out.println("expected 51234 and actual: "+alter);
		av = new AlterValue("12345", 5);
		alter = av.alter();
		System.out.println("expected 12345 and actual: "+alter);
	}
	
}
