package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.utility.Messages;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class NavigationTracking extends CssLayout {

    private final ChicManUI eventHandler;
    private Button btnHome;
    private Label separator;

    public NavigationTracking(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;

        init();
        setEvent();
    }

    private void init() {
        setId("navigationTracking");

        separator = new Label(" / ");
        separator.addStyleName("tracking_separator");
        btnHome = new Button(Messages.get("page.home"));
        btnHome.setStyleName("borderless");
        btnHome.addStyleName("active-noborder");

        addComponent(btnHome);
    }

    public void addTracking(Button trackingBtn) {
        if (trackingBtn != null) {
            trackingBtn.setStyleName("borderless");
            trackingBtn.addStyleName("active-noborder");

            addComponent(separator);
            addComponent(trackingBtn);
        }
    }

    private void setEvent() {
        btnHome.addClickListener(e -> eventHandler.showHomePage());
    }

}
