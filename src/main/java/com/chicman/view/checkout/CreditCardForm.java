package com.chicman.view.checkout;

import com.chicman.ChicManUI;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@SpringComponent
@UIScope
public class CreditCardForm extends VerticalLayout {

    private final ChicManUI eventHandler;
	private final TextField textCardNumber;
	private final TextField textNameOnCard;
	private final TextField textExpDate;
	private final TextField textSecCode;
	private final Button btnCheckout;
	private final Button btnCardNumberCorrect;
    private final Button btnCardNameCorrect;
    private final Button btnExpireCorrect;
    private final Button btnSecCodeCorrect;
    private final Button btnCancel;
    private boolean isCompleteFillNumber;
    private boolean isCompleteFillName;
    private boolean isCompleteFillExpire;
    private boolean isCompleteFillSecCode;

    public CreditCardForm(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;
		this.textCardNumber = new TextField();
		this.textNameOnCard = new TextField();
		this.textExpDate = new TextField();
		this.textSecCode = new TextField();
		this.btnCheckout = new Button();
		this.btnCardNumberCorrect = new Button();
        this.btnCardNameCorrect = new Button();
        this.btnExpireCorrect = new Button();
        this.btnSecCodeCorrect = new Button();
        this.btnCancel = new Button();
        this.isCompleteFillNumber = false;
        this.isCompleteFillName = false;
        this.isCompleteFillExpire = false;
        this.isCompleteFillSecCode = false;

        setupComponents();
		setEvent();

        JavaScript javaScript = Page.getCurrent().getJavaScript();
        javaScript.execute("acceptNumber(" + textCardNumber.getId() + ")");
        javaScript.execute("acceptNumber(" + textExpDate.getId() + ")");
        javaScript.execute("acceptNumber(" + textSecCode.getId() + ")");
        javaScript.execute("acceptLetter(" + textNameOnCard.getId() + ")");

        javaScript.execute("enter2ClickButton(" + textCardNumber.getId() + ", " + btnCheckout.getId() + ")");
        javaScript.execute("enter2ClickButton(" + textNameOnCard.getId() + ", " + btnCheckout.getId() + ")");
        javaScript.execute("enter2ClickButton(" + textExpDate.getId() + ", " + btnCheckout.getId() + ")");
        javaScript.execute("enter2ClickButton(" + textSecCode.getId() + ", " + btnCheckout.getId() + ")");
	}

	private void setupComponents() {
        setStyleName("credit_card_form");
        setWidthUndefined();

	    addCardNumberPart();
	    addCardNamePart();
	    addExpiryPart();
	    addSecurityPart();
	    addButtonPart();

	    btnCheckout.focus();
	    textCardNumber.setTabIndex(1);
	    textNameOnCard.setTabIndex(2);
	    textExpDate.setTabIndex(3);
	    textSecCode.setTabIndex(4);
	    btnCheckout.setTabIndex(5);
    }

