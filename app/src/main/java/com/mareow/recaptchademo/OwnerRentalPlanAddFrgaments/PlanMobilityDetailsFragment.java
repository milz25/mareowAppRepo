package com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanMobilityDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanMobilityDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    AppCompatRadioButton rdSiteOwner;
    AppCompatRadioButton rdSiteClient;
    AppCompatRadioButton rdSiteTransporter;


    AppCompatRadioButton rdOwnerOwner;
    AppCompatRadioButton rdOwnerClient;
    AppCompatRadioButton rdOwnerTransporter;


    AppCompatRadioButton rdFixedMob;
    AppCompatRadioButton rdActualMob;

    AppCompatRadioButton rdFixedDemob;
    AppCompatRadioButton rdActiualDemob;

    TextInputEditText mobAmount;
    TextInputEditText demobAmount;

    LinearLayout linearLayout_one;
    LinearLayout linearLayout_two;


    public static boolean TAKING_AMOUNT=true;
    public static String MOBILITY_AMOUNT=null;
    public static String MOBILITY_REPONSIBILE="O";

    public static boolean RESPONSIBILITY_AMOUNT=true;
    public static String DEMOBILITY_AMOUNT=null;
    public static String DEMOBILITY_REPONSIBILE="O";


    public PlanMobilityDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanMobilityDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanMobilityDetailsFragment newInstance(String param1, String param2) {
        PlanMobilityDetailsFragment fragment = new PlanMobilityDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_plan_mobility_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        linearLayout_one=(LinearLayout)view.findViewById(R.id.ARO_MD_site_ln1);
        linearLayout_two=(LinearLayout)view.findViewById(R.id.ARO_MD_site_ln2);


        rdSiteOwner=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_site_owner);
        if (MOBILITY_REPONSIBILE!=null &&  MOBILITY_REPONSIBILE.equals("O")){
            rdSiteOwner.setChecked(true);
            linearLayout_one.setVisibility(View.VISIBLE);
        }
        rdSiteOwner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    linearLayout_one.setVisibility(View.VISIBLE);
                    MOBILITY_REPONSIBILE="O";
                }else {
                    linearLayout_one.setVisibility(View.GONE);
                }
            }
        });

        rdSiteClient=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_site_client);
        if (MOBILITY_REPONSIBILE!=null &&  MOBILITY_REPONSIBILE.equals("C")){
            rdSiteClient.setChecked(true);
            linearLayout_one.setVisibility(View.GONE);
        }
        rdSiteClient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                  linearLayout_one.setVisibility(View.GONE);
                    MOBILITY_REPONSIBILE="C";
                }
            }
        });

        rdSiteTransporter=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_site_transporter);
        if (MOBILITY_REPONSIBILE!=null &&  MOBILITY_REPONSIBILE.equals("T")){
            rdSiteTransporter.setChecked(true);
            linearLayout_one.setVisibility(View.GONE);
        }
        rdSiteTransporter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   linearLayout_one.setVisibility(View.GONE);
                    MOBILITY_REPONSIBILE="T";
                }
            }
        });


        rdOwnerOwner=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_Owner_owner);
        if (DEMOBILITY_REPONSIBILE!=null &&  DEMOBILITY_REPONSIBILE.equals("O")){
            rdOwnerOwner.setChecked(true);
            linearLayout_two.setVisibility(View.VISIBLE);
        }
        rdOwnerOwner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   linearLayout_two.setVisibility(View.VISIBLE);
                   DEMOBILITY_REPONSIBILE="O";
                }
            }
        });

        rdOwnerClient=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_owner_client);
        if (DEMOBILITY_REPONSIBILE!=null &&  DEMOBILITY_REPONSIBILE.equals("C")){
            rdOwnerClient.setChecked(true);
            linearLayout_two.setVisibility(View.GONE);
        }
        rdOwnerClient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    linearLayout_two.setVisibility(View.GONE);
                    DEMOBILITY_REPONSIBILE="C";
                }
            }
        });

        rdOwnerTransporter=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_owner_transporter);
        if (DEMOBILITY_REPONSIBILE!=null &&  DEMOBILITY_REPONSIBILE.equals("T")){
            rdOwnerTransporter.setChecked(true);
            linearLayout_two.setVisibility(View.GONE);
        }
        rdOwnerTransporter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    linearLayout_two.setVisibility(View.GONE);
                    DEMOBILITY_REPONSIBILE="T";
                }
            }
        });

        mobAmount=(TextInputEditText)view.findViewById(R.id.ARO_MD_mobility_amount_rate1);
        if (MOBILITY_AMOUNT!=null){
            mobAmount.setText(MOBILITY_AMOUNT);
        }
        mobAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              MOBILITY_AMOUNT=s.toString();
            }
        });
        demobAmount=(TextInputEditText)view.findViewById(R.id.ARO_MD_mobility_amount_rate2);
        if (DEMOBILITY_AMOUNT!=null){
            demobAmount.setText(DEMOBILITY_AMOUNT);
        }
        demobAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              DEMOBILITY_AMOUNT=s.toString();
            }
        });


        rdFixedMob=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_mobility_fixed);
        if (TAKING_AMOUNT){
            rdFixedMob.setChecked(true);
        }
        rdFixedMob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    TAKING_AMOUNT=true;
                }
            }
        });
        rdActualMob=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_mobility_onactuals);
        if (!TAKING_AMOUNT){
            rdActualMob.setChecked(true);
        }
        rdActualMob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    TAKING_AMOUNT=false;
                }
            }
        });
        rdFixedDemob=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_mobility2_fixed);
        if (RESPONSIBILITY_AMOUNT){
            rdFixedDemob.setChecked(true);
        }
        rdFixedDemob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    RESPONSIBILITY_AMOUNT=true;
                }
            }
        });
        rdActiualDemob=(AppCompatRadioButton)view.findViewById(R.id.ARO_MD_mobility2_onactuals);
        if (!RESPONSIBILITY_AMOUNT){
            rdActiualDemob.setChecked(true);
        }
        rdActiualDemob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    RESPONSIBILITY_AMOUNT=false;
                }
            }
        });
    }



}
