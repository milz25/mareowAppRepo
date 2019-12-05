package com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments;


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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mareow.recaptchademo.Adapters.DetailActivityViewPagerAdapter;
import com.mareow.recaptchademo.DataModels.CreateRentalPlan;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRentalPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRentalPlanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewPager addRentalPlanViewPager;
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    DetailActivityViewPagerAdapter adapter;

    public FloatingActionButton mButtonSave;

    ProgressDialog progressDialog;
    public AddRentalPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRentalPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRentalPlanFragment newInstance(String param1, String param2) {
        AddRentalPlanFragment fragment = new AddRentalPlanFragment();
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
        View view=inflater.inflate(R.layout.fragment_add_rental_plan, container, false);
        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait..........");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        addRentalPlanViewPager = (ViewPager)view.findViewById(R.id.owner_add_rental_plan_viewpager);
        llPagerDots = (LinearLayout)view.findViewById(R.id.owner_add_rental_plan_pager_dots);
        addRentalPlanViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


        mButtonSave=(FloatingActionButton)view.findViewById(R.id.owner_add_rental_plan);
        mButtonSave.setVisibility(View.GONE);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRentalPlan();
            }
        });

        setData();

        setupViewPager(addRentalPlanViewPager);
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_dots);


    }


    private void setupViewPager(ViewPager viewPager) {

        adapter = new DetailActivityViewPagerAdapter(getChildFragmentManager());

        
        adapter.addFragment(new PlanMainFragment(), "PlanMain");
        adapter.addFragment(new PlanGeneralDetailsFragment(), "general Details");
        adapter.addFragment(new PlanAttachmentDetailsFragment(), "PlanAtttachment");
        adapter.addFragment(new PlanOperatorDetailsFragment(), "PlanOperator");
        adapter.addFragment(new PlanMobilityDetailsFragment(), "PlanMobility");
        adapter.addFragment(new PlanAssociatedMachineFragment(), "PlanAssociated");


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

    private void saveRentalPlan() {

        if (PlanMainFragment.PLAN_TYPE_CODE==null){
            showSnackbar("Please select plan type.");
            return;
        }else if (PlanMainFragment.PLAN_USAGE_CODE==null){
            showSnackbar("Please select plan usage.");
            return;
        }
        else if (PlanMainFragment.PLAN_NAME==null){
            showSnackbar("Please select plan name.");
            return;
        }
        else if (PlanMainFragment.PLAN_DESCRIPTION==null){
            showSnackbar("Please select plan description.");
            return;
        }

        else if (PlanGeneralDetailsFragment.LOAD_CODE==null){
            showSnackbar("Please select Load Capacity.");
            return;
        }
        else if (PlanGeneralDetailsFragment.SHIFT_CODE==null){
            showSnackbar("Please select Shift.");
            return;
        }
        else if (PlanGeneralDetailsFragment.BASIC_RATE==null){
            showSnackbar("Please select Basic Rate.");
            return;
        }
        else if (PlanGeneralDetailsFragment.HOURS_MONTHLY==null){
            showSnackbar("Please select Hours Monthly.");
            return;
        }
        else if (PlanGeneralDetailsFragment.DAILY_HOURS==null){
            showSnackbar("Please select Daily Hours.");
            return;
        }
        else if (PlanGeneralDetailsFragment.DAYS_MONTHLY==null){
            showSnackbar("Please select Days Monthly.");
            return;
        }
        else if (PlanGeneralDetailsFragment.OVERTIME==null){
            showSnackbar("Please select overtime.");
            return;
        }

        if (PlanMainFragment.PLAN_USAGE_CODE.equals("DAILY")){
            if (PlanGeneralDetailsFragment.DAYS_COMMITED==null){
                showSnackbar("Please select Days Commited.");
                return;
            }
        }
        if (PlanMainFragment.PLAN_USAGE_CODE.equals("HOURLY")){
            if (PlanGeneralDetailsFragment.HOURS_COMMITED==null){
                showSnackbar("Please select Hours Commited.");
                return;
            }
        }

        if (PlanMainFragment.PLAN_USAGE_CODE.equals("MONTHLY")){
            if (PlanGeneralDetailsFragment.MONTHLY_COMMITED==null){
                showSnackbar("Please select Monthly Commited.");
                return;
            }
        }

        if (PlanGeneralDetailsFragment.GST){
            if (PlanGeneralDetailsFragment.CGST==null){
                showSnackbar("Please select CGST.");
                return;
            }
            if (PlanGeneralDetailsFragment.SGST==null){
                showSnackbar("Please select SGST.");
                return;
            }
        }
        if (PlanGeneralDetailsFragment.IGST){
            if (PlanGeneralDetailsFragment.IGST_STRING==null){
                showSnackbar("Please select IGST %.");
                return;
            }
        }

        if (PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT){
            if (PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT_RATE==null){
                showSnackbar("Please select attachment rate.");
                return;
            }
        }

        if (PlanOperatorDetailsFragment.OPERATOR_FLAG){
            if (PlanOperatorDetailsFragment.OPERATOR_RATE==null){
                showSnackbar("Please select operator rate.");
                return;
            }
        }

        if (PlanMobilityDetailsFragment.MOBILITY_REPONSIBILE.equals("O")){
            if (PlanMobilityDetailsFragment.MOBILITY_AMOUNT==null){
                showSnackbar("Please select mobility amount.");
                return;
            }
        }
        if (PlanMobilityDetailsFragment.DEMOBILITY_REPONSIBILE.equals("O")){
            if (PlanMobilityDetailsFragment.DEMOBILITY_AMOUNT==null){
                showSnackbar("Please select demobility amount.");
                return;
            }
        }


        CreateRentalPlan createRentalPlan=new CreateRentalPlan();
        createRentalPlan.setPlanType(PlanMainFragment.PLAN_TYPE_CODE);
        createRentalPlan.setPlanUsage(PlanMainFragment.PLAN_USAGE_CODE);
        createRentalPlan.setPlanName(PlanMainFragment.PLAN_NAME);
        createRentalPlan.setPlanDescription(PlanMainFragment.PLAN_DESCRIPTION);
        createRentalPlan.setLoad(PlanGeneralDetailsFragment.LOAD_CODE);
        createRentalPlan.setShift(PlanGeneralDetailsFragment.SHIFT_CODE);
        createRentalPlan.setBasicRate(PlanGeneralDetailsFragment.BASIC_RATE);
        createRentalPlan.setDailyHours(PlanGeneralDetailsFragment.DAILY_HOURS);
        createRentalPlan.setDailyMinHours(PlanGeneralDetailsFragment.DAILY_HOURS);
        createRentalPlan.setDays(PlanGeneralDetailsFragment.DAYS_MONTHLY);
        createRentalPlan.setMinDays(PlanGeneralDetailsFragment.DAYS_MONTHLY);
        createRentalPlan.setMonthlyHours(PlanGeneralDetailsFragment.HOURS_MONTHLY);

        if (PlanMainFragment.PLAN_USAGE_CODE.equals("DAILY")){
            createRentalPlan.setCommitmentMonth(PlanGeneralDetailsFragment.DAYS_COMMITED);
        }else if (PlanMainFragment.PLAN_USAGE_CODE.equals("HOURLY")){
            createRentalPlan.setCommitmentMonth(PlanGeneralDetailsFragment.HOURS_COMMITED);
        }else if(PlanMainFragment.PLAN_USAGE_CODE.equals("MONTHLY")){
            createRentalPlan.setCommitmentMonth(PlanGeneralDetailsFragment.MONTHLY_COMMITED);
        }

        createRentalPlan.setOvertime(Integer.parseInt(PlanGeneralDetailsFragment.OVERTIME));
        createRentalPlan.setGst(PlanGeneralDetailsFragment.GST);
        if (PlanGeneralDetailsFragment.GST){
            createRentalPlan.setCgst(Float.parseFloat(PlanGeneralDetailsFragment.CGST));
            createRentalPlan.setSgst(Float.parseFloat(PlanGeneralDetailsFragment.SGST));
        }

        createRentalPlan.setIgstFlg(PlanGeneralDetailsFragment.IGST);
        if (PlanGeneralDetailsFragment.IGST){
            createRentalPlan.setIgst(Float.parseFloat(PlanGeneralDetailsFragment.IGST_STRING));
        }

        createRentalPlan.setDefaultAttachment(PlanAttachmentDetailsFragment.DEFUALT_ATTACHMENT);
        createRentalPlan.setExtraAttachment(PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT);
        if (PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT){
            createRentalPlan.setExtraAttachmentRateFlg(PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT_RATE_FLAG);
            createRentalPlan.setExtraAttachmentRate(Float.parseFloat(PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT_RATE));
        }

        createRentalPlan.setOperatorFlg(PlanOperatorDetailsFragment.OPERATOR_FLAG);
        if (PlanOperatorDetailsFragment.OPERATOR_FLAG){
            createRentalPlan.setRateFlg(PlanOperatorDetailsFragment.OPERATOR_RATE_FLAG);
            createRentalPlan.setRate(Float.parseFloat(PlanOperatorDetailsFragment.OPERATOR_RATE));
        }

        createRentalPlan.setAccomodation(PlanOperatorDetailsFragment.ACCOMODATION);
        createRentalPlan.setTransportation(PlanOperatorDetailsFragment.TRANSPORTATION);
        createRentalPlan.setFood(PlanOperatorDetailsFragment.FOOD);


        createRentalPlan.setMobilityResponsible(PlanMobilityDetailsFragment.MOBILITY_REPONSIBILE);
         if (PlanMobilityDetailsFragment.MOBILITY_REPONSIBILE.equals("O")){
             createRentalPlan.setTakingAmount(PlanMobilityDetailsFragment.TAKING_AMOUNT);
             createRentalPlan.setMobilityAmt(Float.parseFloat(PlanMobilityDetailsFragment.MOBILITY_AMOUNT));
         }

         createRentalPlan.setDemobilityResponsible(PlanMobilityDetailsFragment.DEMOBILITY_REPONSIBILE);
         if (PlanMobilityDetailsFragment.DEMOBILITY_REPONSIBILE.equals("O")){
             createRentalPlan.setResponsibilityAmount(PlanMobilityDetailsFragment.RESPONSIBILITY_AMOUNT);
             createRentalPlan.setDemobilityAmt(Float.parseFloat(PlanMobilityDetailsFragment.DEMOBILITY_AMOUNT));
         }

         createRentalPlan.setUserId(TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_USERID,0));
        String token= TokenManager.getSessionToken();
        if (progressDialog!=null)
         progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> rentalPlanCall=apiInterface.createRentalPlan("Bearer "+token,createRentalPlan);
        rentalPlanCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                 progressDialog.dismiss();
                if (response.isSuccessful()){
                  showSnackbar(response.body().getMessage());
                }
                else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                      showSnackbar("Enable to Save Plan");
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


    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void setData() {

        PlanMainFragment.PLAN_TYPE_CODE=null;
        PlanMainFragment.PLAN_USAGE_CODE=null;
        PlanMainFragment.PLAN_NAME=null;
        PlanMainFragment.PLAN_DESCRIPTION=null;


        PlanGeneralDetailsFragment.LOAD_CODE=null;
        PlanGeneralDetailsFragment.SHIFT_CODE=null;
        PlanGeneralDetailsFragment.BASIC_RATE=null;
        PlanGeneralDetailsFragment.DAILY_HOURS=null;
        PlanGeneralDetailsFragment.HOURS_MONTHLY=null;
        PlanGeneralDetailsFragment.DAYS_MONTHLY=null;
        PlanGeneralDetailsFragment.HOURS_COMMITED=null;
        PlanGeneralDetailsFragment.DAYS_COMMITED=null;
        PlanGeneralDetailsFragment.MONTHLY_COMMITED=null;
        PlanGeneralDetailsFragment.OVERTIME=null;
        PlanGeneralDetailsFragment.CGST=null;
        PlanGeneralDetailsFragment.SGST=null;
        PlanGeneralDetailsFragment.IGST_STRING=null;


        PlanGeneralDetailsFragment.GST=false;
        PlanGeneralDetailsFragment.IGST=false;


        PlanAttachmentDetailsFragment.DEFUALT_ATTACHMENT=true;
        PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT=false;
        PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT_RATE_FLAG=true;

        PlanAttachmentDetailsFragment.EXTRA_ATTACHMENT_RATE=null;


        PlanOperatorDetailsFragment.OPERATOR_FLAG=true;
        PlanOperatorDetailsFragment.OPERATOR_RATE=null;
        PlanOperatorDetailsFragment.OPERATOR_RATE_FLAG=true;
        PlanOperatorDetailsFragment.ACCOMODATION=false;
        PlanOperatorDetailsFragment.TRANSPORTATION=false;
        PlanOperatorDetailsFragment.FOOD=false;

        PlanMobilityDetailsFragment.TAKING_AMOUNT=true;
        PlanMobilityDetailsFragment.MOBILITY_AMOUNT=null;
        PlanMobilityDetailsFragment.MOBILITY_REPONSIBILE="O";

        PlanMobilityDetailsFragment.RESPONSIBILITY_AMOUNT=true;
        PlanMobilityDetailsFragment.DEMOBILITY_AMOUNT=null;
        PlanMobilityDetailsFragment.DEMOBILITY_REPONSIBILE="O";

    }

}
