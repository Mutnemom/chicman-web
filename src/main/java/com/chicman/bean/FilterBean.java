package com.chicman.bean;

import com.chicman.model.Product;
import com.chicman.view.ProductItem;
import com.vaadin.ui.CssLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Arrays;
import java.util.List;

@SessionScope
@Component
public class FilterBean {

    public static final float DEFAULT_MIN_PRICE = 0.0f;
    public static final float DEFAULT_MAX_PRICE = 10000.0f;

    private final Logger log = LoggerFactory.getLogger(FilterBean.class);

    private boolean selectDialCircle;
    private boolean selectDialSquare;

    private boolean selectDialColorBlack;
    private boolean selectDialColorWhite;
    private boolean selectDialColorGray;
    private boolean selectDialColorBrown;
    private boolean selectDialColorBlue;

    private boolean selectBrandRado;
    private boolean selectBrandDanielWellington;
    private boolean selectBrandLongines;
    private boolean selectBrandReefTiger;
    private boolean selectBrandBulova;
    private boolean selectBrandGoS;
    private boolean selectBrandLangeAndSohne;
    private boolean selectBrandOmega;
    private boolean selectBrandRomainGauthier;

    private boolean selectDiscountRate1;
    private boolean selectDiscountRate2;
    private boolean selectDiscountRate3;
    private boolean selectDiscountRate4;
    private boolean selectDiscountRate5;
    private boolean selectDiscountRate6;
    private boolean selectDiscountRate7;
    private boolean selectDiscountRate8;
    private boolean selectDiscountRate9;
    private boolean selectDiscountRate10;

    private boolean selectStrapLeather;
    private boolean selectStrapStainlessSteel;

    private boolean selectStrapColorBlack;
    private boolean selectStrapColorWhite;
    private boolean selectStrapColorGray;
    private boolean selectStrapColorBrown;
    private boolean selectStrapColorBlue;

    private boolean selectDisplayDigital;
    private boolean selectDisplayAnalog;
    private boolean selectDisplayAutomatic;
    private boolean selectDisplayChronograph;
    private float minPrice;
    private float maxPrice;

    public void reset() {
        this.selectDialCircle = false;
        this.selectDialSquare = false;

        this.selectBrandRado = false;
        this.selectBrandDanielWellington = false;
        this.selectBrandLongines = false;
        this.selectBrandReefTiger = false;
        this.selectBrandBulova = false;
        this.selectBrandGoS = false;
        this.selectBrandLangeAndSohne = false;
        this.selectBrandOmega = false;
        this.selectBrandRomainGauthier = false;

        this.selectDiscountRate1 = false;
        this.selectDiscountRate2 = false;
        this.selectDiscountRate3 = false;
        this.selectDiscountRate4 = false;
        this.selectDiscountRate5 = false;
        this.selectDiscountRate6 = false;
        this.selectDiscountRate7 = false;
        this.selectDiscountRate8 = false;
        this.selectDiscountRate9 = false;
        this.selectDiscountRate10 = false;

        this.selectStrapLeather = false;
        this.selectStrapStainlessSteel = false;

        this.selectDisplayDigital = false;
        this.selectDisplayAnalog = false;
        this.selectDisplayAutomatic = false;
        this.selectDisplayChronograph = false;

        this.selectDialColorBlack = false;
        this.selectDialColorWhite = false;
        this.selectDialColorGray = false;
        this.selectDialColorBrown = false;
        this.selectDialColorBlue = false;

        this.selectStrapColorBlack = false;
        this.selectStrapColorWhite = false;
        this.selectStrapColorGray = false;
        this.selectStrapColorBrown = false;
        this.selectStrapColorBlue = false;
    }

    public boolean isSelectDialCircle() {
        return selectDialCircle;
    }

    public void setSelectDialCircle(boolean selectDialCircle) {
        this.selectDialCircle = selectDialCircle;
    }

    public boolean isSelectDialSquare() {
        return selectDialSquare;
    }

    public void setSelectDialSquare(boolean selectDialSquare) {
        this.selectDialSquare = selectDialSquare;
    }

    public boolean isSelectDialColorBlack() {
        return selectDialColorBlack;
    }

    public void setSelectDialColorBlack(boolean selectDialColorBlack) {
        this.selectDialColorBlack = selectDialColorBlack;
    }

    public boolean isSelectDialColorWhite() {
        return selectDialColorWhite;
    }

    public void setSelectDialColorWhite(boolean selectDialColorWhite) {
        this.selectDialColorWhite = selectDialColorWhite;
    }

    public boolean isSelectDialColorGray() {
        return selectDialColorGray;
    }

