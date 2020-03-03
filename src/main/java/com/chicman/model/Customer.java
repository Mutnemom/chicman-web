package com.chicman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;

	private String fullName;
	private String email;
	private String password;
	private Date submission;
    private String type;
    private Boolean enabled;

	protected Customer() {}

	public Customer(String fullName, String email, String password) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.submission = new Date();
		this.type = "C";
		this.enabled = false;
	}

	public Integer getCustomerId() {
		return customerId;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getSubmission() {
		return submission;
	}

	public void setSubmission(Date submission) {
		this.submission = submission;
	}

	public String getType() {
	    return type;
    }

    public void setType(String type) {
	    this.type = type;
    }

    public Boolean getEnabled() {
	    return enabled;
    }

    public void setEnabled(Boolean enabled) {
	    this.enabled = enabled;
    }
	
}
