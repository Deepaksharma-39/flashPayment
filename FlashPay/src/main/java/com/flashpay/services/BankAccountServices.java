package com.flashpay.services;

import java.util.List;

import com.flashpay.exception.BankAccountException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BankAccount;

public interface BankAccountServices {

	public BankAccount addBank(BankAccount bankAccount,String uniqueId) throws LoginException,BankAccountException;

	public BankAccount removeBank(Integer accountNumber,String uniqueId) throws BankAccountException,LoginException;
	
	public BankAccount viewBankAccount(Integer accountNumber,String uniqueId) throws BankAccountException,LoginException ;
	
	public List<BankAccount> viewAllAccount(String uniqueId) throws LoginException, BankAccountException;
}
