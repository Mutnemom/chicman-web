package com.chicman.view.signup;

import com.chicman.ChicManUI;
import com.chicman.model.Customer;
import com.chicman.model.VerificationToken;
import com.chicman.repository.CustomerRepository;
import com.chicman.repository.VerificationTokenRepository;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import java.util.List;
import java.util.UUID;

@SpringComponent
@UIScope
public class SignUp extends VerticalLayout {

	private final CustomerRepository customerRepo;
	private final VerificationTokenRepository verificationTokenRepo;
	private final ChicManUI eventHandler;
	private final TextField fullName;
	private final TextField email;
	private final PasswordField password;
    private final PasswordField passwordConfirm;
	private final Button btnSignUp;
    private Button btnUsernameCorrect;
    private Button btnEmailCorrect;
    private Button btnPasswordCorrect;
    private Button btnPasswordConfirmCorrect;
    private Button btnTemp;

    public SignUp(CustomerRepository customerRepo, VerificationTokenRepository verificationTokenRepo, ChicManUI eventHandler) {
		this.customerRepo = customerRepo;
		this.verificationTokenRepo = verificationTokenRepo;
		this.eventHandler = eventHandler;
		this.fullName = new TextField(Messages.get("auth.username"));
		this.email = new TextField(Messages.get("auth.email"));
		this.password = new PasswordField(Messages.get("auth.password"));
		this.passwordConfirm = new PasswordField(Messages.get("auth.password.confirm"));
		this.btnSignUp = new Button(Messages.get("auth.register"));
		this.btnUsernameCorrect = new Button();
        this.btnEmailCorrect = new Button();
        this.btnPasswordCorrect = new Button();
        this.btnPasswordConfirmCorrect = new Button();
        this.btnTemp = new Button();

		setupComponent();
		setEvent();
        JavaScript javaScript = Page.getCurrent().getJavaScript();
        javaScript.execute("enter2ClickButton(" + fullName.getId() + ", " + btnSignUp.getId() + ")");
        javaScript.execute("enter2ClickButton(" + email.getId() + ", " + btnSignUp.getId() + ")");
        javaScript.execute("enter2ClickButton(" + password.getId() + ", " + btnSignUp.getId() + ")");
        javaScript.execute("enter2ClickButton(" + passwordConfirm.getId() + ", " + btnSignUp.getId() + ")");
	}

