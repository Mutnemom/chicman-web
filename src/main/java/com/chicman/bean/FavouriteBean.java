package com.chicman.bean;

import com.chicman.model.Customer;
import com.chicman.model.Favourite;
import com.chicman.model.Product;
import com.chicman.repository.FavouriteRepository;
import com.chicman.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionScope
@Component
public class FavouriteBean {

    @Autowired
    private FavouriteRepository favouriteRepo;

    @Autowired
    private ProductRepository productRepo;

    private List<Product> favouriteList;
    private Customer loggedInUser;

    public FavouriteBean() {
        this.favouriteList = new ArrayList<>();
    }

    public List<Product> getFavouriteList() {
        return favouriteList;
    }

    public void attachToCustomerFavourite(Customer loggedInUser) {
        this.loggedInUser = loggedInUser;

        Favourite itemFavourite;
		for (Product product: favouriteList) {
			itemFavourite = new Favourite(loggedInUser.getCustomerId(), product.getProductId());
			favouriteRepo.saveAndFlush(itemFavourite);
		}

        getFavouritesFromDatabase(loggedInUser);
    }

    private void getFavouritesFromDatabase(Customer loggedInUser) {
        resetProductList();

        Optional<Product> storedProduct;
        List<Favourite> favouriteOfLoggedInUserList = favouriteRepo.findByCustomerId(loggedInUser.getCustomerId(),
            new Sort(Sort.Direction.DESC, "submission"));

        for (Favourite item: favouriteOfLoggedInUserList) {
            storedProduct = productRepo.findById(item.getProductId());
            storedProduct.ifPresent(product -> favouriteList.add(product));
        }
    }

    public void resetProductList() {
        favouriteList.clear();
    }

    public void addProductToFavourite(@NotNull Product product) {
        if (loggedInUser != null) {
			Favourite item = new Favourite(loggedInUser.getCustomerId(), product.getProductId());
			favouriteRepo.saveAndFlush(item);

			getFavouritesFromDatabase(loggedInUser);
		} else {
            favouriteList.add(product);
        }
    }
}
