package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class RenterRunningLogDatesViewHolder extends RecyclerView.ViewHolder {

    public AppCompatButton txtDates;
    public RenterRunningLogDatesViewHolder(@NonNull View itemView) {
        super(itemView);
        txtDates=(AppCompatButton) itemView.findViewById(R.id.running_logs_date_dates);
    }
}
