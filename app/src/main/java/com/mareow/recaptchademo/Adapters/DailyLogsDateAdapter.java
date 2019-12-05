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

import com.mareow.recaptchademo.DataModels.DailyLogsSupervisor;
import com.mareow.recaptchademo.MainDetailsFragment.DailyLogSupervisorFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.DailyLogsDateViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DailyLogsDateAdapter extends RecyclerView.Adapter<DailyLogsDateViewHolder> {

    Context context;
    List<String> dailyLogsDates;
    int row_index = -1;
    public DailyLogsDateAdapter(Context context, List<String> dailyLogsDates) {
        this.context = context;
        this.dailyLogsDates = dailyLogsDates;
    }

    @NonNull
    @Override
    public DailyLogsDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.daily_logs_dates_adapter, parent, false);
        return new DailyLogsDateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DailyLogsDateViewHolder holder, final int position) {

        if (!dailyLogsDates.get(position).equals("All")) {

           //SpannableStringBuilder sb = new SpannableStringBuilder(Util.convertYYYYddMMtoMMMdd(dailyLogsDates.get(position)));

           String text1=Util.convertYYYYddMMtoMMMdd(dailyLogsDates.get(position));


         /*   final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
            final StyleSpan iss = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic
            sb.setSpan(bss, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
            sb.setSpan(iss, 7, sb.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make last 2 characters Italic
*/
           // SpannableString span1 = new SpannableString(text1);
            //span1.setSpan(new AbsoluteSizeSpan(12,true), 0,6,Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            SpannableString span2 = new SpannableString(text1);
            span2.setSpan(new AbsoluteSizeSpan(10,true), 7, text1.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span2.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,6,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            span2.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC),7, text1.length(),Spannable.SPAN_INCLUSIVE_INCLUSIVE);

           // CharSequence finalText = TextUtils.concat(span1, " ", span2);

            holder.txtDates.setText(span2);


           /* String [] year=dailyLogsDates.get(position).split("-");
            String italicString="<i>"+year[0]+"</i>";
            String boldString="<b>"+Util.convertYYYYddMMtoMMMdd(dailyLogsDates.get(position))+"</b>";
            holder.txtDates.setText(Html.fromHtml(boldString)+"\n"+Html.fromHtml(italicString));*/

        } else {
            holder.txtDates.setText(dailyLogsDates.get(position));
            holder.txtDates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape_theme));
            holder.txtDates.setTextColor(context.getResources().getColor(android.R.color.white));
        }

        holder.txtDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                notifyDataSetChanged();

                DailyLogSupervisorFragment.btnAllDates.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape));
                DailyLogSupervisorFragment.btnAllDates.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                //DailyLogSupervisorFragment.btnFilter.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.circle_shape));
               // DailyLogSupervisorFragment.btnFilter.setImageResource(R.drawable.ic_filter_blue_new);

                List<DailyLogsSupervisor> newList=new ArrayList<>();

                    for(DailyLogsSupervisor dailyLogsSupervisor : DailyLogSupervisorFragment.dailyLogsList){
                        if (dailyLogsSupervisor.getLogDate().equals(dailyLogsDates.get(position))){
                            newList.add(dailyLogsSupervisor);
                        }
                    }

                if (DailyLogSupervisorFragment.dailyLogsAdapter!=null){
                    DailyLogSupervisorFragment.dailyLogsAdapter.updateNewList(newList);
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
        return dailyLogsDates.size();
    }



}
