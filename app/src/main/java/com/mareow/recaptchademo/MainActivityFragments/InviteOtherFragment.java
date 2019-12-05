package com.mareow.recaptchademo.MainActivityFragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.InviteOtherRequest;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InviteOtherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteOtherFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatButton btnInvite;
    AppCompatButton btnClear;

    TextInputEditText edit_Emails;
    TextInputEditText edit_Message;
    ProgressDialog progressDialog;
    String[] perms = {Manifest.permission.READ_CONTACTS};
    FloatingActionButton btnContactEmails;
    public InviteOtherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InviteOtherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InviteOtherFragment newInstance(String param1, String param2) {
        InviteOtherFragment fragment = new InviteOtherFragment();
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
        View view=inflater.inflate(R.layout.fragment_invite_other, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Invite To Join mareow");
        }else {
            MainActivity.txtTitle.setText("Invite To Join mareow");
        }
       /* if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (EasyPermissions.hasPermissions(getActivity(),perms)){

            }else {
                EasyPermissions.requestPermissions(this,"Please allow this permission for proper functionality.",10,perms);
            }
        }*/
        initView(view);
        return view;
    }

    private void initView(View view) {

        edit_Emails=(TextInputEditText)view.findViewById(R.id.invite_other_to);
        edit_Message=(TextInputEditText)view.findViewById(R.id.invite_other_message);
        btnInvite=(AppCompatButton)view.findViewById(R.id.invite_other_invite);
        btnClear=(AppCompatButton)view.findViewById(R.id.invite_other_cancel);
        btnInvite.setOnClickListener(this);
        btnClear.setOnClickListener(this);

       // btnContactEmails=(FloatingActionButton)view.findViewById(R.id.invite_other_contact_email);
       // btnContactEmails.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invite_other_invite:
                Toast.makeText(getContext(), "Send", Toast.LENGTH_SHORT).show();
                callInviteOther();
                break;
            case R.id.invite_other_cancel:
                clearViewValue();
                break;
           /* case R.id.invite_other_contact_email:
                //launchMultiplePhonePicker();
               // Intent intent=new Intent(getActivity(), ContactsActivity.class);
               // startActivity(intent);
                break;*/
        }
    }

    private void clearViewValue() {
        edit_Emails.setText("");
        edit_Message.setText("");
    }

    private void callInviteOther(){
        String emails=edit_Emails.getText().toString();
        String message=edit_Message.getText().toString();
        String[] emailParts=null;
        if (TextUtils.isEmpty(emails)){
            edit_Emails.setError("please enter email.");
            edit_Emails.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(message)){
            edit_Message.setError("This field is required.");
            edit_Message.requestFocus();
            return;
        }



        emailParts=emails.split("\\,");

        for (int i=0;i<emailParts.length;i++){
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailParts[i]).matches()){
                edit_Emails.setError("Enter valid emails");
                edit_Emails.requestFocus();
                return;
            }
        }

        String token=TokenManager.getSessionToken();
        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        List<String> emailList=new ArrayList<>();
        for (int i=0;i<emailParts.length;i++){
            emailList.add(emailParts[i]);
        }


        InviteOtherRequest inviteOtherRequest=new InviteOtherRequest();
        inviteOtherRequest.setInviteTo(emailList);
        inviteOtherRequest.setMessage(message);
        inviteOtherRequest.setPartyId(String.valueOf(partyId));
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> inviteCall=apiInterface.inviteOtherUser("Bearer "+token,inviteOtherRequest);
        inviteCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                   showSnackbar(response.body().getMessage());
                   edit_Emails.setText("");
                   edit_Message.setText("");
                }
                else {
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
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

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){

            }else {
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,10,perms)
                                .setRationale("Please allow this permission for proper functionality.")
                                .setPositiveButtonText("Ok")
                                .build());
            }
        }
    }*/


}
