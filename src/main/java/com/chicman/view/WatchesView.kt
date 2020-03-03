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
import org.springframework.data.domain.Sort

@SpringComponent
@UIScope
class WatchesView(private val eventHandler: ChicManUI) :
    VerticalLayout(),
    View
{
    private val log = LoggerFactory.getLogger(WatchesView::class.java)
    private var productList: List<Product>? = null
    private var comboSortList: ComboBox<String>? = null
    private var cardBtn: Button? = null
    private var listBtn: Button? = null
    private var groupMainCardLayout: CssLayout? = null
    private var isDisplayListStyle = false
    private var inputFilter: Filter? = null

    override fun enter(event: ViewChangeEvent) {
        log.info("enter All Watches view with parameter /" + event.parameters)
        eventHandler.toolbar?.setMenuAllWatchesActive()
        eventHandler.resetFilter()
        inputFilter!!.clearAll()
        sortItemByPopularity()
    }

    fun filter() {
        eventHandler.filterBean.createFilteredProduct(groupMainCardLayout, productList)
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
        val btnWatches =
            Button(Messages.get("page.watches.all"))
        btnWatches.isEnabled = false
        tracking.addTracking(btnWatches)
        subToolbar.addComponent(tracking)
        subToolbar.setComponentAlignment(tracking, Alignment.TOP_LEFT)
        comboSortList = ComboBox()
        comboSortList!!.id = "comboSortList"
        comboSortList!!.styleName = "borderless"
        comboSortList!!.isTextInputAllowed = false
        comboSortList!!.setItems(
            Messages.get("sort.popularity"),
            Messages.get("sort.fresh"),
            Messages.get("sort.discount"),
            Messages.get("sort.price.asc"),
            Messages.get("sort.price.desc")
        )
        comboSortList!!.isEmptySelectionAllowed = false
        comboSortList!!.value = Messages.get("sort.popularity")
        val groupSorting = CssLayout()
        groupSorting.id = "groupSorting"
        val sortTitle =
            Label(Messages.get("sort.title"))
        sortTitle.id = "sortTitle"
        groupSorting.addComponent(sortTitle)
        groupSorting.addComponent(comboSortList)
        // add 2 button to select display format
        cardBtn = Button()
        cardBtn!!.id = "cardBtn"
        cardBtn!!.styleName = "icon-only"
        cardBtn!!.addStyleName("borderless")
        cardBtn!!.addStyleName("inactive_sorting")
        cardBtn!!.addStyleName("active-noborder")
        cardBtn!!.icon = VaadinIcons.GRID_BIG
        listBtn = Button()
        listBtn!!.id = "listBtn"
        listBtn!!.styleName = "icon-only"
        listBtn!!.addStyleName("borderless")
        listBtn!!.addStyleName("inactive_sorting")
        listBtn!!.addStyleName("active-noborder")
        listBtn!!.icon = VaadinIcons.LIST_UL
        groupSorting.addComponents(cardBtn, listBtn)
        subToolbar.addComponent(groupSorting)
        subToolbar.setComponentAlignment(groupSorting, Alignment.TOP_LEFT)
        val filterAndResult = HorizontalLayout()
        filterAndResult.id = "filterAndResult"
        inputFilter = Filter(eventHandler)
        filterAndResult.addComponent(inputFilter)
        val verticalLayout = VerticalLayout()
        verticalLayout.id = "pageNameAndProducts"
        filterAndResult.addComponent(verticalLayout)
        // add page label
        val pageLabel =
            Label(Messages.get("page.watches.all"))
        pageLabel.id = "pageLabel"
        pageLabel.setWidth("100%")
        verticalLayout.addComponent(pageLabel)
        groupMainCardLayout = CssLayout()
        groupMainCardLayout!!.id = "products"
        groupMainCardLayout!!.styleName = "group_main_card"
        verticalLayout.addComponent(groupMainCardLayout)
        addComponent(filterAndResult)
        setComponentAlignment(filterAndResult, Alignment.TOP_CENTER)
        sortItemByPopularity()
    }

    private fun setEvent() {
        cardBtn!!.addClickListener {
            showProductInCardFormat()
            cardBtn!!.addStyleName("active_sorting")
            listBtn!!.removeStyleName("active_sorting")
        }

        listBtn!!.addClickListener {
            showProductInListFormat()
            listBtn!!.addStyleName("active_sorting")
            cardBtn!!.removeStyleName("active_sorting")
        }

        comboSortList!!.addValueChangeListener { changeSortItemCondition(comboSortList!!.value) }
    }

    private fun showProductInCardFormat() {
        for (i in 0 until groupMainCardLayout!!.componentCount) {
            if (groupMainCardLayout!!.getComponent(i) is ProductItem) {
                val productItem = groupMainCardLayout!!.getComponent(i) as ProductItem
                productItem.removeStyleName("product_list_item")
            }
        }
        isDisplayListStyle = false
    }

    private fun showProductInListFormat() {
        for (i in 0 until groupMainCardLayout!!.componentCount) {
            if (groupMainCardLayout!!.getComponent(i) is ProductItem) {
                val productItem = groupMainCardLayout!!.getComponent(i) as ProductItem
                productItem.addStyleName("product_list_item")
            }
        }
        isDisplayListStyle = true
    }

    private fun changeSortItemCondition(sortCondition: String) {
        when (sortCondition) {
            Messages.get("sort.popularity") -> sortItemByPopularity()
            Messages.get("sort.price.desc") -> sortItemByPriceDesc()
            Messages.get("sort.price.asc") -> sortItemByPriceAsc()
            Messages.get("sort.discount") -> sortItemByDiscount()
            Messages.get("sort.fresh") -> sortItemByFresh()
        }
    }

    private fun sortItemByPopularity() {
        productList = eventHandler.productsRepo
            .findAll(Sort.by(Sort.Direction.DESC, "productRate"))

        comboSortList!!.value = Messages.get("sort.popularity")
        refreshProductItem()
    }

    private fun sortItemByFresh() {
        productList = eventHandler.productsRepo
            .findAll(Sort.by(Sort.Direction.DESC, "submission"))

        comboSortList!!.value = Messages.get("sort.fresh")
        refreshProductItem()
    }

    fun sortItemByDiscount() {
        productList = eventHandler.productsRepo
            .findAll(Sort.by(Sort.Direction.DESC, "productDiscount"))

        comboSortList!!.value = Messages.get("sort.discount")
        refreshProductItem()
    }

    private fun sortItemByPriceAsc() {
        productList = eventHandler.productsRepo
            .findAll(Sort.by(Sort.Direction.ASC, "productPriceSale"))

        comboSortList!!.value = Messages.get("sort.price.asc")
        refreshProductItem()
    }

    private fun sortItemByPriceDesc() {
        productList = eventHandler.productsRepo
            .findAll(Sort.by(Sort.Direction.DESC, "productPriceSale"))

        comboSortList!!.value = Messages.get("sort.price.desc")
        refreshProductItem()
    }

    private fun refreshProductItem() {
        groupMainCardLayout!!.removeAllComponents()
        var productItem: ProductItem
        for (product in productList!!) {
            productItem = ProductItem(eventHandler, product)
            if (isDisplayListStyle) {
                productItem.addStyleName("product_list_item")
            }
            groupMainCardLayout!!.addComponent(productItem)
        }
        filter()
    }

    fun updateFavourite(favouriteList: List<Product>) {
        var component: Component?
        var productItem: ProductItem
        for (i in 0 until groupMainCardLayout!!.componentCount) {
            component = groupMainCardLayout!!.getComponent(i)
            if (component is ProductItem) {
                productItem = component
                for (favouriteItem in favouriteList) {
                    if (favouriteItem.productId.toInt() == productItem.product.productId.toInt()) {
                        productItem.likeBtn.click()
                    }
                }
            }
        }
    }

    companion object {
        const val NAME = "watches"
    }

    init {
        init()
        setEvent()
        cardBtn!!.click()
    }

}
