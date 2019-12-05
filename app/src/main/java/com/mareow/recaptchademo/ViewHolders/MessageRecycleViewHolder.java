package com.mareow.recaptchademo.ViewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;

public class MessageRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public AppCompatImageView profileImageSender;
    public AppCompatTextView txtSenderName;
    public AppCompatTextView txtLastMessage;
    public AppCompatTextView txtDate;
    RecyclerViewClickListener mListener;

    public MessageRecycleViewHolder(@NonNull View itemView, RecyclerViewClickListener mListener) {
        super(itemView);
        this.mListener=mListener;
        profileImageSender=(AppCompatImageView)itemView.findViewById(R.id.message_sender_image);
        txtSenderName=(AppCompatTextView)itemView.findViewById(R.id.message_sendername);
        txtLastMessage=(AppCompatTextView)itemView.findViewById(R.id.message_lastmessage);
        txtDate=(AppCompatTextView)itemView.findViewById(R.id.message_send_date);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v,getAdapterPosition());
    }
}
