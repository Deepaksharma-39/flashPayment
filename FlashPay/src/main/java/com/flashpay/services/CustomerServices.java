package com.flashpay.services;

import com.flashpay.model.Customer;
import com.masai.exception.LoginException;

public interface CustomerServices{

	public Customer createNewSignUp(Customer newCustomer) throws LoginException;
}
