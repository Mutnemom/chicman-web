package com.chicman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer verificationTokenId;

    private String token;
    private Integer customerId;
    private Date expiryDate;

    protected VerificationToken() {}

    public VerificationToken(String token, Integer customerId) {
        this.token = token;
        this.customerId = customerId;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        now = now.plusDays(1);

        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Integer getVerificationTokenId() {
        return verificationTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
