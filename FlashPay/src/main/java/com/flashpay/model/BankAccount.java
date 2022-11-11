package com.flashpay.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class BankAccount {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO )
	@JsonIgnore
	private Integer accountNumber;
	
	private double balance;
	@NotNull
	private String ifscCode;
	@NotNull
	private String bankName;
	
	@Size(min = 10,max = 10,message = "Mobile no should be atleast 10 digits")
	private String mobileNumber;

	private Integer walletId;
}
