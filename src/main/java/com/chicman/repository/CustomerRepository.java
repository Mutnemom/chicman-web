package com.chicman.repository;

import com.chicman.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	List<Customer> findByFullNameStartsWithIgnoreCase(String fullName);
	List<Customer> findByEmail(String email);
}
