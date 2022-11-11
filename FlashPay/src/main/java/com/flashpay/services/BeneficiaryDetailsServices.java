package com.flashpay.services;

import java.util.List;

import com.flashpay.exception.BeneficiaryDetailException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BeneficiaryDetails;

public interface BeneficiaryDetailsServices {

	public BeneficiaryDetails addBeneficiary(String uniqueId,BeneficiaryDetails beneficiaryDetail) throws BeneficiaryDetailException,LoginException;
	public BeneficiaryDetails deleteBeneficiary(String uniqueId,String benficiaryMobileNo) throws BeneficiaryDetailException,LoginException;
	public List<BeneficiaryDetails> viewBeneficiaryByMobileNo(String uniqueId,String beneficiaryMobileNo) throws BeneficiaryDetailException,LoginException;
	public List<BeneficiaryDetails> viewAllBeneficiary(String uniqueId) throws BeneficiaryDetailException,LoginException;
}
