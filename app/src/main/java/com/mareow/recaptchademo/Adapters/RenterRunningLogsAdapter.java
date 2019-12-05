package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.ActivityLogDTO;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.RenterRunningLogsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RenterRunningLogsAdapter extends RecyclerView.Adapter<RenterRunningLogsViewHolder> {


    Context context;
    List<ActivityLogDTO> runningLogsList;

    public RenterRunningLogsAdapter(Context context, List<ActivityLogDTO> runningLogsList) {
        this.context=context;
        this.runningLogsList=runningLogsList;
    }
    @NonNull
    @Override
    public RenterRunningLogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.renter_running_log_adapter,parent,false);
        return new RenterRunningLogsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RenterRunningLogsViewHolder holder, int position) {

        ActivityLogDTO activityLogDTO=runningLogsList.get(position);
       // holder.mContainer.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        holder.txtDate.setText(Util.convertYYYYddMMtoDDmmYYYY(activityLogDTO.getLogDate()));
        holder.txtDate.setKeyListener(null);

        if (activityLogDTO.getPartyType().equals("Operator")){
           holder.userImage.setImageResource(R.drawable.operator_log);
        }else if (activityLogDTO.getPartyType().equals("Supervisor")){
            holder.userImage.setImageResource(R.drawable.supervisor_log);
        }


        holder.txtStartTime.setText(activityLogDTO.getLogStartTimeStr());
        holder.txtStartTime.setKeyListener(null);
        holder.txtEndTime.setText(activityLogDTO.getLogEndTimeStr());
        holder.txtEndTime.setKeyListener(null);
        holder.txtTotalTime.setText(String.valueOf(activityLogDTO.getLogHours()));
        holder.txtTotalTime.setKeyListener(null);

        holder.txtStartKMS.setText(String.valueOf(activityLogDTO.getStartKms()));
        holder.txtStartKMS.setKeyListener(null);
        holder.txtEndKMS.setText(String.valueOf(activityLogDTO.getEndKms()));
        holder.txtEndKMS.setKeyListener(null);
        holder.txtTotalKMS.setText(String.valueOf(activityLogDTO.getNoOfKms()));
        holder.txtTotalKMS.setKeyListener(null);

        holder.txtFuel.setText(String.valueOf(activityLogDTO.getFuel()));
        holder.txtFuel.setKeyListener(null);
        holder.txtRemarks.setKeyListener(null);


        SpannableString span=null;

        if (activityLogDTO.getRemarkType()!=null){
            String text1=activityLogDTO.getRemarkType();
            span= new SpannableString(text1);
            span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,text1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }


        SpannableString span2=null;
        if (!TextUtils.isEmpty(activityLogDTO.getRemarkDesc()) && !activityLogDTO.getRemarkDesc().equals("null")){
            String text2= " - "+activityLogDTO.getRemarkDesc();
            span2= new SpannableString(text2);
            span2.setSpan(new StyleSpan(Typeface.ITALIC),0,text2.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        }
        SpannableStringBuilder builder = new SpannableStringBuilder();

        if (span2!=null && span!=null){
            builder.append(span);
            builder.append(span2);
        }else {
            if (span!=null){
                builder.append(span);
            }else {
                builder.append(span2);
            }

        }

        holder.txtRemarks.setText(builder);

    }

    @Override
    public int getItemCount() {
        return runningLogsList.size();
    }


    public void updateNewList(List<ActivityLogDTO> newList){
        runningLogsList=new ArrayList<>();
        runningLogsList.addAll(newList);
        notifyDataSetChanged();
    }
}
