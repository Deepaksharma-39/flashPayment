package com.flashpay.repositry;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashpay.customENUMS.TransactionType;
import com.flashpay.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

	public List<Transaction> findByWalletId(Integer walletId);
	
	public List<Transaction> getTransactionByTransactionType(TransactionType type);

	public List<Transaction> findByTransactionDate(LocalDate date);
	
}
