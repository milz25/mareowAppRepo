package com.mareow.recaptchademo.MainActivityFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.TypeOfTermAndCondition;
import com.mareow.recaptchademo.OwnerTermAndConditionFragments.OwnerGeneralTermsFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TermAndConditionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TermAndConditionFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    LinearLayout btnGeneralTerms;
    LinearLayout btnCommercialTerms;
    LinearLayout btnPaymentTerms;
    LinearLayout btnLogisticsTerms;
    LinearLayout btnCancellationTerms;

    TypeOfTermAndCondition termAndConditionData;

    public TermAndConditionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TermAndConditionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TermAndConditionFragment newInstance(String param1, String param2) {
        TermAndConditionFragment fragment = new TermAndConditionFragment();
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
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Terms & Conditions");
        }else if (Constants.USER_ROLE.equals("Owner")){
            OwnerMainActivity.txtOwnerTitle.setText("Terms & Conditions");
        }else {
            MainActivity.txtTitle.setText("Terms & Conditions");
        }

        View view=inflater.inflate(R.layout.fragment_term_and_condition, container, false);
        callTermandConditionApi();
        initView(view);
        return view;
    }

    private void initView(View view) {

        btnGeneralTerms=(LinearLayout)view.findViewById(R.id.Owner_TC_general_terms);
        btnGeneralTerms.setOnClickListener(this);
        btnCommercialTerms=(LinearLayout)view.findViewById(R.id.Owner_TC_commercial_term);
        btnCommercialTerms.setOnClickListener(this);
        btnPaymentTerms=(LinearLayout)view.findViewById(R.id.Owner_TC_payment_terms);
        btnPaymentTerms.setOnClickListener(this);
        btnLogisticsTerms=(LinearLayout)view.findViewById(R.id.Owner_TC_logistics_terms);
        btnLogisticsTerms.setOnClickListener(this);
        btnCancellationTerms=(LinearLayout)view.findViewById(R.id.Owner_TC_cancellation_terms);
        btnCancellationTerms.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Owner_TC_general_terms:
                loadNewFragment(termAndConditionData.getGeneralTermsdescription(),"General Terms");
               break;
            case R.id.Owner_TC_commercial_term:
                loadNewFragment(termAndConditionData.getCommercialTermsdescription(),"Commercial Terms");
                break;
            case R.id.Owner_TC_payment_terms:
                loadNewFragment(termAndConditionData.getPaymentTermsdescription(),"Payment Terms");
                break;
            case R.id.Owner_TC_logistics_terms:
                loadNewFragment(termAndConditionData.getLogisticsTermsdescription(),"Logistics Terms");
                break;
            case R.id.Owner_TC_cancellation_terms:
                loadNewFragment(termAndConditionData.getCancellationTermsdescription(),"Cancellation Terms");
                break;

            }
    }

    private void loadNewFragment(String termsdescription,String title) {
        Fragment fragment=new OwnerGeneralTermsFragment(termsdescription,title,termAndConditionData);

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void callTermandConditionApi() {
        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        TokenManager.showProgressDialog(getActivity());
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<TypeOfTermAndCondition> rentalPlanCall=apiInterface.getAllTypeOfTermAndCondition("Bearer "+token,partyId);
        rentalPlanCall.enqueue(new Callback<TypeOfTermAndCondition>() {
            @Override
            public void onResponse(Call<TypeOfTermAndCondition> call, Response<TypeOfTermAndCondition> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()){
                    termAndConditionData=response.body();
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
            public void onFailure(Call<TypeOfTermAndCondition> call, Throwable t) {
                TokenManager.dissmisProgress();
                Toast.makeText(getActivity(), "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
