package com.example.comviva.con_it.java.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by comviva on 11/23/2017.
 */

public class GlobalBeaconOffersData {
    private static  Map<String,List<OffersBean>> macIdOffersMap;
    private static List<DeviceBean> previosDeviceList;
    private static List<String> beaconList;
    private static int flag = 0;



    public static List<String> getBeaconList() {
        return beaconList;
    }

    public static void setBeaconList(List<String> beaconList) {
        GlobalBeaconOffersData.beaconList = beaconList;
    }



    public static List<DeviceBean> getPreviosDeviceList() {
        return previosDeviceList;
    }

    public static void setPreviosDeviceList(List<DeviceBean> previosDeviceList) {
        GlobalBeaconOffersData.previosDeviceList = previosDeviceList;
    }

    public static int getFlag() {
        return flag;
    }

    public static void setFlag(int flag) {
        GlobalBeaconOffersData.flag = flag;
    }

    public static Map<String, List<OffersBean>> getMacIdOffersMap() {
        return macIdOffersMap;
    }

    public static void setMacIdOffersMap(Map<String, List<OffersBean>> macIdOffersMap) {
        GlobalBeaconOffersData.macIdOffersMap = macIdOffersMap;
    }
}
