package com.mareow.recaptchademo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.Adapters.DetailActivityViewPagerAdapter;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.FragmentUserDetails.AddressFragment;
import com.mareow.recaptchademo.FragmentUserDetails.BankDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.GSTDetailsFragment;
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
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.viewpager.widget.ViewPager;

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

public class DetailsSubmissionActivity extends AppCompatActivity {

    public static ViewPager detailsActivityViewPager;
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    DetailActivityViewPagerAdapter adapter;
    private Toolbar toolbar;
    private  AppCompatTextView txtTitle;
    AppCompatButton btnSkip;

    FloatingActionButton mButtonSave;
    ProgressDialog progressDialog;
    String referType;
    public static HashMap<String,String> registrationGovermentMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_submission);


       /* if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            LoginActivity.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            LoginActivity.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/

        progressDialog=new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait..............");
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        callGovermentIdApi();
        mButtonSave=(FloatingActionButton)findViewById(R.id.registration_main_Save);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterTimeSaveProfile();
            }
        });
        mButtonSave.setVisibility(View.GONE);

        btnSkip=(AppCompatButton)findViewById(R.id.details_btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constants.USER_ROLE.equals("Renter")){
                    callrenterMainActivity();
                }else {
                    callMainActivity();
                }

            }
        });

        txtTitle=(AppCompatTextView)toolbar.findViewById(R.id.details_toolbar_title);
        txtTitle.setText("Registration Details");

        detailsActivityViewPager = (ViewPager) findViewById(R.id.details_activity_viewpager);
        llPagerDots = (LinearLayout) findViewById(R.id.pager_dots);
        setupViewPager(detailsActivityViewPager);

        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_dots);

        detailsActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){

                    if (Constants.USER_ROLE.equals("Renter")){
                        RenterMainActivity.navItemIndexRenter=17;
                    }else if(Constants.USER_ROLE.equals("Owner")){
                        OwnerMainActivity.navItemIndexOwner=17;
                    }else {
                        MainActivity.navItemIndex=17;
                    }
                    mButtonSave.setVisibility(View.GONE);

                }else {

                    if (Constants.USER_ROLE.equals("Renter")){
                        RenterMainActivity.navItemIndexRenter=16;
                    }else if (Constants.USER_ROLE.equals("Owner")){
                        OwnerMainActivity.navItemIndexOwner=16;
                    }else {
                        MainActivity.navItemIndex=16;
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

        // callSpecificFrgament();
    }

    private void callrenterMainActivity() {
        Intent intent=new Intent(DetailsSubmissionActivity.this,RenterMainActivity.class);
        startActivity(intent);
        DetailsSubmissionActivity.this.finish();
    }

    private void callMainActivity() {
        Intent intent=new Intent(DetailsSubmissionActivity.this,MainActivity.class);
        startActivity(intent);
        DetailsSubmissionActivity.this.finish();
    }

   /* private void callSpecificFrgament() {
        String signupType= Constants.USER_ROLE;

        if (signupType.equals("Owner")){
            Fragment newFragment = new OwnerHomeFragment();
            loadFragment(newFragment);
            return;
        }
        if (signupType.equals("Renter")){
            Fragment newFragment = new RenterMainHomeFragment();
            loadFragment(newFragment);
            return;
        }
        if (signupType.equals("Supervisor")){
            Fragment newFragment = new SupervisorHomeFragment();
            loadFragment(newFragment);
            return;
        }
        if (signupType.equals("Operator")){
            Fragment newFragment = new OperatorHomeFragment();
            loadFragment(newFragment);
            return;

        }

    }*/

   /* private void loadFragment(Fragment newFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment).commit();
    }*/


    /*@Override
    public void onBackPressed() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
        }else {
            showExitDailog();
        }
    }
*/
    private void setupViewPager(ViewPager viewPager) {


        adapter = new DetailActivityViewPagerAdapter(this.getSupportFragmentManager());

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



        }else if (signupType.equals("Supervisor")) {
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


    private void setupPagerIndidcatorDots() {

        ivArrayDotsPager = new ImageView[adapter.getCount()];

        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(this);
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

    @Override
    public void onBackPressed() {
        if (Constants.USER_ROLE.equals("Renter")){
            if (RenterMainActivity.navItemIndexRenter==17){
                Intent intent=new Intent(this,RenterMainActivity.class);
                startActivity(intent);
                this.finish();
            }else {
                detailsActivityViewPager.setCurrentItem(0);
            }

        }else if (Constants.USER_ROLE.equals("Owner")){
            if (OwnerMainActivity.navItemIndexOwner==17){
                Intent intent=new Intent(this,OwnerMainActivity.class);
                startActivity(intent);
                this.finish();
            }else {
                detailsActivityViewPager.setCurrentItem(0);
            }
        }else {
            if (MainActivity.navItemIndex==17){
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
            }else {
                detailsActivityViewPager.setCurrentItem(0);
            }
        }


    }


    private void RegisterTimeSaveProfile() {

        if (GeneralDetailsFragment.firstName==null || TextUtils.isEmpty(GeneralDetailsFragment.firstName)){
            showSnackbar( "Please select firstname");
            return;
        }

        if (GeneralDetailsFragment.lastName==null || TextUtils.isEmpty(GeneralDetailsFragment.lastName)){
            showSnackbar("Please select lastname");
            return;
        }

        if (Constants.USER_ROLE.equals("Operator") || Constants.USER_ROLE.equals("Renter")){
            if (GeneralDetailsFragment.segmentList.size() == 0) {
                showSnackbar("Please select Segment");
                return;
            }
        }

        if (Constants.USER_ROLE.equals("Operator")){
            if (TextUtils.isEmpty(OperatorchargeDetailsFragment.OperatorWorkAssociation) || OperatorchargeDetailsFragment.OperatorWorkAssociation==null) {
                showSnackbar( "Please select work association");
                return;
            } else if (TextUtils.isEmpty(OperatorchargeDetailsFragment.operatorAmount) || OperatorchargeDetailsFragment.operatorAmount==null) {
                showSnackbar("Please select operator amount");
                return;
            }

        }

        if (TextUtils.isEmpty(AddressFragment.mAddress1) || AddressFragment.mAddress1==null) {
            showSnackbar( "Please select address1");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mCity) || AddressFragment.mCity==null) {
            showSnackbar( "Please select city");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mPincode) || AddressFragment.mCity==null) {
            showSnackbar( "Please select pincode");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mState) || AddressFragment.mState==null) {
            showSnackbar("Please select state");
            return;
        } else if (TextUtils.isEmpty(AddressFragment.mCountry) || AddressFragment.mCountry==null) {
            showSnackbar( "Please select country");
            return;
        } /*else if (TextUtils.isEmpty(GovernmentIdFragment.govId) || GovernmentIdFragment.govId==null) {
            Toast.makeText(this, "Please select goverment id", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(GovernmentIdFragment.govProofPath) || GovernmentIdFragment.govProofPath==null) {
            Toast.makeText(this, "Please select goverment proof", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (Constants.USER_ROLE.equals("Operator")){

            if (TextUtils.isEmpty(GovernmentIdFragment.govId) || GovernmentIdFragment.govId==null) {
                showSnackbar("Please select goverment id");
                return;
            } else if (TextUtils.isEmpty(GovernmentIdFragment.govProofPath) || GovernmentIdFragment.govProofPath==null) {
                showSnackbar("Please select goverment proof");
                return;
            }

        }else {

            if (GovernmentIdFragment.govId!=null && !TextUtils.isEmpty(GovernmentIdFragment.govId)) {

                if (TextUtils.isEmpty(GovernmentIdFragment.govProofPath) || GovernmentIdFragment.govProofPath==null) {
                    showSnackbar( "Please select goverment proof");
                    return;
                }

            }

            if (Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")){

                if (GovernmentIdFragment.gstNo!=null && !TextUtils.isEmpty(GovernmentIdFragment.gstNo)){

                    if (TextUtils.isEmpty(GovernmentIdFragment.gstProofDocPath) || GovernmentIdFragment.gstProofDocPath==null) {
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
                showSnackbar( "Please select payable at");
                return;
            } else if (BankDetailsFragment.mBank == null || BankDetailsFragment.mBank.isEmpty()) {
                showSnackbar( "Please select bank name");
                return;
            } else if (BankDetailsFragment.mAccountNo == null || TextUtils.isEmpty(BankDetailsFragment.mAccountNo)) {
                showSnackbar( "Please select goverment proof");
                return;
            } else if (BankDetailsFragment.mIFSCCode == null || TextUtils.isEmpty(BankDetailsFragment.mIFSCCode)) {
                showSnackbar( "Please select bank iFSC");
                return;
            } else if (BankDetailsFragment.mPaytmAccountNo == null || TextUtils.isEmpty(BankDetailsFragment.mPaytmAccountNo)) {
                showSnackbar( "Please select paytm number");
                return;
            }
        }

        if (ReferDetailsFragment.mRefer_Type != null) {
           /* if (ReferDetailsFragment.mRefer_Type.equals("'mareow' User Refferd")) {
                referType = "UR";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Others")) {
                referType = "OTH";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Advertisment")) {
                referType = "ADDH";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Marketing Team")) {
                referType = "MT";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Sales Team")) {
                referType = "ST";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Owner")) {
                referType = "REF_OWN";
            } else if (ReferDetailsFragment.mRefer_Type.equals("Renter")) {
                referType = "REF_REN";
            }*/

            if (ReferDetailsFragment.mRefer_By==null || TextUtils.isEmpty(ReferDetailsFragment.mRefer_By)){
                showSnackbar( "Please refer by");
                return;
            }

            if (ReferDetailsFragment.mRefer_MobileNo==null || TextUtils.isEmpty(ReferDetailsFragment.mRefer_MobileNo) || ReferDetailsFragment.mRefer_MobileNo.length()<10){
                showSnackbar( "Please enter valid refer mobile");
                return;
            }

        }
        MultipartBody.Part uRCPart=null;
        MultipartBody.Part uMCPart=null;

        if (Constants.USER_ROLE.equals("Operator")){
            File URCFile=null;
            if (OperatorchargeDetailsFragment.certificateToRunPath != null) {

                if (OperatorchargeDetailsFragment.certificateToRunPath.startsWith("uploads/")){

                }else {
                    URCFile= new File(OperatorchargeDetailsFragment.certificateToRunPath);
                    RequestBody uRCImage = RequestBody.create(MediaType.parse("*/*"), URCFile);
                    uRCPart = MultipartBody.Part.createFormData("URC", URCFile.getName(), uRCImage);
                }

            }
            if (OperatorchargeDetailsFragment.credentialsForAgencyPath != null) {
                File UMCFile=null;
                if (OperatorchargeDetailsFragment.credentialsForAgencyPath.startsWith("uploads/")){

                }else {

                    UMCFile = new File(OperatorchargeDetailsFragment.credentialsForAgencyPath);
                    RequestBody UMCImage = RequestBody.create(MediaType.parse("*/*"), UMCFile);
                    uMCPart = MultipartBody.Part.createFormData("UMC", UMCFile.getName(), UMCImage);
                }


            }
        }

        MultipartBody.Part uGCPart = null;
        if (GovernmentIdFragment.govProofPath!=null) {
            if (GovernmentIdFragment.govProofPath.startsWith("uploads/")){

            }else {
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

        }else {
            Toast.makeText(this, "Please select Government proof", Toast.LENGTH_SHORT).show();
            return;
        }


        MultipartBody.Part uGSTPart = null;
        if (Constants.USER_ROLE.equals("Renter") || Constants.USER_ROLE.equals("Owner")){
            if (GovernmentIdFragment.gstProofDocPath!=null) {
                if (GovernmentIdFragment.gstProofDocPath.startsWith("uploads/")){

                }else {
                    File UGPFile = new File(GovernmentIdFragment.gstProofDocPath);
                    RequestBody uGCImage = RequestBody.create(MediaType.parse("*/*"), UGPFile);
                    uGSTPart = MultipartBody.Part.createFormData("UGD", UGPFile.getName(), uGCImage);
                }

            }
        }

        MultipartBody.Part uPIPart=null;
        if (Constants.USER_ROLE.equals("Operator")){
            if (OperatorHomeFragment.mAboutYourSelfPath!=null){
                File UPIfile=null;
                if (OperatorHomeFragment.mAboutYourSelfPath.startsWith("uploads/")){

                }else {
                    UPIfile = new File(OperatorHomeFragment.mAboutYourSelfPath);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }
        if (Constants.USER_ROLE.equals("Supervisor")){
            if (SupervisorHomeFragment.mAboutYourSelfPathSuper!=null){
                File UPIfile=null;
                if (SupervisorHomeFragment.mAboutYourSelfPathSuper.startsWith("uploads/")){

                }else {
                    UPIfile = new File(SupervisorHomeFragment.mAboutYourSelfPathSuper);
                    RequestBody uPIImage = RequestBody.create(MediaType.parse("image/*"), UPIfile);
                    uPIPart = MultipartBody.Part.createFormData("UUI", UPIfile.getName(), uPIImage);
                }

            }
        }

        if (Constants.USER_ROLE.equals("Renter")){
            if (RenterHomeFragment.mAboutYourSelfPathRenter!=null){
                File UPIfile=null;
                if (RenterHomeFragment.mAboutYourSelfPathRenter.startsWith("uploads/")){

                }else {
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

        progressDialog.show();
        String token = TokenManager.getSessionToken();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> updateCall = apiInterface.updateProfileDetails("Bearer " + token, uRCPart, uMCPart, uGCPart,uGSTPart, uPIPart, draBody);
        updateCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    confirmDialogs(response.body().getMessage());
                    /*if (Constants.MY_PROFILE){
                        //callMainFragment();
                        confirmDialogs(response.body().getMessage());
                    }else {
                        Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        callMainActivity();
                    }*/
                } else {
                    if (response.code() == 401) {
                        TokenExpiredUtils.tokenExpired(DetailsSubmissionActivity.this);
                    }
                    if (response.code() == 404) {
                        Gson gson = new GsonBuilder().create();
                        ForgotPasswordResponse stm = new ForgotPasswordResponse();
                        try {
                            stm = gson.fromJson(response.errorBody().string(), ForgotPasswordResponse.class);
                            Toast.makeText(DetailsSubmissionActivity.this, stm.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.e("Error code:", e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailsSubmissionActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private JSONObject getRequestBody() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userId", String.valueOf(TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID, 0)));
            jsonObject.put("firstName",GeneralDetailsFragment.firstName);
            jsonObject.put("lastName",GeneralDetailsFragment.lastName);
            jsonObject.put("userName", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_USERNAME, null));
            jsonObject.put("email", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_EMAIL, null));
            jsonObject.put("mobileNo", TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_MOBILE_NO, null));

            JSONArray segmentList = new JSONArray();
            for (int i = 0; i < GeneralDetailsFragment.segmentList.size(); i++) {
                segmentList.put(GeneralDetailsFragment.segmentList.get(i));
            }
            jsonObject.put("segment", segmentList);

            if (Constants.USER_ROLE.equals("Operator")){

                jsonObject.put("association", OperatorchargeDetailsFragment.OperatorWorkAssociation);
                jsonObject.put("userImagePath", OperatorHomeFragment.mAboutYourSelfPath);
                jsonObject.put("runCertificatePath", OperatorchargeDetailsFragment.certificateToRunPath);
                jsonObject.put("machineCredentialsPath", OperatorchargeDetailsFragment.credentialsForAgencyPath);


            }
            if (Constants.USER_ROLE.equals("Supervisor")){
                jsonObject.put("userImagePath",SupervisorHomeFragment.mAboutYourSelfPathSuper);
            }

            if (Constants.USER_ROLE.equals("Renter")){
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
            jsonObject.put("govtProofDocPath",GovernmentIdFragment.govProofPath);

            jsonObject.put("govtProofId", GovernmentIdFragment.govId);

            if (Constants.USER_ROLE.equals("Operator")){
                jsonObject.put("accountHolder", BankDetailsFragment.mAccountHolder);
                jsonObject.put("accountNo", BankDetailsFragment.mAccountNo);
                jsonObject.put("bank", BankDetailsFragment.mBank);
                jsonObject.put("ifscCode", BankDetailsFragment.mIFSCCode);
                jsonObject.put("payableAtCity", BankDetailsFragment.mPayableAt);
                jsonObject.put("paytmAccount", BankDetailsFragment.mPaytmAccountNo);
            }

            if (ReferDetailsFragment.mRefer_Type != null) {
                jsonObject.put("referType", referType);
                jsonObject.put("referMobileNo", ReferDetailsFragment.mRefer_MobileNo);
                jsonObject.put("referBy", ReferDetailsFragment.mRefer_By);
            }


            jsonObject.put("aboutYourself",GeneralDetailsFragment.mAboutYourSelf);

            if (Constants.USER_ROLE.equals("Operator")){

                jsonObject.put("attribute1", OperatorchargeDetailsFragment.AMOUNTTYPE);
                jsonObject.put("attribute2", Float.parseFloat(OperatorchargeDetailsFragment.operatorAmount));
                jsonObject.put("attribute3", OperatorchargeDetailsFragment.ACCOMODATION);
                jsonObject.put("attribute4", OperatorchargeDetailsFragment.TRASPORTATION);
                jsonObject.put("attribute5", OperatorchargeDetailsFragment.FOOD);
            }

            if (Constants.MY_PROFILE){
                jsonObject.put("editProfile",true);
            }else {
                jsonObject.put("editProfile",false);
            }

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void confirmDialogs(String msg){

        Snackbar snackbar= Snackbar.make(mButtonSave,msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(this.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        snackbar.show();

        /*if (Constants.MY_PROFILE){
            callMainFragment();
        }else {
            callMainActivity();
        }*/

        if (Constants.USER_ROLE.equals("Renter")){
            callrenterMainActivity();
        }else if (Constants.USER_ROLE.equals("Owner")){
             callOwnerMainActivity();
        }else {
            callMainActivity();
        }

    }

    private void callOwnerMainActivity() {
        Intent intent=new Intent(DetailsSubmissionActivity.this,OwnerMainActivity.class);
        startActivity(intent);
        DetailsSubmissionActivity.this.finish();
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
                        TokenExpiredUtils.tokenExpired(DetailsSubmissionActivity.this);
                    }
                    if (response.code()==404){
                        Toast.makeText(DetailsSubmissionActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailsSubmissionActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJSONObject(JSONObject segmentResponse) {
        registrationGovermentMap.clear();
        for (Iterator<String> iter = segmentResponse.keys(); iter.hasNext(); ) {
            String key = iter.next();
            //govermnetIdItems.add(key);
            try {
                registrationGovermentMap.put(key, segmentResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(mButtonSave,msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(this.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}