package com.kabita.rms.services;

import com.kabita.rms.payload.BillResponse;
import com.kabita.rms.payload.BillingDto;

public interface BillingServices {
	void deleteBilling(Integer billId);

	BillResponse getBillByUser(Integer userId);

	void addBookingBilling(BillingDto billingDto, Integer userId, Integer bookingId);

	void addOrderBilling(BillingDto billingDto, Integer userId, Integer orderId);

	BillingDto updateBilling(BillingDto billingDto, Integer billId);

}
