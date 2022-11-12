package com.flashpay.repositry;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashpay.model.BillPayment;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPayment,Integer> {

	public Set<BillPayment> findByWalletId(Integer walletId);
}
