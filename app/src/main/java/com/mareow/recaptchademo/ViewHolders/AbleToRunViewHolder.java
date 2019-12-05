package com.mareow.recaptchademo.ViewHolders;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

public class AbleToRunViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView machineImage;
    public TextInputEditText txtCategory;
    public TextInputEditText txtSubCategory;
    public TextInputEditText txtManufacture;
    public TextInputEditText txtModelNo;
    public TextInputEditText txtUrl;
   // public AppCompatTextView btnDelete;
    public AppCompatImageView btnDelete;
    public LinearLayout mContainer;
    public CardView cardView;


    public AbleToRunViewHolder(@NonNull View itemView) {
        super(itemView);

        mContainer=(LinearLayout)itemView.findViewById(R.id.able_to_run_adapter_Container);
        machineImage=(AppCompatImageView) itemView.findViewById(R.id.able_to_run_adapter_machineImage);
        txtCategory=(TextInputEditText)itemView.findViewById(R.id.able_to_run_adapter_category);
        txtCategory.setKeyListener(null);
        txtSubCategory=(TextInputEditText)itemView.findViewById(R.id.able_to_run_adapter_subcategory);
        txtSubCategory.setKeyListener(null);
        txtManufacture=(TextInputEditText)itemView.findViewById(R.id.able_to_run_adapter_Manufacture);
        txtManufacture.setKeyListener(null);
        txtModelNo=(TextInputEditText)itemView.findViewById(R.id.able_to_run_adapter_modelno);
        txtModelNo.setKeyListener(null);
        //txtName=(AppCompatTextView)itemView.findViewById(R.id.able_to_run_adapter_name);
        txtUrl=(TextInputEditText)itemView.findViewById(R.id.able_to_run_adapter_url);
        txtUrl.setKeyListener(null);

        btnDelete=(AppCompatImageView) itemView.findViewById(R.id.able_to_run_adapter_action);

        cardView=(CardView) itemView.findViewById(R.id.able_to_run_adapter_card);



    }
}
