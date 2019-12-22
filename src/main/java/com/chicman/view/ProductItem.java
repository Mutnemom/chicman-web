package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductItem extends CssLayout {
    private final Logger log = LoggerFactory.getLogger(ProductItem.class);

    private final ChicManUI eventHandler;
    private final Product product;
    private final Image productImg;
    private final Button likeBtn;
    private StreamResource imgResource;

    private boolean showFlag;
    private boolean filteredByDialFlag;
    private boolean filteredByBrandFlag;
    private boolean filteredByDiscountFlag;
    private boolean filteredByStrapFlag;
    private boolean filteredByDisplayFlag;
    private boolean filteredByDialColorFlag;
    private boolean filteredByStrapColorFlag;

    public ProductItem(ChicManUI eventHandler, Product product) {
        this.eventHandler = eventHandler;
        this.product = product;
        this.productImg = new Image();
        this.likeBtn = new Button();

        init();
        setEvent();
        setFavourite2ProductItem();
    }

    private void setFavourite2ProductItem() {
        likeBtn.removeStyleName("like-btn-active");
        for (Product favouritItem: eventHandler.getSessionFavourite().getFavouriteList()) {
            if (favouritItem.getProductId().intValue() == product.getProductId().intValue()) {
                likeBtn.addStyleName("like-btn-active");
            }
        }
    }

    private void init() {
        setStyleName("product_card_item");

        StreamResource.StreamSource imageSource = new Utils.ImageSource(product.getImage());
        imgResource = new StreamResource(imageSource, "product_" + product.getProductId() + ".png");
        productImg.setSource(imgResource);
        productImg.setWidth("300px");
        addComponent(productImg);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthUndefined();
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        Label productDetails = new Label();
        productDetails.setValue(product.getProductDetails());
        productDetails.addStyleName("item_details");
        productDetails.setWidth("300px");
        verticalLayout.addComponent(productDetails);

        CssLayout priceContainer = new CssLayout();
        verticalLayout.addComponent(priceContainer);

        Label productPrice = new Label();
        productPrice.addStyleName("card_item_price");
        productPrice.setValue(Messages.get("price.currency") + Utils.float2Text(product.getProductPrice()));
        if (product.getProductDiscount() > 0) {
            priceContainer.addComponent(productPrice);
        }

        Label productPriceSale = new Label();
        productPriceSale.addStyleName("card_item_price_sale");
        productPriceSale.setValue(Messages.get("price.currency") + Utils.float2Text(product.getProductPriceSale()));
        priceContainer.addComponent(productPriceSale);

        Label productDiscount = new Label();
        productDiscount.addStyleName("card_item_discount");
        productDiscount.setValue(Utils.float2Text(product.getProductDiscount()) + "% off");
        if (product.getProductDiscount() > 0) {
            priceContainer.addComponent(productDiscount);
        }

        HorizontalLayout ratingContainer = new HorizontalLayout();
        ratingContainer.addStyleName("rating_container");
        ratingContainer.setWidth("100%");
        ratingContainer.setMargin(false);
        ratingContainer.setSpacing(false);
        verticalLayout.addComponent(ratingContainer);

        Label productRate = new Label();
        productRate.addStyleName("cardItemRating");
        Utils.ratingClassified(productRate, product);
        ratingContainer.addComponent(productRate);
        ratingContainer.setExpandRatio(productRate, 1);

        likeBtn.addStyleName("borderless");
        likeBtn.addStyleName("icon-only");
        likeBtn.addStyleName("active-noborder");
        likeBtn.addStyleName("like-btn");
        likeBtn.setIcon(VaadinIcons.HEART);
        ratingContainer.addComponent(likeBtn);
    }

    private void setEvent() {
        productImg.addClickListener(e -> eventHandler.exploreProduct(product, imgResource));
        likeBtn.addClickListener(e -> checkLikeBtnActive());
    }

    private void checkLikeBtnActive() {
        if (likeBtn.getStyleName().contains("like-btn-active")) {
            // Move this action to sub-menu of user menu.
//            likeBtn.removeStyleName("like-btn-active");
//            eventHandler.cancelFavouriteProduct(product);
        } else {
            likeBtn.addStyleName("like-btn-active");

            for (Product favouriteItem: eventHandler.getSessionFavourite().getFavouriteList()) {
                if (favouriteItem.getProductId().intValue() == product.getProductId().intValue()) {
                    return;
                }
            }

            eventHandler.favouriteProduct(product);
        }
    }

    public void hideItem() {
        addStyleNames("hide_card");
    }

    public void showItem() {
        removeStyleName("hide_card");
    }

    public boolean isShow() {
        return showFlag;
    }

    public void setShow(boolean showFlag) {
        this.showFlag = showFlag;
    }

    public boolean isFilteredByDial() {
        return filteredByDialFlag;
    }

    public void setFilteredByDial(boolean filteredByDialFlag) {
        this.filteredByDialFlag = filteredByDialFlag;
    }

    public boolean isFilteredByBrand() {
        return filteredByBrandFlag;
    }

    public void setFilteredByBrand(boolean filteredByBrandFlag) {
        this.filteredByBrandFlag = filteredByBrandFlag;
    }

    public boolean isFilteredByDiscount() {
        return filteredByDiscountFlag;
    }

    public void setFilteredByDiscount(boolean filteredByDiscountFlag) {
        this.filteredByDiscountFlag = filteredByDiscountFlag;
    }

    public boolean isFilteredByStrap() {
        return filteredByStrapFlag;
    }

    public void setFilteredByStrap(boolean filteredByStrapFlag) {
        this.filteredByStrapFlag = filteredByStrapFlag;
    }

    public boolean isFilteredByDisplay() {
        return filteredByDisplayFlag;
    }

    public void setFilteredByDisplay(boolean filteredByDisplayFlag) {
        this.filteredByDisplayFlag = filteredByDisplayFlag;
    }

    public boolean isFilteredByDialColor() {
        return filteredByDialColorFlag;
    }

    public void setFilteredByDialColor(boolean filteredByDialColorFlag) {
        this.filteredByDialColorFlag = filteredByDialColorFlag;
    }

    public boolean isFilteredByStrapColor() {
        return filteredByStrapColorFlag;
    }

    public void setFilteredByStrapColor(boolean filteredByStrapColorFlag) {
        this.filteredByStrapColorFlag = filteredByStrapColorFlag;
    }

    public Product getProduct() {
        return product;
    }

    public Button getLikeBtn() {
        return likeBtn;
    }
}
