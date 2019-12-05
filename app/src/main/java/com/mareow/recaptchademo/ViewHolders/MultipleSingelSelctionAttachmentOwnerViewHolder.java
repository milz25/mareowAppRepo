package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class MultipleSingelSelctionAttachmentOwnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatRadioButton radioButtonItem;
    RecyclerViewClickListener mListener;

    public MultipleSingelSelctionAttachmentOwnerViewHolder(@NonNull View itemView, RecyclerViewClickListener mListener) {
        super(itemView);

        radioButtonItem=(AppCompatRadioButton)itemView.findViewById(R.id.singel_selection_dialog_radio);
        this.mListener = mListener;
        radioButtonItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }
}
