package com.chicman.view

import com.chicman.ChicManUI
import com.chicman.utility.Messages
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort

@SpringComponent
@UIScope
class HomeView(private val eventHandler: ChicManUI) : VerticalLayout(), View {

    private val log = LoggerFactory.getLogger(HomeView::class.java)
    private val btnShopNow: Button = Button(Messages.get("shop.now"))
    private val btnViewAll: Button = Button(Messages.get("view.all"))
    private val btnMen: Button = Button(Messages.get("page.men"))
    private val btnWomen: Button = Button(Messages.get("page.women"))

    override fun enter(event: ViewChangeEvent) {
        log.info("enter Home view with parameter /" + event.parameters)
    }

    private fun init() {
        setMargin(false)
        isSpacing = false
        addTopHighlightSection()
        addSomeText()
        addSaleHighlightSection()
        addSomeButton()
        addMenAndWomenSection()
    }

    private fun setEvent() {
        btnViewAll.addClickListener { eventHandler.toolbar?.clickWatchesWithDiscount() }
        btnShopNow.addClickListener { eventHandler.toolbar?.clickWatches() }
        btnWomen.addClickListener { eventHandler.toolbar?.clickWomen() }
        btnMen.addClickListener { eventHandler.toolbar?.clickMen() }
    }

    private fun addTopHighlightSection() { // temp used for show scrollbar
        val verticalLayout = VerticalLayout()
        verticalLayout.addStyleName("cover_image")
        addComponent(verticalLayout)
        btnShopNow.styleName = "borderless"
        btnShopNow.addStyleName("active-noborder")
        btnShopNow.addStyleName("shopnow_btn")
        verticalLayout.addComponent(btnShopNow)
    }

    private fun addSomeText() {
        val label = Label(Messages.get("products.sale"))
        label.addStyleName("products_sale_label")
        addComponent(label)
        setComponentAlignment(label, Alignment.BOTTOM_CENTER)
    }

    private fun addSomeButton() {
        btnViewAll.addStyleName("borderless")
        btnViewAll.addStyleName("active-noborder")
        btnViewAll.addStyleName("ghost_btn")
        addComponent(btnViewAll)
        setComponentAlignment(btnViewAll, Alignment.MIDDLE_CENTER)
    }

    private fun addMenAndWomenSection() {
        val horizontalLayout = HorizontalLayout()
        horizontalLayout.addStyleName("men_women")
        horizontalLayout.setMargin(false)
        horizontalLayout.isSpacing = false
        addComponent(horizontalLayout)
        btnMen.addStyleName("borderless")
        btnMen.addStyleName("active-noborder")
        btnMen.addStyleName("men")
        btnWomen.addStyleName("borderless")
        btnWomen.addStyleName("active-noborder")
        btnWomen.addStyleName("women")
        horizontalLayout.addComponents(btnMen, btnWomen)
    }

    private fun addSaleHighlightSection() {
        val cssLayout = CssLayout()
        cssLayout.addStyleName("sale")
        addComponent(cssLayout)
        setComponentAlignment(cssLayout, Alignment.TOP_CENTER)
        val topDiscount = eventHandler.productsRepo
            .findAll(Sort.by(Sort.Direction.DESC, "productDiscount"))

        cssLayout.addComponent(ProductItem(eventHandler, topDiscount[0]))
        cssLayout.addComponent(ProductItem(eventHandler, topDiscount[1]))
        cssLayout.addComponent(ProductItem(eventHandler, topDiscount[2]))
    }

    companion object {
        const val NAME = ""
    }

    init {
        init()
        setEvent()
    }

}
