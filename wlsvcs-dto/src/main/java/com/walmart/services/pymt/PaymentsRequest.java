package com.walmart.services.pymt;


import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.services.dto.BaseDTO;
import com.walmart.wls.dto.Payments;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentsRequest extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7704250462865116200L;
	@XmlElement private UUID customerId;
	//Cart Response {co-t/f, items[{}]}
	@XmlElement private UUID cartId;
	//TODO - purchase contract id. to be utilized for save payments(outside CO scope).
	//currently outside CO, cart total will be defaulted to preferred card or 
	@XmlElement private UUID pcId;
	@XmlElement private Payments payments;
	//response groups.. include auto renewal,
	public PaymentsRequest() {
	}
	public UUID getCustomerId() {
		return customerId;
	}
	public UUID getCartId() {
		return cartId;
	}
	public UUID getPcId() {
		return pcId;
	}
	public Payments getPayments() {
		return payments;
	}
	
}
