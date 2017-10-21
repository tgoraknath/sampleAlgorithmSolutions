package com.walmart.services.pymt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.services.dto.BaseDTO;
import com.walmart.wls.dto.Member;
import com.walmart.wls.dto.Payments;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentsResponse extends BaseDTO {

	/**
	 * SplitPayment, SplitCart
	 * 
	 * 
	 * PR | AL ARE NOT ALLOWED TO PAY ONLINE
	 * 
	 */
	private static final long serialVersionUID = -3128259072450592172L;
	/**
	 * mark these boolean fields to false when the member/customer is a DM - Direct Member.
	 */
	@XmlElement private Member profile;
	/**
	 * populate below multiTenderAllowed, splitAllowed and eligibleToPlaceOrder 
	 * fields when cart id is provided in request.
	 * 
	 * splitAllowed - false - at least one item in cart is in-store Only
	 * multiTenderAllowed - false - for DM
	 * 
	 * default to true;
	 */
	@XmlElement private Boolean eligibleToPlaceOrder;
	@XmlElement private Boolean multiTenderAllowed;
	//pay online{payNow, payLater}, pay later
	@XmlElement private Boolean splitAllowed;
	/**
	 * adjust eligibleToAddGCs, eligibleToAddCards to false for DMs or 
	 * where ever applicable.
	 */
	@XmlElement private boolean eligibleToAddGCs = true;
	@XmlElement private boolean eligibleToAddCards = true;
	@XmlElement private Boolean eligibleToEditAutorenewals = false;
	//A member/customer can have no payments.
	@XmlElement private Payments payments;
	public PaymentsResponse() {
	}
	
	public Member getProfile() {
		return profile;
	}

	public Payments getPayments() {
		return payments;
	}

	public Boolean getEligibleToPlaceOrder() {
		return eligibleToPlaceOrder;
	}

	public Boolean getMultiTenderAllowed() {
		return multiTenderAllowed;
	}

	public Boolean getSplitAllowed() {
		return splitAllowed;
	}

	public boolean isEligibleToAddGCs() {
		return eligibleToAddGCs;
	}

	public boolean isEligibleToAddCards() {
		return eligibleToAddCards;
	}

	public Boolean getEligibleToEditAutorenewals() {
		return eligibleToEditAutorenewals;
	}

	public static class PaymentsResponseBuilder {
		//rename to profile
		private Member profile;
		//A member/customer can have no payments.
		private Payments payments;
		private Boolean eligibleToPlaceOrder = true;
		private Boolean multiTenderAllowed = true;
		//pay online{payNow, payLater}, pay later
		private Boolean splitAllowed = false;
		private boolean eligibleToAddGCs = true;
		private boolean eligibleToAddCards = true;
		private Boolean eligibleToEditAutorenewals = false;
		public PaymentsResponseBuilder() {
		}
		public PaymentsResponseBuilder profile(Member profile) {
			this.profile = profile;
			return this;
		}
		public PaymentsResponseBuilder payments(Payments payments) {
			this.payments = payments;
			return this;
		}
		public PaymentsResponseBuilder multiTenderAllowed(boolean multiTenderAllowed) {
			this.multiTenderAllowed = multiTenderAllowed;
			return this;
		}
		public PaymentsResponseBuilder splitAllowed(Boolean splitAllowed) {
			this.splitAllowed = splitAllowed;
			return this;
		}
		public PaymentsResponseBuilder eligibleToAddGCs(boolean eligibleToAddGCs) {
			this.eligibleToAddGCs = eligibleToAddGCs;
			return this;
		}
		public PaymentsResponseBuilder eligibleToAddCards(boolean eligibleToAddCards) {
			this.eligibleToAddCards = eligibleToAddCards;
			return this;
		}
		public PaymentsResponseBuilder eligibleToEditAutorenewals(Boolean eligibleToEditAutorenewals) {
			this.eligibleToEditAutorenewals = eligibleToEditAutorenewals;
			return this;
		}
		public PaymentsResponseBuilder eligibleToPlaceOrder(Boolean eligibleToPlaceOrder) {
			this.eligibleToPlaceOrder = eligibleToPlaceOrder;
			return this;
		}
		public PaymentsResponse build() {
			PaymentsResponse r = new PaymentsResponse();
			r.profile = profile;
			//A member/customer can have no payments.
			r.payments = payments;
			r.eligibleToPlaceOrder = eligibleToPlaceOrder;
			r.multiTenderAllowed = multiTenderAllowed;
			//pay online{payNow, payLater}, pay later
			r.splitAllowed = splitAllowed;
			r.eligibleToAddGCs = eligibleToAddGCs;
			r.eligibleToAddCards = eligibleToAddCards;
			r.eligibleToEditAutorenewals = eligibleToEditAutorenewals;
			return r;
		}
	}

}
