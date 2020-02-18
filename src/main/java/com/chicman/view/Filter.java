package com.chicman.view;

import com.chicman.ChicManUI;
import com.chicman.bean.FilterBean;
import com.chicman.model.Product;
import com.chicman.utility.Messages;
import com.chicman.utility.Utils;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.data.domain.Sort;

import java.util.List;

@UIScope
@SpringComponent
public class Filter extends VerticalLayout {

    private static String PRICE_FROM = "";
    private static String PRICE_TO = "";
    private static Double MIN_SLIDER = 0.0;
    private static Double MAX_SLIDER = 0.0;

    private ChicManUI eventHandler;

    private CheckBox cbCircleDial;
    private CheckBox cbSquareDial;

    private CheckBox cbBrandRado;
    private CheckBox cbBrandDaniel;
    private CheckBox cbBrandLongines;
    private CheckBox cbBrandReefTiger;
    private CheckBox cbBrandBulova;
    private CheckBox cbBrandGoS;
    private CheckBox cbBrandLangeAndSohne;
    private CheckBox cbBrandOmega;
    private CheckBox cbBrandRomainGauthier;

    private CheckBox cbDiscountRate1;
    private CheckBox cbDiscountRate2;
    private CheckBox cbDiscountRate3;
    private CheckBox cbDiscountRate4;
    private CheckBox cbDiscountRate5;
    private CheckBox cbDiscountRate6;
    private CheckBox cbDiscountRate7;
    private CheckBox cbDiscountRate8;
    private CheckBox cbDiscountRate9;
    private CheckBox cbDiscountRate10;

    private CheckBox cbStrapLeather;
    private CheckBox cbStrapStainlessSteel;

    private CheckBox cbDisplayDigital;
    private CheckBox cbDisplayAnalog;
    private CheckBox cbDisplayAutomatic;
    private CheckBox cbDisplayChronograph;

    private CheckBox cbDialColorBlack;
    private CheckBox cbDialColorWhite;
    private CheckBox cbDialColorGray;
    private CheckBox cbDialColorBrown;
    private CheckBox cbDialColorBlue;

    private Button btnDialDrawer;
    private Button btnBrandDrawer;
    private Button btnDiscountDrawer;
    private Button btnStrapDrawer;
    private Button btnDisplayDrawer;
    private Button btnDialColorDrawer;
    private Button btnStrapColorDrawer;

    private CheckBox cbStrapColorBlack;
    private CheckBox cbStrapColorWhite;
    private CheckBox cbStrapColorGray;
    private CheckBox cbStrapColorBrown;
    private CheckBox cbStrapColorBlue;

    private TextField priceFrom;
    private TextField priceTo;
    private Slider minSlider;
    private Slider maxSlider;

    public Filter(ChicManUI eventHandler) {
        this.eventHandler = eventHandler;

        init();
        setEvent();
    }

    public void clearAll() {
        cbCircleDial.clear();
        cbSquareDial.clear();

        cbBrandRado.clear();
        cbBrandDaniel.clear();
        cbBrandLongines.clear();
        cbBrandReefTiger.clear();
        cbBrandBulova.clear();
        cbBrandGoS.clear();
        cbBrandLangeAndSohne.clear();
        cbBrandOmega.clear();
        cbBrandRomainGauthier.clear();

        cbDiscountRate1.clear();
        cbDiscountRate2.clear();
        cbDiscountRate3.clear();
        cbDiscountRate4.clear();
        cbDiscountRate5.clear();
        cbDiscountRate6.clear();
        cbDiscountRate7.clear();
        cbDiscountRate8.clear();
        cbDiscountRate9.clear();
        cbDiscountRate10.clear();

        cbStrapLeather.clear();
        cbStrapStainlessSteel.clear();

        cbDisplayDigital.clear();
        cbDisplayAnalog.clear();
        cbDisplayAutomatic.clear();
        cbDisplayChronograph.clear();

        cbDialColorBlack.clear();
        cbDialColorWhite.clear();
        cbDialColorGray.clear();
        cbDialColorBrown.clear();
        cbDialColorBlue.clear();

        cbStrapColorBlack.clear();
        cbStrapColorWhite.clear();
        cbStrapColorGray.clear();
        cbStrapColorBrown.clear();
        cbStrapColorBlue.clear();

        priceFrom.setValue(PRICE_FROM);
        priceTo.setValue(PRICE_TO);
        minSlider.setValue(MIN_SLIDER);
        maxSlider.setValue(MAX_SLIDER);
    }

