package com.flashpay.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	@NotNull
	@Size(min = 3,max = 15)
	private String name;
	
	@NotNull
	@Column(unique = true)
	@Size(min = 10,max = 10,message = "mobile number must contain 10 digits")
	private String mobile;
	
	@NotNull
	@Size(min = 8)
	private String password;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private Wallet wallet;
	

	public Integer getUserId() {
		return userId;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Customer [userId=" + userId + ", name=" + name + ", mobile=" + mobile + ", password=" + password + "]";
	}
	
	
	
	
	
}