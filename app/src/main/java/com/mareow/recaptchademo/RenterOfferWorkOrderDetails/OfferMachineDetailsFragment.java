package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class OfferMachineDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText mMachineName;
    TextInputEditText mCategory;
    TextInputEditText mModel;
    TextInputEditText mMachineDescription;
    TextInputEditText mRegisNo;
    TextInputEditText mManuYear;
    TextInputEditText mLoca_Current;
    TextInputEditText mLoca_Site;

    TextInputEditText mExpectedDate;
    TextInputEditText mDateOfCommencement;
    TextInputEditText mCapacity;
    TextInputEditText mRemarks;
    TextInputEditText mOpeningKmr;
    TextInputEditText mOpningHmr;

    AppCompatTextView mMachine;

    FloatingActionButton currentMap;
    FloatingActionButton siteMap;

     OfferWorkOrder offerWorkOrder;
    public OfferMachineDetailsFragment(OfferWorkOrder offerWorkOrder) {
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
        View view=inflater.inflate(R.layout.fragment_offer_machine_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {


       /* mMachine=(AppCompatTextView)view.findViewById(R.id.ODF_machineDetails_machinewithmodel);
        mMachine.setText(offerWorkOrder.getWorkorderDTO().getMachine().getCategoryName()+"/"+offerWorkOrder.getWorkorderDTO().getMachine().getSubCategoryName()+"/"+
                       offerWorkOrder.getWorkorderDTO().getMachine().getManufacturerName());*/

       // mMachineName=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_machinename);
       // mMachineName.setText(offerWorkOrder.getWorkorderDTO().getMachine().getMachineName());
       // mMachineName.setInputType(InputType.TYPE_NULL);

        mCategory=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_cate_sub);
        mCategory.setText(offerWorkOrder.getWorkorderDTO().getMachine().getCategoryName()+"/"+offerWorkOrder.getWorkorderDTO().getMachine().getSubCategoryName());
        mCategory.setInputType(InputType.TYPE_NULL);

        mModel=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_mfg);
        mModel.setText(offerWorkOrder.getWorkorderDTO().getMachine().getManufacturerName()+"/"+offerWorkOrder.getWorkorderDTO().getMachine().getModelNo());
        mModel.setInputType(InputType.TYPE_NULL);



        mMachineDescription=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_machinedescription);
        if (offerWorkOrder.getWorkorderDTO().getMachine().getMachineDesc()!=null){
            mMachineDescription.setText(offerWorkOrder.getWorkorderDTO().getMachine().getMachineDesc());
        }else {
            mMachineDescription.setText(" ");
        }

        mMachineDescription.setInputType(InputType.TYPE_NULL);

        mRegisNo=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_registration_no);
        if (offerWorkOrder.getWorkorderDTO().getMachine().getMachineDesc()!=null){
            mRegisNo.setText(offerWorkOrder.getWorkorderDTO().getMachine().getRegisterNo());
        }else {
            mRegisNo.setText(" ");
        }

        mRegisNo.setInputType(InputType.TYPE_NULL);

        mManuYear=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_manu_year);
        mManuYear.setText(offerWorkOrder.getWorkorderDTO().getMachine().getManufacturerYear());
        mManuYear.setInputType(InputType.TYPE_NULL);

        mLoca_Current=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_loca_current);
        mLoca_Current.setText(offerWorkOrder.getWorkorderDTO().getMachine().getCurrentLocation()+" "+offerWorkOrder.getWorkorderDTO().getMachine().getCurrentPostalCode());
        mLoca_Current.setInputType(InputType.TYPE_NULL);


        mLoca_Site=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_loca_site);
        mLoca_Site.setText(offerWorkOrder.getWorkLocationSite());
        mLoca_Site.setInputType(InputType.TYPE_NULL);

        mExpectedDate=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_expected_date);
        mExpectedDate.setText(Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getExpectedDateDelivery()));
        mExpectedDate.setInputType(InputType.TYPE_NULL);


        mDateOfCommencement=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_date_of_commencement);
        mDateOfCommencement.setText(Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getCommencementDate()));
        mDateOfCommencement.setInputType(InputType.TYPE_NULL);

        mCapacity=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_capacity);
        mCapacity.setText(offerWorkOrder.getWorkorderDTO().getLoad());
        mCapacity.setInputType(InputType.TYPE_NULL);

        mRemarks=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_remarks);
        mRemarks.setText(offerWorkOrder.getWorkorderDTO().getRemarksofQCELorNACIEL());
        mRemarks.setInputType(InputType.TYPE_NULL);

        mOpeningKmr=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_opening_kmr);
        mOpeningKmr.setText(String.valueOf(offerWorkOrder.getWorkorderDTO().getMachine().getOdometer()));
        mOpeningKmr.setInputType(InputType.TYPE_NULL);

        mOpningHmr=(TextInputEditText)view.findViewById(R.id.ODF_machineDetails_Opening_Hmr);
        mOpningHmr.setText(String.valueOf(offerWorkOrder.getWorkorderDTO().getMachine().getRunHours()));
        mOpningHmr.setInputType(InputType.TYPE_NULL);

        currentMap=(FloatingActionButton)view.findViewById(R.id.ODF_machineDetails_loca_current_map);
        currentMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=" + offerWorkOrder.getWorkorderDTO().getMachine().getCurrentLocation()+" "+offerWorkOrder.getWorkorderDTO().getMachine().getCurrentPostalCode();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });
        siteMap=(FloatingActionButton)view.findViewById(R.id.ODF_machineDetails_loca_site_map);
        siteMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=" + offerWorkOrder.getWorkLocationSite();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });

    }

}
