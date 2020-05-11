package com.chicman.utility;

import com.chicman.model.Product;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.Position;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Utils {

    public static void notify(String message, int delay, Position position, Resource icon) {
        Notification notification = new Notification(message);
        notification.setDelayMsec(delay);
        notification.setPosition(position);
        notification.setIcon(icon);
        notification.show(Page.getCurrent());
    }

    public static String float2Text(float floatNumber) {
    	String textNumber = String.valueOf(floatNumber);
    	textNumber = textNumber.trim();
    	textNumber = textNumber.substring(0, textNumber.indexOf("."));

    	StringBuilder stringBuilder = new StringBuilder();
    	if (textNumber.length() > 6) {
    	    stringBuilder.append(textNumber.substring(0, textNumber.length() - 6));
    	    stringBuilder.append(",");
    	    textNumber = textNumber.substring(textNumber.length() - 6);
    	    textNumber = textNumber.substring(textNumber.indexOf(",") + 1);
        }

        if (textNumber.length() > 3) {
    	    stringBuilder.append(textNumber.substring(0, textNumber.length() - 3));
    	    stringBuilder.append(",");
    	    stringBuilder.append(textNumber.substring(textNumber.length() - 3));
        } else {
    		stringBuilder.append(textNumber);
		}
    	return stringBuilder.toString();
    }

	public static boolean isValidUsername(TextField username) {
		return username.getValue().matches("[A-Za-z0-9]+");
	}

	public static boolean isValidPasswordLogin(PasswordField password) {
        StringLengthValidator validator = new StringLengthValidator(Messages.get("error.password"), 6, 10);
		ValidationResult validationResult = validator.apply(password.getValue(), new ValueContext(password));

		boolean error = validationResult.isError();
		UserError userError;
		if (error) {
			userError = new UserError(validationResult.getErrorMessage());
			password.setComponentError(userError);
		} else {
            password.setComponentError(null);
		}

		return !error;
    }

	public static boolean isValidEmail(TextField email) {
        email.setPlaceholder("");
        EmailValidator validator = new EmailValidator(Messages.get("error.email"));
		ValidationResult result = validator.apply(email.getValue(), new ValueContext(email));
		
		if (result.isError()) {
			UserError userError = new UserError(result.getErrorMessage());
			email.setComponentError(userError);
		} else {
			email.setComponentError(null);
		}
		
		return !result.isError();
	}

	public static boolean isValidExpiryDate(TextField expiryDate) {
        expiryDate.setPlaceholder(Messages.get("form.credit.expire.placeholder"));
        boolean error = false;
        String value = expiryDate.getValue();
        UserError userError = new UserError(Messages.get("form.credit.expire.error"));
        if (value.length() >= 4) {
            if (value.length() == 4) {
                if (value.matches("[0-9]+")) {
                    String mon = value.substring(0, 2);
                    String year = value.substring(2);
                    expiryDate.setValue(mon + "/" + year);
                } else {
                    error = true;
                }
            } else if (!value.matches("(\\d{2})/(\\d{2})")) {
                error = true;
            }
        } else {
            error = true;
        }

		if (error) {
            expiryDate.setComponentError(userError);
		} else {
			expiryDate.setComponentError(null);
		}

		return !error;
    }
	
	public static boolean isValidPassword(PasswordField password) {
		StringLengthValidator validator = new StringLengthValidator("รหัสผ่าน 6-10 ตัว", 6, 10);
		ValidationResult validationResult = validator.apply(password.getValue(), new ValueContext(password));

		boolean error = validationResult.isError();
		UserError userError;
		if (error) {
			userError = new UserError(validationResult.getErrorMessage());
			password.setComponentError(userError);
		} else {
			if (password.getValue().matches("[A-Za-z0-9]+")) {
			    if (password.getValue().matches("[A-Za-z]+")) {
			        error = true;
			        userError = new UserError(Messages.get("auth.password.alpha.only"));
                    password.setComponentError(userError);
                } else if (password.getValue().matches("[0-9]+")){
			        error = true;
                    userError = new UserError(Messages.get("auth.password.numeric.only"));
                    password.setComponentError(userError);
                } else {
			        error = false;
                    password.setComponentError(null);
                }
            } else {
			    error = true;
			    userError = new UserError(Messages.get("auth.password.alphanumeric.only"));
			    password.setComponentError(userError);
            }
		}
		
		return !error;
	}

    public static boolean isValidPasswordConfirm(PasswordField passwordConfirm, PasswordField password) {
        boolean error = false;
        if (!passwordConfirm.getValue().isEmpty() && passwordConfirm.getValue().equals(password.getValue())) {
            passwordConfirm.setComponentError(null);
        } else {
            passwordConfirm.setComponentError(new UserError(Messages.get("auth.password.confirm.failed")));
            error = true;
        }
        return !error;
    }

    public static double text2Double(String value, double defaultValue) {
        double doubleValue;
        if (value == null) {
            doubleValue = 0;
        } else {
            try {
                doubleValue = Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                doubleValue = defaultValue;
                ex.printStackTrace();
            }
        }

        return doubleValue;
    }

    public static List<Product> filterMenWatchList(List<Product> productList) {
        return productList.stream()
                .filter(product -> product.getProductCategoriesId() == 2)
                .collect(Collectors.toList());
    }

    public static List<Product> filterWomenWatchList(List<Product> productList) {
        return productList.stream()
                .filter(product -> product.getProductCategoriesId() == 3)
                .collect(Collectors.toList());
    }

    public static class ImageSource implements StreamResource.StreamSource {
		private static final long serialVersionUID = -5616420547002465758L;
		private final byte[] image;
		
		public ImageSource(byte[] image) {
			this.image = image;
		}
		
		public InputStream getStream() {
			return new ByteArrayInputStream(image);
		}
	}

    public static void ratingClassified(Label productRate, Product product) {
        switch (product.getProductRate()) {
            case 1:
                productRate.addStyleName("rating1");
                break;
            case 2:
                productRate.addStyleName("rating2");
                break;
            case 3:
                productRate.addStyleName("rating3");
                break;
            case 4:
                productRate.addStyleName("rating4");
                break;
            case 5:
                productRate.addStyleName("rating5");
        }
    }

    public static void sendConfirmationSignUpMail(String confirmLink, String email, Date expiryDate) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(properties);
        mailSender.setUsername("tt00kensuke@gmail.com");
        mailSender.setPassword("lryqnkwyvxurhyzi");
        mailSender.setDefaultEncoding("UTF-16");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("tt00kensuke@gmail.com");
        helper.setSubject(Messages.get("mail.verified.subject"));
        helper.setText( "<html>" +
                "<head>" +
                "<meta charset='UTF-16'>" +
                "</head>" +
                "<body>" +
                Messages.get("mail.verified.message1") +
                "<br />" +
                Messages.get("mail.verified.message2") +
                " " +
                new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(expiryDate) +
                "<br />" +
                "<br />" +
                "<a href=" + confirmLink + ">" + confirmLink + "</a>" +
                "</body>" +
                "</html>", true);
        helper.addTo(email);

//        mailSender.send(message);

        new Thread(() -> mailSender.send(message)).start();
    }

}
