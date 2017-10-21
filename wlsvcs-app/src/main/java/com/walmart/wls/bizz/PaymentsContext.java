package com.walmart.wls.bizz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.walmart.ca.dto.PaymentPreference;
import com.walmart.services.pymt.PaymentsResponse;
import com.walmart.services.pymt.PaymentsResponse.PaymentsResponseBuilder;
import com.walmart.services.wls.options.MemberOptions;
import com.walmart.wls.dto.Member;
import com.walmart.wls.dto.Payments;
import com.walmart.wls.dto.Member.MemberBuilder;
import com.walmart.wls.dto.Payment.PaymentBuilder;
import com.walmart.wls.dto.Payments.PaymentsBuilder;

public class PaymentsContext {
	private MemberBuilder profile;
	private PaymentsBuilder pymtsBuilder = new PaymentsBuilder(); 
	private Map<String, PaymentBuilder> pymtsMap = new HashMap<>();
	public PaymentsContext() {
		
	}
	public PaymentsContext profile(MemberBuilder profile) {
		this.profile = profile;
		return this;
	}
	public PaymentsContext payments(List<PaymentPreference> po) {
		for(PaymentPreference pp : po) {
			payment(buildPaymentBuilder(pp));
		}
		return this;
	}
	public PaymentsContext payment(PaymentBuilder pymtBuilder) {
		String pihash = pymtBuilder.getPihashAsString();
		pymtsBuilder.assign(pymtBuilder);
		pymtsMap.put(pihash, pymtBuilder);
		return this;
	}

	public PaymentsResponse construct() {
		Boolean eligibleToPlaceOrder = true;
		Boolean multiTenderAllowed = true;
		boolean eligibleToAddGCs = true;
		boolean eligibleToAddCards = true;
		Boolean eligibleToEditAutorenewals = false;
		//pay online{payNow, payLater}, pay later
		Boolean splitAllowed = true;
		Member m = profile.build();
		Payments payments = pymtsBuilder.build();
		if(MemberOptions.Direct == m.getType()) {
			splitAllowed = false;
			multiTenderAllowed = false;
			eligibleToAddGCs = false;
			eligibleToAddCards = false;
		}
		return new PaymentsResponseBuilder()
					.profile(m)
					.payments(payments)
					.multiTenderAllowed(multiTenderAllowed)
					.eligibleToAddGCs(eligibleToAddGCs)
					.eligibleToAddCards(eligibleToAddCards)
					.eligibleToEditAutorenewals(eligibleToEditAutorenewals)
					.splitAllowed(splitAllowed)
					.eligibleToPlaceOrder(eligibleToPlaceOrder)
					.build();
	}
	private PaymentBuilder buildPaymentBuilder(PaymentPreference pp) {
		return new PaymentBuilder()
		.id(pp.getPreferenceId())
		.pihash(pp.getPiHashId())
		//TODO do we need to externalize 31??
		.expireDate(pp.getCardExpiryDate(), 31);
}
}
