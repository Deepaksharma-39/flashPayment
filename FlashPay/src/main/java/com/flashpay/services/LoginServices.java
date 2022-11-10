package com.flashpay.services;

import com.flashpay.exception.LoginException;
import com.flashpay.model.CustomerDTO;

public interface LoginServices {

	public String logIntoAccount(CustomerDTO dto)throws LoginException;

	public String logOutFromAccount(String key)throws LoginException;
}
