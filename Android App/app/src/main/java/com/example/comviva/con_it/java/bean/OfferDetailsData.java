package com.example.comviva.con_it.java.bean;

/**
 * Created by comviva on 11/23/2017.
 */

public class OfferDetailsData {

    private String beaconId;
    private String beaconName;
    private String shopName;
    private String productName;
    private String offerValue;

    public OfferDetailsData(){

    }

    public OfferDetailsData(String beaconId, String beaconName, String shopName, String productName, String offerValue) {
        this.beaconId = beaconId;
        this.beaconName = beaconName;
        this.shopName = shopName;
        this.productName = productName;
        this.offerValue = offerValue;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(String offerValue) {
        this.offerValue = offerValue;
    }

    @Override
    public String toString() {
        return "OfferDetailsData{" +
                "beaconId='" + beaconId + '\'' +
                ", beaconName='" + beaconName + '\'' +
                ", shopName='" + shopName + '\'' +
                ", productName='" + productName + '\'' +
                ", offerValue='" + offerValue + '\'' +
                '}';
    }
}
