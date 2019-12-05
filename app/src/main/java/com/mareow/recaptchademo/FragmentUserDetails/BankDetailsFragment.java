package com.mareow.recaptchademo.FragmentUserDetails;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mareow.recaptchademo.MainActivityFragments.MyProfileFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextInputEditText editAcHolder;
    TextInputEditText editPayable;
    TextInputEditText editBank;
    TextInputEditText editAccountNo;
    TextInputEditText editIFSC;
    TextInputEditText editPaytm;

    RadioGroup radioGroupAccountType;
    AppCompatRadioButton radioSaving;
    AppCompatRadioButton radioCurrent;

    FloatingActionButton btnNext;
    //AppCompatTextView btnRight;
    //AppCompatTextView btnLeft;

    public static String mAccountHolder=null;
    public static String mPayableAt=null;
    public static String mBank=null;
    public static String mAccountNo=null;
    public static String mAccountType="SA";
    public static String mIFSCCode=null;
    public static String mPaytmAccountNo=null;




    public BankDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BankDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BankDetailsFragment newInstance(String param1, String param2) {
        BankDetailsFragment fragment = new BankDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_bank_details, container, false);
       // MainActivity.navItemIndex=16;
        initView(view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return view;
    }


    private void initView(View view){

        editAcHolder=(TextInputEditText)view.findViewById(R.id.bank_details_frg_account_holder);
        editAcHolder.setHorizontallyScrolling(false);
        editAcHolder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAccountHolder=s.toString();
            }
        });
        editAcHolder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAccountHolder=editAcHolder.getText().toString();
                }
            }
        });

        editPayable=(TextInputEditText)view.findViewById(R.id.bank_details_frg_payable);
        editPayable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPayableAt=s.toString();
            }
        });
        editPayable.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mPayableAt=editPayable.getText().toString();
                }
            }
        });
        editBank=(TextInputEditText)view.findViewById(R.id.bank_details_frg_bank);
        editBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBank=s.toString();
            }
        });
        editBank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mBank=editBank.getText().toString();
                }
            }
        });
        editAccountNo=(TextInputEditText)view.findViewById(R.id.bank_details_frg_account_no);
        editAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAccountNo=s.toString();
            }
        });
        editAccountNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mAccountNo=editAccountNo.getText().toString();
                }
            }
        });

        editIFSC=(TextInputEditText)view.findViewById(R.id.bank_details_frg_ifsc);
        editIFSC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mIFSCCode=s.toString();
            }
        });
        editIFSC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    mIFSCCode=editIFSC.getText().toString();
                }
            }
        });
        editPaytm=(TextInputEditText)view.findViewById(R.id.bank_details_frg_paytm);
        editPaytm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPaytmAccountNo=s.toString();
            }
        });
        editPaytm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mPaytmAccountNo=editPaytm.getText().toString();
                }
            }
        });
        editPaytm.setText(TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO,null));

       // btnNext=(FloatingActionButton) view.findViewById(R.id.bank_details_frg_next);
        //btnNext.setOnClickListener(this);

        radioSaving=(AppCompatRadioButton)view.findViewById(R.id.bank_deails_frg_saving);
        radioCurrent=(AppCompatRadioButton)view.findViewById(R.id.bank_deails_frg_current);


        radioGroupAccountType=(RadioGroup)view.findViewById(R.id.bank_deails_frg_radio_account_type);
        radioGroupAccountType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButtonAccountType = (RadioButton) radioGroupAccountType.findViewById(checkedId);
                String accountTypeText=radioButtonAccountType.getText().toString();

                if (accountTypeText.equals("Saving Account")){
                    mAccountType="SA";
                }
                if (accountTypeText.equals("Current Account")){
                    mAccountType="CA";
                }
            }
        });

        if (Constants.MY_PROFILE){
           if (MyProfileFragment.mUSerProfileDataList.getAccountHolder()!=null){
               if (mAccountHolder!=null){
                   editAcHolder.setText(mAccountHolder);
               }else {
                   editAcHolder.setText(MyProfileFragment.mUSerProfileDataList.getAccountHolder());
                   mAccountHolder=MyProfileFragment.mUSerProfileDataList.getAccountHolder();
               }
           }
           if (MyProfileFragment.mUSerProfileDataList.getPayableAtCity()!=null){

               if (mPayableAt!=null){
                   editPayable.setText(mPayableAt);
               }else {
                   editPayable.setText(MyProfileFragment.mUSerProfileDataList.getPayableAtCity());
                   mPayableAt=MyProfileFragment.mUSerProfileDataList.getPayableAtCity();
               }

           }
           if (MyProfileFragment.mUSerProfileDataList.getBank()!=null){
               if (mBank!=null){
                   editBank.setText(mBank);
               }else {
                   editBank.setText(MyProfileFragment.mUSerProfileDataList.getBank());
                   mBank=MyProfileFragment.mUSerProfileDataList.getBank();
               }

           }
           if (MyProfileFragment.mUSerProfileDataList.getAccountNo()!=null){
               if (mAccountNo!=null){
                   editAccountNo.setText(mAccountNo);
               }else {
                   editAccountNo.setText(MyProfileFragment.mUSerProfileDataList.getAccountNo());
                   mAccountNo=MyProfileFragment.mUSerProfileDataList.getAccountNo();
               }

           }

           if (MyProfileFragment.mUSerProfileDataList.getIfscCode()!=null){
               if (mIFSCCode!=null){
                   editIFSC.setText(mIFSCCode);
               }else {
                   editIFSC.setText(MyProfileFragment.mUSerProfileDataList.getIfscCode());
                   mIFSCCode=MyProfileFragment.mUSerProfileDataList.getIfscCode();
               }

           }

           if (MyProfileFragment.mUSerProfileDataList.getPaytmAccount()!=null){
               if (mPaytmAccountNo!=null){
                   editPaytm.setText(MyProfileFragment.mUSerProfileDataList.getPaytmAccount());
               }else {
                   editPaytm.setText(MyProfileFragment.mUSerProfileDataList.getPaytmAccount());
                   mPaytmAccountNo=MyProfileFragment.mUSerProfileDataList.getPaytmAccount();
               }
           }

           if (mAccountType!=null){
               if (mAccountType.equals("SA")){
                   radioSaving.setChecked(true);
               }else {
                   radioCurrent.setChecked(true);
               }
           }
        }else {

            if (mAccountHolder!=null){
                editAcHolder.setText(mAccountHolder);
            }
            if (mPayableAt!=null){
                editPayable.setText(mPayableAt);
            }
            if (mBank!=null){
                editBank.setText(mBank);
            }
            if (mAccountNo!=null){
                editAccountNo.setText(mAccountNo);
            }
            if (mIFSCCode!=null){
                editIFSC.setText(mIFSCCode);
            }
            if (mPaytmAccountNo!=null){
                editPaytm.setText(mPaytmAccountNo);
            }

        }

       /* btnRight=(AppCompatTextView)view.findViewById(R.id.bank_details_frg_right);
        btnRight.setOnClickListener(this);
        btnLeft=(AppCompatTextView)view.findViewById(R.id.bank_details_frg_left);
        btnLeft.setOnClickListener(this);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           /* case R.id.bank_details_frg_next:
                Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                break;*/
         /*   case R.id.bank_details_frg_right:
                callNextFragment();
                break;
            case R.id.bank_details_frg_left:
                //Toast.makeText(getContext(), "Left", Toast.LENGTH_SHORT).show();
                //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Fragment operatorchargeDetailsFragment = new OperatorchargeDetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_main, operatorchargeDetailsFragment); // give your fragment container id in first parameter
                transaction.commit();
                break;*/
        }
    }

   /* private void callNextFragment() {
        getAllData();
    }*/
