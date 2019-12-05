package com.mareow.recaptchademo.MainDetailsFragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.DataModels.WorkOrderResponse;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorkOrderDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    AppCompatTextView txtHeading;
    AppCompatTextView txtMachineName;
    AppCompatTextView txtLocation;
    AppCompatTextView txtActualStartDates;
    AppCompatTextView txtActualEndDates;

    AppCompatTextView txtPlanStartDates;
    AppCompatTextView txtPlanEndDates;
    AppCompatTextView txtActualNoOfDay;
    AppCompatTextView txtPlanNoOfDay;
    AppCompatTextView txtActualHours;
    AppCompatTextView txtPlanHours;

    AppCompatImageView btnStatus;


    AppCompatButton btnFeedBack;
    AppCompatButton btnDailyLogs;

    public List<WorkOrderResponse> updatedWorkOrderList=new ArrayList<WorkOrderResponse>();
    int position=0;
    ProgressDialog progressDialog;
    public WorkOrderDetailsFragment(int position) {
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
        MainActivity.navItemIndex=12;
        MainActivity.txtTitle.setText("WorkOrder");
        View view=inflater.inflate(R.layout.fragment_work_order_details, container, false);
        initView(view);

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait..........");
        }

        if (Constants.ACTIVITY_LOG_BACK==true){
            CallWorkOrderRetrofit();
        }
        closeKeyBoard();
        return view;
    }

    private void closeKeyBoard() {
        View view=getActivity().getCurrentFocus();
        if (view!=null){
            InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    private void initView(View view) {

        txtHeading=(AppCompatTextView)view.findViewById(R.id.machine_details_heading);
        txtMachineName=(AppCompatTextView)view.findViewById(R.id.machine_details_machine_name);
        txtLocation=(AppCompatTextView)view.findViewById(R.id.machine_details_location);
        txtActualStartDates=(AppCompatTextView)view.findViewById(R.id.machine_details_actual_startdate);
        txtActualEndDates=(AppCompatTextView)view.findViewById(R.id.machine_details_actual_enddate);

        txtPlanStartDates=(AppCompatTextView)view.findViewById(R.id.machine_details_plan_startdate);
        txtPlanEndDates=(AppCompatTextView)view.findViewById(R.id.machine_details_plan_enddate);

        txtActualNoOfDay=(AppCompatTextView)view.findViewById(R.id.machine_details_actual_noofdays);
        txtPlanNoOfDay=(AppCompatTextView)view.findViewById(R.id.machine_details_plane_noofdays);
        txtActualHours=(AppCompatTextView)view.findViewById(R.id.machine_details_actual_hours);
        txtPlanHours=(AppCompatTextView)view.findViewById(R.id.machine_details_plan_hours);

        btnStatus=(AppCompatImageView) view.findViewById(R.id.work_order_details_status);
        if (WorkOrderFragment.workOrderList.get(position).getWorkOrderStatus().equals("WO: Closed")){
            btnStatus.setBackgroundResource(R.drawable.wo_close_final);
        }else {
            btnStatus.setBackgroundResource(R.drawable.wo_open_final);
        }



        txtHeading.setText(WorkOrderFragment.workOrderList.get(position).getWorkOrderNo());
        txtMachineName.setText(WorkOrderFragment.workOrderList.get(position).getMachine().getCategoryName()+"/"+WorkOrderFragment.workOrderList.get(position).getMachine().getSubCategoryName()+"/"+WorkOrderFragment.workOrderList.get(position).getMachine().getModelNo()
                                +"/"+WorkOrderFragment.workOrderList.get(position).getMachine().getMachineName());
        txtLocation.setText(WorkOrderFragment.workOrderList.get(position).getSiteLocation());
        txtPlanStartDates.setText(Util.convertYYYYddMMtoDDmmYYYY(WorkOrderFragment.workOrderList.get(position).getWorkStartDate()));
        txtPlanEndDates.setText(Util.convertYYYYddMMtoDDmmYYYY(WorkOrderFragment.workOrderList.get(position).getWorkEndDate()));
        txtPlanNoOfDay.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getPlanDays()));
        txtPlanHours.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getPlanHours()));

        if (Constants.USER_ROLE.equals("Operator")){
            if(WorkOrderFragment.workOrderList.get(position).getOperatorStartDate()!=null && WorkOrderFragment.workOrderList.get(position).getOperatorEndDate()!=null){

                txtActualStartDates.setText(Util.convertYYYYddMMtoDDmmYYYY(WorkOrderFragment.workOrderList.get(position).getOperatorStartDate()));
                txtActualEndDates.setText(Util.convertYYYYddMMtoDDmmYYYY(WorkOrderFragment.workOrderList.get(position).getOperatorEndDate()));
                txtActualNoOfDay.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getOperatorDays()));
                txtActualHours.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getOperatorHours()));
            }else {
                txtActualStartDates.setText("-");
                txtActualEndDates.setText("-");
                txtActualNoOfDay.setText("-");
                txtActualHours.setText("-");
            }

        }

        if (Constants.USER_ROLE.equals("Supervisor")){
            if(WorkOrderFragment.workOrderList.get(position).getSupervisorStartDate()!=null && WorkOrderFragment.workOrderList.get(position).getSupervisorEndDate()!=null){

                txtActualStartDates.setText(Util.convertYYYYddMMtoDDmmYYYY(WorkOrderFragment.workOrderList.get(position).getSupervisorStartDate()));
                txtActualEndDates.setText(Util.convertYYYYddMMtoDDmmYYYY(WorkOrderFragment.workOrderList.get(position).getSupervisorEndDate()));
                txtActualNoOfDay.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getSupervisorDays()));
                txtActualHours.setText(String.valueOf(WorkOrderFragment.workOrderList.get(position).getSupervisorHours()));

            }else {

                txtActualStartDates.setText("-");
                txtActualEndDates.setText("-");
                txtActualNoOfDay.setText("-");
                txtActualHours.setText("-");
            }

        }

        btnFeedBack=(AppCompatButton)view.findViewById(R.id.machine_details_feedback);
        btnDailyLogs=(AppCompatButton)view.findViewById(R.id.machine_details_dailyhour);

        btnDailyLogs.setOnClickListener(this);
        btnFeedBack.setOnClickListener(this);

        if (WorkOrderFragment.workOrderList.get(position).getWorkOrderStatus().equals("WO: Closed")){
            btnFeedBack.setEnabled(true);
        }else {
            btnFeedBack.setEnabled(false);
            btnFeedBack.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.disable_button_shape));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.machine_details_feedback:
                callFeedBack();
                break;
            case R.id.machine_details_dailyhour:
                callDailyLog();
                break;
        }
    }

    private void callFeedBack() {

        //Toast.makeText(getContext(),"Feedback", Toast.LENGTH_SHORT).show();
        FeedBackFragment feedBackFragment=new FeedBackFragment(position);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, feedBackFragment); // give your fragment container id in first parameter
        transaction.addToBackStack("FeedBack");
        transaction.commitAllowingStateLoss();

    }

    private void callDailyLog() {
       // Toast.makeText(getContext(), "Daily logs", Toast.LENGTH_SHORT).show();
        Fragment dailyLogSupervisorFragment = new DailyLogSupervisorFragment(position);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, dailyLogSupervisorFragment); // give your fragment container id in first parameter
        transaction.addToBackStack("WorkOrderDetails");
        transaction.commitAllowingStateLoss();
    }


    private void CallWorkOrderRetrofit() {
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
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
                    updatedWorkOrderList.clear();
                    updatedWorkOrderList=response.body();
                    updateViewData();
                }
                else {
                    if(response.code()==401){

                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("No Record Found");
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

    private void updateViewData() {

        for (int i=0;i<updatedWorkOrderList.size();i++){

            if (WorkOrderFragment.workOrderList.get(position).getWorkOrderId()==updatedWorkOrderList.get(i).getWorkOrderId()){

                if (Constants.USER_ROLE.equals("Operator")){
                    if(updatedWorkOrderList.get(i).getOperatorStartDate()!=null && updatedWorkOrderList.get(i).getOperatorEndDate()!=null){

                        txtActualStartDates.setText(Util.convertYYYYddMMtoDDmmYYYY(updatedWorkOrderList.get(i).getOperatorStartDate()));
                        txtActualEndDates.setText(Util.convertYYYYddMMtoDDmmYYYY(updatedWorkOrderList.get(i).getOperatorEndDate()));
                        txtActualNoOfDay.setText(String.valueOf(updatedWorkOrderList.get(i).getOperatorDays()));
                        txtActualHours.setText(String.valueOf(updatedWorkOrderList.get(i).getOperatorHours()));
                    }else {
                        txtActualStartDates.setText("-");
                        txtActualEndDates.setText("-");
                        txtActualNoOfDay.setText("-");
                        txtActualHours.setText("-");
                    }

                }

                if (Constants.USER_ROLE.equals("Supervisor")){
                    if(updatedWorkOrderList.get(i).getSupervisorStartDate()!=null && updatedWorkOrderList.get(i).getSupervisorEndDate()!=null){

                        txtActualStartDates.setText(Util.convertYYYYddMMtoDDmmYYYY(updatedWorkOrderList.get(i).getSupervisorStartDate()));
                        txtActualEndDates.setText(Util.convertYYYYddMMtoDDmmYYYY(updatedWorkOrderList.get(i).getSupervisorEndDate()));
                        txtActualNoOfDay.setText(String.valueOf(updatedWorkOrderList.get(i).getSupervisorDays()));
                        txtActualHours.setText(String.valueOf(updatedWorkOrderList.get(i).getSupervisorHours()));

                    }else {

                        txtActualStartDates.setText("-");
                        txtActualEndDates.setText("-");
                        txtActualNoOfDay.setText("-");
                        txtActualHours.setText("-");
                    }

                }

                break;
            }
        }
    }


    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }






}
