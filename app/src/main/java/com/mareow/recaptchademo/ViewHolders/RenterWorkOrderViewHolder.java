package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class RenterWorkOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatTextView mWorkNo;
    public AppCompatTextView mWorkDates;
    public AppCompatTextView mWorkStatus;

    public AppCompatImageView mWorkAgreement;
    public AppCompatImageView mWorkExtend;
    public AppCompatImageView mWorkCancellation;

    RecyclerViewClickListener listener;
    public RenterWorkOrderViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        mWorkNo=(AppCompatTextView)itemView.findViewById(R.id.renter_work_adapter_workorder_no);
        mWorkDates=(AppCompatTextView)itemView.findViewById(R.id.renter_work_adapter_workorder_date);
        mWorkStatus=(AppCompatTextView)itemView.findViewById(R.id.renter_work_adapter_workorder_status);

       /* mWorkAgreement=(AppCompatImageView) itemView.findViewById(R.id.renter_work_adapter_agreement);
        mWorkExtend=(AppCompatImageView) itemView.findViewById(R.id.renter_work_adapter_extend);
        mWorkCancellation=(AppCompatImageView) itemView.findViewById(R.id.renter_work_adapter_cancellation);*/
        this.listener=listener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition());
    }
}
