package com.flashpay.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashpay.exception.BankAccountException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BankAccount;
import com.flashpay.model.Customer;
import com.flashpay.model.UserSession;
import com.flashpay.model.Wallet;
import com.flashpay.repositry.BankAccountRepositry;
import com.flashpay.repositry.CustomerRepositry;
import com.flashpay.repositry.SessionRepositry;

@Service
public class BankAccountServicesImpl implements BankAccountServices {

	@Autowired
	private BankAccountRepositry accountRepo;
	
	@Autowired
	private SessionRepositry sessionRepo;
	
	@Autowired
	private CustomerRepositry customerRepo;
	
	@Override
	public BankAccount addBank(BankAccount bankAccount, String uniqueId) throws LoginException, BankAccountException {
		
		UserSession currentSession=sessionRepo.findByUuid(uniqueId);
		
		if(currentSession==null) throw new LoginException("Login first");
		
		Optional<Customer> opt=customerRepo.findById(currentSession.getUserId());
		Customer customer=opt.get();
		
		Wallet wallet=customer.getWallet();
		
		BankAccount opt1=accountRepo.findBymobileNumber(bankAccount.getMobileNumber());
		
		if(opt1!=null) throw new BankAccountException("Account with phone "+bankAccount.getMobileNumber() +" already Exist");
		
		bankAccount.setWalletId(wallet.getWalletId()); 
		 
		return accountRepo.save(bankAccount);
	}

	@Override
	public BankAccount removeBank(Integer accountNumber, String uniqueId) throws BankAccountException, LoginException {

		UserSession currentSession=sessionRepo.findByUuid(uniqueId);
		
		if(currentSession==null) throw new LoginException("Login first");
		
		Optional<BankAccount> bankAccountopt = accountRepo.findById(accountNumber);
		if(!bankAccountopt.isPresent()) throw new BankAccountException("Bank account does not exist with Account number"+" "+accountNumber);
		
		accountRepo.delete(bankAccountopt.get());
		
		return bankAccountopt.get();
	}

	@Override
	public BankAccount viewBankAccount(Integer accountNumber, String uniqueId)
			throws BankAccountException, LoginException {
		
		UserSession currentUser =  sessionRepo.findByUuid(uniqueId);
		
		if(currentUser==null) {
			throw new LoginException("Please Login first");
		}
		
		Optional<BankAccount> bankAccount = accountRepo.findById(accountNumber);
		
		if(bankAccount.isPresent()) {
			return bankAccount.get();
		}else {
			throw new BankAccountException("Bank account does not existed with account Number :" + accountNumber);
		}
	}

	@Override
	public List<BankAccount> viewAllAccount(String uniqueId) throws LoginException, BankAccountException {
		
		UserSession currentUser =  sessionRepo.findByUuid(uniqueId);
		
		if(currentUser==null) throw new LoginException("Please Login first");
		
		Optional<Customer> customeropt=customerRepo.findById(currentUser.getUserId());
		
		Wallet wallet=customeropt.get().getWallet();
		
		List<BankAccount> accounts=accountRepo.findByWalletId(wallet.getWalletId());
		if(accounts.isEmpty()) throw new BankAccountException("No back Account Registered");
		return accounts;
		
	}

}