    public void setSelectDialColorGray(boolean selectDialColorGray) {
        this.selectDialColorGray = selectDialColorGray;
    }

    public boolean isSelectDialColorBrown() {
        return selectDialColorBrown;
    }

    public void setSelectDialColorBrown(boolean selectDialColorBrown) {
        this.selectDialColorBrown = selectDialColorBrown;
    }

    public boolean isSelectDialColorBlue() {
        return selectDialColorBlue;
    }

    public void setSelectDialColorBlue(boolean selectDialColorBlue) {
        this.selectDialColorBlue = selectDialColorBlue;
    }

    public boolean isSelectBrandRado() {
        return selectBrandRado;
    }

    public void setSelectBrandRado(boolean selectBrandRado) {
        this.selectBrandRado = selectBrandRado;
    }

    public boolean isSelectBrandDanielWellington() {
        return selectBrandDanielWellington;
    }

    public void setSelectBrandDanielWellington(boolean selectBrandDanielWellington) {
        this.selectBrandDanielWellington = selectBrandDanielWellington;
    }

    public boolean isSelectBrandLongines() {
        return selectBrandLongines;
    }

    public void setSelectBrandLongines(boolean selectBrandLongines) {
        this.selectBrandLongines = selectBrandLongines;
    }

    public boolean isSelectBrandReefTiger() {
        return selectBrandReefTiger;
    }

    public void setSelectBrandReefTiger(boolean selectBrandReefTiger) {
        this.selectBrandReefTiger = selectBrandReefTiger;
    }

    public boolean isSelectBrandBulova() {
        return selectBrandBulova;
    }

    public void setSelectBrandBulova(boolean selectBrandBulova) {
        this.selectBrandBulova = selectBrandBulova;
    }

    public boolean isSelectBrandGoS() {
        return selectBrandGoS;
    }

    public void setSelectBrandGoS(boolean selectBrandGoS) {
        this.selectBrandGoS = selectBrandGoS;
    }

    public boolean isSelectBrandLangeAndSohne() {
        return selectBrandLangeAndSohne;
    }

    public void setSelectBrandLangeAndSohne(boolean selectBrandLangeAndSohne) {
        this.selectBrandLangeAndSohne = selectBrandLangeAndSohne;
    }

    public boolean isSelectBrandOmega() {
        return selectBrandOmega;
    }

    public void setSelectBrandOmega(boolean selectBrandOmega) {
        this.selectBrandOmega = selectBrandOmega;
    }

    public boolean isSelectBrandRomainGauthier() {
        return selectBrandRomainGauthier;
    }

    public void setSelectBrandRomainGauthier(boolean selectBrandRomainGauthier) {
        this.selectBrandRomainGauthier = selectBrandRomainGauthier;
    }

    public boolean isSelectDiscountRate1() {
        return selectDiscountRate1;
    }

    public void setSelectDiscountRate1(boolean selectDiscountRate1) {
        this.selectDiscountRate1 = selectDiscountRate1;
    }

    public boolean isSelectDiscountRate2() {
        return selectDiscountRate2;
    }

    public void setSelectDiscountRate2(boolean selectDiscountRate2) {
        this.selectDiscountRate2 = selectDiscountRate2;
    }

    public boolean isSelectDiscountRate3() {
        return selectDiscountRate3;
    }

    public void setSelectDiscountRate3(boolean selectDiscountRate3) {
        this.selectDiscountRate3 = selectDiscountRate3;
    }

    public boolean isSelectDiscountRate4() {
        return selectDiscountRate4;
    }

    public void setSelectDiscountRate4(boolean selectDiscountRate4) {
        this.selectDiscountRate4 = selectDiscountRate4;
    }

    public boolean isSelectDiscountRate5() {
        return selectDiscountRate5;
    }

    public void setSelectDiscountRate5(boolean selectDiscountRate5) {
        this.selectDiscountRate5 = selectDiscountRate5;
    }

    public boolean isSelectDiscountRate6() {
        return selectDiscountRate6;
    }

    public void setSelectDiscountRate6(boolean selectDiscountRate6) {
        this.selectDiscountRate6 = selectDiscountRate6;
    }

    public boolean isSelectDiscountRate7() {
        return selectDiscountRate7;
    }

    public void setSelectDiscountRate7(boolean selectDiscountRate7) {
        this.selectDiscountRate7 = selectDiscountRate7;
    }

    public boolean isSelectDiscountRate8() {
        return selectDiscountRate8;
    }

    public void setSelectDiscountRate8(boolean selectDiscountRate8) {
        this.selectDiscountRate8 = selectDiscountRate8;
    }

    public boolean isSelectDiscountRate9() {
        return selectDiscountRate9;
    }

