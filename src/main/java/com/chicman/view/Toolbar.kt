package com.chicman.view

import com.chicman.ChicManUI
import com.chicman.model.Member
import com.chicman.utility.Messages
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.Page
import com.vaadin.server.ThemeResource
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*

@SpringComponent
@UIScope
class Toolbar(private val eventHandler: ChicManUI) : HorizontalLayout() {

    private val logo: Image = Image()
    private val siteName: Button = Button()
    private val btnCart: Button = Button()
    private val btnLogin: Button = Button()
    private val userMenu: MenuBar = MenuBar()
    private val topMenu: MenuBar = MenuBar()
    private val btnWatches: Button = Button()
    private val btnMen: Button = Button()
    private val btnWomen: Button = Button()
    private val btnHome: Button = Button()

    private lateinit var loggedUser: MenuBar.MenuItem

    fun setLoginStatusToBtn(loggedIn: Boolean, activeUser: Member?) {
        if (loggedIn) {
            btnLogin.isVisible = false
            userMenu.isVisible = true
            loggedUser.text = null
            topMenu.isVisible = activeUser?.type != null && activeUser.type == "A"
        } else {
            btnLogin.isVisible = true
            topMenu.isVisible = false
            userMenu.isVisible = false
        }
    }

    fun setTextToAllComponent() {
        siteName.caption = Messages.get("site.name")
        btnLogin.caption = Messages.get("auth.login")
        btnWatches.caption = Messages.get("page.watches.all")
        btnMen.caption = Messages.get("page.men")
        btnWomen.caption = Messages.get("page.women")
        btnCart.caption = Messages.get("cart")
        btnHome.caption = Messages.get("page.home")
    }

    fun updateCartItemNumber(number: Int) {
        Page.getCurrent().javaScript.execute("showCartItem($number)")
    }

    fun clickHome() {
        Page.getCurrent().ui.navigator.navigateTo(HomeView.NAME)
        clearActiveMenuStyle()
        setActiveMenuStyle(btnHome)
    }

    private fun setupLogo() {
        val groupLogo = HorizontalLayout()
        addComponent(groupLogo)
        groupLogo.setWidthUndefined()
        setComponentAlignment(groupLogo, Alignment.MIDDLE_LEFT)
        logo.source = ThemeResource("images/icon_chicman.png")
        logo.setWidth("50px")
        logo.setHeight("50px")
        logo.id = "logo"
        groupLogo.addComponent(logo)
        groupLogo.setComponentAlignment(logo, Alignment.MIDDLE_LEFT)
        siteName.id = "siteName"
        siteName.styleName = "borderless"
        groupLogo.addComponent(siteName)
        groupLogo.setComponentAlignment(siteName, Alignment.MIDDLE_LEFT)
    }

    private fun setupMenuBar() {
        val groupBtn = HorizontalLayout()
        addComponentsAndExpand(groupBtn)
        setComponentAlignment(groupBtn, Alignment.MIDDLE_RIGHT)
        setupHomeBtn()
        setupAdminMenu()
        setupWatchesBtn()
        setupMenWatchesBtn()
        setupWomenWatchesBtn()
        setupCartBtn()
        setupLoginBtn()
        setupUserMenu()
        groupBtn.addComponent(btnHome)
        groupBtn.setExpandRatio(btnHome, 1f)
        groupBtn.setComponentAlignment(btnHome, Alignment.MIDDLE_RIGHT)
        groupBtn.addComponent(topMenu)
        groupBtn.addComponent(btnWatches)
        groupBtn.addComponent(btnMen)
        groupBtn.addComponent(btnWomen)
        groupBtn.addComponent(btnCart)
        groupBtn.addComponent(btnLogin)
        groupBtn.addComponent(userMenu)
        groupBtn.setMargin(false)
    }

    private fun setupUserMenu() {
        userMenu.styleName = "borderless"
        userMenu.setWidth("125px")
        userMenu.id = "userMenu"
        loggedUser = userMenu.addItem("", VaadinIcons.USER, null)
        loggedUser.addItem("Profiles", VaadinIcons.FILE_TEXT_O, null)
        loggedUser.addItem("Favourites",
            VaadinIcons.HEART_O,
            MenuBar.Command { Page.getCurrent().ui.navigator.navigateTo(FavouriteView.NAME) })
        loggedUser.addSeparator()
        loggedUser.addItem(Messages.get("auth.logout"),
            VaadinIcons.SIGN_OUT,
            MenuBar.Command {
                eventHandler.logout()
                clickHome()
            })
        userMenu.isAutoOpen = true
    }

    private fun setupAdminMenu() {
        topMenu.styleName = "borderless"
        val admin = topMenu.addItem("Admin", null, null)
        admin.addItem("Add Products", VaadinIcons.PLUS, MenuBar.Command { eventHandler.showAddProductsForm() })
    }