    private void setupFiltersLabel() {
        Label label = new Label();
        label.setContentMode(ContentMode.HTML);
        label.setValue(VaadinIcons.FILTER.getHtml() + " Filter");
        label.setId("filterHeader");
        addComponent(label);
    }

    private void setupPriceFilter() {
        List<Product> productList = eventHandler.getProductsRepo().findAll(Sort.by(Sort.Direction.ASC, "productPriceSale"));
        double minPrice = FilterBean.DEFAULT_MIN_PRICE;
        double maxPrice = FilterBean.DEFAULT_MAX_PRICE;

        if (productList.size() > 0) {
            minPrice = productList.get(0).getProductPriceSale();
            maxPrice = productList.get(productList.size() - 1).getProductPriceSale();
        }

        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName("slider");
        addComponent(cssLayout);

//        Label startNode = new Label();
//        startNode.addStyleName("slider_scroll");
//        cssLayout.addComponent(startNode);
//
//        Label endNode = new Label();
//        endNode.addStyleName("slider_scroll");
//        cssLayout.addComponent(endNode);

        Label label = new Label(Messages.get("price"));
        label.addStyleName("filter_label");
        label.setId("firstFilterLabel");
        label.setSizeFull();
        cssLayout.addComponent(label);

        Label from = new Label(Messages.get("price.from"));
        from.addStyleName("labelPriceFrom");
        cssLayout.addComponent(from);

        priceFrom = new TextField();
        priceFrom.setId("priceFrom");
        priceFrom.setWidth("60px");
        priceFrom.setValue(String.valueOf(new Double(minPrice).intValue()));
        PRICE_FROM = priceFrom.getValue();
        priceFrom.setMaxLength(5);
        cssLayout.addComponent(priceFrom);

        minSlider = new Slider();
        minSlider.addStyleName("sliderPrice");
        minSlider.setWidth("100px");
        minSlider.setMin(minPrice);
        minSlider.setMax(maxPrice);
        minSlider.setValue(minSlider.getMin());
        MIN_SLIDER = minSlider.getValue();
        cssLayout.addComponent(minSlider);

        Label to = new Label(Messages.get("price.to"));
        to.addStyleName("labelPriceTo");
        cssLayout.addComponent(to);

        priceTo = new TextField();
        priceTo.setId("priceTo");
        priceTo.setWidth("60px");
        priceTo.setValue(String.valueOf(new Double(maxPrice).intValue()));
        PRICE_TO = priceTo.getValue();
        priceTo.setMaxLength(5);
        cssLayout.addComponent(priceTo);

        maxSlider = new Slider();
        maxSlider.setId("sliderPriceTo");
        maxSlider.addStyleName("sliderPrice");
        maxSlider.setWidth("100px");
        maxSlider.setMin(minPrice);
        maxSlider.setMax(maxPrice);
        maxSlider.setValue(maxSlider.getMax());
        MAX_SLIDER = maxSlider.getValue();
        cssLayout.addComponent(maxSlider);

//        cssLayout.addComponent(new Label("<input type='number'>", ContentMode.HTML));
//        cssLayout.addComponent(new Label("<input type='number'>", ContentMode.HTML));
//        cssLayout.addComponent(new Label("<input value='25000' min='0' max='120000' step='500' type='range'>", ContentMode.HTML));
    }

    private void setupDialFilter() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("filter_label_container");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth("100%");
        addComponent(horizontalLayout);

        Label label = new Label(Messages.get("filter.dial"));
        label.setSizeFull();
        label.addStyleName("filter_label");
        horizontalLayout.addComponent(label);
        horizontalLayout.setExpandRatio(label, 1);

