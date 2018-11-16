package com.example.comviva.con_it.java.bean;

/**
 * Created by comviva on 11/23/2017.
 */

public class OffersBean {
    private String shopName;
    private String productName;
    private String offerValue;

    public OffersBean(String shopName, String productName, String offerValue) {
        this.shopName = shopName;
        this.productName = productName;
        this.offerValue = offerValue;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProductName() {
        return productName;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setOfferValue(String offerValue) {
        this.offerValue = offerValue;
    }
}

