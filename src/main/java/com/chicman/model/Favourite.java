package com.chicman.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@SuppressWarnings("unused")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer favouriteId;
    private Integer customerId;
    private Integer productId;
    private Date submission;

    protected Favourite() {
    }

    public Favourite(Integer customerId, Integer productId) {
        this.customerId = customerId;
        this.productId = productId;
        this.submission = new Date();
    }

    public Integer getFavouriteId() {
        return favouriteId;
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

    public Date getSubmission() {
        return submission;
    }

    public void setSubmission(Date submission) {
        this.submission = submission;
    }
}
