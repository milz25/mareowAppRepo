package com.mareow.recaptchademo.MainDetailsFragment;


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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.Adapters.DailyLogsDateAdapter;
import com.mareow.recaptchademo.Adapters.SuperDailyLogsAdapter;
import com.mareow.recaptchademo.DataModels.DailyLogsSupervisor;
import com.mareow.recaptchademo.DataModels.WorkOrderResponse;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
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


public class DailyLogSupervisorFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText txtWorkOderNo;
    FloatingActionButton btnAddLogs;

    AppCompatImageView btnStatus;
    public static AppCompatImageView btnFilter;

    public static AppCompatButton btnAllDates;
    //SearchView searchView;
    RecyclerView dailyLogsDetailsRecycle;
    RecyclerView dailyLogsDateRecycle;
    DailyLogsDateAdapter dailyLogsDateAdapter;

    AppCompatTextView txtNoRecordFound;

    ProgressDialog progressDialog;
    public static List<DailyLogsSupervisor> dailyLogsList=new ArrayList<>();
    List<String> dailyLogsDates=new ArrayList<>();
    List<Integer> dailyLogsStartKms=new ArrayList<>();
    List<String> uniqueListDate=new ArrayList<>();
    public static SuperDailyLogsAdapter dailyLogsAdapter;
    int position=0;
    public static int MAX_END_KMS=0;
    public static boolean LOG_ZERO=false;

    ArrayList<String> statusItem=new ArrayList<>();
    HashMap<String,String> statusMap=new HashMap<>();


    private String workStartDate=null;
    private String workEndDate=null;
    private String woRemarks=null;

     TextInputEditText btnDialogRemarks;
     TextInputEditText btnDialogFromDate;
     TextInputEditText btnDialogToDate;
     boolean FiterStartDate=false;
    public static List<DailyLogsSupervisor> sameDayLogs=new ArrayList<>();
    public DailyLogSupervisorFragment(int position) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.navItemIndex=13;
        MainActivity.setToolbarTitle();
        View view=inflater.inflate(R.layout.fragment_daily_log_supervisor, container, false);
        initView(view);

        workStartDate=null;
        workEndDate=null;
        woRemarks=null;

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait..........");
        callDailyLogsRectrofit();
        callStatusApi();
        return view;
    }

    private void initView(View view) {

        txtWorkOderNo=(TextInputEditText) view.findViewById(R.id.super_daily_log_workno);
        btnAddLogs=(FloatingActionButton) view.findViewById(R.id.super_daily_log_add_log);
        btnAddLogs.setOnClickListener(this);
        txtNoRecordFound=(AppCompatTextView)view.findViewById(R.id.super_daily_log_no_record_found);
        btnAllDates=(AppCompatButton)view.findViewById(R.id.super_daily_log_dates_all);
        btnAllDates.setOnClickListener(this);


        btnStatus=(AppCompatImageView) view.findViewById(R.id.super_daily_log_workstatus);

        if (WorkOrderFragment.workOrderList.get(position).getWorkOrderStatus().equals("WO: Closed")){
            btnStatus.setBackgroundResource(R.drawable.wo_close_final);
        }else {
            btnStatus.setBackgroundResource(R.drawable.wo_open_final);
        }


        btnFilter=(AppCompatImageView) view.findViewById(R.id.super_daily_log_filter);
        btnFilter.setOnClickListener(this);
        //btnStatus.setText(WorkOrderFragment.workOrderList.get(position).getStatusCode());
       /// btnStatus.setKeyListener(null);

        txtWorkOderNo.setText(WorkOrderFragment.workOrderList.get(position).getWorkOrderNo());
        txtWorkOderNo.setKeyListener(null);

        if (WorkOrderFragment.workOrderList.get(position).getWorkOrderStatus().equals("WO: Closed")){
            Date today = new Date();

            String sdate=WorkOrderFragment.workOrderList.get(position).getWorkStartDate();
            String edate=WorkOrderFragment.workOrderList.get(position).getWorkEndDate();

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date sDate=null;
            Date eDate=null;

            try {
                sDate=simpleDateFormat.parse(sdate);
                eDate=simpleDateFormat.parse(edate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar=Calendar.getInstance();
            calendar.setTime(eDate);
            calendar.add(Calendar.DATE,3);
            Date checkDate=calendar.getTime();
            if (today.compareTo(sDate)>=0 && today.compareTo(checkDate)<=0){
                btnAddLogs.setImageResource(R.drawable.add_record_final);
                btnAddLogs.setEnabled(true);
            }else {
                //btnAddLogs.setImageResource(R.drawable.add_disable);
                //btnAddLogs.setEnabled(false);
                btnAddLogs.setVisibility(View.GONE);
            }
        }else {
            btnAddLogs.setImageResource(R.drawable.add_record_final);
            btnAddLogs.setEnabled(true);
        }

        //searchView=(SearchView)view.findViewById(R.id.super_daily_log_search_view);

       // searchView.setOnQueryTextListener(this);

        dailyLogsDetailsRecycle=(RecyclerView)view.findViewById(R.id.super_daily_log_recycle_details);
        dailyLogsDetailsRecycle.setHasFixedSize(false);
        dailyLogsDetailsRecycle.setItemAnimator(new DefaultItemAnimator());
        dailyLogsDetailsRecycle.setLayoutManager(new LinearLayoutManager(getContext()));


        dailyLogsDateRecycle=(RecyclerView)view.findViewById(R.id.super_daily_log_recycle_dates);
        dailyLogsDateRecycle.setHasFixedSize(false);
        dailyLogsDateRecycle.setItemAnimator(new DefaultItemAnimator());
        dailyLogsDateRecycle.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.super_daily_log_add_log:
                Constants.DAILY_LOG_EDIT=false;
                callAddActivityLogs();
                break;

            case R.id.super_daily_log_dates_all:
               // Toast.makeText(getContext(), "All", Toast.LENGTH_SHORT).show();
               // callAddActivityLogs();
                btnAllDates.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape_theme));
                btnAllDates.setTextColor(getActivity().getResources().getColor(android.R.color.white));

                dailyLogsDateAdapter=new DailyLogsDateAdapter(getActivity(),uniqueListDate);
                dailyLogsDateRecycle.setAdapter(dailyLogsDateAdapter);

                //btnFilter.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape));
                //btnFilter.setImageResource(R.drawable.filter);

                List<DailyLogsSupervisor> newList=new ArrayList<>();
                newList.addAll(dailyLogsList);
                if (dailyLogsAdapter!=null){
                    dailyLogsAdapter.updateNewList(newList);
                }
                break;
            case R.id.super_daily_log_filter:

                btnAllDates.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape));
                btnAllDates.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                dailyLogsDateAdapter=new DailyLogsDateAdapter(getActivity(),uniqueListDate);
                dailyLogsDateRecycle.setAdapter(dailyLogsDateAdapter);

                //btnFilter.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_shape_theme));
                //btnFilter.setImageResource(R.drawable.ic_new_filter_white);

                callFilterDialogs();
                break;
        }
    }
    private void callDailyLogsRectrofit() {
        String token=TokenManager.getSessionToken();
        int workOrderId=WorkOrderFragment.workOrderList.get(position).getWorkOrderId();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<DailyLogsSupervisor>> dailyLogsCall=apiInterface.getDailyLogs("Bearer "+token,workOrderId,partyId);
        dailyLogsCall.enqueue(new Callback<List<DailyLogsSupervisor>>() {
            @Override
            public void onResponse(Call<List<DailyLogsSupervisor>> call, Response<List<DailyLogsSupervisor>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                       /* String workOrderResponse = response.body().string();
                        JSONArray jsonArray=new JSONArray(workOrderResponse);
                        dailyLogsList=parseWorkOrderData.parseDailyLogsData(jsonArray);*/
                        dailyLogsList=response.body();
                        callDailyLogsRecycleAdapter();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==403){
                        Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                        txtNoRecordFound.setVisibility(View.VISIBLE);
                        dailyLogsDetailsRecycle.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<DailyLogsSupervisor>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callDailyLogsRecycleAdapter() {

        if (dailyLogsList.size()>0 && dailyLogsList.get(0).getActivityLogId()!=0){
            dailyLogsDates.clear();
            for (int i=0;i<dailyLogsList.size();i++){
                dailyLogsDates.add(dailyLogsList.get(i).getLogDate());
            }
            dailyLogsStartKms.clear();
            for (int j=0;j<dailyLogsList.size();j++){
                dailyLogsStartKms.add(dailyLogsList.get(j).getEndKms());
            }

            MAX_END_KMS=Collections.max(dailyLogsStartKms);

            LOG_ZERO=false;
            dailyLogsAdapter=new SuperDailyLogsAdapter(getActivity(),dailyLogsList);
            dailyLogsDetailsRecycle.setAdapter(dailyLogsAdapter);
            callDailyLogsAdapterDates();
        }else {
            LOG_ZERO=true;
            txtNoRecordFound.setVisibility(View.VISIBLE);
            dailyLogsDetailsRecycle.setVisibility(View.GONE);
            btnAllDates.setVisibility(View.GONE);
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
            dailyLogsDateAdapter=new DailyLogsDateAdapter(getActivity(),uniqueListDate);
            dailyLogsDateRecycle.setAdapter(dailyLogsDateAdapter);
        }
    }


    private void callAddActivityLogs() {
        Date today=new Date();
        SimpleDateFormat simpleDate=new SimpleDateFormat("yyyy-MM-dd");
        String todayString=simpleDate.format(today);
        sameDayLogs.clear();
        if (dailyLogsList.size()>0 && dailyLogsList.get(0).getActivityLogId()!=0){
            for (int i = 0; i<dailyLogsList.size(); i++){
                if (dailyLogsList.get(i).getLogDate().equals(todayString)){
                    sameDayLogs.add(dailyLogsList.get(i));
                    //sameDayLog.add(dailyLogsList.get(i));
                }
            }
        }

        for (int i=0;i<sameDayLogs.size();i++){
            if (sameDayLogs.get(i).getLogEndTimeStr()==null){
                showSnackbar( "Today's log is already exit just edit endtime.");
                return;
            }
        }
        /*if (dailyLogsSupervisor!=null){
            if (dailyLogsSupervisor.getLogEndTimeStr()==null){
                showSnackbar( "Today's log is already exit just edit endtime.");
                return;
            }else {
                FIXED_START_TIME=dailyLogsSupervisor.getLogEndTimeStr();
            }
        }*/

       /* Date Start = null;
        Date End = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            Start = simpleDateFormat.parse(startTime);
            End = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            //Some thing if its not working
        }*/

        Fragment addDailyActivityLogFragment = new AddDailyActivityLogFragment(position);

        Bundle bundle=new Bundle();
        WorkOrderResponse currentItem=WorkOrderFragment.workOrderList.get(position);
      //  WorkOrderMachine currentMachine=WorkOrderFragment.workOrderMachineList.get(position);
        bundle.putSerializable("newWorkOrder",currentItem);
       // bundle.putSerializable("newMachine",currentMachine);
        addDailyActivityLogFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, addDailyActivityLogFragment); // give your fragment container id in first parameter
        transaction.addToBackStack("DailyLogs");
        transaction.commitAllowingStateLoss();
    }


    /*@Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput=newText.toLowerCase();
        List<DailyLogsSupervisor> newList=new ArrayList<>();
        for(DailyLogsSupervisor dailyLogsSupervisor : dailyLogsList){

            if ((dailyLogsSupervisor.getLogDate()!=null) && dailyLogsSupervisor.getLogDate().toLowerCase().contains(userInput)){
                newList.add(dailyLogsSupervisor);
            }*//*else if((dailyLogsSupervisor.getLogEndTime()!=null) && dailyLogsSupervisor.getLogStartTime().equals(userInput)){
                newList.add(dailyLogsSupervisor);
            }else if((dailyLogsSupervisor.getLogEndTime()!=null) && dailyLogsSupervisor.getLogEndTime().equals(userInput)){
                newList.add(dailyLogsSupervisor);
            }else if((dailyLogsSupervisor.getStartKmsStr()!=null) && dailyLogsSupervisor.getStartKmsStr().equals(userInput)){
                newList.add(dailyLogsSupervisor);
            }else if ((dailyLogsSupervisor.getEndKmsStr()!=null) && dailyLogsSupervisor.getEndKmsStr().equals(userInput)){
                newList.add(dailyLogsSupervisor);
            }else if ((dailyLogsSupervisor.getFuelStr()!=null) && dailyLogsSupervisor.getFuelStr().equals(userInput)){
                newList.add(dailyLogsSupervisor);
            }*//*else if ((dailyLogsSupervisor.getRemarkType()!=null)&& dailyLogsSupervisor.getRemarkType().toLowerCase().contains(userInput)){
                newList.add(dailyLogsSupervisor);
            }

        }
        // ableToRunAdapter.updateList(newList);
        if (dailyLogsAdapter!=null){
            dailyLogsAdapter.updateNewList(newList);
        }

        return false;
    }
*/

    private void callFilterDialogs() {

        TextInputLayout btnRemarksHint;

        AppCompatButton btnDialogReset;
        AppCompatButton btnDialogSave;

        AppCompatImageButton btnDialogClose;

        AppCompatImageView btnDialogFromDateImage;
        AppCompatImageView btnDialogToDateImage;


        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_custom_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnRemarksHint=(TextInputLayout)dialog.findViewById(R.id.filter_dialog_statushint);
        btnRemarksHint.setHint("Remarks");

        btnDialogRemarks=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_status);
        btnDialogRemarks.setInputType(InputType.TYPE_NULL);
        if (woRemarks!=null){
            btnDialogRemarks.setText(woRemarks);
        }else {
            btnRemarksHint.setHint("Remarks");
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
                //final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

               // ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,statusItem);
              /*  CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),statusItem);
                popupWindow.setAnchorView(btnDialogRemarks);
                popupWindow.setAdapter(customListPopupWindowAdapter);
                popupWindow.setWidth(btnDialogRemarks.getMeasuredWidth());
                popupWindow.setVerticalOffset(15);
                popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));
                popupWindow.setModal(true);

                popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnDialogRemarks.setText(statusItem.get(position));
                        woRemarks=statusItem.get(position);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.show();*/
            }
        });
        btnDialogFromDate=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_fromDate);
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
       /* btnDialogFromDateImage=(AppCompatImageView)dialog.findViewById(R.id.filter_dialog_fromDateimage);
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


        btnDialogToDate=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_toDate);
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
       /* btnDialogToDateImage=(AppCompatImageView)dialog.findViewById(R.id.filter_dialog_toDateImage);
        btnDialogToDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiterStartDate=false;
                calenderDialogView();


            }
        });*/
        btnDialogReset=(AppCompatButton) dialog.findViewById(R.id.filter_dialog_resets);
        btnDialogReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dailyLogsAdapter!=null){
                    dailyLogsAdapter.updateNewList(dailyLogsList);
                }

                btnDialogRemarks.setText("Remarks");
                btnDialogFromDate.setText("From Date");
                btnDialogToDate.setText("To Date");

                woRemarks=null;
                workStartDate=null;
                workEndDate=null;
                btnFilter.setImageResource(R.drawable.filter_empty_round);
                dialog.dismiss();
            }
        });
        btnDialogSave=(AppCompatButton) dialog.findViewById(R.id.filter_dialog_save);
        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DailyLogsSupervisor> newList=new ArrayList<>();
                List<DailyLogsSupervisor> resultList = new ArrayList<>();
                newList.clear();
                resultList.clear();

                if (woRemarks==null){
                    if (workStartDate==null && workEndDate==null){
                        Toast.makeText(getActivity(), "Require search value.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (woRemarks!=null){
                    for (DailyLogsSupervisor dailyLogsSupervisor:dailyLogsList){
                        if (dailyLogsSupervisor.getRemarkType()!=null){
                            if (dailyLogsSupervisor.getRemarkType().equals(woRemarks)){
                                newList.add(dailyLogsSupervisor);
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

                    List<Date> listDate = WorkOrderFragment.getDaysBetweenDates(sDate, eDate);
                    for (int i = 0; i < listDate.size(); i++) {
                        Date btDate = listDate.get(i);
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        String newAddDateString = dateFormat1.format(btDate);
                        //searchWorkOrder.setQuery(newAddDateString,true);
                        if (newList.size()>0){
                            for (DailyLogsSupervisor dailyLogsSupervisor : newList) {
                                if (dailyLogsSupervisor.getLogDate().equals(newAddDateString)) {
                                    resultList.add(dailyLogsSupervisor);
                                }

                            }
                        }else {
                            for (DailyLogsSupervisor dailyLogsSupervisor : dailyLogsList) {
                                if (dailyLogsSupervisor.getLogDate().equals(newAddDateString)) {
                                    resultList.add(dailyLogsSupervisor);
                                }

                            }
                        }


                    }
                }

                if (workStartDate!=null && workEndDate==null){
                    String startDate=convertddMMYYYYtoYYYYMMdd(workStartDate);
                    if (newList.size()>0){
                        for (DailyLogsSupervisor dailyLogsSupervisor : newList) {
                            if (dailyLogsSupervisor.getLogDate().equals(startDate)) {
                                resultList.add(dailyLogsSupervisor);
                            }

                        }
                    }else {
                        for (DailyLogsSupervisor dailyLogsSupervisor : dailyLogsList) {
                            if (dailyLogsSupervisor.getLogDate().equals(startDate)) {
                                resultList.add(dailyLogsSupervisor);
                            }

                        }
                    }
                }

                if (workEndDate!=null && workStartDate==null){
                    String endDate=convertddMMYYYYtoYYYYMMdd(workEndDate);
                    if (newList.size()>0){
                        for (DailyLogsSupervisor dailyLogsSupervisor : newList) {
                            if (dailyLogsSupervisor.getLogDate().equals(endDate)) {
                                resultList.add(dailyLogsSupervisor);
                            }

                        }
                    }else {
                        for (DailyLogsSupervisor dailyLogsSupervisor : dailyLogsList) {
                            if (dailyLogsSupervisor.getLogDate().equals(endDate)) {
                                resultList.add(dailyLogsSupervisor);
                            }
                        }
                    }
                }

                if (newList.size()>0){
                    if (resultList.size()>0){
                        if (dailyLogsAdapter!=null){
                            Collections.reverse(resultList);
                            dailyLogsAdapter.updateNewList(resultList);
                        }
                    }else {
                        if (dailyLogsAdapter!=null){
                            dailyLogsAdapter.updateNewList(newList);
                        }
                    }
                }else if (resultList.size()>0){
                    if (dailyLogsAdapter!=null){
                        Collections.reverse(resultList);
                        dailyLogsAdapter.updateNewList(resultList);
                    }
                }else if(newList.size()==0 && resultList.size()==0){
                    if (dailyLogsAdapter!=null){
                        Collections.reverse(resultList);
                        dailyLogsAdapter.updateNewList(resultList);
                    }
                }
                dialog.dismiss();
            }
        });

        btnDialogClose=(AppCompatImageButton)dialog.findViewById(R.id.filter_dialog_close);
        btnDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout((int) (WorkOrderFragment.getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
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

        final Calendar newCalendar = Calendar.getInstance();

        newCalendar.setTime(startDate);
        long minDate=newCalendar.getTime().getTime();

        newCalendar.setTime(endDate);
        long maxDate=newCalendar.getTime().getTime();

        DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
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

                btnFilter.setImageResource(R.drawable.filter_with_value_round);

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



      /*  Calendar c = Calendar.getInstance();
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
                btnFilter.setImageResource(R.drawable.filter_with_value_round);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    private List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        snackbar.show();
    }

}
