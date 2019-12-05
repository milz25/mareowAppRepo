package com.mareow.recaptchademo.ViewHolders;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

public class InvoiceWithinPaymentViewHolder extends RecyclerView.ViewHolder {

    public TextInputEditText editPaymentNo;
    public TextInputEditText editPaymentDate;
    public TextInputEditText editPaidAmount;

    public AppCompatImageView btnReciept;
    public AppCompatImageView btnEditPayment;
    public AppCompatImageView btnMore;
    public AppCompatImageView btnAcknowlegement;
    public LinearLayout acknowSection;

    public InvoiceWithinPaymentViewHolder(@NonNull View itemView) {
        super(itemView);

        editPaymentNo=(TextInputEditText)itemView.findViewById(R.id.invoice_within_payment_payment_no);
        editPaymentDate=(TextInputEditText)itemView.findViewById(R.id.invoice_within_payment_paymentDate);
        editPaidAmount=(TextInputEditText)itemView.findViewById(R.id.invoice_within_payment_paidAmount);

        btnReciept=(AppCompatImageView)itemView.findViewById(R.id.invoice_within_payment_reciept);
        btnEditPayment=(AppCompatImageView)itemView.findViewById(R.id.invoice_within_payment_edit);
        btnMore=(AppCompatImageView)itemView.findViewById(R.id.invoice_within_payment_more);
        btnAcknowlegement=(AppCompatImageView)itemView.findViewById(R.id.invoice_within_payment_acknowlegment);

        acknowSection=(LinearLayout)itemView.findViewById(R.id.linear_acknow);


    }
}
