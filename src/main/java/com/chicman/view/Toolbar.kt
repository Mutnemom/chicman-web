package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.model.Customer;
import com.chicman.model.Member;
import com.chicman.utility.Messages;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.MenuItem;

@SpringComponent
@UIScope
public class Toolbar extends HorizontalLayout {

    private final ChicManUI eventHandler;
    private final Image logo;
    private final Button siteName;
    private final Button btnCart;
    private final Button btnLogin;
    private final MenuBar userMenu;
    private final MenuBar topMenu;
    private final Button btnWatches;
    private final Button btnMen;
    private final Button btnWomen;
    private final Button btnHome;
    private MenuItem loggedUser;

    public Toolbar(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;
        this.logo = new Image();
        this.siteName = new Button();
        this.btnCart = new Button();
        this.btnLogin = new Button();
        this.topMenu = new MenuBar();
        this.userMenu = new MenuBar();
        this.btnWatches = new Button();
        this.btnMen = new Button();
        this.btnWomen = new Button();
        this.btnHome = new Button();

        init();
        setEvent();
    }

    public void setLoginStatusToBtn(boolean loggedIn, Member activeUser) {
        if (loggedIn) {
            btnLogin.setVisible(false);
            userMenu.setVisible(true);
            loggedUser.setText(null);

            if (activeUser != null && activeUser.getType() != null && activeUser.getType().equals("A")) {
                topMenu.setVisible(true);
            } else {
                topMenu.setVisible(false);
            }
        } else {
            btnLogin.setVisible(true);
            topMenu.setVisible(false);
            userMenu.setVisible(false);
        }
    }

    public void setTextToAllComponent() {
        siteName.setCaption(Messages.get("site.name"));
        btnLogin.setCaption(Messages.get("auth.login"));
        btnWatches.setCaption(Messages.get("page.watches.all"));
        btnMen.setCaption(Messages.get("page.men"));
        btnWomen.setCaption(Messages.get("page.women"));
        btnCart.setCaption(Messages.get("cart"));
        btnHome.setCaption(Messages.get("page.home"));
    }

    public void updateCartItemNumber(int number) {
        Page.getCurrent().getJavaScript().execute("showCartItem(" + number + ")");
    }

    public void clickHome() {
        Page.getCurrent().getUI().getNavigator().navigateTo(HomeView.NAME);
        clearActiveMenuStyle();
        setActiveMenuStyle(btnHome);
    }

    private void setupLogo() {
        HorizontalLayout groupLogo = new HorizontalLayout();
        addComponent(groupLogo);
        groupLogo.setWidthUndefined();
        setComponentAlignment(groupLogo, Alignment.MIDDLE_LEFT);

        logo.setSource(new ThemeResource("images/icon_chicman.png"));
        logo.setWidth("50px");
        logo.setHeight("50px");
        logo.setId("logo");
        groupLogo.addComponent(logo);
        groupLogo.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);

