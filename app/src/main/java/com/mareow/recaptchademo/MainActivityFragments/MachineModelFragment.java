package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Adapters.AbleToRunAdapter;
import com.mareow.recaptchademo.DataModels.AbleToRunMachine;
import com.mareow.recaptchademo.MainDetailsFragment.AddOperatorMachineModelFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.SnapHelperOneByOne;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachineModelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineModelFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SearchView mSerach;
    RecyclerView mRecycleView;
    FloatingActionButton btnAddModel;
    AppCompatTextView txtNoRecordFound;

    ProgressDialog progressDialog;
    List<AbleToRunMachine> ableToRunMachineList;
    AbleToRunAdapter ableToRunAdapter;
    public MachineModelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineModelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineModelFragment newInstance(String param1, String param2) {
        MachineModelFragment fragment = new MachineModelFragment();
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
        View view=inflater.inflate(R.layout.fragment_machine_model, container, false);
        initView(view);
        ableToRunMachineList=new ArrayList<>();
        progressDialog=new ProgressDialog(getActivity());

        MainActivity.txtTitle.setText("Machine Model");
        MainActivity.navItemIndex=2;

        callAbleToRunMachine();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mSerach!=null){
            mSerach.clearFocus();
        }
    }

    private void initView(View view) {

        mRecycleView=(RecyclerView)view.findViewById(R.id.able_to_run_recycle);
        mRecycleView.setHasFixedSize(false);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(mRecycleView);



        mSerach=(SearchView)view.findViewById(R.id.able_to_run_search);
        mSerach.setOnQueryTextListener(this);
        txtNoRecordFound=(AppCompatTextView)view.findViewById(R.id.able_to_run_no_record_found);
        btnAddModel=(FloatingActionButton) view.findViewById(R.id.able_to_run_add_model);
        btnAddModel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.able_to_run_add_model:
                callAddMachineModel();
                break;
        }
    }
    private void callAbleToRunMachine() {

        progressDialog.show();
        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<AbleToRunMachine>> ableToRunCall=apiInterface.getAbleToRunMachineDetails("Bearer "+token,partyId);
        ableToRunCall.enqueue(new Callback<List<AbleToRunMachine>>() {
            @Override
            public void onResponse(Call<List<AbleToRunMachine>> call, Response<List<AbleToRunMachine>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){

                    ableToRunMachineList=response.body();
                    callRecycleAdapter();
                    /*try {
                        String workOrderResponse = response.body().string();
                        JSONArray jsonArray=new JSONArray(workOrderResponse);
                        ableToRunMachineList=parseWorkOrderData.parseAbleToRunMachine(jsonArray);
                        callRecycleAdapter();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }else {
                    if (response.code() == 401) {
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code() == 404) {
                        showSnackbar("No Record Found");
                        txtNoRecordFound.setVisibility(View.VISIBLE);
                        mRecycleView.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<AbleToRunMachine>> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    private void callRecycleAdapter() {
        if (ableToRunMachineList.size()>0){
            ableToRunAdapter=new AbleToRunAdapter(getActivity(),ableToRunMachineList);
            mRecycleView.setAdapter(ableToRunAdapter);
         }else {
            txtNoRecordFound.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.GONE);
        }
    }


    private void callAddMachineModel() {

        Fragment addOperatorMachineModelFragment = new AddOperatorMachineModelFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, addOperatorMachineModelFragment); // give your fragment container id in first parameter
        transaction.addToBackStack("MachineModelMain");
        transaction.commitAllowingStateLoss();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput=newText.toLowerCase();

        List<AbleToRunMachine> newList=new ArrayList<>();
        for(AbleToRunMachine ableToRunMachine : ableToRunMachineList){
             if (ableToRunMachine.getCatagoryMeaning().toLowerCase().contains(userInput)){
                  newList.add(ableToRunMachine);
             }else if (ableToRunMachine.getSubCategoryMeaning().toLowerCase().contains(userInput)){
                 newList.add(ableToRunMachine);
             }else if (ableToRunMachine.getManufacturerMeaning().toLowerCase().contains(userInput)){
                 newList.add(ableToRunMachine);
             }else if (ableToRunMachine.getModelNo().toLowerCase().contains(userInput)){
                 newList.add(ableToRunMachine);
             }else if (ableToRunMachine.getModelName().toLowerCase().contains(userInput)){
                 newList.add(ableToRunMachine);
             }

        }
        if (ableToRunAdapter!=null){
            ableToRunAdapter.updateList(newList);
        }
        return false;
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        snackbar.show();
    }
}
