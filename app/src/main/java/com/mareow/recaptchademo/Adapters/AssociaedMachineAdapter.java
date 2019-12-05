package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.AssociaedMachineViewHolder;

import java.util.List;

public class AssociaedMachineAdapter extends RecyclerView.Adapter<AssociaedMachineViewHolder> {

    Context context;
    List<RenterMachine> machineListForOwner;
    RecyclerViewClickListener listener;
    public AssociaedMachineAdapter(Context context, List<RenterMachine> machineListForOwner, RecyclerViewClickListener listener) {
        this.context=context;
        this.machineListForOwner=machineListForOwner;
        this.listener=listener;
    }

    @NonNull
    @Override
    public AssociaedMachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.associated_machine_adapter,parent,false);
        return new AssociaedMachineViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssociaedMachineViewHolder holder, int position) {
        holder.checkBoxMachineName.setText(machineListForOwner.get(position).getMachineName());

    }

    @Override
    public int getItemCount() {
        return machineListForOwner.size();
    }
}