    public void setSelectDiscountRate9(boolean selectDiscountRate9) {
        this.selectDiscountRate9 = selectDiscountRate9;
    }

    public boolean isSelectDiscountRate10() {
        return selectDiscountRate10;
    }

    public void setSelectDiscountRate10(boolean selectDiscountRate10) {
        this.selectDiscountRate10 = selectDiscountRate10;
    }

    public boolean isSelectStrapLeather() {
        return selectStrapLeather;
    }

    public void setSelectStrapLeather(boolean selectStrapLeather) {
        this.selectStrapLeather = selectStrapLeather;
    }

    public boolean isSelectStrapStainlessSteel() {
        return selectStrapStainlessSteel;
    }

    public void setSelectStrapStainlessSteel(boolean selectStrapStainlessSteel) {
        this.selectStrapStainlessSteel = selectStrapStainlessSteel;
    }

    public boolean isSelectStrapColorBlack() {
        return selectStrapColorBlack;
    }

    public void setSelectStrapColorBlack(boolean selectStrapColorBlack) {
        this.selectStrapColorBlack = selectStrapColorBlack;
    }

    public boolean isSelectStrapColorWhite() {
        return selectStrapColorWhite;
    }

    public void setSelectStrapColorWhite(boolean selectStrapColorWhite) {
        this.selectStrapColorWhite = selectStrapColorWhite;
    }

    public boolean isSelectStrapColorGray() {
        return selectStrapColorGray;
    }

    public void setSelectStrapColorGray(boolean selectStrapColorGray) {
        this.selectStrapColorGray = selectStrapColorGray;
    }

    public boolean isSelectStrapColorBrown() {
        return selectStrapColorBrown;
    }

    public void setSelectStrapColorBrown(boolean selectStrapColorBrown) {
        this.selectStrapColorBrown = selectStrapColorBrown;
    }

    public boolean isSelectStrapColorBlue() {
        return selectStrapColorBlue;
    }

    public void setSelectStrapColorBlue(boolean selectStrapColorBlue) {
        this.selectStrapColorBlue = selectStrapColorBlue;
    }

    public boolean isSelectDisplayDigital() {
        return selectDisplayDigital;
    }

    public void setSelectDisplayDigital(boolean selectDisplayDigital) {
        this.selectDisplayDigital = selectDisplayDigital;
    }

    public boolean isSelectDisplayAnalog() {
        return selectDisplayAnalog;
    }

    public void setSelectDisplayAnalog(boolean selectDisplayAnalog) {
        this.selectDisplayAnalog = selectDisplayAnalog;
    }

    public boolean isSelectDisplayAutomatic() {
        return selectDisplayAutomatic;
    }

    public void setSelectDisplayAutomatic(boolean selectDisplayAutomatic) {
        this.selectDisplayAutomatic = selectDisplayAutomatic;
    }

    public boolean isSelectDisplayChronograph() {
        return selectDisplayChronograph;
    }

    public void setSelectDisplayChronograph(boolean selectDisplayChronograph) {
        this.selectDisplayChronograph = selectDisplayChronograph;
    }

    public void createFilteredProduct(CssLayout groupMainCardLayout, List<Product> productList) {
        if (isSelectFilterAtLeastOne()) {
            for (int i = 0; i < productList.size(); i++) {
                if (groupMainCardLayout.getComponent(i) instanceof ProductItem) {
                    ((ProductItem) groupMainCardLayout.getComponent(i)).setShow(true);
                }
            }

            for (int i = 0; i < productList.size(); i++) {
                if (groupMainCardLayout.getComponent(i) instanceof ProductItem) {
                    ProductItem productItem = (ProductItem) groupMainCardLayout.getComponent(i);

                    if (isFilterByDial()) {
                        filterDial(productItem);
                    } else {
                        // no filter by dial
                        // set all item is filtered by dial
                        productItem.setFilteredByDial(true);
                    }

                    if (isFilterByBrand()) {
                        filterBrand(productItem);
                    } else {
                        // no filter by brand
                        // set all item is filtered by brand
                        productItem.setFilteredByBrand(true);
                    }

                    if (isFilterByDiscount()) {
                        filterDiscount(productItem);
                    } else {
                        // no filter by discount
                        // set all item is filtered by discount
                        productItem.setFilteredByDiscount(true);
                    }

                    if (isFilterByStrap()) {
                        filterStrap(productItem);
                    } else {
                        // no filter by strap
                        // set all item is filtered by strap
                        productItem.setFilteredByStrap(true);
                    }

                    if (isFilterByDisplay()) {
                        filterDisplay(productItem);
                    } else {
                        productItem.setFilteredByDisplay(true);
                    }

                    if (isFilterByDialColor()) {
                        filterDialColor(productItem);
                    } else {
                        productItem.setFilteredByDialColor(true);
                    }

                    if (isFilterByStrapColor()) {
                        filterStrapColor(productItem);
                    } else {
                        productItem.setFilteredByStrapColor(true);
                    }

                }
            }
        } else {
            for (int i = 0; i < productList.size(); i++) {
                if (groupMainCardLayout.getComponent(i) instanceof ProductItem) {
                    show((ProductItem) groupMainCardLayout.getComponent(i));
                }
            }
        }
    }

