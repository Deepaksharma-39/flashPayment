package com.flashpay.services;

import java.util.List;

import com.flashpay.exception.BeneficiaryDetailException;
import com.flashpay.exception.LoginException;
import com.flashpay.exception.TransactionException;
import com.flashpay.exception.WalletException;
import com.flashpay.model.BeneficiaryDetails;
import com.flashpay.model.Customer;
import com.flashpay.model.Transaction;

public interface WalletService {

	public  String showBalance(String uniqueId) throws LoginException,WalletException;
	
	public Transaction fundTransferToWallet(String sourceMoblieNo,String targetMobileNo,Double amount,String uniqueId) throws WalletException, BeneficiaryDetailException, LoginException,TransactionException;
	
	public Transaction depositeAmountToWallet(String uniqueId,Double amount,Integer acnum) throws LoginException, TransactionException;
	
	public List<BeneficiaryDetails> getList(String uniqueId) throws LoginException, BeneficiaryDetailException;
	
	public Transaction addMoneyToWallet(String uniqueId, Double amount,Integer acnum) throws WalletException,TransactionException,LoginException;
}
