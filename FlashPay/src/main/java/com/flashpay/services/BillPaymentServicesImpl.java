package com.flashpay.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashpay.exception.BIllPaymentException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BillPayment;
import com.flashpay.model.Customer;
import com.flashpay.model.Transaction;
import com.flashpay.model.UserSession;
import com.flashpay.model.Wallet;
import com.flashpay.repositry.BillPaymentRepository;
import com.flashpay.repositry.CustomerRepositry;
import com.flashpay.repositry.SessionRepositry;
import com.flashpay.repositry.TransactionRepository;

@Service
public class BillPaymentServicesImpl implements BillPaymentServices {

	@Autowired
	private BillPaymentRepository billRepo;
	
	@Autowired
	private SessionRepositry sessionRepo;
	
	@Autowired
	private CustomerRepositry customerRepo;
	
	@Autowired
	private TransactionRepository transRepo;
	
	
	@Override
	public BillPayment makeBillPayment(BillPayment billpayment, String uniqueId)
			throws BIllPaymentException, LoginException {
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null)throw new LoginException("please Login first");
		
		Optional<Customer> opt= customerRepo.findById(session.getUserId());
		if(opt.isEmpty()) throw new BIllPaymentException("Bill Payment Service unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		
		if(wallet.getBalance()<billpayment.getAmount()) throw new BIllPaymentException("Insufficiet balance, add money to your wallet");
		
		wallet.setBalance(wallet.getBalance()-billpayment.getAmount());
		customer.setWallet(wallet);
		
		customerRepo.save(customer);
		
		billpayment.setWalletId(wallet.getWalletId());
		billpayment.setTime(LocalDateTime.now());
		
		BillPayment bill= billRepo.save(billpayment);
		if(bill!=null) {
		Transaction transaction = new Transaction();
		transaction.setDescription(billpayment.getBilltype() +  " successfull");
		transaction.setAmount(billpayment.getAmount());
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionType(billpayment.getTransactionType());
		transaction.setWalletId(wallet.getWalletId());
		wallet.getTransaction().add(transaction);
		transRepo.save(transaction);
		}
		return bill;
	}

	@Override
	public Set<BillPayment> viewBillPayments(String uniqueId) throws LoginException, BIllPaymentException {
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null)throw new LoginException("please Login first");
		
		Optional<Customer> opt= customerRepo.findById(session.getUserId());
		if(opt.isEmpty()) throw new BIllPaymentException("Bill Payment Service unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		
		Set<BillPayment> bills=billRepo.findByWalletId(wallet.getWalletId());
		if(bills.isEmpty()) throw new BIllPaymentException("No Records available");
		return bills;
	}

}
