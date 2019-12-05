package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

public class SuperDailyLogsViewHolder extends RecyclerView.ViewHolder {

    public TextInputEditText txtStartTime;
    public TextInputEditText txtEndTime;
    public TextInputEditText txtTotalTime;

    public TextInputEditText txtStartKMS;
    public TextInputEditText txtEndKMS;
    public TextInputEditText txtTotalKMS;

    public TextInputEditText txtFuel;
    public TextInputEditText txtDate;
    public TextInputEditText txtRemarks;

    public AppCompatImageView btnEdit;
    public CardView mContainer;

    public SuperDailyLogsViewHolder(@NonNull View itemView) {
        super(itemView);

        txtStartTime=(TextInputEditText) itemView.findViewById(R.id.daily_logs_time_start);
        txtEndTime=(TextInputEditText) itemView.findViewById(R.id.daily_logs_time_end);
        txtTotalTime=(TextInputEditText) itemView.findViewById(R.id.daily_logs_time_total);

        txtStartKMS=(TextInputEditText) itemView.findViewById(R.id.daily_logs_kms_start);
        txtEndKMS=(TextInputEditText)itemView.findViewById(R.id.daily_logs_kms_end);
        txtTotalKMS=(TextInputEditText)itemView.findViewById(R.id.daily_logs_kms_total);


        txtRemarks=(TextInputEditText)itemView.findViewById(R.id.daily_logs_remarks);
        txtDate=(TextInputEditText)itemView.findViewById(R.id.daily_logs_date);
        txtFuel=(TextInputEditText)itemView.findViewById(R.id.daily_logs_fuel);
        btnEdit=(AppCompatImageView)itemView.findViewById(R.id.daily_logs_edit);
        mContainer=(CardView)itemView.findViewById(R.id.daily_logs_Container);

    }
}
