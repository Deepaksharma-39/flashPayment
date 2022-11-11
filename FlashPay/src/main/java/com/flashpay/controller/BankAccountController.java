package com.flashpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.exception.BankAccountException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BankAccount;
import com.flashpay.services.BankAccountServices;

@RestController
public class BankAccountController {

	@Autowired
	private BankAccountServices services;
	
	@PostMapping("/bankAccounts")
	public ResponseEntity<BankAccount> addBankController(@RequestBody BankAccount newBankAccount,@RequestParam(required = false) String key) throws LoginException, BankAccountException{
		
		BankAccount bankAccount=services.addBank(newBankAccount, key);
		return new ResponseEntity<BankAccount>(bankAccount,HttpStatus.OK);
	}
	
	@DeleteMapping("/bankAccounts/{accountNumber}/{key}")
	public ResponseEntity<BankAccount> removeBankController(@PathVariable Integer accountNumber, @PathVariable String key) throws BankAccountException, LoginException{
		
		BankAccount account= services.removeBank(accountNumber, key);
		return new ResponseEntity<BankAccount>(account,HttpStatus.ACCEPTED);
	}
}
