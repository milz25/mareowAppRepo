package com.mareow.recaptchademo.RenterMachineBookFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SiteLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SiteLocationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editAddress1;
    TextInputEditText editAddress2;
    TextInputEditText editAddress3;
    TextInputEditText editAddress4;
    TextInputEditText editCity;
    TextInputEditText editPincode;
    TextInputEditText editState;
    TextInputEditText editCountry;


    public static String mAddress1;
    public static String mAddress2;
    public static String mAddress3;
    public static String mAddress4;
    public static String mCity;
    public static String mPincode;
    public static String mState;
    public static String mCountry;

    public SiteLocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SiteLocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SiteLocationFragment newInstance(String param1, String param2) {
        SiteLocationFragment fragment = new SiteLocationFragment();
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
        View view=inflater.inflate(R.layout.fragment_site_location, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        editAddress1=(TextInputEditText)view.findViewById(R.id.RBD_site_location_address1);
        editAddress1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddress1=s.toString();
            }
        });
       /* editAddress1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                   mAddress1=editAddress1.getText().toString();
                }
            }
        });*/
        editAddress2=(TextInputEditText)view.findViewById(R.id.RBD_site_location_address2);
        editAddress2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddress2=s.toString();
            }
        });
       /* editAddress2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAddress2=editAddress2.getText().toString();
                }
            }
        });*/
        editAddress3=(TextInputEditText)view.findViewById(R.id.RBD_site_location_address3);
        editAddress3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddress3=s.toString();
            }
        });
       /* editAddress3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAddress3=editAddress3.getText().toString();
                }
            }
        });*/
        editAddress4=(TextInputEditText)view.findViewById(R.id.RBD_site_location_address4);
        editAddress4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddress4=s.toString();
            }
        });
       /* editAddress4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAddress4=editAddress4.getText().toString();
                }
            }
        });*/
        editCity=(TextInputEditText)view.findViewById(R.id.RBD_site_location_city);
        editCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCity=s.toString();
            }
        });
       /* editCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mCity=editCity.getText().toString();
                }
            }
        });*/
        editPincode=(TextInputEditText)view.findViewById(R.id.RBD_site_location_pincode);
        editPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPincode=s.toString();
            }
        });
       /* editPincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mPincode=editPincode.getText().toString();
                }
            }
        });*/
        editState=(TextInputEditText)view.findViewById(R.id.RBD_site_location_state);
        editState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mState=s.toString();
            }
        });
      /* editState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if (!hasFocus){
                   mState=editState.getText().toString();
               }
           }
       });*/
        editCountry=(TextInputEditText)view.findViewById(R.id.RBD_site_location_country);
        editCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCountry=s.toString();
            }
        });
        editCountry.setText("India");
        mCountry=editCountry.getText().toString();
        /*editCountry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mCountry=editCountry.getText().toString();
                }
            }
        });*/


    }

}
