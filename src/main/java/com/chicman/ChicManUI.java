package com.chicman;

import co.omise.Client;
import co.omise.ClientException;
import co.omise.models.Charge;
import co.omise.models.OmiseException;
import com.chicman.bean.CartBean;
import com.chicman.bean.FavouriteBean;
import com.chicman.bean.FilterBean;
import com.chicman.bean.LoggedInBean;
import com.chicman.model.Customer;
import com.chicman.model.Product;
import com.chicman.model.VerificationToken;
import com.chicman.repository.CustomerRepository;
import com.chicman.repository.ProductRepository;
import com.chicman.repository.VerificationTokenRepository;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.chicman.view.*;
import com.chicman.view.admin.AddProductsForm;
import com.chicman.view.cart.SideWindowCart;
import com.chicman.view.checkout.CreditCardForm;
import com.chicman.view.login.Login;
import com.chicman.view.signup.SignUp;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@com.vaadin.annotations.JavaScript
        ({"https://cdn.omise.co/omise.js.gz", "../../../VAADIN/themes/chicmantheme/app.js"})
@Theme("chicmantheme")
@SpringUI
@PushStateNavigation
public class ChicManUI extends UI implements Window.CloseListener {

    static {
        Messages.addBundle("messages");
    }

    private final Logger log = LoggerFactory.getLogger(ChicManUI.class);

    private final VerificationTokenRepository verificationTokenRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productsRepo;
    private final FavouriteBean sessionFavourite;
    private final LoggedInBean sessionLogIn;
    private final FilterBean filterBean;
    private final CartBean sessionCart;

    private File uploadFile;
    private boolean flagPauseHighlight;
    private String appPath;
    private Toolbar toolbar;
    private Window sideWindow;
    private boolean flagSideWindowClosed;
    private Window floatWindow;
    private WatchesView watchesView;
    private MenWatchesView menWatchesView;
    private WomenWatchesView womenWatchesView;

    @Autowired
    public ChicManUI(
            FavouriteBean favouriteBean,
            CartBean sessionCart,
            LoggedInBean sessionLogIn,
            ProductRepository productsRepo,
            CustomerRepository customerRepo,
            FilterBean filterBean,
            VerificationTokenRepository verificationTokenRepo
    ) {
        this.sessionFavourite = favouriteBean;
        this.sessionCart = sessionCart;
        this.sessionLogIn = sessionLogIn;
        this.productsRepo = productsRepo;
        this.customerRepo = customerRepo;
        this.filterBean = filterBean;
        this.verificationTokenRepo = verificationTokenRepo;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        // reset filters
        filterBean.setPriceRange(FilterBean.DEFAULT_MIN_PRICE, FilterBean.DEFAULT_MAX_PRICE);
        resetFilter();
        appPath = null;
        getPage().setTitle("Chic Man");

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(false);
        mainLayout.setSpacing(false);
        setContent(mainLayout);

        // for side window
        flagSideWindowClosed = true;
        sideWindow = new Window();
        sideWindow.setId("sideWindow");
        sideWindow.addCloseListener(e -> flagSideWindowClosed = true);

        // for float window
        floatWindow = new Window();
        floatWindow.setId("floatWindow");
        floatWindow.addCloseListener(this);


        // for toolbar section
        toolbar = new Toolbar(this);
        toolbar.setId("toolbar");
        toolbar.setTextToAllComponent();
        toolbar.updateCartItemNumber(sessionCart.countItem());
        mainLayout.addComponent(toolbar);
        mainLayout.setComponentAlignment(toolbar, Alignment.TOP_CENTER);

        // for content section
        ComponentContainer contentSection = new VerticalLayout();
        contentSection.setId("main");
        mainLayout.addComponent(contentSection);

        // for footer section
        Footer footer = new Footer();
        footer.setId("footer");
        mainLayout.addComponent(footer);
        mainLayout.setComponentAlignment(footer, Alignment.TOP_CENTER);

        HomeView homeView = new HomeView(this);
        watchesView = new WatchesView(this);
        menWatchesView = new MenWatchesView(this);
        womenWatchesView = new WomenWatchesView(this);
        FavouriteView favouriteView = new FavouriteView(this);

        Navigator navigator = new Navigator(this, contentSection);
        navigator.addView(HomeView.NAME, homeView);
        navigator.addView(WatchesView.NAME, watchesView);
        navigator.addView(MenWatchesView.NAME, menWatchesView);
        navigator.addView(WomenWatchesView.NAME, womenWatchesView);
        navigator.addView(FavouriteView.NAME, favouriteView);

        setJsFunction();
        Page.getCurrent().getJavaScript().execute("extractUrl()");

        String token = vaadinRequest.getParameter("token");
        if (token != null) {
            // retrieve verification token from database
            // check expiry date
            // if valid update enabled field
            // else show message

            VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
            if (verificationToken != null) {
                Optional<Customer> customer =
                        customerRepo.findById(verificationToken.getCustomerId());

                LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
                Date current = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

                log.info("expiry date  : " + sdf.format(verificationToken.getExpiryDate()));
                log.info("current date : " + sdf.format(current));

                if (current.compareTo(verificationToken.getExpiryDate()) <= 0
                        && customer.isPresent()) {

                    Customer storedCustomer = customer.get();
                    storedCustomer.setEnabled(true);
                    customerRepo.saveAndFlush(storedCustomer);

                    Utils.notify(
                            Messages.get("confirmation.complete"),
                            1500,
                            Position.TOP_CENTER, VaadinIcons.CHECK);
                } else {
                    Utils.notify(
                            Messages.get("confirmation.incomplete"),
                            1500,
                            Position.TOP_CENTER, VaadinIcons.CHECK);
                }
            }
        }
    }

