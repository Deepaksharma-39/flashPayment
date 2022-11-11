package com.flashpay.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flashpay.customENUMS.TransactionType;

import lombok.Data;

@Entity
@Data
public class Transaction {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    
    private TransactionType transactionType;
    
    @CreatedDate
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;
    
    private double amount;
    
	private String description;
    
    private Integer  walletId;
}
