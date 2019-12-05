package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;

public class OfferMobilizeDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText mMobRespon;
    TextInputEditText mMobAmount;
    TextInputEditText mDemobRespon;
    TextInputEditText mDemobAmount;

    TextInputLayout mMoblityRateHint;
    TextInputLayout mDeMoblityRateHint;

   OfferWorkOrder offerWorkOrder;
    public OfferMobilizeDetailsFragment(OfferWorkOrder offerWorkOrder) {
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
        View view=inflater.inflate(R.layout.fragment_offer_mobilize_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mMoblityRateHint=(TextInputLayout)view.findViewById(R.id.ODF_mobilze_mob_amounthint);
        mDeMoblityRateHint=(TextInputLayout)view.findViewById(R.id.ODF_mobilze_demob_amounthint);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        mMobAmount=(TextInputEditText)view.findViewById(R.id.ODF_mobilze_mob_amount);
        mDemobAmount=(TextInputEditText)view.findViewById(R.id.ODF_mobilze_demob_amount);

        mMobRespon=(TextInputEditText)view.findViewById(R.id.ODF_mobilze_mob_responsbility);
        mMobRespon.setText(offerWorkOrder.getWorkorderDTO().getMobilityResponsible());
        mMobRespon.setInputType(InputType.TYPE_NULL);

        if (offerWorkOrder.getWorkorderDTO().isMobilityAmtFlg()){
            mMoblityRateHint.setHint("Amount (Fix Rate)");
            mMobAmount.setText("\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkorderDTO().getMobilityFixedAmt()));
        }else {
            mMoblityRateHint.setHint("Amount (Per Km)");
            mMobAmount.setText("\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkorderDTO().getMobilityPerKmRate()));
        }
        mMobAmount.setInputType(InputType.TYPE_NULL);


        mDemobRespon=(TextInputEditText)view.findViewById(R.id.ODF_mobilze_demob_responsbility);
        mDemobRespon.setText(offerWorkOrder.getWorkorderDTO().getDemobilityResponsible());
        mDemobRespon.setInputType(InputType.TYPE_NULL);

        if (offerWorkOrder.getWorkorderDTO().isMobilityAmtFlg()){
            mDeMoblityRateHint.setHint("Amount (Fix Rate)");
            mDemobAmount.setText("\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkorderDTO().getDemobilityFixedAmt()));
        }else {
            mDeMoblityRateHint.setHint("Amount (Per Km)");
            mDemobAmount.setText("\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkorderDTO().getDemobilityPerKmRate()));
        }
        mDemobAmount.setInputType(InputType.TYPE_NULL);

    }

    
}
