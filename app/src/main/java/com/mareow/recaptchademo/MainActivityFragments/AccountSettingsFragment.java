package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.DataModels.AccountSetting;
import com.mareow.recaptchademo.DataModels.AccountSettingModel;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SwitchCompat mEmailSwitch;
    SwitchCompat mNotification;
    SwitchCompat   mMessage;
    SwitchCompat   mLocation;

    boolean EMAIL=false;
    boolean NOTIFICATION=false;
    boolean MESSAGE=false;
    boolean LOCATION=false;

    AppCompatButton btnSave;

     AccountSetting accountSetting;
     ProgressDialog progressDialog;

     LinearLayout ownerContainer;

     TextInputEditText edit_WO_Extenddays;
     TextInputEditText edit_WO_Canceldays;
     TextInputEditText edit_MachineListdays;

     int EXTEND_DAYS=0;
    int CANCEL_DAYS=0;
    int MACHINE_LIST_DAYS=0;


    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountSettingsFragment newInstance(String param1, String param2) {
        AccountSettingsFragment fragment = new AccountSettingsFragment();
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
        View view=inflater.inflate(R.layout.fragment_account_settings, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...........");
        }

        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Account Settings");
        }else if (Constants.USER_ROLE.equals("Owner")){
            OwnerMainActivity.txtOwnerTitle.setText("Account Settings");
        }else {
            MainActivity.txtTitle.setText("Account Settings");
        }

        getUserAccountSetting();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mEmailSwitch=(SwitchCompat)view.findViewById(R.id.account_setting_email);
        mNotification=(SwitchCompat)view.findViewById(R.id.account_setting_notification);
        mMessage=(SwitchCompat)view.findViewById(R.id.account_setting_message);
        mLocation=(SwitchCompat)view.findViewById(R.id.account_setting_location);

        btnSave=(AppCompatButton)view.findViewById(R.id.account_setting_save);

        mEmailSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                 EMAIL=true;
                }else {
                    EMAIL=false;
                }
            }
        });


        mNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    NOTIFICATION=true;
                }else {
                    NOTIFICATION =false;
                }
            }
        });

        mMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MESSAGE=true;
                }else {
                    MESSAGE=false;
                }
            }
        });

        mLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    LOCATION=true;
                }else {
                    LOCATION=false;
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccountSettingApi();
            }
        });


        ownerContainer=(LinearLayout)view.findViewById(R.id.account_setting_ownerContainer);

        if (Constants.USER_ROLE.equals("Owner")){
            ownerContainer.setVisibility(View.VISIBLE);
        }else {
            ownerContainer.setVisibility(View.GONE);
        }


        edit_WO_Extenddays=(TextInputEditText)view.findViewById(R.id.account_setting_wo_extend_days);
        edit_WO_Extenddays.setText("0");
        edit_WO_Extenddays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                EXTEND_DAYS=Integer.parseInt(s.toString());
            }
        });
        edit_WO_Canceldays=(TextInputEditText)view.findViewById(R.id.account_setting_wo_cancel_days);
        edit_WO_Canceldays.setText("0");
        edit_WO_Canceldays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
                CANCEL_DAYS=Integer.parseInt(s.toString());
            }
        });
        edit_MachineListdays=(TextInputEditText)view.findViewById(R.id.account_setting_machine_list_days);
        edit_MachineListdays.setText("0");
        edit_MachineListdays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(""))
              MACHINE_LIST_DAYS=Integer.parseInt(s.toString());
            }
        });



    }

    private void saveAccountSettingApi() {

        int userId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID,0);
        String token= TokenManager.getSessionToken();

        AccountSettingModel settingModel=new AccountSettingModel();
        settingModel.setUserId(userId);
        settingModel.setEmailFlg(EMAIL);
        settingModel.setChatFlg(MESSAGE);
        settingModel.setNotificationFlg(NOTIFICATION);
        settingModel.setLocationFlg(LOCATION);

        if (Constants.USER_ROLE.equals("Owner")){
            settingModel.setWoExtendedDay(EXTEND_DAYS);
            settingModel.setWoCancelDay(CANCEL_DAYS);
            settingModel.setMachineListDay(MACHINE_LIST_DAYS);
        }

        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> settingCall=apiInterface.saveAccountSettingForUser("Bearer "+token,settingModel);
        settingCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                }
                else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record not Found.");
                    }
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
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

    private void getUserAccountSetting() {
        int userId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID,0);
        String token= TokenManager.getSessionToken();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<AccountSetting> settingCall=apiInterface.getUserAccountingSetting("Bearer "+token,userId);
        settingCall.enqueue(new Callback<AccountSetting>() {
            @Override
            public void onResponse(Call<AccountSetting> call, Response<AccountSetting> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    accountSetting=response.body();
                    TokenManager.updateAccountSetting(accountSetting);
                    setViewValue(accountSetting);
                }
                else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record not Found.");
                    }
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountSetting> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    private void setViewValue(AccountSetting accountSetting) {

        mEmailSwitch.setChecked(accountSetting.isEmailFlg());
        mNotification.setChecked(accountSetting.isNotificationFlg());
        mMessage.setChecked(accountSetting.isChatFlg());
        mLocation.setChecked(accountSetting.isLocationFlg());

        if (Constants.USER_ROLE.equals("Owner")){

            edit_WO_Extenddays.setText(String.valueOf(accountSetting.getWoExtendedDay()));
            edit_WO_Canceldays.setText(String.valueOf(accountSetting.getWoCancelDay()));
            edit_MachineListdays.setText(String.valueOf(accountSetting.getMachineListDay()));

        }

    }
}
