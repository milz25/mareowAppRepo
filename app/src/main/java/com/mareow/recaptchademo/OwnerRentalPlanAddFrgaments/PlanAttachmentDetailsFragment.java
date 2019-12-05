package com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
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
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanAttachmentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanAttachmentDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AppCompatCheckBox checkBoxAttachmentIncluded;
    AppCompatCheckBox checkBoxExtraAttachment;


    AppCompatRadioButton radioButtonFixed;
    AppCompatRadioButton radioButtonHourly;

    TextInputEditText editExtraAttRate;
    TextInputLayout extraHint;

    LinearLayout linearLayoutSection;

    public static boolean DEFUALT_ATTACHMENT=true;
    public static boolean EXTRA_ATTACHMENT=false;
    public static boolean EXTRA_ATTACHMENT_RATE_FLAG=true;

    public static String EXTRA_ATTACHMENT_RATE=null;








    public PlanAttachmentDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanAttachmentDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanAttachmentDetailsFragment newInstance(String param1, String param2) {
        PlanAttachmentDetailsFragment fragment = new PlanAttachmentDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_plan_attachment_details, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        linearLayoutSection=(LinearLayout)view.findViewById(R.id.ARO_AD_extra_attachment_section);
        linearLayoutSection.setVisibility(View.GONE);
        checkBoxAttachmentIncluded=(AppCompatCheckBox)view.findViewById(R.id.ARO_AD_attachment_rate_included);
        checkBoxAttachmentIncluded.setChecked(true);
        checkBoxAttachmentIncluded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    DEFUALT_ATTACHMENT=true;
                }else {
                    DEFUALT_ATTACHMENT=false;
                }
            }
        });

        checkBoxExtraAttachment=(AppCompatCheckBox)view.findViewById(R.id.ARO_AD_extra_attachment);
        checkBoxExtraAttachment.setChecked(EXTRA_ATTACHMENT);
        checkBoxExtraAttachment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   EXTRA_ATTACHMENT=true;
                   extraHint.setHint("Attachment  Rate *");
                   linearLayoutSection.setVisibility(View.VISIBLE);
               }else {
                   extraHint.setHint("Attachment  Rate");
                   EXTRA_ATTACHMENT=false;
                   linearLayoutSection.setVisibility(View.GONE);
               }
            }
        });

        radioButtonFixed=(AppCompatRadioButton)view.findViewById(R.id.ARO_AD_fixed_monthly);
        if (EXTRA_ATTACHMENT_RATE_FLAG){
            radioButtonFixed.setChecked(true);
        }
        radioButtonFixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    EXTRA_ATTACHMENT_RATE_FLAG=true;
                    radioButtonHourly.setChecked(false);
                }
            }
        });
        radioButtonHourly=(AppCompatRadioButton)view.findViewById(R.id.ARO_AD_hourly_basis);
        if (!EXTRA_ATTACHMENT_RATE_FLAG){
            radioButtonHourly.setChecked(true);
        }
        radioButtonHourly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    EXTRA_ATTACHMENT_RATE_FLAG=false;
                    radioButtonFixed.setChecked(false);
                }
            }
        });


        editExtraAttRate=(TextInputEditText)view.findViewById(R.id.ARO_AD_extra_attachment_rate);
        if (EXTRA_ATTACHMENT_RATE!=null){
            editExtraAttRate.setText(EXTRA_ATTACHMENT_RATE);
        }
        editExtraAttRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               EXTRA_ATTACHMENT_RATE=s.toString();
            }
        });
        extraHint=(TextInputLayout)view.findViewById(R.id.ARO_AD_extra_attachment_hint);
    }




}
