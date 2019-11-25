package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringComponent
@UIScope
public class FavouriteView extends VerticalLayout implements View {
    public static final String NAME = "favourite";
    private final ChicManUI eventHandler;

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(FavouriteView.class);

    private List<Product> productList;
    private ComboBox<String> comboSortList;
    private CssLayout groupMainCardLayout;

    public FavouriteView(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;
        init();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (eventHandler.getCurrentUser() == null) {
            eventHandler.getToolbar().clickHome();
        } else {
            addFavouriteProduct();
            eventHandler.getToolbar().clickFavourite();
        }
    }

    private void init() {
//        removeAllComponents();
//        addComponent(new Label("Favourite View"));
//        ProductItem productItem;
//        for (Product product: eventHandler.getSessionFavourite().getFavouriteList()) {
//            productItem = new ProductItem(eventHandler, product);
//            productItem.addStyleName("product_list_item");
//            addComponent(productItem);
//        }

        setId("main");
        setSpacing(false);

        // under toolbar
        HorizontalLayout subToolbar = new HorizontalLayout();
        subToolbar.setId("subToolbar");
        subToolbar.setMargin(new MarginInfo(true, false, false, false));
        subToolbar.setSpacing(false);
        addComponent(subToolbar);
        setComponentAlignment(subToolbar, Alignment.TOP_CENTER);

        // navigation tracking
        NavigationTracking tracking = new NavigationTracking(eventHandler);
        Button btnFavourite = new Button(Messages.get("page.favourite"));
        btnFavourite.setEnabled(false);
        tracking.addTracking(btnFavourite);
        subToolbar.addComponent(tracking);
        subToolbar.setComponentAlignment(tracking, Alignment.TOP_LEFT);

        comboSortList = new ComboBox<>();
        comboSortList.setId("comboSortList");
        comboSortList.setStyleName("borderless");
        comboSortList.setTextInputAllowed(false);
        comboSortList.setItems(Messages.get("sort.popularity"),
                Messages.get("sort.fresh"),
                Messages.get("sort.discount"),
                Messages.get("sort.price.asc"),
                Messages.get("sort.price.desc"));
        comboSortList.setEmptySelectionAllowed(false);
        comboSortList.setValue(Messages.get("sort.popularity"));

        CssLayout groupSorting = new CssLayout();
        groupSorting.setId("groupSorting");
        Label sortTitle = new Label(Messages.get("sort.title"));
        sortTitle.setId("sortTitle");
        groupSorting.addComponent(sortTitle);
        groupSorting.addComponent(comboSortList);

        // add 2 button to select display format
        Button cardBtn = new Button();
        cardBtn.setId("cardBtn");
        cardBtn.setStyleName("icon-only");
        cardBtn.addStyleName("borderless");
        cardBtn.addStyleName("inactive_sorting");
        cardBtn.addStyleName("active-noborder");
        cardBtn.setIcon(VaadinIcons.GRID_BIG);
        Button listBtn = new Button();
        listBtn.setId("listBtn");
        listBtn.setStyleName("icon-only");
        listBtn.addStyleName("borderless");
        listBtn.addStyleName("inactive_sorting");
        listBtn.addStyleName("active-noborder");
        listBtn.setIcon(VaadinIcons.LIST_UL);
        groupSorting.addComponents(cardBtn, listBtn);

        subToolbar.addComponent(groupSorting);
        subToolbar.setComponentAlignment(groupSorting, Alignment.TOP_LEFT);

        HorizontalLayout filterAndResult = new HorizontalLayout();
        filterAndResult.setId("filterAndResult");

        Filter inputFilter = new Filter(eventHandler);
        filterAndResult.addComponent(inputFilter);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setId("pageNameAndProducts");
        filterAndResult.addComponent(verticalLayout);

        // add page label
        Label pageLabel = new Label(Messages.get("page.favourite"));
        pageLabel.setId("pageLabel");
        pageLabel.setWidth("100%");
        verticalLayout.addComponent(pageLabel);

        groupMainCardLayout = new CssLayout();
        groupMainCardLayout.setId("products");
        groupMainCardLayout.setStyleName("group_main_card");

        verticalLayout.addComponent(groupMainCardLayout);
        addComponent(filterAndResult);
        setComponentAlignment(filterAndResult, Alignment.TOP_CENTER);

        addFavouriteProduct();
    }

    private void addFavouriteProduct() {
        productList = eventHandler.getSessionFavourite().getFavouriteList();
        comboSortList.setValue(Messages.get("sort.popularity"));
        refreshProductItem();
    }

    private void refreshProductItem() {
        groupMainCardLayout.removeAllComponents();
        ProductItem productItem;
        for (Product product : productList) {
            productItem = new ProductItem(eventHandler, product);
            groupMainCardLayout.addComponent(productItem);
        }
    }
}
