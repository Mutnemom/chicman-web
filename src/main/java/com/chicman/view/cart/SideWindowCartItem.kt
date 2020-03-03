package com.chicman.view.cart

import com.chicman.ChicManUI
import com.chicman.model.Product
import com.chicman.utility.Messages
import com.chicman.utility.Utils
import com.chicman.utility.Utils.ImageSource
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.StreamResource
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*

@SpringComponent
@UIScope
class SideWindowCartItem : HorizontalLayout {
    private val eventHandler: ChicManUI?
    private val product: Product?
    private val itemImg: Image?
    private val itemName: Label?
    private val itemPrice: Label
    private val btnDelete: Button?

    constructor(eventHandler: ChicManUI?, product: Product?) {
        this.eventHandler = eventHandler
        this.product = product
        itemImg = Image()
        itemName = Label()
        itemPrice = Label()
        btnDelete = Button()
        init()
        setEvent()
    }

    constructor(totalPrice: Float) {
        eventHandler = null
        product = null
        itemImg = null
        itemName = null
        itemPrice = Label()
        btnDelete = null
        if (totalPrice > 0) {
            itemPrice.value = (Messages.get("cart.price.total")
                + "     " + Utils.float2Text(totalPrice)
                + "  " + Messages.get("cart.price.currency"))
        } else {
            itemPrice.value = Messages.get("cart.blank")
        }
        setHeight("40px")
        setWidth("100%")
        addComponent(itemPrice)
        setComponentAlignment(itemPrice, Alignment.BOTTOM_CENTER)
    }

    private fun init() {
        setWidth("95%")
        styleName = "side_cart_item"
        val imageSource: StreamResource.StreamSource = ImageSource(product!!.image)
        val resource = StreamResource(imageSource, "product_" + product.productId + ".png")
        itemImg!!.source = resource
        itemImg.setHeightUndefined()
        itemImg.setWidth("100px")
        addComponent(itemImg)
        setComponentAlignment(itemImg, Alignment.TOP_LEFT)
        val detailsLayout = HorizontalLayout()
        detailsLayout.setWidth("100%")
        addComponent(detailsLayout)
        setExpandRatio(detailsLayout, 1.0f)
        val itemDetails = VerticalLayout()
        itemDetails.setWidth("100%")
        itemDetails.setMargin(false)
        itemDetails.isSpacing = false
        detailsLayout.addComponent(itemDetails)
        detailsLayout.setComponentAlignment(itemDetails, Alignment.TOP_LEFT)
        detailsLayout.setExpandRatio(itemDetails, 1.0f)
        itemName!!.value = product.productBrandId.toString()
        itemDetails.addComponent(itemName)
        itemDetails.setComponentAlignment(itemName, Alignment.TOP_LEFT)
        itemPrice.value = Utils.float2Text(product.productPrice) + " " + Messages.get("cart.price.currency")
        itemPrice.styleName = "bold"
        itemDetails.addComponent(itemPrice)
        itemDetails.setComponentAlignment(itemPrice, Alignment.TOP_LEFT)
        btnDelete!!.styleName = "large"
        btnDelete.setStyleName("borderless-colored", true)
        btnDelete.setStyleName("icon-only", true)
        btnDelete.icon = VaadinIcons.DEL_A
        btnDelete.caption = "delete"
        detailsLayout.addComponent(btnDelete)
        detailsLayout.setComponentAlignment(btnDelete, Alignment.TOP_LEFT)
    }

    private fun setEvent() {
        btnDelete!!.addClickListener { e: Button.ClickEvent? -> eventHandler!!.removeCartList(product) }
    }
}