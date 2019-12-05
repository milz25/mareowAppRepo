package com.mareow.recaptchademo.RenterFragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.RentalPlanForRenterAdapter;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.DataModels.RenterSpecificMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterMachineBookFragment.BookDateAndReasonFragment;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.SnapHelperOneByOne;
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
 * Use the {@link RentalPlanForRenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RentalPlanForRenterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mRentalPlanRecycleView;

    ProgressDialog progressDialog;
    List<RentalPlan> rentalPlansList=new ArrayList<>();
    RenterSpecificMachine machineDetails;
    public RentalPlanForRenterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RentalPlanForRenterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RentalPlanForRenterFragment newInstance(String param1, String param2) {
        RentalPlanForRenterFragment fragment = new RentalPlanForRenterFragment();
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
        View view=inflater.inflate(R.layout.fragment_rental_plan_for_renter, container, false);
        RenterMainActivity.txtRenterTitle.setText("Rental Plan");
        Bundle bundle=getArguments();
        machineDetails=(RenterSpecificMachine) bundle.getSerializable("machineDetails");
        initView(view);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait................");
        callRentalPlanApi();
        return view;
    }

    private void initView(View view) {

        mRentalPlanRecycleView=(RecyclerView)view.findViewById(R.id.RPD_recycleview);
        mRentalPlanRecycleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRentalPlanRecycleView.setHasFixedSize(false);
        mRentalPlanRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRentalPlanRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(mRentalPlanRecycleView);

    }

    private void callRentalPlanApi() {
        String token= TokenManager.getSessionToken();
        int partyId=machineDetails.getPartyId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<RentalPlan>> rentalPlanCall=apiInterface.getAllPlanForMachine("Bearer "+token,partyId);
        rentalPlanCall.enqueue(new Callback<List<RentalPlan>>() {
            @Override
            public void onResponse(Call<List<RentalPlan>> call, Response<List<RentalPlan>> response) {
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
                }
            }

            @Override
            public void onFailure(Call<List<RentalPlan>> call, Throwable t) {
               progressDialog.dismiss();
               Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callRecycleAdapter() {
        if (rentalPlansList.size()>0){

            RecyclerViewClickListener listener=new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    BookDateAndReasonFragment.selectedPlan.clear();
                    BookDateAndReasonFragment.selectedPlan.add(rentalPlansList.get(position));
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            };
            RentalPlanForRenterAdapter planForRenterAdapter=new RentalPlanForRenterAdapter(getActivity(),rentalPlansList,listener);
            mRentalPlanRecycleView.setAdapter(planForRenterAdapter);
        }
    }



}
