package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class DailyLogsDateViewHolder extends RecyclerView.ViewHolder{

    public AppCompatButton txtDates;
   // public AppCompatButton cardView;
    public DailyLogsDateViewHolder(@NonNull View itemView){
        super(itemView);
        txtDates=(AppCompatButton) itemView.findViewById(R.id.daily_logs_date_dates);
        //cardView=(MaterialCardView)itemView.findViewById(R.id.daili_log_date_adapter_card);
    }

}
