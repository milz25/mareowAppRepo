package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class OfferGeneralDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextInputEditText mOwner;
    TextInputEditText mWorkDuration;
    TextInputEditText mMachine;
    TextInputEditText mModel;
    TextInputEditText mNoOfDays;
    TextInputEditText mNoOfHours;
    TextInputEditText mRentalPlane;
    TextInputEditText mEstimatedAmount;

    TextInputLayout hintOwnerOrRenter;

    AppCompatTextView txtGST;

    OfferWorkOrder offerWorkOrder;
    public OfferGeneralDetailsFragment(OfferWorkOrder offerWorkOrder) {
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
        View view=inflater.inflate(R.layout.fragment_offer_general_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {


        hintOwnerOrRenter=(TextInputLayout)view.findViewById(R.id.ODF_generalDetails_owner_hint);

        mOwner=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_owner);
        mWorkDuration=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_duration);
        mMachine=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_machine);
       // mModel=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_machinemodel);
        mNoOfDays=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_noofdays);
        mNoOfHours=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_noofhours);
        mRentalPlane=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_rentalplan);


        if (Constants.USER_ROLE.equals("Renter")){
            hintOwnerOrRenter.setHint("Owner");
            mOwner.setText(offerWorkOrder.getWorkorderDTO().getWorkOrderOwner());
        }

        if (Constants.USER_ROLE.equals("Owner")){
            hintOwnerOrRenter.setHint("Renter");
            mOwner.setText(offerWorkOrder.getWorkorderDTO().getWorkOrderRenter());
        }

        mOwner.setInputType(InputType.TYPE_NULL);

        mWorkDuration.setText(Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkStartDate())+" to "+Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkEndDate()));
        mWorkDuration.setInputType(InputType.TYPE_NULL);

        mMachine.setText(offerWorkOrder.getWorkorderDTO().getMachine().getMachineName());
        mMachine.setInputType(InputType.TYPE_NULL);

      //  mModel.setText(offerWorkOrder.getWorkorderDTO().getMachine().getMachineName()+"/"+offerWorkOrder.getWorkorderDTO().getMachine().getModelNo());
       // mModel.setInputType(InputType.TYPE_NULL);

        mNoOfDays.setText(String.valueOf(offerWorkOrder.getWorkorderDTO().getNoOfDays()));
        mNoOfDays.setInputType(InputType.TYPE_NULL);

        mNoOfHours.setText(String.valueOf(offerWorkOrder.getWorkorderDTO().getReqHour()));
        mNoOfHours.setInputType(InputType.TYPE_NULL);


        mRentalPlane.setText(offerWorkOrder.getWorkorderDTO().getPlanType()+" - "+offerWorkOrder.getWorkorderDTO().getPlanUsage());
        mRentalPlane.setInputType(InputType.TYPE_NULL);
        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        mEstimatedAmount=(TextInputEditText)view.findViewById(R.id.ODF_generalDetails_estimated_amount);
        txtGST=(AppCompatTextView)view.findViewById(R.id.ODF_generalDetails_estimated_amount_gst);
        if (Constants.CHECK_OFFER){
            mEstimatedAmount.setVisibility(View.GONE);
            txtGST.setVisibility(View.GONE);
        }else {
            mEstimatedAmount.setVisibility(View.VISIBLE);
            txtGST.setVisibility(View.VISIBLE);
            mEstimatedAmount.setText("\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkOrderAmt()));
        }


    }

}
