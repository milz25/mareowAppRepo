package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.Notification;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.ViewHolders.NotificationRecycleViewHolder;

import java.util.List;

public class NotificationRecycleAdapter extends RecyclerView.Adapter<NotificationRecycleViewHolder> {
    Context context;
    List<Notification> notificationList;
    public NotificationRecycleAdapter(Context context, List<Notification> notificationList) {
        this.context=context;
        this.notificationList=notificationList;
    }

    @NonNull
    @Override
    public NotificationRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.notification_recycle_adapter,parent,false);
        return new NotificationRecycleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRecycleViewHolder holder, int position) {
         holder.txtMessage.setText(notificationList.get(position).getUserName()+" ("+notificationList.get(position).getRoleLogic()+")"+notificationList.get(position).getMessage());
         holder.txtTime.setText(notificationList.get(position).getTime()+" ago");
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
