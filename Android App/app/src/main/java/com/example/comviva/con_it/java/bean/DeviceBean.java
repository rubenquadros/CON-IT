package com.example.comviva.con_it.java.bean;

/**
 * Created by comviva on 11/23/2017.
 */

public class DeviceBean {


    @Override
    public String toString() {
        return "DeviceBean{" +
                "devicename='" + devicename + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceStrength='" + deviceStrength + '\'' +
                '}';
    }

    private String devicename;
    private String deviceId;
    private String deviceStrength;

    public String getDevicename() {
        return devicename;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceStrength() {
        return deviceStrength;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceStrength(String deviceStrength) {
        this.deviceStrength = deviceStrength;
    }
}
