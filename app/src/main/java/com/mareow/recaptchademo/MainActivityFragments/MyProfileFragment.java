package com.mareow.recaptchademo.MainActivityFragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.DetailActivityViewPagerAdapter;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.ProfileData;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.FragmentUserDetails.AddressFragment;
import com.mareow.recaptchademo.FragmentUserDetails.BankDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.GeneralDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.GovernmentIdFragment;
import com.mareow.recaptchademo.FragmentUserDetails.OperatorHomeFragment;
import com.mareow.recaptchademo.FragmentUserDetails.OperatorchargeDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.OwnerHomeFragment;
import com.mareow.recaptchademo.FragmentUserDetails.ReferDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.RenterHomeFragment;
import com.mareow.recaptchademo.FragmentUserDetails.SegmentOwnerFragment;
import com.mareow.recaptchademo.FragmentUserDetails.SupervisorHomeFragment;
import com.mareow.recaptchademo.FragmentUserDetails.TellAboutFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.CircleTransform;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static ViewPager myProfileViewPager;
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    DetailActivityViewPagerAdapter adapter;

    public static ProfileData mUSerProfileDataList = new ProfileData();
    String referType;
    ProgressDialog progressDialog;

    public FloatingActionButton mButtonSave;
    FragmentManager childFragMang;
    public static HashMap<String,String> govermentMap=new HashMap<>();
    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_profile_main, container, false);
        if (Constants.USER_ROLE.equals("Renter")) {
            RenterMainActivity.txtRenterTitle.setText("My Profile");
        } else if (Constants.USER_ROLE.equals("Owner")) {
            OwnerMainActivity.txtOwnerTitle.setText("My Profile");
        } else {
            MainActivity.txtTitle.setText("My Profile");
        }

        if (getActivity() != null) {

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait.......");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        childFragMang = getChildFragmentManager();
        callGovermentIdApi();

        new AsynkTaksForProfileData().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        initView(view);
        return view;
    }

 /*   private void callProfileData() {
        progressDialog.show();
        String token= TokenManager.getSessionToken();
        int userId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<ProfileData>> callProfile=apiInterface.getUserProfileAllData("Bearer "+token,userId);
        callProfile.enqueue(new Callback<ArrayList<ProfileData>>() {
            @Override
            public void onResponse(Call<ArrayList<ProfileData>> call, Response<ArrayList<ProfileData>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    mUserProfileData=response.body();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getContext(),mError.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProfileData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void initView(View view) {

        myProfileViewPager = (ViewPager) view.findViewById(R.id.myprofile_viewpager);
        llPagerDots = (LinearLayout) view.findViewById(R.id.myprofile_pager_dots);
        myProfileViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    if (Constants.USER_ROLE.equals("Renter")) {
                        RenterMainActivity.navItemIndexRenter = 17;
                    } else if (Constants.USER_ROLE.equals("Owner")) {
                        OwnerMainActivity.navItemIndexOwner = 17;
                    } else {
                        MainActivity.navItemIndex = 17;
                    }
                    mButtonSave.setVisibility(View.GONE);

                } else {

                    if (Constants.USER_ROLE.equals("Renter")) {
                        RenterMainActivity.navItemIndexRenter = 16;
                    } else if (Constants.USER_ROLE.equals("Owner")) {
                        OwnerMainActivity.navItemIndexOwner = 16;
                    } else {
                        MainActivity.navItemIndex = 16;
                    }

                    mButtonSave.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dots);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected_dots);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mButtonSave = (FloatingActionButton) view.findViewById(R.id.myprofile_main_Save);
        mButtonSave.setVisibility(View.GONE);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiSaveCall();
            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {

        if (getActivity() != null && isAdded()) {

            adapter = new DetailActivityViewPagerAdapter(childFragMang);

            String signupType = Constants.USER_ROLE;

            if (signupType.equals("Owner")) {

                adapter.addFragment(new OwnerHomeFragment(), "OwnerHome");
                adapter.addFragment(new GeneralDetailsFragment(), "GeneralDetails");
                adapter.addFragment(new AddressFragment(), "Address");
                adapter.addFragment(new GovernmentIdFragment(), "Government");
                adapter.addFragment(new BankDetailsFragment(), "Bank");
                adapter.addFragment(new ReferDetailsFragment(), "ReferDetails");

            } else if (signupType.equals("Renter")) {

                adapter.addFragment(new RenterHomeFragment(), "RenterHome");
                adapter.addFragment(new GeneralDetailsFragment(), "GeneralDetails");
                adapter.addFragment(new AddressFragment(), "Address");
                adapter.addFragment(new GovernmentIdFragment(), "Government");
                adapter.addFragment(new BankDetailsFragment(), "Bank");
                adapter.addFragment(new ReferDetailsFragment(), "ReferDetails");


            } else if (signupType.equals("Supervisor")) {
                adapter.addFragment(new SupervisorHomeFragment(), "SupervisorHome");
                adapter.addFragment(new GeneralDetailsFragment(), "GeneralDetails");
                adapter.addFragment(new AddressFragment(), "Address");
                adapter.addFragment(new GovernmentIdFragment(), "Government");
                adapter.addFragment(new ReferDetailsFragment(), "ReferDetails");
                //adapter.addFragment(new TellAboutFragment(), "TellMeAbout");

            } else if (signupType.equals("Operator")) {
                adapter.addFragment(new OperatorHomeFragment(), "OperatorHome");
                adapter.addFragment(new GeneralDetailsFragment(), "GeneralDetails");
                adapter.addFragment(new OperatorchargeDetailsFragment(), "Operator");
                adapter.addFragment(new AddressFragment(), "Address");
                adapter.addFragment(new BankDetailsFragment(), "Bank");
                adapter.addFragment(new GovernmentIdFragment(), "Government");
                adapter.addFragment(new ReferDetailsFragment(), "ReferDetails");
            }

            viewPager.setAdapter(adapter);

        }

    }

    private void setupPagerIndidcatorDots() {
        ivArrayDotsPager = new ImageView[adapter.getCount()];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dots);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
            llPagerDots.bringToFront();
        }
    }


    public class AsynkTaksForProfileData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();

            if (mUSerProfileDataList==null){
                TokenExpiredUtils.tokenExpired(getActivity());
                return;
            }

            setupViewPager(myProfileViewPager);
            setupPagerIndidcatorDots();
            ivArrayDotsPager[0].setImageResource(R.drawable.selected_dots);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String token = TokenManager.getSessionToken();
            int userId = TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID, 0);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<ProfileData> callProfile = apiInterface.getUserProfileAllData("Bearer " + token, userId);
            try {
                mUSerProfileDataList = callProfile.execute().body();
                if (mUSerProfileDataList != null) {
                    callSetPrimaryData();
                    TokenManager.updateUuserDetails(mUSerProfileDataList);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void callSetPrimaryData() {

        GeneralDetailsFragment.firstName = mUSerProfileDataList.getFirstName();
        GeneralDetailsFragment.lastName = mUSerProfileDataList.getLastName();
        GeneralDetailsFragment.mAboutYourSelf = mUSerProfileDataList.getAboutYourself();

        OperatorchargeDetailsFragment.OperatorWorkAssociation = mUSerProfileDataList.getAssociation();
        OperatorchargeDetailsFragment.AssociatedWith = mUSerProfileDataList.getAssociatedTo();
        if (Constants.USER_ROLE.equals("Operator")) {
            if (mUSerProfileDataList.getAssociation().equals("ASSOC")) {
                callAssociatedOwnerName();
            }
        }
        // OperatorchargeDetailsFragment.AssociatedWith=mUSerProfileDataList.getAssociatedTo();
        OperatorchargeDetailsFragment.certificateToRunPath = mUSerProfileDataList.getRunCertificatePath();
        OperatorchargeDetailsFragment.credentialsForAgencyPath = mUSerProfileDataList.getMachineCredentialsPath();
        OperatorchargeDetailsFragment.operatorAmount = String.valueOf(mUSerProfileDataList.getAttribute2());
        OperatorchargeDetailsFragment.AMOUNTTYPE = mUSerProfileDataList.isAttribute1();
        OperatorchargeDetailsFragment.ACCOMODATION = mUSerProfileDataList.isAttribute3();
        OperatorchargeDetailsFragment.TRASPORTATION = mUSerProfileDataList.isAttribute4();
        OperatorchargeDetailsFragment.FOOD = mUSerProfileDataList.isAttribute5();


        AddressFragment.mAddress1 = mUSerProfileDataList.getAddress1();
        AddressFragment.mAddress2 = mUSerProfileDataList.getAddress2();
        AddressFragment.mAddress3 = mUSerProfileDataList.getAddress3();
        AddressFragment.mAddress4 = mUSerProfileDataList.getAddress4();
        AddressFragment.mPincode = mUSerProfileDataList.getPostalCode();
        AddressFragment.mCity = mUSerProfileDataList.getCity();
        AddressFragment.mState = mUSerProfileDataList.getState();
        AddressFragment.mCountry = mUSerProfileDataList.getCountry();


        BankDetailsFragment.mAccountHolder = mUSerProfileDataList.getAccountHolder();
        BankDetailsFragment.mPayableAt = mUSerProfileDataList.getPayableAtCity();
        BankDetailsFragment.mBank = mUSerProfileDataList.getBank();
        BankDetailsFragment.mAccountNo = mUSerProfileDataList.getAccountNo();
        BankDetailsFragment.mAccountType=mUSerProfileDataList.getAccountType();
        BankDetailsFragment.mIFSCCode = mUSerProfileDataList.getIfscCode();
        BankDetailsFragment.mPaytmAccountNo = mUSerProfileDataList.getPaytmAccount();

        GovernmentIdFragment.govId = mUSerProfileDataList.getGovtProofId();
        GovernmentIdFragment.govProofPath = mUSerProfileDataList.getGovtProofDocPath();
        GovernmentIdFragment.gstNo = mUSerProfileDataList.getGstNumber();
        GovernmentIdFragment.gstProofDocPath = mUSerProfileDataList.getGstDocumentPath();


        ReferDetailsFragment.mRefer_Type = mUSerProfileDataList.getReferType();
        ReferDetailsFragment.mRefer_By = mUSerProfileDataList.getReferBy();
        ReferDetailsFragment.mRefer_MobileNo = mUSerProfileDataList.getReferMobileNo();


    }


    public void apiSaveCall() {

        if (GeneralDetailsFragment.firstName == null || TextUtils.isEmpty(GeneralDetailsFragment.firstName)) {
            showSnackbar( "Please select firstname");
            return;
        }

        if (GeneralDetailsFragment.lastName == null || TextUtils.isEmpty(GeneralDetailsFragment.lastName)) {
            showSnackbar("Please select lastname");
            return;
        }

        if (Constants.USER_ROLE.equals("Operator") || Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")) {
            if (GeneralDetailsFragment.segmentList.size() == 0) {
                showSnackbar("Please select Segment");
                return;
            }
        }

        if (Constants.USER_ROLE.equals("Operator")) {
            if (TextUtils.isEmpty(OperatorchargeDetailsFragment.OperatorWorkAssociation) || OperatorchargeDetailsFragment.OperatorWorkAssociation == null) {
                showSnackbar("Please select work association");
                return;
            } else if (TextUtils.isEmpty(OperatorchargeDetailsFragment.operatorAmount) || OperatorchargeDetailsFragment.operatorAmount == null) {
                showSnackbar("Please select operator amount");
                return;
            }

        }

        if (TextUtils.isEmpty(AddressFragment.mAddress1) || AddressFragment.mAddress1 == null) {
            showSnackbar("Please select address1");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mCity) || AddressFragment.mCity == null) {
            showSnackbar("Please select city");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mPincode) || AddressFragment.mCity == null) {
            showSnackbar("Please select pincode");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mState) || AddressFragment.mState == null) {
            showSnackbar( "Please select state");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mCountry) || AddressFragment.mCountry == null) {
            showSnackbar("Please select country");
            return;
        }/* else if (TextUtils.isEmpty(GovernmentIdFragment.govId) || GovernmentIdFragment.govId==null) {
            Toast.makeText(getActivity(), "Please select goverment id", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(GovernmentIdFragment.govProofPath) || GovernmentIdFragment.govProofPath==null) {
            Toast.makeText(getActivity(), "Please select goverment proof", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (Constants.USER_ROLE.equals("Operator")) {

            if (TextUtils.isEmpty(GovernmentIdFragment.govId) || GovernmentIdFragment.govId == null) {
                showSnackbar("Please select goverment id");
                return;
            } else if (TextUtils.isEmpty(GovernmentIdFragment.govProofPath) || GovernmentIdFragment.govProofPath == null) {
                showSnackbar("Please select goverment proof");
                return;
            }

        } else {

            if (GovernmentIdFragment.govId != null && !TextUtils.isEmpty(GovernmentIdFragment.govId)) {

                if (TextUtils.isEmpty(GovernmentIdFragment.govProofPath) || GovernmentIdFragment.govProofPath == null) {
                    showSnackbar("Please select goverment proof");
                    return;
                }

            }

            if (Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")){

                if (GovernmentIdFragment.gstNo != null && !TextUtils.isEmpty(GovernmentIdFragment.gstNo)) {
                    if (TextUtils.isEmpty(GovernmentIdFragment.gstProofDocPath) || GovernmentIdFragment.gstProofDocPath == null) {
                        showSnackbar( "Please select gst proof");
                        return;
                    }
                }
            }



        }


        if (Constants.MY_PROFILE && Constants.USER_ROLE.equals("Operator") || Constants.MY_PROFILE && Constants.USER_ROLE.equals("Renter") || Constants.MY_PROFILE && Constants.USER_ROLE.equals("Owner")) {
            if (BankDetailsFragment.mAccountHolder == null || TextUtils.isEmpty(BankDetailsFragment.mAccountHolder)) {
                showSnackbar("Please select account holder");
                return;
            } else if (BankDetailsFragment.mPayableAt == null || TextUtils.isEmpty(BankDetailsFragment.mPayableAt)) {
                showSnackbar("Please select payable at");
                return;
            } else if (BankDetailsFragment.mBank == null || BankDetailsFragment.mBank.isEmpty()) {
                showSnackbar( "Please select bank name");
                return;
            } else if (BankDetailsFragment.mAccountNo == null || TextUtils.isEmpty(BankDetailsFragment.mAccountNo)) {
                showSnackbar("Please select goverment proof");
                return;
            } else if (BankDetailsFragment.mIFSCCode == null || TextUtils.isEmpty(BankDetailsFragment.mIFSCCode)) {
                showSnackbar("Please select bank iFSC");
                return;
            } else if (BankDetailsFragment.mPaytmAccountNo == null || TextUtils.isEmpty(BankDetailsFragment.mPaytmAccountNo)) {
                showSnackbar("Please select paytm number");
                return;
            }
        }

        if (ReferDetailsFragment.mRefer_Type != null) {

            if (ReferDetailsFragment.mRefer_By == null || TextUtils.isEmpty(ReferDetailsFragment.mRefer_By)) {
                showSnackbar( "Please refer by");
                return;
            }

            if (ReferDetailsFragment.mRefer_MobileNo == null || TextUtils.isEmpty(ReferDetailsFragment.mRefer_MobileNo) || ReferDetailsFragment.mRefer_MobileNo.length() < 10) {
                showSnackbar("Please enter valid refer mobile");
                return;
            }


        }
        MultipartBody.Part uRCPart = null;
        MultipartBody.Part uMCPart = null;

        if (Constants.USER_ROLE.equals("Operator")) {
            File URCFile = null;
            if (OperatorchargeDetailsFragment.certificateToRunPath != null) {

                if (OperatorchargeDetailsFragment.certificateToRunPath.startsWith("uploads/")) {

                } else {
                    URCFile = new File(OperatorchargeDetailsFragment.certificateToRunPath);
                    RequestBody uRCImage = RequestBody.create(MediaType.parse("*/*"), URCFile);
                    uRCPart = MultipartBody.Part.createFormData("URC", URCFile.getName(), uRCImage);
                }

            }
            if (OperatorchargeDetailsFragment.credentialsForAgencyPath != null) {
                File UMCFile = null;
                if (OperatorchargeDetailsFragment.credentialsForAgencyPath.startsWith("uploads/")) {

                } else {

                    UMCFile = new File(OperatorchargeDetailsFragment.credentialsForAgencyPath);
                    RequestBody UMCImage = RequestBody.create(MediaType.parse("*/*"), UMCFile);
                    uMCPart = MultipartBody.Part.createFormData("UMC", UMCFile.getName(), UMCImage);
                }


            }
        }

        MultipartBody.Part uGCPart = null;
        if (GovernmentIdFragment.govProofPath != null) {
            if (GovernmentIdFragment.govProofPath.startsWith("uploads/")) {

            } else {
                File UGPFile = new File(GovernmentIdFragment.govProofPath);
                RequestBody uGCImage = RequestBody.create(MediaType.parse("*/*"), UGPFile);
                if (GovernmentIdFragment.govId.equals("UPT")) {
                    uGCPart = MultipartBody.Part.createFormData("UPT", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UAD")) {
                    uGCPart = MultipartBody.Part.createFormData("UAD", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UVD")) {
                    uGCPart = MultipartBody.Part.createFormData("UVD", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UDL")) {
                    uGCPart = MultipartBody.Part.createFormData("UDL", UGPFile.getName(), uGCImage);
                }
                if (GovernmentIdFragment.govId.equals("UPN")) {
                    uGCPart = MultipartBody.Part.createFormData("UPN", UGPFile.getName(), uGCImage);
                }
            }

        }

        /*else {
            Toast.makeText(getActivity(), "Please select Government proof", Toast.LENGTH_SHORT).show();
            return;
        }*/

        MultipartBody.Part uGSTPart = null;
        if (Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")) {
            if (GovernmentIdFragment.gstProofDocPath != null) {
                if (GovernmentIdFragment.gstProofDocPath.startsWith("uploads/")) {

                } else {
                    File UGPFile = new File(GovernmentIdFragment.gstProofDocPath);
                    RequestBody uGCImage = RequestBody.create(MediaType.parse("*/*"), UGPFile);
                    uGSTPart = MultipartBody.Part.createFormData("UGD", UGPFile.getName(), uGCImage);
                }

            }
        }

        MultipartBody.Part uPIPart = null;
        if (Constants.USER_ROLE.equals("Operator")) {
            if (OperatorHomeFragment.mAboutYourSelfPath != null) {
                File UPIfile = null;
                if (OperatorHomeFragment.mAboutYourSelfPath.startsWith("uploads/")) {

                } else {
                    UPIfile = new File(OperatorHomeFragment.mAboutYourSelfPath);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }
        if (Constants.USER_ROLE.equals("Supervisor")) {
            if (SupervisorHomeFragment.mAboutYourSelfPathSuper != null) {
                File UPIfile = null;
                if (SupervisorHomeFragment.mAboutYourSelfPathSuper.startsWith("uploads/")) {

                } else {
                    UPIfile = new File(SupervisorHomeFragment.mAboutYourSelfPathSuper);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }

        if (Constants.USER_ROLE.equals("Renter")) {
            if (RenterHomeFragment.mAboutYourSelfPathRenter != null) {
                File UPIfile = null;
                if (RenterHomeFragment.mAboutYourSelfPathRenter.startsWith("uploads/")) {

                } else {
                    UPIfile = new File(RenterHomeFragment.mAboutYourSelfPathRenter);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }

        if (Constants.USER_ROLE.equals("Owner")) {
            if (OwnerHomeFragment.mAboutYourSelfPathOwner != null) {
                File UPIfile = null;
                if (OwnerHomeFragment.mAboutYourSelfPathOwner.startsWith("uploads/")) {

                } else {
                    UPIfile = new File(OwnerHomeFragment.mAboutYourSelfPathOwner);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }


        JSONObject requestBody = getRequestBody();

        RequestBody draBody = null;

        try {
            draBody = RequestBody.create(MediaType.parse("text/plain"), requestBody.toString(1));
            //Log.d(TAG, "requestUploadSurvey: RequestBody : " + requestBody.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .transform(new CircleTransform(getContext()));

        progressDialog.show();
        String token = TokenManager.getSessionToken();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> updateCall = apiInterface.updateProfileDetails("Bearer " + token, uRCPart, uMCPart, uGCPart, uGSTPart, uPIPart, draBody);
        updateCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    showSnackbar(response.body().getMessage());
                    callMainFragment();

                   /* if (Constants.USER_ROLE.equals("Renter")){
                        Glide.with(getActivity()).load(Uri.fromFile(new File(RenterHomeFragment.mAboutYourSelfPathRenter)))
                                .apply(options)
                                .into(RenterMainActivity.imgIconRenter);
                    }else if (Constants.USER_ROLE.equals("Owner")){
                        Glide.with(getActivity()).load(Uri.fromFile(new File(OwnerHomeFragment.mAboutYourSelfPathOwner)))
                                .apply(options)
                                .into(OwnerMainActivity.imgIconOwner);
                    }
                    else if(Constants.USER_ROLE.equals("Operator")){
                        Glide.with(getActivity()).load(Uri.fromFile(new File(OperatorHomeFragment.mAboutYourSelfPath)))
                                .apply(options)
                                .into(MainActivity.imgIcon);
                    } else if(Constants.USER_ROLE.equals("Supervisor")){
                        Glide.with(getActivity()).load(Uri.fromFile(new File(SupervisorHomeFragment.mAboutYourSelfPathSuper)))
                                .apply(options)
                                .into(MainActivity.imgIcon);
                    }*/
                    /*if (Constants.MY_PROFILE){
                        //callMainFragment();
                        confirmDialogs(response.body().getMessage());
                    }else {
                        Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        callMainActivity();
                    }*/
                } else {
                    if (response.code() == 401) {
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code() == 404) {
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse stm = new ForgotPasswordResponse();
                        try {
                            stm = gson.fromJson(response.errorBody().string(), ForgotPasswordResponse.class);
                            showSnackbar(stm.getMessage());
                        } catch (IOException e) {
                            Log.e("Error code:", e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private JSONObject getRequestBody() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", String.valueOf(TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID, 0)));
            jsonObject.put("firstName", GeneralDetailsFragment.firstName);
            jsonObject.put("lastName", GeneralDetailsFragment.lastName);
            jsonObject.put("userName", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USERNAME, null));
            jsonObject.put("email", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_EMAIL, null));
            jsonObject.put("mobileNo", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO, null));

            JSONArray segmentList = new JSONArray();
            for (int i = 0; i < GeneralDetailsFragment.segmentList.size(); i++) {
                segmentList.put(GeneralDetailsFragment.segmentList.get(i));
            }
            jsonObject.put("segment", segmentList);

            if (Constants.USER_ROLE.equals("Operator")) {

                jsonObject.put("association", OperatorchargeDetailsFragment.OperatorWorkAssociation);
                jsonObject.put("userImagePath", OperatorHomeFragment.mAboutYourSelfPath);
                jsonObject.put("runCertificatePath", OperatorchargeDetailsFragment.certificateToRunPath);
                jsonObject.put("machineCredentialsPath", OperatorchargeDetailsFragment.credentialsForAgencyPath);


            }
            if (Constants.USER_ROLE.equals("Supervisor")) {
                jsonObject.put("userImagePath", SupervisorHomeFragment.mAboutYourSelfPathSuper);
            }

            if (Constants.USER_ROLE.equals("Renter")) {
                jsonObject.put("userImagePath", RenterHomeFragment.mAboutYourSelfPathRenter);
                jsonObject.put("gstNumber", GovernmentIdFragment.gstNo);
                jsonObject.put("gstDocumentPath", GovernmentIdFragment.gstProofDocPath);
            }
            if (Constants.USER_ROLE.equals("Owner")) {

                jsonObject.put("userImagePath", OwnerHomeFragment.mAboutYourSelfPathOwner);
                jsonObject.put("gstNumber", GovernmentIdFragment.gstNo);
                jsonObject.put("gstDocumentPath", GovernmentIdFragment.gstProofDocPath);
            }


            jsonObject.put("address1", AddressFragment.mAddress1);
            jsonObject.put("address2", AddressFragment.mAddress2);
            jsonObject.put("address3", AddressFragment.mAddress3);
            jsonObject.put("address4", AddressFragment.mAddress4);
            jsonObject.put("city", AddressFragment.mCity);
            jsonObject.put("postal_code", AddressFragment.mPincode);
            jsonObject.put("state", AddressFragment.mState);
            jsonObject.put("country", AddressFragment.mCountry);
            jsonObject.put("govtProofDocPath", GovernmentIdFragment.govProofPath);

            jsonObject.put("govtProofId", GovernmentIdFragment.govId);

            if (Constants.USER_ROLE.equals("Operator") || Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")) {
                jsonObject.put("accountHolder", BankDetailsFragment.mAccountHolder);
                jsonObject.put("accountType",BankDetailsFragment.mAccountType);
                jsonObject.put("accountNo", BankDetailsFragment.mAccountNo);
                jsonObject.put("bank", BankDetailsFragment.mBank);
                jsonObject.put("ifscCode", BankDetailsFragment.mIFSCCode);
                jsonObject.put("payableAtCity", BankDetailsFragment.mPayableAt);
                jsonObject.put("paytmAccount", BankDetailsFragment.mPaytmAccountNo);
            }

            if (ReferDetailsFragment.mRefer_Type != null) {
                jsonObject.put("referType", ReferDetailsFragment.mRefer_Type);
                jsonObject.put("referMobileNo", ReferDetailsFragment.mRefer_MobileNo);
                jsonObject.put("referBy", ReferDetailsFragment.mRefer_By);
            }


            jsonObject.put("aboutYourself", GeneralDetailsFragment.mAboutYourSelf);

            if (Constants.USER_ROLE.equals("Operator")) {

                jsonObject.put("attribute1", OperatorchargeDetailsFragment.AMOUNTTYPE);
                jsonObject.put("attribute2", Float.parseFloat(OperatorchargeDetailsFragment.operatorAmount));
                jsonObject.put("attribute3", OperatorchargeDetailsFragment.ACCOMODATION);
                jsonObject.put("attribute4", OperatorchargeDetailsFragment.TRASPORTATION);
                jsonObject.put("attribute5", OperatorchargeDetailsFragment.FOOD);
            }


            if (Constants.MY_PROFILE) {
                jsonObject.put("editProfile", true);
            } else {
                jsonObject.put("editProfile", false);
            }

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public void callMainFragment() {
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, myProfileFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void callMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    public void showSnackbar(String msg) {

        /*AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
         *//*LayoutInflater newinInflater=getLayoutInflater();
            View view = newinInflater.inflate(R.layout.custom_title_alert_dialog, null);
            AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.custom_title_text);
            titleText.setText("Profile");
            builder.setCustomTitle(view);*//*
        //builder .setTitle("Internet")
        builder.setCancelable(false);
        builder .setMessage(msg)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();

                        if (Constants.MY_PROFILE){
                            callMainFragment();
                        }else {
                            callMainActivity();
                        }

                    }
                })
                .show();*/
        Snackbar snackbar = Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    public void callAssociatedOwnerName() {
        String token = TokenManager.getSessionToken();
        int partyId = TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID, 0);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<String> associatedOwner = apiInterface.getAssociatedOwnerName("Bearer " + token, partyId);
        associatedOwner.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    OperatorchargeDetailsFragment.AssociatedWith = response.body().toString();
                } else {
                    if (response.code() == 401) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code() == 404) {
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError = new StatuTitleMessageResponse();
                        try {
                            mError = gson.fromJson(response.errorBody().string(), StatuTitleMessageResponse.class);
                            String data = "0";
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code() == 403) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callGovermentIdApi() {
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> govermentIdCall=apiInterface.getGovermentIdCombo("GOVERMENT_ID","GOVID");
        govermentIdCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String segment = response.body().string();
                        JSONObject jsonObject = new JSONObject(segment);
                        parseJSONObject(jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSONObject(JSONObject segmentResponse) {
        govermentMap.clear();
        for (Iterator<String> iter = segmentResponse.keys(); iter.hasNext(); ) {
            String key = iter.next();
            //govermnetIdItems.add(key);
            try {
                govermentMap.put(key, segmentResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
