package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringComponent
@UIScope
public class HomeView extends VerticalLayout implements View {

    public static final String NAME = "";
    private final Logger log = LoggerFactory.getLogger(HomeView.class);

    private final ChicManUI eventHandler;
    private final Button btnShopNow;
    private Button btnViewAll;
    private Button btnMen;
    private Button btnWomen;

    public HomeView(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;
        this.btnShopNow = new Button(Messages.get("shop.now"));
        this.btnViewAll = new Button(Messages.get("view.all"));
        this.btnMen = new Button(Messages.get("page.men"));
        this.btnWomen = new Button(Messages.get("page.women"));

        init();
        setEvent();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        log.info("enter Home view with parameter /" + event.getParameters());
    }

    private void init() {
        setMargin(false);
        setSpacing(false);


        addTopHighlightSection();
        addSomeText();
        addSaleHighlightSection();
        addSomeButton();
        addMenAndWomenSection();
    }

    private void setEvent() {
        btnShopNow.addClickListener(e -> eventHandler.getToolbar().clickWatches());
        btnViewAll.addClickListener(e -> eventHandler.getToolbar().clickWatchesWithDiscount());
        btnMen.addClickListener(e -> eventHandler.getToolbar().clickMen());
        btnWomen.addClickListener(e -> eventHandler.getToolbar().clickWomen());
    }

    private void addTopHighlightSection() {
        // temp used for show scrollbar
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("cover_image");
        addComponent(verticalLayout);

        btnShopNow.setStyleName("borderless");
        btnShopNow.addStyleName("active-noborder");
        btnShopNow.addStyleName("shopnow_btn");
        verticalLayout.addComponent(btnShopNow);
    }

    private void addSomeText() {
        Label label = new Label(Messages.get("products.sale"));
        label.addStyleName("products_sale_label");
        addComponent(label);
        setComponentAlignment(label, Alignment.BOTTOM_CENTER);
    }

    private void addSomeButton() {
        btnViewAll.addStyleName("borderless");
        btnViewAll.addStyleName("active-noborder");
        btnViewAll.addStyleName("ghost_btn");
        addComponent(btnViewAll);
        setComponentAlignment(btnViewAll, Alignment.MIDDLE_CENTER);
    }

    private void addMenAndWomenSection() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("men_women");
        horizontalLayout.setMargin(false);
        horizontalLayout.setSpacing(false);
        addComponent(horizontalLayout);

        btnMen.addStyleName("borderless");
        btnMen.addStyleName("active-noborder");
        btnMen.addStyleName("men");

        btnWomen.addStyleName("borderless");
        btnWomen.addStyleName("active-noborder");
        btnWomen.addStyleName("women");

        horizontalLayout.addComponents(btnMen, btnWomen);
    }

    private void addSaleHighlightSection() {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName("sale");
        addComponent(cssLayout);
        setComponentAlignment(cssLayout, Alignment.TOP_CENTER);

        List<Product> topDiscount = eventHandler.getProductsRepo().findAll(
                new Sort(Sort.Direction.DESC, "productDiscount"));

        cssLayout.addComponent(new ProductItem(eventHandler, topDiscount.get(0)));
        cssLayout.addComponent(new ProductItem(eventHandler, topDiscount.get(1)));
        cssLayout.addComponent(new ProductItem(eventHandler, topDiscount.get(2)));
    }
}
