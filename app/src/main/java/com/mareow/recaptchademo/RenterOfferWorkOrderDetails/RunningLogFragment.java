package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.Adapters.RenterRunningLogDatesAdapter;
import com.mareow.recaptchademo.Adapters.RenterRunningLogsAdapter;
import com.mareow.recaptchademo.DataModels.ActivityLogDTO;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.Utils.Constants;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunningLogFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static AppCompatImageView btnRunningFilter;

    public static AppCompatButton btnRunningAllDates;
    //SearchView searchView;
    RecyclerView dailyLogsDetailsRecycle;
    RecyclerView dailyLogsDateRecycle;
    RenterRunningLogDatesAdapter runningLogsDateAdapter;

    AppCompatTextView txtNoRecordFound;

    List<String> dailyLogsDates=new ArrayList<>();
    List<String> uniqueListDate=new ArrayList<>();

    public static RenterRunningLogsAdapter runningLogsAdapter;
    public static List<ActivityLogDTO> runningLogsList=new ArrayList<>();

    private String workStartDate=null;
    private String workEndDate=null;
    private String woRemarks=null;
    private String woRoleType=null;

    OfferWorkOrder offerWorkOrder;
    ProgressDialog progressDialog;

    ArrayList<String> statusItem=new ArrayList<>();
    HashMap<String,String> statusMap=new HashMap<>();


    TextInputEditText btnDialogRemarks;
    TextInputEditText btnDialogRoleType;
    TextInputEditText btnDialogFromDate;
    TextInputEditText btnDialogToDate;

    boolean FiterStartDate=false;
    ArrayList<String> roleType=new ArrayList<>();

    public RunningLogFragment(OfferWorkOrder offerWorkOrder) {
        // Required empty public constructor
        this.offerWorkOrder=offerWorkOrder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_running_log, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait ...........");
        }

        roleType.clear();
        roleType.add("Operator");
        roleType.add("Supervisor");
        roleType.add("Both");
        callStatusApi();
        initView(view);
        return view;
    }

    private void initView(View view) {

        txtNoRecordFound=(AppCompatTextView)view.findViewById(R.id.ODF_runninglog_no_record_found);
        btnRunningAllDates=(AppCompatButton)view.findViewById(R.id.ODF_runninglog_dates_all);
        btnRunningAllDates.setOnClickListener(this);

        btnRunningFilter=(AppCompatImageView) view.findViewById(R.id.ODF_runninglog_filter);
        btnRunningFilter.setOnClickListener(this);


        dailyLogsDetailsRecycle=(RecyclerView)view.findViewById(R.id.ODF_runninglog_recycle_details);
        dailyLogsDetailsRecycle.setHasFixedSize(false);
        dailyLogsDetailsRecycle.setItemAnimator(new DefaultItemAnimator());
        dailyLogsDetailsRecycle.setLayoutManager(new LinearLayoutManager(getContext()));


        dailyLogsDateRecycle=(RecyclerView)view.findViewById(R.id.ODF_runninglog_recycle_dates);
        dailyLogsDateRecycle.setHasFixedSize(false);
        dailyLogsDateRecycle.setItemAnimator(new DefaultItemAnimator());
        dailyLogsDateRecycle.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        callDailyLogsRecycleAdapter();


        workStartDate=null;
        workEndDate=null;
        woRemarks=null;


    }


    private void callDailyLogsRecycleAdapter() {

        if (offerWorkOrder.getWorkorderDTO().getActivityLogDTOs()!=null)
        runningLogsList=offerWorkOrder.getWorkorderDTO().getActivityLogDTOs();

        if (runningLogsList.size()>0){
            dailyLogsDates.clear();
            for (int i=0;i<runningLogsList.size();i++){
                dailyLogsDates.add(runningLogsList.get(i).getLogDate());
            }

            runningLogsAdapter=new RenterRunningLogsAdapter(getActivity(),runningLogsList);
            dailyLogsDetailsRecycle.setAdapter(runningLogsAdapter);

            callDailyLogsAdapterDates();
        }else {

            txtNoRecordFound.setVisibility(View.VISIBLE);
            dailyLogsDetailsRecycle.setVisibility(View.GONE);
            btnRunningAllDates.setVisibility(View.GONE);
            btnRunningFilter.setVisibility(View.GONE);

        }


    }

    private void callDailyLogsAdapterDates() {

        if (dailyLogsDates.size()>0){
            uniqueListDate.clear();
            Set<String> uniqueDates = new HashSet<String>(dailyLogsDates);
            for (String value : uniqueDates) {
                uniqueListDate.add(value);
            }
            Collections.sort(uniqueListDate);
            runningLogsDateAdapter=new RenterRunningLogDatesAdapter(getActivity(),uniqueListDate);
            dailyLogsDateRecycle.setAdapter(runningLogsDateAdapter);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ODF_runninglog_dates_all:
                // Toast.makeText(getContext(), "All", Toast.LENGTH_SHORT).show();
                // callAddActivityLogs();
                btnRunningAllDates.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape_theme));
                btnRunningAllDates.setTextColor(getActivity().getResources().getColor(android.R.color.white));

                runningLogsDateAdapter=new RenterRunningLogDatesAdapter(getActivity(),uniqueListDate);
                dailyLogsDateRecycle.setAdapter(runningLogsDateAdapter);

              //  btnRunningFilter.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape));
               // btnRunningFilter.setImageResource(R.drawable.filter_empty);

                List<ActivityLogDTO> newList=new ArrayList<>();
                newList.addAll(runningLogsList);
                if (runningLogsAdapter!=null){
                    runningLogsAdapter.updateNewList(newList);
                }
                break;
            case R.id.ODF_runninglog_filter:

                btnRunningAllDates.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape));
                btnRunningAllDates.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                runningLogsDateAdapter=new RenterRunningLogDatesAdapter(getActivity(),uniqueListDate);
                dailyLogsDateRecycle.setAdapter(runningLogsDateAdapter);

               // btnRunningFilter.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape_theme));
              //  btnRunningFilter.setImageResource(R.drawable.ic_new_filter_white);

                callFilterDialogs();
                break;
        }
    }


    private void callStatusApi() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> statusCall=apiInterface.getDailyLogsStatus(Constants.LOOK_REMAR_TYPE);
        statusCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String statusJson = response.body().string();
                        JSONObject jsonObject=new JSONObject(statusJson);
                        parseJSONObject(jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Record not Found.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void parseJSONObject(JSONObject statusResponse) {
        statusItem.clear();
        statusMap.clear();
        for(Iterator<String> iter = statusResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            statusItem.add(key);
            try {
                statusMap.put(key, statusResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private void callFilterDialogs() {


        AppCompatButton btnDialogReset;
        AppCompatButton btnDialogSave;

        AppCompatImageButton btnDialogClose;

        AppCompatImageView btnDialogFromDateImage;
        AppCompatImageView btnDialogToDateImage;


        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_for_running_logs);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnDialogRemarks=(TextInputEditText) dialog.findViewById(R.id.filter_rl_dialog_remarks);
        btnDialogRemarks.setInputType(InputType.TYPE_NULL);
        if (woRemarks!=null){
            btnDialogRemarks.setText(woRemarks);
        }
        btnDialogRemarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    callPopwindow();
                }
            }
        });
        btnDialogRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopwindow();
            }
        });



        btnDialogRoleType=(TextInputEditText)dialog.findViewById(R.id.filter_rl_dialog_role_type);
        btnDialogRoleType.setInputType(InputType.TYPE_NULL);
        if (woRoleType!=null){
            btnDialogRoleType.setText(woRoleType);
        }
        btnDialogRoleType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    callrolePopwindow();
                }
            }
        });
        btnDialogRoleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callrolePopwindow();
            }
        });

        btnDialogFromDate=(TextInputEditText) dialog.findViewById(R.id.filter_rl_dialog_fromDate);
        btnDialogFromDate.setInputType(InputType.TYPE_NULL);
        btnDialogFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiterStartDate=true;
                calenderDialogView();
            }
        });
        btnDialogFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    FiterStartDate=true;
                    calenderDialogView();
                }
            }
        });

        if (workStartDate!=null){
            btnDialogFromDate.setText(workStartDate);
            btnDialogFromDate.setTextColor(getActivity().getResources().getColor(android.R.color.black));
        }

       /* btnDialogFromDateImage=(AppCompatImageView)dialog.findViewById(R.id.filter_rl_dialog_fromDateimage);
        btnDialogFromDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiterStartDate=true;
                calenderDialogView();

            }
        });*/
        /*btnDialogFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/


        btnDialogToDate=(TextInputEditText) dialog.findViewById(R.id.filter_rl_dialog_toDate);
        btnDialogToDate.setInputType(InputType.TYPE_NULL);
        btnDialogToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiterStartDate=false;
                calenderDialogView();
            }
        });
        btnDialogToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FiterStartDate=false;
                calenderDialogView();
            }
        });
        if (workEndDate!=null){
            btnDialogToDate.setText(workEndDate);
            btnDialogToDate.setTextColor(getActivity().getResources().getColor(android.R.color.black));
        }
      /*  btnDialogToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
       /* btnDialogToDateImage=(AppCompatImageView)dialog.findViewById(R.id.filter_rl_dialog_toDateImage);
        btnDialogToDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiterStartDate=false;
                calenderDialogView();

            }
        });*/
        btnDialogReset=(AppCompatButton) dialog.findViewById(R.id.filter_rl_dialog_resets);
        btnDialogReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (runningLogsAdapter!=null){
                    runningLogsAdapter.updateNewList(runningLogsList);
                }

                btnDialogRemarks.setText("Remarks");
                btnDialogFromDate.setText("From Date");
                btnDialogToDate.setText("To Date");

                woRemarks=null;
                workStartDate=null;
                workEndDate=null;
                woRoleType=null;

                btnRunningFilter.setImageResource(R.drawable.filter_empty_round);
                dialog.dismiss();
            }
        });
        btnDialogSave=(AppCompatButton) dialog.findViewById(R.id.filter_rl_dialog_save);
        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ActivityLogDTO> newList=new ArrayList<>();
                List<ActivityLogDTO> resultList = new ArrayList<>();
                List<ActivityLogDTO> roleResultList=new ArrayList<>();
                newList.clear();
                resultList.clear();
                roleResultList.clear();

                if (woRemarks==null){
                    if (workStartDate==null && workEndDate==null && woRoleType==null){
                        Toast.makeText(getActivity(), "Require search value.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (woRemarks!=null){
                    for (ActivityLogDTO activityLogDTO:runningLogsList){
                        if (activityLogDTO.getRemarkType()!=null){
                            if (activityLogDTO.getRemarkType().equals(woRemarks)){
                                newList.add(activityLogDTO);
                            }
                        }
                    }

                }
                if (workStartDate!=null && workEndDate!=null) {

                    String sDateString=convertddMMYYYYtoYYYYMMdd(workStartDate);
                    String eDateString=convertddMMYYYYtoYYYYMMdd(workEndDate);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date sDate = null;
                    //Date eDate=null;
                    try {
                        sDate = dateFormat.parse(sDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    try {
                        eDate = dateFormat.parse(eDateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    List<Date> listDate = getDaysBetweenDates(sDate, eDate);
                    for (int i = 0; i < listDate.size(); i++) {
                        Date btDate = listDate.get(i);
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        String newAddDateString = dateFormat1.format(btDate);
                        //searchWorkOrder.setQuery(newAddDateString,true);
                        if (newList.size()>0){
                            for (ActivityLogDTO activityLogDTO : newList) {
                                if (activityLogDTO.getLogDate().equals(newAddDateString)) {
                                    resultList.add(activityLogDTO);
                                }

                            }
                        }else {
                            for (ActivityLogDTO activityLogDTO : runningLogsList) {
                                if (activityLogDTO.getLogDate().equals(newAddDateString)) {
                                    resultList.add(activityLogDTO);
                                }

                            }
                        }


                    }
                }

                if (workStartDate!=null && workEndDate==null){
                    String startDate=convertddMMYYYYtoYYYYMMdd(workStartDate);
                    if (newList.size()>0){
                        for (ActivityLogDTO activityLogDTO : newList) {
                            if (activityLogDTO.getLogDate().equals(startDate)) {
                                resultList.add(activityLogDTO);
                            }

                        }
                    }else {
                        for (ActivityLogDTO activityLogDTO : runningLogsList) {
                            if (activityLogDTO.getLogDate().equals(startDate)) {
                                resultList.add(activityLogDTO);
                            }

                        }
                    }
                }

                if (workEndDate!=null && workStartDate==null){
                    String endDate=convertddMMYYYYtoYYYYMMdd(workEndDate);
                    if (newList.size()>0){
                        for (ActivityLogDTO activityLogDTO : newList) {
                            if (activityLogDTO.getLogDate().equals(endDate)) {
                                resultList.add(activityLogDTO);
                            }

                        }
                    }else {
                        for (ActivityLogDTO activityLogDTO : runningLogsList) {
                            if (activityLogDTO.getLogDate().equals(endDate)) {
                                resultList.add(activityLogDTO);
                            }
                        }
                    }
                }

                if (woRoleType!=null){
                    if (!woRoleType.equals("Both")){

                        if (newList.size()>0){
                            if (resultList.size()>0){
                                for (ActivityLogDTO activityLogDTO : resultList) {
                                    if (activityLogDTO.getPartyType().equals(woRoleType)) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }else {
                                for (ActivityLogDTO activityLogDTO : newList) {
                                    if (activityLogDTO.getPartyType().equals(woRoleType)) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }

                        }else {
                            if (resultList.size()>0){
                                for (ActivityLogDTO activityLogDTO : resultList) {
                                    if (activityLogDTO.getPartyType().equals(woRoleType)) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }else {
                                for (ActivityLogDTO activityLogDTO : runningLogsList) {
                                    if (activityLogDTO.getPartyType().equals(woRoleType)) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }
                        }

                        if (roleResultList.size()>0){
                            resultList.clear();
                            resultList=roleResultList;
                        }

                    }else {
                        if (newList.size()>0){
                            if (resultList.size()>0){
                                for (ActivityLogDTO activityLogDTO : resultList) {
                                    if (activityLogDTO.getPartyType().equals("Supervisor") || activityLogDTO.getPartyType().equals("Operator")) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }else {
                                for (ActivityLogDTO activityLogDTO : newList) {
                                    if (activityLogDTO.getPartyType().equals("Supervisor") || activityLogDTO.getPartyType().equals("Operator")) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }
                        }else {
                            if (resultList.size()>0){
                                for (ActivityLogDTO activityLogDTO : resultList) {
                                    if (activityLogDTO.getPartyType().equals("Supervisor") || activityLogDTO.getPartyType().equals("Operator")) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }else {
                                for (ActivityLogDTO activityLogDTO : runningLogsList) {
                                    if (activityLogDTO.getPartyType().equals("Supervisor") || activityLogDTO.getPartyType().equals("Operator")) {
                                        roleResultList.add(activityLogDTO);
                                    }
                                }
                            }
                        }

                        if (roleResultList.size()>0){
                            resultList.clear();
                            resultList=roleResultList;
                        }
                    }



                }

                if (newList.size()>0){
                    if (resultList.size()>0){
                        if (runningLogsAdapter!=null){
                            Collections.reverse(resultList);
                            runningLogsAdapter.updateNewList(resultList);
                        }
                    }else {
                        if (runningLogsAdapter!=null){
                            runningLogsAdapter.updateNewList(newList);
                        }
                    }
                }else if (resultList.size()>0){
                    if (runningLogsAdapter!=null){
                        Collections.reverse(resultList);
                        runningLogsAdapter.updateNewList(resultList);
                    }
                }else if(newList.size()==0 && resultList.size()==0){
                    if (runningLogsAdapter!=null){
                        Collections.reverse(resultList);
                        runningLogsAdapter.updateNewList(resultList);
                    }
                }
                dialog.dismiss();
            }
        });

        btnDialogClose=(AppCompatImageButton)dialog.findViewById(R.id.filter_rl_dialog_close);
        btnDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout((int) (WorkOrderFragment.getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }

    public String convertddMMYYYYtoYYYYMMdd(String dateString){
        String outputDate="";
        SimpleDateFormat input=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date=input.parse(dateString);
            outputDate=output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }


    public void calenderDialogView(){

        String startDateString= offerWorkOrder.getWorkorderDTO().getWorkStartDate();
        String endDateString=offerWorkOrder.getWorkorderDTO().getWorkEndDate();
        Date startDate=null;
        Date endDate=null;
        SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate=dateFormat1.parse(startDateString);
            endDate=dateFormat1.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Calendar newCalendar = Calendar.getInstance();

        newCalendar.setTime(startDate);
        long minDate=newCalendar.getTime().getTime();

        newCalendar.setTime(endDate);
        long maxDate=newCalendar.getTime().getTime();

        DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
               // Calendar newDate = Calendar.getInstance();
                //newDate.set(year, monthOfYear, dayOfMonth);
                String dateString;
                if ((monthOfYear+1)<10){
                    if (dayOfMonth<10){
                        dateString="0"+String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }else {
                        dateString=String.valueOf(dayOfMonth)+"/"+"0"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }
                }else {
                    if (dayOfMonth<10){
                        dateString="0"+String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }else {
                        dateString=String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year);
                    }
                }

                if (FiterStartDate){
                  /*  btnDialogFromDate.setText(dateString);
                    workStartDate=btnDialogFromDate.getText().toString().trim();*/
                    if (!btnDialogToDate.getText().toString().isEmpty()){
                        String toDateString=btnDialogToDate.getText().toString();

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date seletedDate = null;
                        Date toDate=null;
                        try {
                            seletedDate = sdf.parse(dateString);
                            toDate=sdf.parse(toDateString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (seletedDate.before(toDate)|| seletedDate.compareTo(toDate)==0) {
                            btnDialogFromDate.setText(dateString);
                            workStartDate=btnDialogFromDate.getText().toString().trim();
                        }else {
                            // showSnackbar("Selected Date is greater than to Date :"+toDateString);
                            Toast.makeText(getActivity(), "Selected Date is greater than to Date :"+toDateString, Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        btnDialogFromDate.setText(dateString);
                        workStartDate=btnDialogFromDate.getText().toString().trim();
                    }

                }else {
                   /* btnDialogToDate.setText(dateString);
                    workEndDate=btnDialogToDate.getText().toString().trim();*/

                    if (!btnDialogFromDate.getText().toString().isEmpty()){
                        String fromDateString=btnDialogFromDate.getText().toString();

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date seletedDate = null;
                        Date fromDate=null;
                        try {
                            seletedDate = sdf.parse(dateString);
                            fromDate=sdf.parse(fromDateString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (seletedDate.after(fromDate) || seletedDate.compareTo(fromDate)==0) {
                            btnDialogToDate.setText(dateString);
                            workEndDate=btnDialogToDate.getText().toString();
                        }else {
                            // showSnackbar("Selected Date is Lesser than from Date :"+fromDateString);
                            Toast.makeText(getActivity(), "Selected Date is Lesser than from Date :"+fromDateString, Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        btnDialogToDate.setText(dateString);
                        workEndDate=btnDialogToDate.getText().toString();
                    }

                }

                btnRunningFilter.setImageResource(R.drawable.filter_with_value_round);

                       /* SimpleDateFormat input=new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat output=new SimpleDateFormat("yyyy-MM-dd");
                        Date date=null;
                        try {
                            date=input.parse(btnDialogFromDate.getText().toString().trim());
                            workStartDate=output.format(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }*/
                //  btnDialogFromDate.setText(String.valueOf(dayOfMonth) + " / " + String.valueOf((monthOfYear + 1)) + " / " + String.valueOf(year));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMinDate(minDate);
        StartTime.getDatePicker().setMaxDate(maxDate);
/*
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        StartTime.getDatePicker().init(year,month,day,null);*/

        StartTime .show();
    }

    public void callPopwindow(){
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),statusItem);
        popupWindow.setAnchorView(btnDialogRemarks);
        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setWidth(btnDialogRemarks.getMeasuredWidth());
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnDialogRemarks.setText(statusItem.get(position));
                woRemarks=statusItem.get(position);
                btnRunningFilter.setImageResource(R.drawable.filter_with_value_round);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    public void callrolePopwindow(){
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),roleType);
        popupWindow.setAnchorView(btnDialogRoleType);
        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setWidth(btnDialogRoleType.getMeasuredWidth());
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnDialogRoleType.setText(roleType.get(position));
                woRoleType=roleType.get(position);

                btnRunningFilter.setImageResource(R.drawable.filter_with_value_round);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    public List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();

        /*Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }*/

        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Date  startDate = (Date)formatter.parse(startdate);
        //Date  endDate = (Date)formatter.parse(enddate);
        long interval = 24*1000 * 60 * 60; // 1 hour in millis
        long endTime =enddate.getTime() ; // create your endtime here, possibly using Calendar or Date
        long curTime = startdate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        for(int i=0;i<dates.size();i++){
            Date lDate =(Date)dates.get(i);
            Log.d("LogDate",lDate.toString());
            //String ds = formatter.format(lDate);
            //System.out.println(" Date is ..." + ds);
        }

        return dates;
    }

}