        btnDialDrawer = new Button();
        btnDialDrawer.setIcon(VaadinIcons.MINUS);
        btnDialDrawer.addStyleName("btn_toggle");
        btnDialDrawer.addStyleName("borderless");
        btnDialDrawer.addStyleName("icon-only");
        btnDialDrawer.addStyleName("colored-when-hover");
        btnDialDrawer.addStyleName("active-noborder");
        horizontalLayout.addComponent(btnDialDrawer);
        horizontalLayout.setComponentAlignment(btnDialDrawer, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("group_filter");
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        cbCircleDial = new CheckBox(Messages.get("filter.dial.circle"));
        verticalLayout.addComponent(cbCircleDial);

        cbSquareDial = new CheckBox(Messages.get("filter.dial.square"));
        verticalLayout.addComponent(cbSquareDial);

        horizontalLayout.addLayoutClickListener(e -> btnDialDrawer.click());
    }

    private void setupDialColorFilter() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("filter_label_container");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth("100%");
        addComponent(horizontalLayout);

        Label label = new Label(Messages.get("filter.dial.color"));
        label.setSizeFull();
        label.addStyleName("filter_label");
        horizontalLayout.addComponent(label);
        horizontalLayout.setExpandRatio(label, 1);

        btnDialColorDrawer = new Button();
        btnDialColorDrawer.setIcon(VaadinIcons.MINUS);
        btnDialColorDrawer.addStyleName("btn_toggle");
        btnDialColorDrawer.addStyleName("borderless");
        btnDialColorDrawer.addStyleName("icon-only");
        btnDialColorDrawer.addStyleName("colored-when-hover");
        btnDialColorDrawer.addStyleName("active-noborder");
        horizontalLayout.addComponent(btnDialColorDrawer);
        horizontalLayout.setComponentAlignment(btnDialColorDrawer, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("group_filter");
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        cbDialColorBlack = new CheckBox(Messages.get("color.black"));
        verticalLayout.addComponent(cbDialColorBlack);

        cbDialColorWhite = new CheckBox(Messages.get("color.white"));
        verticalLayout.addComponent(cbDialColorWhite);

        cbDialColorGray = new CheckBox(Messages.get("color.gray"));
        verticalLayout.addComponent(cbDialColorGray);

        cbDialColorBrown = new CheckBox(Messages.get("color.brown"));
        verticalLayout.addComponent(cbDialColorBrown);

        cbDialColorBlue = new CheckBox(Messages.get("color.blue"));
        verticalLayout.addComponent(cbDialColorBlue);

        horizontalLayout.addLayoutClickListener(e -> btnDialColorDrawer.click());
    }

    private void setupBrandFilter() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("filter_label_container");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth("100%");
        addComponent(horizontalLayout);

        Label label = new Label(Messages.get("filter.brand"));
        label.setSizeFull();
        label.addStyleName("filter_label");
        horizontalLayout.addComponent(label);
        horizontalLayout.setExpandRatio(label, 1);

        btnBrandDrawer = new Button();
        btnBrandDrawer.setIcon(VaadinIcons.MINUS);
        btnBrandDrawer.addStyleName("btn_toggle");
        btnBrandDrawer.addStyleName("borderless");
        btnBrandDrawer.addStyleName("icon-only");
        btnBrandDrawer.addStyleName("colored-when-hover");
        btnBrandDrawer.addStyleName("filter_label");
        btnBrandDrawer.addStyleName("active-noborder");
        horizontalLayout.addComponent(btnBrandDrawer);
        horizontalLayout.setComponentAlignment(btnBrandDrawer, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("group_filter");
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        cbBrandRado = new CheckBox(Messages.get("filter.brand.rado"));
        verticalLayout.addComponent(cbBrandRado);

        cbBrandDaniel = new CheckBox(Messages.get("filter.brand.daniel"));
        verticalLayout.addComponent(cbBrandDaniel);

        cbBrandLongines = new CheckBox(Messages.get("filter.brand.longines"));
        verticalLayout.addComponent(cbBrandLongines);

        cbBrandReefTiger = new CheckBox(Messages.get("filter.brand.reeftiger"));
        verticalLayout.addComponent(cbBrandReefTiger);

        cbBrandBulova= new CheckBox(Messages.get("filter.brand.bulova"));
        verticalLayout.addComponent(cbBrandBulova);

        cbBrandGoS = new CheckBox(Messages.get("filter.brand.gos"));
        verticalLayout.addComponent(cbBrandGoS);

        cbBrandLangeAndSohne = new CheckBox(Messages.get("filter.brand.langeandsohne"));
        verticalLayout.addComponent(cbBrandLangeAndSohne);

        cbBrandOmega = new CheckBox(Messages.get("filter.brand.omega"));
        verticalLayout.addComponent(cbBrandOmega);

        cbBrandRomainGauthier = new CheckBox(Messages.get("filter.brand.romaingauthier"));
        verticalLayout.addComponent(cbBrandRomainGauthier);

        horizontalLayout.addLayoutClickListener(e -> btnBrandDrawer.click());
    }

