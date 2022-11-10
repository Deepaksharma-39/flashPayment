package com.flashpay.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.exception.LoginException;
import com.flashpay.exception.SignupException;
import com.flashpay.model.Customer;
import com.flashpay.services.CustomerServices;

@RestController
public class CustomerController {

	@Autowired
	private CustomerServices service;
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> newSignUpController(@Valid @RequestBody Customer newCustomer) throws SignupException {
		Customer c= service.createNewSignUp(newCustomer);
		return new ResponseEntity<>(c,HttpStatus.CREATED);
	}
	
	@PutMapping("/customers")
	public ResponseEntity<Customer> updateCustomerController(@RequestBody Customer customer,@RequestParam(required = false) String key) throws LoginException{
		Customer updatedCustomer=service.updateCustomer(customer, key);
		return new ResponseEntity<Customer>(updatedCustomer,HttpStatus.OK);
	}
	
}
