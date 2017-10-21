package com.walmart.services.wls.options;

public enum MemberOptions {
	//BUSINESS, DIRECT, SAVINGS, GUEST, BUSINESS_TAX_EXEMPT, DIRECT_TAX_EXEMPT
	Business("BUSINESS"), 
	Direct("DIRECT"), 
	Plus("SAVINGS"), 
	DirectTaxExempt("DIRECT_TAX_EXEMPT"), 
	BusinessTaxExempt("BUSINESS_TAX_EXEMPT"), 
	//Need to check on managing payment methods 
	Guest("GUEST");
	private String type;
	MemberOptions(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
}
