package com.mareow.recaptchademo.MainActivityFragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.Adapters.SpinnerRecycleAdapter;
import com.mareow.recaptchademo.Adapters.SuperWorkOrderMainAdapter;
import com.mareow.recaptchademo.DataModels.WorkOrderMachine;
import com.mareow.recaptchademo.DataModels.WorkOrderResponse;
import com.mareow.recaptchademo.MainDetailsFragment.WorkOrderDetailsFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Date;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkOrderFragment extends Fragment implements View.OnClickListener,SearchView.OnQueryTextListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SearchView searchWorkOrder;
    AppCompatImageView btnFilter;
    RecyclerView mWorkOrderRecyclerView;
    ProgressDialog progressDialog;

    AppCompatTextView txtNoRecordFound;

    public static List<WorkOrderResponse> workOrderList=new ArrayList<WorkOrderResponse>();
    public static List<WorkOrderMachine>  workOrderMachineList=new ArrayList<WorkOrderMachine>();
    String[] workStatusList;
    ArrayList<String> StatusList=new ArrayList<>();
    HashMap<String,String> StatusMap=new HashMap<>();

    TokenManager tokenManager;
    int singleSlected=0;
    SuperWorkOrderMainAdapter adapter;

    //String[] selectionOne;
    private int startYear, startMonth, startDay, endYear, endMonth, endDay;

    String workStartDate=null;
    String workEndDate=null;
    String woStatus=null;

    boolean DateFilter=false;

    boolean FilterStartDate=false;

    TextInputEditText btnDialogFromDate;
    TextInputEditText btnDialogToDate;
    TextInputEditText btnDialogStatus;
    public WorkOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkOrderFragment newInstance(String param1, String param2) {
        WorkOrderFragment fragment = new WorkOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view=inflater.inflate(R.layout.fragment_work_order, container, false);
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Work Order");
        }else {
            MainActivity.txtTitle.setText("Work Order");
            MainActivity.navItemIndex=1;
        }

        Constants.ACTIVITY_LOG_BACK=false;
        initView(view);
        woStatus=null;
        workStartDate=null;
        workEndDate=null;
        tokenManager=new TokenManager(getActivity());
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait..........");
        }

        CallWorkOrderRetrofit();
        CallWorkOrderStatusApi();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;


    }

    private void initView(View view) {
        searchWorkOrder=(SearchView) view.findViewById(R.id.work_order_search_view);
        searchWorkOrder.setOnQueryTextListener(this);

        mWorkOrderRecyclerView=(RecyclerView)view.findViewById(R.id.work_order_super_recycle);
        mWorkOrderRecyclerView.setHasFixedSize(false);
        mWorkOrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mWorkOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnFilter=(AppCompatImageView) view.findViewById(R.id.work_order_filter);
        txtNoRecordFound=(AppCompatTextView)view.findViewById(R.id.work_order_super_no_recordd_found);
        btnFilter.setOnClickListener(this);




    }
    @Override
    public void onStart() {
        super.onStart();
        if (searchWorkOrder!=null){
            searchWorkOrder.clearFocus();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
          /*  case R.id.work_order_super_all:
                showSpinnerSingleselection();
                break;
            case R.id.work_order_super_start_date:
                Constants.WO_START_DATE=true;
                Constants.WO_END_DATE=false;
                Constants.ADDDALIYLOG=false;
                DatePickerFragment startDate=new DatePickerFragment();
                startDate.show(getFragmentManager(),"DatePicker");
                break;
            case R.id.work_order_super_end_date:
                Constants.WO_START_DATE=false;
                Constants.WO_END_DATE=true;
                Constants.ADDDALIYLOG=false;
                DatePickerFragment endDate=new DatePickerFragment();
                endDate.show(getFragmentManager(),"DatePicker");
                break;
            case R.id.work_order_super_search:
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.work_order_filter:
               /* ArrayList<String> data=new ArrayList<>();
                data.add("Date");
                data.add("Status");
                ShowSpinnerDialog(data);*/
                ShowFilterDialog();
                break;
        }
    }



    private void CallWorkOrderRetrofit() {
        int partyId=tokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        if (progressDialog!=null)
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<WorkOrderResponse>> workOrderCall=null;

        if (Constants.USER_ROLE.equals("Operator")){
            workOrderCall=apiInterface.getOperatorWorkOrder("Bearer "+ TokenManager.getSessionToken(),partyId);
        }
        if (Constants.USER_ROLE.equals("Supervisor"))
        {
            workOrderCall=apiInterface.getSupervisorWorkOrder("Bearer "+ TokenManager.getSessionToken(),partyId);
        }

        workOrderCall.enqueue(new Callback<List<WorkOrderResponse>>() {
            @Override
            public void onResponse(Call<List<WorkOrderResponse>> call, Response<List<WorkOrderResponse>> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                        workOrderList.clear();
                        workOrderMachineList.clear();
                        workOrderList=response.body();

                       // String workOrderResponse = response.body().string();
                      //  JSONArray jsonArray=new JSONArray(workOrderResponse);
                       // parseWorkOrderData.parseWorkorderDetailsData(jsonArray);
                        callMainRecycleAdapter();

                }
              else {
                    if(response.code()==401){

                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("No Record Found");
                        txtNoRecordFound.setVisibility(View.VISIBLE);
                        mWorkOrderRecyclerView.setVisibility(View.GONE);
                    }
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }

                }
            }
            @Override
            public void onFailure(Call<List<WorkOrderResponse>> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });

    }


    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    private void callMainRecycleAdapter() {
       if (workOrderList.size()>0){

           RecyclerViewClickListener listener = new RecyclerViewClickListener() {
               @Override
               public void onClick(View view, int position) {

                   //Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
                   Fragment workOrderDetailsFragment = new WorkOrderDetailsFragment(position);
                   FragmentTransaction transaction = getFragmentManager().beginTransaction();
                   transaction.replace(R.id.fragment_container_main, workOrderDetailsFragment);// give your fragment container id in first parameter
                   transaction.addToBackStack("WorkOrderMain");
                   transaction.commitAllowingStateLoss();
               }
           };
           adapter=new SuperWorkOrderMainAdapter(getActivity(),workOrderList,listener);
           mWorkOrderRecyclerView.setAdapter(adapter);
       }else {
           txtNoRecordFound.setVisibility(View.VISIBLE);
           mWorkOrderRecyclerView.setVisibility(View.GONE);
       }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput=newText.toLowerCase();
        List<WorkOrderResponse> newList=new ArrayList<>();
        for (WorkOrderResponse workOrderResponse:workOrderList){
            if (DateFilter){
                if (workOrderResponse.getWorkStartDate().equals(newText)){
                    newList.add(workOrderResponse);
                }
            }else {

                if (workOrderResponse.getWorkOrderNo().toLowerCase().contains(userInput)){
                    newList.add(workOrderResponse);
                }else if (workOrderResponse.getWorkOrderStatus().toLowerCase().contains(userInput)){
                    newList.add(workOrderResponse);
                }
            }
        }
        DateFilter=false;
        if (adapter!=null){
            adapter.updateList(newList);
        }
        return false;
    }


    private void showSpinnerSingleselection() {
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText("Status");
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                // btnFilter.setText(listData.get(position));
                if (StatusList.get(position).equals("All")){
                    searchWorkOrder.setQuery("",true);
                }else {
                    searchWorkOrder.setQuery(StatusList.get(position),true);
                }

            }

        };
        Collections.sort(StatusList);
        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),StatusList,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }


    private void ShowSpinnerDialog(final ArrayList<String> listData){
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText("Filter");
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
               // btnFilter.setText(listData.get(position));
                if (listData.get(position).equals("Status")){
                    showSpinnerSingleselection();
                }
                if (listData.get(position).equals("Date")){
                    showDatePicker();
                }

            }

        };
        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),listData,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }

    public void showDatePicker() {
        // Inflate your custom layout containing 2 DatePickers
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_date_picker, null);

        // Define your date pickers
        final DatePicker dpStartDate = (DatePicker) customView.findViewById(R.id.dpStartDate);
       // final DatePicker dpEndDate = (DatePicker) customView.findViewById(R.id.dpEndDate);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(customView); // Set the view of the dialog to your custom layout
       // builder.setTitle("Start Date");
        //LayoutInflater inflater1=getLayoutInflater();
       // View view = inflater1.inflate(R.layout.custom_title_alert_dialog, null);
       // AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.custom_title_text);
       // titleText.setText("Start Date");
        //builder.setCustomTitle(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startYear = dpStartDate.getYear();
                startMonth = dpStartDate.getMonth()+1;
                startDay = dpStartDate.getDayOfMonth();
               // endYear = dpEndDate.getYear();
              //  endMonth = dpEndDate.getMonth()+1;
               // endDay = dpEndDate.getDayOfMonth();

                if(startMonth<10){
                    workStartDate=startYear+"-"+"0"+startMonth+"-"+startDay;
                }else {
                    workStartDate=startYear+"-"+startMonth+"-"+startDay;
                }

              /*  if (endMonth<10){
                    workEndDate=endYear+"-"+"0"+endMonth+"-"+endDay;
                }else {
                    workEndDate=endYear+"-"+endMonth+"-"+endDay;
                }*/
                DateFilter=true;
              //  searchWorkOrder.setQuery(workStartDate,true);
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date sDate=null;
                //Date eDate=null;
                try {
                    sDate=dateFormat.parse(workStartDate);
                  //  eDate=dateFormat.parse(workEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
               // List<Date> listDate=getDaysBetweenDates(sDate,eDate);
               // List<WorkOrderResponse> newList=new ArrayList<>();
               // for(int i=0;i<listDate.size();i++){
                   // Date btDate=listDate.get(i);
                   SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd");
                   String newAddDateString=dateFormat1.format(sDate);
                     searchWorkOrder.setQuery(newAddDateString,true);
                  /*  for (WorkOrderResponse workOrderResponse:workOrderList){
                        if (workOrderResponse.getWorkStartDate().equals(workStartDate) *//*|| workOrderResponse.getWorkEndDate().equals(newAddDateString)*//*){
                            newList.add(workOrderResponse);
                        }

                    }

               // }
                if (adapter!=null){
                    adapter.updateList(newList);
                }*/
                //searchWorkOrder.setQuery(workStartDate+" "+workEndDate,true);
                dialog.dismiss();
            }});

        // Create and show the dialog
        builder.create().show();
    }


    private void CallWorkOrderStatusApi() {
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        progressDialog.show();
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> woStatusCall=null;
        if (Constants.USER_ROLE.equals("Operator") ||Constants.USER_ROLE.equals("Supervisor") ){
            woStatusCall=apiInterface.getWorkOrderStatusByRole(partyId);
        }else {
            woStatusCall=apiInterface.getWorkOrderStatus();
        }

        woStatusCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                    if (response.isSuccessful()){
                        try {
                            String roleResponse = response.body().string();
                            JSONObject jsonObject=new JSONObject(roleResponse);
                            parseJSONObject(jsonObject);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                       // showSnackbar(response.message());
                        progressDialog.dismiss();
                    }

                 }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               progressDialog.dismiss();
               showSnackbar(t.getMessage());
            }
        });
    }

    private void parseJSONObject(JSONObject woResponse) {
        StatusList.clear();
        StatusMap.clear();
        //StatusList.add("All");
        for(Iterator<String> iter = woResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            StatusList.add(key);
            try {
                StatusMap.put(key, woResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       /* int size=StatusList.size();
        workStatusList=new String[size];
        workStatusList[0]="All";
        for (int i=1;i<=StatusList.size();i++){
            workStatusList[i]=StatusList.get(i);
        }*/
    }


    public void  ShowFilterDialog(){

        //RecyclerView spinnerRecycle;
       // AppCompatTextView titleText;
        //final TextInputEditText btnDialogStatus;
        //final TextInputEditText btnDialogFromDate;
       // final TextInputEditText btnDialogToDate;

        AppCompatImageView btnImageFromDate;
        AppCompatImageView btnImageToDate;

        final TextInputLayout btnStatusHint;

        AppCompatButton btnDialogReset;
        AppCompatButton btnDialogSave;

        AppCompatImageButton btnDialogClose;

       /* workStartDate=null;
        workEndDate=null;
        woStatus=null;*/

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_custom_layout);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnStatusHint=(TextInputLayout)dialog.findViewById(R.id.filter_dialog_statushint);

        btnDialogStatus=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_status);
        btnDialogStatus.setInputType(InputType.TYPE_NULL);
        if (woStatus!=null){
            btnDialogStatus.setText(woStatus);
        }else {
            btnStatusHint.setHint("Status");
        }
        btnDialogStatus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    callPopwindow();
                }
            }
        });

        btnDialogStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopwindow();

            }
        });


        btnDialogFromDate=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_fromDate);
        btnDialogFromDate.setInputType(InputType.TYPE_NULL);
        btnDialogFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterStartDate=true;
                calenderDialogView();
            }
        });
        btnDialogFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                   FilterStartDate=true;
                   calenderDialogView();
                }
            }
        });

        if (workStartDate!=null){
            btnDialogFromDate.setText(workStartDate);
            btnDialogFromDate.setTextColor(getActivity().getResources().getColor(android.R.color.black));
        }

        btnDialogToDate=(TextInputEditText) dialog.findViewById(R.id.filter_dialog_toDate);
        btnDialogToDate.setInputType(InputType.TYPE_NULL);
        btnDialogToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterStartDate=false;
                calenderDialogView();
            }
        });

        btnDialogToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    FilterStartDate=false;
                    calenderDialogView();
                }
            }
        });
        if (workEndDate!=null){
            btnDialogToDate.setText(workEndDate);
            btnDialogToDate.setTextColor(getActivity().getResources().getColor(android.R.color.black));
        }
        /*btnImageToDate=(AppCompatImageView)dialog.findViewById(R.id.filter_dialog_toDateImage);
        btnImageToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FilterStartDate=false;
                calenderDialogView();

            }
        });*/
       /* btnDialogToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
        btnDialogReset=(AppCompatButton) dialog.findViewById(R.id.filter_dialog_resets);
        btnDialogReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter!=null){
                    adapter.updateList(workOrderList);
                }

                btnDialogStatus.setText("Status");
                btnDialogFromDate.setText("From Date");
                btnDialogToDate.setText("To Date");

                woStatus=null;
                workStartDate=null;
                workEndDate=null;

                btnFilter.setImageResource(R.drawable.filter_empty);

                dialog.dismiss();
            }

        });

        btnDialogSave=(AppCompatButton) dialog.findViewById(R.id.filter_dialog_save);
        btnDialogSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (woStatus==null){
                    if (workStartDate==null && workEndDate==null){
                        Toast.makeText(getActivity(), "Require search value.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                List<WorkOrderResponse> newList=new ArrayList<>();
                List<WorkOrderResponse> resultList = new ArrayList<>();
                newList.clear();
                resultList.clear();
                if (woStatus!=null){
                      for (WorkOrderResponse workOrderResponse:workOrderList){
                         if (workOrderResponse.getWorkOrderStatus().equals(woStatus)){
                                newList.add(workOrderResponse);
                            }
                         }

                     }
                if (workStartDate!=null && workEndDate!=null) {

                    String startDate=convertddMMYYYYtoYYYYMMdd(workStartDate);
                    String endDate=convertddMMYYYYtoYYYYMMdd(workEndDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date sDate = null;
                    //Date eDate=null;
                    try {
                        sDate = dateFormat.parse(startDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date eDate = null;
                    try {
                        eDate = dateFormat.parse(endDate);
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
                            for (WorkOrderResponse workOrderResponse : newList) {
                                if (workOrderResponse.getWorkStartDate().equals(newAddDateString) || workOrderResponse.getWorkEndDate().equals(newAddDateString)) {
                                    if (!resultList.contains(workOrderResponse))
                                        resultList.add(workOrderResponse);
                                }

                            }
                        }else {
                            for (WorkOrderResponse workOrderResponse : workOrderList) {
                                if (workOrderResponse.getWorkStartDate().equals(newAddDateString) || workOrderResponse.getWorkEndDate().equals(newAddDateString)) {
                                    if (!resultList.contains(workOrderResponse))
                                    resultList.add(workOrderResponse);
                                }

                            }
                        }


                    }
                }
                if (workStartDate!=null && workEndDate==null){
                    String startDate=convertddMMYYYYtoYYYYMMdd(workStartDate);
                    if (newList.size()>0){
                        for (WorkOrderResponse workOrderResponse : newList) {
                            if (workOrderResponse.getWorkStartDate().equals(startDate) || workOrderResponse.getWorkEndDate().equals(startDate)) {
                                if (!resultList.contains(workOrderResponse))
                                resultList.add(workOrderResponse);
                            }

                        }
                    }else {
                        for (WorkOrderResponse workOrderResponse : workOrderList) {
                            if (workOrderResponse.getWorkStartDate().equals(startDate) || workOrderResponse.getWorkEndDate().equals(startDate)) {
                                if (!resultList.contains(workOrderResponse))
                                  resultList.add(workOrderResponse);
                            }

                        }
                    }
                }

                if (workEndDate!=null && workStartDate==null){
                    String endDate=convertddMMYYYYtoYYYYMMdd(workEndDate);
                    if (newList.size()>0){
                        for (WorkOrderResponse workOrderResponse : newList) {
                            if (workOrderResponse.getWorkStartDate().equals(endDate) || workOrderResponse.getWorkEndDate().equals(endDate)) {
                                if (!resultList.contains(workOrderResponse))
                                resultList.add(workOrderResponse);
                            }

                        }
                    }else {
                        for (WorkOrderResponse workOrderResponse : workOrderList) {
                            if (workOrderResponse.getWorkStartDate().equals(endDate) || workOrderResponse.getWorkEndDate().equals(endDate)) {
                                if (!resultList.contains(workOrderResponse))
                                resultList.add(workOrderResponse);
                            }
                        }
                    }
                }

                if (newList.size()>0){
                    if (resultList.size()>0){
                        if (adapter!=null){
                            adapter.updateList(resultList);
                        }
                    }else {
                        if (adapter!=null){
                            adapter.updateList(newList);
                        }
                    }
                }else if (resultList.size()>0){
                    if (adapter!=null){
                        adapter.updateList(resultList);
                    }
                }else if(newList.size()==0 && resultList.size()==0){
                    if (adapter!=null){
                        adapter.updateList(newList);
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


        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

       public static int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
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
       /* for(int i=0;i<dates.size();i++){
            Date lDate =(Date)dates.get(i);
            Log.d("LogDate",lDate.toString());
        }*/

        return dates;
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

        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog  StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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

                if (FilterStartDate){

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

                btnFilter.setImageResource(R.drawable.filter_with_value);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        StartTime .show();
    }


    public void callPopwindow(){
        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),StatusList);
        popupWindow.setAnchorView(btnDialogStatus);
        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setWidth(btnDialogStatus.getMeasuredWidth());
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnDialogStatus.setText(StatusList.get(position));
                woStatus=StatusList.get(position);
                btnFilter.setImageResource(R.drawable.filter_with_value);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

}
