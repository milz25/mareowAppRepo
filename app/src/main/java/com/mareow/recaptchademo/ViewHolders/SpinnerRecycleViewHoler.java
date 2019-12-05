package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class SpinnerRecycleViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatTextView txtItem;
    private RecyclerViewClickListener mListener;
    public SpinnerRecycleViewHoler(@NonNull View itemView,RecyclerViewClickListener listener) {
        super(itemView);
        mListener = listener;
        txtItem=(AppCompatTextView)itemView.findViewById(R.id.recyle_dialog_item);
        txtItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }
}
