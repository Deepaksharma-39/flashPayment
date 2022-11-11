package com.flashpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashpay.exception.BeneficiaryDetailException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BeneficiaryDetails;
import com.flashpay.services.BeneficiaryDetailsServices;

@RestController
public class BeneficiaryController {

	@Autowired
	private BeneficiaryDetailsServices services;
	
	@PostMapping("/beneficiaries")
	public ResponseEntity<BeneficiaryDetails> addBeneficiaryController(@RequestParam String key,@RequestBody BeneficiaryDetails beneficiary) throws BeneficiaryDetailException, LoginException{
		
		BeneficiaryDetails details= services.addBeneficiary(key, beneficiary);
		return new ResponseEntity<BeneficiaryDetails>(details,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/beneficiaries")
	public ResponseEntity<BeneficiaryDetails> deleteBenficiaryController(@RequestParam(required = false) String key,@RequestParam String mobile) throws BeneficiaryDetailException, LoginException{
		
		BeneficiaryDetails details=services.deleteBeneficiary(key, mobile);
		
		return new ResponseEntity<BeneficiaryDetails>(details,HttpStatus.OK);
	}
	
	@GetMapping("/beneficiaries/{key}")
	public ResponseEntity<List<BeneficiaryDetails>> getBeneficiaries(@PathVariable String key,@RequestParam String mobile) throws BeneficiaryDetailException, LoginException{
		
		List<BeneficiaryDetails> list= services.viewBeneficiaryByMobileNo(key, mobile);
		return new ResponseEntity<List<BeneficiaryDetails>>(list,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/beneficiaries")
	public ResponseEntity<List<BeneficiaryDetails>> getBeneficiaries(@RequestParam String key) throws BeneficiaryDetailException, LoginException{
		
		List<BeneficiaryDetails> list= services.viewAllBeneficiary(key);
		return new ResponseEntity<List<BeneficiaryDetails>>(list,HttpStatus.ACCEPTED);
	}
	
}
