package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class MainDashBoardViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView title;
    public AppCompatTextView count;
    public AppCompatImageView imageView;
    public MainDashBoardViewHolder(@NonNull View itemView) {
         super(itemView);
         title = (AppCompatTextView) itemView.findViewById(R.id.dashboard_adapter_machine_name);
         count = (AppCompatTextView) itemView.findViewById(R.id.dashboard_adapter_machine_Count);
         imageView=(AppCompatImageView)itemView.findViewById(R.id.dashboard_adapter_machine_image);
    }
}
