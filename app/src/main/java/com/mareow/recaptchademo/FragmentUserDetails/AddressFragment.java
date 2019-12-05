package com.mareow.recaptchademo.FragmentUserDetails;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText edit_Address1;
    private TextInputEditText edit_Address2;
    private TextInputEditText edit_Address3;
    private TextInputEditText edit_Address4;
    private TextInputEditText edit_City;
    private TextInputEditText edit_Pincode;
    private TextInputEditText edit_State;
    private TextInputEditText edit_Country;

    public static String mAddress1=null;
    public static String mAddress2=null;
    public static String mAddress3=null;
    public static String mAddress4=null;
    public static String mCity=null;
    public static String mPincode=null;
    public static String mState=null;
    public static String mCountry="India";


    private FloatingActionButton btnSave;
   // AppCompatTextView btnRight;
    //AppCompatTextView btnLeft;


    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_address, container, false);
       // MainActivity.navItemIndex=16;
        initView(view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }

    private void initView(View view) {
        edit_Address1=(TextInputEditText)view.findViewById(R.id.add_frg_address1);
        edit_Address1.addTextChangedListener(new TextWatcher() {
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
        edit_Address1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAddress1=edit_Address1.getText().toString();
                }
            }
        });

        edit_Address2=(TextInputEditText)view.findViewById(R.id.add_frg_address2);
        edit_Address2.addTextChangedListener(new TextWatcher() {
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
        edit_Address2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAddress2=edit_Address2.getText().toString();
                }
            }
        });
        edit_Address3=(TextInputEditText)view.findViewById(R.id.add_frg_address3);
        edit_Address3.addTextChangedListener(new TextWatcher() {
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

        edit_Address3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAddress3=edit_Address3.getText().toString();
                }
            }
        });
        edit_Address4=(TextInputEditText)view.findViewById(R.id.add_frg_address4);
        edit_Address4.addTextChangedListener(new TextWatcher() {
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
        edit_Address4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAddress4=edit_Address4.getText().toString();
                }
            }
        });
        edit_City=(TextInputEditText)view.findViewById(R.id.add_frg_city);
        edit_City.addTextChangedListener(new TextWatcher() {
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
        edit_City.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mCity=edit_City.getText().toString();
                }
            }
        });
        edit_Pincode=(TextInputEditText)view.findViewById(R.id.add_frg_pincode);
        edit_Pincode.addTextChangedListener(new TextWatcher() {
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
        edit_Pincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mPincode=edit_Pincode.getText().toString();
                }
            }
        });
        edit_State=(TextInputEditText)view.findViewById(R.id.add_frg_state);
        edit_State.addTextChangedListener(new TextWatcher() {
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
        edit_State.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mState=edit_State.getText().toString();
                }
            }
        });
        edit_Country=(TextInputEditText)view.findViewById(R.id.add_frg_country);
        edit_Country.setText(mCountry);
        edit_Country.addTextChangedListener(new TextWatcher() {
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
        /*edit_Country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mCountry=edit_Country.getText().toString();
                }
            }
        });*/

        if (Constants.MY_PROFILE){

            if (MyProfileFragment.mUSerProfileDataList.getAddress1()!=null){
                if (mAddress1!=null){
                    edit_Address1.setText(mAddress1);
                }else {
                    edit_Address1.setText(MyProfileFragment.mUSerProfileDataList.getAddress1());
                    mAddress1=MyProfileFragment.mUSerProfileDataList.getAddress1();
                }
            }

            if (MyProfileFragment.mUSerProfileDataList.getAddress2()!=null){
                if (mAddress2!=null){
                    edit_Address2.setText(mAddress2);
                }else {
                    edit_Address2.setText(MyProfileFragment.mUSerProfileDataList.getAddress2());
                    mAddress2=MyProfileFragment.mUSerProfileDataList.getAddress2();
                }

            }
            if (MyProfileFragment.mUSerProfileDataList.getAddress3()!=null){
                if (mAddress3!=null){
                    edit_Address3.setText(mAddress3);
                }else {
                    edit_Address3.setText(MyProfileFragment.mUSerProfileDataList.getAddress3());
                    mAddress3=MyProfileFragment.mUSerProfileDataList.getAddress3();
                }

            }

            if (MyProfileFragment.mUSerProfileDataList.getAddress4()!=null){
                if (mAddress4!=null){
                    edit_Address4.setText(MyProfileFragment.mUSerProfileDataList.getAddress4());
                }else {
                    edit_Address4.setText(MyProfileFragment.mUSerProfileDataList.getAddress4());
                    mAddress4=MyProfileFragment.mUSerProfileDataList.getAddress4();
                }

            }

            if (MyProfileFragment.mUSerProfileDataList.getCity()!=null){
                if (mCity!=null){
                    edit_City.setText(MyProfileFragment.mUSerProfileDataList.getCity());
                }else {
                    edit_City.setText(MyProfileFragment.mUSerProfileDataList.getCity());
                    mCity=MyProfileFragment.mUSerProfileDataList.getCity();
                }

            }

            if (MyProfileFragment.mUSerProfileDataList.getPostalCode()!=null){
                if (mPincode!=null){
                    edit_Pincode.setText(MyProfileFragment.mUSerProfileDataList.getPostalCode());
                }else {
                    edit_Pincode.setText(MyProfileFragment.mUSerProfileDataList.getPostalCode());
                    mPincode=MyProfileFragment.mUSerProfileDataList.getPostalCode();
                }

            }
            if (MyProfileFragment.mUSerProfileDataList.getState()!=null){
                if (mState!=null){
                    edit_State.setText(MyProfileFragment.mUSerProfileDataList.getState());
                }else {
                    edit_State.setText(MyProfileFragment.mUSerProfileDataList.getState());
                    mState=MyProfileFragment.mUSerProfileDataList.getState();
                }
            }

            if (MyProfileFragment.mUSerProfileDataList.getCountry()!=null){
                if (mCountry!=null){
                    edit_Country.setText(MyProfileFragment.mUSerProfileDataList.getCountry());
                }else {
                    edit_Country.setText(MyProfileFragment.mUSerProfileDataList.getCountry());
                    mCountry=MyProfileFragment.mUSerProfileDataList.getCountry();
                }
            }


        }else {

            if (mAddress1!=null){
                edit_Address1.setText(mAddress1);
            }
            if (mAddress2!=null){
                edit_Address2.setText(mAddress2);
            }
            if (mAddress3!=null){
                edit_Address3.setText(mAddress3);
            }
            if (mAddress4!=null){
                edit_Address4.setText(mAddress4);
            }
            if (mPincode!=null){
                edit_Pincode.setText(mPincode);
            }
            if (mCity!=null){
                edit_City.setText(mCity);
            }
            if (mState!=null){
                edit_State.setText(mState);
            }
            if (mCountry!=null){
                edit_Country.setText(mCountry);
            }else {
                edit_Country.setText("India");
            }

        }

       // btnSave=(FloatingActionButton) view.findViewById(R.id.add_frg_next);
       // btnSave.setOnClickListener(this);

//        btnLeft=(AppCompatTextView)view.findViewById(R.id.add_frg_left);
//        btnLeft.setOnClickListener(this);
//
//        btnRight=(AppCompatTextView)view.findViewById(R.id.add_frg_right);
//        btnRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
           /* case R.id.add_frg_next:
                Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                //getAllData();
                break;*/
//            case R.id.add_frg_right:
//                Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
//                getAllData();
//                break;
//            case R.id.add_frg_left:
//                //Toast.makeText(getContext(), "Next", Toast.LENGTH_SHORT).show();
//               // getAllData();
//                //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                if (Constants.USER_ROLE.equals("Supervisor")){
//                    Fragment generalDetailsFragment = new GeneralDetailsFragment();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_container_main, generalDetailsFragment ); // give your fragment container id in first parameter
//                    transaction.commit();
//                }else {
//                    Fragment bankDetailsFragment = new BankDetailsFragment();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragment_container_main, bankDetailsFragment ); // give your fragment container id in first parameter
//                    transaction.commit();
//                }
//                break;


        }
    }

    /*private void getAllData() {
        mAddress1=edit_Address1.getText().toString();
        mAddress2=edit_Address2.getText().toString();
        mAddress3=edit_Address3.getText().toString();
        mAddress4=edit_Address4.getText().toString();

        mCity=edit_City.getText().toString();
        mPincode=edit_Pincode.getText().toString();
        mState=edit_State.getText().toString();
        mCountry=edit_Country.getText().toString();

        if (TextUtils.isEmpty(mAddress1)){
            edit_Address1.setError("Enter address1");
            edit_Address1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mCity)){
            edit_City.setError("Enter city");
            edit_City.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mPincode)){
            edit_Pincode.setError("Enter pincode");
            edit_Pincode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mState)){
            edit_State.setError("Enter state");
            edit_State.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mCountry)){
            edit_Country.setError("Enter country");
            edit_Country.requestFocus();
            return;
        }


        Fragment governFragment = new GovernmentIdFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container_main, governFragment ); // give your fragment container id in first parameter
        transaction.commit();

    }*/
}
