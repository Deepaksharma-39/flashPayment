package com.flashpay.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.customENUMS.TransactionType;
import com.flashpay.exception.LoginException;
import com.flashpay.exception.TransactionException;
import com.flashpay.model.Transaction;
import com.flashpay.services.TransactionServices;

@RestController
public class TransactionController {

	private TransactionServices services;
	
	@GetMapping("/transactions")
	public ResponseEntity<List<Transaction>> viewTransactionController(@RequestParam String key) throws LoginException, TransactionException{
		
		List<Transaction> transactions=services.viewAlltransaction(key);
		return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.OK);
	}
	
	@GetMapping("/transactions/bydate")
	public ResponseEntity<List<Transaction>> viewTransactionbyDateController(@RequestParam String from,@RequestParam String to,@RequestParam String key) throws LoginException, TransactionException{
		
		List<Transaction> transactions=services.viewTranscationByDate(from, to, key);
		return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/transactions/bytype")
	public ResponseEntity<List<Transaction>> viewTransactionbyTypeController(@RequestParam String key,@RequestParam TransactionType type) throws LoginException, TransactionException{
		
		List<Transaction> transactions=services.viewAllTransactionbyTransactionType(key, type);
		return new ResponseEntity<List<Transaction>>(transactions,HttpStatus.ACCEPTED);
	}
	
	
}
