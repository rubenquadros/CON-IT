package com.example.comviva.con_it.java.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comviva.con_it.R;
import com.example.comviva.con_it.java.bean.GlobalBeaconOffersData;
import com.example.comviva.con_it.java.bean.OffersBean;

import java.util.List;
import java.util.Map;

/**
 * Created by comviva on 11/23/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<OffersBean> offersList;
    private Context context;
    Map<String,List<OffersBean>> macIdOffersMap= GlobalBeaconOffersData.getMacIdOffersMap();

    public RecyclerAdapter(List<OffersBean> offersList, Context context) {
        this.offersList = offersList;
        this.context = context;
    }
    public void UpdateData(List<OffersBean> offersList){
        this.offersList = offersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*holder.myTextView.setText("Device Name     " + deviceList.get(position).getDevicename() + "\n" +
                "Device MacId   " + deviceList.get(position).getDeviceId() + "\n" + "Device SignalStrength     " +
                deviceList.get(position).getDeviceStrength());*/

        OffersBean offersBean =offersList.get(position);
        StringBuilder sb =new StringBuilder();

            sb.append("Shop Name    ").append(offersBean.getShopName()).append("\n");
            sb.append("Product Name    ").append(offersBean.getProductName()).append("\n");
            sb.append("Offer Value    ").append(offersBean.getOfferValue()).append(" % Off");

            holder.myTextView.setText(sb);
    }

    @Override
    public int getItemCount() {
        if(offersList==null||offersList.isEmpty()){
            return 0;
        }
        else{
            return offersList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.myTextView);
        }
    }
}
