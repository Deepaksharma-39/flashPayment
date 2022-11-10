package com.flashpay.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashpay.model.UserSession;

@Repository
public interface SessionRepositry extends JpaRepository<UserSession, Integer> {

	public UserSession findByUuid(String uuid);
}
