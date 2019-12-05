package com.mareow.recaptchademo.MainDetailsFragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Adapters.SpinnerRecycleAdapter;
import com.mareow.recaptchademo.DataModels.AddDailyLog;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.UpdateDailyLogs;
import com.mareow.recaptchademo.DataModels.WorkOrderResponse;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.CustomDialogForSpinner;
import com.mareow.recaptchademo.Utils.DatePickerFragment;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TimePickerFragment;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

public class AddDailyActivityLogFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static TextInputEditText edit_StartHours;
    public static TextInputEditText edit_EndHours;
    AppCompatEditText edit_Reason;

    TextInputEditText edit_TotalHours;
    TextInputEditText edit_StartKms;
    TextInputEditText edit_EndKms;
    TextInputEditText edit_TotalKms;

    TextInputEditText edit_Fuel;
    TextInputEditText workOrderNo;
    AppCompatImageView btnWorkStatus;

    public String mDefualtStatus="Normal";

    TextInputEditText mSpinnerStatus;
    public static TextInputEditText mDate;

    FloatingActionButton btnSave;
    int position=0;
    public static boolean START_TIME=false;
    public static boolean END_TIME=false;
    ProgressDialog progressDialog;

    public static Date logDate;
    TokenManager tokenManager;


    ArrayList<String> statusItem=new ArrayList<>();
    HashMap<String,String> statusMap=new HashMap<>();
    WorkOrderResponse workOrderResponse;
   // WorkOrderMachine workOrderMachine;

    public AddDailyActivityLogFragment(int position) {
        // Required empty public constructor
        this.position=position;
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
        MainActivity.navItemIndex=14;
        MainActivity.setToolbarTitle();
        View view=inflater.inflate(R.layout.fragment_add_daily_activity_log, container, false);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..........");
        tokenManager=new TokenManager(getActivity());
        if (!Constants.DAILY_LOG_EDIT){
            Bundle bundle=getArguments();
            workOrderResponse=(WorkOrderResponse) bundle.getSerializable("newWorkOrder");
           // workOrderMachine=(WorkOrderMachine)bundle.getSerializable("newMachine");
        }
        initView(view);
        callStatusApi();
        return view;
    }

    private void initView(View view) {

        edit_StartHours=(TextInputEditText)view.findViewById(R.id.add_daily_log_starttime);
        edit_StartHours.setInputType(InputType.TYPE_NULL);

        edit_EndHours=(TextInputEditText)view.findViewById(R.id.add_daily_log_endtime);
        edit_EndHours.setInputType(InputType.TYPE_NULL);

        edit_TotalHours=(TextInputEditText)view.findViewById(R.id.add_daily_log_totaltime);
        edit_TotalHours.setText("0");
        edit_TotalHours.setKeyListener(null);

        edit_StartKms=(TextInputEditText)view.findViewById(R.id.add_daily_log_startkms);
        edit_EndKms=(TextInputEditText)view.findViewById(R.id.add_daily_log_endkms);
        edit_TotalKms=(TextInputEditText)view.findViewById(R.id.add_daily_log_totalkms);
        edit_TotalKms.setText("0");
        edit_TotalKms.setKeyListener(null);

        edit_Fuel=(TextInputEditText)view.findViewById(R.id.add_daily_log_fuel);
        edit_Reason=(AppCompatEditText)view.findViewById(R.id.add_daily_log_reason);

        workOrderNo=(TextInputEditText)view.findViewById(R.id.add_daily_log_workorder);
        if (!Constants.DAILY_LOG_EDIT){
            workOrderNo.setText(workOrderResponse.getWorkOrderNo());
        }else {
            workOrderNo.setText(DailyLogSupervisorFragment.dailyLogsList.get(position).getWorkOrderNo());
        }

        workOrderNo.setKeyListener(null);

        btnWorkStatus=(AppCompatImageView) view.findViewById(R.id.add_daily_log_status_button);

       if (!Constants.DAILY_LOG_EDIT){
           if (workOrderResponse.getWorkOrderStatus().equals("WO: Closed")){
               btnWorkStatus.setBackgroundResource(R.drawable.wo_close_final);
           }else {
               btnWorkStatus.setBackgroundResource(R.drawable.wo_open_final);
           }
       }else {
           if (DailyLogSupervisorFragment.dailyLogsList.get(position).isWoCloseFLG()){
               btnWorkStatus.setBackgroundResource(R.drawable.wo_close_final);
           }else {
               btnWorkStatus.setBackgroundResource(R.drawable.wo_open_final);
           }
       }


        //btnWorkStatus.setText();

        mSpinnerStatus=(TextInputEditText) view.findViewById(R.id.add_daily_log_status);
        mSpinnerStatus.setInputType(InputType.TYPE_NULL);
        mSpinnerStatus.setOnClickListener(this);
        mSpinnerStatus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                    /*Constants.SIGNUP=false;
                    Constants.ADD_DAILY_LOG=true;
                    callSpinnerDialog();*/
                    Collections.sort(statusItem);
                    showSpinnerDialog("Status",statusItem);
                }
            }
        });
        mDate=(TextInputEditText) view.findViewById(R.id.add_daily_log_date);
        mDate.setInputType(InputType.TYPE_NULL);
      /*  mDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    callDateFragment();
                }
            }
        });*/

      logDate=new Date();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");

        String startDateString= WorkOrderFragment.workOrderList.get(position).getWorkStartDate();
        String endDateString=WorkOrderFragment.workOrderList.get(position).getWorkEndDate();

        Date startDate=null;
        Date endDate=null;

        SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate=dateFormat1.parse(startDateString);
            endDate=dateFormat1.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

         if (logDate.after(startDate) && logDate.before(endDate)){
             mDate.setText(simpleDateFormat.format(logDate));
         }else {
             mDate.setText(simpleDateFormat.format(endDate));
         }


      mDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
              if (hasFocus){
                  callDateFragment();
              }
          }
      });

      mDate.setOnClickListener(this);

        btnSave=(FloatingActionButton)view.findViewById(R.id.add_daily_log_save);
        btnSave.setOnClickListener(this);

        edit_StartHours.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    START_TIME=true;
                    END_TIME=false;
                    callTimeFragment();
                }else {
                    try {
                        calcTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        edit_StartHours.setOnClickListener(this);
        edit_EndHours.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    START_TIME=false;
                    END_TIME=true;
                    callTimeFragment();
                }else {
                    try {
                        calcTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        edit_EndHours.setOnClickListener(this);

        edit_StartKms.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }else {
                    try {
                        edit_EndKms.setText(String.valueOf(Integer.parseInt(edit_StartKms.getText().toString())+1));
                        calcKms();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        edit_EndKms.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }else {
                    try {
                        calcKms();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        if (Constants.DAILY_LOG_EDIT){
           // DailyLogSupervisorFragment.dailyLogsList.get(position);
            mDate.setText(Util.convertYYYYddMMtoDDmmYYYY(DailyLogSupervisorFragment.dailyLogsList.get(position).getLogDate()));
            mDate.setEnabled(false);

            edit_StartHours.setText(convertyyyyMMddTHHmmssToHHmm(DailyLogSupervisorFragment.dailyLogsList.get(position).getLogStartTime()));
            if (DailyLogSupervisorFragment.dailyLogsList.get(position).getLogEndTime()!=null)
            edit_EndHours.setText(convertyyyyMMddTHHmmssToHHmm(DailyLogSupervisorFragment.dailyLogsList.get(position).getLogEndTime()));

            edit_TotalHours.setText(String.valueOf(DailyLogSupervisorFragment.dailyLogsList.get(position).getLogHours()));

            edit_StartKms.setText(String.valueOf(DailyLogSupervisorFragment.dailyLogsList.get(position).getStartKms()));
            edit_EndKms.setText(String.valueOf(DailyLogSupervisorFragment.dailyLogsList.get(position).getEndKms()));
            edit_TotalKms.setText(String.valueOf(DailyLogSupervisorFragment.dailyLogsList.get(position).getNoOfKms()));

            edit_Fuel.setText(String.valueOf(DailyLogSupervisorFragment.dailyLogsList.get(position).getFuel()));
            mSpinnerStatus.setText(DailyLogSupervisorFragment.dailyLogsList.get(position).getRemarkType());

            edit_Reason.setText(DailyLogSupervisorFragment.dailyLogsList.get(position).getRemarkDesc());



        }else {
            if (DailyLogSupervisorFragment.LOG_ZERO){
                edit_StartKms.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getMachine().getOdometer()));
                edit_EndKms.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getMachine().getOdometer()));
            }else {
                edit_StartKms.setText(String.valueOf(DailyLogSupervisorFragment.MAX_END_KMS));
                edit_EndKms.setText(String.valueOf(DailyLogSupervisorFragment.MAX_END_KMS));
            }
            edit_Reason.setText("No problem occured. Work completed normally.");

            /*if (DailyLogSupervisorFragment.FIXED_START_TIME!=null){
                edit_StartHours.setText(DailyLogSupervisorFragment.FIXED_START_TIME);
                edit_StartHours.setEnabled(false);
            }*/
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_daily_log_save:
                callSaveDailyLogRetrofit();
                break;
            case R.id.add_daily_log_status:
                /*Constants.SIGNUP=false;
                Constants.ADD_DAILY_LOG=true;
                callSpinnerDialog();*/
                Collections.sort(statusItem);
                showSpinnerDialog("Status",statusItem);
                break;
            case R.id.add_daily_log_date:
                callDateFragment();
                break;
            case R.id.add_daily_log_starttime:
                START_TIME=true;
                END_TIME=false;
                callTimeFragment();
                break;
            case R.id.add_daily_log_endtime:
                START_TIME=false;
                END_TIME=true;
                callTimeFragment();
                break;
        }
    }

    private void callSpinnerDialog() {
        Collections.sort(statusItem);
        CustomDialogForSpinner customDialogForSpinner=new CustomDialogForSpinner(getActivity(),"Status",statusItem);
        customDialogForSpinner.ShowSpinnerDialog();
    }

    private void callTimeFragment() {

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "TimePicker");
    }
    private void callDateFragment() {
        Constants.WO_START_DATE=false;
        Constants.WO_END_DATE=false;
        Constants.ADDDALIYLOG=true;
        DialogFragment newFragment = new DatePickerFragment(position);
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    private void callSaveDailyLogRetrofit() {
        String date=mDate.getText().toString();
        String startTime=edit_StartHours.getText().toString();
        String endTime=edit_EndHours.getText().toString();
        String startKms=edit_StartKms.getText().toString();
        String endKms=edit_EndKms.getText().toString();
        String fuel=edit_Fuel.getText().toString();
        String remarks=edit_Reason.getText().toString();
        String status=mSpinnerStatus.getText().toString().trim();

        String token= TokenManager.getSessionToken();
        int partyId=tokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String partyName=tokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_NAME,null);

        if (date.isEmpty()){
            mDate.setError("Select date");
            mDate.requestFocus();
            return;
        }

        if (startTime.isEmpty()){
            edit_StartHours.setError("Select start time");
            edit_StartHours.requestFocus();
            return;
        }

        if (startKms.isEmpty()){
            edit_StartKms.setError("Enter start kms");
            edit_StartKms.requestFocus();
            return;
        }
      /*  if (endKms.isEmpty()){
            edit_EndKms.setError("Enter end kms");
            edit_EndKms.requestFocus();
            return;
        }*/
        if (fuel.isEmpty()){
            edit_Fuel.setError("Enter end kms");
            edit_Fuel.requestFocus();
            return;
        }
        if (status.isEmpty()){
            mSpinnerStatus.setError("Enter status.");
            mSpinnerStatus.requestFocus();
            return;
        }
        if (remarks.isEmpty()){
            edit_Reason.setError("Enter remarks.");
            edit_Reason.requestFocus();
            return;
        }


       /* Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("IST"));
        logDate=calendar.getTime();
*/

        SimpleDateFormat smp=new SimpleDateFormat("dd/MM/yyyy");
        Date lognewDate=null;
        try {
            lognewDate=smp.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String logDateString=simpleDateFormat.format(lognewDate);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        Date startTimeStamp=null;
        String TFStartFormatString=null;
        try {
            TFStartFormatString=convertTo24HoursFormat(edit_StartHours.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            startTimeStamp=sdf.parse(TFStartFormatString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        SimpleDateFormat  outputFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String startTimeString=outputFormat.format(startTimeStamp);

       /* Date STD=null;
        try {
            STD=outputFormat.parse(startTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        String endTimeString=null;

        if (!edit_EndHours.getText().toString().isEmpty()){
            String TFFormatString=null;
            try {
                TFFormatString=convertTo24HoursFormat(edit_EndHours.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date endTimeStamp=null;
            try {
                endTimeStamp=sdf.parse(TFFormatString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            endTimeString=outputFormat.format(endTimeStamp);

            /*Date ETD=null;
            try {
                ETD=outputFormat.parse(endTimeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
        }



        if (Constants.DAILY_LOG_EDIT){

            UpdateDailyLogs updateDailyLogs=new UpdateDailyLogs();
            updateDailyLogs.setActivityLogId(DailyLogSupervisorFragment.dailyLogsList.get(position).getActivityLogId());
            updateDailyLogs.setWorkOrderId(DailyLogSupervisorFragment.dailyLogsList.get(position).getWorkOrderId());
            updateDailyLogs.setMachineId(DailyLogSupervisorFragment.dailyLogsList.get(position).getMachineId());
            updateDailyLogs.setPartyId(DailyLogSupervisorFragment.dailyLogsList.get(position).getPartyId());

            String editLogDate=mDate.getText().toString();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String editLogDateString=null;
            try {
                Date updateLogDate=dateFormat.parse(editLogDate);
                editLogDateString=simpleDateFormat.format(updateLogDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            updateDailyLogs.setLogDate(editLogDateString);
            updateDailyLogs.setLogDateStr(editLogDate);
            updateDailyLogs.setLogStartTime(startTimeString);
            updateDailyLogs.setLogEndTime(endTimeString);


            if (!edit_TotalHours.getText().toString().isEmpty()){
                updateDailyLogs.setLogHours(Float.parseFloat(edit_TotalHours.getText().toString()));
            }else {
                updateDailyLogs.setLogHours(0);
            }


            updateDailyLogs.setRemarkType(status);

            for(int i=0;i<statusMap.size();i++){
                if (statusMap.containsKey(status)){
                    String logicName=statusMap.get(status);
                    updateDailyLogs.setRemarkCode(logicName);
                    break;
                }
            }

            if (!edit_Reason.getText().toString().isEmpty()){
                updateDailyLogs.setRemarkDesc(edit_Reason.getText().toString());
            }else {
                updateDailyLogs.setRemarkDesc("");
            }


            updateDailyLogs.setStartKms(Integer.parseInt(startKms));
            if (edit_EndKms.getText().toString().isEmpty()){
                updateDailyLogs.setEndKms(Integer.parseInt(startKms));
                updateDailyLogs.setNoOfKms(0);
                updateDailyLogs.setNoOfKmsStr("0");
            }else {
                updateDailyLogs.setEndKms(Integer.parseInt(endKms));
                updateDailyLogs.setNoOfKms(Integer.parseInt(edit_TotalKms.getText().toString().trim()));
                updateDailyLogs.setNoOfKmsStr(edit_TotalKms.getText().toString().trim());
            }

           // updateDailyLogs.setNoOfKms(Integer.parseInt(edit_TotalKms.getText().toString().trim()));
            updateDailyLogs.setFuel(Integer.parseInt(fuel));


            updateDailyLogs.setPartyName(partyName);
            updateDailyLogs.setWorkOrderNo(DailyLogSupervisorFragment.dailyLogsList.get(position).getWorkOrderNo());
            updateDailyLogs.setEditLogFLG(true);
            UpdateDailyLogsApi(updateDailyLogs);

            return;
        }

        AddDailyLog addDailyLog=new AddDailyLog();
        addDailyLog.setWorkOrderId(workOrderResponse.getWorkOrderId());
        addDailyLog.setMachineId(workOrderResponse.getMachine().getMachineId());
        addDailyLog.setPartyId(partyId);
        addDailyLog.setLogDate(logDateString);
        addDailyLog.setLogDateStr(date);

        addDailyLog.setLogStartTime(startTimeString);

        if (!edit_EndHours.getText().toString().isEmpty()){
            addDailyLog.setLogEndTime(endTimeString);

        }else {
            addDailyLog.setLogEndTime(null);
        }
        if (!edit_TotalHours.getText().toString().isEmpty()){
            addDailyLog.setLogHours(Float.parseFloat(edit_TotalHours.getText().toString()));
        }else {
            addDailyLog.setLogHours(0);
        }

        addDailyLog.setRemarkType(status);

        for(int i=0;i<statusMap.size();i++){
            if (statusMap.containsKey(status)){
                String logicName=statusMap.get(status);
                addDailyLog.setRemarkCode(logicName);
                break;
            }
        }

        if (!edit_Reason.getText().toString().isEmpty()){
            addDailyLog.setRemarkDesc(edit_Reason.getText().toString());
        }else {
            addDailyLog.setRemarkDesc("");
        }



        addDailyLog.setStartKms(Integer.parseInt(startKms));
        if (edit_EndKms.getText().toString().isEmpty()){
            addDailyLog.setEndKms(Integer.parseInt(startKms));
        }else {
            addDailyLog.setEndKms(Integer.parseInt(endKms));
        }

        /* if (!endKms.isEmpty()){
            addDailyLog.setEndKms(Integer.parseInt(endKms));
        }else {
            addDailyLog.setEndKms(0);
        }*/

        addDailyLog.setNoOfKms(Integer.parseInt(edit_TotalKms.getText().toString().trim()));
        addDailyLog.setFuel(Integer.parseInt(fuel));


        addDailyLog.setPartyName(partyName);
        addDailyLog.setWorkOrderNo(workOrderResponse.getWorkOrderNo());
        addDailyLog.setEditLogFLG(false);


        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> addDailyLogCall=apiInterface.addDailyLog("Bearer "+token,addDailyLog);
        addDailyLogCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    confirmDialogs(response.body().getMessage());
                    Constants.ACTIVITY_LOG_BACK=true;
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                           // Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                            confirmDialogs(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            //Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                            confirmDialogs(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==303){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            //Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                            confirmDialogs(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                progressDialog.dismiss();
               // Toast.makeText(getActivity(), "Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                confirmDialogs("Error:"+t.getMessage());
            }
        });
    }

    private void UpdateDailyLogsApi(UpdateDailyLogs updateDailyLogs) {
        progressDialog.show();
        String token=TokenManager.getSessionToken();
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> editDailogsCall=apiInterface.editDailyLog("Bearer "+token,updateDailyLogs);
        editDailogsCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    confirmDialogs(response.body().getMessage());
                    Constants.ACTIVITY_LOG_BACK=true;
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==303){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            } 
        });
    }

    public void calcTime() throws Exception {

        if (!edit_StartHours.getText().toString().isEmpty() && !edit_EndHours.getText().toString().isEmpty()) {
            String startTime=convertTo24HoursFormat(edit_StartHours.getText().toString());
            String endTime=convertTo24HoursFormat(edit_EndHours.getText().toString());


            Date Start = null;
            Date End = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            try {
                Start = simpleDateFormat.parse(startTime);
                End = simpleDateFormat.parse(endTime);
            } catch (ParseException e) {
                //Some thing if its not working
            }

            long difference = End.getTime() - Start.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            if (hours < 0) {
                hours += 24;
            }
            if (min < 0) {
                float newone = (float) min / 60;
                min += 60;
                hours = (int) (hours + newone);
            }
            String c = hours + "." + min;
           edit_TotalHours.setText(c);
        }
    }
    public  String convertTo24HoursFormat(String twelveHourTime) throws ParseException {
        DateFormat TWELVE_TF = new SimpleDateFormat("hh:mm aa");
        // Replace with kk:mm if you want 1-24 interval
        DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("HH:mm aa");
        return TWENTY_FOUR_TF.format(TWELVE_TF.parse(twelveHourTime));
    }

    public void calcKms() throws Exception{

        if (!edit_StartKms.getText().toString().isEmpty() && !edit_EndKms.getText().toString().isEmpty()) {
            int startKms = Integer.parseInt(edit_StartKms.getText().toString());
            int endKms = Integer.parseInt(edit_EndKms.getText().toString());
            if (startKms > endKms) {
                edit_EndKms.setText(String.valueOf(startKms + 1));
                endKms = startKms + 1;
            }
            edit_TotalKms.setText(String.valueOf(endKms - startKms));
        }else {
            edit_TotalKms.setText("0");
        }
    }


    private void callStatusApi() {
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
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

        for(Iterator<String> iter = statusResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            statusItem.add(key);
            try {
                statusMap.put(key, statusResponse.getString(key));
                if (statusResponse.getString(key).equals("RT_NORMAL")){
                    if (!Constants.DAILY_LOG_EDIT)
                    mSpinnerStatus.setText(key);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private String convertyyyyMMddTHHmmssToHHmm(String timeStamp){

        SimpleDateFormat  inputFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat  outputFormat= new SimpleDateFormat("hh:mm aa");

        String outTimeStamp=null;
        try {
            Date outDate=inputFormat.parse(timeStamp);
            outTimeStamp=outputFormat.format(outDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return outTimeStamp;
    }


    public void confirmDialogs(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    public void showSpinnerDialog(String title,ArrayList<String> listData){
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);


        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText(title);
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                mSpinnerStatus.setText(listData.get(position));
                for(int i=0;i<statusMap.size();i++){
                    if (statusMap.containsKey(listData.get(position))){
                        String logicName=statusMap.get(listData.get(position));
                        if (!logicName.equals("RTNO")){
                          edit_Reason.setText("");
                        }
                        break;
                    }
                }

            }

        };


        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),listData,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }

}
