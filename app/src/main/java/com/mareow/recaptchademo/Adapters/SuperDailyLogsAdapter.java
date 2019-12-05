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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.DailyLogsSupervisor;
import com.mareow.recaptchademo.MainDetailsFragment.AddDailyActivityLogFragment;
import com.mareow.recaptchademo.MainDetailsFragment.DailyLogSupervisorFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.SuperDailyLogsViewHolder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SuperDailyLogsAdapter extends RecyclerView.Adapter<SuperDailyLogsViewHolder> {
    Context context;
    List<DailyLogsSupervisor> dailyLogsList;

    public SuperDailyLogsAdapter(Context context, List<DailyLogsSupervisor> dailyLogsList) {
        this.context=context;
        this.dailyLogsList=dailyLogsList;
    }

    @NonNull
    @Override
    public SuperDailyLogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.super_daily_logs_adapter,parent,false);
        return new SuperDailyLogsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SuperDailyLogsViewHolder holder, final int position) {
        DailyLogsSupervisor dailyLogs=dailyLogsList.get(position);

        //holder.mContainer.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        holder.txtDate.setText(Util.convertYYYYddMMtoDDmmYYYY(dailyLogs.getLogDate()));
        holder.txtDate.setKeyListener(null);

        holder.txtStartTime.setText(dailyLogs.getLogStartTimeStr());
        holder.txtStartTime.setKeyListener(null);
        holder.txtEndTime.setText(dailyLogs.getLogEndTimeStr());
        holder.txtEndTime.setKeyListener(null);
        holder.txtTotalTime.setText(String.valueOf(dailyLogs.getLogHours()));
        holder.txtTotalTime.setKeyListener(null);

        holder.txtStartKMS.setText(String.valueOf(dailyLogs.getStartKms()));
        holder.txtStartKMS.setKeyListener(null);
        holder.txtEndKMS.setText(String.valueOf(dailyLogs.getEndKms()));
        holder.txtEndKMS.setKeyListener(null);
        holder.txtTotalKMS.setText(String.valueOf(dailyLogs.getNoOfKms()));
        holder.txtTotalKMS.setKeyListener(null);

        holder.txtFuel.setText(String.valueOf(dailyLogs.getFuel()));
        holder.txtFuel.setKeyListener(null);
        holder.txtRemarks.setKeyListener(null);


        SpannableString span=null;

        if (dailyLogs.getRemarkType()!=null){
            String text1=dailyLogs.getRemarkType();
            span= new SpannableString(text1);
            span.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,text1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }


        SpannableString span2=null;
        if (!TextUtils.isEmpty(dailyLogs.getRemarkDesc()) && !dailyLogs.getRemarkDesc().equals("null")){
            String text2= " - "+dailyLogs.getRemarkDesc();
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

        if (!calcualteEditOption(position)){
            holder.btnEdit.setImageResource(R.drawable.edit_disable_round);
            holder.btnEdit.setEnabled(false);
        }else {
            holder.btnEdit.setImageResource(R.drawable.edit_enable_round);
            holder.btnEdit.setEnabled(true);
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calcualteEditOption(position)){
                     Constants.DAILY_LOG_EDIT=true;

                    DailyLogsSupervisor dailyLogsSupervisor=dailyLogsList.get(position);
                    DailyLogSupervisorFragment.sameDayLogs.clear();
                    for (int i = 0; i<dailyLogsList.size(); i++){

                        if (dailyLogsList.get(i).getLogDate().equals(dailyLogsSupervisor.getLogDate())){

                            if (dailyLogsSupervisor.getActivityLogId()==dailyLogsList.get(i).getActivityLogId()){

                            }else {
                                DailyLogSupervisorFragment.sameDayLogs.add(dailyLogsList.get(i));
                            }

                        }
                    }

                     Fragment addDailyActivityLogFragment = new AddDailyActivityLogFragment(position);
                     FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                     transaction.replace(R.id.fragment_container_main, addDailyActivityLogFragment); // give your fragment container id in first parameter
                     transaction.addToBackStack("DailyLogsEdit");
                     transaction.commit();
                }
            }
        });


    }

    private boolean calcualteEditOption(int position) {
        String fromDateString = dailyLogsList.get(position).getLogDate();

            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String todayString = sdf.format(today);
            Date fromDate = null;
            Date toDate = null;
            try {
                fromDate = sdf.parse(fromDateString);
                toDate = sdf.parse(todayString);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar c = Calendar.getInstance();
            c.setTime(fromDate);
            c.add(Calendar.DATE, 3);
            if (c.getTime().compareTo(toDate) < 0) {
                //It's more than 3 days.
                //  Toast.makeText(context, "It's more than 3 days.", Toast.LENGTH_SHORT).show();
                return false;

            } else {
                return true;
            }


    }


    @Override
    public int getItemCount() {
        return dailyLogsList.size();
    }
    public void updateNewList(List<DailyLogsSupervisor> newList){
        dailyLogsList=new ArrayList<>();
        dailyLogsList.addAll(newList);
        notifyDataSetChanged();
    }
}
