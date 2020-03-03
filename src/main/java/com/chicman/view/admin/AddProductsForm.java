package com.chicman.view.admin;

import com.chicman.ChicManUI;
import com.chicman.utility.ImageUploader;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@SpringComponent
@UIScope
public class AddProductsForm extends VerticalLayout {

    private final ChicManUI eventHandler;
    private final TextField productName;
    private final TextField productDetails;
    private final TextField productPrice;
    private final Image productImage;
    private final Upload uploadImage;
    private final Button addBtn;
    private final RadioButtonGroup<String> groupDialType;

    public AddProductsForm(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;
        this.productName = new TextField("Product Name");
        this.productDetails = new TextField("Product Details");
        this.productPrice = new TextField("Product Price");
        this.productImage = new Image("Product Image");
        this.uploadImage = new Upload();
        this.addBtn = new Button("Add");
        this.groupDialType = new RadioButtonGroup<>();

        setupComponent();
        setEvent();
    }

    private void setupComponent() {
        addComponent(productName);
        addComponent(productDetails);
        addComponent(productPrice);

        productImage.setWidth("400px");
        addComponent(productImage);

        ImageUploader receiver = new ImageUploader(eventHandler, productImage);
        uploadImage.setReceiver(receiver);
        uploadImage.setImmediateMode(false);
        uploadImage.addSucceededListener(receiver);
        addComponent(uploadImage);

        groupDialType.setCaption("Dial type");
        groupDialType.setItems("Circle", "Square");
        groupDialType.setSelectedItem("Circle");
        addComponent(groupDialType);

        addComponent(addBtn);
    }

    private void setEvent() {
        addBtn.addClickListener(e -> {
            if (eventHandler.addNewProduct(productName.getValue(),
                    productDetails.getValue(),
                    productPrice.getValue(),
                    groupDialType.getSelectedItem().map(s -> s.substring(0, 1)).orElse("null"))) {
                productName.clear();
                productDetails.clear();
                productPrice.clear();
                productImage.setSource(null);
                groupDialType.setSelectedItem("Circle");
            }
        });
    }

}
