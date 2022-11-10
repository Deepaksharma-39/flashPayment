package com.flashpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.exception.LoginException;
import com.flashpay.model.CustomerDTO;
import com.flashpay.services.LoginServices;

@RestController
public class CustomerLoginController {

	@Autowired
	private LoginServices services;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginController(@RequestBody CustomerDTO dto) throws LoginException{
		
		String result=services.logIntoAccount(dto);
		return new ResponseEntity<String>(result,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logoutController(@RequestParam(required = false) String key) throws LoginException{
		String result=services.logOutFromAccount(key);
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
}
