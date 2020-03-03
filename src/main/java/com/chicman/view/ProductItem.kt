package com.chicman.view

import com.chicman.ChicManUI
import com.chicman.model.Product
import com.chicman.utility.Messages
import com.chicman.utility.Utils
import com.chicman.utility.Utils.ImageSource
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.StreamResource
import com.vaadin.ui.*
import org.slf4j.LoggerFactory

class ProductItem(private val eventHandler: ChicManUI, val product: Product) : CssLayout() {

    private val log = LoggerFactory.getLogger(ProductItem::class.java)

    private val productImg: Image = Image()
    private var imgResource: StreamResource? = null
    val likeBtn: Button = Button()

    var isShow = false
    var isFilteredByDial = false
    var isFilteredByBrand = false
    var isFilteredByDiscount = false
    var isFilteredByStrap = false
    var isFilteredByDisplay = false
    var isFilteredByDialColor = false
    var isFilteredByStrapColor = false

    private fun setFavourite2ProductItem() {
        likeBtn.removeStyleName("like-btn-active")
        eventHandler.sessionFavourite.favourites.forEach {
            if (it.productId.toInt() == product.productId.toInt()) {
                likeBtn.addStyleName("like-btn-active")
            }
        }
    }

    private fun init() {
        styleName = "product_card_item"
        val imageSource: StreamResource.StreamSource = ImageSource(product.image)
        imgResource = StreamResource(imageSource, "product_" + product.productId + ".png")
        productImg.source = imgResource
        productImg.setWidth("300px")
        addComponent(productImg)
        val verticalLayout = VerticalLayout()
        verticalLayout.setWidthUndefined()
        verticalLayout.setMargin(false)
        addComponent(verticalLayout)
        val productDetails = Label()
        productDetails.value = product.productDetails
        productDetails.addStyleName("item_details")
        productDetails.setWidth("300px")
        verticalLayout.addComponent(productDetails)
        val priceContainer = CssLayout()
        verticalLayout.addComponent(priceContainer)
        val productPrice = Label()
        productPrice.addStyleName("card_item_price")
        productPrice.value = Messages.get("price.currency") + Utils.float2Text(product.productPrice)
        if (product.productDiscount > 0) {
            priceContainer.addComponent(productPrice)
        }
        val productPriceSale = Label()
        productPriceSale.addStyleName("card_item_price_sale")
        productPriceSale.value = Messages.get("price.currency") + Utils.float2Text(product.productPriceSale)
        priceContainer.addComponent(productPriceSale)
        val productDiscount = Label()
        productDiscount.addStyleName("card_item_discount")
        productDiscount.value = Utils.float2Text(product.productDiscount) + "% off"
        if (product.productDiscount > 0) {
            priceContainer.addComponent(productDiscount)
        }
        val ratingContainer = HorizontalLayout()
        ratingContainer.addStyleName("rating_container")
        ratingContainer.setWidth("100%")
        ratingContainer.setMargin(false)
        ratingContainer.isSpacing = false
        verticalLayout.addComponent(ratingContainer)
        val productRate = Label()
        productRate.addStyleName("cardItemRating")
        Utils.ratingClassified(productRate, product)
        ratingContainer.addComponent(productRate)
        ratingContainer.setExpandRatio(productRate, 1f)
        likeBtn.addStyleName("borderless")
        likeBtn.addStyleName("icon-only")
        likeBtn.addStyleName("active-noborder")
        likeBtn.addStyleName("like-btn")
        likeBtn.icon = VaadinIcons.HEART
        ratingContainer.addComponent(likeBtn)
    }

    private fun setEvent() {
        productImg.addClickListener { eventHandler.exploreProduct(product, imgResource) }
        likeBtn.addClickListener { checkLikeBtnActive() }
    }

    private fun checkLikeBtnActive() {
//        if (likeBtn.styleName.contains("like-btn-active")) {
            // Move this action to sub-menu of user menu.
//            likeBtn.removeStyleName("like-btn-active");
//            eventHandler.cancelFavouriteProduct(product);
//        } else {

        if (!likeBtn.styleName.contains("like-btn-active")) {
            likeBtn.addStyleName("like-btn-active")
            for (favouriteItem in eventHandler.sessionFavourite.favourites) {
                if (favouriteItem.productId.toInt() == product.productId.toInt()) {
                    return
                }
            }
            eventHandler.favouriteProduct(product)
        }
    }

    fun hideItem() {
        addStyleNames("hide_card")
    }

    fun showItem() {
        removeStyleName("hide_card")
    }

    init {
        init()
        setEvent()
        setFavourite2ProductItem()
    }
}