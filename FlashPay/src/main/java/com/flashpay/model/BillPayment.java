package com.flashpay.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;

import com.flashpay.customENUMS.BillType;
import com.flashpay.customENUMS.TransactionType;

import lombok.Data;

@Entity
@Data
public class BillPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer billId;
	
	private BillType billtype;
	
	private TransactionType transactionType;
	
	@DecimalMin(value = "0.1", inclusive = true)
	private Double amount;
	
	private LocalDateTime time;
	
	private Integer walletId;
}
