package com.chicman.repository;

import com.chicman.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	List<Comment> findByProductId(Integer productId);
}
