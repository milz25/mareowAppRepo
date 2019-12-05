package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.offerRecycleViewHolder;

import java.util.ArrayList;
import java.util.List;

public class offerRecycleAdapter extends RecyclerView.Adapter<offerRecycleViewHolder> {

    Context context;
    List<OfferWorkOrder> offerWorkOrderList;
    RecyclerViewClickListener mListener;
    public offerRecycleAdapter(Context context, List<OfferWorkOrder> offeredWorkOrderList, RecyclerViewClickListener listener) {
        this.context=context;
        this.offerWorkOrderList=offeredWorkOrderList;
        mListener=listener;
    }

    @NonNull
    @Override
    public offerRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.offer_work_order_adapter,parent,false);
        return new offerRecycleViewHolder(itemView,mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull offerRecycleViewHolder holder, int position) {
        OfferWorkOrder offerWorkOrder=offerWorkOrderList.get(position);

        holder.workOfferNumber.setText("Work Order # "+offerWorkOrder.getWorkorderDTO().getWorkOrderNo());
        holder.offerDates.setText(Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkStartDate())+" - "+Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkEndDate()));
        holder.offerStatus.setText(offerWorkOrder.getWorkorderDTO().getWorkOrderStatusMeaning());
        holder.offerStatus.setTextColor(context.getResources().getColor(R.color.Blue_Text));

        if (Constants.USER_ROLE.equals("Owner")){

            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN") || offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
                holder.imageOffer.setBackground(context.getResources().getDrawable(R.drawable.selected_plan));
            }else if(offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("REJECT")){
                holder.imageOffer.setBackground(context.getResources().getDrawable(R.drawable.unselected_plan));
            }
        }

        if (Constants.USER_ROLE.equals("Renter")){

            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_OWN") ||offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
                holder.imageOffer.setBackground(context.getResources().getDrawable(R.drawable.selected_plan));
            }else if(offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("REJECT")){
                holder.imageOffer.setBackground(context.getResources().getDrawable(R.drawable.unselected_plan));
            }

        }

        holder.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return offerWorkOrderList.size();
    }


    public void updateList(List<OfferWorkOrder> newList){

        offerWorkOrderList=new ArrayList<>();
        offerWorkOrderList.addAll(newList);
        notifyDataSetChanged();

    }
}
