package com.chicman.view.cart;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@SpringComponent
@UIScope
public class SideWindowCartItem extends HorizontalLayout {

    private final ChicManUI eventHandler;
    private final Product product;
    private final Image itemImg;
    private final Label itemName;
    private final Label itemPrice;
    private final Button btnDelete;

    public SideWindowCartItem(ChicManUI eventHandler, Product product) {
        this.eventHandler = eventHandler;
        this.product = product;
        this.itemImg = new Image();
        this.itemName = new Label();
        this.itemPrice = new Label();
        this.btnDelete = new Button();

        init();
        setEvent();
    }

    public SideWindowCartItem(float totalPrice) {
        this.eventHandler = null;
        this.product = null;
        this.itemImg = null;
        this.itemName = null;
        this.itemPrice = new Label();
        this.btnDelete = null;

        if (totalPrice > 0) {
            itemPrice.setValue(Messages.get("cart.price.total")
                    + "     " + Utils.float2Text(totalPrice)
                    + "  " + Messages.get("cart.price.currency"));
        } else {
            itemPrice.setValue(Messages.get("cart.blank"));
        }

        setHeight("40px");
        setWidth("100%");
        addComponent(itemPrice);
        setComponentAlignment(itemPrice, Alignment.BOTTOM_CENTER);
    }

    private void init() {
        setWidth("95%");
        setStyleName("side_cart_item");

        StreamResource.StreamSource imageSource = new Utils.ImageSource(product.getImage());
        StreamResource resource = new StreamResource(imageSource, "product_" + product.getProductId() + ".png");
        itemImg.setSource(resource);
        itemImg.setHeightUndefined();
        itemImg.setWidth("100px");
        addComponent(itemImg);
        setComponentAlignment(itemImg, Alignment.TOP_LEFT);

        HorizontalLayout detailsLayout = new HorizontalLayout();
        detailsLayout.setWidth("100%");
        addComponent(detailsLayout);
        setExpandRatio(detailsLayout, 1.0f);

        VerticalLayout itemDetails = new VerticalLayout();
        itemDetails.setWidth("100%");
        itemDetails.setMargin(false);
        itemDetails.setSpacing(false);
        detailsLayout.addComponent(itemDetails);
        detailsLayout.setComponentAlignment(itemDetails, Alignment.TOP_LEFT);
        detailsLayout.setExpandRatio(itemDetails, 1.0f);

        itemName.setValue(product.getProductBrandId().toString());
        itemDetails.addComponent(itemName);
        itemDetails.setComponentAlignment(itemName, Alignment.TOP_LEFT);

        itemPrice.setValue(Utils.float2Text(product.getProductPrice()) + " " + Messages.get("cart.price.currency"));
        itemPrice.setStyleName("bold");
        itemDetails.addComponent(itemPrice);
        itemDetails.setComponentAlignment(itemPrice, Alignment.TOP_LEFT);

        btnDelete.setStyleName("large");
        btnDelete.setStyleName("borderless-colored", true);
        btnDelete.setStyleName("icon-only", true);
        btnDelete.setIcon(VaadinIcons.DEL_A);
        btnDelete.setCaption("delete");
        detailsLayout.addComponent(btnDelete);
        detailsLayout.setComponentAlignment(btnDelete, Alignment.TOP_LEFT);
    }

    private void setEvent() {
        btnDelete.addClickListener(e -> eventHandler.removeCartList(product));
    }
}
