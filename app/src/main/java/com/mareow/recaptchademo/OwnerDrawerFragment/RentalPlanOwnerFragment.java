package com.mareow.recaptchademo.OwnerDrawerFragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.RenterPlanForOwnerAdapter;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments.AddRentalPlanFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.SnapHelperOneByOne;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
 * Use the {@link RentalPlanOwnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentalPlanOwnerFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mRentalPlanRecyclerView;
    List<RentalPlan> rentalPlansList=new ArrayList<>();

    FloatingActionButton btnAddPlan;
    ProgressDialog progressDialog;
    public RentalPlanOwnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RentalPlanOwnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RentalPlanOwnerFragment newInstance(String param1, String param2) {
        RentalPlanOwnerFragment fragment = new RentalPlanOwnerFragment();
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
        View view=inflater.inflate(R.layout.fragment_rental_plan_owner, container, false);
        if(getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...........");
        }
        callRentalPlanApi();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRentalPlanRecyclerView=(RecyclerView)view.findViewById(R.id.rental_plan_recycleview);
        mRentalPlanRecyclerView.setHasFixedSize(false);
        mRentalPlanRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRentalPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(mRentalPlanRecyclerView);

        btnAddPlan=(FloatingActionButton)view.findViewById(R.id.add_owner_rental_add);
        btnAddPlan.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_owner_rental_add:
                callAddRentalPlanFragment();
                break;
        }
    }

    private void callAddRentalPlanFragment() {

        Fragment fragment=new AddRentalPlanFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void callRentalPlanApi() {
        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
       if (progressDialog!=null)
           progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RentalPlan>> rentalPlanCall=apiInterface.getAllPlanForMachine("Bearer "+token,partyId);
        rentalPlanCall.enqueue(new Callback<List<RentalPlan>>() {
            @Override
            public void onResponse(Call<List<RentalPlan>> call, Response<List<RentalPlan>> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    rentalPlansList=response.body();
                    callRecycleAdapter();
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
                    if (response.code()==403){
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
            public void onFailure(Call<List<RentalPlan>> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callRecycleAdapter() {
        if (rentalPlansList.size()>0){
            RenterPlanForOwnerAdapter planForOwnerAdapter=new RenterPlanForOwnerAdapter(getActivity(),rentalPlansList);
            mRentalPlanRecyclerView.setAdapter(planForOwnerAdapter);
        }
    }

}
