package com.mareow.recaptchademo.MainDetailsFragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Adapters.MainDashBoardAdapter;
import com.mareow.recaptchademo.DataModels.OperatorMachineCount;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.AppUpdateChecker;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
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
 * Use the {@link SupervisorDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupervisorDashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatTextView txtVerified;


    RecyclerView dashRecycle;
    ApiInterface apiInterface;
    String token;
    int partyId;
    List<OperatorMachineCount> operatorMachineCountList=new ArrayList<>();
    private final int DEFAULT_SPAN_COUNT = 2;
    GridLayoutManager gridLayoutManager;
    List<String> dataKeyInteger=new ArrayList<>();
    List<String> dataKeyString=new ArrayList<>();
    List<String> dataKeyImages=new ArrayList<>();

    ProgressDialog progressDialog;

    AppCompatImageView verifiedImage;
    public SupervisorDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SupervisorDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupervisorDashboardFragment newInstance(String param1, String param2) {
        SupervisorDashboardFragment fragment = new SupervisorDashboardFragment();
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
        View view=inflater.inflate(R.layout.fragment_supervisor_dashboard, container, false);
        initView(view);
        MainActivity.txtTitle.setText("Dashboard");
        MainActivity.navItemIndex=0;

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait.......");
        }

        token= TokenManager.getSessionToken();
        partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        dataKeyInteger.clear();
        dataKeyString.clear();
        dataKeyImages.clear();
        //mItemList.clear();

        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(getActivity());  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false);

        callWOInProgressData();
        return view;
    }

    private void initView(View view) {
       /* txtWOProgress=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_workorder_progress);
        txtWOCompleted=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_workorder_completed);
        txtHoursLogged=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_hourslogged);
        txtKMMachineRun=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_km_machine_run);*/
        StringBuilder stringBuilder=new StringBuilder();
        txtVerified=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_verified);
        verifiedImage=(AppCompatImageView)view.findViewById(R.id.dashboard_verified_image);

        String category=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null);
        stringBuilder.append(category+" Customer");

       /* if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Blue")){
            // txtVerified.setText("BLUE verified customer.");
            stringBuilder.append("Blue Customer");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Platinum")){
            // txtVerified.setText("PLATINUM verified customer.");
            stringBuilder.append("Platinum Customer");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Gold")){
            //txtVerified.setText("GOLD verified customer.");
            stringBuilder.append("Gold Customer");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Silver")){
            // txtVerified.setText("SILVER verified customer.");
            stringBuilder.append("Silver Customer");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Diamond")){
            // txtVerified.setText("DIAMOND verified customer.");
            stringBuilder.append("Diamond Customer");
        }*/

        if (TokenManager.getUserDetailsPreference().getBoolean(Constants.PREF_KEY_IS_VERIFIED,false)){
            stringBuilder.append(" (Verified)");
            verifiedImage.setImageResource(R.drawable.ic_verify_true);
        }else {
            stringBuilder.append(" (Not Verified)");
            verifiedImage.setImageResource(R.drawable.ic_verify_false);
        }

        txtVerified.setText(stringBuilder.toString());

        dashRecycle=(RecyclerView)view.findViewById(R.id.dashboard_frag_recycle_machine);
        dashRecycle.setHasFixedSize(false);
        dashRecycle.setItemAnimator(new DefaultItemAnimator());
        dashRecycle.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        gridLayoutManager = new GridLayoutManager(getActivity(), DEFAULT_SPAN_COUNT);
        dashRecycle.setLayoutManager(gridLayoutManager);



        // dashRecycle.addItemDecoration(new SpacesItemDecoration(10));
        // dashRecycle.setLayoutManager(new GridLayoutManager(getContext(),2));

    }

    private void callWOInProgressData() {
        progressDialog.show();
        Call<String> woInProgressCountCall=apiInterface.getWorkOrderInProgressCount("Bearer "+token,partyId);
        woInProgressCountCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("WO: In Progress");
                    dataKeyImages.add("wo_open_final");
                    callWOCompleted();
                }else {
                    if (response.code()==401){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            String data="0";
                            dataKeyInteger.add(data);
                            dataKeyString.add("WO: In Progress");
                            dataKeyImages.add("wo_open_final");
                            //  dataList.add("0 WO in Progress");
                            callWOCompleted();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==403){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callWOCompleted(){
        Call<String> woCompletedCall=apiInterface.getWorkOrderInCompletedCount("Bearer "+token,partyId);
        woCompletedCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("WO: Completed");
                    dataKeyImages.add("wo_close_final");
                    callHoursLogged();
                    /*if (Constants.USER_ROLE.equals("Operator")||Constants.USER_ROLE.equals("Supervisor")){
                        callHoursLogged();
                    }
                    if (Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")){
                        callOfferCount();
                    }*/
                }else {
                    if (response.code()==401){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            String data="0";
                            dataKeyInteger.add(data);
                            dataKeyString.add("WO: Completed");
                            dataKeyImages.add("wo_close_final");
                            callHoursLogged();
                           /* if (Constants.USER_ROLE.equals("Operator")||Constants.USER_ROLE.equals("Supervisor")){
                                callHoursLogged();
                            }
                            if (Constants.USER_ROLE.equals("Renter")|| Constants.USER_ROLE.equals("Owner")){
                                callOfferCount();
                            }*/
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==403){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callHoursLogged(){
        Call<String> hoursLoggedCall=apiInterface.getHoursLoggedCount("Bearer "+token,partyId);
        hoursLoggedCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("Hours Logged");
                    dataKeyImages.add("logged_hour_final");
                    callKMSMachineRun();
                }else {
                    if (response.code()==401){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            String data="0";
                            dataKeyInteger.add(data);
                            dataKeyString.add("Hours Logged");
                            dataKeyImages.add("logged_hour_final");
                            callKMSMachineRun();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==403){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callKMSMachineRun(){
        Call<String> kmsMachineRunCall=apiInterface.getKmMachineRunCount("Bearer "+token,partyId);
        kmsMachineRunCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("Kms Machine Run");
                    dataKeyImages.add("kms_runs_final");
                    callRecycle();
                    //callFuelCountForOperator();
                    /*if (Constants.USER_ROLE.equals("Supervisor")){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        callRecycle();
                    }
                    if (Constants.USER_ROLE.equals("Operator")){
                        callOperatorMachineRunCount();
                    }*/
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            String data="0";
                            dataKeyInteger.add(data);
                            dataKeyString.add("Kms Machine Run");
                            dataKeyImages.add("kms_runs_final");
                            callRecycle();
                           // callFuelCountForOperator();
                            /*if (Constants.USER_ROLE.equals("Supervisor")){
                                progressDialog.dismiss();
                                callRecycle();
                            }
                            if (Constants.USER_ROLE.equals("Operator")){
                                callOperatorMachineRunCount();
                            }*/
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRecycle() {

        /*for (int i=0;i<operatorMachineCountList.size();i++){
           *//* HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put(String.valueOf(operatorMachineCountList.get(i).getCount()),operatorMachineCountList.get(i).getMachineFullName());
            //dataList.add(operatorMachineCountList.get(i).getCount()+" "+operatorMachineCountList.get(i).getMachineFullName());
            dataList.add(hashMap);*//*
            dataKeyInteger.add(String.valueOf(operatorMachineCountList.get(i).getCount()));
            dataKeyString.add(operatorMachineCountList.get(i).getCategory()+":"+operatorMachineCountList.get(i).getSubcategory()+":"+operatorMachineCountList.get(i).getManufacturer());
            dataKeyImages.add(operatorMachineCountList.get(i).getCategoryImage());
        }*/
        //OperatorMachineCountAdapter machineCountAdapter=new OperatorMachineCountAdapter(getActivity(),operatorMachineCountList);
       /* mAdapter=new GridAdapter(getActivity(),mItemList,gridLayoutManager,DEFAULT_SPAN_COUNT);
        dashRecycle.setAdapter(mAdapter);
        addMockList();
*/

        MainDashBoardAdapter mainDashBoardAdapter=new MainDashBoardAdapter(getActivity(),dataKeyInteger,dataKeyString,dataKeyImages);
        dashRecycle.setAdapter(mainDashBoardAdapter);
    }

}
