package com.mareow.recaptchademo.ForgotPasswordFragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.SignupActivity;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.UserPassword;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotChangePasswordFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputEditText edit_New;
    TextInputEditText edit_Repeat;


    AppCompatButton btnReset;
    AppCompatTextView txtMessage;

    TokenManager tokenManager;
    ProgressDialog progressDialog;

    ApiInterface apiInterface;

    public ForgotChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotChangePasswordFragment newInstance(String param1, String param2) {
        ForgotChangePasswordFragment fragment = new ForgotChangePasswordFragment();
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
        View view=inflater.inflate(R.layout.fragment_forgot_change_password, container, false);
        initView(view);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait......");
        callVerifyToken();
        return view;

    }


    public void callVerifyToken(){
        SharedPreferences sharedPreferences=TokenManager.getUserDetailsPreference();
        progressDialog.show();
        Call<ForgotPasswordResponse> tokenCall=apiInterface.verifyToken(sharedPreferences.getString(Constants.PREF_KEY_USERID,null),TokenManager.getSessionToken());
        tokenCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                   Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                        return;
                    }
                    if (response.code()==403){
                        txtMessage.setText("Invalid token");
                        txtMessage.requestFocus();
                    }
                }
            }
            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void initView(View view) {
        edit_New=(TextInputEditText)view.findViewById(R.id.forgot_pass_change_new);
        edit_Repeat=(TextInputEditText)view.findViewById(R.id.forgot_pass_change_repeat);

        btnReset=(AppCompatButton)view.findViewById(R.id.forgot_pass_change_btn_reset);
        btnReset.setOnClickListener(this);
        txtMessage=(AppCompatTextView)view.findViewById(R.id.forgot_pass_change_text);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.forgot_pass_change_btn_reset:
                CallUpdatePassword();
                break;
        }
    }

    private void CallUpdatePassword() {

        String newPassword=edit_New.getText().toString();
        final String repeatPassword=edit_Repeat.getText().toString();

        if (TextUtils.isEmpty(newPassword)){
            edit_New.setError("Enter new Password");
            edit_New.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(repeatPassword)){
            edit_New.setError("Enter repeat Password");
            edit_New.requestFocus();
            return;
        }

        if (!SignupActivity.passwordCharValidation(newPassword)){
            edit_New.setError("Password must contains atleast,1 Special character,1 Number,and 1 Uppercase letter");
            edit_New.requestFocus();
            return;
        }
        if (!SignupActivity.passwordCharValidation(repeatPassword)){
            edit_Repeat.setError("Password must contains atleast,1 Special character,1 Number,and 1 Uppercase letter");
            edit_Repeat.requestFocus();
            return;
        }

        if (!newPassword.equals(repeatPassword)){
            txtMessage.setText("Password is mismatch");
        }

        UserPassword userPassword=new UserPassword();
        userPassword.setUserId(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USERID,null));
        userPassword.setPassword(newPassword);
        userPassword.setConfirmPassword(repeatPassword);

        progressDialog.show();
        Call<ForgotPasswordResponse> updatePasswordCall=apiInterface.updatePassword(userPassword);
        updatePasswordCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    txtMessage.setText(response.body().getMessage());
                    TokenExpiredUtils.tokenExpired(getActivity());
                    return;
                }
                else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                        return;
                    }
                    else {
                        txtMessage.setText("Password is mismatch");
                        showSnackbar("Password is mismatch");
                    }

                    if (response.code()==403){
                        showSnackbar("403");
                        return;
                    }

                    if (response.code()==404){
                        showSnackbar("404");
                        return;
                    }
                }
            }
            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                showSnackbar(t.getMessage());
                progressDialog.dismiss();
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
}
