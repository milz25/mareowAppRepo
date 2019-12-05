package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class OperatorWorkOrderViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView txtWorkOrderNo;
    public AppCompatTextView txtWorkOrderDate;
    public AppCompatTextView mWorkOrderStatus;
    public AppCompatTextView txtWOrkOrderMachine;
    //public CardView mContainer;

    public OperatorWorkOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtWorkOrderNo=(AppCompatTextView)itemView.findViewById(R.id.OAA_wo_no);
        txtWorkOrderDate=(AppCompatTextView)itemView.findViewById(R.id.OAA_wo_dates);
        mWorkOrderStatus=(AppCompatTextView)itemView.findViewById(R.id.OAA_wo_status);
        txtWOrkOrderMachine=(AppCompatTextView)itemView.findViewById(R.id.OAA_wo_machine);
        //mContainer=(CardView)itemView.findViewById(R.id.work_order_details_container);


    }
}
