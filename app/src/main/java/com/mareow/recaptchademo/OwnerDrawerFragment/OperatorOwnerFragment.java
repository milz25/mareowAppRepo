package com.mareow.recaptchademo.OwnerDrawerFragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mareow.recaptchademo.Adapters.AssocitedOperatorAdapter;
import com.mareow.recaptchademo.DataModels.AssociatedOperator;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.SnapHelperOneByOne;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
 * Use the {@link OperatorOwnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OperatorOwnerFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView mRecyclerViewOperator;
    FloatingActionButton btnAddOperator;

    AppCompatTextView txtNoRecoredFound;
    ProgressDialog progressDialog;
    List<AssociatedOperator> associatedOperatorList=new ArrayList<>();

    public OperatorOwnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OperatorOwnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OperatorOwnerFragment newInstance(String param1, String param2) {
        OperatorOwnerFragment fragment = new OperatorOwnerFragment();
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
        View view=inflater.inflate(R.layout.fragment_operator_owner, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait..............");
        }

        getAssocitedOperatorList();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerViewOperator=(RecyclerView)view.findViewById(R.id.owner_associted_operator_recycle);
        mRecyclerViewOperator.setHasFixedSize(false);
        mRecyclerViewOperator.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewOperator.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(mRecyclerViewOperator);

        txtNoRecoredFound=(AppCompatTextView)view.findViewById(R.id.owner_associted_Operator_no_recordd_found);

        btnAddOperator=(FloatingActionButton)view.findViewById(R.id.owner_associted_operator_add);
        btnAddOperator.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.owner_associted_operator_add:
                callAddOperatorFragment();
                break;
        }
    }

    private void callAddOperatorFragment(){

        Fragment fragment=new AddOperatorFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
    public void getAssocitedOperatorList(){
        if (progressDialog!=null){
            progressDialog.show();
        }

        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<AssociatedOperator>> callOfferWorkOrder=apiInterface.getAssosicatedOperatorList("Bearer "+token,partyId);
        callOfferWorkOrder.enqueue(new Callback<List<AssociatedOperator>>() {
            @Override
            public void onResponse(Call<List<AssociatedOperator>> call, Response<List<AssociatedOperator>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    associatedOperatorList=response.body();
                    setAdapterRecycle();
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackbar("Record Not Found");

                        txtNoRecoredFound.setVisibility(View.VISIBLE);
                        mRecyclerViewOperator.setVisibility(View.GONE);
                    }
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<AssociatedOperator>> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        snackbar.show();
    }

    private void setAdapterRecycle() {
        if (associatedOperatorList.size()>0){

            AssocitedOperatorAdapter operatorAdapter=new AssocitedOperatorAdapter(getActivity(),associatedOperatorList);
            mRecyclerViewOperator.setAdapter(operatorAdapter);

        }
    }


}
