package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.NotificationRecycleAdapter;
import com.mareow.recaptchademo.DataModels.Notification;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mNotiRecycle;
    AppCompatTextView txtNoRecordFound;
    List<Notification> notificationList=new ArrayList<>();
    ProgressDialog progressDialog;
    public NotificationFragment() {
        // Required empty public constructor
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
        View view=inflater.inflate(R.layout.fragment_notification, container, false);
        initView(view);
        progressDialog=new ProgressDialog(getContext());
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Notification");
        }else if (Constants.USER_ROLE.equals("Owner")){
            OwnerMainActivity.txtOwnerTitle.setText("Notification");
        }else {
            MainActivity.txtTitle.setText("Notification");
        }

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait....................");
        }

        callNotification();
        callFlagApi();
        return view;
    }

    private void callNotification() {

        if (progressDialog!=null)
            progressDialog.show();
        String token=TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<Notification>> notiCall=apiInterface.getNotificationList("Bearer "+token,partyId,20);
        notiCall.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){

                        notificationList=response.body();
                        /*String notiResponse = response.body().string();
                        JSONArray jsonArray=new JSONArray(notiResponse);
                        notificationList=parseWorkOrderData.parseNotificationArray(jsonArray);*/
                        callRecycleAdater();
                }
                if (response.code()==401){
                    TokenExpiredUtils.tokenExpired(getActivity());
                }

                 if (response.code()==403){
                     showSnackbar("No Record Found");
                     mNotiRecycle.setVisibility(View.GONE);
                     txtNoRecordFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                if (progressDialog!=null)
                    progressDialog.dismiss();
            }
        });
    }

    private void callFlagApi() {
        String token=TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> updateNotiCall=apiInterface.updateNotificationFlag("Bearer "+token,partyId);
        updateNotiCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                }
                if (response.code()==401){
                    TokenExpiredUtils.tokenExpired(getActivity());
                }
                if (response.code()==403){
                   // Toast.makeText(getContext(),response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                showSnackbar(t.getMessage());
            }
        });
    }

    private void callRecycleAdater() {
        if (notificationList.size()>0){
            NotificationRecycleAdapter recycleAdapter=new NotificationRecycleAdapter(getActivity(),notificationList);
            mNotiRecycle.setAdapter(recycleAdapter);
        }else {
            mNotiRecycle.setVisibility(View.GONE);
            txtNoRecordFound.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {

        txtNoRecordFound=(AppCompatTextView)view.findViewById(R.id.notification_no_record);
        mNotiRecycle=(RecyclerView)view.findViewById(R.id.notification_recycle);
        mNotiRecycle.setHasFixedSize(false);
        mNotiRecycle.setItemAnimator(new DefaultItemAnimator());
        mNotiRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
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