    private void setupDiscountFilter() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("filter_label_container");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth("100%");
        addComponent(horizontalLayout);

        Label label = new Label(Messages.get("filter.discount"));
        label.setSizeFull();
        label.addStyleName("filter_label");
        horizontalLayout.addComponent(label);
        horizontalLayout.setExpandRatio(label, 1);

        btnDiscountDrawer = new Button();
        btnDiscountDrawer.setIcon(VaadinIcons.MINUS);
        btnDiscountDrawer.addStyleName("btn_toggle");
        btnDiscountDrawer.addStyleName("borderless");
        btnDiscountDrawer.addStyleName("icon-only");
        btnDiscountDrawer.addStyleName("colored-when-hover");
        btnDiscountDrawer.addStyleName("filter_label");
        btnDiscountDrawer.addStyleName("active-noborder");
        horizontalLayout.addComponent(btnDiscountDrawer);
        horizontalLayout.setComponentAlignment(btnDiscountDrawer, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("group_filter");
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        cbDiscountRate1 = new CheckBox(Messages.get("filter.discount.rate1"));
        verticalLayout.addComponent(cbDiscountRate1);

        cbDiscountRate2 = new CheckBox(Messages.get("filter.discount.rate2"));
        verticalLayout.addComponent(cbDiscountRate2);

        cbDiscountRate3 = new CheckBox(Messages.get("filter.discount.rate3"));
        verticalLayout.addComponent(cbDiscountRate3);

        cbDiscountRate4 = new CheckBox(Messages.get("filter.discount.rate4"));
        verticalLayout.addComponent(cbDiscountRate4);

        cbDiscountRate5 = new CheckBox(Messages.get("filter.discount.rate5"));
        verticalLayout.addComponent(cbDiscountRate5);

        cbDiscountRate6 = new CheckBox(Messages.get("filter.discount.rate6"));
        verticalLayout.addComponent(cbDiscountRate6);

        cbDiscountRate7 = new CheckBox(Messages.get("filter.discount.rate7"));
        verticalLayout.addComponent(cbDiscountRate7);

        cbDiscountRate8 = new CheckBox(Messages.get("filter.discount.rate8"));
        verticalLayout.addComponent(cbDiscountRate8);

        cbDiscountRate9 = new CheckBox(Messages.get("filter.discount.rate9"));
        verticalLayout.addComponent(cbDiscountRate9);

        cbDiscountRate10 = new CheckBox(Messages.get("filter.discount.rate10"));
        verticalLayout.addComponent(cbDiscountRate10);

        horizontalLayout.addLayoutClickListener(e -> btnDiscountDrawer.click());
    }

    private void setupStrapFilter() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("filter_label_container");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth("100%");
        addComponent(horizontalLayout);

        Label label = new Label(Messages.get("filter.strap"));
        label.setSizeFull();
        label.addStyleName("filter_label");
        horizontalLayout.addComponent(label);
        horizontalLayout.setExpandRatio(label, 1);

