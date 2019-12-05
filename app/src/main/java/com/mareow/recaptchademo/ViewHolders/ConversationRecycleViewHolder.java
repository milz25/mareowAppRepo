package com.mareow.recaptchademo.ViewHolders;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;

public class ConversationRecycleViewHolder extends RecyclerView.ViewHolder {

    public AppCompatImageView profileToImage;
    public AppCompatImageView profileFromImage;

    public AppCompatTextView toMessageTextView;
    public AppCompatTextView fromMessageTextView;

    public AppCompatTextView toTime;
    public AppCompatTextView fromTime;

    public LinearLayout toMessageLinearLayout;
    public LinearLayout fromMessageLinearLayout;

    public LinearLayout toTimeLinearLayout;
    public LinearLayout fromTimeLinearLayout;


    public ConversationRecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        profileToImage=(AppCompatImageView)itemView.findViewById(R.id.chat_left_msg_image_view);
        toMessageTextView=(AppCompatTextView)itemView.findViewById(R.id.chat_left_msg_message);
        toTime=(AppCompatTextView)itemView.findViewById(R.id.chat_left_msg_time);

        profileFromImage=(AppCompatImageView)itemView.findViewById(R.id.chat_right_msg_image_view);
        fromMessageTextView=(AppCompatTextView)itemView.findViewById(R.id.chat_right_msg_message);
        fromTime=(AppCompatTextView)itemView.findViewById(R.id.chat_right_msg_time);

        toMessageLinearLayout=(LinearLayout)itemView.findViewById(R.id.chat_left_msg_layout);
        toTimeLinearLayout=(LinearLayout)itemView.findViewById(R.id.chat_left_time_layout);

        fromMessageLinearLayout=(LinearLayout)itemView.findViewById(R.id.chat_right_msg_layout);
        fromTimeLinearLayout=(LinearLayout)itemView.findViewById(R.id.chat_right_time_layout);




    }
}
