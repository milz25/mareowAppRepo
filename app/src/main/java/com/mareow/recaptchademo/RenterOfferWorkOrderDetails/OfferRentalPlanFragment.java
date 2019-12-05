package com.mareow.recaptchademo.RenterOfferWorkOrderDetails;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mareow.recaptchademo.DataModels.InvoiceByInvoiceId;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.PlanById;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;

import java.io.IOException;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OfferRentalPlanFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    TextInputEditText mPlan;
    TextInputEditText mShift;
    TextInputEditText mPlanDescription;
    TextInputEditText mBasicRate;
    TextInputEditText mAttachmentCost;
    AppCompatImageView mPlanDetails;

    AppCompatCheckBox mGST;
    AppCompatCheckBox mIGST;
    AppCompatTextView mPlanName;


    OfferWorkOrder offerWorkOrder;
    PlanById planById;
    ProgressDialog progressDialog;
    public OfferRentalPlanFragment(OfferWorkOrder offerWorkOrder) {
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
        View view=inflater.inflate(R.layout.fragment_offer_rental_plan, container, false);

        if (getActivity()!=null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...............");
        }
        initView(view);
        return view;
    }

    private void initView(View view) {

        mPlanName=(AppCompatTextView)view.findViewById(R.id.ODF_rentalPlan_planname);
        mPlanName.setText(offerWorkOrder.getWorkorderDTO().getPlanName());

        mPlanDetails=(AppCompatImageView) view.findViewById(R.id.ODF_rentalPlan_planDetails);
        mPlanDetails.setOnClickListener(this);


        mPlan=(TextInputEditText)view.findViewById(R.id.ODF_rentalPlan_plan);
        mPlan.setText(offerWorkOrder.getWorkorderDTO().getPlanType()+" - "+offerWorkOrder.getWorkorderDTO().getPlanUsage());
        mPlan.setInputType(InputType.TYPE_NULL);


        mShift=(TextInputEditText)view.findViewById(R.id.ODF_rentalPlan_shift);
        mShift.setText(offerWorkOrder.getWorkorderDTO().getShift());
        mShift.setInputType(InputType.TYPE_NULL);

        mPlanDescription=(TextInputEditText)view.findViewById(R.id.ODF_rentalPlan_plandescription);
        mPlanDescription.setText(offerWorkOrder.getWorkorderDTO().getPlanDescription());
        mPlanDescription.setInputType(InputType.TYPE_NULL);


        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        mBasicRate=(TextInputEditText)view.findViewById(R.id.ODF_rentalPlan_basic_rate);
        mBasicRate.setText("\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkorderDTO().getBasicRate()));
        mBasicRate.setInputType(InputType.TYPE_NULL);


        mAttachmentCost=(TextInputEditText)view.findViewById(R.id.ODF_rentalPlan_attachment_rate);
        mAttachmentCost.setText("\u20B9 "+IndianCurrencyFormat.format(offerWorkOrder.getWorkorderDTO().getExtraAttachmentHourlyRate()));
        mAttachmentCost.setInputType(InputType.TYPE_NULL);


        mGST=(AppCompatCheckBox)view.findViewById(R.id.ODF_rentalPlan_gst);
        mGST.setChecked(offerWorkOrder.getWorkorderDTO().isGst());
        mGST.setKeyListener(null);

        mIGST=(AppCompatCheckBox)view.findViewById(R.id.ODF_rentalPlan_igst);
        mIGST.setChecked(offerWorkOrder.getWorkorderDTO().isIgst());
        mIGST.setKeyListener(null);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ODF_rentalPlan_planDetails:
                callSpecificPlanForDetails(offerWorkOrder.getWorkorderDTO().getPlanId());
                break;
        }
    }

    private void callSpecificPlanForDetails(int planId) {
        if (progressDialog!=null)
        progressDialog.show();
        String token= TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<PlanById> paymentCall=apiInterface.getRentalPlanById("Bearer "+token,planId);
        paymentCall.enqueue(new Callback<PlanById>() {
            @Override
            public void onResponse(Call<PlanById> call, Response<PlanById> response) {
                if (progressDialog!=null)
                 progressDialog.dismiss();
                if (response.isSuccessful()){
                    planById=response.body();
                    callPlanDetailsDialogs(planById);
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired(getActivity());
                    }
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();
                        StatuTitleMessageResponse mError=new StatuTitleMessageResponse();
                        try {
                            mError= gson.fromJson(response.errorBody().string(),StatuTitleMessageResponse .class);
                            Toast.makeText(getActivity(), "Record not found", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<PlanById> call, Throwable t) {
                if (progressDialog!=null)
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callPlanDetailsDialogs(PlanById plan) {


        AppCompatTextView txtPlanType;
        AppCompatTextView txtPlanName;
        AppCompatTextView txtPlanUsage;
        AppCompatTextView txtPlanDescription;

        ExpandableCardView expandableCardViewGeneral;
        ExpandableCardView expandableCardViewAttachment;
        ExpandableCardView expandableCardViewOperator;
        ExpandableCardView expandableCardViewMobility;



        String[] parents = new String[]{"General Details", "Attachment","Operator","Mobility Of Machine"};
        AppCompatImageButton btnClose;

       // ExpandableListView mExpandableList;
      //  ExpandableAdapter expandableAdapter;

        final Dialog dialog=new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.rental_plan_details_dialogs);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        txtPlanType=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plantype);
        txtPlanName=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planname);
        txtPlanUsage=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planusage);
        txtPlanDescription=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plandescription);


        txtPlanType.setText(plan.getPlanType());
        txtPlanName.setText(plan.getPlanName());
        txtPlanUsage.setText(plan.getPlanUsage());
        txtPlanDescription.setText(plan.getPlanDescription());


        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.rental_plan_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        expandableCardViewGeneral=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandablegeneral);
        expandableCardViewAttachment=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandableattachment);
        expandableCardViewOperator=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandableoperator);
        expandableCardViewMobility=(ExpandableCardView)dialog.findViewById(R.id.rental_plan_dailog_expandablemobility);


        expandableCardViewGeneral.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){
                    if (expandableCardViewAttachment.isExpanded()){
                        expandableCardViewAttachment.collapse();
                    }else if (expandableCardViewOperator.isExpanded()){
                        expandableCardViewOperator.collapse();
                    }else if (expandableCardViewMobility.isExpanded()){
                        expandableCardViewMobility.collapse();
                    }
                }
            }
        });
        expandableCardViewAttachment.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){

                    if (expandableCardViewGeneral.isExpanded()){
                        expandableCardViewGeneral.collapse();
                    }else if (expandableCardViewOperator.isExpanded()){
                        expandableCardViewOperator.collapse();
                    }else if (expandableCardViewMobility.isExpanded()){
                        expandableCardViewMobility.collapse();
                    }
                }
            }
        });

        expandableCardViewOperator.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){

                    if (expandableCardViewGeneral.isExpanded()){
                        expandableCardViewGeneral.collapse();
                    }else if (expandableCardViewAttachment.isExpanded()){
                        expandableCardViewAttachment.collapse();
                    }else if (expandableCardViewMobility.isExpanded()){
                        expandableCardViewMobility.collapse();
                    }

                }
            }
        });

        expandableCardViewMobility.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                if (isExpanded){

                    if (expandableCardViewGeneral.isExpanded()){
                        expandableCardViewGeneral.collapse();
                    }else if (expandableCardViewAttachment.isExpanded()){
                        expandableCardViewAttachment.collapse();
                    }else if (expandableCardViewOperator.isExpanded()){
                        expandableCardViewOperator.collapse();
                    }
                }
            }
        });

       // AppCompatTextView generalTitle=(AppCompatTextView)expandableCardViewGeneral.parentLayout.findViewById(R.id.parent_txt_general);
       // generalTitle.setText("General Details");
       // generalTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        AppCompatTextView load = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_load);
        AppCompatTextView shift = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_shift);
        AppCompatTextView basic_rate = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_basicrate);
        AppCompatTextView hours_Monthly = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_hourMonthly);
        AppCompatTextView daily_Hours = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_dailyhours);
        AppCompatTextView days_Monthly = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_daysmonthly);
        AppCompatTextView committed_Name = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_committed_name);
        AppCompatTextView committed_Month = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_committed_month);
        AppCompatTextView overtime = (AppCompatTextView) expandableCardViewGeneral.findViewById(R.id.rental_plan_dialog_overtime);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");

        load.setText(plan.getLoad());
        shift.setText(plan.getShift());
        basic_rate.setText("\u20B9 "+IndianCurrencyFormat.format(plan.getBasicRate()));
        hours_Monthly.setText(""+plan.getMonthlyHours());
        if (plan.getShift().equals("Single Shift")){
            daily_Hours.setText(String.valueOf(plan.getDailyHours()));
        }else {
            daily_Hours.setText(String.valueOf(plan.getDailyHours()));
        }

        days_Monthly.setText(""+offerWorkOrder.getWorkorderDTO().getPlanDays());
        if (plan.getPlanUsageCode().equals("DAILY")){
            committed_Name.setText("Days (Committed)");
            committed_Month.setText(""+plan.getDailyMinHours());
        }else if (plan.getPlanUsageCode().equals("MONTHLY")){
            committed_Name.setText("Months (Committed)");
            committed_Month.setText(""+plan.getCommitmentMonth());
        }else if (plan.getPlanUsageCode().equals("HOURLY")){
            committed_Name.setText("Hours (Committed)");
            committed_Month.setText(""+plan.getDailyMinHours());
        }

        overtime.setText(""+plan.getOvertime());



       // AppCompatTextView attatmentTitle=(AppCompatTextView)expandableCardViewAttachment.parentLayout.findViewById(R.id.parent_txt_attachment);
        //attatmentTitle.setText("Attachment");
       // attatmentTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        AppCompatCheckBox att_basic_rate = (AppCompatCheckBox) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_basicrate_check);
        AppCompatCheckBox extra_att = (AppCompatCheckBox) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_extra_attachment_check);
        AppCompatTextView extra_att_rate = (AppCompatTextView) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_rate);
        AppCompatTextView extra_att_rate_heading=(AppCompatTextView)expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_rate_heading);

        att_basic_rate.setChecked(plan.isExtraAttachment());
        att_basic_rate.setKeyListener(null);
        extra_att.setChecked(plan.isExtraAttachmentRateFlg());
        extra_att.setKeyListener(null);
        if (plan.isExtraAttachmentRateFlg()){
            extra_att_rate_heading.setText("Attachment Rate (Per Hr.)");
        }else {
            extra_att_rate_heading.setText("Attachment Rate (Fixed)");
        }
        extra_att_rate.setText("\u20B9 "+IndianCurrencyFormat.format(plan.getExtraAttachmentRate()));



       // AppCompatTextView operatorTitle=(AppCompatTextView)expandableCardViewOperator.findViewById(R.id.parent_txt_operator);
       //    operatorTitle.setText("Operator");
       // operatorTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        AppCompatCheckBox operatorInclude = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_oprator_check);
        AppCompatTextView operatorRate = (AppCompatTextView) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_fixed_amount);
        AppCompatCheckBox accomodation = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_accomodation);
        AppCompatCheckBox transportation = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_transportation);
        AppCompatCheckBox food = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_food);
        AppCompatTextView operatorRateHeading=(AppCompatTextView) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_fixed_amount_heading);

        operatorInclude.setChecked(plan.isOperatorFlg());
        operatorInclude.setKeyListener(null);
        if (plan.isRateFlg()){
            operatorRateHeading.setText("Amount (Fixed)");
        }else {
            operatorRateHeading.setText("Amount (Per Hr.)");
        }
        operatorRate.setText("\u20B9 "+IndianCurrencyFormat.format(plan.getRate()));
        accomodation.setChecked(plan.isAccomodation());
        accomodation.setKeyListener(null);
        transportation.setChecked(plan.isTransportation());
        transportation.setKeyListener(null);
        food.setChecked(plan.isFood());
        food.setKeyListener(null);





     //   AppCompatTextView mobilityTitle=(AppCompatTextView)expandableCardViewMobility.parentLayout.findViewById(R.id.parent_txt_mobility);
       // mobilityTitle.setText("Mobility Of Machine");
       // mobilityTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        LinearLayout mobilityLn = (LinearLayout) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobolity_ln);
        LinearLayout demobilityLn = (LinearLayout) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobolity_ln);

        AppCompatTextView mobilityAmount = (AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobility_amount);
        AppCompatTextView demobilityAmount = (AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityAmount);
        AppCompatTextView mobilityresposibility=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_responsibility);
        AppCompatTextView demobilityresposibility=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityResponsibility);
        AppCompatTextView mobiAmountHeading=(AppCompatTextView)expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobility_amount_heading);
        AppCompatTextView demobiAmountHeading=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityAmount_heading);

        if (!plan.getMobilityResponsible().equals("O")){
            mobilityLn.setVisibility(View.GONE);
        }
        if (!plan.getDemobilityResponsible().equals("O")){
            demobilityLn.setVisibility(View.GONE);
        }

        mobilityresposibility.setText(offerWorkOrder.getWorkorderDTO().getMobilityResponsible());
        if (plan.isTakingAmount()){
            mobiAmountHeading.setText("Mobility Amount (Fixed)");
        }else {
            mobiAmountHeading.setText("Mobility Amount (Per Hr.)");
        }

        mobilityAmount.setText("\u20B9 "+IndianCurrencyFormat.format(plan.getMobilityAmt()));
        demobilityresposibility.setText(offerWorkOrder.getWorkorderDTO().getDemobilityResponsible());
        if (plan.isResponsibilityAmount()){
            demobiAmountHeading.setText("Mobility Amount (Fixed)");
        }else {
            demobiAmountHeading.setText("Mobility Amount (Per Hr.)");
        }
        demobilityAmount.setText("\u20B9 "+IndianCurrencyFormat.format(plan.getDemobilityAmt()));



      //  mExpandableList=(ExpandableListView)dialog.findViewById(R.id.rental_plan_dailog_expandablelist);


       /* List<String> parent=new ArrayList<>();
        for (int i=0;i<parents.length;i++)
            parent.add(parents[i]);

        General_Details=new ArrayList<>();
        Attachment=new ArrayList<>();
        Operator =new ArrayList<>();
        MobilityOfMachine=new ArrayList<>();

        General_Details.add(rentalPlan.getLoad());
        General_Details.add(rentalPlan.getShift());
        General_Details.add(String.valueOf(rentalPlan.getBasicRate()));
        General_Details.add(String.valueOf(rentalPlan.getMonthlyHours()));
        General_Details.add(String.valueOf(rentalPlan.getDailyHours()));
        General_Details.add(String.valueOf(rentalPlan.getDays()));
        General_Details.add(String.valueOf(rentalPlan.getCommitmentMonth()));
        General_Details.add(String.valueOf(rentalPlan.getOvertime()));

        Attachment.add(String.valueOf(rentalPlan.isExtraAttachment()));
        Attachment.add(String.valueOf(rentalPlan.isExtraAttachmentRateFlg()));
        Attachment.add(String.valueOf(rentalPlan.getExtraAttachmentRate()));


        Operator.add(String.valueOf(rentalPlan.isOperatorFlg()));
        Operator.add(String.valueOf(rentalPlan.isAccomodation()));
        Operator.add(String.valueOf(rentalPlan.isTransportation()));
        Operator.add(String.valueOf(rentalPlan.isFood()));
        Operator.add(String.valueOf(rentalPlan.getRate()));

        MobilityOfMachine.add(String.valueOf(rentalPlan.getMobilityResponsible()));
        MobilityOfMachine.add(String.valueOf(rentalPlan.getMobilityAmt()));
        MobilityOfMachine.add(String.valueOf(rentalPlan.getDemobilityResponsible()));
        MobilityOfMachine.add(String.valueOf(rentalPlan.getDemobilityAmt()));


       *//* childList = new ArrayList<>();


        childList.add(General_Details);
        childList.add(Attachment);
        childList.add(Operator);
        childList.add(MobilityOfMachine);*//*

        Map<String, List<String>> childMap=new HashMap<>();
        childMap.put("General Details",General_Details);
        childMap.put("Attachment",Attachment);
        childMap.put("Operator",Operator);
        childMap.put("Mobility Of Machine",MobilityOfMachine);



        // expandableAdapter = new ExpandableAdapter(context, childList, parents);
        CommentsExpandableListAdapter expandableListAdapter=new CommentsExpandableListAdapter((Activity) context,parent,childMap);
        mExpandableList.setAdapter(expandableListAdapter);*/



        dialog.getWindow().setLayout((int) (getScreenWidth(getActivity()) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }



}