        btnStrapDrawer = new Button();
        btnStrapDrawer.setIcon(VaadinIcons.MINUS);
        btnStrapDrawer.addStyleName("btn_toggle");
        btnStrapDrawer.addStyleName("borderless");
        btnStrapDrawer.addStyleName("icon-only");
        btnStrapDrawer.addStyleName("colored-when-hover");
        btnStrapDrawer.addStyleName("filter_label");
        btnStrapDrawer.addStyleName("active-noborder");
        horizontalLayout.addComponent(btnStrapDrawer);
        horizontalLayout.setComponentAlignment(btnStrapDrawer, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("group_filter");
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        cbStrapLeather = new CheckBox(Messages.get("filter.strap.leather"));
        verticalLayout.addComponent(cbStrapLeather);

        cbStrapStainlessSteel = new CheckBox(Messages.get("filter.strap.stainless"));
        verticalLayout.addComponent(cbStrapStainlessSteel);

        horizontalLayout.addLayoutClickListener(e -> btnStrapDrawer.click());
    }

    private void setupStrapColorFilter() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("filter_label_container");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth("100%");
        addComponent(horizontalLayout);

        Label label = new Label(Messages.get("filter.strap.color"));
        label.setSizeFull();
        label.addStyleName("filter_label");
        horizontalLayout.addComponent(label);
        horizontalLayout.setExpandRatio(label, 1);

        btnStrapColorDrawer = new Button();
        btnStrapColorDrawer.setIcon(VaadinIcons.MINUS);
        btnStrapColorDrawer.addStyleName("btn_toggle");
        btnStrapColorDrawer.addStyleName("borderless");
        btnStrapColorDrawer.addStyleName("icon-only");
        btnStrapColorDrawer.addStyleName("colored-when-hover");
        btnStrapColorDrawer.addStyleName("active-noborder");
        horizontalLayout.addComponent(btnStrapColorDrawer);
        horizontalLayout.setComponentAlignment(btnStrapColorDrawer, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("group_filter");
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        cbStrapColorBlack = new CheckBox(Messages.get("color.black"));
        verticalLayout.addComponent(cbStrapColorBlack);

        cbStrapColorWhite = new CheckBox(Messages.get("color.white"));
        verticalLayout.addComponent(cbStrapColorWhite);

        cbStrapColorGray = new CheckBox(Messages.get("color.gray"));
        verticalLayout.addComponent(cbStrapColorGray);

        cbStrapColorBrown = new CheckBox(Messages.get("color.brown"));
        verticalLayout.addComponent(cbStrapColorBrown);

        cbStrapColorBlue = new CheckBox(Messages.get("color.blue"));
        verticalLayout.addComponent(cbStrapColorBlue);

        horizontalLayout.addLayoutClickListener(e -> btnStrapColorDrawer.click());
    }

    private void setupDisplayFilter() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addStyleName("filter_label_container");
        horizontalLayout.setSpacing(false);
        horizontalLayout.setWidth("100%");
        addComponent(horizontalLayout);

        Label label = new Label(Messages.get("filter.display"));
        label.setSizeFull();
        label.addStyleName("filter_label");
        horizontalLayout.addComponent(label);
        horizontalLayout.setExpandRatio(label, 1);

        btnDisplayDrawer = new Button();
        btnDisplayDrawer.setIcon(VaadinIcons.MINUS);
        btnDisplayDrawer.addStyleName("btn_toggle");
        btnDisplayDrawer.addStyleName("borderless");
        btnDisplayDrawer.addStyleName("icon-only");
        btnDisplayDrawer.addStyleName("colored-when-hover");
        btnDisplayDrawer.addStyleName("filter_label");
        btnDisplayDrawer.addStyleName("active-noborder");
        horizontalLayout.addComponent(btnDisplayDrawer);
        horizontalLayout.setComponentAlignment(btnDisplayDrawer, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addStyleName("group_filter");
        verticalLayout.setMargin(false);
        addComponent(verticalLayout);

        cbDisplayDigital = new CheckBox(Messages.get("filter.display.digital"));
        verticalLayout.addComponent(cbDisplayDigital);

        cbDisplayAnalog = new CheckBox(Messages.get("filter.display.analog"));
        verticalLayout.addComponent(cbDisplayAnalog);

        cbDisplayAutomatic = new CheckBox(Messages.get("filter.display.automatic"));
        verticalLayout.addComponent(cbDisplayAutomatic);

        cbDisplayChronograph = new CheckBox(Messages.get("filter.display.chronograph"));
        verticalLayout.addComponent(cbDisplayChronograph);

        horizontalLayout.addLayoutClickListener(e -> btnDisplayDrawer.click());
    }

