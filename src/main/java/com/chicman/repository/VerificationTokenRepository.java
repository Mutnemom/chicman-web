package com.chicman.repository;

import com.chicman.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
	VerificationToken findByToken(String token);
	VerificationToken findByCustomerId(Integer customerId);
}
