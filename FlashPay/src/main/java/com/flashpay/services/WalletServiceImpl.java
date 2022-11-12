package com.flashpay.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashpay.customENUMS.TransactionType;
import com.flashpay.exception.BeneficiaryDetailException;
import com.flashpay.exception.LoginException;
import com.flashpay.exception.TransactionException;
import com.flashpay.exception.WalletException;
import com.flashpay.model.BankAccount;
import com.flashpay.model.BeneficiaryDetails;
import com.flashpay.model.Customer;
import com.flashpay.model.Transaction;
import com.flashpay.model.UserSession;
import com.flashpay.model.Wallet;
import com.flashpay.repositry.BankAccountRepositry;
import com.flashpay.repositry.CustomerRepositry;
import com.flashpay.repositry.SessionRepositry;
import com.flashpay.repositry.TransactionRepository;
import com.flashpay.repositry.WalletRepositry;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepositry walletRepo;
	
	@Autowired
	private SessionRepositry sessionRepo;
	
	@Autowired
	private BankAccountRepositry bankRepo;
	
	@Autowired
	private CustomerRepositry customerRepo;
	
	@Autowired
	private TransactionRepository transRepo;
	
	@Override
	public String showBalance(String uniqueId) throws LoginException,WalletException {
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		if(opt.isEmpty()) throw new WalletException("wallet Service Unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
				
		return "Wallet balance="+wallet.getBalance();
	}

	@Override
	public Transaction fundTransferToWallet(String sourceMoblieNo, String targetMobileNo, Double amount, String uniqueId)
			throws BeneficiaryDetailException, LoginException, TransactionException, WalletException {
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		if(opt.isEmpty()) throw new WalletException("wallet Service Unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		
		
		if(wallet.getBalance()<amount) throw new TransactionException("Insufficient funds available, recharge your wallet");
		
		
		List<BeneficiaryDetails> beneficiaries=wallet.getBeneficiaryDetails();
		
		if(beneficiaries.isEmpty()) throw new BeneficiaryDetailException("No Beneficiaries available");
		
		boolean flag=false;
		
		for(BeneficiaryDetails b:beneficiaries) {
			if(b.getMobile().equals(targetMobileNo)) {
				flag=true;
				break;
			}
		}
		
		if(!flag) throw new BeneficiaryDetailException("No Beneficiaries available with mobile number :"+targetMobileNo);
		
		Customer target=customerRepo.findByMobile(targetMobileNo);
		if(target==null) throw new WalletException("Customer Wallet not available");
		Wallet targetWallet= target.getWallet();
		
		wallet.setBalance(wallet.getBalance()-amount);
		targetWallet.setBalance(targetWallet.getBalance()+amount);
		
		
		
		Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WALLET_TO_WALLET);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setDescription("Fund Transfer from Wallet to Wallet Successfull !");
        transaction.setWalletId(wallet.getWalletId());
       
        List<Transaction> transactions= wallet.getTransaction();
        transactions.add(transaction);
        
        wallet.setTransaction(transactions);
        
        customer.setWallet(wallet);
		target.setWallet(targetWallet);
		
		customerRepo.save(customer);
		customerRepo.save(target);
		
        Transaction t1= transRepo.save(transaction);
		
		return t1;
	}

	@Override
	public Transaction depositeAmountToWallet(String uniqueId, Double amount,Integer acnum) throws LoginException, TransactionException {
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		if(opt.isEmpty()) throw new TransactionException("Wallet Service Unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		if(wallet.getBalance()<amount) throw new TransactionException("Insufficient funds available in wallet");
		
		
		List<BankAccount> accounts= bankRepo.findByWalletId(wallet.getWalletId());
		
		BankAccount target=accounts.stream().filter(x->x.getAccountNumber()==acnum).findFirst().orElse(null);
		
		if(target==null) throw new TransactionException("account number not registered with the user");
		
		
			wallet.setBalance(wallet.getBalance()-amount);
			target.setBalance(target.getBalance()+amount);
			
		
		
		Transaction transaction = new Transaction();
		
        transaction.setTransactionType(TransactionType.WALLET_TO_BANK);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setDescription("Fund Transfer from Wallet to Bank is successfull!");
        transaction.setWalletId(wallet.getWalletId());
        
        List<Transaction> transactions= wallet.getTransaction();
        transactions.add(transaction);
        wallet.setTransaction(transactions);
        
        customer.setWallet(wallet);
		
		customerRepo.save(customer);
		bankRepo.save(target);
        
        Transaction t= transRepo.save(transaction);  
        
		
		
		return t;
	}

	@Override
	public List<BeneficiaryDetails> getList(String uniqueId) throws LoginException, BeneficiaryDetailException {
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		if(opt.isEmpty()) throw new BeneficiaryDetailException("Beneficiary Service Unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		
		List<BeneficiaryDetails> details=wallet.getBeneficiaryDetails();
		if(details.isEmpty()) throw new BeneficiaryDetailException("No beneficiaries available");
		
		return details;
	}



	@Override
	public Transaction addMoneyToWallet(String uniqueId, Double amount, Integer acnum)
			throws WalletException, TransactionException, LoginException {
		
		UserSession session=sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		if(opt.isEmpty()) throw new TransactionException("Wallet Service Unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		
		List<BankAccount> accounts= bankRepo.findByWalletId(wallet.getWalletId());
		
		BankAccount target=accounts.stream().filter(x->x.getAccountNumber()==acnum).findFirst().orElse(null);
		
		if(target==null) throw new TransactionException("account number not registered with the user");
		
		if(target.getBalance()<amount) throw new TransactionException("Insufficient funds available in wallet");
		
		wallet.setBalance(wallet.getBalance()+amount);
		target.setBalance(target.getBalance()-amount);
		
	
	
	Transaction transaction = new Transaction();
	
    transaction.setTransactionType(TransactionType.BANK_TO_WALLET);
    transaction.setTransactionDate(LocalDateTime.now());
    transaction.setAmount(amount);
    transaction.setDescription("Fund Transfer from Bank to Wallet is successfull!");
    transaction.setWalletId(wallet.getWalletId());
    
    List<Transaction> transactions= wallet.getTransaction();
    transactions.add(transaction);
    wallet.setTransaction(transactions);
    
    customer.setWallet(wallet);
	
	customerRepo.save(customer);
	bankRepo.save(target);
    
    Transaction t= transRepo.save(transaction);  
    
	
	
	return t;
		
		
	}

}
