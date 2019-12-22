package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringComponent
@UIScope
public class MenWatchesView extends VerticalLayout implements View {
    public static final String NAME = "watches-men";
    private final ChicManUI eventHandler;

    private final Logger log = LoggerFactory.getLogger(MenWatchesView.class);

    private List<Product> productList;
    private ComboBox<String> comboSortList;
    private Button cardBtn;
    private Button listBtn;
    private CssLayout groupMainCardLayout;
    private boolean isDisplayListStyle;
    private Filter inputFilter;

    public MenWatchesView(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;
        init();
        setEvent();

        cardBtn.click();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        log.info("enter Men Watches view with parameter /" + event.getParameters());
        eventHandler.getToolbar().setMenuMenWatchesActive();
        eventHandler.resetFilter();
        inputFilter.clearAll();
        sortItemByPopularity();
    }

    public void filter() {
        eventHandler.getFilterBean().createFilteredProduct(groupMainCardLayout, productList);
    }

    private void init() {
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
        Button btnMenWatches = new Button(Messages.get("page.men"));
        btnMenWatches.setEnabled(false);
        tracking.addTracking(btnMenWatches);
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
        cardBtn = new Button();
        cardBtn.setId("cardBtn");
        cardBtn.setStyleName("icon-only");
        cardBtn.addStyleName("borderless");
        cardBtn.addStyleName("inactive_sorting");
        cardBtn.addStyleName("active-noborder");
        cardBtn.setIcon(VaadinIcons.GRID_BIG);
        listBtn = new Button();
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

        inputFilter = new Filter(eventHandler);
        filterAndResult.addComponent(inputFilter);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setId("pageNameAndProducts");
        filterAndResult.addComponent(verticalLayout);

        // add page label
        Label pageLabel = new Label(Messages.get("page.men"));
        pageLabel.setId("pageLabel");
        pageLabel.setWidth("100%");
        verticalLayout.addComponent(pageLabel);

        groupMainCardLayout = new CssLayout();
        groupMainCardLayout.setId("products");
        groupMainCardLayout.setStyleName("group_main_card"  );

        verticalLayout.addComponent(groupMainCardLayout);
        addComponent(filterAndResult);
        setComponentAlignment(filterAndResult, Alignment.TOP_CENTER);

        sortItemByPopularity();
    }

    private void setEvent() {
        cardBtn.addClickListener(e -> {
            showProductInCardFormat();
            cardBtn.addStyleName("active_sorting");
            listBtn.removeStyleName("active_sorting");
        });

        listBtn.addClickListener(e -> {
            showProductInListFormat();
            listBtn.addStyleName("active_sorting");
            cardBtn.removeStyleName("active_sorting");
        });

        comboSortList.addValueChangeListener(e -> changeSortItemCondition(comboSortList.getValue()));
    }

    private void showProductInCardFormat() {
        for (int i = 0; i < groupMainCardLayout.getComponentCount(); i++) {
            if (groupMainCardLayout.getComponent(i) instanceof ProductItem) {
                ProductItem productItem = (ProductItem) groupMainCardLayout.getComponent(i);
                productItem.removeStyleName("product_list_item");
            }
        }

        isDisplayListStyle = false;
    }

    private void showProductInListFormat() {
        for (int i = 0; i < groupMainCardLayout.getComponentCount(); i++) {
            if (groupMainCardLayout.getComponent(i) instanceof ProductItem) {
                ProductItem productItem = (ProductItem) groupMainCardLayout.getComponent(i);
                productItem.addStyleName("product_list_item");
            }
        }

        isDisplayListStyle = true;
    }

    private void changeSortItemCondition(String sortCondition) {
        if (sortCondition.equals(Messages.get("sort.popularity"))) {
            sortItemByPopularity();
        } else if (sortCondition.equals(Messages.get("sort.fresh"))) {
            sortItemByFresh();
        } else if (sortCondition.equals(Messages.get("sort.discount"))) {
            sortItemByDiscount();
        } else if (sortCondition.equals(Messages.get("sort.price.asc"))) {
            sortItemByPriceAsc();
        } else if (sortCondition.equals(Messages.get("sort.price.desc"))) {
            sortItemByPriceDesc();
        }
    }

    private void sortItemByPopularity() {
        productList = eventHandler.getProductsRepo().findAll(new Sort(Sort.Direction.DESC, "productRate"));
        productList = Utils.filterMenWatchList(productList);
        comboSortList.setValue(Messages.get("sort.popularity"));
        refreshProductItem();
    }

    private void sortItemByFresh() {
        productList = eventHandler.getProductsRepo().findAll(new Sort(Sort.Direction.DESC, "submission"));
        productList = Utils.filterMenWatchList(productList);
        comboSortList.setValue(Messages.get("sort.fresh"));
        refreshProductItem();
    }

    private void sortItemByDiscount() {
        productList = eventHandler.getProductsRepo().findAll(new Sort(Sort.Direction.DESC, "productDiscount"));
        productList = Utils.filterMenWatchList(productList);
        comboSortList.setValue(Messages.get("sort.discount"));
        refreshProductItem();
    }

    private void sortItemByPriceAsc() {
        productList = eventHandler.getProductsRepo().findAll(new Sort(Sort.Direction.ASC, "productPriceSale"));
        productList = Utils.filterMenWatchList(productList);
        comboSortList.setValue(Messages.get("sort.price.asc"));
        refreshProductItem();
    }

    private void sortItemByPriceDesc() {
        productList = eventHandler.getProductsRepo().findAll(new Sort(Sort.Direction.DESC, "productPriceSale"));
        productList = Utils.filterMenWatchList(productList);
        comboSortList.setValue(Messages.get("sort.price.desc"));
        refreshProductItem();
    }

    private void refreshProductItem() {
        groupMainCardLayout.removeAllComponents();
        ProductItem productItem;
        for (Product product: productList) {
            productItem = new ProductItem(eventHandler, product);

            if (isDisplayListStyle) {
                productItem.addStyleName("product_list_item");
            }

            groupMainCardLayout.addComponent(productItem);
        }

        filter();
    }

    public void updateFavourite(List<Product> favouriteList) {
        Component component;
        ProductItem productItem;
        for (int i = 0; i < groupMainCardLayout.getComponentCount(); i++) {
            component = groupMainCardLayout.getComponent(i);

            if (component instanceof ProductItem) {
                productItem = (ProductItem) component;

                for (Product favouriteItem: favouriteList) {
                    if (favouriteItem.getProductId().intValue() == productItem.getProduct().getProductId().intValue()) {
                        productItem.getLikeBtn().click();
                    }
                }
            }
        }
    }
}
