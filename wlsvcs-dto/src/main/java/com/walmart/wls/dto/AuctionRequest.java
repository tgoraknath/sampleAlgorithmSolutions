package com.walmart.wls.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class AuctionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3093446532247202L;
	private String profileContext;//Member, Addresses, cards
	//UUIDs to search by ids..
	private Set<UUID> skus;
	private String startDate;
	private String endDate;
	//bids, bid amt, name, startDate, endDate ;
	private String sortBy;
	

}
