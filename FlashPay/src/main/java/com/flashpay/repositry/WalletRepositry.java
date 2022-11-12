package com.flashpay.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashpay.model.Wallet;

@Repository
public interface WalletRepositry extends JpaRepository<Wallet,Integer> {

}
