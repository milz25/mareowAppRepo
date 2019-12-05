package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.Adapters.DetailActivityViewPagerAdapter;
import com.mareow.recaptchademo.DataModels.AcceptWorkOrder;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.RenterWorkOrder;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.WorkOrderCancel;
import com.mareow.recaptchademo.DataModels.WorkOrderExtend;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfferDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferDetailsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ViewPager mOfferDetailsViewPager;
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    DetailActivityViewPagerAdapter adapter;

    AppCompatTextView workNo;
    AppCompatTextView workDates;
    AppCompatTextView workStatus;

    AppCompatTextView mEstimatedCost;
    AppCompatImageView btnOfferAccepted;

    OfferWorkOrder offerWorkOrder;
    public  AppCompatTextView offerEstimatedCostValue;
    public  AppCompatTextView offerEstimatedCosttext;
    public  AppCompatTextView offerEstimatedGst;

    FragmentManager childFragmentManager;

    RenterWorkOrder renterWorkOrder;

    ProgressDialog progressDialog;
     public OfferDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OfferDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OfferDetailsFragment newInstance(String param1, String param2) {
        OfferDetailsFragment fragment = new OfferDetailsFragment();
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
        View view=inflater.inflate(R.layout.fragment_offer_details, container, false);
        if (Constants.USER_ROLE.equals("Renter")){
            RenterMainActivity.txtRenterTitle.setText("Work Order Details");
        }else {
            OwnerMainActivity.txtOwnerTitle.setText("Work Order Details");
        }

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait................");
        }

        childFragmentManager=getChildFragmentManager();

        Bundle bundle=getArguments();
        offerWorkOrder=(OfferWorkOrder) bundle.getSerializable("offerDetails");


        if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){

            callRenterWorkOrderApi();
        }

        initView(view);


        return view;
    }


    private void initView(View view) {

        //Header
        workNo=(AppCompatTextView)view.findViewById(R.id.offer_details_header_workorder_no);
        workDates=(AppCompatTextView)view.findViewById(R.id.offer_details_header_date);
        //workStatus=(AppCompatTextView)view.findViewById(R.id.offer_details_header_status);

        workNo.setText("Work Order # "+offerWorkOrder.getWorkorderDTO().getWorkOrderNo());
        workDates.setText(Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkStartDate())+" - "+Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrder.getWorkorderDTO().getWorkEndDate()));

      /*  workStatus.setText(offerWorkOrder.getWorkorderDTO().getWorkOrderStatusMeaning());
        workStatus.setTextColor(getActivity().getResources().getColor(R.color.theme_orange));*/

        offerEstimatedCosttext=(AppCompatTextView)view.findViewById(R.id.offer_details_footer_estimated_cost_text);
        offerEstimatedCostValue=(AppCompatTextView)view.findViewById(R.id.offer_details_footer_estimated_cost_value);
        offerEstimatedGst=(AppCompatTextView)view.findViewById(R.id.offer_details_footer_estimated_Gst);

        //footer
       // mEstimatedCost=(AppCompatTextView)view.findViewById(R.id.offer_details_footer_estimated_cost);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        String price="\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkOrderAmt());
        String eString="Estimated Amount :";

       /* SpannableString ss2=  new SpannableString(price);
        ss2.setSpan(new RelativeSizeSpan(2f), 0,price.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss2.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, price.length(), 1);

        String eString="Estimated Amount :";
        SpannableString ss1=  new SpannableString(eString);
        ss1.setSpan(new RelativeSizeSpan(1f), 0,eString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss1.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, eString.length(), 0);// set color

       *//* SpannableString ss2=  new SpannableString(amountString);
        ss2.setSpan(new RelativeSizeSpan(2f), 0,amountString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.colorPrimary)), 0, amountString.length(), 0);*//*
*/
        String GstandIGst="( * GST / IGST and Mobility Amount Excluded )";
      /*  if (offerWorkOrder.getWorkorderDTO().isGst()){
            GstandIGst="( * GST / IGST Amount Included )";
        }else {
            GstandIGst="( * GST / IGST Amount Excluded )";
        }*/