    private void init() {
        setId("filteringGroup");
        setMargin(new MarginInfo(true, true, true, true));
        setSpacing(false);
        setWidth("300px");

        setupFiltersLabel();
        setupPriceFilter();
        setupDialFilter();
        setupDialColorFilter();
        setupBrandFilter();
        setupDiscountFilter();
        setupStrapFilter();
        setupStrapColorFilter();
        setupDisplayFilter();
    }

    private void setEvent() {
        cbCircleDial.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDialCircle(cbCircleDial.getValue());
            eventHandler.filter();
        });

        cbSquareDial.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDialSquare(cbSquareDial.getValue());
            eventHandler.filter();
        });

        cbDialColorBlack.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDialColorBlack(cbDialColorBlack.getValue());
            eventHandler.filter();
        });

        cbDialColorWhite.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDialColorWhite(cbDialColorWhite.getValue());
            eventHandler.filter();
        });

        cbDialColorGray.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDialColorGray(cbDialColorGray.getValue());
            eventHandler.filter();
        });

        cbDialColorBrown.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDialColorBrown(cbDialColorBrown.getValue());
            eventHandler.filter();
        });

        cbDialColorBlue.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDialColorBlue(cbDialColorBlue.getValue());
            eventHandler.filter();
        });

        cbBrandRado.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandRado(cbBrandRado.getValue());
            eventHandler.filter();
        });

        cbBrandDaniel.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandDanielWellington(cbBrandDaniel.getValue());
            eventHandler.filter();
        });

        cbBrandLongines.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandLongines(cbBrandLongines.getValue());
            eventHandler.filter();
        });

        cbBrandReefTiger.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandReefTiger(cbBrandReefTiger.getValue());
            eventHandler.filter();
        });

        cbBrandBulova.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandBulova(cbBrandBulova.getValue());
            eventHandler.filter();
        });

        cbBrandGoS.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandGoS(cbBrandGoS.getValue());
            eventHandler.filter();
        });

        cbBrandLangeAndSohne.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandLangeAndSohne(cbBrandLangeAndSohne.getValue());
            eventHandler.filter();
        });

        cbBrandOmega.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandOmega(cbBrandOmega.getValue());
            eventHandler.filter();
        });

        cbBrandRomainGauthier.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectBrandRomainGauthier(cbBrandRomainGauthier.getValue());
            eventHandler.filter();
        });

        cbDiscountRate1.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate1(cbDiscountRate1.getValue());
            eventHandler.filter();
        });

        cbDiscountRate2.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate2(cbDiscountRate2.getValue());
            eventHandler.filter();
        });

        cbDiscountRate3.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate3(cbDiscountRate3.getValue());
            eventHandler.filter();
        });

        cbDiscountRate4.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate4(cbDiscountRate4.getValue());
            eventHandler.filter();
        });

        cbDiscountRate5.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate5(cbDiscountRate5.getValue());
            eventHandler.filter();
        });

        cbDiscountRate6.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate6(cbDiscountRate6.getValue());
            eventHandler.filter();
        });

        cbDiscountRate7.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate7(cbDiscountRate7.getValue());
            eventHandler.filter();
        });

        cbDiscountRate8.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate8(cbDiscountRate8.getValue());
            eventHandler.filter();
        });

        cbDiscountRate9.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate9(cbDiscountRate9.getValue());
            eventHandler.filter();
        });

        cbDiscountRate10.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDiscountRate10(cbDiscountRate10.getValue());
            eventHandler.filter();
        });

        cbStrapLeather.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectStrapLeather(cbStrapLeather.getValue());
            eventHandler.filter();
        });

        cbStrapStainlessSteel.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectStrapStainlessSteel(cbStrapStainlessSteel.getValue());
            eventHandler.filter();
        });

        cbStrapColorBlack.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectStrapColorBlack(cbStrapColorBlack.getValue());
            eventHandler.filter();
        });

        cbStrapColorWhite.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectStrapColorWhite(cbStrapColorWhite.getValue());
            eventHandler.filter();
        });

        cbStrapColorGray.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectStrapColorGray(cbStrapColorGray.getValue());
            eventHandler.filter();
        });

        cbStrapColorBrown.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectStrapColorBrown(cbStrapColorBrown.getValue());
            eventHandler.filter();
        });

        cbStrapColorBlue.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectStrapColorBlue(cbStrapColorBlue.getValue());
            eventHandler.filter();
        });

        cbDisplayDigital.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDisplayDigital(cbDisplayDigital.getValue());
            eventHandler.filter();
        });

        cbDisplayAnalog.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDisplayAnalog(cbDisplayAnalog.getValue());
            eventHandler.filter();
        });

        cbDisplayAutomatic.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDisplayAutomatic(cbDisplayAutomatic.getValue());
            eventHandler.filter();
        });

        cbDisplayChronograph.addValueChangeListener(e -> {
            eventHandler.getFilterBean().setSelectDisplayChronograph(cbDisplayChronograph.getValue());
            eventHandler.filter();
        });

        btnDialDrawer.addClickListener(e -> toggleFilter(btnDialDrawer, cbCircleDial.getParent()));
        btnDialColorDrawer.addClickListener(e -> toggleFilter(btnDialColorDrawer, cbDialColorBlack.getParent()));
        btnBrandDrawer.addClickListener(e -> toggleFilter(btnBrandDrawer, cbBrandRado.getParent()));
        btnDiscountDrawer.addClickListener(e -> toggleFilter(btnDiscountDrawer, cbDiscountRate1.getParent()));
        btnStrapDrawer.addClickListener(e -> toggleFilter(btnStrapDrawer, cbStrapLeather.getParent()));
        btnStrapColorDrawer.addClickListener(e -> toggleFilter(btnStrapColorDrawer, cbStrapColorBlack.getParent()));
        btnDisplayDrawer.addClickListener(e -> toggleFilter(btnDisplayDrawer, cbDisplayDigital.getParent()));

        priceFrom.addFocusListener(e -> Page.getCurrent().getJavaScript().execute("acceptNumber(priceFrom)"));
        priceTo.addFocusListener(e -> Page.getCurrent().getJavaScript().execute("acceptNumber(priceTo)"));

        priceFrom.addValueChangeListener(e ->
                eventHandler.getFilterBean().setPriceRange(priceFrom.getValue(), priceTo.getValue()));

        priceTo.addValueChangeListener(e ->
                eventHandler.getFilterBean().setPriceRange(priceFrom.getValue(), priceTo.getValue()));

        priceFrom.addBlurListener(e -> {
            if (priceFrom.getValue().isEmpty()) {
                priceFrom.setValue(String.valueOf(new Double(minSlider.getMin()).intValue()));
            }
            minSlider.setValue(Utils.text2Double(priceFrom.getValue(), minSlider.getMin()));
        });

        priceTo.addBlurListener(e -> {
            if (priceTo.getValue().isEmpty()) {
                priceTo.setValue(String.valueOf(new Double(maxSlider.getMax()).intValue()));
            }
            maxSlider.setValue(Utils.text2Double(priceTo.getValue(), maxSlider.getMax()));
        });

        minSlider.addValueChangeListener(e -> {
            priceFrom.setValue(String.valueOf(minSlider.getValue().intValue()));
            eventHandler.filter();
        });

        maxSlider.addValueChangeListener(e -> {
            priceTo.setValue(String.valueOf(maxSlider.getValue().intValue()));
            eventHandler.filter();
        });
    }

    private void toggleFilter(Button toggleBtn, HasComponents groupFilter) {
        if (toggleBtn.getIcon() == VaadinIcons.MINUS) {
            groupFilter.addStyleName("filter_collapsed");
            toggleBtn.setIcon(VaadinIcons.PLUS);
        } else {
            groupFilter.removeStyleName("filter_collapsed");
            toggleBtn.setIcon(VaadinIcons.MINUS);
        }
    }
}
