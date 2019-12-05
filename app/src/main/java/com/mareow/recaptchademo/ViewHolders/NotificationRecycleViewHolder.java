package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class NotificationRecycleViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView txtMessage;
    public AppCompatTextView txtTime;

    public NotificationRecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMessage=(AppCompatTextView)itemView.findViewById(R.id.noti_adapter_message);
        txtTime=(AppCompatTextView)itemView.findViewById(R.id.noti_adapter_time);

    }
}
