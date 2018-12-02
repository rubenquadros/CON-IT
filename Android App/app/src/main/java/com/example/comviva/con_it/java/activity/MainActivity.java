package com.example.comviva.con_it.java.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.comviva.con_it.R;
import com.example.comviva.con_it.java.adapter.RecyclerAdapter;
import com.example.comviva.con_it.java.bean.GlobalBeaconOffersData;
import com.example.comviva.con_it.java.bean.DeviceBean;
import com.example.comviva.con_it.java.bean.OffersBean;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;
    private final int REQUEST_CHECK_SETTINGS = 0x1;

    private Handler handler;
    private Runnable runnableCode;

    GoogleApiClient mGoogleApiClient;
    private boolean isResultsAvailable = true;

    WifiManager mainWifi;
    WifiReceiver wifiReceiver;
    WifiSignalChangeReceiver wifiSignalChangeReceiver;
    List<ScanResult> wifiList;

    public static int DELAY = 1;
    private List<DeviceBean> deviceList;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;



    private Map<String,List<OffersBean>> macIdOffersMap;
    private List<String> beaconList;

    @SuppressLint("WifiManagerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeBeacons();
        initViews();



        mainWifi = (WifiManager) getSystemService(WIFI_SERVICE);

        if(mainWifi.isWifiEnabled()==false)
        {
            mainWifi.setWifiEnabled(true);
        }

        requestPermission();


    }

    private void initViews(){
        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        List<OffersBean> devices = new ArrayList<>();
       adapter = new RecyclerAdapter(devices,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }


    private void requestPermission() {
        //Marshmellow check permission for location
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
        }
        else {
            requestEnableLocation();
        }
    }


    public void requestEnableLocation()
    {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) MainActivity.this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .build();

        mGoogleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setInterval(100000);
        locationRequest.setFastestInterval(5000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        initRecieversStartScan();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    public void initRecieversStartScan() {

        wifiReceiver = new WifiReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.registerReceiver(wifiReceiver, intentFilter);

        WifiSignalChangeReceiver wifiSignalChangeReceiver = new WifiSignalChangeReceiver();
        IntentFilter intentFilterSignal = new IntentFilter();
        intentFilterSignal.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilterSignal.addAction(WifiManager.RSSI_CHANGED_ACTION);
        this.registerReceiver(wifiSignalChangeReceiver, intentFilterSignal);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    class WifiReceiver extends BroadcastReceiver
    {
        public void onReceive(Context c, Intent intent)
        {


            wifiList = mainWifi.getScanResults();

            deviceList= new ArrayList<>();
         for(ScanResult scanResult : wifiList)
         {

             if(beaconList.contains(scanResult.BSSID)){
               DeviceBean deviceBean=new DeviceBean();
             deviceBean.setDeviceId(scanResult.BSSID);
              deviceBean.setDevicename(scanResult.SSID);
                 deviceBean.setDeviceStrength(String.valueOf(scanResult.level));
                deviceList.add(deviceBean);
                  }

         }


              UpdateView(deviceList);
        }
    }

    class WifiSignalChangeReceiver extends BroadcastReceiver
    {
        @SuppressLint("LongLogTag")
        public void onReceive(Context c, Intent intent)
        {

            wifiList = mainWifi.getScanResults();

            DeviceBean deviceBean;
            deviceList= new ArrayList<>();
            for(ScanResult scanResult : wifiList) {

                if (beaconList.contains(scanResult.BSSID)) {
                    Log.i("device Name  ",scanResult.SSID);
                    deviceBean = new DeviceBean();
                    deviceBean.setDeviceId(scanResult.BSSID);
                    deviceBean.setDevicename(scanResult.SSID);
                    deviceBean.setDeviceStrength(String.valueOf(scanResult.level));
                    deviceList.add(deviceBean);
                }

            }

            UpdateView(deviceList);

        }
    }

public void UpdateView(List<DeviceBean> deviceList) {



    List<OffersBean> offersList=new ArrayList<>();
    Collections.sort(deviceList, new Comparator<DeviceBean>() {
        @Override
        public int compare(DeviceBean a, DeviceBean b) {
            return (Integer.parseInt(b.getDeviceStrength()) > Integer.parseInt(a.getDeviceStrength()) ? 1 : -1);
        }
    });

    if (GlobalBeaconOffersData.getFlag() == 0) {

        GlobalBeaconOffersData.setFlag(1);

    } else  if(ValidateDeviceList( GlobalBeaconOffersData.getPreviosDeviceList(),deviceList)) {
        return;
    }
    for(DeviceBean deviceBean:deviceList){
        offersList.addAll( GlobalBeaconOffersData.getMacIdOffersMap().get(deviceBean.getDeviceId()));
    }
    adapter.UpdateData(offersList);
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    GlobalBeaconOffersData.setPreviosDeviceList(deviceList);
}

    private boolean ValidateDeviceList(List<DeviceBean> previosDeviceList, List<DeviceBean> deviceList) {

        int size=previosDeviceList.size()<deviceList.size()?previosDeviceList.size():deviceList.size();

        for(int i=0;i<size;i++){
            if(!previosDeviceList.get(i).getDeviceId().equals(deviceList.get(i).getDeviceId())){
                return false;
            }
        }
        return true;
    }


    public void startScanningScheduler(){

        handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {
                mainWifi.startScan();
                handler.postDelayed(runnableCode, DELAY * 200);
            }
        };
        handler.post(runnableCode);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        initRecieversStartScan();
                        break;

                    case Activity.RESULT_CANCELED:
                        requestEnableLocation();//keep asking if imp or do whatever
                        break;

                }

                break;
        }
    }

public void initializeBeacons(){

    beaconList=GlobalBeaconOffersData.getBeaconList();

}


    @Override
    protected void onStart() {
        super.onStart();
        initRecieversStartScan();
        startScanningScheduler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
        removeCallback();

    }

    public void removeCallback() {
        if(handler != null){
            handler.removeCallbacks(runnableCode);
            Log.d("nextgen", "removeCallback: removedCallback");
        }
    }
}