	private void addUsernamePart() {
        HorizontalLayout usernamePart = new HorizontalLayout();
        usernamePart.setMargin(false);
        usernamePart.setSpacing(false);
        usernamePart.setWidth("100%");

        fullName.setId("fullNameSignUp");
        fullName.setWidth("100%");

        fullName.addFocusListener(e -> {
            fullName.setComponentError(null);
            if (fullName.getValue().length() > 0) {
                fullName.setCursorPosition(fullName.getValue().length());
            }
        });

        fullName.addBlurListener(e -> {
            if (fullName.getValue().length() > 0) {
                fullName.setComponentError(null);
                btnUsernameCorrect.setStyleName("validate-status-correct", true);
            } else {
                fullName.setComponentError(new UserError(Messages.get("auth.username.blank")));
                btnUsernameCorrect.removeStyleName("validate-status-correct");
            }
            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        usernamePart.addComponent(fullName);
        usernamePart.setComponentAlignment(fullName, Alignment.TOP_LEFT);
        usernamePart.setExpandRatio(fullName, 1);

        btnUsernameCorrect.setStyleName("borderless", true);
        btnUsernameCorrect.setStyleName("icon-only", true);
        btnUsernameCorrect.setStyleName("validate-status", true);
        btnUsernameCorrect.setIcon(VaadinIcons.CHECK);
        btnUsernameCorrect.setEnabled(false);
        btnUsernameCorrect.setVisible(true);

        usernamePart.addComponent(btnUsernameCorrect);
        usernamePart.setComponentAlignment(btnUsernameCorrect, Alignment.BOTTOM_RIGHT);

		addComponent(usernamePart);
		setComponentAlignment(usernamePart, Alignment.TOP_LEFT);
    }

    private void addEmailPart() {
        HorizontalLayout emailPart = new HorizontalLayout();
        emailPart.setMargin(false);
        emailPart.setSpacing(false);
        emailPart.setWidth("100%");

        email.setId("emailSignUp");
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
    }

    private void addPasswordPart() {
        HorizontalLayout passwordPart = new HorizontalLayout();
        passwordPart.setMargin(false);
        passwordPart.setSpacing(false);
        passwordPart.setWidth("100%");

        password.setCaption(Messages.get("auth.password") +
                "&nbsp;" +
                "<span class='password-suggest'>" +
                Messages.get("auth.password.suggest") + "</span>");
        password.setCaptionAsHtml(true);

		password.setId("passwordSignUp");
		password.setWidth("100%");
		password.setMaxLength(10);

        password.addFocusListener(e -> {
            password.setComponentError(null);
            if (password.getValue().length() > 0) {
                password.setCursorPosition(password.getValue().length());
            }
        });

        password.addBlurListener(e -> {
            if (Utils.isValidPassword(password)) {
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
    }

    private void addPasswordConfirmPart() {
        HorizontalLayout passwordConfirmPart = new HorizontalLayout();
        passwordConfirmPart.setMargin(false);
        passwordConfirmPart.setSpacing(false);
        passwordConfirmPart.setWidth("100%");

		passwordConfirm.setId("passwordConfirmSignUp");
		passwordConfirm.setWidth("100%");
		passwordConfirm.setMaxLength(10);

        passwordConfirm.addFocusListener(e -> {
            passwordConfirm.setComponentError(null);
            if (passwordConfirm.getValue().length() > 0) {
                passwordConfirm.setCursorPosition(passwordConfirm.getValue().length());
            }
        });

        passwordConfirm.addBlurListener(e -> {
            if (Utils.isValidPasswordConfirm(passwordConfirm, password)) {
                btnPasswordConfirmCorrect.addStyleName("validate-status-correct");
            } else {
                btnPasswordConfirmCorrect.removeStyleName("validate-status-correct");
            }

            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        passwordConfirmPart.addComponent(passwordConfirm);
        passwordConfirmPart.setComponentAlignment(passwordConfirm, Alignment.TOP_LEFT);
        passwordConfirmPart.setExpandRatio(passwordConfirm, 1);

        btnPasswordConfirmCorrect.setStyleName("borderless");
        btnPasswordConfirmCorrect.setStyleName("icon-only", true);
        btnPasswordConfirmCorrect.setStyleName("validate-status", true);
        btnPasswordConfirmCorrect.setIcon(VaadinIcons.CHECK);
        btnPasswordConfirmCorrect.setEnabled(false);
        btnPasswordConfirmCorrect.setVisible(true);
        passwordConfirmPart.addComponent(btnPasswordConfirmCorrect);
        passwordConfirmPart.setComponentAlignment(btnPasswordConfirmCorrect, Alignment.BOTTOM_RIGHT);

		addComponent(passwordConfirmPart);
		setComponentAlignment(passwordConfirmPart, Alignment.TOP_LEFT);
    }

    private void addBtnSignUpPart() {
        HorizontalLayout btnSignUpPart = new HorizontalLayout();
        btnSignUpPart.setMargin(false);
        btnSignUpPart.setSpacing(false);
        btnSignUpPart.setWidth("100%");

		btnSignUp.setStyleName("primary");
		btnSignUp.setWidth("100%");
		btnSignUp.setId("btnSignUp");
        btnSignUp.addBlurListener(e -> Page.getCurrent().getJavaScript().execute("getFocused()"));
		btnSignUpPart.addComponent(btnSignUp);
        btnSignUpPart.setComponentAlignment(btnSignUp, Alignment.TOP_LEFT);
        btnSignUpPart.setExpandRatio(btnSignUp, 1);

        btnTemp.setStyleName("borderless");
        btnTemp.setStyleName("icon-only", true);
        btnTemp.setStyleName("validate-status", true);
        btnTemp.setIcon(VaadinIcons.CHECK);
        btnTemp.setEnabled(false);
        btnTemp.setVisible(true);
        btnSignUpPart.addComponent(btnTemp);
        btnSignUpPart.setComponentAlignment(btnTemp, Alignment.BOTTOM_RIGHT);

        addComponent(btnSignUpPart);
        setComponentAlignment(btnSignUpPart, Alignment.TOP_LEFT);
    }
	
	private void setupComponent() {
	    addUsernamePart();
	    addEmailPart();
	    addPasswordPart();
	    addPasswordConfirmPart();
	    addBtnSignUpPart();

	    btnSignUp.focus();
	    fullName.setTabIndex(1);
	    email.setTabIndex(2);
	    password.setTabIndex(3);
	    passwordConfirm.setTabIndex(4);
	    btnSignUp.setTabIndex(5);
	}
	
	private void setEvent() {
		btnSignUp.addClickListener(e -> {
		    if (!Utils.isValidUsername(fullName)) return;
		    if (!Utils.isValidEmail(email)) return;
		    if (!Utils.isValidPassword(password)) return;
            if (!Utils.isValidPasswordConfirm(passwordConfirm, password)) return;

            List<Customer> storedCustomer = customerRepo.findByEmail(email.getValue());
            if (storedCustomer.size() > 0) {
                // email usable
                String error = Messages.get("error.sign_up.failed2") +
                        " " +
                        email.getValue() +
                        " " +
                        Messages.get("error.sign_up.failed3");

                Notification.show(Messages.get("error.sign_up.failed"), error, Notification.Type.HUMANIZED_MESSAGE);

                UserError userError = new UserError(Messages.get("error.email.already.used"));
                email.setComponentError(userError);
                btnEmailCorrect.removeStyleName("validate-status-correct");
            } else {
                Customer newUser = new Customer(fullName.getValue(), email.getValue(), password.getValue());
                customerRepo.save(newUser);

                String token = UUID.randomUUID().toString();
                VerificationToken verificationToken = new VerificationToken(token, newUser.getCustomerId());
                verificationTokenRepo.save(verificationToken);

                eventHandler.signedUp(verificationToken, newUser);
            }
		});
	}
}
