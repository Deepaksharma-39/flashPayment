package com.flashpay.services;

import java.util.Set;

import com.flashpay.exception.BIllPaymentException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BillPayment;

public interface BillPaymentServices {

	public BillPayment makeBillPayment(BillPayment billpayment,String uniqueId) throws BIllPaymentException, LoginException;

	public Set<BillPayment> viewBillPayments(String uniqueId) throws LoginException,BIllPaymentException;
}
