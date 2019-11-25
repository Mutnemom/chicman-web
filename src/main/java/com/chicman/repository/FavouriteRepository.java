package com.chicman.repository;

import com.chicman.model.Favourite;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
	List<Favourite> findByCustomerId(Integer customerId, Sort sortConstraints);
}
