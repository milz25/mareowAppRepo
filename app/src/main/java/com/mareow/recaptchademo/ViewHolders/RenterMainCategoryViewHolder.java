package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class RenterMainCategoryViewHolder extends RecyclerView.ViewHolder{

    public AppCompatImageView machineImage;
    public AppCompatTextView mMachineText;
    RecyclerViewClickListener listener;
    public CardView cardView;

    int selectedPostion=-1;
    public RenterMainCategoryViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        machineImage=(AppCompatImageView)itemView.findViewById(R.id.renter_category_machine_image);
        mMachineText=(AppCompatTextView)itemView.findViewById(R.id.renter_category_machine_text);
        cardView=(CardView)itemView.findViewById(R.id.category_card_view);
        this.listener=listener;
     //   itemView.setOnClickListener(this);
    }

   /* @Override
    public void onClick(View v) {
      listener.onClick(v,getAdapterPosition());
    }*/
}
