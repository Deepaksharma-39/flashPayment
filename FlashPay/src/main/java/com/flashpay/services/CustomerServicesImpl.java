package com.flashpay.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashpay.exception.LoginException;
import com.flashpay.exception.SignupException;

import com.flashpay.model.Customer;
import com.flashpay.model.UserSession;
import com.flashpay.model.Wallet;
import com.flashpay.repositry.CustomerRepositry;
import com.flashpay.repositry.SessionRepositry;

@Service
public class CustomerServicesImpl implements CustomerServices {
	
	@Autowired
	private CustomerRepositry customerRepo;
	
	private SessionRepositry sessionRepo;
	@Override
	public Customer createNewSignUp(Customer newCustomer) throws SignupException {
		
		
		Customer opt= customerRepo.findByMobile(newCustomer.getMobile());
		if(opt!=null) {
			throw new SignupException("Customer already Present");
		}
		
		Wallet wallet=new Wallet();
		wallet.setBalance(0);
		wallet.setCustomer(newCustomer);
		newCustomer.setWallet(wallet);
		
		Customer customer=customerRepo.save(newCustomer);
		
		
		
		if(customer ==null)throw new SignupException("failed");
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer, String key) throws LoginException {
		UserSession loggedInUser= sessionRepo.findByUuid(key);
		
		if(loggedInUser == null) {
			throw new LoginException("Please provide a valid key to update a customer");
		}
		
		
		if(customer.getUserId() == loggedInUser.getUserId()) {
			//If LoggedInUser id is same as the id of supplied Customer which we want to update
			return customerRepo.save(customer);
		}
		else
			throw new LoginException("Invalid Customer Details, please login first");
	
	}
	

	


}