/*

    private void getAllData() {
        mAccountHolder=editAcHolder.getText().toString();
        mPayableAt=editPayable.getText().toString();
        mBank=editBank.getText().toString();
        mAccountNo=editAccountNo.getText().toString();
        mIFSCCode=editIFSC.getText().toString();
        mPaytmAccountNo=editPaytm.getText().toString();

        int seletedAccountTypeId=radioGroupAccountType.getCheckedRadioButtonId();
        RadioButton radioButtonAccountType = (RadioButton) radioGroupAccountType.findViewById(seletedAccountTypeId);
        String accountTypeText=radioButtonAccountType.getText().toString();

        if (accountTypeText.equals("Saving Account")){
            mAccountType="SA";
        }
        if (accountTypeText.equals("Current Account")){
            mAccountType="CA";
        }

        if (mAccountHolder.isEmpty()){
           editAcHolder.setError("This value is reqiured.");
           editAcHolder.requestFocus();
           return;
        }
        if (mPayableAt.isEmpty()){
            editPayable.setError("This value is reqiured.");
            editPayable.requestFocus();
            return;
        }
        if (mBank.isEmpty()){
            editBank.setError("This value is reqiured.");
            editBank.requestFocus();
            return;
        }
        if (mAccountNo.isEmpty()){
            editAccountNo.setError("This value is reqiured.");
            editAccountNo.requestFocus();
            return;
        }
        if (mIFSCCode.isEmpty()){
            editIFSC.setError("This value is reqiured.");
            editIFSC.requestFocus();
            return;
        }
        if (mPaytmAccountNo.isEmpty()){
            editPaytm.setError("This value is reqiured.");
            editPaytm.requestFocus();
            return;
        }

        Fragment referFragment = new AddressFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container_main, referFragment); // give your fragment container id in first parameter
        transaction.commit();
    }
*/



}
