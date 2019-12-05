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
import com.mareow.recaptchademo.ViewHolders.ConversationRecycleViewHolder;

import java.util.List;

public class ConversationRecycleAdapter extends RecyclerView.Adapter<ConversationRecycleViewHolder> {

    Context context;
    List<Message> messageHistory;
    RequestOptions options;

    public ConversationRecycleAdapter(Context context, List<Message> messageHistory) {
        this.context=context;
        this.messageHistory=messageHistory;

        options= new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .transform(new CircleTransform(context));
    }

    @NonNull
    @Override
    public ConversationRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.conversation_recycle_adapter,parent,false);
        return new ConversationRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationRecycleViewHolder holder, int position) {

        Message message=messageHistory.get(position);

        if (message.getToMsg()==null){

            holder.toMessageLinearLayout.setVisibility(View.GONE);
            holder.toTimeLinearLayout.setVisibility(View.GONE);

        }else {

            Glide.with(context).load("http://18.204.165.238:8080/mareow-api/"+message.getFromImagePath())
                    .apply(options)
                    .into(holder.profileFromImage);

            holder.toMessageTextView.setText(message.getToMsg());
            holder.toTime.setText(message.getToMsgDateStr());

        }



        if (message.getFromMsg()==null){

            holder.fromMessageLinearLayout.setVisibility(View.GONE);
            holder.fromTimeLinearLayout.setVisibility(View.GONE);

        }else {
            Glide.with(context).load("http://18.204.165.238:8080/mareow-api/"+message.getFromImagePath())
                    .apply(options)
                    .into(holder.profileToImage);

            holder.fromMessageTextView.setText(message.getFromMsg());
            holder.fromTime.setText(message.getFromMsgDateStr());

        }


    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return messageHistory.size();
    }


    public void addMessageHistory(Message message){
        int index=messageHistory.size();
        messageHistory.add(index, message);
        notifyItemInserted(messageHistory.size()-1);
        //notifyDataSetChanged();
    }
}
