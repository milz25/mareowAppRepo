package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

public class RenterPlanForOwnerViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView planName;

    public TextInputEditText editPlan;
    public TextInputEditText editPlanType;
    public TextInputEditText editPlanUsage;
    public TextInputEditText editBasicRate;
    public TextInputEditText editDailyHours;
    public TextInputEditText editMonthsCommited;
    public TextInputLayout committedHint;

    public AppCompatCheckBox mSwitchTax;
    public AppCompatCheckBox mSwitchOperator;
    public AppCompatCheckBox mSwitchAttachment;
    public AppCompatCheckBox mSwitchMachineAssocited;


    public AppCompatImageView btnDelete;
    public AppCompatImageView btnEdit;
    public AppCompatImageView btnMore;

    public CardView mainCard;
    public CardView headerCard;

    public RenterPlanForOwnerViewHolder(@NonNull View itemView) {
        super(itemView);

        planName=(AppCompatTextView) itemView.findViewById(R.id.ORP_adapter_plan_name);

        editPlan=(TextInputEditText) itemView.findViewById(R.id.ORP_adapter_plan);
        editPlanType=(TextInputEditText) itemView.findViewById(R.id.ORP_adapter_plan_type);
        editPlanUsage=(TextInputEditText) itemView.findViewById(R.id.ORP_adapter_plan_usage);
        editBasicRate=(TextInputEditText) itemView.findViewById(R.id.ORP_adapter_basic_rate);
        editDailyHours=(TextInputEditText) itemView.findViewById(R.id.ORP_adapter_daily_hours);
        editMonthsCommited=(TextInputEditText) itemView.findViewById(R.id.ORP_adapter_commited_month);
        committedHint=(TextInputLayout)itemView.findViewById(R.id.ORP_adapter_commited_hint);

        mSwitchTax=(AppCompatCheckBox) itemView.findViewById(R.id.ORP_adapter_tax_applicable);
        mSwitchOperator=(AppCompatCheckBox) itemView.findViewById(R.id.ORP_adapter_operator);
        mSwitchAttachment=(AppCompatCheckBox) itemView.findViewById(R.id.ORP_adapter_attachment);
        mSwitchMachineAssocited=(AppCompatCheckBox) itemView.findViewById(R.id.ORP_adapter_machine_associated);


        btnDelete=(AppCompatImageView) itemView.findViewById(R.id.ORP_adapter_delete);
        btnEdit=(AppCompatImageView) itemView.findViewById(R.id.ORP_adapter_edit);
        btnMore=(AppCompatImageView) itemView.findViewById(R.id.ORP_adapter_more);

        mainCard=(CardView)itemView.findViewById(R.id.ORP_adapter_card);
        headerCard=(CardView)itemView.findViewById(R.id.ORP_adapter_card_two);

    }
}