    public void resetFilter() {
        filterBean.reset();
    }

    public boolean login(String email, String password) {
        boolean result = false;

        if (attemptToLogin(email, password)) {
            floatWindow.close();
            sessionCart.attachToCustomerCart(sessionLogIn.loggedInUser);
            sessionFavourite.attachToCustomerFavourite(sessionLogIn.loggedInUser);
            updateHeartImageColor(sessionFavourite.getFavouriteList());
            toolbar.setLoginStatusToBtn(true, sessionLogIn.loggedInUser);
            toolbar.updateCartItemNumber(sessionCart.countItem());

            if (isSideWindowShownOn() && sideWindow.getContent() != null) {
                SideWindowCart sideWindowCart = (SideWindowCart) sideWindow.getContent();
                sideWindowCart.updateCartList(sessionCart.getProductList());
            }

            result = true;
        } else {
            Notification.show(Messages.get("auth.login.failed"),
                    Messages.get("auth.login.failed2"), Notification.Type.HUMANIZED_MESSAGE);
        }

        return result;
    }

    public void logout() {
        sessionLogIn.loggedInUser = null;
        sessionCart.resetProductList();
        sessionFavourite.resetProductList();
        toolbar.updateCartItemNumber(sessionCart.countItem());
        toolbar.setLoginStatusToBtn(false, null);
    }

    public void signedUp(VerificationToken verificationToken, Customer newCustomer) {
        floatWindow.close();

        if (appPath != null) {
            StringBuilder confirmLink = new StringBuilder();
            confirmLink.append(appPath);
            confirmLink.append("?token=");
            confirmLink.append(verificationToken.getToken());

            try {
                Utils.sendConfirmationSignUpMail(
                        confirmLink.toString(),
                        newCustomer.getEmail(),
                        verificationToken.getExpiryDate());

                log.info("email was send.");

                String message = Messages.get("send.mail.success1") +
                        " " +
                        newCustomer.getEmail() +
                        " " +
                        Messages.get("send.mail.success2");

                Utils.notify(message, 1500, Position.TOP_CENTER, VaadinIcons.CHECK);
            } catch (MessagingException e) {
                e.printStackTrace();
                log.info("incomplete to sending email.");
            }
        }
    }

    public void showLoginPopup() {
        floatWindow.close();
        Login content = new Login(this);
        content.setId("loginForm");
        floatWindow.setContent(content);
        setupFloatWindow();
    }

    public void showSignUpPopup() {
        floatWindow.close();
        SignUp content = new SignUp(customerRepo, verificationTokenRepo, this);
        content.setId("signUpForm");
        floatWindow.setContent(content);
        setupFloatWindow();
    }

    public void exploreProduct(Product product, StreamResource imgProduct) {
        floatWindow.close();
        flagPauseHighlight = true;
        floatWindow.setContent(new ProductDetails(this, product, imgProduct));
        floatWindow.setWidth("484px");
        floatWindow.setHeight("478px");
        floatWindow.center();
        floatWindow.setResizable(false);
        floatWindow.setClosable(false);
        floatWindow.setModal(true);
        floatWindow.addBlurListener(e ->
                Page.getCurrent().getJavaScript().execute("getFocused()"));

        addWindow(floatWindow);
    }

