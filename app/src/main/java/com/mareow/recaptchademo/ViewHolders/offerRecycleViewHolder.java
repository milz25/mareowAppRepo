package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class offerRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatTextView workOfferNumber;
    public AppCompatTextView imageOffer;
    public AppCompatTextView offerDates;
    public AppCompatTextView offerStatus;
    public RecyclerViewClickListener mListener;

    public offerRecycleViewHolder(View itemView, RecyclerViewClickListener mListener)
    {
        super(itemView);

        workOfferNumber=(AppCompatTextView)itemView.findViewById(R.id.renter_offer_adapter_workorder_no);
        offerDates=(AppCompatTextView)itemView.findViewById(R.id.renter_offer_adapter_workorder_date);
        offerStatus=(AppCompatTextView)itemView.findViewById(R.id.renter_offer_adapter_workorder_status);

        imageOffer=(AppCompatTextView) itemView.findViewById(R.id.renter_offer_adapter_offer_accept);
        this.mListener=mListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }
}
