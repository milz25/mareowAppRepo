package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

public class InvoiceMainInnerViewHolder extends RecyclerView.ViewHolder {

    public TextInputEditText editInvoiceNo;
    public TextInputEditText editInvoiceDate;
    public AppCompatImageView btnEdit;
    public AppCompatImageView btnDelete;
    public AppCompatImageView btnMore;
    public InvoiceMainInnerViewHolder(@NonNull View itemView) {
        super(itemView);

        editInvoiceNo=(TextInputEditText)itemView.findViewById(R.id.invoice_inner_adapter_invoice_no);
        editInvoiceDate=(TextInputEditText)itemView.findViewById(R.id.invoice_inner_adapter_invoice_date);
        btnEdit=(AppCompatImageView) itemView.findViewById(R.id.invoice_inner_adapter_invoice_edit);
        btnMore=(AppCompatImageView) itemView.findViewById(R.id.invoice_inner_adapter_invoice_more);
        btnDelete=(AppCompatImageView) itemView.findViewById(R.id.invoice_inner_adapter_invoice_delete);

    }
}
