package com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.SwitchCompat;
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
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanOperatorDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanOperatorDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editAmount;
    TextInputLayout amountHint;

    AppCompatCheckBox checkBoxOperatorIncluded;

    AppCompatRadioButton radioButtonFixed;
    AppCompatRadioButton radioButtonHourly;
    LinearLayout linearLayoutSection;

    AppCompatCheckBox switchAccommodation;
    AppCompatCheckBox switchTransportation;
    AppCompatCheckBox switchFood;

    public static boolean OPERATOR_FLAG=true;
    public static String OPERATOR_RATE=null ;
    public static boolean OPERATOR_RATE_FLAG=true;
    public static boolean ACCOMODATION=false;
    public static boolean TRANSPORTATION=false;
    public static boolean FOOD=false;






    public PlanOperatorDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanOperatorDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanOperatorDetailsFragment newInstance(String param1, String param2) {
        PlanOperatorDetailsFragment fragment = new PlanOperatorDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_plan_operator_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        linearLayoutSection=(LinearLayout)view.findViewById(R.id.ARO_OP_operator_included_section);
        editAmount=(TextInputEditText)view.findViewById(R.id.ARO_OP_working_charges);
        if (OPERATOR_RATE!=null){
            editAmount.setText(OPERATOR_RATE);
        }
        editAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               OPERATOR_RATE=s.toString();
            }
        });
        amountHint=(TextInputLayout)view.findViewById(R.id.ARO_OP_working_charges_hint);

        checkBoxOperatorIncluded=(AppCompatCheckBox)view.findViewById(R.id.ARO_OP_operator_included_checkbox);
        checkBoxOperatorIncluded.setChecked(OPERATOR_FLAG);
        checkBoxOperatorIncluded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   amountHint.setHint("Amount (\u20B9) *");
                   OPERATOR_FLAG=true;
                    linearLayoutSection.setVisibility(View.VISIBLE);
                }else {
                    amountHint.setHint("Amount (\u20B9)");
                    OPERATOR_FLAG=false;
                    linearLayoutSection.setVisibility(View.GONE);
                }
            }
        });

        radioButtonFixed=(AppCompatRadioButton)view.findViewById(R.id.ARO_OP_fixed);
        if (OPERATOR_RATE_FLAG){
            radioButtonFixed.setChecked(true);
        }
        radioButtonFixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                  OPERATOR_RATE_FLAG=true;
                }
            }
        });
        radioButtonHourly=(AppCompatRadioButton)view.findViewById(R.id.ARO_OP_hourly);
        if (!OPERATOR_RATE_FLAG){
            radioButtonHourly.setChecked(true);
        }
        radioButtonHourly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    OPERATOR_RATE_FLAG=false;
                }
            }
        });

        switchAccommodation=(AppCompatCheckBox)view.findViewById(R.id.ARO_OP_accommodation);
        switchAccommodation.setChecked(ACCOMODATION);
        switchAccommodation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                 ACCOMODATION=true;
               }else {
                   ACCOMODATION=false;
               }
            }
        });
        switchTransportation=(AppCompatCheckBox)view.findViewById(R.id.ARO_OP_transportation);
        switchTransportation.setChecked(TRANSPORTATION);
        switchTransportation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                  TRANSPORTATION=true;
                }else {
                    TRANSPORTATION=false;
                }
            }
        });
        switchFood=(AppCompatCheckBox)view.findViewById(R.id.ARO_OP_food);
        switchFood.setChecked(FOOD);
        switchFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    FOOD=true;
                }else {
                    FOOD=false;
                }
            }
        });

    }

}
