package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;


public class OfferTandCFragment extends Fragment implements View.OnClickListener{
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
    LinearLayout btnlogisticsTerms;
    LinearLayout btnCancellationTerms;


    OfferWorkOrder offerWorkOrder;
    public OfferTandCFragment(OfferWorkOrder offerWorkOrder) {
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
        View view=inflater.inflate(R.layout.fragment_offer_tand_c, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        btnGeneralTerms=(LinearLayout)view.findViewById(R.id.ODF_term_and_condition_general);
        btnGeneralTerms.setOnClickListener(this);
        btnCommercialTerms=(LinearLayout)view.findViewById(R.id.ODF_term_and_condition_commercial);
        btnCommercialTerms.setOnClickListener(this);
        btnPaymentTerms=(LinearLayout)view.findViewById(R.id.ODF_term_and_condition_payments);
        btnPaymentTerms.setOnClickListener(this);
        btnlogisticsTerms=(LinearLayout)view.findViewById(R.id.ODF_term_and_condition_logistics);
        btnlogisticsTerms.setOnClickListener(this);
        btnCancellationTerms=(LinearLayout)view.findViewById(R.id.ODF_term_and_condition_cancelterms);
        btnCancellationTerms.setOnClickListener(this);

    }


    private void showPopupDialog(String data,String title) {

        AppCompatTextView mTitle;
        WebView mWebViewData;
        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.term_and_condition_popup_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
        mTitle.setText(title);
        mWebViewData=(WebView)dialog.findViewById(R.id.RBD_popdialog_web_data);
        mWebViewData.loadData(data,"text/html",null);
        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.RBD_popdialog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ODF_term_and_condition_general:
                showPopupDialog(offerWorkOrder.getWorkorderDTO().getTermsDTO().getGeneralTermsdescription(),"General Terms");
                break;
            case R.id.ODF_term_and_condition_commercial:
                showPopupDialog(offerWorkOrder.getWorkorderDTO().getTermsDTO().getCommercialTermsdescription(),"Commercial Terms");
                break;
            case R.id.ODF_term_and_condition_payments:
                showPopupDialog(offerWorkOrder.getWorkorderDTO().getTermsDTO().getPaymentTermsdescription(),"Payments Terms");
                break;
            case R.id.ODF_term_and_condition_logistics:
                showPopupDialog(offerWorkOrder.getWorkorderDTO().getTermsDTO().getLogisticsTermsdescription(),"Logistics Terms");
                break;
            case R.id.ODF_term_and_condition_cancelterms:
                showPopupDialog(offerWorkOrder.getWorkorderDTO().getTermsDTO().getCancellationTermsdescription(),"Cancellation Terms");
                break;

        }
    }
}
