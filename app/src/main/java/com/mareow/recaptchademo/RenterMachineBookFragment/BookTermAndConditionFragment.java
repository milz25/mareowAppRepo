package com.mareow.recaptchademo.RenterMachineBookFragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mareow.recaptchademo.DataModels.RenterSpecificMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.TypeOfTermAndCondition;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookTermAndConditionFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout btnGeneralTerms;
    LinearLayout btnCommercialTerms;
    LinearLayout btnPaymentTerms;
    LinearLayout btnlogisticsTerms;
    LinearLayout btnCancellationTerms;
    TypeOfTermAndCondition termAndConditionData=null;
    RenterSpecificMachine specificMachine;
    ProgressDialog progressDialog;
    public BookTermAndConditionFragment(RenterSpecificMachine renterSpecificMachine) {
        // Required empty public constructor
        specificMachine=renterSpecificMachine;
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
        View view=inflater.inflate(R.layout.fragment_book_term_and_condition, container, false);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait........");
        if (termAndConditionData==null){
            callTermandConditionApi();
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnGeneralTerms=(LinearLayout)view.findViewById(R.id.RBD_TC_general);
        btnGeneralTerms.setOnClickListener(this);
        btnCommercialTerms=(LinearLayout)view.findViewById(R.id.RBD_TC_commercial);
        btnCommercialTerms.setOnClickListener(this);
        btnPaymentTerms=(LinearLayout)view.findViewById(R.id.RBD_TC_payments);
        btnPaymentTerms.setOnClickListener(this);
        btnlogisticsTerms=(LinearLayout)view.findViewById(R.id.RBD_TC_logistics);
        btnlogisticsTerms.setOnClickListener(this);
        btnCancellationTerms=(LinearLayout)view.findViewById(R.id.RBD_TC_cancelterms);
        btnCancellationTerms.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.RBD_TC_general:
                showPopupDialog(termAndConditionData.getGeneralTermsdescription(),"General Terms");
                break;
            case R.id.RBD_TC_commercial:
                showPopupDialog(termAndConditionData.getCommercialTermsdescription(),"Commercial Terms");
                break;
            case R.id.RBD_TC_payments:
                showPopupDialog(termAndConditionData.getPaymentTermsdescription(),"Payments Terms");
                break;
            case R.id.RBD_TC_logistics:
                showPopupDialog(termAndConditionData.getLogisticsTermsdescription(),"Logistics Terms");
                break;
            case R.id.RBD_TC_cancelterms:
                showPopupDialog(termAndConditionData.getCancellationTermsdescription(),"Cancellation Terms");
                break;

        }
    }

    private void showPopupDialog(String data,String title) {

        AppCompatTextView mTitle;
        WebView mWebViewData;
        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.term_and_condition_popup_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
        mTitle.setText(title);
        mWebViewData=(WebView)dialog.findViewById(R.id.RBD_popdialog_web_data);
        mWebViewData.loadData(data,"text/html",null);
        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.RBD_popdialog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
    private void callTermandConditionApi() {
        String token= TokenManager.getSessionToken();
        int partyId=specificMachine.getPartyId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<TypeOfTermAndCondition> rentalPlanCall=apiInterface.getAllTypeOfTermAndCondition("Bearer "+token,partyId);
        rentalPlanCall.enqueue(new Callback<TypeOfTermAndCondition>() {
            @Override
            public void onResponse(Call<TypeOfTermAndCondition> call, Response<TypeOfTermAndCondition> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    termAndConditionData=response.body();
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
                }
            }

            @Override
            public void onFailure(Call<TypeOfTermAndCondition> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }
}
