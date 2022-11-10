package com.flashpay.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashpay.exception.LoginException;
import com.flashpay.model.Customer;
import com.flashpay.model.CustomerDTO;
import com.flashpay.model.UserSession;
import com.flashpay.repositry.CustomerRepositry;
import com.flashpay.repositry.SessionRepositry;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServicesImpl implements LoginServices {

	@Autowired
	private CustomerRepositry customerRepo;
	
	@Autowired
	private SessionRepositry sessionRepo;
	
	@Override
	public String logIntoAccount(CustomerDTO dto) throws LoginException {
		
//		Customer customer=customerRepo.findByMobile(dto.getMobileNo());
		System.out.println(dto.getMobile());
		Customer customer= customerRepo.findByMobile(dto.getMobile());
//		checking if mobile number is valid
		if(customer==null) {
			throw new LoginException("Mobile number not registered");
		}
		
//		checking if user is already logged in
		Optional<UserSession> validSession=sessionRepo.findById(customer.getUserId());
		if(validSession.isPresent()) {
			throw new LoginException("Session is Already Active, Logout First");
		}
		
//		checking password matches or not
		if(customer.getPassword().equals(dto.getPassword())) {
			String key= RandomString.make(6);
			
			UserSession currentSession=new UserSession(customer.getUserId(), key, LocalDateTime.now());
			sessionRepo.save(currentSession);
			return currentSession.toString();
		}else {
			return "Enter Correct Password";
		}
		
	}

	@Override
	public String logOutFromAccount(String key) throws LoginException {
		
		UserSession currentSession=sessionRepo.findByUuid(key);
		if(currentSession==null) {
			throw new LoginException("User not logged in");
		}
		sessionRepo.delete(currentSession);
		return "Successfully Logged out";
	}

}
