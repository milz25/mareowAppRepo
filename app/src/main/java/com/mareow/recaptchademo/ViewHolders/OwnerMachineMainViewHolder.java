package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.mareow.recaptchademo.R;

public class OwnerMachineMainViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView txtMachineTitle;

    public AppCompatTextView txtMachineCateSubCate;
    public AppCompatTextView txtMachineSubcategory;
    public AppCompatTextView txtModel;
    public AppCompatTextView txtManu_Year;
    public AppCompatTextView txtCurrentLocation;
    public AppCompatTextView txtDescription;
    public AppCompatTextView txtManufacturer;

    public AppCompatTextView txtStatus;

    public AppCompatImageView deleteMachine;
    public AppCompatImageView editMachine;

    public CardView cardView;
    public CardView cardViewTwo;

    public OwnerMachineMainViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMachineTitle=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_machinetitle);

        txtMachineCateSubCate=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_cate_subcate);
        txtMachineSubcategory=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_subcate);
        txtModel=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_model);
        txtManu_Year=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_manuyear);
        txtCurrentLocation=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_current_location);
        txtDescription=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_description);
        txtManufacturer=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_manufacturer);

        txtStatus=(AppCompatTextView)itemView.findViewById(R.id.owner_machine_adapter_cate_availability);

        deleteMachine=(AppCompatImageView)itemView.findViewById(R.id.owner_machine_adapter_delete);
        editMachine=(AppCompatImageView)itemView.findViewById(R.id.owner_machine_adapter_edit);

        cardView=(CardView)itemView.findViewById(R.id.owner_machine_adapter_card);
        cardViewTwo=(CardView)itemView.findViewById(R.id.owner_machine_adapter_card_two);


    }
}
