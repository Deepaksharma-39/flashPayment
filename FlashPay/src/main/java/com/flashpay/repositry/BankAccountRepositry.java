package com.flashpay.repositry;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashpay.model.BankAccount;

@Repository
public interface BankAccountRepositry extends JpaRepository<BankAccount, Integer> {

	public List<BankAccount> findByWalletId(Integer walletId);
	
	public BankAccount findBymobileNumber(String mobileNumber);
	
}
