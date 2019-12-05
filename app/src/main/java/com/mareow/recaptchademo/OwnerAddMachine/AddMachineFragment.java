package com.mareow.recaptchademo.OwnerAddMachine;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Adapters.DetailActivityViewPagerAdapter;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMachineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMachineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ViewPager addMachineViewPager;
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    DetailActivityViewPagerAdapter adapter;

    ProgressDialog progressDialog;
    public FloatingActionButton mButtonSave;
    public AddMachineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMachineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMachineFragment newInstance(String param1, String param2) {
        AddMachineFragment fragment = new AddMachineFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_machine, container, false);

        OwnerMainActivity.txtOwnerTitle.setText("Machine Details");
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait.................");
        }
        initView(view);
        return view;
    }

    private void initView(View view){
        addMachineViewPager = (ViewPager)view.findViewById(R.id.owner_add_machine_viewpager);
        llPagerDots = (LinearLayout)view.findViewById(R.id.owner_add_machine_pager_dots);
        addMachineViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    /*if (Constants.USER_ROLE.equals("Renter")){
                        RenterMainActivity.navItemIndexRenter=17;
                    }else if(Constants.USER_ROLE.equals("Owner")){
                        OwnerMainActivity.navItemIndexOwner=17;
                    }else {
                        MainActivity.navItemIndex=17;
                    }*/
                    mButtonSave.setVisibility(View.GONE);

                }else {

                    /*if (Constants.USER_ROLE.equals("Renter")){
                        RenterMainActivity.navItemIndexRenter=16;
                    }else if (Constants.USER_ROLE.equals("Owner")){
                        OwnerMainActivity.navItemIndexOwner=16;
                    }else {
                        MainActivity.navItemIndex=16;
                    }*/

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


        mButtonSave=(FloatingActionButton)view.findViewById(R.id.owner_add_machin_main_add);
        mButtonSave.setVisibility(View.GONE);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMachineForOwner();
            }
        });

        setupViewPager(addMachineViewPager);
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_dots);


        setupData();
    }


    private void setupViewPager(ViewPager viewPager) {

        adapter = new DetailActivityViewPagerAdapter(getChildFragmentManager());

        String signupType = Constants.USER_ROLE;

            adapter.addFragment(new MachineGeneralDetailsFragment(), "MachineGeneralDetails");
            adapter.addFragment(new MachineBriefIntroductoryFragment(), "BriefIntroductry");
            adapter.addFragment(new MachineRegitrationDetailsFragment(), "Registration");
            adapter.addFragment(new MachineInsuranceFragment(), "Insurance");

        viewPager.setAdapter(adapter);
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


    private void saveMachineForOwner() {

        if (MachineGeneralDetailsFragment.SEGMENT_CODE==null){
            showSnackbar("Please select segment.");
            return;
        }else if (MachineGeneralDetailsFragment.CATEGORY_CODE==null){
            showSnackbar("Please select category.");
            return;
        }else if (MachineGeneralDetailsFragment.SUBCATEGORY_CODE==null){
            showSnackbar("Please select subcategory.");
            return;
        } else if (MachineGeneralDetailsFragment.MANUFACTURER_CODE==null){
            showSnackbar("Please select manufacturer.");
            return;
        }else if (MachineGeneralDetailsFragment.MODEL_CODE==0){
            showSnackbar("Please select model.");
            return;
        }


        if (MachineBriefIntroductoryFragment.MACHINE_NAME==null){
            showSnackbar("Please select machine name.");
            return;
        }else if (MachineBriefIntroductoryFragment.AVAILABLE_DATE==null){
            showSnackbar("Please select AVailable Date.");
            return;
        }else if (MachineBriefIntroductoryFragment.STATE_CODE==null){
            showSnackbar("Please select state.");
            return;
        } else if (MachineBriefIntroductoryFragment.DISTRICT_CODE==null){
            showSnackbar("Please select district.");
            return;
        }else if (MachineBriefIntroductoryFragment.CITY_CURRENT_LOCATION==null){
            showSnackbar("Please select city(Current Location).");
            return;
        }else if (MachineBriefIntroductoryFragment.POSTAL_CURRENT_CODE==null){
            showSnackbar("Please select Postal caode.");
            return;
        }else if (MachineBriefIntroductoryFragment.ODOMETER_READTING==null){
            showSnackbar("Please select Odoermter.");
            return;
        } else if (MachineBriefIntroductoryFragment.MACHINE_RUN==null){
            showSnackbar("Please select machine run.");
            return;
        }else if (MachineBriefIntroductoryFragment.LOAD_CAPACITY_CODE==null){
            showSnackbar("Please select load capacity.");
            return;
        }

        if (MachineRegitrationDetailsFragment.MANUFACTURING_YEAR==null){
            showSnackbar("Please select manufacture year.");
            return;
        }

        if (MachineInsuranceFragment.INSURANCE_FALG){
            if (MachineInsuranceFragment.INSURANCE_COMPANY==null){
                showSnackbar("Please select insurance company.");
                return;
            }else if (MachineInsuranceFragment.INSURANCE_TYPE_CODE==null){
                showSnackbar("Please select insurance type.");
                return;
            }else if (MachineInsuranceFragment.INSURANCE_START_DATE==null){
                showSnackbar("Please select insurance startdate.");
                return;
            }else if (MachineInsuranceFragment.INSURANCE_END_DATE==null){
                showSnackbar("Please select insurance enddate.");
                return;
            }else if (MachineInsuranceFragment.MACHINE_AGE==null){
                showSnackbar("Please select machine age.");
                return;
            }else if (MachineInsuranceFragment.INSURANCE_CLAIMED_YEAR==null){
                showSnackbar("Please select insurance claimed year.");
                return;
            }else if (MachineInsuranceFragment.INSURANCE_NCB==null){
                showSnackbar("Please select insurance NCB.");
                return;
            }
        }




        MultipartBody.Part[] machineImagePart=new MultipartBody.Part[MachineBriefIntroductoryFragment.mMachineImagePath.size()];
        if (MachineBriefIntroductoryFragment.mMachineImagePath.size()>0){
            for (int i=0;i<MachineBriefIntroductoryFragment.mMachineImagePath.size();i++){

                File machineImagefile= new File(MachineBriefIntroductoryFragment.mMachineImagePath.get(i));
                RequestBody supportImage = RequestBody.create(MediaType.parse("image/*"), machineImagefile);
                machineImagePart[i] = MultipartBody.Part.createFormData("MIMG", machineImagefile.getName(), supportImage);
            }

        }

        MultipartBody.Part rcBookPart=null;
        if (MachineRegitrationDetailsFragment.mRCBookPath!=null){
            File rcBookfile= new File(MachineRegitrationDetailsFragment.mRCBookPath);
            RequestBody supportImage = RequestBody.create(MediaType.parse("image/*"), rcBookfile);
            rcBookPart = MultipartBody.Part.createFormData("MRCB", rcBookfile.getName(), supportImage);
        }
        MultipartBody.Part pucPart=null;
        if (MachineRegitrationDetailsFragment.mPUCCertificatePath!=null){
            File pucfile= new File(MachineRegitrationDetailsFragment.mPUCCertificatePath);
            RequestBody supportImage = RequestBody.create(MediaType.parse("image/*"), pucfile);
            pucPart = MultipartBody.Part.createFormData("MPUC", pucfile.getName(), supportImage);
        }
        MultipartBody.Part nationalPermitPart=null;
        if (MachineRegitrationDetailsFragment.mNationalPermitPath!=null){
            File nationalpermitfile= new File(MachineRegitrationDetailsFragment.mNationalPermitPath);
            RequestBody supportImage = RequestBody.create(MediaType.parse("image/*"), nationalpermitfile);
            nationalPermitPart = MultipartBody.Part.createFormData("MIMG", nationalpermitfile.getName(), supportImage);
        }
        MultipartBody.Part roadTaxPart=null;
        if (MachineRegitrationDetailsFragment.mRoadTaxPath!=null){
            File roadTaxfile= new File(MachineRegitrationDetailsFragment.mRoadTaxPath);
            RequestBody supportImage = RequestBody.create(MediaType.parse("image/*"), roadTaxfile);
            roadTaxPart = MultipartBody.Part.createFormData("MRTX", roadTaxfile.getName(), supportImage);
        }


        JSONObject requestBody = getRequestBody();
        RequestBody draBody = null;

        try {
            draBody = RequestBody.create(MediaType.parse("text/plain"), requestBody.toString(1));
            //Log.d(TAG, "requestUploadSurvey: RequestBody : " + requestBody.toString(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (progressDialog!=null){
            progressDialog.show();
        }

        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> createMachineCall=apiInterface.createMachineForOwner("Bearer "+token,machineImagePart,rcBookPart,pucPart,nationalPermitPart,roadTaxPart,draBody);
        createMachineCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                    if (response.code()==403){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            showSnackbar(mError.getMessage());
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                showSnackbar(t.getMessage());
                progressDialog.dismiss();
            }
        });



    }

    private JSONObject getRequestBody() {


        JSONObject jsonObject = new JSONObject();
        try {

            int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
            int userId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID,0);
            // jsonObject.put("invoiceId",selectedInvoiceID);
            jsonObject.put("segmentCode",MachineGeneralDetailsFragment.SEGMENT_CODE);
            jsonObject.put("categoryCode",MachineGeneralDetailsFragment.CATEGORY_CODE);
            jsonObject.put("subCategoryCode",MachineGeneralDetailsFragment.SUBCATEGORY_CODE);
            jsonObject.put("manufacturerCode",MachineGeneralDetailsFragment.MANUFACTURER_CODE);
            //jsonObject.put("invoiceStatus",);
            jsonObject.put("machineModelId",MachineGeneralDetailsFragment.MODEL_CODE);
            jsonObject.put("machineSpec",MachineGeneralDetailsFragment.SPECIFICATION_URL);

            JSONArray attachmentList = new JSONArray();
                for (int i = 0; i < MachineGeneralDetailsFragment.selectedattachmentList.size(); i++) {
                    attachmentList.put(MachineGeneralDetailsFragment.selectedattachmentList.get(i));
                }

            jsonObject.put("attachmentId", attachmentList);

           /* JSONArray defualtAttachmentList = new JSONArray();
            for (int i = 0; i < MachineGeneralDetailsFragment.defaultSelectedattachmentList.size(); i++) {
                defualtAttachmentList.put(Integer.parseInt(MachineGeneralDetailsFragment.defaultSelectedattachmentList.get(i)));
            }*/

            jsonObject.put("defaultAttachment", MachineGeneralDetailsFragment.DEFUALT_ATTACHMENT_CODE);

            //jsonObject.put("defaultAttachment",defualtAttachmentList);
            jsonObject.put("machineName",MachineBriefIntroductoryFragment.MACHINE_NAME);
            jsonObject.put("availibilityDate",convertddmmyyToYYYYMMDD(MachineBriefIntroductoryFragment.AVAILABLE_DATE));
            jsonObject.put("currentLocation",MachineBriefIntroductoryFragment.CITY_CURRENT_LOCATION);
            jsonObject.put("currentPostalCode",MachineBriefIntroductoryFragment.POSTAL_CURRENT_CODE);


            jsonObject.put("odometer",Integer.parseInt(MachineBriefIntroductoryFragment.ODOMETER_READTING));
            jsonObject.put("runHours",Integer.parseInt(MachineBriefIntroductoryFragment.MACHINE_RUN));
            jsonObject.put("machineDesc",MachineBriefIntroductoryFragment.BRIFE_INTRO);
            jsonObject.put("loadCapacityCode",MachineBriefIntroductoryFragment.LOAD_CAPACITY_CODE);
            jsonObject.put("imagesPath",MachineBriefIntroductoryFragment.mMachineImagePath);


            jsonObject.put("ownership",MachineRegitrationDetailsFragment.OWNERSHIP);
            jsonObject.put("uniqueNo",MachineRegitrationDetailsFragment.UNIQUENO);
            jsonObject.put("registerNo",MachineRegitrationDetailsFragment.REGISTER_NO);
            jsonObject.put("registerDate",convertddmmyyToYYYYMMDD(MachineRegitrationDetailsFragment.REGISTER_DATE));
            jsonObject.put("engineNo",MachineRegitrationDetailsFragment.ENGINE_NO);
            jsonObject.put("chessisNo",MachineRegitrationDetailsFragment.CHASSIS_NO);
            jsonObject.put("manufacturerYear",MachineRegitrationDetailsFragment.MANUFACTURING_YEAR);
            jsonObject.put("mcFitnessDate",convertddmmyyToYYYYMMDD(MachineRegitrationDetailsFragment.MACHINE_FITNESS));

            if (MachineRegitrationDetailsFragment.mRCBookPath!=null){
                jsonObject.put("rcbookCHK",true);
            }else {
                jsonObject.put("rcbookCHK",false);
            }
            if (MachineRegitrationDetailsFragment.mNationalPermitPath!=null){
                jsonObject.put("nationPermitCHK",true);

            }else {
                jsonObject.put("nationPermitCHK",false);
            }
            if (MachineRegitrationDetailsFragment.mPUCCertificatePath!=null){
                jsonObject.put("pucCertifiacteCHK",true);
            }else {
                jsonObject.put("pucCertifiacteCHK",false);
            }

            if (MachineRegitrationDetailsFragment.mRoadTaxPath!=null){
                jsonObject.put("roadTaxCHK",true);
            }else {
                jsonObject.put("roadTaxCHK",false);
            }


            jsonObject.put("insurance",MachineInsuranceFragment.INSURANCE_FALG);

            if (MachineInsuranceFragment.INSURANCE_FALG){

                jsonObject.put("insuranceCompanyName",MachineInsuranceFragment.INSURANCE_COMPANY);
                jsonObject.put("insuranceStartDate",convertddmmyyToYYYYMMDD(MachineInsuranceFragment.INSURANCE_START_DATE));
                jsonObject.put("insuranceEndDate",convertddmmyyToYYYYMMDD(MachineInsuranceFragment.INSURANCE_END_DATE));
                jsonObject.put("insuranceType",MachineInsuranceFragment.INSURANCE_TYPE_CODE);
                jsonObject.put("machineAge",MachineInsuranceFragment.MACHINE_AGE);
                jsonObject.put("lastIncClaim",MachineInsuranceFragment.INSURANCE_CLAIMED_YEAR);
                jsonObject.put("ncbPer",Integer.parseInt(MachineInsuranceFragment.INSURANCE_NCB));

            }
            jsonObject.put("partyId",partyId);
            jsonObject.put("userId",userId);
             jsonObject.put("editmachine",false);
            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String convertddmmyyToYYYYMMDD(String stringDate){

        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date inputdate = input.parse(stringDate);                 // parse input
            return output.format(inputdate).toString();              // format output
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    private void setupData() {

        MachineGeneralDetailsFragment.SEGMENT_CODE=null;
        MachineGeneralDetailsFragment.CATEGORY_CODE=null;
        MachineGeneralDetailsFragment.SUBCATEGORY_CODE=null;
        MachineGeneralDetailsFragment.MANUFACTURER_CODE=null;
        MachineGeneralDetailsFragment.MODEL_CODE=0;
        MachineGeneralDetailsFragment.SPECIFICATION_URL=null;

        MachineGeneralDetailsFragment.DEFUALT_ATTACHMENT_CODE=0;
        MachineGeneralDetailsFragment.selectedattachmentList=new ArrayList<>();





        MachineBriefIntroductoryFragment.STATE_CODE=null;
        MachineBriefIntroductoryFragment.DISTRICT_CODE=null;
        MachineBriefIntroductoryFragment.CITY_CURRENT_LOCATION=null;
        MachineBriefIntroductoryFragment.POSTAL_CURRENT_CODE=null;
        MachineBriefIntroductoryFragment.LOAD_CAPACITY_CODE=null;
        MachineBriefIntroductoryFragment.BRIFE_INTRO=null;
        MachineBriefIntroductoryFragment.MACHINE_RUN=null;
        MachineBriefIntroductoryFragment.ODOMETER_READTING=null;
        MachineBriefIntroductoryFragment.MACHINE_NAME=null;
        MachineBriefIntroductoryFragment.AVAILABLE_DATE=null;

        MachineRegitrationDetailsFragment.mRCBookPath=null;
        MachineRegitrationDetailsFragment.mPUCCertificatePath=null;
        MachineRegitrationDetailsFragment.mNationalPermitPath=null;
        MachineRegitrationDetailsFragment.mRoadTaxPath=null;

        MachineRegitrationDetailsFragment.REGISTER_NO=null;
        MachineRegitrationDetailsFragment.REGISTER_DATE=null;
        MachineRegitrationDetailsFragment.ENGINE_NO=null;
        MachineRegitrationDetailsFragment.CHASSIS_NO=null;
        MachineRegitrationDetailsFragment.MANUFACTURING_YEAR=null;
        MachineRegitrationDetailsFragment.MACHINE_FITNESS=null;
        MachineRegitrationDetailsFragment.OWNERSHIP=null;

        MachineRegitrationDetailsFragment.RC_BOOK_FLAG=false;
        MachineRegitrationDetailsFragment.PUC_CERTIFICATE_FLAG=false;
        MachineRegitrationDetailsFragment.NATIONAL_PERMIT_FLAG=false;
        MachineRegitrationDetailsFragment.ROAD_TAX_FLAG=false;


        MachineInsuranceFragment.INSURANCE_TYPE_CODE =null;
        MachineInsuranceFragment.INSURANCE_FALG=true;
        MachineInsuranceFragment.INSURANCE_COMPANY=null;
        MachineInsuranceFragment.INSURANCE_START_DATE=null;
        MachineInsuranceFragment.INSURANCE_END_DATE=null;
        MachineInsuranceFragment.INSURANCE_CLAIMED_YEAR=null;
        MachineInsuranceFragment.INSURANCE_NCB=null;
        MachineInsuranceFragment.MACHINE_AGE=null;

    }

}
