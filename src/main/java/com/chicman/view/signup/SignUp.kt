package com.chicman.view.signup

import com.chicman.ChicManUI
import com.chicman.bean.GuestBean
import com.chicman.repository.VerificationTokenRepository
import com.chicman.service.AuthService
import com.chicman.utility.LogUtils
import com.chicman.utility.Messages
import com.chicman.utility.Utils
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.Page
import com.vaadin.server.UserError
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*

@SpringComponent
@UIScope
class SignUp(
    private val verificationTokenRepo: VerificationTokenRepository,
    private val eventHandler: ChicManUI,
    private val guestBean: GuestBean
) : VerticalLayout() {

    private val fullName: TextField = TextField(Messages.get("auth.username"))
    private val btnUsernameCorrect: Button = Button()

    private val email: TextField = TextField(Messages.get("auth.email"))
    private val btnEmailCorrect: Button = Button()

    private val password: PasswordField = PasswordField(Messages.get("auth.password"))
    private val btnPasswordCorrect: Button = Button()

    private val passwordConfirm: PasswordField = PasswordField(Messages.get("auth.password.confirm"))
    private val btnPasswordConfirmCorrect: Button = Button()

    private val btnSignUp: Button = Button(Messages.get("auth.register"))
    private val btnTempSignUp: Button = Button()


    init {
        setupComponent()
        setEvent()

        Page.getCurrent().javaScript.apply {
            execute("enter2ClickButton(${email.id}, ${btnSignUp.id})")
            execute("enter2ClickButton(${fullName.id}, ${btnSignUp.id})")
            execute("enter2ClickButton(${password.id}, ${btnSignUp.id})")
            execute("enter2ClickButton(${passwordConfirm.id}, ${btnSignUp.id})")
        }
    }

    private fun setupComponent() {
        addUsernamePart()
        addEmailPart()
        addPasswordPart()
        addPasswordConfirmPart()
        addBtnSignUpPart()

        btnSignUp.focus()
        fullName.tabIndex = 1
        email.tabIndex = 2
        password.tabIndex = 3
        passwordConfirm.tabIndex = 4
        btnSignUp.tabIndex = 5
    }

    private fun addUsernamePart() {
        val usernamePart = HorizontalLayout()
        usernamePart.setMargin(false)
        usernamePart.isSpacing = false
        usernamePart.setWidth("100%")
        fullName.id = "fullNameSignUp"
        fullName.setWidth("100%")
        fullName.addFocusListener {
            fullName.componentError = null
            if (fullName.value.isNotEmpty()) {
                fullName.cursorPosition = fullName.value.length
            }
        }
        fullName.addBlurListener {
            if (fullName.value.isNotEmpty()) {
                fullName.componentError = null
                btnUsernameCorrect.setStyleName("validate-status-correct", true)
            } else {
                fullName.componentError = UserError(Messages.get("auth.username.blank"))
                btnUsernameCorrect.removeStyleName("validate-status-correct")
            }
            Page.getCurrent().javaScript.execute("getFocused()")
        }
        usernamePart.addComponent(fullName)
        usernamePart.setComponentAlignment(fullName, Alignment.TOP_LEFT)
        usernamePart.setExpandRatio(fullName, 1f)
        btnUsernameCorrect.setStyleName("borderless", true)
        btnUsernameCorrect.setStyleName("icon-only", true)
        btnUsernameCorrect.setStyleName("validate-status", true)
        btnUsernameCorrect.icon = VaadinIcons.CHECK
        btnUsernameCorrect.isEnabled = false
        btnUsernameCorrect.isVisible = true
        usernamePart.addComponent(btnUsernameCorrect)
        usernamePart.setComponentAlignment(btnUsernameCorrect, Alignment.BOTTOM_RIGHT)
        addComponent(usernamePart)
        setComponentAlignment(usernamePart, Alignment.TOP_LEFT)
    }

    private fun addEmailPart() {
        val emailPart = HorizontalLayout()
        emailPart.setMargin(false)
        emailPart.isSpacing = false
        emailPart.setWidth("100%")
        email.id = "emailSignUp"
        email.placeholder = Messages.get("auth.email.placeholder")
        email.setWidth("100%")
        email.addFocusListener {
            email.componentError = null
            if (email.value.isNotEmpty()) {
                email.cursorPosition = email.value.length
            } else {
                email.placeholder = Messages.get("auth.email.placeholder")
            }
        }
        email.addBlurListener {
            if (Utils.isValidEmail(email)) {
                btnEmailCorrect.setStyleName("validate-status-correct", true)
            } else {
                btnEmailCorrect.removeStyleName("validate-status-correct")
            }
            Page.getCurrent().javaScript.execute("getFocused()")
        }
        emailPart.addComponent(email)
        emailPart.setComponentAlignment(email, Alignment.TOP_LEFT)
        emailPart.setExpandRatio(email, 1f)
        btnEmailCorrect.setStyleName("borderless", true)
        btnEmailCorrect.setStyleName("icon-only", true)
        btnEmailCorrect.setStyleName("validate-status", true)
        btnEmailCorrect.icon = VaadinIcons.CHECK
        btnEmailCorrect.isEnabled = false
        btnEmailCorrect.isVisible = true
        emailPart.addComponent(btnEmailCorrect)
        emailPart.setComponentAlignment(btnEmailCorrect, Alignment.BOTTOM_RIGHT)
        addComponent(emailPart)
        setComponentAlignment(emailPart, Alignment.TOP_LEFT)
    }

    private fun addPasswordPart() {
        val passwordPart = HorizontalLayout()
        passwordPart.setMargin(false)
        passwordPart.isSpacing = false
        passwordPart.setWidth("100%")
        password.caption = Messages.get("auth.password") +
            "&nbsp;" +
            "<span class='password-suggest'>" +
            Messages.get("auth.password.suggest") + "</span>"
        password.isCaptionAsHtml = true
        password.id = "passwordSignUp"
        password.setWidth("100%")
        password.maxLength = 10
        password.addFocusListener {
            password.componentError = null
            if (password.value.isNotEmpty()) {
                password.cursorPosition = password.value.length
            }
        }
        password.addBlurListener {
            if (Utils.isValidPassword(password)) {
                btnPasswordCorrect.setStyleName("validate-status-correct", true)
            } else {
                btnPasswordCorrect.removeStyleName("validate-status-correct")
            }
            Page.getCurrent().javaScript.execute("getFocused()")
        }
        passwordPart.addComponent(password)
        passwordPart.setComponentAlignment(password, Alignment.TOP_LEFT)
        passwordPart.setExpandRatio(password, 1f)
        btnPasswordCorrect.styleName = "borderless"
        btnPasswordCorrect.setStyleName("icon-only", true)
        btnPasswordCorrect.setStyleName("validate-status", true)
        btnPasswordCorrect.icon = VaadinIcons.CHECK
        btnPasswordCorrect.isEnabled = false
        btnPasswordCorrect.isVisible = true
        passwordPart.addComponent(btnPasswordCorrect)
        passwordPart.setComponentAlignment(btnPasswordCorrect, Alignment.BOTTOM_RIGHT)
        addComponent(passwordPart)
        setComponentAlignment(passwordPart, Alignment.TOP_LEFT)
    }

    private fun addPasswordConfirmPart() {
        val passwordConfirmPart = HorizontalLayout()
        passwordConfirmPart.setMargin(false)
        passwordConfirmPart.isSpacing = false
        passwordConfirmPart.setWidth("100%")
        passwordConfirm.id = "passwordConfirmSignUp"
        passwordConfirm.setWidth("100%")
        passwordConfirm.maxLength = 10
        passwordConfirm.addFocusListener {
            passwordConfirm.componentError = null
            if (passwordConfirm.value.isNotEmpty()) {
                passwordConfirm.cursorPosition = passwordConfirm.value.length
            }
        }
        passwordConfirm.addBlurListener {
            if (Utils.isValidPasswordConfirm(passwordConfirm, password)) {
                btnPasswordConfirmCorrect.addStyleName("validate-status-correct")
            } else {
                btnPasswordConfirmCorrect.removeStyleName("validate-status-correct")
            }
            Page.getCurrent().javaScript.execute("getFocused()")
        }
        passwordConfirmPart.addComponent(passwordConfirm)
        passwordConfirmPart.setComponentAlignment(passwordConfirm, Alignment.TOP_LEFT)
        passwordConfirmPart.setExpandRatio(passwordConfirm, 1f)
        btnPasswordConfirmCorrect.styleName = "borderless"
        btnPasswordConfirmCorrect.setStyleName("icon-only", true)
        btnPasswordConfirmCorrect.setStyleName("validate-status", true)
        btnPasswordConfirmCorrect.icon = VaadinIcons.CHECK
        btnPasswordConfirmCorrect.isEnabled = false
        btnPasswordConfirmCorrect.isVisible = true
        passwordConfirmPart.addComponent(btnPasswordConfirmCorrect)
        passwordConfirmPart.setComponentAlignment(btnPasswordConfirmCorrect, Alignment.BOTTOM_RIGHT)
        addComponent(passwordConfirmPart)
        setComponentAlignment(passwordConfirmPart, Alignment.TOP_LEFT)
    }

    private fun addBtnSignUpPart() {
        val btnSignUpPart = HorizontalLayout()
        btnSignUpPart.setMargin(false)
        btnSignUpPart.isSpacing = false
        btnSignUpPart.setWidth("100%")
        btnSignUp.styleName = "primary"
        btnSignUp.setWidth("100%")
        btnSignUp.id = "btnSignUp"
        btnSignUp.addBlurListener { Page.getCurrent().javaScript.execute("getFocused()") }
        btnSignUpPart.addComponent(btnSignUp)
        btnSignUpPart.setComponentAlignment(btnSignUp, Alignment.TOP_LEFT)
        btnSignUpPart.setExpandRatio(btnSignUp, 1f)
        btnTempSignUp.styleName = "borderless"
        btnTempSignUp.setStyleName("icon-only", true)
        btnTempSignUp.setStyleName("validate-status", true)
        btnTempSignUp.icon = VaadinIcons.CHECK
        btnTempSignUp.isEnabled = false
        btnTempSignUp.isVisible = true
        btnSignUpPart.addComponent(btnTempSignUp)
        btnSignUpPart.setComponentAlignment(btnTempSignUp, Alignment.BOTTOM_RIGHT)
        addComponent(btnSignUpPart)
        setComponentAlignment(btnSignUpPart, Alignment.TOP_LEFT)
    }

    private fun setEvent() {
        btnSignUp.addClickListener {
            if (!Utils.isValidUsername(fullName)) {
                LogUtils.error("invalid username")
                return@addClickListener
            }
            if (!Utils.isValidEmail(email)) {
                LogUtils.error("invalid email")
                return@addClickListener
            }
            if (!Utils.isValidPassword(password)) {
                LogUtils.error("invalid password")
                return@addClickListener
            }
            if (!Utils.isValidPasswordConfirm(passwordConfirm, password)) {
                LogUtils.error("confirm password not match")
                return@addClickListener
            }

            AuthService.register(
                guestBean.token!!,
                fullName.value,
                email.value,
                password.value,
                eventHandler.appPath ?: "?"
            )?.apply {
                LogUtils.error("register result: $redirectUrl")

//            if (1 > 0) {
//                // email usable
//                val error = Messages.get("error.sign_up.failed2") +
//                    " " +
//                    email.value +
//                    " " +
//                    Messages.get("error.sign_up.failed3")
//                Notification.show(Messages.get("error.sign_up.failed"), error, Notification.Type.HUMANIZED_MESSAGE)
//                val userError = UserError(Messages.get("error.email.already.used"))
//                email.componentError = userError
//                btnEmailCorrect.removeStyleName("validate-status-correct")
//            } else {
//                val newUser = Customer(fullName.value, email.value, password.value)
//                val token = UUID.randomUUID().toString()
//                val verificationToken = VerificationToken(token, newUser.customerId)
//                verificationTokenRepo.save(verificationToken)
//                eventHandler.signedUp(verificationToken, newUser)
//            }
            }
        }
    }

}
