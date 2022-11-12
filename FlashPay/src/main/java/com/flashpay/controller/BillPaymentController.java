package com.flashpay.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.exception.BIllPaymentException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BillPayment;
import com.flashpay.services.BillPaymentServices;

@RestController
public class BillPaymentController {

	@Autowired
	private BillPaymentServices services;
	
	@PostMapping("/bills")
	public ResponseEntity<BillPayment> payBillController(@RequestBody BillPayment newBill,@RequestParam String key) throws BIllPaymentException, LoginException{
		
		BillPayment bill=services.makeBillPayment(newBill, key);
		return new ResponseEntity<BillPayment>(bill,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/bills")
	public ResponseEntity<Set<BillPayment>> showAllBillsCotroller(@RequestParam String key) throws LoginException, BIllPaymentException{
		
		Set<BillPayment> bills=services.viewBillPayments(key);
		return new ResponseEntity<Set<BillPayment>>(bills,HttpStatus.OK);
	}
}
