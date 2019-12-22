package com.chicman.view;

import com.chicman.utility.Messages;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class Footer extends HorizontalLayout {

	public Footer() {
		init();
	}
	
	private void init() {
		VerticalLayout v1 = new VerticalLayout();
		addComponent(v1);
		setComponentAlignment(v1, Alignment.TOP_CENTER);
		v1.setStyleName("footer-container");
		
		Label chicman = new Label(Messages.get("site.name"));
		chicman.setStyleName("mark");
		v1.addComponent(chicman);
		
		Button btnInstagram = new Button("Instagram");
		btnInstagram.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnInstagram.setStyleName("afterContentNone", true);
		btnInstagram.setIcon(new ThemeResource("images/logo_instagram.png"));
		btnInstagram.setResponsive(true);
		v1.addComponent(btnInstagram);
		
		Button btnFacebook = new Button("Facebook");
		btnFacebook.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnFacebook.setStyleName("afterContentNone", true);
		btnFacebook.setIcon(new ThemeResource("images/logo_facebook.png"));
		v1.addComponent(btnFacebook);

		Button btnTwitter = new Button("Twitter");
		btnTwitter.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnTwitter.setStyleName("afterContentNone", true);
		btnTwitter.setIcon(new ThemeResource("images/logo_twitter.png"));
		v1.addComponent(btnTwitter);
		
		Button btnPinterest = new Button("Pinterest");
		btnPinterest.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		btnPinterest.setStyleName("afterContentNone", true);
		btnPinterest.setIcon(new ThemeResource("images/logo_pinterest.png"));
		v1.addComponent(btnPinterest);
		
		setResponsive(true);
	}

}
