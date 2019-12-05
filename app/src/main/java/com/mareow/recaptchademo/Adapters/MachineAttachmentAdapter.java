package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.MachineAttachment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.ViewHolders.MachineAttachmentViewHolder;

import java.util.List;

public class MachineAttachmentAdapter extends RecyclerView.Adapter<MachineAttachmentViewHolder> {
    Context context;
    List<MachineAttachment> machineAttachments;
    public MachineAttachmentAdapter(Context context, List<MachineAttachment> machineAttachments) {
        this.context=context;
        this.machineAttachments=machineAttachments;
    }

    @NonNull
    @Override
    public MachineAttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.machine_attachment_adapter,parent,false);
        return new MachineAttachmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineAttachmentViewHolder holder, int position) {

        if (machineAttachments.get(position).isDefaultCheck()){
            holder.itemCheckBox.setText(machineAttachments.get(position).getAttachmentName()+" (Default)");
        }else {
            holder.itemCheckBox.setText(machineAttachments.get(position).getAttachmentName()+" (Extra)");
        }

        holder.itemCheckBox.setChecked(true);
        holder.itemCheckBox.setKeyListener(null);
    }

    @Override
    public int getItemCount() {
        return machineAttachments.size();
    }
}
