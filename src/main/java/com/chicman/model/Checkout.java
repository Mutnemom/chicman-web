package com.chicman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Checkout {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer checkoutId;
	
	private Integer customerId;
	private Integer productId;
	private Boolean checkedOut;
	private Date checkedTime;
	
	protected Checkout() {}
	
	public Checkout(Integer customerId, Integer productId) {
		this.customerId = customerId;
		this.productId = productId;
		this.checkedOut = false;
//		this.checkedTime = new Date();
	}

	public Integer getCheckoutId() {
		return checkoutId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Boolean getCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(Boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Date checkedTime) {
		this.checkedTime = checkedTime;
	}
	
}
