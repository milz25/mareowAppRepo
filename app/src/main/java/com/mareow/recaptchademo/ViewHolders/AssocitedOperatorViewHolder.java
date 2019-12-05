package com.mareow.recaptchademo.ViewHolders;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

public class AssocitedOperatorViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView mOperatorIcon;
    public AppCompatImageView mOperatorIconmask;
    public AppCompatImageView mOperatorVerfied;

    public AppCompatTextView  mOperatorName;
    public LinearLayout mOperatorWorkOrder;
    public LinearLayout mOperatorDeassociation;

    public AppCompatTextView mOperatorAssociationText;

    public TextInputEditText editAboutMySelf;
    public TextInputEditText editAbilitytoRun;
    public TextInputEditText editBasicRate;

    public SwitchCompat mSwitchAccomodation;
    public SwitchCompat mSwitchTransportation;
    public SwitchCompat mSwitchFood;
    public CardView cardView;

    public AssocitedOperatorViewHolder(@NonNull View itemView) {
        super(itemView);

        mOperatorIcon=(AppCompatImageView)itemView.findViewById(R.id.owner_APA_icon);
        mOperatorIconmask=(AppCompatImageView)itemView.findViewById(R.id.owner_APA_icon_mask);
        mOperatorVerfied=(AppCompatImageView)itemView.findViewById(R.id.owner_APA_user_verified);


        mOperatorName=(AppCompatTextView)itemView.findViewById(R.id.owner_APA_operator_name);

        mOperatorWorkOrder=(LinearLayout) itemView.findViewById(R.id.owner_APA_workOrder);
        mOperatorDeassociation=(LinearLayout) itemView.findViewById(R.id.owner_APA_deassociation);

        mOperatorAssociationText=(AppCompatTextView)itemView.findViewById(R.id.owner_APA_operator_association);

        editAboutMySelf=(TextInputEditText)itemView.findViewById(R.id.owner_APA_about_myself);
        editAbilitytoRun=(TextInputEditText)itemView.findViewById(R.id.owner_APA_ability_to_run);
        editBasicRate=(TextInputEditText)itemView.findViewById(R.id.owner_APA_rate);


        mSwitchAccomodation=(SwitchCompat)itemView.findViewById(R.id.owner_APA_accomodation);
        mSwitchTransportation=(SwitchCompat)itemView.findViewById(R.id.owner_APA_trnsportation);
        mSwitchFood=(SwitchCompat)itemView.findViewById(R.id.owner_APA_food);

        cardView=(CardView)itemView.findViewById(R.id.owner_APA_cardView);


    }
}
