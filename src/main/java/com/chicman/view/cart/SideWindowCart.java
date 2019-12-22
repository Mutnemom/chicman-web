package com.chicman.view.cart;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import java.util.List;

@SpringComponent
@UIScope
public class SideWindowCart extends VerticalLayout {

    private final ChicManUI eventHandler;
    private List<Product> productList;
    private final Button btnCheckout;
    private Button btnClose;
    private float totalPrice;

    public SideWindowCart(ChicManUI eventHandler, List<Product> productList) {
        this.eventHandler = eventHandler;
        this.productList = productList;
        this.btnCheckout = new Button();
        this.totalPrice = 0.0f;

        init();
        setEvent();
    }

    private void init() {
        setHeight("100%");
        setId("sideWindow");
        setSpacing(false);

        VerticalLayout productPart = new VerticalLayout();
        productPart.setMargin(false);
        productPart.setHeightUndefined();
        productPart.setId("productPart");

        VerticalLayout checkoutPart = new VerticalLayout();
        checkoutPart.setMargin(false);
        checkoutPart.setSpacing(false);
        checkoutPart.setHeight("100%");
        checkoutPart.setId("checkoutPart");

        addComponent(productPart);
        addComponent(checkoutPart);
        setExpandRatio(checkoutPart, 1.0f);

        Label labelYourCart = new Label(Messages.get("cart.yourcart"));
//        labelYourCart.setId("sideCartTitle");
//        labelYourCart.setStyleName("side_cart_title");
        labelYourCart.setStyleName("large", true);
        if (productList != null && productList.size() != 0) {
            labelYourCart.setStyleName("side_cart_title_border", true);
        }
//        labelYourCart.setWidthUndefined();
//        productPart.addComponent(labelYourCart);

        HorizontalLayout groupTitle = new HorizontalLayout();
        groupTitle.setWidth("100%");
        groupTitle.addComponent(labelYourCart);
        groupTitle.setComponentAlignment(labelYourCart, Alignment.MIDDLE_LEFT);

        btnClose = new Button("x");
        btnClose.setId("btnCloseSideWindow");
        btnClose.setStyleName("borderless");
        btnClose.setStyleName("active-noborder", true);
        btnClose.addClickListener(e -> eventHandler.closeSideWindow());
        groupTitle.addComponent(btnClose);
        groupTitle.setComponentAlignment(btnClose, Alignment.TOP_RIGHT);

        productPart.addComponent(groupTitle);
        productPart.setComponentAlignment(groupTitle, Alignment.TOP_CENTER);

        totalPrice = 0.0f;
        SideWindowCartItem cartItem;

        if (productList != null) {
            for (Product product : productList) {
                cartItem = new SideWindowCartItem(eventHandler, product);
                totalPrice += product.getProductPrice();
                productPart.addComponent(cartItem);
                cartItem.setHeightUndefined();
            }
        }

        productPart.addComponent(new SideWindowCartItem(totalPrice));

        btnCheckout.setWidth("90%");
        btnCheckout.setStyleName("primary");
        if (eventHandler.getCurrentUser() == null) {
            btnCheckout.setCaption(Messages.get("cart.login"));
        } else {
            btnCheckout.setCaption(Messages.get("cart.checkout"));
            btnCheckout.setStyleName("icon-align-right", true);
            btnCheckout.setIcon(VaadinIcons.CREDIT_CARD);
        }
        btnCheckout.setId("checkoutButton");
        btnCheckout.setEnabled(productList != null && productList.size() > 0);
        checkoutPart.addComponent(btnCheckout);
        checkoutPart.setComponentAlignment(btnCheckout, Alignment.BOTTOM_CENTER);

    }

    private void setEvent() {
        btnCheckout.addClickListener(e -> {
            if (eventHandler.getCurrentUser() != null) {
                eventHandler.showCreditCardForm();
            } else {
                eventHandler.showLoginPopup();
            }
        });
    }

    public void updateCartList(List<Product> newCartList) {
        productList = newCartList;
        removeAllComponents();
        init();
    }

    public float getTotalPrice() {
        return totalPrice;
    }

}
