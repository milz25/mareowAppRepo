package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class MachineAttachmentViewHolder extends RecyclerView.ViewHolder {

    public AppCompatCheckBox itemCheckBox;
    public MachineAttachmentViewHolder(@NonNull View itemView) {
        super(itemView);
        itemCheckBox=(AppCompatCheckBox)itemView.findViewById(R.id.machine_attachments_adapter);
    }
}