    private boolean isFilteredDialBrandDiscountStrap(ProductItem productItem) {
        return productItem.isFilteredByDial()
                && productItem.isFilteredByBrand()
                && productItem.isFilteredByDiscount()
                && productItem.isFilteredByStrap();
    }

    private boolean isFilteredDialBrandDiscountStrapDisplay(ProductItem productItem) {
        return productItem.isFilteredByDial()
                && productItem.isFilteredByBrand()
                && productItem.isFilteredByDiscount()
                && productItem.isFilteredByStrap()
                && productItem.isFilteredByDisplay();
    }

    private boolean isFilteredDialBrandDiscountStrapDisplayDialColor(ProductItem productItem) {
        return productItem.isFilteredByDial()
                && productItem.isFilteredByDialColor()
                && productItem.isFilteredByBrand()
                && productItem.isFilteredByDiscount()
                && productItem.isFilteredByStrap()
                && productItem.isFilteredByDisplay();
    }

    private void filterStrapColor(ProductItem productMain) {
        if (isSelectStrapColorBlack()) {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                filterStrapColorBlack(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                hideStrapColorBlack(productMain);
            }
        }

        if (isSelectStrapColorWhite()) {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                filterStrapColorWhite(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                hideStrapColorWhite(productMain);
            }
        }

        if (isSelectStrapColorGray()) {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                filterStrapColorGray(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                hideStrapColorGray(productMain);
            }
        }

        if (isSelectStrapColorBrown()) {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                filterStrapColorBrown(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                hideStrapColorBrown(productMain);
            }
        }

        if (isSelectStrapColorBlue()) {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                filterStrapColorBlue(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplayDialColor(productMain)) {
                hideStrapColorBlue(productMain);
            }
        }
    }

    private void filterDialColor(ProductItem productMain) {
        if (isSelectDialColorBlack()) {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                filterDialColorBlack(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                hideDialColorBlack(productMain);
            }
        }

        if (isSelectDialColorWhite()) {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                filterDialColorWhite(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                hideDialColorWhite(productMain);
            }
        }

        if (isSelectDialColorGray()) {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                filterDialColorGray(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                hideDialColorGray(productMain);
            }
        }

        if (isSelectDialColorBrown()) {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                filterDialColorBrown(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                hideDialColorBrown(productMain);
            }
        }

        if (isSelectDialColorBlue()) {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                filterDialColorBlue(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrapDisplay(productMain)) {
                hideDialColorBlue(productMain);
            }
        }
    }

