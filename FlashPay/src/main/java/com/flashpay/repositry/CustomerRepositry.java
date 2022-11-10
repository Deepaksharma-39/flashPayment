package com.flashpay.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashpay.model.Customer;

@Repository
public interface CustomerRepositry extends JpaRepository<Customer, Integer> {

	public Customer findByMobile(String mobile);
}
