package com.chicman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer commentId;
	
	private Integer productId;
	private Integer customerId;
	private Date submission;
	private String commentMessage;
	
	protected Comment() {}
	
	public Comment(Integer productId, Integer customerId, String commentMessage) {
		this.productId = productId;
		this.customerId = customerId;
		this.submission = new Date();
		this.commentMessage = commentMessage;
	}
	
	public Integer getCommentId() {
		return commentId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getSubmission() {
		return submission;
	}

	public void setSubmission(Date submission) {
		this.submission = submission;
	}

	public String getCommentMessage() {
		return commentMessage;
	}

	public void setCommentMessage(String commentMessage) {
		this.commentMessage = commentMessage;
	}

}