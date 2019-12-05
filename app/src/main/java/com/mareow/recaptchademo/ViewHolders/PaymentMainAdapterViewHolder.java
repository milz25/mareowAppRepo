package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class PaymentMainAdapterViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView txtInvoiceNo;
    public AppCompatTextView txtInvoiceDate;
    public AppCompatTextView txtInvoiceStatus;

    public PaymentMainAdapterViewHolder(@NonNull View itemView) {
        super(itemView);

        txtInvoiceNo=(AppCompatTextView)itemView.findViewById(R.id.payment_main__adapter_invoice_no);
        txtInvoiceDate=(AppCompatTextView)itemView.findViewById(R.id.payment_main_adapter_date);
        txtInvoiceStatus=(AppCompatTextView)itemView.findViewById(R.id.payment_main_adapter_status);
    }
}
