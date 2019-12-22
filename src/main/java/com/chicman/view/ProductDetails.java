package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@SpringComponent
@UIScope
public class ProductDetails extends VerticalLayout {

    private final ChicManUI eventHandler;
    private Product product;
    private final Button btnAdd;

    public ProductDetails(ChicManUI eventHandler, Product product, StreamResource imgSource) {
        this.eventHandler = eventHandler;
        this.product = product;
        Image imageProduct = new Image();
        Label labelDetails = new Label();
        Label labelPrice = new Label();
        this.btnAdd = new Button();

        setHeightUndefined();
        setWidthUndefined();

        imageProduct.setSource(imgSource);
        imageProduct.setWidth("400px");
        addComponent(imageProduct);
        setComponentAlignment(imageProduct, Alignment.TOP_LEFT);

        labelDetails.setWidth("350px");
        labelDetails.setValue(product.getProductDetails());
        addComponent(labelDetails);
        setComponentAlignment(labelDetails, Alignment.TOP_LEFT);

        HorizontalLayout horizontal1 = new HorizontalLayout();
        horizontal1.setSpacing(true);
        horizontal1.setMargin(false);
        horizontal1.setWidthUndefined();
        addComponent(horizontal1);
        setComponentAlignment(horizontal1, Alignment.TOP_LEFT);
        horizontal1.addComponent(new Label("ราคา"));

        HorizontalLayout horizontal2 = new HorizontalLayout();
        horizontal1.setSpacing(true);
        horizontal1.setMargin(false);
        horizontal1.setHeightUndefined();
        horizontal1.setWidthUndefined();
        horizontal1.addComponent(horizontal2);
        horizontal1.setComponentAlignment(horizontal2, Alignment.TOP_LEFT);

        labelPrice.setValue(Utils.float2Text(product.getProductPrice()));
        labelPrice.setWidthUndefined();
        horizontal2.addComponent(labelPrice);
        horizontal2.setComponentAlignment(labelPrice, Alignment.TOP_LEFT);
        horizontal2.addComponent(new Label(Messages.get("cart.price.currency")));

        btnAdd.setCaption(Messages.get("product.addtocart"));
        btnAdd.setStyleName("primary");
        btnAdd.setStyleName("icon-align-right", true);
        btnAdd.setIcon(VaadinIcons.CART_O);
        btnAdd.setWidth("400px");
        addComponent(btnAdd);
        setComponentAlignment(btnAdd, Alignment.MIDDLE_LEFT);
        btnAdd.focus();
        btnAdd.addBlurListener(e -> Page.getCurrent().getJavaScript().execute("getFocused()"));

        setStyleName("card_product_details");
        setEvent();
    }

    private void setEvent() {
        btnAdd.addClickListener(e -> eventHandler.addItemToCart(product));
    }
}

