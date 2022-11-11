package com.flashpay.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class BeneficiaryDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int benificiaryId;
	
	@Size(min = 3,max = 15, message = "Name should be atleast 3 letters")
	private String name;
	
	@Size(min = 10,max = 10)
	private String mobile;
	
	private Integer walletId;
}