/*
        SpannableString ss3=  new SpannableString(GstandIGst);
        //ss3.setSpan(new AbsoluteSizeSpan(getActivity().getResources().getDimensionPixelSize(R.dimen.text2),true), 0,GstandIGst.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss3.setSpan(new RelativeSizeSpan(0.5f), 0,GstandIGst.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss3.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.theme_orange)), 0, GstandIGst.length(), 0);


        SpannableStringBuilder builder=new SpannableStringBuilder();
        builder.append(ss1);
        builder.append(ss2+"\n");
        builder.append(ss3);
        mEstimatedCost.setText(builder);*/

       offerEstimatedCosttext.setText(eString);
       offerEstimatedCostValue.setText(price);
       offerEstimatedGst.setText(GstandIGst);

       btnOfferAccepted=(AppCompatImageView) view.findViewById(R.id.offer_details_footer_accept_offer);
       btnOfferAccepted.setOnClickListener(this);

       if (Constants.USER_ROLE.equals("Renter")){
           if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_OWN") || offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
               btnOfferAccepted.setEnabled(true);
               btnOfferAccepted.setImageResource(R.drawable.add_squre_final);
           }else {
               btnOfferAccepted.setEnabled(false);
               btnOfferAccepted.setImageResource(R.drawable.add_diable_squre);
           }
       }

        if (Constants.USER_ROLE.equals("Owner")){
            if (offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("OFFER") || offerWorkOrder.getWorkorderDTO().getWorkOrderStatus().equals("ACCEPT_REN")){
                btnOfferAccepted.setEnabled(true);
                btnOfferAccepted.setImageResource(R.drawable.add_squre_final);
            }else {
                btnOfferAccepted.setEnabled(false);
                btnOfferAccepted.setImageResource(R.drawable.add_diable_squre);
            }
        }


        mOfferDetailsViewPager = (ViewPager)view.findViewById(R.id.offer_details_viewpager);
        llPagerDots = (LinearLayout)view.findViewById(R.id.offer_details_pager_dots);
        mOfferDetailsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               /* if (position==0){
                    RenterMainActivity.navItemIndexRenter=21;
                }else {
                    RenterMainActivity.navItemIndexRenter=20;
                }*/
                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dots);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected_dots);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(mOfferDetailsViewPager);
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_dots);


    }

    private void setupViewPager(ViewPager viewPager) {

        if (getActivity()!=null && isAdded()) {

            adapter = new DetailActivityViewPagerAdapter(childFragmentManager);

            if (Constants.CHECK_OFFER) {

                adapter.addFragment(new OfferGeneralDetailsFragment(offerWorkOrder), "GeneralDetails");
                adapter.addFragment(new OfferMachineDetailsFragment(offerWorkOrder), "MachineDetails");
                adapter.addFragment(new OfferAttachmentFragment(offerWorkOrder), "AttachmentDetails");
                adapter.addFragment(new OfferRentalPlanFragment(offerWorkOrder), "RentalPlanDetails");
                adapter.addFragment(new OfferOperatorandSupervisorFragment(offerWorkOrder), "OperSuperDetails");
                adapter.addFragment(new OfferMobilizeDetailsFragment(offerWorkOrder), "MobilizedDetails");
                adapter.addFragment(new OfferTandCFragment(offerWorkOrder), "TermAndCondition");

            } else {

                adapter.addFragment(new OfferGeneralDetailsFragment(offerWorkOrder), "GeneralDetails");
                adapter.addFragment(new OfferMachineDetailsFragment(offerWorkOrder), "MachineDetails");
                adapter.addFragment(new OfferAttachmentFragment(offerWorkOrder), "AttachmentDetails");
                adapter.addFragment(new OfferRentalPlanFragment(offerWorkOrder), "RentalPlanDetails");
                adapter.addFragment(new OfferOperatorandSupervisorFragment(offerWorkOrder), "OperSuperDetails");
                adapter.addFragment(new OfferMobilizeDetailsFragment(offerWorkOrder), "MobilizedDetails");
                adapter.addFragment(new OfferTandCFragment(offerWorkOrder), "TermAndCondition");
                adapter.addFragment(new RunningLogFragment(offerWorkOrder), "RunningLogDetials");
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offer_details_footer_accept_offer:
                  //callAcceptWorkOrderOfferAcceptedByOwner();
                callOfferAcceptanceFragment();
                break;
        }
    }

    private void callOfferAcceptanceFragment() {

        Fragment fragment = new OfferAcceptanceFragment(offerWorkOrder,renterWorkOrder);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void callAcceptWorkOrderOfferAcceptedByOwner() {

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        AcceptWorkOrder acceptWorkOrder=new AcceptWorkOrder();
      //  acceptWorkOrder.setPartyId(String.valueOf(partyId));

        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.acceptworkOrderApibyOwner("Bearer "+token,acceptWorkOrder);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                showSnackBar(t.getMessage());
                if (progressDialog!=null)
                    progressDialog.dismiss();
            }
        });

    }
    private void callRejectWorkOrder() {

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        int workorderId=offerWorkOrder.getWorkorderDTO().getWorkOrderId();

        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.rejectWorkOrderApi("Bearer "+token,workorderId);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                showSnackBar(t.getMessage());
                if (progressDialog!=null)
                    progressDialog.dismiss();
            }
        });

    }

    private void callAcceptEXTEND_WOApi() {

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        WorkOrderExtend workOrderExtend=new WorkOrderExtend();
        workOrderExtend.setWorkorderNo(offerWorkOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderExtend.setWorkOrderId(offerWorkOrder.getWorkorderDTO().getWorkOrderId());
        workOrderExtend.setWoStartDate(offerWorkOrder.getWorkorderDTO().getWorkStartDate());
        workOrderExtend.setWoEndDate(offerWorkOrder.getWorkorderDTO().getWorkEndDate());
        workOrderExtend.setWoStartDateStr(offerWorkOrder.getWorkorderDTO().getWorkStartDateStr());
        workOrderExtend.setWoEndDateStr(offerWorkOrder.getWorkorderDTO().getWorkEndDateStr());
       // workOrderExtend.setWoExtendedEndDate(offerWorkOrder.getWorkorderDTO().getE);
        //workOrderExtend.setWoExtendedEndDateStr(offerWorkOrder.getWorkorderDTO().toString());

        if (Constants.USER_ROLE.equals("Owner")){
            workOrderExtend.setExtendStatus(Constants.OWNER_ACCEPT_EXTEND);
        }
        TokenManager.showProgressDialog(getActivity());
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.extendworkOrder("Bearer "+token,workOrderExtend);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                TokenManager.dissmisProgress();
                showSnackBar(t.getMessage());
            }
        });
    }


    private void showSnackBar(String message) {
        Snackbar snackbar= Snackbar.make(getView(),message,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getActivity().getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    private void callRejectEXTEND_WOApi() {

        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        WorkOrderExtend workOrderExtend=new WorkOrderExtend();
        workOrderExtend.setWorkorderNo(offerWorkOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderExtend.setWorkOrderId(offerWorkOrder.getWorkorderDTO().getWorkOrderId());
        workOrderExtend.setWoStartDate(offerWorkOrder.getWorkorderDTO().getWorkStartDate());
        workOrderExtend.setWoEndDate(offerWorkOrder.getWorkorderDTO().getWorkEndDate());
        workOrderExtend.setWoStartDateStr(offerWorkOrder.getWorkorderDTO().getWorkStartDateStr());
        workOrderExtend.setWoEndDateStr(offerWorkOrder.getWorkorderDTO().getWorkEndDateStr());
        // workOrderExtend.setWoExtendedEndDate(offerWorkOrder.getWorkorderDTO().getE);
        //workOrderExtend.setWoExtendedEndDateStr(offerWorkOrder.getWorkorderDTO().toString());

        if (Constants.USER_ROLE.equals("Owner")){
            workOrderExtend.setExtendStatus(null);
        }

        TokenManager.showProgressDialog(getActivity());
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.rejectExtendedWO("Bearer "+token,workOrderExtend);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                TokenManager.dissmisProgress();
                showSnackBar(t.getMessage());
            }
        });
    }


    private void callRejectCANCEL_WOApi() {
      /*  if (editCancelDate.getText().toString().isEmpty()){
            Snackbar.make(getView(),"Please enter Cancellation date",Snackbar.LENGTH_LONG).show();
            editCancelDate.requestFocus();
            return;
        }
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startTimeStamp=null;

        try {
            startTimeStamp=input.parse(editCancelDate.getText().toString().trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        /*SimpleDateFormat outputFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String cancelDateString=outputFormat.format(startTimeStamp);
*/
        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        String token=TokenManager.getSessionToken();

        WorkOrderCancel workOrderCancel=new WorkOrderCancel();
        workOrderCancel.setWorkorderNo(offerWorkOrder.getWorkorderDTO().getWorkOrderNo());
        workOrderCancel.setWorkOrderId(offerWorkOrder.getWorkorderDTO().getWorkOrderId());
        workOrderCancel.setWoStartDate(offerWorkOrder.getWorkorderDTO().getWorkStartDate());
        workOrderCancel.setWoEndDate(offerWorkOrder.getWorkorderDTO().getWorkEndDate());
        workOrderCancel.setWoStartDateStr(offerWorkOrder.getWorkorderDTO().getWorkStartDateStr());
        workOrderCancel.setWoEndDateStr(offerWorkOrder.getWorkorderDTO().getWorkEndDateStr());

        //workOrderCancel.setWoCancelDate(cancelDateString);
       // workOrderCancel.setWoCancelDateStr(editCancelDate.getText().toString());
        workOrderCancel.setCancelStatus(Constants.REJECT_CANCEL_WO);


        TokenManager.showProgressDialog(getActivity());

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> callOfferWorkOrder=apiInterface.cancelworkOrder("Bearer "+token,workOrderCancel);
        callOfferWorkOrder.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                TokenManager.dissmisProgress();
                if (response.isSuccessful()){
                    showSnackBar(response.body().getMessage());
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                TokenManager.dissmisProgress();
                showSnackBar(t.getMessage());
            }
        });
    }

    private void callRenterWorkOrderApi() {

       // int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
      //  String token=TokenManager.getSessionToken();

        int workorderId=offerWorkOrder.getWorkorderDTO().getWorkOrderId();

        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<RenterWorkOrder> callOfferWorkOrder=apiInterface.getWorkOrderEsign(workorderId);
        callOfferWorkOrder.enqueue(new Callback<RenterWorkOrder>() {
            @Override
            public void onResponse(Call<RenterWorkOrder> call, Response<RenterWorkOrder> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    renterWorkOrder=response.body();

                    if (Constants.USER_ROLE.equals("Renter")){
                        if (TextUtils.isEmpty(renterWorkOrder.getOwnerSign()) || renterWorkOrder.getOwnerSign()==null){
                            btnOfferAccepted.setEnabled(false);
                            btnOfferAccepted.setImageResource(R.drawable.add_diable_squre);
                        }else {
                            btnOfferAccepted.setEnabled(true);
                            btnOfferAccepted.setImageResource(R.drawable.add_squre_final);
                        }
                    }

                    if (Constants.USER_ROLE.equals("Owner")){
                        if (!TextUtils.isEmpty(renterWorkOrder.getOwnerSign())){
                            btnOfferAccepted.setEnabled(false);
                            btnOfferAccepted.setImageResource(R.drawable.add_diable_squre);
                        }else {
                            btnOfferAccepted.setEnabled(true);
                            btnOfferAccepted.setImageResource(R.drawable.add_squre_final);
                        }
                    }
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        showSnackBar("Record Not Found");
                    }

                }
            }
            @Override
            public void onFailure(Call<RenterWorkOrder> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackBar(t.getMessage());

            }
        });
    }


}
