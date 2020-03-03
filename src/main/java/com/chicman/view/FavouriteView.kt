package com.chicman.view

import com.chicman.ChicManUI
import com.chicman.model.Product
import com.chicman.utility.Messages
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*
import org.slf4j.LoggerFactory

@SpringComponent
@UIScope
class FavouriteView(private val eventHandler: ChicManUI) : VerticalLayout(), View {

    private val log = LoggerFactory.getLogger(FavouriteView::class.java)

    private var productList: List<Product?>? = null
    private var comboSortList: ComboBox<String>? = null
    private var groupMainCardLayout: CssLayout? = null

    override fun enter(event: ViewChangeEvent) {
        if (eventHandler.currentUser == null) {
            eventHandler.toolbar?.clickHome()
        } else {
            addFavouriteProduct()
            eventHandler.toolbar?.clickFavourite()
        }
    }

    private fun init() {
        id = "main"
        isSpacing = false
        // under toolbar
        val subToolbar = HorizontalLayout()
        subToolbar.id = "subToolbar"
        subToolbar.margin = MarginInfo(true, false, false, false)
        subToolbar.isSpacing = false
        addComponent(subToolbar)
        setComponentAlignment(subToolbar, Alignment.TOP_CENTER)
        // navigation tracking
        val tracking = NavigationTracking(eventHandler)
        val btnFavourite = Button(Messages.get("page.favourite"))
        btnFavourite.isEnabled = false
        tracking.addTracking(btnFavourite)
        subToolbar.addComponent(tracking)
        subToolbar.setComponentAlignment(tracking, Alignment.TOP_LEFT)
        comboSortList = ComboBox()
        comboSortList!!.id = "comboSortList"
        comboSortList!!.styleName = "borderless"
        comboSortList!!.isTextInputAllowed = false
        comboSortList!!.setItems(Messages.get("sort.popularity"),
                Messages.get("sort.fresh"),
                Messages.get("sort.discount"),
                Messages.get("sort.price.asc"),
                Messages.get("sort.price.desc"))
        comboSortList!!.isEmptySelectionAllowed = false
        comboSortList!!.value = Messages.get("sort.popularity")
        val groupSorting = CssLayout()
        groupSorting.id = "groupSorting"
        val sortTitle = Label(Messages.get("sort.title"))
        sortTitle.id = "sortTitle"
        groupSorting.addComponent(sortTitle)
        groupSorting.addComponent(comboSortList)
        // add 2 button to select display format
        val cardBtn = Button()
        cardBtn.id = "cardBtn"
        cardBtn.styleName = "icon-only"
        cardBtn.addStyleName("borderless")
        cardBtn.addStyleName("inactive_sorting")
        cardBtn.addStyleName("active-noborder")
        cardBtn.icon = VaadinIcons.GRID_BIG
        val listBtn = Button()
        listBtn.id = "listBtn"
        listBtn.styleName = "icon-only"
        listBtn.addStyleName("borderless")
        listBtn.addStyleName("inactive_sorting")
        listBtn.addStyleName("active-noborder")
        listBtn.icon = VaadinIcons.LIST_UL
        groupSorting.addComponents(cardBtn, listBtn)
        subToolbar.addComponent(groupSorting)
        subToolbar.setComponentAlignment(groupSorting, Alignment.TOP_LEFT)
        val filterAndResult = HorizontalLayout()
        filterAndResult.id = "filterAndResult"
        val inputFilter = Filter(eventHandler)
        filterAndResult.addComponent(inputFilter)
        val verticalLayout = VerticalLayout()
        verticalLayout.id = "pageNameAndProducts"
        filterAndResult.addComponent(verticalLayout)
        // add page label
        val pageLabel = Label(Messages.get("page.favourite"))
        pageLabel.id = "pageLabel"
        pageLabel.setWidth("100%")
        verticalLayout.addComponent(pageLabel)
        groupMainCardLayout = CssLayout()
        groupMainCardLayout!!.id = "products"
        groupMainCardLayout!!.styleName = "group_main_card"
        verticalLayout.addComponent(groupMainCardLayout)
        addComponent(filterAndResult)
        setComponentAlignment(filterAndResult, Alignment.TOP_CENTER)
        addFavouriteProduct()
    }

    private fun addFavouriteProduct() {
        productList = eventHandler.sessionFavourite.favourites
        comboSortList!!.value = Messages.get("sort.popularity")
        refreshProductItem()
    }

    private fun refreshProductItem() {
        groupMainCardLayout!!.removeAllComponents()
        if (productList == null) return
        var productItem: ProductItem
        for (product in productList!!) {
            productItem = ProductItem(eventHandler, product!!)
            groupMainCardLayout!!.addComponent(productItem)
        }
    }

    companion object {
        const val NAME = "favourite"
    }

    init {
        init()
    }

}
