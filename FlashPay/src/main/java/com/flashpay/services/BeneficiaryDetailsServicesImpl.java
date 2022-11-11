package com.flashpay.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashpay.exception.BeneficiaryDetailException;
import com.flashpay.exception.LoginException;
import com.flashpay.model.BeneficiaryDetails;
import com.flashpay.model.Customer;
import com.flashpay.model.UserSession;
import com.flashpay.model.Wallet;
import com.flashpay.repositry.BeneficiaryDetailsRepositry;
import com.flashpay.repositry.CustomerRepositry;
import com.flashpay.repositry.SessionRepositry;


@Service
public class BeneficiaryDetailsServicesImpl implements BeneficiaryDetailsServices {

	@Autowired
	private BeneficiaryDetailsRepositry beneficiaryRepo;
	
	@Autowired
	private SessionRepositry sessionRepo;
	
	@Autowired
	private CustomerRepositry customerRepo;
	
	@Override
	public BeneficiaryDetails addBeneficiary(String uniqueId, BeneficiaryDetails beneficiaryDetail) throws BeneficiaryDetailException,LoginException {
		
		UserSession session= sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("Please Login first");
		
		Optional<Customer> opt= customerRepo.findById(session.getUserId());
		
		if(opt.isPresent()) {
			Customer customer=opt.get();
			Wallet wallet=customer.getWallet();
			beneficiaryDetail.setWalletId(wallet.getWalletId());
			
			List<BeneficiaryDetails> beneficiaries=wallet.getBeneficiaryDetails();
			
			beneficiaries.add(beneficiaryDetail);
			BeneficiaryDetails beneficiary=beneficiaryRepo.save(beneficiaryDetail);
			
			return beneficiary;
		}else throw new BeneficiaryDetailException("No Customer found");
		
		
	}

	@Override
	public BeneficiaryDetails deleteBeneficiary(String uniqueId, String benficiaryMobileNo) throws BeneficiaryDetailException,LoginException {
		
		UserSession session= sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("Please Login first");
		
		Optional<Customer> opt= customerRepo.findById(session.getUserId());
		if(!opt.isPresent()) throw new LoginException("Beneficiary Service not available"); 
		
			Customer customer= opt.get();
			Wallet wallet= customer.getWallet();
			
			List<BeneficiaryDetails> list=wallet.getBeneficiaryDetails();
			
			if(list.isEmpty())throw new BeneficiaryDetailException("There is no beneficiary found in the list");
			
				int index=-1;
				for(int i=0;i<list.size();i++) {
					if(list.get(i).getMobile().equals(benficiaryMobileNo)) {
						index=i;
						break;
					}
				}
				if(index==-1) throw new BeneficiaryDetailException("Beneficiary Not found");
				
				BeneficiaryDetails beneficiaryDetail=list.get(index);
				BeneficiaryDetails updated=list.remove(index);
				wallet.setBeneficiaryDetails(list);
				
				beneficiaryRepo.delete(beneficiaryDetail);
				return beneficiaryDetail;
				
		
	}

	@Override
	public List<BeneficiaryDetails> viewBeneficiaryByMobileNo(String uniqueId,String beneficiaryMobileNo) throws BeneficiaryDetailException, LoginException {
		UserSession session= sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("Please Login first");
		
		List<BeneficiaryDetails> beneficiaryDetail=beneficiaryRepo.findByMobile(beneficiaryMobileNo);
		if(beneficiaryDetail.isEmpty()) throw new BeneficiaryDetailException("No Beneficiary found with Mobile No : "+beneficiaryMobileNo);
		
			return beneficiaryDetail;
		
	}

	@Override
	public List<BeneficiaryDetails> viewAllBeneficiary(String uniqueId) throws BeneficiaryDetailException, LoginException {
		UserSession session= sessionRepo.findByUuid(uniqueId);
		if(session==null) throw new LoginException("Please Login first");
		
		Optional<Customer> opt=customerRepo.findById(session.getUserId());
		if(!opt.isPresent()) throw new BeneficiaryDetailException("beneficiary Service Unavailable");
		
		Customer customer=opt.get();
		Wallet wallet=customer.getWallet();
		
		List<BeneficiaryDetails> list= wallet.getBeneficiaryDetails();
		
		if(list.isEmpty()) throw new BeneficiaryDetailException("No Beneficiary available");
		return list;
	}

}