    private void filterDisplay(ProductItem productMain) {
        if (isSelectDisplayDigital()) {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                filterDisplayDigital(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                hideDisplayDigital(productMain);
            }
        }

        if (isSelectDisplayAnalog()) {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                filterDisplayAnalog(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                hideDisplayAnalog(productMain);
            }
        }

        if (isSelectDisplayAutomatic()) {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                filterDisplayAutomatic(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                hideDisplayAutomatic(productMain);
            }
        }

        if (isSelectDisplayChronograph()) {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                filterDisplayChronograph(productMain);
            }
        } else {
            if (isFilteredDialBrandDiscountStrap(productMain)) {
                hideDisplayChronograph(productMain);
            }
        }
    }

    private void filterStrap(ProductItem productMain) {
        if (isSelectStrapLeather()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand() && productMain.isFilteredByDiscount()) {
                filterStrapLeather(productMain);
            }
        } else {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand() && productMain.isFilteredByDiscount()) {
                hideStrapLeather(productMain);
            }
        }

        if (isSelectStrapStainlessSteel()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand() && productMain.isFilteredByDiscount()) {
                filterStrapStainlessSteel(productMain);
            }
        } else {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand() && productMain.isFilteredByDiscount()) {
                hideStrapStainlessSteel(productMain);
            }
        }
    }

    private void filterDiscount(ProductItem productMain) {
        if (isSelectDiscountRate1()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 0, 10);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 0, 10);
            }
        }

        if (isSelectDiscountRate2()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 10, 20);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 10, 20);
            }
        }

        if (isSelectDiscountRate3()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 20, 30);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 20, 30);
            }
        }

        if (isSelectDiscountRate4()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 30, 40);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 30, 40);
            }
        }

        if (isSelectDiscountRate5()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 40, 50);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 40, 50);
            }
        }

        if (isSelectDiscountRate6()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 50, 60);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 50, 60);
            }
        }

        if (isSelectDiscountRate7()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 60, 70);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 60, 70);
            }
        }

        if (isSelectDiscountRate8()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 70, 80);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 70, 80);
            }
        }

        if (isSelectDiscountRate9()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 80, 90);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 80, 90);
            }
        }

        if (isSelectDiscountRate10()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                filterDiscountRate(productMain, 90, 100);
            }
        } else if (isFilterByDiscount()) {
            if (productMain.isFilteredByDial() && productMain.isFilteredByBrand()) {
                hideDiscountRate(productMain, 90, 100);
            }
        }
    }

    private void filterBrand(ProductItem productMain) {
        if (isSelectBrandRado()) {
            if (productMain.isFilteredByDial()) {
                filterBrandRado(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandRado(productMain);
            }
        }

        if (isSelectBrandDanielWellington()) {
            if (productMain.isFilteredByDial()) {
                filterBrandDanielWellington(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandDanielWellington(productMain);
            }
        }

        if (isSelectBrandLongines()) {
            if (productMain.isFilteredByDial()) {
                filterBrandLongines(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandLongines(productMain);
            }
        }

        if (isSelectBrandReefTiger()) {
            if (productMain.isFilteredByDial()) {
                filterBrandReefTiger(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandReefTiger(productMain);
            }
        }

        if (isSelectBrandBulova()) {
            if (productMain.isFilteredByDial()) {
                filterBrandBulova(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandBulova(productMain);
            }
        }

        if (isSelectBrandGoS()) {
            if (productMain.isFilteredByDial()) {
                filterBrandGoS(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandGoS(productMain);
            }
        }

        if (isSelectBrandLangeAndSohne()) {
            if (productMain.isFilteredByDial()) {
                filterBrandLangeAndSohne(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandLangeAndSohne(productMain);
            }
        }

        if (isSelectBrandOmega()) {
            if (productMain.isFilteredByDial()) {
                filterBrandOmega(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandOmega(productMain);
            }
        }

        if (isSelectBrandRomainGauthier()) {
            if (productMain.isFilteredByDial()) {
                filterBrandRomainGauthier(productMain);
            }
        } else if (isFilterByBrand()) {
            if (productMain.isFilteredByDial()) {
                hideBrandRomainGauthier(productMain);
            }
        }
    }

    private void filterDial(ProductItem productMain) {
        if (isSelectDialCircle()) {
            filterDialCircle(productMain);
        } else if (isFilterByDial()){
            hideDialCircle(productMain);
        }

        if (isSelectDialSquare()) {
            filterDialSquare(productMain);
        }
    }

    private boolean isSelectFilterAtLeastOne() {
        Boolean[] filters = new Boolean[] {
                isFilterByDial(),
                isFilterByDialColor(),
                isFilterByBrand(),
                isFilterByDiscount(),
                isFilterByStrap(),
                isFilterByStrapColor(),
                isFilterByDisplay()
        };
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private boolean isFilterByDisplay() {
        Boolean[] filters = new Boolean[] {
                selectDisplayDigital,
                selectDisplayAnalog,
                selectDisplayAutomatic,
                selectDisplayChronograph
        };
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private boolean isFilterByStrap() {
        Boolean[] filters = new Boolean[] {selectStrapLeather, selectStrapStainlessSteel};
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private boolean isFilterByDiscount() {
        Boolean[] filters = new Boolean[] {
                selectDiscountRate1,
                selectDiscountRate2,
                selectDiscountRate3,
                selectDiscountRate4,
                selectDiscountRate5,
                selectDiscountRate6,
                selectDiscountRate7,
                selectDiscountRate8,
                selectDiscountRate9,
                selectDiscountRate10
        };
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private boolean isFilterByBrand() {
        Boolean[] filters = new Boolean[] {
                selectBrandRado,
                selectBrandDanielWellington,
                selectBrandLongines,
                selectBrandReefTiger,
                selectBrandBulova,
                selectBrandGoS,
                selectBrandLangeAndSohne,
                selectBrandOmega,
                selectBrandRomainGauthier
        };
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private boolean isFilterByDial() {
        Boolean[] filters = new Boolean[] {selectDialCircle, selectDialSquare};
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private boolean isFilterByDialColor() {
        Boolean[] filters = new Boolean[] {
                selectDialColorBlack,
                selectDialColorWhite,
                selectDialColorGray,
                selectDialColorBrown,
                selectDialColorBlue
        };
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private boolean isFilterByStrapColor() {
        Boolean[] filters = new Boolean[] {
                selectStrapColorBlack,
                selectStrapColorWhite,
                selectStrapColorGray,
                selectStrapColorBrown,
                selectStrapColorBlue
        };
        List<Boolean> filterList = Arrays.asList(filters);
        return filterList.contains(true);
    }

    private void filterDialCircle(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDialTypeId() == 1) {
            show(productItem);
            productItem.setFilteredByDial(true);
        } else {
            hide(productItem);
            productItem.setFilteredByDial(false);
        }
    }

    private void hideDialCircle(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDialTypeId() == 1) {
            hide(productItem);
            productItem.setFilteredByDial(false);
        }
    }

    private void filterDialSquare(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDialCircle) {
            // do with hide item
            if (!productItem.isShow() && productItem.getProduct().getProductDialTypeId() == 3) {
                show(productItem);
                productItem.setFilteredByDial(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDialTypeId() == 3) {
                    show(productItem);
                    productItem.setFilteredByDial(true);
                } else {
                    hide(productItem);
                    productItem.setFilteredByDial(false);
                }
            }
        }
    }

    private void filterBrandRado(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 1) {
            show(productItem);
            productItem.setFilteredByBrand(true);
        } else {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void hideBrandRado(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 1) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandDanielWellington(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 2 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 2) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandDanielWellington(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 2) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandLongines(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado || selectBrandDanielWellington) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 3 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 3) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandLongines(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 3) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandReefTiger(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado || selectBrandDanielWellington || selectBrandLongines) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 4 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 4) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else  {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandReefTiger(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 4) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandBulova(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado || selectBrandDanielWellington || selectBrandLongines || selectBrandReefTiger) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 5 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 5) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else  {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandBulova(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 5) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandGoS(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado || selectBrandDanielWellington || selectBrandLongines || selectBrandReefTiger
                || selectBrandBulova) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 6 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 6) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else  {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandGoS(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 6) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandLangeAndSohne(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado || selectBrandDanielWellington || selectBrandLongines || selectBrandReefTiger
                || selectBrandBulova || selectBrandGoS) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 7 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 7) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else  {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandLangeAndSohne(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 7) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandOmega(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado || selectBrandDanielWellington || selectBrandLongines || selectBrandReefTiger
                || selectBrandBulova || selectBrandGoS || selectBrandLangeAndSohne) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 8 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 8) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else  {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandOmega(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 8) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterBrandRomainGauthier(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectBrandRado || selectBrandDanielWellington || selectBrandLongines || selectBrandReefTiger
                || selectBrandBulova || selectBrandGoS || selectBrandLangeAndSohne || selectBrandOmega) {
            // do with hide item
            if (productItem.getProduct().getProductBrandId() == 9 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByBrand(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductBrandId() == 9) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByBrand(true);
                } else  {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByBrand(false);
                }
            }
        }
    }

    private void hideBrandRomainGauthier(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductBrandId() == 9) {
            hide(productItem);
            productItem.setFilteredByBrand(false);
        }
    }

    private void filterDiscountRateWithHideItem(ProductItem productItem, float minRate, float maxRate, float discount) {
        if (!productItem.isShow() && minRate <= discount && discount <= maxRate) {
            show(productItem);
            productItem.setFilteredByDiscount(true);
        }
    }

    private void filterDiscountRateWithShowItem(ProductItem productItem, float minRate, float maxRate, float discount) {
        if (productItem.isShow() || productItem.getProduct().getProductDiscount() == minRate) {
            if (minRate <= discount && discount <= maxRate) {
                // show matched item
                show(productItem);
                productItem.setFilteredByDiscount(true);
            } else {
                // hide mismatched item
                hide(productItem);
                productItem.setFilteredByDiscount(false);
            }
        }
    }

    private void filterDiscountRate(ProductItem productItem, float minRate, float maxRate) {
        if (productItem.getProduct() == null) return;

        float discount = productItem.getProduct().getProductDiscount();
        if (minRate == 0) {
            // first condition do with show item
            if (productItem.isShow()) {
                if (minRate <= discount && discount <= maxRate) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByDiscount(true);
                } else {
                    // hide mismatched item
                    hide(productItem);
                    productItem.setFilteredByDiscount(false);
                }
            }
        } else if (minRate == 10) {
            // check rate 1 is selected
            if (selectDiscountRate1) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 20) {
            // check rate 1,2 is selected
            if (selectDiscountRate1 || selectDiscountRate2) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 30) {
            // check rate 1,2,3 is selected
            if (selectDiscountRate1 || selectDiscountRate2 || selectDiscountRate3) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 40) {
            // check rate 1,2,3,4 is selected
            if (selectDiscountRate1 || selectDiscountRate2 || selectDiscountRate3 || selectDiscountRate4) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 50) {
            // check rate 1,2,3,4,5 is selected
            if (selectDiscountRate1 || selectDiscountRate2 || selectDiscountRate3 || selectDiscountRate4
                    || selectDiscountRate5) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 60) {
            // check rate 1,2,3,4,5,6 is selected
            if (selectDiscountRate1 || selectDiscountRate2 || selectDiscountRate3 || selectDiscountRate4
                    || selectDiscountRate5 || selectDiscountRate6) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 70) {
            // check rate 1,2,3,4,5,6,7 is selected
            if (selectDiscountRate1 || selectDiscountRate2 || selectDiscountRate3 || selectDiscountRate4
                    || selectDiscountRate5 || selectDiscountRate6 || selectDiscountRate7) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 80) {
            // check rate 1,2,3,4,5,6,7,8 is selected
            if (selectDiscountRate1 || selectDiscountRate2 || selectDiscountRate3 || selectDiscountRate4
                    || selectDiscountRate5 || selectDiscountRate6 || selectDiscountRate7 || selectDiscountRate8) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        } else if (minRate == 90) {
            // check rate 1,2,3,4,5,6,7,8,9 is selected
            if (selectDiscountRate1 || selectDiscountRate2 || selectDiscountRate3 || selectDiscountRate4
                    || selectDiscountRate5 || selectDiscountRate6 || selectDiscountRate7 || selectDiscountRate8
                    || selectDiscountRate9) {
                // do with hide item
                filterDiscountRateWithHideItem(productItem, minRate, maxRate, discount);
            } else {
                // do with show item
                filterDiscountRateWithShowItem(productItem, minRate, maxRate, discount);
            }
        }
    }

    private void hideDiscountRate(ProductItem productItem, float minRate, float maxRate) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        float discount = productItem.getProduct().getProductDiscount();
        if (minRate < discount && discount <= maxRate) {
            hide(productItem);
            productItem.setFilteredByDiscount(false);
        }
    }

    private void filterStrapLeather(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        // first condition do with show item
        if (productItem.getProduct().getProductStrapTypeId() == 1) {
            // show matched item
            show(productItem);
            productItem.setFilteredByStrap(true);
        } else {
            // hide mismatched item
            hide(productItem);
            productItem.setFilteredByStrap(false);
        }
    }

    private void hideStrapLeather(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductStrapTypeId() == 1) {
            hide(productItem);
            productItem.setFilteredByStrap(false);
        }
    }

    private void filterStrapStainlessSteel(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectStrapLeather) {
            // do with hide item
            if (productItem.getProduct().getProductStrapTypeId() == 2 && !productItem.isShow()) {
                show(productItem);
                productItem.setFilteredByStrap(true);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductStrapTypeId() == 2) {
                    // show matched item
                    show(productItem);
                    productItem.setFilteredByStrap(true);
                } else {
                    // hide mismatch item
                    hide(productItem);
                    productItem.setFilteredByStrap(false);
                }
            }
        }
    }

    private void hideStrapStainlessSteel(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductStrapTypeId() == 2) {
            hide(productItem);
            productItem.setFilteredByStrap(false);
        }
    }

    private void filterDisplayDigital(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        // first condition do with show item
        if (productItem.getProduct().getProductDisplayId() == 1) {
            // show matched item
            show(productItem);
        } else {
            // hide mismatched item
            hide(productItem);
        }
    }

    private void hideDisplayDigital(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDisplayId() == 1) {
            hide(productItem);
        }
    }

    private void filterDisplayAnalog(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDisplayDigital) {
            // do with hide item
            if (productItem.getProduct().getProductDisplayId() == 2 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDisplayId() == 2) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideDisplayAnalog(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDisplayId() == 2) {
            hide(productItem);
        }
    }

    private void filterDisplayAutomatic(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDisplayDigital || selectDisplayAnalog) {
            // do with hide item
            if (productItem.getProduct().getProductDisplayId() == 3 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDisplayId() == 3) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideDisplayAutomatic(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDisplayId() == 3) {
            hide(productItem);
        }
    }

    private void filterDisplayChronograph(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDisplayDigital || selectDisplayAnalog || selectDisplayAutomatic) {
            // do with hide item
            if (productItem.getProduct().getProductDisplayId() == 4 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDisplayId() == 4) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideDisplayChronograph(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDisplayId() == 4) {
            hide(productItem);
        }
    }


private void filterDialColorBlack(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        // first condition do with show item
        if (productItem.getProduct().getProductDialColorId() == 1) {
            // show matched item
            show(productItem);
        } else {
            // hide mismatched item
            hide(productItem);
        }
    }

    private void hideDialColorBlack(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDialColorId() == 1) {
            hide(productItem);
        }
    }

    private void filterDialColorWhite(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDialColorBlack) {
            // do with hide item
            if (productItem.getProduct().getProductDialColorId() == 2 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDialColorId() == 2) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideDialColorWhite(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDialColorId() == 2) {
            hide(productItem);
        }
    }

    private void filterDialColorGray(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDialColorBlack || selectDialColorWhite) {
            // do with hide item
            if (productItem.getProduct().getProductDialColorId() == 3 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDialColorId() == 3) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideDialColorGray(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDialColorId() == 3) {
            hide(productItem);
        }
    }

    private void filterDialColorBrown(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDialColorBlack || selectDialColorWhite || selectDialColorGray) {
            // do with hide item
            if (productItem.getProduct().getProductDialColorId() == 4 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDialColorId() == 4) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideDialColorBrown(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDialColorId() == 4) {
            hide(productItem);
        }
    }

    private void filterDialColorBlue(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectDialColorBlack || selectDialColorWhite || selectDialColorGray || selectDialColorBrown) {
            // do with hide item
            if (productItem.getProduct().getProductDialColorId() == 5 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductDialColorId() == 5) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideDialColorBlue(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductDialColorId() == 5) {
            hide(productItem);
        }
    }

    private void filterStrapColorBlack(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        // first condition do with show item
        if (productItem.getProduct().getProductStrapColorId() == 1) {
            // show matched item
            show(productItem);
        } else {
            // hide mismatched item
            hide(productItem);
        }
    }

    private void hideStrapColorBlack(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductStrapColorId() == 1) {
            hide(productItem);
        }
    }

    private void filterStrapColorWhite(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectStrapColorBlack) {
            // do with hide item
            if (productItem.getProduct().getProductStrapColorId() == 2 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductStrapColorId() == 2) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideStrapColorWhite(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductStrapColorId() == 2) {
            hide(productItem);
        }
    }

    private void filterStrapColorGray(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectStrapColorBlack || selectStrapColorWhite) {
            // do with hide item
            if (productItem.getProduct().getProductStrapColorId() == 3 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductStrapColorId() == 3) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideStrapColorGray(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductStrapColorId() == 3) {
            hide(productItem);
        }
    }

    private void filterStrapColorBrown(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectStrapColorBlack || selectStrapColorWhite || selectStrapColorGray) {
            // do with hide item
            if (productItem.getProduct().getProductStrapColorId() == 4 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductStrapColorId() == 4) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideStrapColorBrown(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductStrapColorId() == 4) {
            hide(productItem);
        }
    }

    private void filterStrapColorBlue(ProductItem productItem) {
        if (productItem.getProduct() == null) return;

        if (selectStrapColorBlack || selectStrapColorWhite || selectStrapColorGray || selectStrapColorBrown) {
            // do with hide item
            if (productItem.getProduct().getProductStrapColorId() == 5 && !productItem.isShow()) {
                show(productItem);
            }
        } else {
            // do with show item
            if (productItem.isShow()) {
                if (productItem.getProduct().getProductStrapColorId() == 5) {
                    // show matched item
                    show(productItem);
                } else {
                    // hide mismatched item
                    hide(productItem);
                }
            }
        }
    }

    private void hideStrapColorBlue(ProductItem productItem) {
        if (productItem.getProduct() == null || !productItem.isShow()) return;

        if (productItem.getProduct().getProductStrapColorId() == 5) {
            hide(productItem);
        }
    }

    private void hide(ProductItem productItem) {
        productItem.hideItem();
        productItem.setShow(false);
    }

    private void show(ProductItem productItem) {
        if (minPrice <= productItem.getProduct().getProductPriceSale()
                && productItem.getProduct().getProductPriceSale() <= maxPrice) {
            productItem.showItem();
            productItem.setShow(true);
        } else {
            hide(productItem);
        }
    }

    public void setPriceRange(String minPrice, String maxPrice) {
        try {
            this.minPrice = Float.parseFloat(minPrice);
            this.maxPrice = Float.parseFloat(maxPrice);
        } catch (NumberFormatException ex) {
            this.minPrice = DEFAULT_MIN_PRICE;
            this.maxPrice = DEFAULT_MAX_PRICE;
        }

        log.info("set price range from " + this.minPrice + " to " + this.maxPrice);
    }

    public void setPriceRange(float min, float max) {
        minPrice = min;
        maxPrice = max;
    }

}
