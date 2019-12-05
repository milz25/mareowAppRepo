package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class SuperWorkOrderMainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatTextView txtWorkOrderId;
    public AppCompatTextView txtWorkOrderDate;
    public AppCompatTextView txtWorkOrderStatus;
    public CardView mContainer;

    RecyclerViewClickListener mListener;
    public SuperWorkOrderMainViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        this.mListener = listener;
        txtWorkOrderId=(AppCompatTextView)itemView.findViewById(R.id.super_work_order_adapter_workorder_no);
        txtWorkOrderDate=(AppCompatTextView)itemView.findViewById(R.id.super_work_order_adapter_date);
        txtWorkOrderStatus=(AppCompatTextView)itemView.findViewById(R.id.super_work_order_adapter_status);
        mContainer=(CardView)itemView.findViewById(R.id.work_order_details_container);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }
}
