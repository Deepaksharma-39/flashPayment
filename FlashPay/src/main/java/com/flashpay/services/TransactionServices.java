package com.flashpay.services;

import java.util.List;


import com.flashpay.customENUMS.TransactionType;
import com.flashpay.exception.LoginException;
import com.flashpay.exception.TransactionException;
import com.flashpay.model.Transaction;

public interface TransactionServices {

	public List<Transaction> viewAlltransaction(String  uniqueId)throws LoginException, TransactionException ;
	
	public List<Transaction> viewTranscationByDate(String from, String to , String uniqueId)  throws LoginException,TransactionException ;
		
	public List<Transaction> viewAllTransactionbyTransactionType(String uniqueId,TransactionType type) throws LoginException, TransactionException;
}
