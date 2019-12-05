package com.mareow.recaptchademo.FragmentUserDetails;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Adapters.SpinnerRecycleAdapter;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.ReferByResponse;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReferDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputEditText mReferTypeSpinner;
    TextInputEditText edit_ReferBy;
    TextInputEditText edit_Mobile;

    TextInputLayout editReferByName;
    TextInputLayout editMobileHint;

    FloatingActionButton btnSave;

   // AppCompatTextView btnRight;
  //  AppCompatTextView btnLeft;

    int singleSlected=0;
   String referType;


    public static String mRefer_Type=null;
    public static String mRefer_By=null;
    public static String mRefer_MobileNo=null;

    ArrayList<String> referTypeItems=new ArrayList<>();
    HashMap<String,String> referTypeMap=new HashMap<>();

    ArrayList<String> referByItems=new ArrayList<>();
    HashMap<String, ReferByResponse> referByMap=new HashMap<>();
    String[] referBy;

    ProgressDialog progressDialog;
    public ReferDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReferDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReferDetailsFragment newInstance(String param1, String param2) {
        ReferDetailsFragment fragment = new ReferDetailsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_refer_details, container, false);
       // MainActivity.navItemIndex=16;
        initView(view);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait.............");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        callReferTypeApi();
        //referType=new String[referTypeItems.size()];
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    private void initView(View view) {
        mReferTypeSpinner=(TextInputEditText) view.findViewById(R.id.refer_details_frg_spinner);
        mReferTypeSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showReferTypeDialog();
                }
            }
        });
        mReferTypeSpinner.setOnClickListener(this);
        editReferByName=(TextInputLayout)view.findViewById(R.id.refer_details_frg_referby_name_layout);
        edit_ReferBy=(TextInputEditText) view.findViewById(R.id.refer_details_frg_referby_name);
        edit_ReferBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (Constants.USER_ROLE.equals("Supervisor")){
                        showReferByDialog();
                    }
                }else {
                    if (!Constants.USER_ROLE.equals("Supervisor")){
                       mRefer_By=edit_ReferBy.getText().toString();
                    }
                }
            }
        });
        edit_ReferBy.setOnClickListener(this);

        edit_Mobile=(TextInputEditText)view.findViewById(R.id.refer_details_frg_mobile);
        if (Constants.USER_ROLE.equals("Supervisor")){
            edit_Mobile.setEnabled(false);
        }else {
            edit_Mobile.setEnabled(true);
        }
        if (!Constants.USER_ROLE.equals("Supervisor")){

            edit_ReferBy.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mRefer_By=s.toString();
                }
            });

            edit_Mobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mRefer_MobileNo=String.valueOf(s);
                }
            });

        }


        editMobileHint=(TextInputLayout)view.findViewById(R.id.refer_details_frg_mobile_layout);
      //  btnSave=(FloatingActionButton) view.findViewById(R.id.refer_details_frg_next);
        //btnSave.setOnClickListener(this);
