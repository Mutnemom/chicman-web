package com.chicman.view.cart;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Utils;
import com.chicman.view.CartItemDesign;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Notification;
import org.springframework.transaction.annotation.Transactional;

class CartItem extends CartItemDesign {

    private final ChicManUI eventHandler;
    private final Product product;

    CartItem(ChicManUI eventHandler, Product product) {
        this.eventHandler = eventHandler;
        this.product = product;

        init();
        setEvent();
    }

    @Transactional
	void init() {
		StreamResource.StreamSource imageSource = new Utils.ImageSource(product.getImage());
		StreamResource resource = new StreamResource(imageSource, "product_" + product.getProductId() + ".png");

		itemImage.setSource(resource);
		itemImage.setStyleName("imgContent");
		itemImage.setWidth("200px");
		itemDetail.setValue(product.getProductDetails());
		itemPrice.setValue(product.getProductPrice().toString());
	}

	private void setEvent() {
		btnRemove.addClickListener(e -> {
			Notification.show(product.getProductId().toString());
			eventHandler.removeCartList(product);
		});
	}
}
