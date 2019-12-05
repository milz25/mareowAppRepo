package com.mareow.recaptchademo.MainDetailsFragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
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
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.WorkOrderCancel;
import com.mareow.recaptchademo.DataModels.WorkOrderExtend;
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
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorkOrderExtendFragment extends Fragment implements View.OnClickListener{
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
    TextInputEditText editExtendDate;
    AppCompatImageView btnCancelDate;

    TextInputEditText editCancelComment;

    AppCompatButton btnCancelWorkOrder;
    AppCompatCheckBox checkBoxTnCApply;

    TextInputLayout cancelHint;

    boolean TnCCheck=false;

    ProgressDialog progressDialog;

    OfferWorkOrder workOrder;

    WorkOrderExtend workOrderExtend;

    AppCompatButton btnAccept;
    AppCompatButton btnReject;

    TextInputEditText editStatusCommemt;
    TextInputLayout   hintStatusCommemt;

    LinearLayout statusSection;

    public WorkOrderExtendFragment(OfferWorkOrder workOrder) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_work_order_extend, container, false);
        initView(view);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait....................");
        }

        if (Constants.USER_ROLE.equals("Owner")){

            if (workOrder.getWorkorderDTO().getExtendStatus().equals("EXTEND")){
                callgetExtendWorkOrderDetailsApi();
            }

        }
        return view;
    }



    private void initView(View view) {

        editWorkNo=(TextInputEditText)view.findViewById(R.id.work_order_extend_workno);
        editWorkNo.setInputType(InputType.TYPE_NULL);
        editWorkNo.setText(workOrder.getWorkorderDTO().getWorkOrderNo());



        editWorkStartDate=(TextInputEditText)view.findViewById(R.id.work_order_extend_startdate);
        editWorkStartDate.setText(Util.convertYYYYddMMtoDDmmYYYY(workOrder.getWorkorderDTO().getWorkStartDate()));
        editWorkStartDate.setInputType(InputType.TYPE_NULL);

        editWorkEndDate=(TextInputEditText)view.findViewById(R.id.work_order_extend_enddate);
        editWorkEndDate.setText(Util.convertYYYYddMMtoDDmmYYYY(workOrder.getWorkorderDTO().getWorkEndDate()));
        editWorkEndDate.setInputType(InputType.TYPE_NULL);

        editExtendDate=(TextInputEditText)view.findViewById(R.id.work_order_extend_cancelDate);
        editExtendDate.setInputType(InputType.TYPE_NULL);
        editExtendDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showDateDialog();
                }
            }
        });
        editExtendDate.setOnClickListener(this);

        editCancelComment=(TextInputEditText)view.findViewById(R.id.work_order_extend_comment);

        btnCancelDate=(AppCompatImageView) view.findViewById(R.id.work_order_extend_btncancelDate);
        btnCancelDate.setOnClickListener(this);

        checkBoxTnCApply=(AppCompatCheckBox) view.findViewById(R.id.work_order_extend_TC_apply);
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

        btnCancelWorkOrder=(AppCompatButton) view.findViewById(R.id.work_order_extend_btnCancelWorkorder);
        btnCancelWorkOrder.setOnClickListener(this);


        cancelHint=(TextInputLayout)view.findViewById(R.id.work_order_cancel_hint);
        if (Constants.USER_ROLE.equals("Renter")){
            cancelHint.setHint("Reason for Extension");
        }

        editStatusCommemt=(TextInputEditText)view.findViewById(R.id.work_order_extend_status_comment);
        editStatusCommemt.setVisibility(View.GONE);
        hintStatusCommemt=(TextInputLayout)view.findViewById(R.id.work_order_extend_status_hint);

        btnAccept=(AppCompatButton)view.findViewById(R.id.work_order_extend_btnextendAccept);
        btnAccept.setOnClickListener(this);
        btnReject=(AppCompatButton)view.findViewById(R.id.work_order_extend_btnextendReject);
        btnReject.setOnClickListener(this);


        statusSection=(LinearLayout)view.findViewById(R.id.work_order_extend_status_setion);
        statusSection.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.work_order_extend_cancelDate:
                showDateDialog();
                break;
            case R.id.work_order_extend_btncancelDate:
                showDateDialog();
                break;
            case R.id.work_order_extend_btnCancelWorkorder:
                callWorkExtensionApi();
                break;
            case R.id.work_order_extend_btnextendAccept:
                callWorkExtensionAcceptApi();
                break;
            case R.id.work_order_extend_btnextendReject:
                callWorkExtensionRejectApi();
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
        newCalendar.add(Calendar.DATE,1);
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

                editExtendDate.setText(dateString);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.getDatePicker().setMinDate(maxDate);
        StartTime .show();

    }

    private void callWorkExtensionApi() {
        if (editExtendDate.getText().toString().isEmpty()){
            Snackbar.make(getView(),"Please enter Extension date",Snackbar.LENGTH_LONG).show();
            editExtendDate.requestFocus();
            return;
        }
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date startTimeStamp=null;
        String extensionDateString=null;
        try {
            startTimeStamp=input.parse(editExtendDate.getText().toString().trim());
            extensionDateString=output.format(startTimeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        WorkOrderExtend workOrderExtend=new WorkOrderExtend();
        workOrderExtend.setWorkorderNo(workOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderExtend.setWorkOrderId(workOrder.getWorkorderDTO().getWorkOrderId());
        workOrderExtend.setWoStartDate(workOrder.getWorkorderDTO().getWorkStartDate());
        workOrderExtend.setWoEndDate(workOrder.getWorkorderDTO().getWorkEndDate());
        workOrderExtend.setWoStartDateStr(editWorkStartDate.getText().toString());
        workOrderExtend.setWoEndDateStr(editWorkEndDate.getText().toString());
        workOrderExtend.setWoExtendedEndDate(extensionDateString);
        workOrderExtend.setWoExtendedEndDateStr(editExtendDate.getText().toString());

        if (Constants.USER_ROLE.equals("Renter")){
            workOrderExtend.setExtendStatus(Constants.RENTER_EXTEND_STATUS);
        }
        workOrderExtend.setTncflg(checkBoxTnCApply.isChecked());

        if (Constants.USER_ROLE.equals("Renter"))
            workOrderExtend.setRenExtendReason(editCancelComment.getText().toString());


      if (progressDialog!=null)
          progressDialog.show();

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.extendworkOrder("Bearer "+token,workOrderExtend);
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
                        showSnackBar("Record Not Found");
                    }

                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.show();
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

    private void callgetExtendWorkOrderDetailsApi() {

        if (progressDialog!=null)
            progressDialog.show();

        String token=TokenManager.getSessionToken();

        int workOrderId=workOrder.getWorkorderDTO().getWorkOrderId();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<WorkOrderExtend> callOfferWorkOrder=apiInterface.getDetailsOfExtendWorkorder("Bearer "+token,workOrderId);
        callOfferWorkOrder.enqueue(new Callback<WorkOrderExtend>() {
            @Override
            public void onResponse(Call<WorkOrderExtend> call, Response<WorkOrderExtend> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    workOrderExtend=response.body();
                    setupExtendedData();
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<WorkOrderExtend> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.show();
                showSnackBar(t.getMessage());
            }
        });
    }

    private void setupExtendedData() {

        editExtendDate.setText(workOrderExtend.getWoExtendedEndDateStr());
        editExtendDate.setEnabled(false);

        btnCancelDate.setEnabled(false);

        checkBoxTnCApply.setChecked(workOrderExtend.isTncflg());
        checkBoxTnCApply.setKeyListener(null);

        editCancelComment.setText(workOrderExtend.getRenExtendReason());
        editCancelComment.setInputType(InputType.TYPE_NULL);


        hintStatusCommemt.setHint("Owner Extension Comment");
        editStatusCommemt.setVisibility(View.VISIBLE);

        btnCancelWorkOrder.setVisibility(View.GONE);
        statusSection.setVisibility(View.VISIBLE);
    }

    private void callWorkExtensionAcceptApi() {

        if (editExtendDate.getText().toString().isEmpty()){
            Snackbar.make(getView(),"Please enter extend date",Snackbar.LENGTH_LONG).show();
            editExtendDate.requestFocus();
            return;
        }

        if (Constants.USER_ROLE.equals("Owner")){
            if(workOrder.getWorkorderDTO().getExtendStatus().equals("EXTEND")){
                if (editStatusCommemt.getText().toString().isEmpty()){
                    showSnackBar("Please enter comment");
                    return;
                }
            }
        }


        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTimeStamp=null;

        try {
            startTimeStamp=input.parse(editExtendDate.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String cancelDateString=outputFormat.format(startTimeStamp);

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        WorkOrderExtend workOrderEx=new WorkOrderExtend();
        workOrderEx.setWorkorderNo(workOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderEx.setWorkOrderId(workOrder.getWorkorderDTO().getWorkOrderId());
        workOrderEx.setWoStartDate(workOrder.getWorkorderDTO().getWorkStartDate());
        workOrderEx.setWoEndDate(workOrder.getWorkorderDTO().getWorkEndDate());
        workOrderEx.setWoStartDateStr(editWorkStartDate.getText().toString());
        workOrderEx.setWoEndDateStr(editWorkEndDate.getText().toString());
        workOrderEx.setWoExtendedEndDate(cancelDateString);
        workOrderEx.setWoExtendedEndDateStr(editExtendDate.getText().toString());
        workOrderEx.setExtendStatus(Constants.OWNER_ACCEPT_EXTEND);
        workOrderEx.setRenExtendReason(workOrderExtend.getRenExtendReason());
        workOrderEx.setOwnComment(editStatusCommemt.getText().toString());


        if (progressDialog!=null)
            progressDialog.show();


        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.extendworkOrder("Bearer "+token,workOrderEx);
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

    private void callWorkExtensionRejectApi() {


        if (editExtendDate.getText().toString().isEmpty()){
            Snackbar.make(getView(),"Please enter extend  date",Snackbar.LENGTH_LONG).show();
            editExtendDate.requestFocus();
            return;
        }

        if (Constants.USER_ROLE.equals("Owner")){
            if(workOrder.getWorkorderDTO().getExtendStatus().equals("EXTEND")){
                if (editStatusCommemt.getText().toString().isEmpty()){
                    showSnackBar("Please enter comment");
                    return;
                }
            }
        }


        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTimeStamp=null;

        try {
            startTimeStamp=input.parse(editExtendDate.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String cancelDateString=outputFormat.format(startTimeStamp);

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        WorkOrderExtend workOrderEx=new WorkOrderExtend();
        workOrderEx.setWorkorderNo(workOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderEx.setWorkOrderId(workOrder.getWorkorderDTO().getWorkOrderId());
        workOrderEx.setWoStartDate(workOrder.getWorkorderDTO().getWorkStartDate());
        workOrderEx.setWoEndDate(workOrder.getWorkorderDTO().getWorkEndDate());
        workOrderEx.setWoStartDateStr(editWorkStartDate.getText().toString());
        workOrderEx.setWoEndDateStr(editWorkEndDate.getText().toString());
        workOrderEx.setWoExtendedEndDate(cancelDateString);
        workOrderEx.setWoExtendedEndDateStr(editExtendDate.getText().toString());
        workOrderEx.setExtendStatus(Constants.OWNER_REJECT_EXTEND);
        workOrderEx.setRenExtendReason(workOrderExtend.getRenExtendReason());
        workOrderEx.setOwnComment(editStatusCommemt.getText().toString());


        if (progressDialog!=null)
            progressDialog.show();

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.extendworkOrder("Bearer "+token,workOrderEx);
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
