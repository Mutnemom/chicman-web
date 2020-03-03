package com.chicman.repository;

import com.chicman.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {
	List<Checkout> findByCustomerId(Integer customerId);
}
