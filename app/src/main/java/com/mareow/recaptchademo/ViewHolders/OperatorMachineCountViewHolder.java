package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class OperatorMachineCountViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView txtMachineName;
    public OperatorMachineCountViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMachineName=(AppCompatTextView)itemView.findViewById(R.id.dashboard_adapter_machine_name);
    }
}
