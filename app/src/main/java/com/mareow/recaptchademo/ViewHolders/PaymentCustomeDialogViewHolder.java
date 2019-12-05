package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class PaymentCustomeDialogViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView txtHeading;
    public AppCompatTextView txtValue;

    public PaymentCustomeDialogViewHolder(@NonNull View itemView) {
        super(itemView);

        txtHeading=(AppCompatTextView)itemView.findViewById(R.id.payment_dailog_heading_text);
        txtValue=(AppCompatTextView)itemView.findViewById(R.id.payment_dailog_value_text);
    }
}
