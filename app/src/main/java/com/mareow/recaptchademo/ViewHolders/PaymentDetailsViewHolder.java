package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

public class PaymentDetailsViewHolder extends RecyclerView.ViewHolder {
    public TextInputEditText editInvoiceNo;
    public TextInputEditText editInvoiceCategory;
    public TextInputEditText editInvoicePayableAmount;
    public TextInputEditText editInvoiceDueAmount;
    public AppCompatImageView btnInvoiceMore;
    public AppCompatImageView btnBankDetail;
    public AppCompatImageView btnInvoiceDownload;

    public RecyclerView paymentDetailsRecycle;

    public PaymentDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        editInvoiceNo=(TextInputEditText) itemView.findViewById(R.id.payment_details_adapter_invoice_no);
        editInvoiceCategory=(TextInputEditText) itemView.findViewById(R.id.payment_details_adapter_invoice_category);
        editInvoicePayableAmount=(TextInputEditText) itemView.findViewById(R.id.payment_details_adapter_invoice_payable_amount);
        editInvoiceDueAmount=(TextInputEditText) itemView.findViewById(R.id.payment_details_adapter_invoice_due_amount);
        btnInvoiceMore=(AppCompatImageView) itemView.findViewById(R.id.payment_details_adapter_invoice_more);
        btnBankDetail=(AppCompatImageView)itemView.findViewById(R.id.payment_details_adapter_invoice_bank_details);

        btnInvoiceDownload=(AppCompatImageView)itemView.findViewById(R.id.payment_details_adapter_invoice_download);
        paymentDetailsRecycle=(RecyclerView) itemView.findViewById(R.id.payment_details_adapter_recycle);

    }
}
