package com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.AssociaedMachineAdapter;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanAssociatedMachineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanAssociatedMachineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mAssociatedMachineRecycle;

    List<RenterMachine> machineListForOwner=new ArrayList<>();
    List<RenterMachine> associatedMachine=new ArrayList<>();

    public PlanAssociatedMachineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanAssociatedMachineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanAssociatedMachineFragment newInstance(String param1, String param2) {
        PlanAssociatedMachineFragment fragment = new PlanAssociatedMachineFragment();
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
        View view=inflater.inflate(R.layout.fragment_plan_associated_machine, container, false);
        callOwnerMachineApi();
        initView(view);
        return view;
    }

    private void initView(View view) {

        mAssociatedMachineRecycle=(RecyclerView)view.findViewById(R.id.ARO_AM_recycle);
        mAssociatedMachineRecycle.setHasFixedSize(false);
        mAssociatedMachineRecycle.setItemAnimator(new DefaultItemAnimator());
        mAssociatedMachineRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

    }




    private void callOwnerMachineApi() {
        TokenManager.showProgressDialog(getActivity());
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token= new TokenManager(getActivity()).getSessionToken();

        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("partyId",String.valueOf(partyId));

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RenterMachine>> machineCall=apiInterface.getMachineForRenter("Bearer "+token,queryMap);
        machineCall.enqueue(new Callback<List<RenterMachine>>() {
            @Override
            public void onResponse(Call<List<RenterMachine>> call, Response<List<RenterMachine>> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()){
                    machineListForOwner.clear();
                    machineListForOwner=response.body();
                    setRecycleAdapter();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(), "Record not found", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RenterMachine>> call, Throwable t) {
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecycleAdapter() {
        if (machineListForOwner.size()>0){
            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                   if (((AppCompatCheckBox)view).isChecked()){
                       if (!associatedMachine.contains(machineListForOwner.get(position)))
                       associatedMachine.add(machineListForOwner.get(position));
                   }else {
                       if (associatedMachine.contains(machineListForOwner.get(position)))
                       associatedMachine.remove(associatedMachine.indexOf(machineListForOwner.get(position)));
                   }
                }
            };
            AssociaedMachineAdapter machineMainAdapter=new AssociaedMachineAdapter(getActivity(),machineListForOwner,listener);
            mAssociatedMachineRecycle.setAdapter(machineMainAdapter);
        }
    }





}
