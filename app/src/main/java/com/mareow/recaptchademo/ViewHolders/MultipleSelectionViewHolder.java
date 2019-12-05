package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class MultipleSelectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatCheckBox checkBoxItem;
    RecyclerViewClickListener mListener;
    public MultipleSelectionViewHolder(@NonNull View itemView, RecyclerViewClickListener mListener) {
        super(itemView);
        checkBoxItem=(AppCompatCheckBox)itemView.findViewById(R.id.mutilple_selection_dialog_checkbox);
        this.mListener = mListener;
        checkBoxItem.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }
}