/*
        btnRight=(AppCompatTextView)view.findViewById(R.id.refer_details_frg_right);
        btnRight.setOnClickListener(this);

        btnLeft=(AppCompatTextView)view.findViewById(R.id.refer_details_frg_left);
        btnLeft.setOnClickListener(this);*/

          if (Constants.MY_PROFILE){

              if (MyProfileFragment.mUSerProfileDataList.getReferBy()!=null){
                  if (mRefer_By!=null){
                      edit_ReferBy.setText(mRefer_By);
                  }else {
                      edit_ReferBy.setText(MyProfileFragment.mUSerProfileDataList.getReferBy());
                      mRefer_By=MyProfileFragment.mUSerProfileDataList.getReferBy();
                  }

              }

              if (MyProfileFragment.mUSerProfileDataList.getReferMobileNo()!=null){
                  if (mRefer_MobileNo!=null){
                      edit_Mobile.setText(mRefer_MobileNo);
                      edit_Mobile.setEnabled(false);
                  }else {
                      edit_Mobile.setText(MyProfileFragment.mUSerProfileDataList.getReferMobileNo());
                      mRefer_MobileNo=MyProfileFragment.mUSerProfileDataList.getReferMobileNo();
                  }
              }
          }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
          /*  case R.id.refer_details_frg_next:
               // Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                //callFragment();
                apiSaveCall();
                break;*/
            case R.id.refer_details_frg_referby_name:
                if (Constants.USER_ROLE.equals("Supervisor")){
                    showReferByDialog();
                }
                //callFragment();
                break;
            case R.id.refer_details_frg_spinner:
                showReferTypeDialog();
                break;

        }

    }

    private void callReferTypeApi() {
        //progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> referCall;
        if(Constants.USER_ROLE.equals("Supervisor")){
            referCall =apiInterface.getCommonLookUp(Constants.LOOK_TYPE_REFER_TYPE_SUP);
        }else {
            referCall=apiInterface.getCommonLookUp(Constants.LOOK_TYPE_REFER_TYPE);
        }
        referCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               // progressDialog.dismiss();
                if (response.isSuccessful()){
                    if (response.isSuccessful()){
                        try {
                            String referResponse = response.body().string();
                            JSONObject jsonObject=new JSONObject(referResponse);
                            parseJSONObject(jsonObject);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                        Toast.makeText(getActivity(),"No Record Available", Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
               // progressDialog.dismiss();
            }
        });
    }

    private void parseJSONObject(JSONObject referTypeResponse) {
        //referType=null;
        referTypeItems.clear();
        referTypeMap.clear();

        for(Iterator<String> iter = referTypeResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            referTypeItems.add(key);
            try {
                referTypeMap.put(key, referTypeResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (Constants.MY_PROFILE) {
            if (MyProfileFragment.mUSerProfileDataList.getReferType() != null) {
                for (String o : referTypeMap.keySet()) {
                    if (referTypeMap.get(o).equals(MyProfileFragment.mUSerProfileDataList.getReferType())) {
                        mReferTypeSpinner.setText(o);
                        mRefer_Type=MyProfileFragment.mUSerProfileDataList.getReferType();
                    }

                }
            }
        }
       // int size=referTypeItems.size();
       // referType=new String[size];
      //  referType[0]="--Refer Type--";
    //    for (int i=0;i<referTypeItems.size();i++)
          //  referType[i]=referTypeItems.get(i);

    }

    private void callUserListBaseOnRole() {
        String token= TokenManager.getSessionToken();
        String role=mReferTypeSpinner.getText().toString();
        String roleType=null;
        if (role.equals("Owner")){
            roleType="OWN";
        }else {
            roleType="REN";
        }

        //progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        final Call<ResponseBody> referCall=apiInterface.getUserListBaseOnRole("Bearer "+token,roleType);
        referCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               // progressDialog.dismiss();
                if (response.isSuccessful()){
                    if (response.isSuccessful()){
                        try {
                            String referByResponse = response.body().string();
                            JSONObject jsonObject=new JSONObject(referByResponse);
                             parseReferByJson(jsonObject);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else {
                        Toast.makeText(getActivity(),"No Record Available", Toast.LENGTH_SHORT).show();
                       // progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
               // progressDialog.dismiss();
            }
        });
    }

    private void parseReferByJson(JSONObject referByResponse) {
        referByItems.clear();
        referByMap.clear();
        referBy=null;
        for(Iterator<String> iter = referByResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            referByItems.add(key);
            try {
                JSONObject newJsonObject=referByResponse.getJSONObject(key);

                ReferByResponse referByResponseObject=new ReferByResponse();
                referByResponseObject.setMobileNo(newJsonObject.getString("mobileNo"));
                referByMap.put(key,referByResponseObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        int size=referByItems.size();
        referBy=new String[size];
        for (int i=0;i<referByItems.size();i++)
            referBy[i]=referByItems.get(i);

    }

    private void showReferByDialog() {
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText("Refer By");
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                edit_ReferBy.setText(referByItems.get(position));
                if (referByMap.containsKey(referByItems.get(position))){
                    ReferByResponse referByResponse=referByMap.get(referByItems.get(position));
                    edit_Mobile.setText(referByResponse.getMobileNo());
                    mRefer_By=referByItems.get(position);
                    mRefer_MobileNo=referByResponse.getMobileNo();
                }
            }

        };
        Collections.sort(referByItems);
        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),referByItems,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }

    private void showReferTypeDialog() {
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText("Refer Type");
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                mReferTypeSpinner.setText(referTypeItems.get(position));

                for (Map.Entry<String,String> entry : referTypeMap.entrySet()) {
                    if (entry.getKey().equals(referTypeItems.get(position))){
                        mRefer_Type=entry.getValue();
                    }
                }

             //   mRefer_Type=referTypeMap.get(referByItems.get(position));
               /* edit_ReferBy.setText("");
                edit_Mobile.setText("");
                mRefer_By=null;
                mRefer_MobileNo=null;

                editReferByName.setHint("Refer By (Name) *");
                editMobileHint.setHint("Mobile *");*/
                if (Constants.USER_ROLE.equals("Supervisor")){
                    edit_ReferBy.setText("");
                    edit_Mobile.setText("");
                    mRefer_By=null;
                    mRefer_MobileNo=null;

                    editReferByName.setHint("Refer By (Name) *");
                    editMobileHint.setHint("Mobile *");

                    callUserListBaseOnRole();
                }
            }

        };

        Collections.sort(referTypeItems);
        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(getActivity(),referTypeItems,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }




    public void apiSaveCall() {

        if (GeneralDetailsFragment.firstName==null || TextUtils.isEmpty(GeneralDetailsFragment.firstName)){
            Toast.makeText(getActivity(), "Please select firstname", Toast.LENGTH_SHORT).show();
            return;
        }

        if (GeneralDetailsFragment.lastName==null || TextUtils.isEmpty(GeneralDetailsFragment.lastName)){
            Toast.makeText(getActivity(), "Please select lastname", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Constants.USER_ROLE.equals("Operator") || Constants.USER_ROLE.equals("Renter")){
            if (GeneralDetailsFragment.segmentList.size() == 0) {
                Toast.makeText(getActivity(), "Please select Segment", Toast.LENGTH_SHORT).show();
                return;
            }
        }

         if (Constants.USER_ROLE.equals("Operator")){
             if (TextUtils.isEmpty(OperatorchargeDetailsFragment.OperatorWorkAssociation) || OperatorchargeDetailsFragment.OperatorWorkAssociation==null) {
                 Toast.makeText(getActivity(), "Please select work association", Toast.LENGTH_SHORT).show();
                 return;
             } else if (TextUtils.isEmpty(OperatorchargeDetailsFragment.operatorAmount) || OperatorchargeDetailsFragment.operatorAmount==null) {
                 Toast.makeText(getActivity(), "Please select operator amount", Toast.LENGTH_SHORT).show();
                 return;
             }

         }

         if (TextUtils.isEmpty(AddressFragment.mAddress1) || AddressFragment.mAddress1==null) {
            Toast.makeText(getActivity(), "Please select address1", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mCity) || AddressFragment.mCity==null) {
            Toast.makeText(getActivity(), "Please select city", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mPincode) || AddressFragment.mCity==null) {
            Toast.makeText(getActivity(), "Please select pincode", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mState) || AddressFragment.mState==null) {
            Toast.makeText(getActivity(), "Please select state", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mCountry) || AddressFragment.mCountry==null) {
            Toast.makeText(getActivity(), "Please select country", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(GovernmentIdFragment.govId) || GovernmentIdFragment.govId==null) {
            Toast.makeText(getActivity(), "Please select goverment id", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(GovernmentIdFragment.govProofPath) || GovernmentIdFragment.govProofPath==null) {
            Toast.makeText(getActivity(), "Please select goverment proof", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Constants.MY_PROFILE && Constants.USER_ROLE.equals("Operator") || Constants.MY_PROFILE && Constants.USER_ROLE.equals("Renter")) {
            if (BankDetailsFragment.mAccountHolder == null || TextUtils.isEmpty(BankDetailsFragment.mAccountHolder)) {
                Toast.makeText(getActivity(), "Please select account holder", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mPayableAt == null || TextUtils.isEmpty(BankDetailsFragment.mPayableAt)) {
                Toast.makeText(getActivity(), "Please select payable at", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mBank == null || BankDetailsFragment.mBank.isEmpty()) {
                Toast.makeText(getActivity(), "Please select bank name", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mAccountNo == null || TextUtils.isEmpty(BankDetailsFragment.mAccountNo)) {
                Toast.makeText(getActivity(), "Please select goverment proof", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mIFSCCode == null || TextUtils.isEmpty(BankDetailsFragment.mIFSCCode)) {
                Toast.makeText(getActivity(), "Please select bank iFSC", Toast.LENGTH_SHORT).show();
                return;
            } else if (BankDetailsFragment.mPaytmAccountNo == null || TextUtils.isEmpty(BankDetailsFragment.mPaytmAccountNo)) {
                Toast.makeText(getActivity(), "Please select paytm number", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (mRefer_Type != null) {
            if (mRefer_Type.equals("'mareow' User Refferd")) {
                referType = "UR";
            } else if (mRefer_Type.equals("Others")) {
                referType = "OTH";
            } else if (mRefer_Type.equals("Advertisment")) {
                referType = "ADDH";
            } else if (mRefer_Type.equals("Marketing Team")) {
                referType = "MT";
            } else if (mRefer_Type.equals("Sales Team")) {
                referType = "ST";
            } else if (mRefer_Type.equals("Owner")) {
                referType = "REF_OWN";
            } else if (mRefer_Type.equals("Renter")) {
                referType = "REF_REN";
            }

            if (mRefer_By==null || TextUtils.isEmpty(mRefer_By)){
                Toast.makeText(getActivity(), "Please refer by", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mRefer_MobileNo==null || TextUtils.isEmpty(mRefer_MobileNo) || mRefer_MobileNo.length()<10){
                Toast.makeText(getActivity(), "Please enter valid refer mobile", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        MultipartBody.Part uRCPart=null;
        MultipartBody.Part uMCPart=null;

        if (Constants.USER_ROLE.equals("Operator")){
            File URCFile=null;
            if (OperatorchargeDetailsFragment.certificateToRunPath != null) {

                if (OperatorchargeDetailsFragment.certificateToRunPath.startsWith("uploads/")){

                }else {
                    URCFile= new File(OperatorchargeDetailsFragment.certificateToRunPath);
                    RequestBody uRCImage = RequestBody.create(MediaType.parse("*/*"), URCFile);
                    uRCPart = MultipartBody.Part.createFormData("URC", URCFile.getName(), uRCImage);
                }

            }
            if (OperatorchargeDetailsFragment.credentialsForAgencyPath != null) {
                File UMCFile=null;
                if (OperatorchargeDetailsFragment.credentialsForAgencyPath.startsWith("uploads/")){

                }else {

                    UMCFile = new File(OperatorchargeDetailsFragment.credentialsForAgencyPath);
                    RequestBody UMCImage = RequestBody.create(MediaType.parse("*/*"), UMCFile);
                    uMCPart = MultipartBody.Part.createFormData("UMC", UMCFile.getName(), UMCImage);
                }


            }
        }

        MultipartBody.Part uGCPart = null;
        if (GovernmentIdFragment.govProofPath!=null) {
            if (GovernmentIdFragment.govProofPath.startsWith("uploads/")){

            }else {
                File UGPFile = new File(GovernmentIdFragment.govProofPath);
                RequestBody uGCImage = RequestBody.create(MediaType.parse("*/*"), UGPFile);
                if (GovernmentIdFragment.govId.equals("UPT")) {
                    uGCPart = MultipartBody.Part.createFormData("UPT", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UAD")) {
                    uGCPart = MultipartBody.Part.createFormData("UAD", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UVD")) {
                    uGCPart = MultipartBody.Part.createFormData("UVD", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UDL")) {
                    uGCPart = MultipartBody.Part.createFormData("UDL", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UPN")) {
                    uGCPart = MultipartBody.Part.createFormData("UPN", UGPFile.getName(), uGCImage);
                }
            }

        }else {
            Toast.makeText(getActivity(), "Please select Government proof", Toast.LENGTH_SHORT).show();
            return;
        }

        MultipartBody.Part uGSTPart = null;
        if (Constants.USER_ROLE.equals("Renter")){
            if (GovernmentIdFragment.gstProofDocPath!=null) {
                if (GovernmentIdFragment.gstProofDocPath.startsWith("uploads/")){

                }else {
                    File UGPFile = new File(GovernmentIdFragment.gstProofDocPath);
                    RequestBody uGCImage = RequestBody.create(MediaType.parse("*/*"), UGPFile);
                    uGSTPart = MultipartBody.Part.createFormData("UGD", UGPFile.getName(), uGCImage);
                }

            }
        }

        MultipartBody.Part uPIPart=null;
        if (Constants.USER_ROLE.equals("Operator")){
            if (OperatorHomeFragment.mAboutYourSelfPath!=null){
                File UPIfile=null;
                if (OperatorHomeFragment.mAboutYourSelfPath.startsWith("uploads/")){

                }else {
                    UPIfile = new File(OperatorHomeFragment.mAboutYourSelfPath);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }
        if (Constants.USER_ROLE.equals("Supervisor")){
            if (SupervisorHomeFragment.mAboutYourSelfPathSuper!=null){
                File UPIfile=null;
                if (SupervisorHomeFragment.mAboutYourSelfPathSuper.startsWith("uploads/")){

                }else {
                    UPIfile = new File(SupervisorHomeFragment.mAboutYourSelfPathSuper);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }

        if (Constants.USER_ROLE.equals("Renter")){
            if (RenterHomeFragment.mAboutYourSelfPathRenter!=null){
                File UPIfile=null;
                if (RenterHomeFragment.mAboutYourSelfPathRenter.startsWith("uploads/")){

                }else {
                    UPIfile = new File(RenterHomeFragment.mAboutYourSelfPathRenter);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }


        JSONObject requestBody = getRequestBody();

        RequestBody draBody = null;

        try {
            draBody = RequestBody.create(MediaType.parse("text/plain"), requestBody.toString(1));
            //Log.d(TAG, "requestUploadSurvey: RequestBody : " + requestBody.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        String token = TokenManager.getSessionToken();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> updateCall = apiInterface.updateProfileDetails("Bearer " + token, uRCPart, uMCPart, uGCPart,uGSTPart, uPIPart, draBody);
        updateCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    confirmDialogs(response.body().getMessage());
                    /*if (Constants.MY_PROFILE){
                        //callMainFragment();
                        confirmDialogs(response.body().getMessage());
                    }else {
                        Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        callMainActivity();
                    }*/
                } else {
                    if (response.code() == 401) {
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code() == 404) {
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse stm = new ForgotPasswordResponse();
                        try {
                            stm = gson.fromJson(response.errorBody().string(), ForgotPasswordResponse.class);
                            Toast.makeText(getContext(), stm.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.e("Error code:", e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private JSONObject getRequestBody() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", String.valueOf(TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID, 0)));
            jsonObject.put("firstName",GeneralDetailsFragment.firstName);
            jsonObject.put("lastName",GeneralDetailsFragment.lastName);
            jsonObject.put("userName", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USERNAME, null));
            jsonObject.put("email", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_EMAIL, null));
            jsonObject.put("mobileNo", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO, null));

            JSONArray segmentList = new JSONArray();
            for (int i = 0; i < GeneralDetailsFragment.segmentList.size(); i++) {
                segmentList.put(GeneralDetailsFragment.segmentList.get(i));
            }
            jsonObject.put("segment", segmentList);

            if (Constants.USER_ROLE.equals("Operator")){

                    jsonObject.put("association", OperatorchargeDetailsFragment.OperatorWorkAssociation);
                    jsonObject.put("userImagePath", OperatorHomeFragment.mAboutYourSelfPath);
                    jsonObject.put("runCertificatePath", OperatorchargeDetailsFragment.certificateToRunPath);
                    jsonObject.put("machineCredentialsPath", OperatorchargeDetailsFragment.credentialsForAgencyPath);


            }
            if (Constants.USER_ROLE.equals("Supervisor")){
                jsonObject.put("userImagePath",SupervisorHomeFragment.mAboutYourSelfPathSuper);
            }

            if (Constants.USER_ROLE.equals("Renter")){
                jsonObject.put("userImagePath",RenterHomeFragment.mAboutYourSelfPathRenter);
            }



            jsonObject.put("address1", AddressFragment.mAddress1);
            jsonObject.put("address2", AddressFragment.mAddress2);
            jsonObject.put("address3", AddressFragment.mAddress3);
            jsonObject.put("address4", AddressFragment.mAddress4);
            jsonObject.put("city", AddressFragment.mCity);
            jsonObject.put("postal_code", AddressFragment.mPincode);
            jsonObject.put("state", AddressFragment.mState);
            jsonObject.put("country", AddressFragment.mCountry);
            jsonObject.put("govtProofDocPath",GovernmentIdFragment.govProofPath);

            jsonObject.put("govtProofId", GovernmentIdFragment.govId);

            if (Constants.USER_ROLE.equals("Operator")){
                jsonObject.put("accountHolder", BankDetailsFragment.mAccountHolder);
                jsonObject.put("accountNo", BankDetailsFragment.mAccountNo);
                jsonObject.put("bank", BankDetailsFragment.mBank);
                jsonObject.put("ifscCode", BankDetailsFragment.mIFSCCode);
                jsonObject.put("payableAtCity", BankDetailsFragment.mPayableAt);
                jsonObject.put("paytmAccount", BankDetailsFragment.mPaytmAccountNo);
            }

            if (ReferDetailsFragment.mRefer_Type != null) {
                jsonObject.put("referType", referType);
                jsonObject.put("referMobileNo", ReferDetailsFragment.mRefer_MobileNo);
                jsonObject.put("referBy", ReferDetailsFragment.mRefer_By);
            }


            jsonObject.put("aboutYourself",GeneralDetailsFragment.mAboutYourSelf);
            if (Constants.USER_ROLE.equals("Operator")){

                jsonObject.put("attribute1", OperatorchargeDetailsFragment.AMOUNTTYPE);
                jsonObject.put("attribute2", Float.parseFloat(OperatorchargeDetailsFragment.operatorAmount));
                jsonObject.put("attribute3", OperatorchargeDetailsFragment.ACCOMODATION);
                jsonObject.put("attribute4", OperatorchargeDetailsFragment.TRASPORTATION);
                jsonObject.put("attribute5", OperatorchargeDetailsFragment.FOOD);
            }

            if (Constants.MY_PROFILE){
                jsonObject.put("editProfile",true);
            }else {
                jsonObject.put("editProfile",false);
            }

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


   public void callMainFragment(){
        MyProfileFragment myProfileFragment=new MyProfileFragment();
       FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.fragment_container_main, myProfileFragment);
       fragmentTransaction.commitAllowingStateLoss();
    }

    private void callMainActivity() {
        Intent intent=new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    public void confirmDialogs(String msg){

            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            /*LayoutInflater newinInflater=getLayoutInflater();
            View view = newinInflater.inflate(R.layout.custom_title_alert_dialog, null);
            AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.custom_title_text);
            titleText.setText("Profile");
            builder.setCustomTitle(view);*/
            //builder .setTitle("Internet")
            builder.setCancelable(false);
            builder .setMessage(msg)
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            dialog.dismiss();

                            if (Constants.MY_PROFILE){
                                callMainFragment();
                            }else {
                                callMainActivity();
                            }

                        }
                    })
                    .show();
        }

}
