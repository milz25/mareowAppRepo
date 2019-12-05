package com.mareow.recaptchademo.MainDetailsFragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.RenterWorkOrder;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.WorkOrderCancel;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Date;


public class WorkOrderCancelFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editWorkNo;
    TextInputEditText editWorkStartDate;
    TextInputEditText editWorkEndDate;
    TextInputEditText editCancelDate;
    AppCompatImageView btnCancelDate;

    TextInputEditText editCancelComment;

    AppCompatButton btnCancelWorkOrder;
    AppCompatCheckBox checkBoxTnCApply;

    TextInputLayout cancelHint;

    AppCompatButton btnAccept;
    AppCompatButton btnReject;

    TextInputEditText editStatusCommemt;
    TextInputLayout   hintStatusCommemt;

    LinearLayout statusSection;
    boolean TnCCheck=false;



   ProgressDialog progressDialog;
   OfferWorkOrder workOrder;

    WorkOrderCancel workOrderCancelDetails;
    public WorkOrderCancelFragment(OfferWorkOrder workOrder) {
        // Required empty public constructor
        this.workOrder=workOrder;
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
        View view=inflater.inflate(R.layout.fragment_work_order_cancel, container, false);
        initView(view);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...............");
        }

        if (workOrder.getWorkorderDTO().getCancelStatus()!=null){

            if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_REN") || workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_OWN") ){
                getWorkWorkOrderForCancel();
            }
        }


        return view;
    }



    private void initView(View view) {

        editWorkNo=(TextInputEditText)view.findViewById(R.id.work_order_cancel_workno);
        editWorkNo.setInputType(InputType.TYPE_NULL);
        editWorkNo.setText(workOrder.getWorkorderDTO().getWorkOrderNo());



        editWorkStartDate=(TextInputEditText)view.findViewById(R.id.work_order_cancel_startdate);
        editWorkStartDate.setText(Util.convertYYYYddMMtoDDmmYYYY(workOrder.getWorkorderDTO().getWorkStartDate()));
        editWorkStartDate.setInputType(InputType.TYPE_NULL);

        editWorkEndDate=(TextInputEditText)view.findViewById(R.id.work_order_cancel_enddate);
        editWorkEndDate.setText(Util.convertYYYYddMMtoDDmmYYYY(workOrder.getWorkorderDTO().getWorkEndDate()));
        editWorkEndDate.setInputType(InputType.TYPE_NULL);

        editCancelDate=(TextInputEditText)view.findViewById(R.id.work_order_cancel_cancelDate);
        editCancelDate.setInputType(InputType.TYPE_NULL);
        editCancelDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                   showDateDialog();
                }
            }
        });
        editCancelDate.setOnClickListener(this);

        editCancelComment=(TextInputEditText)view.findViewById(R.id.work_order_cancel_commment);

        btnCancelDate=(AppCompatImageView) view.findViewById(R.id.work_order_cancel_btncancelDate);
        btnCancelDate.setOnClickListener(this);

        checkBoxTnCApply=(AppCompatCheckBox) view.findViewById(R.id.work_order_cancel_TC_apply);
        checkBoxTnCApply.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    TnCCheck=true;
                }else {
                    TnCCheck=false;
                }
            }
        });

        btnCancelWorkOrder=(AppCompatButton) view.findViewById(R.id.work_order_cancel_btnCancelWorkorder);
        btnCancelWorkOrder.setOnClickListener(this);


        cancelHint=(TextInputLayout)view.findViewById(R.id.work_order_cancel_hint);
        if (Constants.USER_ROLE.equals("Renter")){
            cancelHint.setHint("Reason for Cancellation");
        }else {
            cancelHint.setHint("Cancellation Comment");
        }


        editStatusCommemt=(TextInputEditText)view.findViewById(R.id.work_order_cancel_staus_commment);
        editStatusCommemt.setVisibility(View.GONE);
        hintStatusCommemt=(TextInputLayout)view.findViewById(R.id.work_order_cancel_staus_hint);

        btnAccept=(AppCompatButton)view.findViewById(R.id.work_order_cancel_btnCancelAccept);
        btnAccept.setOnClickListener(this);
        btnReject=(AppCompatButton)view.findViewById(R.id.work_order_cancel_btnCancelReject);
        btnReject.setOnClickListener(this);


        statusSection=(LinearLayout)view.findViewById(R.id.work_order_cancel_status_section);
        statusSection.setVisibility(View.GONE);



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.work_order_cancel_cancelDate:
                showDateDialog();
                break;
            case R.id.work_order_cancel_btncancelDate:
                showDateDialog();
                break;
            case R.id.work_order_cancel_btnCancelWorkorder:
                callWorkCancelApi();
                break;
            case R.id.work_order_cancel_btnCancelAccept:
                callWorkCancelApi();
                break;
            case R.id.work_order_cancel_btnCancelReject:
                callWorkCancelRejectApi();
                break;


        }
    }




    private void showDateDialog() {
        final Calendar newCalendar = Calendar.getInstance();

        String endDateString=workOrder.getWorkorderDTO().getWorkEndDate();
        Date endDate=null;
        SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            endDate=dateFormat1.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


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

                editCancelDate.setText(dateString);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        StartTime.getDatePicker().setMaxDate(maxDate);
        StartTime .show();

    }

    private void callWorkCancelApi() {


        if (editCancelDate.getText().toString().isEmpty()){
            Snackbar.make(getView(),"Please enter Cancellation date",Snackbar.LENGTH_LONG).show();
            editCancelDate.requestFocus();
            return;
        }

        if(workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_REN")|| workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_OWN")){
            if (editStatusCommemt.getText().toString().isEmpty()){
                showSnackBar("Please enter comment");
                return;
            }
        }

        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTimeStamp=null;

        try {
            startTimeStamp=input.parse(editCancelDate.getText().toString().trim());
          } catch (ParseException e) {
            e.printStackTrace();
         }

        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String cancelDateString=outputFormat.format(startTimeStamp);

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();



        WorkOrderCancel workOrderCancel=new WorkOrderCancel();

        workOrderCancel.setWorkorderNo(workOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderCancel.setWorkOrderId(workOrder.getWorkorderDTO().getWorkOrderId());
        workOrderCancel.setWoStartDate(workOrder.getWorkorderDTO().getWorkStartDate());
        workOrderCancel.setWoEndDate(workOrder.getWorkorderDTO().getWorkEndDate());
        workOrderCancel.setWoStartDateStr(editWorkStartDate.getText().toString());
        workOrderCancel.setWoEndDateStr(editWorkEndDate.getText().toString());
        workOrderCancel.setWoCancelDate(cancelDateString);
        workOrderCancel.setWoCancelDateStr(editCancelDate.getText().toString());
        workOrderCancel.setLoginRoleLogic(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_LOGIC,null));



        if (Constants.USER_ROLE.equals("Renter")){
            workOrderCancel.setCancelStatus(Constants.RENTER_CANCEL_STATUS);
        }else if (Constants.USER_ROLE.equals("Owner")){
            workOrderCancel.setCancelStatus(Constants.OWNER_CANCEL_STATUS);
        }

        workOrderCancel.setTncflg(checkBoxTnCApply.isChecked());

        if (Constants.USER_ROLE.equals("Renter")){

            if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_OWN")){
                    workOrderCancel.setRenCancelReason(editStatusCommemt.getText().toString());
                    workOrderCancel.setOwnCancelComment(editCancelComment.getText().toString());
            }else {
                workOrderCancel.setRenCancelReason(editCancelComment.getText().toString());
            }

        }

        if (Constants.USER_ROLE.equals("Owner")){

            if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_REN")){
                workOrderCancel.setOwnCancelComment(editStatusCommemt.getText().toString());
                workOrderCancel.setRenCancelReason(editCancelComment.getText().toString());
            }else {
                workOrderCancel.setOwnCancelComment(editCancelComment.getText().toString());
            }

        }


       if (progressDialog!=null)
           progressDialog.show();


        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.cancelworkOrder("Bearer "+token,workOrderCancel);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                   showSnackBar(response.body().getMessage());
                }else {

                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            // Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                            showSnackBar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackBar(t.getMessage());
            }
        });
    }

    private void showSnackBar(String message) {

        Snackbar snackbar= Snackbar.make(getView(),message,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();

    }


    private void getWorkWorkOrderForCancel() {

        int workorderId=workOrder.getWorkorderDTO().getWorkOrderId();
        String token=TokenManager.getSessionToken();
        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<WorkOrderCancel> callOfferWorkOrder=apiInterface.getDetailsOfCancelWorkorder("Bearer "+token,workorderId);
        callOfferWorkOrder.enqueue(new Callback<WorkOrderCancel>() {
            @Override
            public void onResponse(Call<WorkOrderCancel> call, Response<WorkOrderCancel> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    workOrderCancelDetails=new WorkOrderCancel();
                    workOrderCancelDetails=response.body();
                    if (workOrderCancelDetails.getCancelStatus().equals("CANCEL_REN")){
                        if (Constants.USER_ROLE.equals("Owner")){
                            setDataInitialData();
                        }

                    }
                    if (workOrderCancelDetails.getCancelStatus().equals("CANCEL_OWN")){
                        if (Constants.USER_ROLE.equals("Renter")){
                            setDataInitialData();
                        }
                    }

                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<WorkOrderCancel> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackBar(t.getMessage());

            }
        });
    }

    private void setDataInitialData() {

        editCancelDate.setText(workOrderCancelDetails.getWoCancelDateStr());
        editCancelDate.setEnabled(false);
        btnCancelDate.setEnabled(false);

        checkBoxTnCApply.setChecked(workOrderCancelDetails.isTncflg());
        checkBoxTnCApply.setKeyListener(null);

        if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_REN") ){
            editCancelComment.setText(workOrderCancelDetails.getRenCancelReason());
            editCancelComment.setInputType(InputType.TYPE_NULL);

            hintStatusCommemt.setHint("Owner Cancellation Comment");
            editStatusCommemt.setVisibility(View.VISIBLE);

        }


        if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_OWN")){
            editCancelComment.setText(workOrderCancelDetails.getOwnCancelComment());
            editCancelComment.setInputType(InputType.TYPE_NULL);

            hintStatusCommemt.setHint("Renter Cancellation Comment");
            editStatusCommemt.setVisibility(View.VISIBLE);
        }


        btnCancelWorkOrder.setVisibility(View.GONE);
        statusSection.setVisibility(View.VISIBLE);



    }

    private void callWorkCancelRejectApi() {


        if (editCancelDate.getText().toString().isEmpty()){
            Snackbar.make(getView(),"Please enter Cancellation date",Snackbar.LENGTH_LONG).show();
            editCancelDate.requestFocus();
            return;
        }

        if(workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_REN")|| workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_OWN")){
            if (editStatusCommemt.getText().toString().isEmpty()){
                showSnackBar("Please enter comment");
                return;
            }
        }

        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTimeStamp=null;

        try {
            startTimeStamp=input.parse(editCancelDate.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String cancelDateString=outputFormat.format(startTimeStamp);

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();



        WorkOrderCancel workOrderCancel=new WorkOrderCancel();

        workOrderCancel.setWorkorderNo(workOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderCancel.setWorkOrderId(workOrder.getWorkorderDTO().getWorkOrderId());
        workOrderCancel.setWoStartDate(workOrder.getWorkorderDTO().getWorkStartDate());
        workOrderCancel.setWoEndDate(workOrder.getWorkorderDTO().getWorkEndDate());
        workOrderCancel.setWoStartDateStr(editWorkStartDate.getText().toString());
        workOrderCancel.setWoEndDateStr(editWorkEndDate.getText().toString());
        workOrderCancel.setWoCancelDate(cancelDateString);
        workOrderCancel.setWoCancelDateStr(editCancelDate.getText().toString());
        workOrderCancel.setLoginRoleLogic(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_LOGIC,null));
        workOrderCancel.setCancelStatus(Constants.REJECT_CANCEL_WO);


        /*if (Constants.USER_ROLE.equals("Renter")){
            workOrderCancel.setCancelStatus(Constants.RENTER_CANCEL_STATUS);
        }else if (Constants.USER_ROLE.equals("Owner")){
            workOrderCancel.setCancelStatus(Constants.OWNER_CANCEL_STATUS);
        }*/

        workOrderCancel.setTncflg(checkBoxTnCApply.isChecked());

        if (Constants.USER_ROLE.equals("Renter")){

            if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_OWN")){
                workOrderCancel.setRenCancelReason(editStatusCommemt.getText().toString());
            }else {
                workOrderCancel.setRenCancelReason(editCancelComment.getText().toString());
            }

        }

        if (Constants.USER_ROLE.equals("Owner")){

            if (workOrder.getWorkorderDTO().getCancelStatus().equals("CANCEL_REN")){
                workOrderCancel.setOwnCancelComment(editStatusCommemt.getText().toString());
            }else {
                workOrderCancel.setOwnCancelComment(editCancelComment.getText().toString());
            }

        }


        if (progressDialog!=null)
            progressDialog.show();


        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.cancelworkOrder("Bearer "+token,workOrderCancel);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                }else {

                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            // Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                            showSnackBar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackBar(t.getMessage());
            }
        });
    }


}