    public void showAddProductsForm() {
        floatWindow.close();
        AddProductsForm detail = new AddProductsForm(this);
        floatWindow.setContent(detail);
        floatWindow.setWidthUndefined();
        floatWindow.setHeightUndefined();
        floatWindow.center();
        floatWindow.setResizable(false);
        floatWindow.setClosable(true);
        floatWindow.setModal(true);
        addWindow(floatWindow);
    }

    public void showCreditCardForm() {
        floatWindow.close();
        floatWindow.setHeightUndefined();
        floatWindow.setWidthUndefined();
        floatWindow.setResizable(false);
        floatWindow.setClosable(false);
        floatWindow.setModal(true);
        floatWindow.center();
        floatWindow.addBlurListener(e ->
                Page.getCurrent().getJavaScript().execute("getFocused()"));

        CreditCardForm creditCardForm = new CreditCardForm(this);
        floatWindow.setContent(creditCardForm);
        addWindow(floatWindow);
    }

    public void setImageSourceToPlaceholder(File image, Image placeholder) {
        uploadFile = image;
        placeholder.setSource(new FileResource(image));
    }

    public boolean addNewProduct(
            String productName,
            String productDetails,
            String productPrice,
            String dialType
    ) {
        boolean flagSuccess = false;
        FileInputStream imageStream;
        byte[] buffer;

        log.info("dial type: " + dialType);

        try {
            float price = Float.parseFloat(productPrice);

            imageStream = new FileInputStream(uploadFile);
            buffer = new byte[imageStream.available()];
            if (imageStream.read(buffer) >= 0) {
                productsRepo.save(new Product(productName, productDetails, price, buffer));
            }
            flagSuccess = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return flagSuccess;
    }

    public void addItemToCart(Product product) {
        if (product != null) {
            log.info("add product : " + product.getProductBrandId());
            sessionCart.addProductToCart(product);
            floatWindow.close();
            toolbar.updateCartItemNumber(sessionCart.countItem());

            Notification notification = new Notification(Messages.get("product.add")
                    + " " + product.getProductBrandId() + " " + Messages.get("product.add.done"));
            notification.setDelayMsec(1500);
            notification.setPosition(Position.TOP_CENTER);
            notification.setIcon(VaadinIcons.CHECK);
            notification.show(Page.getCurrent());

            if (isSideWindowShownOn()) {
                SideWindowCart sideWindowCart = (SideWindowCart) sideWindow.getContent();
                sideWindowCart.updateCartList(sessionCart.getProductList());
            }
        } else {
            log.info("add product : null");
        }
    }

    public void favouriteProduct(@NotNull Product product) {
        log.info("favourite product : " + product.getProductBrandId());
        sessionFavourite.addProductToFavourite(product);
    }

    public void showCartListInSideWindow() {
        sideWindow.close();
        sideWindow.setHeight("100%");
        sideWindow.setWidth("420px");
        sideWindow.setPositionX(Page.getCurrent().getBrowserWindowWidth() - 420);
        sideWindow.setPositionY(0);
        sideWindow.setStyleName("side_cart_list");
        sideWindow.setResizable(false);
        sideWindow.setClosable(false);

        SideWindowCart sideWindowCart =
                new SideWindowCart(this, sessionCart.getProductList());

        sideWindow.setContent(sideWindowCart);

        addWindow(sideWindow);
        setFlagSideWindowClosed(false);
    }

    public void closeSideWindow() {
        sideWindow.close();
        setFlagSideWindowClosed(true);
    }

    public void showHomePage() {
        sideWindow.close();
        toolbar.clickHome();

        // test
        sessionFavourite.getFavouriteList().forEach(item ->
                log.info("price : " + item.getProductPriceSale()));
    }

    public Customer getCurrentUser() {
        return sessionLogIn != null ? sessionLogIn.loggedInUser : null;
    }

    public void removeCartList(Product removeItem) {
        if (sessionCart.countItem() > 0) {
            sessionCart.removeProductFromCart(removeItem);
        }

        toolbar.updateCartItemNumber(sessionCart.countItem());
        SideWindowCart sideWindowCart = (SideWindowCart) sideWindow.getContent();
        sideWindowCart.updateCartList(sessionCart.getProductList());
    }

    @Override
    public void windowClose(Window.CloseEvent closeEvent) {
        flagPauseHighlight = !(closeEvent.getWindow() == floatWindow && flagPauseHighlight);
    }

    public void filter() {
        View currentView = Page.getCurrent().getUI().getNavigator().getCurrentView();

        if (currentView instanceof WatchesView) {
            watchesView.filter();
        } else if (currentView instanceof MenWatchesView) {
            menWatchesView.filter();
        } else if (currentView instanceof WomenWatchesView) {
            womenWatchesView.filter();
        } else {
            log.info("Cannot filter in current View.");
        }
    }

    public ProductRepository getProductsRepo() {
        return productsRepo;
    }

    public FilterBean getFilterBean() {
        return filterBean;
    }

    public Window getSideWindow() {
        return sideWindow;
    }

    public Window getFloatWindow() {
        return floatWindow;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public FavouriteBean getSessionFavourite() {
        return sessionFavourite;
    }

    private void genCharge(String token) {
        try {
            SideWindowCart sideWindowCart = (SideWindowCart) sideWindow.getContent();
            String textAmount = String.valueOf(sideWindowCart.getTotalPrice());
            String dec = textAmount.substring(0, textAmount.indexOf('.'));
            String flo = textAmount.substring(textAmount.indexOf('.') + 1);
            if (flo.length() == 1) {
                flo += "0";
            }

            log.info("amount = " + textAmount);
            log.info("dec = " + dec);
            log.info("flo = " + flo);

            int totalAmount = Integer.parseInt(dec + flo);
            log.info("int amount = " + totalAmount);

            Client client = new Client(
                    "pkey_test_5bdl08brxlvpou9e3nb",
                    "skey_test_5bdl08bs84aysjyesv8");

            Charge charge = client.charges()
                    .create(new Charge.Create().amount(totalAmount).currency("THB").card(token));

            log.info("Created charge: " + charge.getId());

            sessionCart.checkoutAllProductFromCart();
            toolbar.updateCartItemNumber(sessionCart.countItem());
            sideWindowCart.updateCartList(sessionCart.getProductList());
            floatWindow.close();

            Notification notif = new Notification(Messages.get("checkout.success"),
                    Messages.get("checkout.success.description"));

            notif.setDelayMsec(1500);
            notif.setPosition(Position.TOP_CENTER);
            notif.setIcon(VaadinIcons.CHECK);
            notif.show(Page.getCurrent());

        } catch (ClientException | OmiseException | IOException e) {
            e.printStackTrace();
            Notification.show("ทำรายการไม่สำเร็จ");
        }
    }

    private void setJsFunction() {
        JavaScript.getCurrent().addFunction("receiveId", (JavaScriptFunction) arguments -> {
            String id = arguments.getString(0);
            log.info("received id from client : " + id);
            genCharge(id);
        });

        JavaScript.getCurrent().addFunction("receiveFocused", (JavaScriptFunction) arguments -> {
            String id = arguments.getString(0);

            if (id.equalsIgnoreCase("body")) {
                floatWindow.close();
            }
        });

        JavaScript.getCurrent().addFunction("receiveUrl",
                (JavaScriptFunction) arguments -> appPath = arguments.getString(0));
    }

    private void setupFloatWindow() {
        floatWindow.center();
        floatWindow.setClosable(false);
        floatWindow.setResizable(false);
        floatWindow.setWidth("300px");
        floatWindow.setHeightUndefined();
        floatWindow.setModal(true);
        floatWindow.addBlurListener(e ->
                Page.getCurrent().getJavaScript().execute("getFocused()"));

        addWindow(floatWindow);
    }

    private boolean attemptToLogin(String email, String password) {
        boolean loginSuccess = false;
        Customer loginUser = null;

        List<Customer> storedUser = customerRepo.findByEmail(email);
        if (storedUser.size() > 0) {
            loginUser = storedUser.get(0);
        }

        if (loginUser != null
                && loginUser.getEnabled()
                && password.equals(loginUser.getPassword())) {

            sessionLogIn.loggedInUser = loginUser;
            loginSuccess = true;
        }

        return loginSuccess;
    }

    private boolean isSideWindowShownOn() {
        return !flagSideWindowClosed;
    }

    private void setFlagSideWindowClosed(boolean windowClosed) {
        flagSideWindowClosed = windowClosed;
    }

    private void updateHeartImageColor(List<Product> productList) {
        View currentView = Page.getCurrent().getUI().getNavigator().getCurrentView();

        if (currentView instanceof WatchesView) {
            ((WatchesView) currentView).updateFavourite(productList);
        } else if (currentView instanceof MenWatchesView) {
            ((MenWatchesView) currentView).updateFavourite(productList);
        } else if (currentView instanceof WomenWatchesView) {
            ((WomenWatchesView) currentView).updateFavourite(productList);
        }
    }
}
