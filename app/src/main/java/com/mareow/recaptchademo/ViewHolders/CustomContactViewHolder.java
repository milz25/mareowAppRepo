package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class CustomContactViewHolder extends RecyclerView.ViewHolder {

    public AppCompatCheckBox checkBox;
    public AppCompatTextView txtName;
    public AppCompatTextView txtPhoneNumber;
    public AppCompatTextView txtEmail;

    public CustomContactViewHolder(@NonNull View itemView) {
        super(itemView);
        checkBox=(AppCompatCheckBox)itemView.findViewById(R.id.contact_check);
        txtName=(AppCompatTextView)itemView.findViewById(R.id.contact_name);
        txtPhoneNumber=(AppCompatTextView)itemView.findViewById(R.id.contact_number);
        txtEmail=(AppCompatTextView)itemView.findViewById(R.id.contact_email);

    }
}
