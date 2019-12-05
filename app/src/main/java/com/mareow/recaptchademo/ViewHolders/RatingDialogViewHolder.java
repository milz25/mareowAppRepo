package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingDialogViewHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView mQuestion;
    public MaterialRatingBar mRating;
    public RatingDialogViewHolder(@NonNull View itemView) {
        super(itemView);
        mQuestion=(AppCompatTextView)itemView.findViewById(R.id.rating_dialog_adapter_question);
        mRating=(MaterialRatingBar)itemView.findViewById(R.id.rating_dialog_adapter_rating);
    }
}
