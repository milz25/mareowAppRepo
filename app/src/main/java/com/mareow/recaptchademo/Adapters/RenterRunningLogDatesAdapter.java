package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.ActivityLogDTO;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterOfferWorkOrderDetails.RunningLogFragment;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.RenterRunningLogDatesViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RenterRunningLogDatesAdapter extends RecyclerView.Adapter<RenterRunningLogDatesViewHolder> {


    Context context;
    List<String> runningLogsDates;
    int row_index = -1;
    public RenterRunningLogDatesAdapter(Context context, List<String> runningLogsDates) {
        this.context = context;
        this.runningLogsDates = runningLogsDates;
    }


    @NonNull
    @Override
    public RenterRunningLogDatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.renter_running_log_date_adapter, parent, false);
        return new RenterRunningLogDatesViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RenterRunningLogDatesViewHolder holder, int position) {

        if (!runningLogsDates.get(position).equals("All")) {

            String text1= Util.convertYYYYddMMtoMMMdd(runningLogsDates.get(position));

            SpannableString span2 = new SpannableString(text1);
            span2.setSpan(new AbsoluteSizeSpan(10,true), 7, text1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span2.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,6,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span2.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC),7, text1.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            // CharSequence finalText = TextUtils.concat(span1, " ", span2);

            holder.txtDates.setText(span2);

        } else {
            holder.txtDates.setText(runningLogsDates.get(position));
            holder.txtDates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape_theme));
            holder.txtDates.setTextColor(context.getResources().getColor(android.R.color.white));
        }

        holder.txtDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();

                RunningLogFragment.btnRunningAllDates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape));
                RunningLogFragment.btnRunningAllDates.setTextColor(context.getResources().getColor(R.color.colorPrimary));

               // RunningLogFragment.btnRunningFilter.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape));
               // RunningLogFragment.btnRunningFilter.setImageResource(R.drawable.ic_filter_blue_new);

                List<ActivityLogDTO> newList=new ArrayList<>();

                for(ActivityLogDTO activityLogDTO : RunningLogFragment.runningLogsList){
                    if (activityLogDTO.getLogDate().equals(runningLogsDates.get(position))){
                        newList.add(activityLogDTO);
                    }
                }

                if (RunningLogFragment.runningLogsAdapter!=null){
                    RunningLogFragment.runningLogsAdapter.updateNewList(newList);
                }
            }
        });


        if (row_index == position) {
            holder.txtDates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape_theme));
            holder.txtDates.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            if (row_index!=-1){
                holder.txtDates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape));
                holder.txtDates.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        }

    }

    @Override
    public int getItemCount() {
        return runningLogsDates.size();
    }
}
