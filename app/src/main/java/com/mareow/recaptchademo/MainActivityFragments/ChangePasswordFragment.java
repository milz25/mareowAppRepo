package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
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

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Activities.SignupActivity;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.Password;
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
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextInputEditText edit_New;
    TextInputEditText edit_Repeat;
    TextInputEditText edit_Old;

    AppCompatButton btnReset;
    AppCompatTextView txtMessage;

    TokenManager tokenManager;
    ProgressDialog progressDialog;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View view=inflater.inflate(R.layout.fragment_change_password, container, false);
        initView(view);
        tokenManager=new TokenManager(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait.........");
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Change Password");
        }else {
            MainActivity.txtTitle.setText("Change Password");
        }

        return view;
    }

    private void initView(View view) {
        edit_Old=(TextInputEditText)view.findViewById(R.id.pass_change_old);
        edit_New=(TextInputEditText)view.findViewById(R.id.pass_change_new);
        edit_Repeat=(TextInputEditText)view.findViewById(R.id.pass_change_repeat);

        btnReset=(AppCompatButton)view.findViewById(R.id.pass_change_btn_reset);
        btnReset.setOnClickListener(this);

        txtMessage=(AppCompatTextView)view.findViewById(R.id.pass_change_text);


    }

    private void checkOldPassword() {
        String oldPassword=edit_Old.getText().toString();

        if (!SignupActivity.passwordCharValidation(oldPassword)){
          edit_Old.setError("Password must contains atleast,1 Special character,1 Number,and 1 Uppercase letter");
          edit_Old.requestFocus();
          return;
        }
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> call=apiInterface.getResetPassword("Bearer "+ tokenManager.getSessionToken(),oldPassword);
        call.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                   callRetrofit();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }else {
                        txtMessage.setText("old Password is invalid");
                        txtMessage.requestFocus();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pass_change_btn_reset:
                checkOldPassword();
                break;
        }
    }

    private void callRetrofit() {

        String oldPassword=edit_Old.getText().toString();
        String newPassword=edit_New.getText().toString();
        final String repeatPassword=edit_Repeat.getText().toString();

        if (TextUtils.isEmpty(oldPassword)){
            edit_Old.setError("Enter old Password");
            edit_Old.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(newPassword)){
            edit_New.setError("Enter new Password");
            edit_New.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(repeatPassword)){
            edit_Old.setError("Enter repeat Password");
            edit_Old.requestFocus();
            return;
        }
        if (!newPassword.equals(repeatPassword)){
            edit_Repeat.setError("Repeat Password does not match!");
            edit_Repeat.requestFocus();
            return;
        }

        final Password password=new Password();
        password.setPassword(newPassword);
        password.setConfirmPassword(repeatPassword);

        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> updateCall=apiInterface.getUpdatePassword("Bearer "+ tokenManager.getSessionToken(),password);
                    updateCall.enqueue(new Callback<StatuTitleMessageResponse>() {
                        @Override
                        public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()){
                                showSnackbar(response.body().getMessage());
                                txtMessage.setTextColor(getResources().getColor(R.color.colorPrimary));
                                txtMessage.setText("Password has been change successfully");
                                TokenExpiredUtils.tokenExpired(getActivity());
                                return;
                            }else {
                                if (response.code()==401){
                                    TokenExpiredUtils.tokenExpired(getActivity());
                                }else {
                                    txtMessage.setText("Password is mismatch");
                                    showSnackbar("Password is mismatch");
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
}
