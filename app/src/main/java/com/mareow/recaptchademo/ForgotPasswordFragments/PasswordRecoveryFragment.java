package com.mareow.recaptchademo.ForgotPasswordFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.textfield.TextInputEditText;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordRecoveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordRecoveryFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText edit_Email;
    AppCompatButton btnReset;
    AppCompatTextView txtMessage;

    ProgressDialog pd;



    public PasswordRecoveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordRecoveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordRecoveryFragment newInstance(String param1, String param2) {
        PasswordRecoveryFragment fragment = new PasswordRecoveryFragment();
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
        View view=inflater.inflate(R.layout.fragment_password_recovery, container, false);
        initView(view);
        pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait........");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return view;
    }

    private void initView(View view) {
        edit_Email=(TextInputEditText)view.findViewById(R.id.pass_recov_email);
        btnReset=(AppCompatButton)view.findViewById(R.id.pass_recov_btn_reset);
        txtMessage=(AppCompatTextView)view.findViewById(R.id.pass_recov_text);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.pass_recov_btn_reset:
                CheckEmailValidation();
                break;
        }
    }

    private void CheckEmailValidation() {

        String email=edit_Email.getText().toString();
        if (email.isEmpty()){
            edit_Email.setError("Enter email address");
            edit_Email.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_Email.setError("Enter valid email");
            edit_Email.requestFocus();
            return;
        }

        pd.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

        Call<ForgotPasswordResponse> call=apiInterface.getForgotPasswordLinkResponse(email);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                pd.dismiss();
                if (response.isSuccessful()){

                   //txtMessage.setText(response.body().getMessage());
                   txtMessage.setTextColor(getResources().getColor(R.color.colorPrimary));
                   callNextFragments();
               }
               else {
                   if (response.code()==401){
                       TokenExpiredUtils.tokenExpired(getActivity());
                   }else {
                       txtMessage.setText("This email is not registered,Please enter valid email.");
                   }

               }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

    private void callNextFragments() {

        Fragment forgotChangePasswordFragment = new ForgotChangePasswordFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_forgot, forgotChangePasswordFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();



    }
}
