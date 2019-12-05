package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.google.android.material.textfield.TextInputEditText;

public class RentalPlanForRenterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatTextView planName;

    public TextInputEditText plan;
    public TextInputEditText shift;
    public TextInputEditText basicRate;
    public TextInputEditText overtime;

    public AppCompatCheckBox OperatorIncluded;

    public TextInputEditText mobilityTo;
    public TextInputEditText demobilityFrom;

    public  AppCompatCheckBox GST;
    public AppCompatCheckBox IGST;
    public AppCompatImageView planDetails;
    RecyclerViewClickListener listener;

    public CardView cardView;
    public RentalPlanForRenterViewHolder(@NonNull View itemView, RecyclerViewClickListener listener){
        super(itemView);

        planName=(AppCompatTextView)itemView.findViewById(R.id.RPD_plan_name);
        plan=(TextInputEditText)itemView.findViewById(R.id.RPD_adapter_plan);
        shift=(TextInputEditText)itemView.findViewById(R.id.RPD_adapter_shift);
        basicRate=(TextInputEditText)itemView.findViewById(R.id.RPD_adapter_basic_rate);
        overtime=(TextInputEditText)itemView.findViewById(R.id.RPD_adapter_overtime);
        OperatorIncluded=(AppCompatCheckBox)itemView.findViewById(R.id.RPD_adapter_operatorIncluded);
        mobilityTo=(TextInputEditText)itemView.findViewById(R.id.RPD_adapter_mobility);
        demobilityFrom=(TextInputEditText)itemView.findViewById(R.id.RPD_adapter_demobility);

        GST=(AppCompatCheckBox)itemView.findViewById(R.id.RPD_adapter_gst);
        IGST=(AppCompatCheckBox)itemView.findViewById(R.id.RPD_adapter_igst);
        planDetails=(AppCompatImageView) itemView.findViewById(R.id.RPD_plan_details);
        cardView=(CardView) itemView.findViewById(R.id.RPD_plan_card);

        this.listener=listener;
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition());
    }
}
