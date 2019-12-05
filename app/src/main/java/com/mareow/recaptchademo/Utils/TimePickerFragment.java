package com.mareow.recaptchademo.Utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import androidx.fragment.app.DialogFragment;

import com.mareow.recaptchademo.MainDetailsFragment.AddDailyActivityLogFragment;
import com.mareow.recaptchademo.MainDetailsFragment.DailyLogSupervisorFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

       /* Date today=new Date();
        String startTime=null;
        String endTime=null;
        SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy-MM-dd");
        String todayString=simpleDate.format(today);
        DailyLogsSupervisor dailyLogsSupervisor;
        for (int i = 0; i< DailyLogSupervisorFragment.dailyLogsList.size(); i++){
            if (DailyLogSupervisorFragment.dailyLogsList.get(i).getLogDate().equals(todayString)){
                startTime=DailyLogSupervisorFragment.dailyLogsList.get(i).getLogStartTimeStr();
                endTime=DailyLogSupervisorFragment.dailyLogsList.get(i).getLogEndTimeStr();
                break;
            }
        }

        Date Start = null;
        Date End = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            Start = simpleDateFormat.parse(startTime);
            End = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            //Some thing if its not working
        }*/


        final Calendar c = Calendar.getInstance();
       // c.setTime(End);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerDialog and return it


        return new TimePickerDialog(getActivity(), this, hour, minute,DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String am_pm = "";


        Calendar datetime = Calendar.getInstance();
        datetime.setTimeZone(TimeZone.getTimeZone("IST"));
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        String selectedTime=null;

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR)+"";
            if (minute>=10){
                selectedTime=strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm;
            }else {
                selectedTime=strHrsToShow+":"+"0"+datetime.get(Calendar.MINUTE)+" "+am_pm;
            }

            String logdate=convertddmmyyyyToyyyyMMdd(AddDailyActivityLogFragment.mDate.getText().toString());
            DailyLogSupervisorFragment.sameDayLogs.clear();

        if (DailyLogSupervisorFragment.dailyLogsList.size()>0 && DailyLogSupervisorFragment.dailyLogsList.get(0).getActivityLogId()!=0){
            for (int i = 0; i < DailyLogSupervisorFragment.dailyLogsList.size(); i++) {
                if (DailyLogSupervisorFragment.dailyLogsList.get(i).getLogDate().equals(logdate)) {
                    DailyLogSupervisorFragment.sameDayLogs.add(DailyLogSupervisorFragment.dailyLogsList.get(i));
                }
            }
        }


        if (DailyLogSupervisorFragment.sameDayLogs.size()>0){

            for (int i=0;i<DailyLogSupervisorFragment.sameDayLogs.size();i++){

                String startTime=DailyLogSupervisorFragment.sameDayLogs.get(i).getLogStartTimeStr();
                String endTime=DailyLogSupervisorFragment.sameDayLogs.get(i).getLogEndTimeStr();

                if (endTime!=null){
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
                    Date startTimeStamp=null;
                    String TFStartFormatString=null;
                    try {
                        TFStartFormatString=convertTo24HoursFormat(startTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        startTimeStamp=sdf.parse(TFStartFormatString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Date endTimeStamp=null;
                    String TFEndFormatString=null;
                    try {
                        TFEndFormatString=convertTo24HoursFormat(endTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        endTimeStamp=sdf.parse(TFEndFormatString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Date seletedTimeStamp=null;
                    String TFseletedFormatString=null;
                    try {
                        TFseletedFormatString=convertTo24HoursFormat(selectedTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        seletedTimeStamp=sdf.parse(TFseletedFormatString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat requiredFormat = new SimpleDateFormat("HH:mm:ss");

                    String finalStartTime=requiredFormat.format(startTimeStamp);
                    String finalEndTime=requiredFormat.format(endTimeStamp);
                    String finalSelectedTime=requiredFormat.format(seletedTimeStamp);

                    try {
                        if(isTimeBetweenTwoTime(finalStartTime,finalEndTime,finalSelectedTime)){
                            Toast.makeText(getActivity(), "Invalid time! Please Select greater than"+startTime+" "+endTime, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

        }



        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR)+"";

        if (AddDailyActivityLogFragment.START_TIME){


            if (!AddDailyActivityLogFragment.edit_EndHours.getText().toString().isEmpty()){

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");

                Date selectedStartTime = null;
                Date outTime=null;
                try {

                     String outTime24=convertTo24HoursFormat(AddDailyActivityLogFragment.edit_EndHours.getText().toString());
                     outTime = sdf.parse(outTime24);
                     String seleted24=convertTo24HoursFormat(selectedTime);
                     selectedStartTime = sdf.parse(seleted24);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (selectedStartTime.before(outTime)){

                    if (minute>=10){
                        AddDailyActivityLogFragment.edit_StartHours.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                    }else {
                        AddDailyActivityLogFragment.edit_StartHours.setText(strHrsToShow+":"+"0"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                    }
                }else {
                    Toast.makeText(getActivity(), "starttime lesser time than endtime", Toast.LENGTH_SHORT).show();
                    return;
                }

            }else {

                if (minute>=10){
                    AddDailyActivityLogFragment.edit_StartHours.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                }else {
                    AddDailyActivityLogFragment.edit_StartHours.setText(strHrsToShow+":"+"0"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                }
            }

        }

        if (AddDailyActivityLogFragment.END_TIME){


            if (!AddDailyActivityLogFragment.edit_StartHours.getText().toString().isEmpty()){

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");

                Date selectedEndTime = null;
                Date outTime=null;
                try {


                    String outTime24=convertTo24HoursFormat(AddDailyActivityLogFragment.edit_StartHours.getText().toString());
                    outTime = sdf.parse(outTime24);
                    String seleted24=convertTo24HoursFormat(selectedTime);
                    selectedEndTime = sdf.parse(seleted24);

                  //  outTime = sdf.parse(AddDailyActivityLogFragment.edit_StartHours.getText().toString());
                    //selectedEndTime = sdf.parse(selectedTime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (selectedEndTime.after(outTime)){

                    if (minute>=10){
                        AddDailyActivityLogFragment.edit_EndHours.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                    }else {
                        AddDailyActivityLogFragment.edit_EndHours.setText(strHrsToShow+":"+"0"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                    }

                }else {
                    Toast.makeText(getActivity(), "endtime greater than starttime", Toast.LENGTH_SHORT).show();
                    return;
                }

            }else {

                if (minute>=10){
                    AddDailyActivityLogFragment.edit_EndHours.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                }else {
                    AddDailyActivityLogFragment.edit_EndHours.setText(strHrsToShow+":"+"0"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                }
            }

        }


    }


    public  String convertTo24HoursFormat(String twelveHourTime) throws ParseException {
        java.text.DateFormat TWELVE_TF = new SimpleDateFormat("hh:mm aa");
        // Replace with kk:mm if you want 1-24 interval
        java.text.DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("HH:mm aa");
        return TWENTY_FOUR_TF.format(TWELVE_TF.parse(twelveHourTime));
    }

    public static boolean isTimeBetweenTwoTime(String argStartTime,String argEndTime, String argCurrentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg) && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm:ss")
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

               // System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

              //  System.out.println("Comparing , Start Time /n " + startTime);
              //  System.out.println("Comparing , End Time /n " + endTime);
               // System.out.println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                   // System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    //System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
        }

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