    private void addCardNumberPart() {
        HorizontalLayout cardNumberPart = new HorizontalLayout();
        cardNumberPart.setMargin(false);
        cardNumberPart.setSpacing(false);
        cardNumberPart.setWidth("100%");

        textCardNumber.setId("card_number");
        textCardNumber.setCaption(Messages.get("form.credit.number"));
        textCardNumber.setWidth("100%");
        textCardNumber.setMaxLength(19);

        textCardNumber.addFocusListener(e -> {
            textCardNumber.setComponentError(null);
            if (textCardNumber.getValue().length() > 0) {
                textCardNumber.setCursorPosition(textCardNumber.getValue().length());
            }
        });

        textCardNumber.addBlurListener(e -> {
            if (textCardNumber.getValue().length() == 19) {
                btnCardNumberCorrect.addStyleName("validate-status-correct");
                isCompleteFillNumber = true;
            } else {
                btnCardNumberCorrect.removeStyleName("validate-status-correct");
                textCardNumber.setComponentError(new UserError(Messages.get("form.credit.number.error")));
                isCompleteFillNumber = false;
            }

            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        textCardNumber.addValueChangeListener(e -> {
            String value = e.getValue();
            value = value.trim();
            value = value.replace("-", "");

            if (value.length() >= 4) {
                if (value.length() == 4) {
                    value += "-";
                } else {
                    String left = value.substring(0, 4);
                    String right = value.substring(4);
                    value = left + "-" + right;
                }

                if (value.length() >= 9) {
                    if (value.length() == 9) {
                        value += "-";
                    } else {
                        String left = value.substring(0, 9);
                        String right = value.substring(9);
                        value = left + "-" + right;
                    }

                    if (value.length() >= 14) {
                        if (value.length() == 14) {
                            value += "-";
                        } else {
                            String left = value.substring(0, 14);
                            String right = value.substring(14);
                            value = left + "-" + right;
                        }
                    }
                }
            }

            textCardNumber.setValue(value);
        });

        cardNumberPart.addComponent(textCardNumber);
        cardNumberPart.setComponentAlignment(textCardNumber, Alignment.TOP_LEFT);
        cardNumberPart.setExpandRatio(textCardNumber, 1);

        btnCardNumberCorrect.setStyleName("borderless");
        btnCardNumberCorrect.setStyleName("icon-only", true);
        btnCardNumberCorrect.setStyleName("validate-status", true);
        btnCardNumberCorrect.setIcon(VaadinIcons.CHECK);
        btnCardNumberCorrect.setEnabled(false);

        cardNumberPart.addComponent(btnCardNumberCorrect);
        cardNumberPart.setComponentAlignment(btnCardNumberCorrect, Alignment.BOTTOM_RIGHT);

		addComponent(cardNumberPart);
		setComponentAlignment(cardNumberPart, Alignment.TOP_LEFT);
	}

	private void addCardNamePart() {
	    HorizontalLayout cardNamePart = new HorizontalLayout();
        cardNamePart.setMargin(false);
        cardNamePart.setSpacing(false);
        cardNamePart.setWidth("100%");

        textNameOnCard.setId("name_oncard");
        textNameOnCard.setCaption(Messages.get("form.credit.name"));
        textNameOnCard.setWidth("100%");

        textNameOnCard.addFocusListener(e -> {
            textNameOnCard.setComponentError(null);
            if (textNameOnCard.getValue().length() > 0) {
                textNameOnCard.setCursorPosition(textNameOnCard.getValue().length());
            }
        });

        textNameOnCard.addBlurListener(e -> {
            if (textNameOnCard.getValue().length() > 0) {
                btnCardNameCorrect.addStyleName("validate-status-correct");
                isCompleteFillName = true;
            } else {
                btnCardNameCorrect.removeStyleName("validate-status-correct");
                textNameOnCard.setComponentError(new UserError(Messages.get("form.credit.name.error")));
                isCompleteFillName = false;
            }

            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        cardNamePart.addComponent(textNameOnCard);
        cardNamePart.setComponentAlignment(textNameOnCard, Alignment.TOP_LEFT);
        cardNamePart.setExpandRatio(textNameOnCard, 1);

        btnCardNameCorrect.setStyleName("borderless");
        btnCardNameCorrect.setStyleName("icon-only", true);
        btnCardNameCorrect.setStyleName("validate-status", true);
        btnCardNameCorrect.setIcon(VaadinIcons.CHECK);
        btnCardNameCorrect.setEnabled(false);

        cardNamePart.addComponent(btnCardNameCorrect);
        cardNamePart.setComponentAlignment(btnCardNameCorrect, Alignment.BOTTOM_RIGHT);

		addComponent(cardNamePart);
		setComponentAlignment(cardNamePart, Alignment.TOP_LEFT);
    }

    private void addExpiryPart() {
        HorizontalLayout expiryPart = new HorizontalLayout();
        expiryPart.setMargin(false);
        expiryPart.setSpacing(false);
        expiryPart.setWidth("100%");

        textExpDate.setId("expiry_date");
        textExpDate.setCaption(Messages.get("form.credit.expire"));
        textExpDate.setPlaceholder(Messages.get("form.credit.expire.placeholder"));
        textExpDate.setWidth("100%");
        textExpDate.setMaxLength(5);

        textExpDate.addFocusListener(e -> {
            textExpDate.setComponentError(null);
            if (textExpDate.getValue().length() > 0) {
                textExpDate.setCursorPosition(textExpDate.getValue().length());
            } else {
                textExpDate.setPlaceholder(Messages.get("form.credit.expire.placeholder"));
            }
        });

        textExpDate.addBlurListener(e -> {
            if (textExpDate.getValue().length() > 0) {
                if (Utils.isValidExpiryDate(textExpDate)) {
                    btnExpireCorrect.addStyleName("validate-status-correct");
                    isCompleteFillExpire = true;
                } else {
                    btnExpireCorrect.removeStyleName("validate-status-correct");
                    isCompleteFillExpire = false;
                }
            } else {
                btnExpireCorrect.removeStyleName("validate-status-correct");
                textExpDate.setComponentError(new UserError(Messages.get("form.credit.expire.error")));
                isCompleteFillExpire = false;
            }

            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        textExpDate.addValueChangeListener(e -> {
            if (e.getValue().length() == 2) {
                String oldValue = textExpDate.getValue();
                textExpDate.setValue(oldValue + "/");
            }
        });

        expiryPart.addComponent(textExpDate);
        expiryPart.setComponentAlignment(textExpDate, Alignment.TOP_LEFT);
        expiryPart.setExpandRatio(textExpDate, 1);

        btnExpireCorrect.setStyleName("borderless");
        btnExpireCorrect.setStyleName("icon-only", true);
        btnExpireCorrect.setStyleName("validate-status", true);
        btnExpireCorrect.setIcon(VaadinIcons.CHECK);
        btnExpireCorrect.setEnabled(false);

        expiryPart.addComponent(btnExpireCorrect);
        expiryPart.setComponentAlignment(btnExpireCorrect, Alignment.BOTTOM_RIGHT);

		addComponent(expiryPart);
		setComponentAlignment(expiryPart, Alignment.TOP_LEFT);
    }

    private void  addSecurityPart() {
        HorizontalLayout securityPart = new HorizontalLayout();
        securityPart.setMargin(false);
        securityPart.setSpacing(false);
        securityPart.setWidth("100%");

        textSecCode.setId("security_code");
        textSecCode.setCaption(Messages.get("form.credit.secure.cvv"));
        textSecCode.setWidth("100%");
        textSecCode.setMaxLength(3);

        textSecCode.addFocusListener(e -> {
            textSecCode.setComponentError(null);
            if (textSecCode.getValue().length() > 0) {
                textSecCode.setCursorPosition(textSecCode.getValue().length());
            }
        });

        textSecCode.addBlurListener(e -> {
            if (textSecCode.getValue().length() == 3) {
                btnSecCodeCorrect.addStyleName("validate-status-correct");
                isCompleteFillSecCode = true;
            } else {
                btnSecCodeCorrect.removeStyleName("validate-status-correct");
                textSecCode.setComponentError(new UserError(Messages.get("form.credit.secure.error")));
                isCompleteFillSecCode = false;
            }

            Page.getCurrent().getJavaScript().execute("getFocused()");
        });

        securityPart.addComponent(textSecCode);
        securityPart.setComponentAlignment(textSecCode, Alignment.TOP_LEFT);
        securityPart.setExpandRatio(textSecCode, 1);

        btnSecCodeCorrect.setStyleName("borderless");
        btnSecCodeCorrect.setStyleName("icon-only", true);
        btnSecCodeCorrect.setStyleName("validate-status", true);
        btnSecCodeCorrect.setIcon(VaadinIcons.CHECK);
        btnSecCodeCorrect.setEnabled(false);

        securityPart.addComponent(btnSecCodeCorrect);
        securityPart.setComponentAlignment(btnSecCodeCorrect, Alignment.BOTTOM_RIGHT);

		addComponent(securityPart);
		setComponentAlignment(securityPart, Alignment.TOP_LEFT);
    }

    private void addButtonPart() {
        HorizontalLayout btnCheckoutPart = new HorizontalLayout();
        btnCheckoutPart.setId("formCreditCheckoutPart");
        btnCheckoutPart.setMargin(false);
        btnCheckoutPart.setSpacing(false);
        btnCheckoutPart.setWidth("100%");

        btnCheckout.setId("formCreditCheckoutBtn");
        btnCheckout.setCaption(Messages.get("form.credit.confirm"));
        btnCheckout.setStyleName("primary");
        btnCheckout.setStyleName("confirm_button", true);
        btnCheckout.setWidth("100%");

        btnCheckout.addBlurListener(e -> Page.getCurrent().getJavaScript().execute("getFocused()"));
		btnCheckoutPart.addComponent(btnCheckout);
        btnCheckoutPart.setComponentAlignment(btnCheckout, Alignment.TOP_LEFT);
        btnCheckoutPart.setExpandRatio(btnCheckout, 1);

        btnCancel.setId("formCreditCancelBtn");
        btnCancel.setCaption(Messages.get("cancel"));
        btnCheckoutPart.addComponent(btnCancel);
        btnCheckoutPart.setComponentAlignment(btnCancel, Alignment.TOP_LEFT);

        addComponent(btnCheckoutPart);
        setComponentAlignment(btnCheckoutPart, Alignment.TOP_LEFT);
    }
	
	private void setEvent() {
		btnCheckout.addClickListener(e -> {
		    if (isCompleteFillNumber && isCompleteFillName && isCompleteFillExpire && isCompleteFillSecCode) {
		        JavaScript.getCurrent().execute("genCreditCardToken()");
            }
        });

		btnCancel.addClickListener(e -> eventHandler.getFloatWindow().close());
	}

}
