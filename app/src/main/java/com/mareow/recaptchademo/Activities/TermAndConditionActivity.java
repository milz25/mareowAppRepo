package com.mareow.recaptchademo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.mareow.recaptchademo.DataModels.TermAndCondition;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermAndConditionActivity extends AppCompatActivity {

    WebView termWebView;
    String token;
    int partyId;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);

        termWebView=(WebView)findViewById(R.id.term_webview);
        token= TokenManager.getSessionToken();
        partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait ..........");
        if (partyId!=0 && token!=null){
            callTermConditionApi();
        }else {
            Toast.makeText(this, "user not register", Toast.LENGTH_SHORT).show();
            termWebView.setVisibility(View.GONE);
        }
    }

    private void callTermConditionApi() {
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<TermAndCondition> termCall=apiInterface.getTermAndCondition("Bearer "+token,partyId);
        termCall.enqueue(new Callback<TermAndCondition>() {
            @Override
            public void onResponse(Call<TermAndCondition> call, Response<TermAndCondition> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    termWebView.loadData(response.body().getGeneralTermsdescription(),"text/html","utf-8");
                }else {
                    Toast.makeText(TermAndConditionActivity.this, "Data Not Available", Toast.LENGTH_SHORT).show();
                    termWebView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TermAndCondition> call, Throwable t) {
                Toast.makeText(TermAndConditionActivity.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                termWebView.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }


}
