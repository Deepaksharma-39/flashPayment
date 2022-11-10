package com.flashpay.services;

import com.flashpay.exception.SignupException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.Customer;

public interface CustomerServices{

	public Customer createNewSignUp(Customer newCustomer) throws SignupException;
	
	public Customer updateCustomer(Customer customer,String key)throws LoginException;
}
