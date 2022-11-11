package com.flashpay.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashpay.exception.BeneficiaryDetailException;
import com.flashpay.model.BeneficiaryDetails;

@Repository
public interface BeneficiaryDetailsRepositry extends JpaRepository<BeneficiaryDetails,Integer> {

	public List<BeneficiaryDetails> findByMobile(String beneficiaryMobileNo) throws BeneficiaryDetailException;
	public List<BeneficiaryDetails> findByWalletId(Integer walletId) throws BeneficiaryDetailException;
}
