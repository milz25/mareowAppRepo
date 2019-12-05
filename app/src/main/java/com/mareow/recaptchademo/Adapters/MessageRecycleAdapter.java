package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.DataModels.Message;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.CircleTransform;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.MessageRecycleViewHolder;

import java.util.List;

public class MessageRecycleAdapter extends RecyclerView.Adapter<MessageRecycleViewHolder> {

    Context context;
    List<Message> messageList;
    RecyclerViewClickListener mListener;
    public MessageRecycleAdapter(Context context, List<Message> messageList, RecyclerViewClickListener listener) {
        this.context=context;
        this.messageList=messageList;
        this.mListener=listener;
    }

    @NonNull
    @Override
    public MessageRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.message_recycle_adapter,parent,false);
        return new MessageRecycleViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecycleViewHolder holder, int position){
        Message message=messageList.get(position);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .transform(new CircleTransform(context));



        Glide.with(context).load("http://18.204.165.238:8080/mareow-api/"+message.getImagePath())
                .apply(options)
                .into(holder.profileImageSender);

       /* Glide.with(context)
                .load(message.getImagePath())
                 .override(55, 55)
                .into(holder.profileImageSender);*/

        holder.txtSenderName.setText(message.getName()+" ("+message.getRole()+")");
        holder.txtLastMessage.setText(message.getMessage());
        holder.txtDate.setText(Util.convertYYYYddMMtoDDmmYYYY(String.valueOf(message.getCreatedDate())));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}
