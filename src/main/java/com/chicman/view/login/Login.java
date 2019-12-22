package com.chicman.view.login;

import com.chicman.ChicManUI;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@SpringComponent
@UIScope
public class Login extends VerticalLayout {

	private final ChicManUI eventHandler;
	private final TextField email;
	private final PasswordField password;
	private final Button btnLogin;
	private final Button btnSignup;
	private final Button btnEmailCorrect;
    private final Button btnPasswordCorrect;
    private final Button btnTemp;

	public Login(ChicManUI eventHandler) {
		this.eventHandler = eventHandler;
		email = new TextField(Messages.get("auth.email"));
		password = new PasswordField();
		btnLogin = new Button(Messages.get("auth.login"));
		btnSignup = new Button(Messages.get("auth.register"));
		btnEmailCorrect = new Button();
		btnPasswordCorrect = new Button();
		btnTemp = new Button();

		setupComponent();
		setEvent();
		JavaScript javaScript = Page.getCurrent().getJavaScript();
        javaScript.execute("enter2ClickButton(" + email.getId() + ", " + btnLogin.getId() + ")");
		javaScript.execute("enter2ClickButton(" + password.getId() + ", " + btnLogin.getId() + ")");
    }
	
	private void setupComponent() {
        HorizontalLayout emailPart = new HorizontalLayout();
        emailPart.setMargin(false);
        emailPart.setSpacing(false);
        emailPart.setWidth("100%");

        email.setId("emailLogin");
        email.setPlaceholder(Messages.get("auth.email.placeholder"));
        email.setWidth("100%");

        email.addFocusListener(e -> {
            email.setComponentError(null);
            if (email.getValue().length() > 0) {
                email.setCursorPosition(email.getValue().length());
            } else {
                email.setPlaceholder(Messages.get("auth.email.placeholder"));
            }
        });

        email.addBlurListener(e -> {
            if (Utils.isValidEmail(email)) {
                btnEmailCorrect.setStyleName("validate-status-correct", true);
            } else {
                btnEmailCorrect.removeStyleName("validate-status-correct");
            }

            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        emailPart.addComponent(email);
        emailPart.setComponentAlignment(email, Alignment.TOP_LEFT);
        emailPart.setExpandRatio(email, 1);

        btnEmailCorrect.setStyleName("borderless", true);
        btnEmailCorrect.setStyleName("icon-only", true);
        btnEmailCorrect.setStyleName("validate-status", true);
        btnEmailCorrect.setIcon(VaadinIcons.CHECK);
        btnEmailCorrect.setEnabled(false);
        btnEmailCorrect.setVisible(true);
        emailPart.addComponent(btnEmailCorrect);
        emailPart.setComponentAlignment(btnEmailCorrect, Alignment.BOTTOM_RIGHT);
		addComponent(emailPart);
		setComponentAlignment(emailPart, Alignment.TOP_LEFT);

        HorizontalLayout passwordPart = new HorizontalLayout();
        passwordPart.setMargin(false);
        passwordPart.setSpacing(false);
        passwordPart.setWidth("100%");

        password.setCaption(Messages.get("auth.password"));
		password.setId("passwordLogin");
		password.setWidth("100%");
		password.setMaxLength(10);

        password.addFocusListener(e -> {
            password.setComponentError(null);
            if (password.getValue().length() > 0) {
                password.setCursorPosition(password.getValue().length());
            }
        });

        password.addBlurListener(e -> {
            if (Utils.isValidPasswordLogin(password)) {
                btnPasswordCorrect.setStyleName("validate-status-correct", true);
            } else {
                btnPasswordCorrect.removeStyleName("validate-status-correct");
            }

            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        passwordPart.addComponent(password);
        passwordPart.setComponentAlignment(password, Alignment.TOP_LEFT);
        passwordPart.setExpandRatio(password, 1);

        btnPasswordCorrect.setStyleName("borderless");
        btnPasswordCorrect.setStyleName("icon-only", true);
        btnPasswordCorrect.setStyleName("validate-status", true);
        btnPasswordCorrect.setIcon(VaadinIcons.CHECK);
        btnPasswordCorrect.setEnabled(false);
        btnPasswordCorrect.setVisible(true);
        passwordPart.addComponent(btnPasswordCorrect);
        passwordPart.setComponentAlignment(btnPasswordCorrect, Alignment.BOTTOM_RIGHT);

		addComponent(passwordPart);
		setComponentAlignment(passwordPart, Alignment.TOP_LEFT);

        HorizontalLayout btnLoginPart = new HorizontalLayout();
        btnLoginPart.setMargin(false);
        btnLoginPart.setSpacing(false);
        btnLoginPart.setWidth("100%");

		btnLogin.setStyleName("primary");
		btnLogin.setWidth("100%");
		btnLogin.setId("btnLogin");
        btnLogin.addBlurListener(e -> Page.getCurrent().getJavaScript().execute("getFocused()"));
		btnLoginPart.addComponent(btnLogin);
        btnLoginPart.setComponentAlignment(btnLogin, Alignment.TOP_LEFT);
        btnLoginPart.setExpandRatio(btnLogin, 1);

        btnTemp.setStyleName("borderless");
        btnTemp.setStyleName("icon-only", true);
        btnTemp.setStyleName("validate-status", true);
        btnTemp.setIcon(VaadinIcons.CHECK);
        btnTemp.setEnabled(false);
        btnTemp.setVisible(true);
        btnLoginPart.addComponent(btnTemp);
        btnLoginPart.setComponentAlignment(btnTemp, Alignment.BOTTOM_RIGHT);

        addComponent(btnLoginPart);
        setComponentAlignment(btnLoginPart, Alignment.TOP_LEFT);

		btnSignup.setId("linkSignUp");
		btnSignup.setWidthUndefined();
		btnSignup.setStyleName("borderless");
		btnSignup.setStyleName("active-noborder", true);
		btnSignup.setStyleName("colored-when-hover", true);
		addComponent(btnSignup);
		setComponentAlignment(btnSignup, Alignment.BOTTOM_RIGHT);

        btnLogin.focus();
        email.setTabIndex(1);
        password.setTabIndex(2);
        btnLogin.setTabIndex(3);
	}
	
	private void setEvent() {
		btnLogin.addClickListener(e -> {
			if (Utils.isValidEmail(email)) {
				if (Utils.isValidPasswordLogin(password)) {
					if (!eventHandler.login(email.getValue(), password.getValue())) {
					    btnLogin.focus();
                    }
				}
			}
		});
		
		btnSignup.addClickListener(e -> eventHandler.showSignUpPopup());
	}
	
}
