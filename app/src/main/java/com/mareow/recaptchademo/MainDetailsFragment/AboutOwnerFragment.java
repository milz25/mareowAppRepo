package com.mareow.recaptchademo.MainDetailsFragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.DataModels.CreateFeedback;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutOwnerFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatRatingBar ratingBarEfficiency;
    AppCompatRatingBar ratingBarQueryResponse;
    AppCompatRatingBar ratingBarWork_Attitude;
    AppCompatRatingBar ratingBarworkCommitment;

    TextInputEditText edit_UserComment;
    FloatingActionButton btnSave;

    int efficiency= 0;
    int queryResponse= 0;
    int workAttitude= 0;
    int workCommitment=0;

    ProgressDialog progressDialog;

    int position=0;
    public AboutOwnerFragment(int positoon) {
        // Required empty public constructor
        this.position=positoon;
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
        View view=inflater.inflate(R.layout.fragment_about_owner, container, false);
        initView(view);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait.........");
        }

        return view;
    }

    private void initView(View view) {

        ratingBarworkCommitment=(AppCompatRatingBar)view.findViewById(R.id.about_owner_rating_one);
        ratingBarQueryResponse=(AppCompatRatingBar)view.findViewById(R.id.about_owner_rating_two);
        ratingBarEfficiency=(AppCompatRatingBar)view.findViewById(R.id.about_owner_rating_three);
        ratingBarWork_Attitude=(AppCompatRatingBar)view.findViewById(R.id.about_owner_rating_four);

        ratingBarworkCommitment.setOnClickListener(this);
        ratingBarQueryResponse.setOnClickListener(this);
        ratingBarEfficiency.setOnClickListener(this);
        ratingBarWork_Attitude.setOnClickListener(this);



        edit_UserComment=(TextInputEditText) view.findViewById(R.id.about_owner_user_comment);
        btnSave=(FloatingActionButton) view.findViewById(R.id.about_owner_save);
        btnSave.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.about_owner_rating_one:
                AppCompatRatingBar bar = (AppCompatRatingBar) v;
                workCommitment=(int)bar.getRating();
                break;
            case R.id.about_owner_rating_two:
                AppCompatRatingBar bar2 = (AppCompatRatingBar) v;
                queryResponse=(int)bar2.getRating();
                break;

            case R.id.about_owner_rating_three:
                AppCompatRatingBar bar3 = (AppCompatRatingBar) v;
                efficiency=(int)bar3.getRating();
                break;
            case R.id.about_owner_rating_four:
                AppCompatRatingBar bar4 = (AppCompatRatingBar) v;
                workAttitude=(int)bar4.getRating();
                break;
            case R.id.about_owner_save:
                callSaveApi();
                break;
        }
    }

    private void callSaveApi() {
        if (progressDialog!=null)
        progressDialog.show();
        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        int workOrderId= WorkOrderFragment.workOrderList.get(position).getWorkOrderId();

        CreateFeedback createFeedback=new CreateFeedback();
        createFeedback.setEfficiency(efficiency);
        createFeedback.setQueryResponse(queryResponse);
        createFeedback.setWorkCommitment(workCommitment);
        createFeedback.setWorkAttitude(workAttitude);
        createFeedback.setSuggestion(String.valueOf(edit_UserComment.getText().toString()));
        createFeedback.setWorkOrderId(workOrderId);
        createFeedback.setFeedbackTo(WorkOrderFragment.workOrderList.get(position).getOwnerId());
        createFeedback.setFeedbackBy(partyId);

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

        Call<StatuTitleMessageResponse> feedbackCallOwner=apiInterface.createFeedBack("Bearer "+token,partyId,createFeedback);

        feedbackCallOwner.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getContext(), "No value present", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse mError=new ForgotPasswordResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ForgotPasswordResponse .class);
                            Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                if (progressDialog!=null)
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
