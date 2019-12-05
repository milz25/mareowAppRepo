package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class OfferOperatorandSupervisorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextInputEditText mOperatorName;
    TextInputEditText mSupervisorName;
    TextInputEditText mRate;


    AppCompatCheckBox mAccomodation;
    AppCompatCheckBox mFood;
    AppCompatCheckBox mTrasportation;

    TextInputLayout operatorRateHint;


    OfferWorkOrder offerWorkOrder;
    public OfferOperatorandSupervisorFragment(OfferWorkOrder offerWorkOrder) {
        // Required empty public constructor
        this.offerWorkOrder=offerWorkOrder;
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
        View view=inflater.inflate(R.layout.fragment_offer_operatorand_supervisor, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        operatorRateHint=(TextInputLayout)view.findViewById(R.id.ODF_oper_super_ratehint);

        mOperatorName=(TextInputEditText)view.findViewById(R.id.ODF_oper_super_opername);
        mOperatorName.setText(offerWorkOrder.getOperatorName());
        mOperatorName.setInputType(InputType.TYPE_NULL);

        mSupervisorName=(TextInputEditText)view.findViewById(R.id.ODF_oper_super_super_name);
        mSupervisorName.setText(offerWorkOrder.getSupervisorName());
        mSupervisorName.setInputType(InputType.TYPE_NULL);

        if (offerWorkOrder.getWorkorderDTO().isOperatorFixRateFlg()){
            operatorRateHint.setHint("Rate (FixRate)");
        }else {
            operatorRateHint.setHint("Rate (Per Hr.)");
        }

        mRate=(TextInputEditText)view.findViewById(R.id.ODF_oper_super_rate);
        mRate.setText("\u20B9 "+offerWorkOrder.getWorkorderDTO().getOperatorRate());
        mRate.setInputType(InputType.TYPE_NULL);


        mAccomodation=(AppCompatCheckBox)view.findViewById(R.id.ODF_oper_super_accomodation);
        mAccomodation.setChecked(offerWorkOrder.getWorkorderDTO().isAccomodation());
        mAccomodation.setKeyListener(null);

        mFood=(AppCompatCheckBox)view.findViewById(R.id.ODF_oper_super_food);
        mFood.setChecked(offerWorkOrder.getWorkorderDTO().isFood());
        mFood.setKeyListener(null);

        mTrasportation=(AppCompatCheckBox)view.findViewById(R.id.ODF_oper_super_transportation);
        mTrasportation.setChecked(offerWorkOrder.getWorkorderDTO().isTransportation());
        mTrasportation.setKeyListener(null);




    }

}
