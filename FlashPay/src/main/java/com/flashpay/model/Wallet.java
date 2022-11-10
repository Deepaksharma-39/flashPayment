package com.flashpay.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
public @Data class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer walletId;
	
	private double balance;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;

	public Wallet() {

	}
	
	
}
