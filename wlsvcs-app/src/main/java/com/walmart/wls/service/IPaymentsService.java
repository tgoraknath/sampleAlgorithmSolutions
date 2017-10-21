package com.walmart.wls.service;

import com.walmart.services.pymt.PaymentsRequest;
import com.walmart.services.pymt.PaymentsResponse;

public interface IPaymentsService {
	PaymentsResponse paymentOptions(PaymentsRequest request);

}
