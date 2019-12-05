package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.OperatorWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.OperatorWorkOrderViewHolder;

import java.util.List;

public class OperatorWorkOrderAdapter extends RecyclerView.Adapter<OperatorWorkOrderViewHolder> {

    Context context;
    List<OperatorWorkOrder> workOrderResponseList;
    public OperatorWorkOrderAdapter(Context context, List<OperatorWorkOrder> workOrderResponseList) {

        this.context=context;
        this.workOrderResponseList=workOrderResponseList;
    }

    @NonNull
    @Override
    public OperatorWorkOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.operator_associated_adapter_layout,parent,false);
        return new OperatorWorkOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OperatorWorkOrderViewHolder holder, int position) {

        //holder.mContainer.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        holder.txtWorkOrderNo.setText(workOrderResponseList.get(position).getWorkOrderNo());
        holder.txtWorkOrderDate.setText(Util.convertYYYYddMMtoDDmmYYYY(workOrderResponseList.get(position).getWorkStartDate())+" - "+Util.convertYYYYddMMtoDDmmYYYY(workOrderResponseList.get(position).getWorkEndDate()));
        holder.txtWOrkOrderMachine.setText(workOrderResponseList.get(position).getMachine().getModelNo());
        holder.mWorkOrderStatus.setText(workOrderResponseList.get(position).getWorkOrderStatus());
        if (workOrderResponseList.get(position).getWorkOrderStatus().equals("WO: Closed")){
            holder.mWorkOrderStatus.setTextColor(context.getResources().getColor(R.color.theme_orange));
        }else {
            holder.mWorkOrderStatus.setTextColor(context.getResources().getColor(R.color.text_blue));
        }
    }

    @Override
    public int getItemCount() {
        return workOrderResponseList.size();
    }
}
