package com.mareow.recaptchademo.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.mareow.recaptchademo.MainDetailsFragment.AddDailyActivityLogFragment;
import com.mareow.recaptchademo.MainDetailsFragment.DailyLogSupervisorFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int position=0;

    public DatePickerFragment(int position){
     this.position=position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        String startDateString= DailyLogSupervisorFragment.dailyLogsList.get(position).getWoStartDate();
        String endDateString=DailyLogSupervisorFragment.dailyLogsList.get(position).getWoEndDate();
        Date startDate=null;
        Date endDate=null;
        SimpleDateFormat dateFormat1=new SimpleDateFormat("dd/MM/yyyy");
        try {
            startDate=dateFormat1.parse(startDateString);
            endDate=dateFormat1.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(endDate);
        c1.add(Calendar.DATE, 1);
        endDate = c1.getTime();


        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        Date today=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE,-3);
        Date checkDate=calendar.getTime();

        List<Date> validDates=getDaysBetweenDates(checkDate,today);
        List<Date> confirmDate=new ArrayList<>();
        for (int i=0;i<validDates.size();i++){
            if (validDates.get(i).after(startDate) && validDates.get(i).before(endDate)){
                 confirmDate.add(validDates.get(i));
            }
        }
        Date confirmStartDate=confirmDate.get(0);
        Date confirmEndDate=confirmDate.get(confirmDate.size()-1);


        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);

        c.setTime(confirmStartDate);
        long minDate=c.getTime().getTime();

        dialog.getDatePicker().setMinDate(minDate);
        c.setTime(confirmEndDate);
        long maxDate=c.getTime().getTime();

        dialog.getDatePicker().setMaxDate(maxDate);
        dialog.setTitle("");

        // Create a new instance of DatePickerDialog and return it
        return dialog;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar datetime = Calendar.getInstance();
        datetime.setTimeZone(TimeZone.getTimeZone("IST"));
        datetime.set(Calendar.HOUR_OF_DAY, dayOfMonth);
        datetime.set(Calendar.MINUTE, month);
        datetime.set(Calendar.YEAR,year);

        if (Constants.ADDDALIYLOG) {
           /* if (month<10){
                AddDailyActivityLogFragment.mDate.setText(String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(month+1)+"/"+String.valueOf(year));
            }else {
                AddDailyActivityLogFragment.mDate.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
            }*/

            String selectedDate = null;
            if ((month+1)< 10) {
                if (dayOfMonth < 10) {
                    selectedDate = "0" + String.valueOf(dayOfMonth) + "/" + "0" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                    // AddDailyActivityLogFragment.mDate.setText("0"+String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(month+1)+"/"+String.valueOf(year));
                } else {
                    selectedDate = String.valueOf(dayOfMonth) + "/" + "0" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                    // AddDailyActivityLogFragment.mDate.setText(String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(month+1)+"/"+String.valueOf(year));
                }

            } else {
                if (dayOfMonth < 10) {
                    selectedDate = "0" + String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                    // AddDailyActivityLogFragment.mDate.setText("0"+String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                } else {
                    selectedDate = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);
                    // AddDailyActivityLogFragment.mDate.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(month+1)+"/"+String.valueOf(year));
                }

            }


            String todayString = convertddmmyyyyToyyyyMMdd(selectedDate);

            DailyLogSupervisorFragment.sameDayLogs.clear();

            if (DailyLogSupervisorFragment.dailyLogsList.size()>0 && DailyLogSupervisorFragment.dailyLogsList.get(0).getActivityLogId()!=0){
                for (int i = 0; i < DailyLogSupervisorFragment.dailyLogsList.size(); i++) {
                    if (DailyLogSupervisorFragment.dailyLogsList.get(i).getLogDate().equals(todayString)) {
                        DailyLogSupervisorFragment.sameDayLogs.add(DailyLogSupervisorFragment.dailyLogsList.get(i));
                    }
                }
            }


            for (int i = 0; i < DailyLogSupervisorFragment.sameDayLogs.size(); i++) {
                if (DailyLogSupervisorFragment.sameDayLogs.get(i).getLogEndTimeStr() == null) {
                    Toast.makeText(getActivity(), "Selected Date log is already exit just edit endtime.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            AddDailyActivityLogFragment.mDate.setText(selectedDate);
            AddDailyActivityLogFragment.edit_StartHours.setText("");
            AddDailyActivityLogFragment.edit_EndHours.setText("");
        }

    }


    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();
        long interval = 24*1000 * 60 * 60; // 1 hour in millis
        long endTime =enddate.getTime() ; // create your endtime here, possibly using Calendar or Date
        long curTime = startdate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        for(int i=0;i<dates.size();i++){
            Date lDate =(Date)dates.get(i);
        }
        return dates;
    }

    public String convertddmmyyyyToyyyyMMdd(String date){
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


}
