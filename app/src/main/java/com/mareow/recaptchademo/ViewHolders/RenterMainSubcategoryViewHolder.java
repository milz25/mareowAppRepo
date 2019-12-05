package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class RenterMainSubcategoryViewHolder extends RecyclerView.ViewHolder {

    public RecyclerView mSubRecyleView;
    public AppCompatButton btnShowAll;
    public RenterMainSubcategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        mSubRecyleView=(RecyclerView)itemView.findViewById(R.id.renter_subcategory_adapter_recycle);
        btnShowAll=(AppCompatButton)itemView.findViewById(R.id.renter_subcategory_adapter_button);
    }
}
