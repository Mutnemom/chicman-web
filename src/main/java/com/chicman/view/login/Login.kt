package com.chicman.view.login

import com.chicman.ChicManUI
import com.chicman.utility.Messages
import com.chicman.utility.Utils
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.Page
import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.UIScope
import com.vaadin.ui.*

@SpringComponent
@UIScope
class Login(private val eventHandler: ChicManUI) : VerticalLayout() {

    companion object {
        private const val VALID_FIELD = "validate-status-correct"
        private const val FIELD_PLACEHOLDER = "auth.email.placeholder"
    }

    private val email: TextField = TextField(Messages.get("auth.email"))
    private val password: PasswordField = PasswordField(Messages.get("auth.password"))
    private val btnLogin: Button = Button(Messages.get("auth.login"))
    private val btnSignUp: Button = Button(Messages.get("auth.register"))
    private val btnEmailCorrect: Button = Button()
    private val btnPasswordCorrect: Button = Button()
    private val btnTemp: Button = Button()

    private fun setupComponent() {
        val emailPart = HorizontalLayout()
        emailPart.matchParent()
        email.apply {
            id = "emailLogin"
            placeholder = Messages.get(FIELD_PLACEHOLDER)

            addFocusListener {
                componentError = null
                when {
                    value.isNotEmpty() -> cursorPosition = value.length
                    else -> placeholder = Messages.get(FIELD_PLACEHOLDER)
                }
            }

            addCustomBlurListener(btnEmailCorrect)
            emailPart.addChild(this)
        }
        emailPart.addChildBottomRight(btnEmailCorrect)

        val passwordPart = HorizontalLayout()
        passwordPart.matchParent()
        password.apply {
            id = "passwordLogin"
            maxLength = 10

            addFocusListener {
                componentError = null
                if (value.isNotEmpty()) cursorPosition = value.length
            }

            addCustomBlurListener(btnPasswordCorrect)
            passwordPart.addChild(this)
        }
        passwordPart.addChildBottomRight(btnPasswordCorrect)

        val btnLoginPart = HorizontalLayout()
        btnLoginPart.matchParent()
        btnLogin.id = "btnLogin"
        btnLogin.styleName = "primary"
        btnLogin.addBlurListener { Page.getCurrent().javaScript.execute("getFocused()") }

        btnLoginPart.addChild(btn= btnLogin)
        btnTemp.setStyleCorrect()
        btnLoginPart.addComponent(btnTemp)
        btnLoginPart.setComponentAlignment(btnTemp, Alignment.BOTTOM_RIGHT)
        addComponent(btnLoginPart)
        setComponentAlignment(btnLoginPart, Alignment.TOP_LEFT)
        btnSignUp.id = "linkSignUp"
        btnSignUp.setWidthUndefined()
        btnSignUp.styleName = "borderless"
        btnSignUp.setStyleName("active-noborder", true)
        btnSignUp.setStyleName("colored-when-hover", true)
        addComponent(btnSignUp)
        setComponentAlignment(btnSignUp, Alignment.BOTTOM_RIGHT)
        btnLogin.focus()
        email.tabIndex = 1
        password.tabIndex = 2
        btnLogin.tabIndex = 3
    }

    private fun TextField.addCustomBlurListener(btn: Button) {
        addBlurListener {
            when (Utils.isValidPasswordLogin(this as PasswordField)) {
                true -> btn.setStyleName(VALID_FIELD, true)
                false -> btn.removeStyleName(VALID_FIELD)
            }
            Page.getCurrent().javaScript.execute("getFocused()")
        }
    }

    private fun Button.setStyleCorrect() {
        setStyleName("validate-status", true)
        setStyleName("borderless", true)
        setStyleName("icon-only", true)

        icon = VaadinIcons.CHECK
        isEnabled = false
        isVisible = true
    }

    private fun HorizontalLayout.addChild(tf: TextField? = null, btn: Button? = null) {
        tf?.setWidth("100%")
        btn?.setWidth("100%")
        addComponent(tf ?: btn!!)
        setExpandRatio(tf ?: btn, 1f)
        setComponentAlignment(tf ?: btn!!, Alignment.TOP_LEFT)
    }

    private fun HorizontalLayout.addChildBottomRight(btn: Button) {
        btn.setStyleCorrect()
        addComponent(btn)
        setComponentAlignment(btn, Alignment.BOTTOM_RIGHT)

        this@Login.addComponent(this)
        this@Login.setComponentAlignment(this, Alignment.TOP_LEFT)
    }

    private fun HorizontalLayout.matchParent() {
        isSpacing = false
        setMargin(false)
        setWidth("100%")
    }

    private fun setEvent() {
        btnSignUp.addClickListener { eventHandler.showSignUpPopup() }
        btnLogin.addClickListener { validateForm() }
    }

    private fun validateForm() {
        if (Utils.isValidEmail(email)) {
            if (Utils.isValidPasswordLogin(password)) {
                if (!eventHandler.login(email.value, password.value)) {
                    btnLogin.focus()
                }
            }
        }
    }

    init {
        setupComponent()
        setEvent()
        Page.getCurrent().javaScript.apply {
            execute("enter2ClickButton(${email.id}, ${btnLogin.id})")
            execute("enter2ClickButton(${password.id}, ${btnLogin.id})")
        }

//        javaScript.execute("enter2ClickButton(" + email.id + ", " + btnLogin.id + ")")
//        javaScript.execute("enter2ClickButton(" + password.id + ", " + btnLogin.id + ")")
    }

}
