package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.GridAdapter;
import com.mareow.recaptchademo.DataModels.GridItem;
import com.mareow.recaptchademo.DataModels.OperatorMachineCount;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
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
import com.mareow.recaptchademo.DataModels.Item;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainDashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDashBoardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatTextView txtWOProgress;
    AppCompatTextView txtWOCompleted;
    AppCompatTextView txtHoursLogged;
    AppCompatTextView txtKMMachineRun;
    AppCompatTextView  txtVerified;


    RecyclerView dashRecycle;
    ApiInterface apiInterface;
    String token;
    int partyId;
    List<OperatorMachineCount> operatorMachineCountList=new ArrayList<>();
    private static final int DEFAULT_SPAN_COUNT = 2;
    GridLayoutManager gridLayoutManager;

    List<String> dataKeyInteger=new ArrayList<>();
    List<String> dataKeyString=new ArrayList<>();
    List<String> dataKeyImages=new ArrayList<>();

    private List<Item> mItemList = new ArrayList<>();
    // public static ProfileData mUserProfileData=new ProfileData();
    ProgressDialog progressDialog;
    GridAdapter mAdapter;
    public MainDashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainDashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDashBoardFragment newInstance(String param1, String param2) {
        MainDashBoardFragment fragment = new MainDashBoardFragment();
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
        View view=inflater.inflate(R.layout.fragment_main_dash_board, container, false);
        initView(view);
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Dashboard");
            RenterMainActivity.navItemIndexRenter=0;
        }else if(Constants.USER_ROLE.equals("Owner")){
            OwnerMainActivity.txtOwnerTitle.setText("Dashboard");
            OwnerMainActivity.navItemIndexOwner=0;
        }else {
            MainActivity.txtTitle.setText("Dashboard");
            MainActivity.navItemIndex=0;
        }

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
        mItemList.clear();
        callWOInProgressData();
        //callWOCompleted();
        //callHoursLogged();
        // callKMSMachineRun();
        //callOperatorMachineRunCount();
        //callProfileData();

        //callUnReadNotification();
        return view;
    }
    private void initView(View view) {
       /* txtWOProgress=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_workorder_progress);
        txtWOCompleted=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_workorder_completed);
        txtHoursLogged=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_hourslogged);
        txtKMMachineRun=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_km_machine_run);*/
        txtVerified=(AppCompatTextView)view.findViewById(R.id.dashboard_frag_verified);

        if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Blue")){
            txtVerified.setText("BLUE verified customer.");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Platinum")){
            txtVerified.setText("PLATINUM verified customer.");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Gold")){
            txtVerified.setText("GOLD verified customer.");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Silver")){
            txtVerified.setText("SILVER verified customer.");
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("User Category: Diamond")){
            txtVerified.setText("DIAMOND verified customer.");
        }

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
                    if (Constants.USER_ROLE.equals("Operator")||Constants.USER_ROLE.equals("Supervisor")){
                        callHoursLogged();
                    }
                    if (Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")){
                        callOfferCount();
                    }
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
                            if (Constants.USER_ROLE.equals("Operator")||Constants.USER_ROLE.equals("Supervisor")){
                                callHoursLogged();
                            }
                            if (Constants.USER_ROLE.equals("Renter")|| Constants.USER_ROLE.equals("Owner")){
                                callOfferCount();
                            }
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
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("Kms Machine Run");
                    dataKeyImages.add("kms_runs_final");
                    if (Constants.USER_ROLE.equals("Supervisor")){
                        if (progressDialog!=null)
                          progressDialog.dismiss();
                        callRecycle();
                    }
                    if (Constants.USER_ROLE.equals("Operator")){
                        callOperatorMachineRunCount();
                    }
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
                            dataKeyString.add("Kms Machine Run");
                            dataKeyImages.add("kms_runs_final");
                            if (Constants.USER_ROLE.equals("Supervisor")){
                                progressDialog.dismiss();
                                callRecycle();
                            }
                            if (Constants.USER_ROLE.equals("Operator")){
                                callOperatorMachineRunCount();
                            }
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }if (response.code()==403){
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

    public void callOperatorMachineRunCount(){
        Call<List<OperatorMachineCount>> omcCall=apiInterface.getOperatorMachineCount("Bearer "+token,partyId);
        omcCall.enqueue(new Callback<List<OperatorMachineCount>>() {
            @Override
            public void onResponse(Call<List<OperatorMachineCount>> call, Response<List<OperatorMachineCount>> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    operatorMachineCountList=response.body();
                    callRecycle();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getContext(), "Recor not Found", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code()==403){
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OperatorMachineCount>> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getContext(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRecycle() {

        for (int i=0;i<operatorMachineCountList.size();i++){
           /* HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put(String.valueOf(operatorMachineCountList.get(i).getCount()),operatorMachineCountList.get(i).getMachineFullName());
            //dataList.add(operatorMachineCountList.get(i).getCount()+" "+operatorMachineCountList.get(i).getMachineFullName());
            dataList.add(hashMap);*/
            dataKeyInteger.add(String.valueOf(operatorMachineCountList.get(i).getCount()));
            dataKeyString.add(operatorMachineCountList.get(i).getCategory()+":"+operatorMachineCountList.get(i).getSubcategory()+":"+operatorMachineCountList.get(i).getManufacturer());
            dataKeyImages.add(operatorMachineCountList.get(i).getCategoryImage());
        }
        //OperatorMachineCountAdapter machineCountAdapter=new OperatorMachineCountAdapter(getActivity(),operatorMachineCountList);
        mAdapter=new GridAdapter(getActivity(),mItemList,gridLayoutManager,DEFAULT_SPAN_COUNT);
        dashRecycle.setAdapter(mAdapter);
        addMockList();


        //MainDashBoardAdapter mainDashBoardAdapter=new MainDashBoardAdapter(getActivity(),)

    }

    private void addMockList() {
        // mAdapter.addItem(new HeaderItem("Work Order Details"));

        //mAdapter.addItem(new GridItem(dataKeyString.get(0),dataKeyInteger.get(0)));
        //mAdapter.addItem(new GridItem(dataKeyString.get(1),dataKeyInteger.get(1)));

        // mAdapter.addItem(new HeaderItem("Daily Logs Details"));

        //mAdapter.addItem(new GridItem(dataKeyString.get(2),dataKeyInteger.get(2)));
        //mAdapter.addItem(new GridItem(dataKeyString.get(3),dataKeyInteger.get(3)));

        for (int i=0;i<dataKeyString.size();i++){
            mAdapter.addItem(new GridItem(dataKeyString.get(i),dataKeyInteger.get(i),dataKeyImages.get(i)));
        }


       /* if (dataKeyString.size()>0){
            //mAdapter.addItem(new HeaderItem("Machine Details"));
            for (int i=0;i<4;i++){
                mAdapter.addItem(new GridItem(dataKeyString.get(i),dataKeyInteger.get(i),""));
            }

            if (Constants.USER_ROLE.equals("Operator")){
                    int count=0;
                    for (int i=4;i<dataKeyString.size();i++){
                        String image=operatorMachineCountList.get(count).getCategoryImage();
                        mAdapter.addItem(new GridItem(dataKeyString.get(i),dataKeyInteger.get(i),image));
                        count++;
                    }

            }

            if (Constants.USER_ROLE.equals("Renter")){
                if (dataKeyString.size()>4){
                    for (int i=4;i<dataKeyString.size();i++){
                        mAdapter.addItem(new GridItem(dataKeyString.get(i),dataKeyInteger.get(i),""));
                    }
                }
            }


        }*/
       /* int headerPosition = new Random().nextInt(19) + 1;

        for (int i = 0; i < 100; i++) {
            if (i % headerPosition == 0) {
                mAdapter.addItem(new HeaderItem("Header " + getHeaderCounter()));
                headerPosition = new Random().nextInt(19) + 1;
            }

            mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        }*/
    }

   /* private void callProfileData() {
        progressDialog.show();
        String token= TokenManager.getSessionToken();
        int userId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileData> callProfile=apiInterface.getUserProfileAllData("Bearer "+token,userId);
        callProfile.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    mUserProfileData=response.body();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getContext(),mError.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/



    /*private ArrayList<ListItem> getList() {
        ArrayList<String> headerTile=new ArrayList<>();
        headerTile.add("Work Order Details");
        headerTile.add("Daily Log Details");
        headerTile.add("Machine Details");
        ArrayList<ListItem> arrayList = new ArrayList<>();
        for(int j = 0; j <3; j++) {
            Header header = new Header();
            header.setHeader(headerTile.get(j));
            arrayList.add(header);
            for (int i = 0; i <2; i++) {
                DashContentItem item = new DashContentItem();
                item.setName(dataList.get(i));
                arrayList.add(item);
            }
        }
        return arrayList;
    }*/

    public SpannableString getSpannableString(String string){
        SpannableString ss1=  new SpannableString(string);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,5, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color

        return ss1;
    }

    private void callOfferCount(){
        Call<String> kmsMachineRunCall=apiInterface.getWorkOrderOfferCount("Bearer "+token,partyId);
        kmsMachineRunCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("WO: Offer");
                    dataKeyImages.add("wo_offer");
                    callInvoiceCount();
                }else {
                    if (response.code()==401){
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
                            dataKeyString.add("WO: Offer");
                            dataKeyImages.add("wo_offer");
                            callInvoiceCount();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }if (response.code()==403){
                        progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
               progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void callInvoiceCount(){
        Call<String> kmsMachineRunCall=apiInterface.getInvoiceCount("Bearer "+token,partyId);
        kmsMachineRunCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("Invoice Generated");
                    dataKeyImages.add("invoice_generated");
                    callPaymentPending();
                }else {
                    if (response.code()==401){
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
                            dataKeyString.add("Invoice Generated");
                            dataKeyImages.add("invoice_generated");
                            callPaymentPending();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }if (response.code()==403){
                        progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callPaymentPending(){
        Call<String> kmsMachineRunCall=apiInterface.getPaymentPendingCount("Bearer "+token,partyId);
        kmsMachineRunCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    String data=response.body().toString();
                    dataKeyInteger.add(data);
                    dataKeyString.add("Payment Due");
                    dataKeyImages.add("payment_due");
                    callRecycle();
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
                            dataKeyString.add("Payment Due");
                            dataKeyImages.add("payment_due");
                            callRecycle();
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
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
