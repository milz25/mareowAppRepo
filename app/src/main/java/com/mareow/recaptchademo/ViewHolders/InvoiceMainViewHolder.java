package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.google.android.material.textfield.TextInputEditText;

public class InvoiceMainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextInputEditText editWorkNo;
    public TextInputEditText editWorkEstimatedAmount;
    public TextInputEditText editWorkStartDate;
    public TextInputEditText editWorkEndDate;

    public AppCompatImageView btnStatus;
    public AppCompatImageView btnWorkDetails;

    public RecyclerView mInvoiceRecyclerView;

    RecyclerViewClickListener listener;
    public InvoiceMainViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);

        editWorkNo=(TextInputEditText)itemView.findViewById(R.id.invoice_main_adapter_workorder_no);
        editWorkEstimatedAmount=(TextInputEditText)itemView.findViewById(R.id.invoice_main_adapter_workorder_estimated_amount);
        editWorkStartDate=(TextInputEditText)itemView.findViewById(R.id.invoice_main_adapter_workorder_startdate);
        editWorkEndDate=(TextInputEditText)itemView.findViewById(R.id.invoice_main_adapter_workorder_enddate);

        btnStatus=(AppCompatImageView)itemView.findViewById(R.id.invoice_main_adapter_workorder_status);
        btnWorkDetails=(AppCompatImageView) itemView.findViewById(R.id.invoice_main_adapter_workorder_more);

        mInvoiceRecyclerView=(RecyclerView)itemView.findViewById(R.id.invoice_main_adapter_recycle);
        this.listener=listener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
     listener.onClick(v,getAdapterPosition());
    }
}
