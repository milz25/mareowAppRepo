package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class PaymentMainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatTextView mWorkNo;
    public RecyclerView mRecycleView;

    public AppCompatTextView mInvoiceNo;
    public AppCompatTextView mDate;
    public AppCompatTextView mStatus;
    public RecyclerViewClickListener listener;
    public PaymentMainViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);

        mWorkNo=(AppCompatTextView) itemView.findViewById(R.id.payment_main_adapter_workorder_no);
        //mRecycleView=(RecyclerView)itemView.findViewById(R.id.payment_main_adapter_recycle);

        mInvoiceNo=(AppCompatTextView) itemView.findViewById(R.id.payment_main__adapter_invoice_no);
        mDate=(AppCompatTextView) itemView.findViewById(R.id.payment_main_adapter_date);
        mStatus=(AppCompatTextView) itemView.findViewById(R.id.payment_main_adapter_status);
        itemView.setOnClickListener(this);
        this.listener=listener;


    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition());
    }
}