        siteName.setId("siteName");
        siteName.setStyleName("borderless");
        groupLogo.addComponent(siteName);
        groupLogo.setComponentAlignment(siteName, Alignment.MIDDLE_LEFT);
    }

    private void setupMenuBar() {
        HorizontalLayout groupBtn = new HorizontalLayout();
        addComponentsAndExpand(groupBtn);
        setComponentAlignment(groupBtn, Alignment.MIDDLE_RIGHT);

        setupHomeBtn();
        setupAdminMenu();
        setupWatchesBtn();
        setupMenWatchesBtn();
        setupWomenWatchesBtn();
        setupCartBtn();
        setupLoginBtn();
        setupUserMenu();

        groupBtn.addComponent(btnHome);
        groupBtn.setExpandRatio(btnHome, 1);
        groupBtn.setComponentAlignment(btnHome, Alignment.MIDDLE_RIGHT);
        groupBtn.addComponent(topMenu);
        groupBtn.addComponent(btnWatches);
        groupBtn.addComponent(btnMen);
        groupBtn.addComponent(btnWomen);
        groupBtn.addComponent(btnCart);
        groupBtn.addComponent(btnLogin);
        groupBtn.addComponent(userMenu);
        groupBtn.setMargin(false);
    }

    private void setupUserMenu() {
        userMenu.setStyleName("borderless");
        userMenu.setWidth("125px");
        userMenu.setId("userMenu");

        loggedUser = userMenu.addItem("", VaadinIcons.USER, null);

        loggedUser.addItem("Profiles", VaadinIcons.FILE_TEXT_O, null);

        loggedUser.addItem("Favourites",
                VaadinIcons.HEART_O,
                (MenuBar.Command) it -> Page.getCurrent().getUI().getNavigator().navigateTo(FavouriteView.NAME));

        loggedUser.addSeparator();
        loggedUser.addItem(Messages.get("auth.logout"),
                VaadinIcons.SIGN_OUT,
                (MenuBar.Command) it -> {
                    eventHandler.logout();
                    clickHome();
                });

        userMenu.setAutoOpen(true);
    }

    private void setupAdminMenu() {
        topMenu.setStyleName("borderless");

        MenuBar.Command addProductsCmd = (MenuBar.Command) selectedItem -> eventHandler.showAddProductsForm();
        MenuItem admin = topMenu.addItem("Admin", null, null);
        admin.addItem("Add Products", VaadinIcons.PLUS, addProductsCmd);
    }

    private void setupHomeBtn() {
        btnHome.setStyleName("borderless");
        btnHome.addStyleName("active-noborder");
        btnHome.addStyleName("active_menu");
        btnHome.setId("womenBtn");
    }

    private void setupWomenWatchesBtn() {
        btnWomen.setStyleName("borderless");
        btnWomen.addStyleName("colored-when-hover");
        btnWomen.addStyleName("active-noborder");
        btnWomen.setId("womenBtn");
    }

    private void setupMenWatchesBtn() {
        btnMen.setStyleName("borderless");
        btnMen.addStyleName("colored-when-hover");
        btnMen.addStyleName("active-noborder");
        btnMen.setId("menBtn");
    }

    private void setupWatchesBtn() {
        btnWatches.setStyleName("borderless");
        btnWatches.addStyleName("colored-when-hover");
        btnWatches.addStyleName("active-noborder");
        btnWatches.setId("watchesBtn");
    }

    private void setupCartBtn() {
        btnCart.setIcon(VaadinIcons.CART);
        btnCart.setStyleName("borderless");
        btnCart.addStyleName("icon-align-right");
        btnCart.addStyleName("colored-when-hover");
        btnCart.addStyleName("active-noborder");
        btnCart.setId("cart");
    }

    private void setupLoginBtn() {
        btnLogin.setIcon(VaadinIcons.USER);
        btnLogin.setStyleName("borderless");
        btnLogin.addStyleName("icon-align-right");
        btnLogin.addStyleName("colored-when-hover");
        btnLogin.addStyleName("active-noborder");
        btnLogin.setId("loginBtn");
        btnLogin.setWidth("125px");
    }

    private void init() {
        setSpacing(false);
        setupLogo();
        setupMenuBar();

        if (eventHandler.getCurrentUser() != null) {
            setLoginStatusToBtn(true, eventHandler.getCurrentUser());
        } else {
            setLoginStatusToBtn(false, null);
        }
    }

    private void setEvent() {
        logo.addClickListener(e -> eventHandler.showHomePage());
        siteName.addClickListener(e -> eventHandler.showHomePage());
        btnHome.addClickListener(e -> eventHandler.showHomePage());
        btnCart.addClickListener(e -> eventHandler.showCartListInSideWindow());
        btnLogin.addClickListener(e -> eventHandler.showLoginPopup());

        btnWatches.addClickListener(e -> {
            eventHandler.getSideWindow().close();
            Page.getCurrent().getUI().getNavigator().navigateTo(WatchesView.NAME);
            setMenuAllWatchesActive();
        });

        btnMen.addClickListener(e -> {
            eventHandler.getSideWindow().close();
            Page.getCurrent().getUI().getNavigator().navigateTo(MenWatchesView.NAME);
            setMenuMenWatchesActive();
        });

        btnWomen.addClickListener(e -> {
            eventHandler.getSideWindow().close();
            Page.getCurrent().getUI().getNavigator().navigateTo(WomenWatchesView.NAME);
            setMenuWomenWatchesActive();
        });
    }

    private void clearActiveMenuStyle() {
        btnHome.removeStyleName("active_menu");
        btnHome.addStyleName("colored-when-hover");
        btnWatches.removeStyleName("active_menu");
        btnWatches.addStyleName("colored-when-hover");
        btnMen.removeStyleName("active_menu");
        btnMen.addStyleName("colored-when-hover");
        btnWomen.removeStyleName("active_menu");
        btnWomen.addStyleName("colored-when-hover");
    }

    private void setActiveMenuStyle(Button activeMenu) {
        activeMenu.addStyleName("active_menu");
        activeMenu.removeStyleName("colored-when-hover");
    }

    void setMenuAllWatchesActive() {
        clearActiveMenuStyle();
        setActiveMenuStyle(btnWatches);
    }

    void setMenuWomenWatchesActive() {
        clearActiveMenuStyle();
        setActiveMenuStyle(btnWomen);
    }

    void setMenuMenWatchesActive() {
        clearActiveMenuStyle();
        setActiveMenuStyle(btnMen);
    }

    void clickWatchesWithDiscount() {
        btnWatches.click();
        ((WatchesView) Page.getCurrent().getUI().getNavigator().getCurrentView()).sortItemByDiscount();
        btnWatches.focus();
    }

    void clickMen() {
        btnMen.click();
        btnMen.focus();
    }

    void clickWomen() {
        btnWomen.click();
        btnWomen.focus();
    }

    void clickWatches() {
        btnWatches.click();
        btnWatches.focus();
    }

    void clickFavourite() {
        clearActiveMenuStyle();
//        userMenu.focus();
    }
}
