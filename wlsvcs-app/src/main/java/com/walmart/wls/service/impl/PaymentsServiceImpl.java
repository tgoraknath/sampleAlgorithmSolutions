package com.walmart.wls.service.impl;

import static com.walmart.wls.bizz.ProfileSCMContext.find;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.ca.dto.PaymentPreference;
import com.walmart.services.pymt.PaymentsRequest;
import com.walmart.services.pymt.PaymentsResponse;
import com.walmart.wls.bizz.PaymentsContext;
import com.walmart.wls.dto.Member;
import com.walmart.wls.dto.Member.MemberBuilder;
import com.walmart.wls.service.IPaymentsService;

public class PaymentsServiceImpl implements IPaymentsService {
	public static final Logger LOG = LoggerFactory.getLogger(WLRestServiceImpl.class);

	@Override
	public PaymentsResponse paymentOptions(PaymentsRequest request) {
		Member m = find(request.getCustomerId());
		MemberBuilder mb = m.builder();
		List<PaymentPreference> po = m.getPaymentPreferences();
		return new PaymentsContext()
					.profile(mb)
					.payments(po)
					.construct();
	}

}
