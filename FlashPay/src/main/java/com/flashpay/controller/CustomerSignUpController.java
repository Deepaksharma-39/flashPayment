package com.flashpay.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.model.Customer;
import com.flashpay.services.CustomerServices;
import com.masai.exception.LoginException;

@RestController
public class CustomerSignUpController {

	@Autowired
	private CustomerServices service;
	
	@PostMapping("/signup")
	public ResponseEntity<Customer> newSignUpController(@Valid @RequestBody Customer newCustomer) throws LoginException {
		Customer c= service.createNewSignUp(newCustomer);
		return new ResponseEntity<>(c,HttpStatus.CREATED);
	}
	
}
