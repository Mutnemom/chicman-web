package com.chicman

import co.omise.ClientException
import co.omise.models.OmiseException
import com.chicman.bean.*
import com.chicman.model.Customer
import com.chicman.model.Member
import com.chicman.model.Product
import com.chicman.model.VerificationToken
import com.chicman.repository.ProductRepository
import com.chicman.repository.VerificationTokenRepository
import com.chicman.service.AuthService
import com.chicman.service.MemberService
import com.chicman.utility.LogUtils
import com.chicman.utility.Messages
import com.chicman.utility.Utils
import com.chicman.view.*
import com.chicman.view.admin.AddProductsForm
import com.chicman.view.cart.SideWindowCart
import com.chicman.view.checkout.CreditCardForm
import com.chicman.view.login.Login
import com.chicman.view.signup.SignUp
import com.vaadin.annotations.JavaScript
import com.vaadin.annotations.Theme
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.Navigator
import com.vaadin.navigator.PushStateNavigation
import com.vaadin.server.FileResource
import com.vaadin.server.Page
import com.vaadin.server.StreamResource
import com.vaadin.server.VaadinRequest
import com.vaadin.shared.Position
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@JavaScript("https://cdn.omise.co/omise.js.gz", "../../../VAADIN/themes/chicmantheme/app.js")
@Theme("chicmantheme")
@SpringUI
@PushStateNavigation
class ChicManUI @Autowired constructor(
        val sessionFavourite: FavouriteBean,
        private val sessionCart: CartBean,
        private val sessionLogIn: LoggedInBean?,
        val productsRepo: ProductRepository,
        val filterBean: FilterBean,
        private val guestBean: GuestBean,
        private val verificationTokenRepo: VerificationTokenRepository
) : UI(), Window.CloseListener {

    companion object {
        init {
            Messages.addBundle("messages")
        }
    }

    var appPath: String? = null
    private var uploadFile: File? = null
    private var watchesView: WatchesView? = null
    private var menWatchesView: MenWatchesView? = null
    private var womenWatchesView: WomenWatchesView? = null
    private var flagPauseHighlight = false
    private var flagSideWindowClosed = false

    var toolbar: Toolbar? = null
    var sideWindow: Window? = null
    var floatWindow: Window? = null

    private val isSideWindowShownOn: Boolean
        get() = !flagSideWindowClosed

    val currentUser: Member?
        get() = sessionLogIn?.loggedInUser


    override fun init(vaadinRequest: VaadinRequest) {
        /* get guest token for using without logging in */
        guestBean.token = AuthService.getGuestToken()!!.accessToken!!

        // reset filters
        filterBean.setPriceRange(FilterBean.DEFAULT_MIN_PRICE, FilterBean.DEFAULT_MAX_PRICE)
        resetFilter()

        appPath = null
        page.setTitle("Chic Man")
        setupRootLayout()

        setupFloatWindow()
        setupSideWindow()
        setupToolbar()
        setupContent()
        setupFooter()

        setJsFunction()
        Page.getCurrent().javaScript.execute("extractUrl()")
//        handleVerificationToken(vaadinRequest)
    }

    fun resetFilter() {
        filterBean.reset()
    }

    fun login(email: String, password: String): Boolean {
        val loginResult = AuthService.login(guestBean.token!!, email, password)
        return if (loginResult != null) {
            loginResult.member.run {
                sessionLogIn!!.loggedInUser = this
//                sessionCart.attachToCustomerCart(this)
//                sessionFavourite.attachToCustomerFavourite(this)
                toolbar!!.setLoginStatusToBtn(true, this)
            }

            floatWindow!!.close()
            updateHeartImageColor(sessionFavourite.favourites)
//            toolbar!!.updateCartItemNumber(sessionCart.productList.size)

            if (isSideWindowShownOn && sideWindow!!.content != null) {
                val sideWindowCart = sideWindow!!.content as SideWindowCart
                sideWindowCart.updateCartList(sessionCart.productList)
            }

            true
        } else {
            Notification.show(
                    Messages.get("auth.login.failed"),
                    Messages.get("auth.login.failed2"),
                    Notification.Type.HUMANIZED_MESSAGE
            )

            false
        }
    }

    fun logout() {
        sessionLogIn!!.loggedInUser = null
//        sessionCart.resetProductList()
//        sessionFavourite.resetProductList()
        toolbar!!.updateCartItemNumber(0 /* sessionCart.productList.size */)
        toolbar!!.setLoginStatusToBtn(false, null)
    }

    fun showLoginPopup() {
        floatWindow!!.close()
        val content = Login(this)
        content.id = "loginForm"
        floatWindow!!.content = content
        showFloatWindow()
    }

    fun showSignUpPopup() {
        floatWindow!!.close()
        val content = SignUp(verificationTokenRepo, this, guestBean)
        content.id = "signUpForm"
        floatWindow!!.content = content
        showFloatWindow()
    }

    fun exploreProduct(product: Product?, imgProduct: StreamResource?) {
        floatWindow!!.close()
        flagPauseHighlight = true
        floatWindow!!.content = ProductDetails(this, product, imgProduct)
        floatWindow!!.setWidth("484px")
        floatWindow!!.setHeight("478px")
        floatWindow!!.center()
        floatWindow!!.isResizable = false
        floatWindow!!.isClosable = false
        floatWindow!!.isModal = true
        floatWindow!!.addBlurListener { Page.getCurrent().javaScript.execute("getFocused()") }
        addWindow(floatWindow)
    }

    fun showAddProductsForm() {
        floatWindow!!.close()
        val detail = AddProductsForm(this)
        floatWindow!!.content = detail
        floatWindow!!.setWidthUndefined()
        floatWindow!!.setHeightUndefined()
        floatWindow!!.center()
        floatWindow!!.isResizable = false
        floatWindow!!.isClosable = true
        floatWindow!!.isModal = true
        addWindow(floatWindow)
    }

    fun showCreditCardForm() {
        floatWindow!!.close()
        floatWindow!!.setHeightUndefined()
        floatWindow!!.setWidthUndefined()
        floatWindow!!.isResizable = false
        floatWindow!!.isClosable = false
        floatWindow!!.isModal = true
        floatWindow!!.center()
        floatWindow!!.addBlurListener { Page.getCurrent().javaScript.execute("getFocused()") }
        val creditCardForm = CreditCardForm(this)
        floatWindow!!.content = creditCardForm
        addWindow(floatWindow)
    }

    fun setImageSourceToPlaceholder(image: File?, placeholder: Image) {
        uploadFile = image
        placeholder.source = FileResource(image)
    }

    fun addNewProduct(
            productName: String?,
            productDetails: String?,
            productPrice: String,
            dialType: String
    ): Boolean {
        var flagSuccess = false
        val imageStream: FileInputStream
        val buffer: ByteArray
        try {
            val price = productPrice.toFloat()
            imageStream = FileInputStream(uploadFile!!)
            buffer = ByteArray(imageStream.available())
            if (imageStream.read(buffer) >= 0) {
                productsRepo.save(Product(productName, productDetails, price, buffer))
            }
            flagSuccess = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return flagSuccess
    }

    fun addItemToCart(product: Product?) {
        if (product != null) {
            sessionCart.addProductToCart(product)
            floatWindow!!.close()
            toolbar!!.updateCartItemNumber(sessionCart.productList.size)
            val notification = Notification(Messages.get("product.add")
                    + " " + product.productBrandId + " " + Messages.get("product.add.done"))
            notification.delayMsec = 1500
            notification.position = Position.TOP_CENTER
            notification.icon = VaadinIcons.CHECK
            notification.show(Page.getCurrent())
            if (isSideWindowShownOn) {
                val sideWindowCart = sideWindow!!.content as SideWindowCart
                sideWindowCart.updateCartList(sessionCart.productList)
            }
        }
    }

    fun favouriteProduct(product: Product?) {
        sessionFavourite.addProductToFavourite(product)
    }

    fun showCartListInSideWindow() {
        sideWindow!!.close()
        sideWindow!!.setHeight("100%")
        sideWindow!!.setWidth("420px")
        sideWindow!!.positionX = Page.getCurrent().browserWindowWidth - 420
        sideWindow!!.positionY = 0
        sideWindow!!.styleName = "side_cart_list"
        sideWindow!!.isResizable = false
        sideWindow!!.isClosable = false
        val sideWindowCart = SideWindowCart(this, sessionCart.productList)
        sideWindow!!.content = sideWindowCart
        addWindow(sideWindow)
        setFlagSideWindowClosed(false)
    }

    fun closeSideWindow() {
        sideWindow!!.close()
        setFlagSideWindowClosed(true)
    }

    fun showHomePage() {
        sideWindow!!.close()
        toolbar!!.clickHome()
    }

    fun removeCartList(removeItem: Product?) {
        if (sessionCart.productList.size > 0) {
            removeItem?.let { sessionCart.removeProductFromCart(it) }
        }
        toolbar!!.updateCartItemNumber(sessionCart.productList.size)
        val sideWindowCart = sideWindow!!.content as SideWindowCart
        sideWindowCart.updateCartList(sessionCart.productList)
    }

    override fun windowClose(closeEvent: Window.CloseEvent) {
        flagPauseHighlight = !(closeEvent.window === floatWindow && flagPauseHighlight)
    }

    fun filter() {
        when (Page.getCurrent().ui.navigator.currentView) {
            is WatchesView -> watchesView!!.filter()
            is MenWatchesView -> menWatchesView!!.filter()
            is WomenWatchesView -> womenWatchesView!!.filter()
            else -> LogUtils.info("Cannot filter in current View.")
        }
    }

    private fun setupRootLayout() {
        content = VerticalLayout().apply {
            setMargin(false)
            isSpacing = false
        }
    }

    private fun setupSideWindow() {
        flagSideWindowClosed = true
        sideWindow = Window().apply {
            id = "sideWindow"
            addCloseListener { flagSideWindowClosed = true }
        }
    }

    private fun setupFloatWindow() {
        floatWindow = Window().apply {
            id = "floatWindow"
            addCloseListener(this@ChicManUI)
        }
    }

    private fun setupToolbar() {
        toolbar = Toolbar(this).apply {
            id = "toolbar"
            setTextToAllComponent()
            updateCartItemNumber(0 /* sessionCart.productList.size */)

            (content as? VerticalLayout)?.also {
                it.addComponent(this)
                it.setComponentAlignment(this, Alignment.TOP_CENTER)
            }
        }
    }

    private fun setupFooter() {
        Footer().apply {
            id = "footer"

            (content as? VerticalLayout)?.also {
                it.addComponent(this)
                it.setComponentAlignment(this, Alignment.TOP_CENTER)
            }
        }
    }

    private fun setupContent() {
        womenWatchesView = WomenWatchesView(this)
        menWatchesView = MenWatchesView(this)
        watchesView = WatchesView(this)

        VerticalLayout().apply {
            id = "main"
            (content as? VerticalLayout)?.also { it.addComponent(this) }

            navigator = Navigator(this@ChicManUI, this).apply {
                addView(HomeView.NAME, HomeView(this@ChicManUI))
                addView(WatchesView.NAME, watchesView)
                addView(MenWatchesView.NAME, menWatchesView)
                addView(WomenWatchesView.NAME, womenWatchesView)
                addView(FavouriteView.NAME, FavouriteView(this@ChicManUI))
            }
        }
    }

    private fun handleVerificationToken(vaadinRequest: VaadinRequest) {
        Page.getCurrent().javaScript.execute("extractUrl()")
        val token = vaadinRequest.getParameter("token")
        if (token != null) {
            // retrieve verification token from database
            // check expiry date
            // if valid update enabled field
            // else show message

            val verificationToken = verificationTokenRepo.findByToken(token)
            if (verificationToken != null) {
                val now = LocalDateTime.now(ZoneId.systemDefault())
                val current = Date.from(now.atZone(ZoneId.systemDefault()).toInstant())
                val sdf = SimpleDateFormat("yyy-MM-dd HH:mm:ss")
                if (current <= verificationToken.expiryDate) {

                    //                    val storedCustomer = customerRepo.get()
                    //                    storedCustomer.enabled = true

                    MemberService.verifyAccount(verificationToken)

                    Utils.notify(
                            Messages.get("confirmation.complete"),
                            1500,
                            Position.TOP_CENTER, VaadinIcons.CHECK)
                } else {
                    Utils.notify(
                            Messages.get("confirmation.incomplete"),
                            1500,
                            Position.TOP_CENTER, VaadinIcons.CHECK)
                }
            }
        }
    }

    private fun genCharge(token: String) {
        try {
            val sideWindowCart = sideWindow!!.content as SideWindowCart
            val textAmount = sideWindowCart.totalPrice.toString()
//            val dec = textAmount.substring(0, textAmount.indexOf('.'))
            var flo = textAmount.substring(textAmount.indexOf('.') + 1)
            if (flo.length == 1) {
                flo += "0"
            }

//            val totalAmount = (dec + flo).toInt()
//            val client = Client(
//                "pkey_test_5bdl08brxlvpou9e3nb",
//                "skey_test_5bdl08bs84aysjyesv8"
//            )
//
//            val charge = client.charges().create(
//                Charge.Create().amount(totalAmount.toLong()).currency("THB").card(token)
//            )

            sessionCart.checkoutAllProductFromCart()
            toolbar!!.updateCartItemNumber(sessionCart.productList.size)
            sideWindowCart.updateCartList(sessionCart.productList)
            floatWindow!!.close()
            val notif = Notification(Messages.get("checkout.success"),
                    Messages.get("checkout.success.description"))
            notif.delayMsec = 1500
            notif.position = Position.TOP_CENTER
            notif.icon = VaadinIcons.CHECK
            notif.show(Page.getCurrent())
        } catch (e: ClientException) {
            e.printStackTrace()
            Notification.show("ทำรายการไม่สำเร็จ")
        } catch (e: OmiseException) {
            e.printStackTrace()
            Notification.show("ทำรายการไม่สำเร็จ")
        } catch (e: IOException) {
            e.printStackTrace()
            Notification.show("ทำรายการไม่สำเร็จ")
        }
    }

    private fun setJsFunction() {
        com.vaadin.ui.JavaScript.getCurrent().addFunction("receiveId") { args ->
            val id = args.getString(0)
            genCharge(id)
        }

        com.vaadin.ui.JavaScript.getCurrent().addFunction("receiveFocused") { args ->
            val id = args.getString(0)
            if (id.equals("body", ignoreCase = true)) {
                floatWindow!!.close()
            }
        }

        com.vaadin.ui.JavaScript.getCurrent().addFunction("receiveUrl") { args ->
            appPath = args.getString(0)
        }
    }

    private fun setFlagSideWindowClosed(windowClosed: Boolean) {
        flagSideWindowClosed = windowClosed
    }

    private fun updateHeartImageColor(productList: List<Product>) {
        when (val currentView = Page.getCurrent().ui.navigator.currentView) {
            is WatchesView -> currentView.updateFavourite(productList)
            is MenWatchesView -> currentView.updateFavourite(productList)
            is WomenWatchesView -> currentView.updateFavourite(productList)
        }
    }

    private fun showFloatWindow() {
        floatWindow!!.center()
        floatWindow!!.isClosable = false
        floatWindow!!.isResizable = false
        floatWindow!!.setWidth("300px")
        floatWindow!!.setHeightUndefined()
        floatWindow!!.isModal = true
        floatWindow!!.addBlurListener { Page.getCurrent().javaScript.execute("getFocused()") }
        addWindow(floatWindow)
    }

}
