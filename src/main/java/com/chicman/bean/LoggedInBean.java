package com.chicman.bean;

import com.chicman.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class LoggedInBean {
    
	private Customer logedInUser = null;
	
	public Customer getLoggedInUser() {
		return logedInUser;
	}
	
	public void setLogedInUser(Customer logedInUser) {
		this.logedInUser = logedInUser;
	}
}