    private fun setupHomeBtn() {
        btnHome.styleName = "borderless"
        btnHome.addStyleName("active-noborder")
        btnHome.addStyleName("active_menu")
        btnHome.id = "womenBtn"
    }

    private fun setupWomenWatchesBtn() {
        btnWomen.styleName = "borderless"
        btnWomen.addStyleName("colored-when-hover")
        btnWomen.addStyleName("active-noborder")
        btnWomen.id = "womenBtn"
    }

    private fun setupMenWatchesBtn() {
        btnMen.styleName = "borderless"
        btnMen.addStyleName("colored-when-hover")
        btnMen.addStyleName("active-noborder")
        btnMen.id = "menBtn"
    }

    private fun setupWatchesBtn() {
        btnWatches.styleName = "borderless"
        btnWatches.addStyleName("colored-when-hover")
        btnWatches.addStyleName("active-noborder")
        btnWatches.id = "watchesBtn"
    }

    private fun setupCartBtn() {
        btnCart.icon = VaadinIcons.CART
        btnCart.styleName = "borderless"
        btnCart.addStyleName("icon-align-right")
        btnCart.addStyleName("colored-when-hover")
        btnCart.addStyleName("active-noborder")
        btnCart.id = "cart"
    }

    private fun setupLoginBtn() {
        btnLogin.icon = VaadinIcons.USER
        btnLogin.styleName = "borderless"
        btnLogin.addStyleName("icon-align-right")
        btnLogin.addStyleName("colored-when-hover")
        btnLogin.addStyleName("active-noborder")
        btnLogin.id = "loginBtn"
        btnLogin.setWidth("125px")
    }

    private fun init() {
        isSpacing = false
        setupLogo()
        setupMenuBar()
        if (eventHandler.currentUser != null) {
            setLoginStatusToBtn(true, eventHandler.currentUser)
        } else {
            setLoginStatusToBtn(false, null)
        }
    }

    private fun setEvent() {
        logo.addClickListener { eventHandler.showHomePage() }
        siteName.addClickListener { eventHandler.showHomePage() }
        btnHome.addClickListener { eventHandler.showHomePage() }
        btnCart.addClickListener { eventHandler.showCartListInSideWindow() }
        btnLogin.addClickListener { eventHandler.showLoginPopup() }
        btnWatches.addClickListener {
            eventHandler.sideWindow!!.close()
            Page.getCurrent().ui.navigator.navigateTo(WatchesView.NAME)
            setMenuAllWatchesActive()
        }
        btnMen.addClickListener {
            eventHandler.sideWindow!!.close()
            Page.getCurrent().ui.navigator.navigateTo(MenWatchesView.NAME)
            setMenuMenWatchesActive()
        }
        btnWomen.addClickListener {
            eventHandler.sideWindow!!.close()
            Page.getCurrent().ui.navigator.navigateTo(WomenWatchesView.NAME)
            setMenuWomenWatchesActive()
        }
    }

    private fun clearActiveMenuStyle() {
        btnHome.removeStyleName("active_menu")
        btnHome.addStyleName("colored-when-hover")
        btnWatches.removeStyleName("active_menu")
        btnWatches.addStyleName("colored-when-hover")
        btnMen.removeStyleName("active_menu")
        btnMen.addStyleName("colored-when-hover")
        btnWomen.removeStyleName("active_menu")
        btnWomen.addStyleName("colored-when-hover")
    }

    private fun setActiveMenuStyle(activeMenu: Button) {
        activeMenu.addStyleName("active_menu")
        activeMenu.removeStyleName("colored-when-hover")
    }

    fun setMenuAllWatchesActive() {
        clearActiveMenuStyle()
        setActiveMenuStyle(btnWatches)
    }

    fun setMenuWomenWatchesActive() {
        clearActiveMenuStyle()
        setActiveMenuStyle(btnWomen)
    }

    fun setMenuMenWatchesActive() {
        clearActiveMenuStyle()
        setActiveMenuStyle(btnMen)
    }

    fun clickWatchesWithDiscount() {
        btnWatches.click()
        (Page.getCurrent().ui.navigator.currentView as WatchesView).sortItemByDiscount()
        btnWatches.focus()
    }

    fun clickMen() {
        btnMen.click()
        btnMen.focus()
    }

    fun clickWomen() {
        btnWomen.click()
        btnWomen.focus()
    }

    fun clickWatches() {
        btnWatches.click()
        btnWatches.focus()
    }

    fun clickFavourite() {
        clearActiveMenuStyle()
        //        userMenu.focus();
    }

    init {
        init()
        setEvent()
    }
}