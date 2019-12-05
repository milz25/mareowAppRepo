package com.mareow.recaptchademo.OwnerDrawerFragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
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
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Adapters.OwnerMachineMainAdapter;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.OwnerAddMachine.AddMachineFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.SnapHelperOneByOne;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
 * Use the {@link MachineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineFragment extends Fragment implements View.OnClickListener,SearchView.OnQueryTextListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SearchView mSearchView;
    AppCompatImageView btnFilter;

    RecyclerView mMachineRecycle;

    FloatingActionButton btnAddMachine;

    List<RenterMachine> machineListForOwner=new ArrayList<>();

    ProgressDialog progressDialog;
    public MachineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineFragment newInstance(String param1, String param2) {
        MachineFragment fragment = new MachineFragment();
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
        View view=inflater.inflate(R.layout.fragment_machine, container, false);

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait.................");
        }
        callOwnerMachineApi();
        initView(view);
        return view;
    }



    private void initView(View view){

        mSearchView=(SearchView)view.findViewById(R.id.owner_machine_main_serach);
        btnFilter=(AppCompatImageView) view.findViewById(R.id.owner_machine_main_filter);
        btnAddMachine=(FloatingActionButton) view.findViewById(R.id.owner_machine_main_add_machine);
        btnAddMachine.setOnClickListener(this);

        mMachineRecycle=(RecyclerView) view.findViewById(R.id.owner_machine_main_recycle);
        mMachineRecycle.setHasFixedSize(false);
        mMachineRecycle.setItemAnimator(new DefaultItemAnimator());

        mMachineRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(mMachineRecycle);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.owner_machine_main_filter:

                break;
            case R.id.owner_machine_main_add_machine:
              callAddMachineFragment();
                break;
        }

    }

    private void callAddMachineFragment() {
        Constants.MACHINE_UPDATE=false;
        Fragment fragment=new AddMachineFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    private void callOwnerMachineApi() {
        if (progressDialog!=null){
            progressDialog.show();
        }
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token= new TokenManager(getActivity()).getSessionToken();

        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("partyId",String.valueOf(partyId));

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RenterMachine>> machineCall=apiInterface.getMachineForRenter("Bearer "+token,queryMap);
        machineCall.enqueue(new Callback<List<RenterMachine>>() {
            @Override
            public void onResponse(Call<List<RenterMachine>> call, Response<List<RenterMachine>> response) {
                if (progressDialog!=null){
                    progressDialog.dismiss();
                }
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
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<RenterMachine>> call, Throwable t) {
                if (progressDialog!=null){
                    progressDialog.dismiss();
                }
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecycleAdapter() {
        if (machineListForOwner.size()>0){
            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {


                }
            };
            OwnerMachineMainAdapter machineMainAdapter=new OwnerMachineMainAdapter(getActivity(),machineListForOwner,listener);
            mMachineRecycle.setAdapter(machineMainAdapter);
        }
    }

}
