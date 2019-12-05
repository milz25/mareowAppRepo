package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.ViewHolders.RatingDialogViewHolder;

import java.util.List;

public class RatingDialogAdapter extends RecyclerView.Adapter<RatingDialogViewHolder> {

    Context context;
    List<String> question;
    List<String> ratingValue;
    public RatingDialogAdapter(Context context, List<String> questions,List<String> ratingvalues) {
        this.context=context;
        this.question=questions;
        this.ratingValue=ratingvalues;

    }

    @NonNull
    @Override
    public RatingDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.rating_adapter_dailog,parent,false);
        return new RatingDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingDialogViewHolder holder, int position) {

        holder.mQuestion.setText(question.get(position));
        holder.mRating.setRating(Float.parseFloat(ratingValue.get(position)));
        holder.mRating.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return question.size();
    }
}
