package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.CustomListPopupWindowAdapter;
import com.mareow.recaptchademo.DataModels.AcceptWorkOrder;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.RenterWorkOrder;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.OwnerDrawerFragment.OwnerOfferFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.AddPaymentFragment;
import com.mareow.recaptchademo.RenterFragments.RenterOfferFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OfferAcceptanceFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextInputEditText edit_Operator;
    TextInputEditText edit_supervisor;
    TextInputEditText edit_OwnerEsign;
    TextInputEditText edit_RenterEsign;

    AppCompatImageView btnOwnerEsign;
    AppCompatImageView btnRenterEsign;
    AppCompatImageView btnAgreement;
    AppCompatButton btnOfferAccept;
    AppCompatButton btnOfferReject;


   /* AppCompatRadioButton radioRequired;
    AppCompatRadioButton radioNotRequired;*/

    AppCompatCheckBox formalAgreement;
    HashMap<String,String>  supervisorMap = new HashMap<>();
    HashMap<String,String>  operatorMap = new HashMap<>();

    ProgressDialog progressDialog;

    boolean OPERATOR_SUPERVISOR=false;
    int selectedOperatorId=0;
    int selectedSupervisorId=0;

    boolean AGREEMENT_FLAG=false;
    OfferWorkOrder offerWorkOrder;

    boolean OWNER_RENTER_SIGN=false;
    RenterWorkOrder renterWorkOrder;
    public OfferAcceptanceFragment(OfferWorkOrder offerWorkOrder, RenterWorkOrder renterWorkOrder) {
        // Required empty public constructor
        this.offerWorkOrder=offerWorkOrder;
        this.renterWorkOrder=renterWorkOrder;

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
        View view=inflater.inflate(R.layout.fragment_offer_acceptance, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait..................");
        }

        if (Constants.USER_ROLE.equals("Renter")){
            getSupervisorList();
        }

        if (Constants.USER_ROLE.equals("Owner")){
            getOperatorList();
        }
        initView(view);
        return view;
    }




    private void initView(View view) {


        edit_Operator=(TextInputEditText)view.findViewById(R.id.offer_accept_oper_super_operatorlist);
        edit_Operator.setInputType(InputType.TYPE_NULL);
        edit_Operator.setOnClickListener(this);
        edit_Operator.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               if (hasFocus){
                   if (operatorMap.size()>0){
                       OPERATOR_SUPERVISOR=true;
                       callCustomeDialog(operatorMap,0,"Operator");
                   }else {
                       showSnackBar("No Operator assoiciated with this Owner");
                   }

               }
            }
        });
        edit_supervisor=(TextInputEditText)view.findViewById(R.id.offer_accept_oper_super_supervisorList);
        edit_supervisor.setInputType(InputType.TYPE_NULL);
        edit_supervisor.setOnClickListener(this);
        edit_supervisor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (supervisorMap.size()>0){
                        OPERATOR_SUPERVISOR=false;
                        callCustomeDialog(supervisorMap,0,"Operator");
                    }else {
                        showSnackBar("No Supervisor assoiciated with this Owner");
                    }
                }

            }
        });
        edit_OwnerEsign=(TextInputEditText)view.findViewById(R.id.offer_accept_oper_super_owneresign_edit);
        edit_RenterEsign=(TextInputEditText)view.findViewById(R.id.offer_accept_oper_super_renteresign_edit);


        btnOwnerEsign=(AppCompatImageView) view.findViewById(R.id.offer_accept_oper_super_owneresign_btn);
        btnOwnerEsign.setOnClickListener(this);
        btnRenterEsign=(AppCompatImageView) view.findViewById(R.id.offer_accept_oper_super_renteresign_btn);
        btnRenterEsign.setOnClickListener(this);


        btnAgreement=(AppCompatImageView) view.findViewById(R.id.offer_accept_oper_super_agreement);
        btnOfferAccept=(AppCompatButton) view.findViewById(R.id.offer_accept_oper_super_offeraccept_btn);
        btnOfferAccept.setOnClickListener(this);
        btnOfferReject=(AppCompatButton) view.findViewById(R.id.offer_accept_oper_super_offerreject_btn);
        btnOfferReject.setOnClickListener(this);

        formalAgreement=(AppCompatCheckBox)view.findViewById(R.id.offer_accept_oper_super_agreement_checkbox);
        formalAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AGREEMENT_FLAG=true;

                    if (offerWorkOrder.getWorkorderDTO().getNoOfDays()>7){
                        showSnackBar("Work Order days are less then 7, you want to allow require agreement.");
                    }

                    if (Constants.USER_ROLE.equals("Owner")){
                        callOwnerSetting();
                    }

                    if (Constants.USER_ROLE.equals("Renter")){
                        callRenterSetting();
                    }


                }else {

                    AGREEMENT_FLAG=false;
                    if (Constants.USER_ROLE.equals("Owner")){
                        callOwnerSetting();
                    }

                    if (Constants.USER_ROLE.equals("Renter")){
                        callRenterSetting();
                    }

                }
            }
        });


        if (Constants.USER_ROLE.equals("Renter")){

            if (offerWorkOrder.getWorkorderDTO().isAgreementFlg()){
                formalAgreement.setChecked(true);
                AGREEMENT_FLAG=true;
            }else {
                formalAgreement.setChecked(false);
                AGREEMENT_FLAG=false;
            }

            formalAgreement.setEnabled(false);

            callRenterSetting();

        }

        if (Constants.USER_ROLE.equals("Owner")){

            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
                if (offerWorkOrder.getWorkorderDTO().isAgreementFlg()){
                    formalAgreement.setChecked(true);
                    AGREEMENT_FLAG=true;
                }else {
                    formalAgreement.setChecked(false);
                    AGREEMENT_FLAG=false;
                }
            }

            selectedOperatorId=offerWorkOrder.getOperatorId();
            selectedSupervisorId=offerWorkOrder.getSupervisorId();

            callOwnerSetting();

        }

        /*radioRequired=(AppCompatRadioButton)view.findViewById(R.id.offer_accept_oper_super_required);
        radioRequired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AGREEMENT_FLAG=true;
                    if (offerWorkOrder.getWorkorderDTO().getNoOfDays()>7){
                        showSnackBar("Work Order days are less then 7, you want to allow require agreement.");
                    }


                }
            }
        });
        radioNotRequired=(AppCompatRadioButton)view.findViewById(R.id.offer_accept_oper_super_notRequired);
        radioNotRequired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AGREEMENT_FLAG=false;
                }
            }
        });


        if (offerWorkOrder.getWorkorderDTO().getNoOfDays()>7){
            radioRequired.setChecked(true);
        }else {
            radioNotRequired.setChecked(true);
        }*/


        /*if (Constants.USER_ROLE.equals("Owner")){
            edit_supervisor.setEnabled(false);
            edit_Operator.setEnabled(true);

            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("OFFER")){
                edit_OwnerEsign.setEnabled(false);;
                edit_RenterEsign.setEnabled(false);

                btnOwnerEsign.setImageResource(R.drawable.esign_disable);
                btnOwnerEsign.setEnabled(false);
                btnRenterEsign.setImageResource(R.drawable.esign_disable);
                btnRenterEsign.setEnabled(false);
                btnAgreement.setEnabled(false);
                btnAgreement.setImageResource(R.drawable.agreement_disable);

            }

        }*/

        /*if (Constants.USER_ROLE.equals("Renter")){
            edit_Operator.setText(offerWorkOrder.getWorkorderDTO().getOperatorName());
            edit_Operator.setEnabled(false);

            edit_supervisor.setEnabled(true);

            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_OWN")){

               // radioRequired.setEnabled(false);
               //radioNotRequired.setEnabled(false);

                btnOwnerEsign.setImageResource(R.drawable.esign_disable);
                btnOwnerEsign.setEnabled(false);
                btnRenterEsign.setImageResource(R.drawable.esign_disable);
                btnRenterEsign.setEnabled(false);
                btnAgreement.setEnabled(false);
                btnAgreement.setImageResource(R.drawable.agreement_disable);

            }

        }*/





    }

    public void callOwnerSetting(){

            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("OFFER")){

                edit_supervisor.setEnabled(false);
                edit_Operator.setEnabled(true);

                edit_OwnerEsign.setEnabled(false);;
                edit_RenterEsign.setEnabled(false);

                btnOwnerEsign.setImageResource(R.drawable.esign_disable);
                btnOwnerEsign.setEnabled(false);
                btnRenterEsign.setImageResource(R.drawable.esign_disable);
                btnRenterEsign.setEnabled(false);

                btnAgreement.setEnabled(false);
                btnAgreement.setImageResource(R.drawable.agreement_disable);

            }else if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){

                edit_Operator.setText(offerWorkOrder.getOperatorName());
                edit_supervisor.setText(offerWorkOrder.getSupervisorName());


                selectedOperatorId=offerWorkOrder.getOperatorId();
                selectedSupervisorId=offerWorkOrder.getSupervisorId();

                edit_supervisor.setEnabled(false);
                edit_Operator.setEnabled(false);

                edit_OwnerEsign.setEnabled(false);;
                edit_RenterEsign.setEnabled(false);
                if (AGREEMENT_FLAG){
                    btnOwnerEsign.setEnabled(true);
                    btnOwnerEsign.setImageResource(R.drawable.esign_enable);
                    btnAgreement.setEnabled(true);
                    btnAgreement.setImageResource(R.drawable.agreement_final);
                }else {
                    edit_OwnerEsign.setText("");
                    edit_OwnerEsign.setEnabled(false);
                    btnOwnerEsign.setEnabled(false);
                    btnOwnerEsign.setImageResource(R.drawable.esign_disable);
                    btnAgreement.setEnabled(false);
                    btnAgreement.setImageResource(R.drawable.agreement_disable);

                }

                btnRenterEsign.setImageResource(R.drawable.esign_disable);
                btnRenterEsign.setEnabled(false);


            }

    }

    public void callRenterSetting(){

        if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_OWN")){

            edit_Operator.setText(offerWorkOrder.getWorkorderDTO().getOperatorName());
            selectedOperatorId=offerWorkOrder.getOperatorId();
            edit_Operator.setEnabled(false);

            edit_supervisor.setEnabled(true);

            edit_OwnerEsign.setEnabled(false);;
            edit_RenterEsign.setEnabled(false);

            btnOwnerEsign.setImageResource(R.drawable.esign_disable);
            btnOwnerEsign.setEnabled(false);
            btnRenterEsign.setImageResource(R.drawable.esign_disable);
            btnRenterEsign.setEnabled(false);

            btnAgreement.setEnabled(false);
            btnAgreement.setImageResource(R.drawable.agreement_disable);


        }else if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){

            edit_Operator.setText(offerWorkOrder.getOperatorName());
            edit_supervisor.setText(offerWorkOrder.getSupervisorName());

            selectedOperatorId=offerWorkOrder.getOperatorId();
            selectedSupervisorId=offerWorkOrder.getSupervisorId();


            edit_supervisor.setEnabled(false);
            edit_Operator.setEnabled(false);

            edit_OwnerEsign.setText(renterWorkOrder.getOwnerSign());
            edit_OwnerEsign.setEnabled(false);;
            edit_RenterEsign.setEnabled(false);

            btnOwnerEsign.setImageResource(R.drawable.esign_disable);
            btnOwnerEsign.setEnabled(false);

            if (AGREEMENT_FLAG){
                btnRenterEsign.setImageResource(R.drawable.esign_enable);
                btnRenterEsign.setEnabled(true);
                btnAgreement.setEnabled(true);
                btnAgreement.setImageResource(R.drawable.agreement_final);
            }else {
                btnRenterEsign.setImageResource(R.drawable.esign_disable);
                btnRenterEsign.setEnabled(false);

                btnAgreement.setEnabled(false);
                btnAgreement.setImageResource(R.drawable.agreement_disable);
            }


        }
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.offer_accept_oper_super_offeraccept_btn:
               callAcceptWorkOrderOfferAccepted();
               break;
           case R.id.offer_accept_oper_super_offerreject_btn:
               callRejectWorkOrder();
               break;

           case R.id.offer_accept_oper_super_operatorlist:
               if (operatorMap.size()>0){
                   OPERATOR_SUPERVISOR=true;
                   callCustomeDialog(operatorMap,0,"Operator");
               }else {
                   showSnackBar("No Operator assoiciated with this Owner");
               }
               break;
           case R.id.offer_accept_oper_super_supervisorList:
               if (supervisorMap.size()>0){
                   OPERATOR_SUPERVISOR=false;
                   callCustomeDialog(supervisorMap,0,"Operator");
               }else {
                   showSnackBar("No Supervisor assoiciated with this Owner");
               }
               break;

           case R.id.offer_accept_oper_super_owneresign_btn:
               OWNER_RENTER_SIGN=true;
              showeSignDialog();
               break;
           case R.id.offer_accept_oper_super_renteresign_btn:
               OWNER_RENTER_SIGN=false;
               showeSignDialog();
               break;

       }
    }


    private void callAcceptWorkOrderOfferAccepted() {

        if (Constants.USER_ROLE.equals("Owner")){
            if (selectedOperatorId==0 || edit_Operator.getText().toString().isEmpty()){
               showSnackBar("Select Operator for WorkOrder");
               return;
            }

            if (AGREEMENT_FLAG){

                if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
                    if (edit_OwnerEsign.getText().toString().isEmpty()){
                        showSnackBar("Please esignature");
                        return;
                    }

                }
            }

        }

        if (Constants.USER_ROLE.equals("Renter")){
            if (selectedSupervisorId==0 || edit_supervisor.getText().toString().isEmpty()){
                showSnackBar("Select Supervisor for WorkOrder");
                return;
            }

            if (AGREEMENT_FLAG){
                if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
                    if (edit_RenterEsign.getText().toString().isEmpty()){
                        showSnackBar("Please esignature");
                        return;
                    }
                }
            }

        }

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        AcceptWorkOrder acceptWorkOrder=new AcceptWorkOrder();
        acceptWorkOrder.setWorkOrderId(offerWorkOrder.getWorkorderDTO().getWorkOrderId());
        acceptWorkOrder.setPartyId(partyId);
        if (Constants.USER_ROLE.equals("Owner")){
            acceptWorkOrder.setOperatorId(selectedOperatorId);
            acceptWorkOrder.setSupervisorId(selectedSupervisorId);
            acceptWorkOrder.setOperatorFlg(false);

        }
        if (Constants.USER_ROLE.equals("Renter")){
            acceptWorkOrder.setOperatorId(offerWorkOrder.getWorkorderDTO().getOperatorId());
            acceptWorkOrder.setSupervisorId(selectedSupervisorId);
            acceptWorkOrder.setOperatorFlg(true);
        }

        acceptWorkOrder.setPlanId(offerWorkOrder.getWorkorderDTO().getPlanId());
        //acceptWorkOrder.setOperatorFlg();
        acceptWorkOrder.setAgreementFlg(AGREEMENT_FLAG);

        if (edit_OwnerEsign.getText().toString().isEmpty()){
            acceptWorkOrder.setOwnerSign("");
        }else {
            acceptWorkOrder.setOwnerSign(edit_OwnerEsign.getText().toString());
        }

        if (edit_RenterEsign.getText().toString().isEmpty()){
            acceptWorkOrder.setRenSign("");
        }else {
            acceptWorkOrder.setRenSign(edit_RenterEsign.getText().toString());
        }

        acceptWorkOrder.setStatus(offerWorkOrder.getWorkorderDTO().getWorkOrderStatus());

        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=null;
        if (Constants.USER_ROLE.equals("Owner")){
            callOfferWorkOrder=apiInterface.acceptworkOrderApibyOwner("Bearer "+token,acceptWorkOrder);
        }else if (Constants.USER_ROLE.equals("Renter")){
            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
                callOfferWorkOrder=apiInterface.acceptWorkOrderByRenter("Bearer "+token,acceptWorkOrder);
            }else {
                callOfferWorkOrder=apiInterface.acceptWorkOrderByRenter("Bearer "+token,acceptWorkOrder);
            }
        }

        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                    callOfferWorkOrderFragment();
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
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                showSnackBar(t.getMessage());
                if (progressDialog!=null)
                    progressDialog.dismiss();
            }
        });

    }

    private void callRejectWorkOrder() {

        String token=TokenManager.getSessionToken();

        int workorderId=offerWorkOrder.getWorkorderDTO().getWorkOrderId();

        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.rejectWorkOrderApi("Bearer "+token,workorderId);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                    callOfferWorkOrderFragment();
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
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                showSnackBar(t.getMessage());
                if (progressDialog!=null)
                    progressDialog.dismiss();
            }
        });

    }

    private void callOfferWorkOrderFragment() {

        if (Constants.USER_ROLE.equals("Renter")){

            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            Fragment fragment = new RenterOfferFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_main, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (Constants.USER_ROLE.equals("Owner")){

            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment fragment = new OwnerOfferFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_main, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }


    }

    private void showSnackBar(String message) {
        Snackbar snackbar= Snackbar.make(getActivity().getCurrentFocus(),message,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    private void getSupervisorList() {
        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> attachmentCall=apiInterface.getWorkOfferSupervisors("Bearer "+token);
        attachmentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(), "mError", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOperatorList() {

        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> attachmentCall=apiInterface.getWorkOfferOperators("Bearer "+token,offerWorkOrder.getWorkorderDTO().getWorkOrderId());
        attachmentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(), "mError", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void parseJSONObject(JSONObject jsonObject, int check) {
        HashMap<String, String> commonMap = new HashMap<>();
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            try {
                commonMap.put(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        setHashMap(commonMap, check);

    }

    public void setHashMap(HashMap<String, String> map, int check) {
        switch (check) {
            case 0:
                supervisorMap = map;
                break;
            case 1:
                operatorMap = map;
                break;
        }

    }

    private void callCustomeDialog(HashMap<String, String> hashMap, int check, String tilte) {

        ArrayList<String> listData = new ArrayList<>();
        listData.clear();

        for (String key : hashMap.keySet()) {
            listData.add(key);
        }

        Collections.sort(listData);
        showPopWindowForView(tilte,listData);

    }

    private void showPopWindowForView(String tilte, ArrayList<String> listData) {

        final ListPopupWindow popupWindow=new ListPopupWindow(getActivity());

        //  ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,StatusList);

        CustomListPopupWindowAdapter customListPopupWindowAdapter=new CustomListPopupWindowAdapter(getActivity(),listData);


        if (OPERATOR_SUPERVISOR){
            popupWindow.setAnchorView(edit_Operator);
            popupWindow.setWidth(edit_Operator.getMeasuredWidth());
        }else {
            popupWindow.setAnchorView(edit_supervisor);
            popupWindow.setWidth(edit_supervisor.getMeasuredWidth());
        }


        popupWindow.setAdapter(customListPopupWindowAdapter);
        popupWindow.setVerticalOffset(15);
        popupWindow.setModal(true);
        //popupWindow.setListSelector(getActivity().getResources().getDrawable(R.drawable.list_item));
        popupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.back_list));

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (OPERATOR_SUPERVISOR){
                  edit_Operator.setText(listData.get(position));

                  selectedOperatorId=Integer.parseInt(operatorMap.get(listData.get(position)));
                }else {
                    edit_supervisor.setText(listData.get(position));
                    selectedSupervisorId=Integer.parseInt(supervisorMap.get(listData.get(position)));
                }

                popupWindow.dismiss();
            }
        });

        popupWindow.show();
    }

    private void showeSignDialog() {

        AppCompatTextView mTitle;
        WebView mWebViewData;
        AppCompatImageButton btnClose;
        AppCompatButton btnOk;
        TextInputEditText editName;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.esign_custome_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.esign_dailog_title);
        mTitle.setText("eSignature");
        editName=(TextInputEditText)dialog.findViewById(R.id.esign_dailog_enter_name);
        mWebViewData=(WebView)dialog.findViewById(R.id.esign_dailog_webview);
        mWebViewData.getSettings().setJavaScriptEnabled(true);

        String data=getHTMLTermandConditionData();
        String justifyTag = "<html><body style='text-align:justify;'>%s</body></html>";
        String dataString = String.format(Locale.US, justifyTag,data);
        mWebViewData.loadData(dataString, "text/html", "UTF-8");


        btnOk=(AppCompatButton)dialog.findViewById(R.id.esign_dailog_Ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editName.getText().toString().isEmpty()){
                   showSnackBar("Please enter name");
                   return;
                }

                dialog.dismiss();

                if (OWNER_RENTER_SIGN){
                    edit_OwnerEsign.setText(editName.getText().toString());
                    edit_OwnerEsign.setEnabled(false);
                }else {
                    edit_RenterEsign.setText(editName.getText().toString());
                    edit_RenterEsign.setEnabled(false);
                }

            }
        });
        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.esign_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }

    private String getHTMLTermandConditionData() {
        StringBuilder html = new StringBuilder();
        try {
            AssetManager assetManager = getActivity().getAssets();

            InputStream input = assetManager.open("esign_text.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }
            br.close();
        } catch (Exception e) {
            //Handle the exception here
        }

        return html.toString();
    }



}
