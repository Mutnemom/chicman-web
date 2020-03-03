package com.chicman.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	@Lob
	private byte[] image;
	private Date submission;

	private String productDetails;
	private Float productPrice;
	private Float productDiscount;
	private Float productPriceSale;
	private Integer productRate;
	private Integer productDialTypeId;
    private Integer productDialColorId;
    private Integer productStrapTypeId;
    private Integer productStrapColorId;
    private Integer productBrandId;
	private Integer productDisplayId;
	private Integer productCategoriesId;

	protected Product() {}
	
	public Product(String productName) {
		this.productDetails = "";
		this.productPrice = 0.0f;
		this.productPriceSale = 0.0f;
		this.submission = new Date();
		this.image = null;
	}
	
	public Product(String productName, byte[] image) {
		this.productDetails = "";
		this.productPrice = 0.0f;
		this.productPriceSale = 0.0f;
        this.submission = new Date();
		this.image = image;
	}

	public Product(String name, String details, float price, byte[] image) {
	    this.productDetails = details;
	    this.productPrice = price;
		this.productPriceSale = price;
	    this.submission = new Date();
	    this.image = image;
    }

	public Integer getProductId() {
		return productId;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	public Float getProductPriceSale() {
		return productPriceSale;
	}

	public void setProductPriceSale(Float productPriceSale) {
		this.productPriceSale = productPriceSale;
	}

	public Date getSubmission() {
		return submission;
	}

	public void setSubmission(Date submission) {
		this.submission = submission;
	}

	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	public Float getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(Float productDiscount) {
		this.productDiscount = productDiscount;
	}

    public Integer getProductRate() {
        return productRate;
    }

    public void setProductRate(Integer productRate) {
	    this.productRate = productRate;
    }

    public Integer getProductDialTypeId() {
		return productDialTypeId;
	}

	public Integer getProductDialColorId() {
	    return productDialColorId;
    }

    public Integer getProductStrapTypeId() {
        return productStrapTypeId;
    }

    public Integer getProductStrapColorId() {
        return productStrapColorId;
    }

    public Integer getProductBrandId() {
        return productBrandId;
    }

	public Integer getProductDisplayId() {
		return productDisplayId;
	}

    public Integer getProductCategoriesId() {
        return productCategoriesId;
    }
}
