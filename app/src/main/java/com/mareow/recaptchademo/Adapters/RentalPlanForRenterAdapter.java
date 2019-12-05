package com.mareow.recaptchademo.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterMachineBookFragment.BookDateAndReasonFragment;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.RentalPlanForRenterViewHolder;


import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class RentalPlanForRenterAdapter extends RecyclerView.Adapter<RentalPlanForRenterViewHolder> {

    Context context;
    List<RentalPlan> rentalPlanList;
    private ArrayList<String> General_Details,Attachment,Operator,MobilityOfMachine;
    public ArrayList<ArrayList<String>> childList;
    RecyclerViewClickListener listener;
    public RentalPlanForRenterAdapter(Context context, List<RentalPlan> rentalPlansList, RecyclerViewClickListener listener) {
        this.context=context;
        this.rentalPlanList=rentalPlansList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public RentalPlanForRenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.renter_plan_details_adapter_for_renter,parent,false);
        return new RentalPlanForRenterViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RentalPlanForRenterViewHolder holder, int position) {
        if (rentalPlanList.size()==1){
            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.cardView.setLayoutParams(layoutParams);
        }else {

            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            int scale = (int)(context.getResources().getDisplayMetrics().widthPixels *0.85);
            layoutParams.width = (int)scale;
            holder.cardView.setLayoutParams(layoutParams);

        }


        RentalPlan rentalPlan=rentalPlanList.get(position);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");

        holder.planName.setText(rentalPlan.getPlanName());

        if (rentalPlan.getPlanId()== BookDateAndReasonFragment.selectedPlan.get(0).getPlanId()){
            holder.planName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selected_plan,0,0,0);
        }else {
            holder.planName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.unselected_plan,0,0,0);
        }

        holder.plan.setText(rentalPlan.getPlanUsage());
        holder.plan.setInputType(InputType.TYPE_NULL);
        holder.shift.setText(rentalPlan.getShift());
        holder.shift.setInputType(InputType.TYPE_NULL);
        holder.basicRate.setText("\u20B9"+" "+IndianCurrencyFormat.format(rentalPlan.getBasicRate()));
        holder.basicRate.setInputType(InputType.TYPE_NULL);
        holder.overtime.setText(String.valueOf(rentalPlan.getOvertime()));
        holder.overtime.setInputType(InputType.TYPE_NULL);

        holder.OperatorIncluded.setChecked(rentalPlan.isOperatorFlg());
        //holder.OperatorIncluded.setTextColor(context.getResources().getColor(android.R.color.black));
        holder.OperatorIncluded.setKeyListener(null);

        if (rentalPlan.getMobilityResponsible().equals("C")){
            holder.mobilityTo.setText("Client");
        }else {
            holder.mobilityTo.setText("Owner");
        }

        if (rentalPlan.getDemobilityResponsible().equals("C")){
            holder.demobilityFrom.setText("Client");
        }else {
            holder.demobilityFrom.setText("Owner");
        }

        holder.mobilityTo.setInputType(InputType.TYPE_NULL);
        holder.demobilityFrom.setInputType(InputType.TYPE_NULL);

        holder.GST.setChecked(rentalPlan.isGst());
        //holder.GST.setTextColor(context.getResources().getColor(android.R.color.black));
       // holder.GST.setEnabled(false);
        holder.GST.setKeyListener(null);
       // holder.GST.setInputType(InputType.TYPE_NULL);
        holder.IGST.setChecked(rentalPlan.isIgstFlg());
        //holder.IGST.setTextColor(context.getResources().getColor(android.R.color.black));
       // holder.IGST.setEnabled(false);
        //holder.IGST.setInputType(InputType.TYPE_NULL);
        holder.IGST.setKeyListener(null);

        holder.planDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  callPlanDetailsDialogs(rentalPlanList.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {
        return rentalPlanList.size();
    }
    private void callPlanDetailsDialogs(RentalPlan rentalPlan) {

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

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.rental_plan_details_dialogs);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        txtPlanType=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plantype);
        txtPlanName=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planname);
        txtPlanUsage=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planusage);
        txtPlanDescription=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plandescription);


        txtPlanType.setText(rentalPlan.getPlanType());
        txtPlanName.setText(rentalPlan.getPlanName());
        txtPlanUsage.setText(rentalPlan.getPlanUsage());
        txtPlanDescription.setText(rentalPlan.getPlanDescription());


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



       // AppCompatTextView generalTitle=(AppCompatTextView)expandableCardViewGeneral.findViewById(R.id.parent_txt_general);
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

        load.setText(rentalPlan.getLoad());
        shift.setText(rentalPlan.getShift());
        basic_rate.setText(""+rentalPlan.getBasicRate());
        hours_Monthly.setText(""+rentalPlan.getMonthlyHours());
        if (rentalPlan.getShift().equals("Single Shift")){
            daily_Hours.setText("8");
        }else {
            daily_Hours.setText("16");
        }

        days_Monthly.setText(""+rentalPlan.getMinDays());
        if (rentalPlan.getPlanUsage().equals("Usage (Daily)")){
            committed_Name.setText("Days (Committed)");
        }else if (rentalPlan.getPlanUsage().equals("Usage (Monthly)")){
            committed_Name.setText("Months (Committed)");
        }else if (rentalPlan.getPlanUsage().equals("Usage (Hourly)")){
            committed_Name.setText("Hours (Committed)");
        }

        committed_Month.setText(""+rentalPlan.getCommitmentMonth());
        overtime.setText(""+rentalPlan.getOvertime());


        //AppCompatTextView attatmentTitle=(AppCompatTextView)expandableCardViewAttachment.parentLayout.findViewById(R.id.parent_txt_attachment);
       // attatmentTitle.setText("Attachment");
      //  attatmentTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        AppCompatCheckBox att_basic_rate = (AppCompatCheckBox) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_basicrate_check);
        AppCompatCheckBox extra_att = (AppCompatCheckBox) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_extra_attachment_check);
        AppCompatTextView extra_att_rate = (AppCompatTextView) expandableCardViewAttachment.findViewById(R.id.rental_plan_dialog_attachment_rate);

        att_basic_rate.setChecked(rentalPlan.isDefaultAttachment());
        extra_att.setChecked(rentalPlan.isExtraAttachmentRateFlg());
        extra_att_rate.setText(""+rentalPlan.getExtraAttachmentRate());


       // AppCompatTextView operatorTitle=(AppCompatTextView)expandableCardViewOperator.parentLayout.findViewById(R.id.parent_txt_operator);
        //operatorTitle.setText("Operator");
       // operatorTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        AppCompatCheckBox operatorInclude = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_oprator_check);
        AppCompatTextView operatorRate = (AppCompatTextView) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_fixed_amount);
        AppCompatCheckBox accomodation = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_accomodation);
        AppCompatCheckBox transportation = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_transportation);
        AppCompatCheckBox food = (AppCompatCheckBox) expandableCardViewOperator.findViewById(R.id.rental_plan_dialog_food);

        operatorInclude.setChecked(rentalPlan.isOperatorFlg());
        operatorRate.setText(""+rentalPlan.getRate());
        accomodation.setChecked(rentalPlan.isAccomodation());
        transportation.setChecked(rentalPlan.isTransportation());
        food.setChecked(rentalPlan.isFood());

      //  AppCompatTextView mobilityTitle=(AppCompatTextView)expandableCardViewMobility.findViewById(R.id.parent_txt_mobility);
      //  mobilityTitle.setText("Mobility Of Machine");
      //  mobilityTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time,0,0,0);

        LinearLayout mobilityLn = (LinearLayout) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobolity_ln);
        LinearLayout demobilityLn = (LinearLayout) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobolity_ln);

        AppCompatTextView mobilityAmount = (AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_mobility_amount);
        AppCompatTextView demobilityAmount = (AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityAmount);
        AppCompatTextView mobilityresposibility=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_responsibility);
        AppCompatTextView demobilityresposibility=(AppCompatTextView) expandableCardViewMobility.findViewById(R.id.rental_plan_dialog_demobilityResponsibility);

        if (!rentalPlan.getMobilityResponsible().equals("Owner")){
            mobilityLn.setVisibility(View.GONE);
        }
        if (!rentalPlan.getDemobilityResponsible().equals("Owner")){
            demobilityLn.setVisibility(View.GONE);
        }

        mobilityresposibility.setText(rentalPlan.getMobilityResponsible());
        mobilityAmount.setText(""+rentalPlan.getMobilityAmt());
        demobilityresposibility.setText(rentalPlan.getDemobilityResponsible());
        demobilityAmount.setText(""+rentalPlan.getDemobilityAmt());



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



        dialog.getWindow().setLayout((int) (getScreenWidth((Activity) context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


}
