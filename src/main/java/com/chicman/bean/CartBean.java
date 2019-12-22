package com.chicman.bean;

import com.chicman.model.Checkout;
import com.chicman.model.Customer;
import com.chicman.model.Product;
import com.chicman.repository.CheckoutRepository;
import com.chicman.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SessionScope
@Component
public class CartBean {
	
	@Autowired
	private CheckoutRepository checkoutRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	private List<Product> productList = new ArrayList<>();
	private Customer customer = null;
	
	public List<Product> getProductList() {
		return productList;
	}
	
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	public int countItem() {
		return productList.size();
	}
	
	public void addProductToCart(Product product) {
		if (customer != null) {
			Checkout itemCheckout = new Checkout(customer.getCustomerId(), product.getProductId());
			checkoutRepo.saveAndFlush(itemCheckout);
		}
		
		productList.add(product);
	}
	
	public void resetProductList() {
		productList.clear();
	}
	
	public void removeProductFromCart(Product removeItem) {
		if (customer != null) {
			Checkout checkItem;
			List<Checkout> checkList = checkoutRepo.findByCustomerId(customer.getCustomerId());
			for (int i = checkList.size(); i > 0; --i) {
				checkItem = checkList.get(i - 1);
				if (!checkItem.getCheckedOut() && checkItem.getProductId() == removeItem.getProductId()) {
					checkoutRepo.delete(checkItem);
					break;
				}
			}
		}
		
		productList.remove(removeItem);
	}
	
	public void attachToCustomerCart(Customer customer) {
		this.customer = customer;
		
		Checkout itemCheckout;
		for (Product product: productList) {
			itemCheckout = new Checkout(customer.getCustomerId(), product.getProductId());
			checkoutRepo.saveAndFlush(itemCheckout);
		}
		
		productList = new ArrayList<>();
		Optional<Product> storedProduct;
		List<Checkout> checkList = checkoutRepo.findByCustomerId(customer.getCustomerId());
		for (Checkout item: checkList) {
		    if (!item.getCheckedOut()) {
                storedProduct = productRepo.findById(item.getProductId());
                storedProduct.ifPresent(product -> productList.add(product));
            }
		}
	}

	public void checkoutAllProductFromCart() {
        if (customer != null) {
            for (Product removeItem: productList) {
                List<Checkout> checkList = checkoutRepo.findByCustomerId(customer.getCustomerId());
                for (Checkout checkItem : checkList) {
                    if (!checkItem.getCheckedOut() && checkItem.getProductId().equals(removeItem.getProductId())) {
                        checkItem.setCheckedOut(true);
                        checkItem.setCheckedTime(new Date());
                        checkoutRepo.saveAndFlush(checkItem);
                        break;
                    }
                }
            }
        }

		productList = new ArrayList<>();
	}
}
