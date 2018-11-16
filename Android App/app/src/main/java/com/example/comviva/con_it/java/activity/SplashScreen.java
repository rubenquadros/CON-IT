package com.example.comviva.con_it.java.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.comviva.con_it.R;
import com.example.comviva.con_it.java.bean.GlobalBeaconOffersData;
import com.example.comviva.con_it.java.bean.OfferDetailsData;
import com.example.comviva.con_it.java.bean.OffersBean;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    private FirebaseDatabase myDatabase;
    private DatabaseReference myReference;
    private String referenceName="OfferDetailsData";
    private List<OfferDetailsData> myList=new ArrayList<>();
    private List<String> beaconList=new ArrayList<>();
    private List<OffersBean> offersBeanList=new ArrayList<>();
    private Map<String,List<OffersBean>> macIdOffersMap=new HashMap<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        dialog = new ProgressDialog(SplashScreen.this);
        dialog.setTitle("CON-it Starting ....");
        dialog.setMessage("Fetching Offers ....");
        dialog.show();

        //Check if wifi is enabled
        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()){
            Toast.makeText(this,"Switching on WIFI", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }

        //Check if there is active internet connection
        if(!isNetworkAvailable()){
            Toast.makeText(this, "This device is not connected to internet", Toast.LENGTH_LONG).show();
        }

        //Initialize app in firebase
        FirebaseApp.initializeApp(SplashScreen.this);

        //Get instance of firebase
        myDatabase = FirebaseDatabase.getInstance();

        //Get reference to the node in db
        myReference = myDatabase.getReference(referenceName);
        Log.i("REFERENCE", myReference.toString());
        myReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myList = new ArrayList<>();

                for(DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                    OfferDetailsData value = myDataSnapshot.getValue(OfferDetailsData.class);
                    OffersBean offersBean=new OffersBean(value.getShopName(),value.getProductName(),value.getOfferValue());

                   String macId=value.getBeaconId();
                   if(beaconList.isEmpty()||!beaconList.contains(macId))
                          beaconList.add(macId);

                    if(macIdOffersMap==null||macIdOffersMap.isEmpty()){
                        macIdOffersMap.put(macId,new ArrayList<OffersBean>(Arrays.asList(offersBean)));
                    }
                    else if(macIdOffersMap.get(macId)==null){
                        macIdOffersMap.put(macId,new ArrayList<OffersBean>(Arrays.asList(offersBean)));
                    }
                    else{
                        List<OffersBean> offersBeans=macIdOffersMap.get(macId);
                        offersBeans.add(offersBean);
                        macIdOffersMap.put(macId,offersBeans);
                    }

                    offersBeanList.add(offersBean);
                    myList.add(value);
                }

                GlobalBeaconOffersData.setBeaconList(beaconList);
                GlobalBeaconOffersData.setMacIdOffersMap(macIdOffersMap);

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SplashScreen.this, "Failed to load user data", Toast.LENGTH_LONG).show();
            }
        });

    }

    //Function to check internet connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;

    }
}
