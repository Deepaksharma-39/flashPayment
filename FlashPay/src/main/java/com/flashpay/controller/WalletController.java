package com.flashpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.exception.BeneficiaryDetailException;
import com.flashpay.exception.LoginException;
import com.flashpay.exception.TransactionException;
import com.flashpay.exception.WalletException;
import com.flashpay.model.Transaction;
import com.flashpay.model.Wallet;
import com.flashpay.services.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService service;
	

	@GetMapping("/showbalance")
	public ResponseEntity<String> showBalanceController(@RequestParam String uniqueId) throws LoginException, WalletException {
		
		String res=service.showBalance(uniqueId);
		return new ResponseEntity<String>(res,HttpStatus.OK);
		
	}
	@GetMapping("/transfer")
	public ResponseEntity<Transaction> fundTransferToWalletController(@RequestParam String sourceMoblieNo,@RequestParam String targetMobileNo,@RequestParam Double amount,@RequestParam String uniqueId) throws WalletException, BeneficiaryDetailException, LoginException, TransactionException {
		Transaction t=service.fundTransferToWallet(sourceMoblieNo, targetMobileNo, amount, uniqueId);
		return new ResponseEntity<Transaction>(t,HttpStatus.OK);
	}
	
	@GetMapping("/deposit")
	public ResponseEntity<Transaction> depositeAmountToWalletController(@RequestParam String uniqueId,@RequestParam Double amount,@RequestParam Integer acnum) throws LoginException, TransactionException {
		
		Transaction t=service.depositeAmountToWallet(uniqueId, amount, acnum);
		return new ResponseEntity<Transaction>(t,HttpStatus.OK);
	}
	
	@GetMapping("/addmoney")
	public ResponseEntity<Transaction> addMoneyToWalletController(@RequestParam String uniqueId,@RequestParam Double amount,@RequestParam Integer acnum) throws WalletException, TransactionException, LoginException {
		
		Transaction t= service.addMoneyToWallet(uniqueId, amount, acnum);
		return new ResponseEntity<Transaction>(t,HttpStatus.OK);
	}
	
}
