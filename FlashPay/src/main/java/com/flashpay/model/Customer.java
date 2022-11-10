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

import lombok.Data;



@Entity
public @Data class Customer {

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
	

	
	
}
