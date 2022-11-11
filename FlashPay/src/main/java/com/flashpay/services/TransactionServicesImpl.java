package com.flashpay.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.TransactionalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.flashpay.customENUMS.TransactionType;
import com.flashpay.exception.LoginException;
import com.flashpay.exception.TransactionException;
import com.flashpay.model.Customer;
import com.flashpay.model.Transaction;
import com.flashpay.model.UserSession;
import com.flashpay.model.Wallet;
import com.flashpay.repositry.CustomerRepositry;
import com.flashpay.repositry.SessionRepositry;
import com.flashpay.repositry.TransactionRepository;

@Service
public class TransactionServicesImpl implements TransactionServices{

	@Autowired
	private SessionRepositry sessionRepo;
	
	@Autowired
	private CustomerRepositry customerRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Override
	public List<Transaction> viewAlltransaction(String uniqueId) throws LoginException, TransactionException {
	
		UserSession session=sessionRepo.findByUuid(uniqueId);
		
		if(session==null) throw new LoginException("Please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		
		if(opt.isEmpty()) throw new TransactionException("Transaction Service Unavailable");
		
		Customer customer= opt.get();
		Wallet wallet=customer.getWallet();
		
		List<Transaction> transactions= wallet.getTransaction();
		
		if(transactions.isEmpty()) throw new TransactionException("No transactions available");
		
		return transactions;
	}

	
	@Override
	public List<Transaction> viewTranscationByDate(String from, String to, String uniqueId)
			throws LoginException, TransactionException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		LocalDate start = LocalDate.parse(from, formatter);
		LocalDate end = LocalDate.parse(to, formatter);
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("Please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		
		if(opt.isEmpty()) throw new TransactionException("Transaction service is unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		
		List<Transaction> transaction=wallet.getTransaction();

		List<Transaction> trans = new ArrayList<>();
		
		for(int i = 0 ; i<transaction.size() ; i++) {
			String str1 =  transaction.get(i).getTransactionDate().format(formatter);
			LocalDate temp = LocalDate.parse(str1, formatter);
			if ((temp.isAfter(start) && temp.isBefore(end)) || temp.equals(start) || temp.equals(end)) {
				trans.add(transaction.get(i));
			}
		}
		return trans;
	}

	@Override
	public List<Transaction> viewAllTransactionbyTransactionType(String uniqueId, TransactionType type)
			throws LoginException, TransactionException {
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("Please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		Wallet wallet=opt.get().getWallet();
		
		List<Transaction> trans=transactionRepo.getTransactionByTransactionType(type);
		List<Transaction> transactions=new ArrayList<>();
		
		for(Transaction t: trans) {
			if(t.getWalletId()==wallet.getWalletId()) {
				transactions.add(t);
			}
		}
		
		if(transactions.isEmpty()) throw new TransactionException("No Transaction available of type "+type);
		return transactions;
	}

}
