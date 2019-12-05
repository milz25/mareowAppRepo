package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class AssociaedMachineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatCheckBox checkBoxMachineName;
    RecyclerViewClickListener listener;
    public AssociaedMachineViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        checkBoxMachineName=(AppCompatCheckBox)itemView.findViewById(R.id.associated_machine_adapter_checkbox);
        this.listener=listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       listener.onClick(v,getAdapterPosition());
    }
}
