package com.mareow.recaptchademo.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.alespero.expandablecardview.ExpandableCardView;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.OwnerRentalPlanAddFrgaments.UpdateRentalPlanFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.ViewHolders.RenterPlanForOwnerViewHolder;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterPlanForOwnerAdapter extends RecyclerView.Adapter<RenterPlanForOwnerViewHolder> {

    Context context;
    List<RentalPlan> rentalPlansList;
    ProgressDialog progressDialog;
    public RenterPlanForOwnerAdapter(Context context, List<RentalPlan> rentalPlansList) {
        this.context=context;
        this.rentalPlansList=rentalPlansList;

        if (this.context!=null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait................");
        }
    }

    @NonNull
    @Override
    public RenterPlanForOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.owner_rental_plan_adapter,parent,false);
        return new RenterPlanForOwnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RenterPlanForOwnerViewHolder holder, int position) {


        if (rentalPlansList.size()==1){
            ViewGroup.LayoutParams layoutParams = holder.mainCard.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.mainCard.setLayoutParams(layoutParams);
        }else {
            ViewGroup.LayoutParams layoutParams = holder.mainCard.getLayoutParams();
            int scale = (int)(context.getResources().getDisplayMetrics().widthPixels *0.85);
            layoutParams.width = (int)scale;
            holder.mainCard.setLayoutParams(layoutParams);
        }

        holder.headerCard.setBackgroundResource(R.drawable.card_two_corner);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");


        holder.planName.setText(rentalPlansList.get(position).getPlanName());
        holder.editPlan.setText(rentalPlansList.get(position).getPlanNameShort());
        holder.editPlan.setInputType(InputType.TYPE_NULL);
        holder.editPlanType.setText(rentalPlansList.get(position).getPlanType());
        holder.editPlanType.setInputType(InputType.TYPE_NULL);
        holder.editPlanUsage.setText(rentalPlansList.get(position).getPlanUsage());
        holder.editPlanUsage.setInputType(InputType.TYPE_NULL);
        holder.editBasicRate.setText("\u20B9 "+IndianCurrencyFormat.format(rentalPlansList.get(position).getBasicRate()));
        holder.editBasicRate.setInputType(InputType.TYPE_NULL);
        holder.editDailyHours.setText(String.valueOf(rentalPlansList.get(position).getDailyHours()));
        holder.editDailyHours.setInputType(InputType.TYPE_NULL);

        if (rentalPlansList.get(position).getPlanUsageCode().equals("DAILY")){
            holder.committedHint.setHint("Days (Committed)");
            holder.editMonthsCommited.setText(""+rentalPlansList.get(position).getDailyMinHours());
        }else if (rentalPlansList.get(position).getPlanUsageCode().equals("MONTHLY")){
            holder.committedHint.setHint("Months (Committed)");
            holder.editMonthsCommited.setText(""+rentalPlansList.get(position).getCommitmentMonth());
        }else if (rentalPlansList.get(position).getPlanUsageCode().equals("HOURLY")){
            holder.committedHint.setHint("Hours (Committed)");
            holder.editMonthsCommited.setText(""+rentalPlansList.get(position).getDailyMinHours());
        }

        holder.editMonthsCommited.setInputType(InputType.TYPE_NULL);
        //holder.editMonthsCommited.setText(String.valueOf(rentalPlansList.get(position).getCommitmentMonth()));


        holder.mSwitchTax.setChecked(rentalPlansList.get(position).isGst());
        holder.mSwitchTax.setKeyListener(null);

        holder.mSwitchOperator.setChecked(rentalPlansList.get(position).isOperatorFlg());
        holder.mSwitchOperator.setKeyListener(null);

        holder.mSwitchAttachment.setChecked(rentalPlansList.get(position).isExtraAttachment());
        holder.mSwitchAttachment.setKeyListener(null);

        //holder.mSwitchMachineAssocited.setChecked();
        holder.mSwitchMachineAssocited.setKeyListener(null);


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeletePlanDialog(rentalPlansList.get(position).getPlanId(),position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callEditPlanFragments(rentalPlansList.get(position));
            }
        });

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "More", Toast.LENGTH_SHORT).show();
                showPlanDetailsDialog(rentalPlansList.get(position));
            }
        });

    }

    private void callEditPlanFragments(RentalPlan rentalPlan) {



       /* Fragment fragment=new UpdateRentalPlanFragment(rentalPlan);


        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();*/


        AppCompatTextView txtPlanType;
        AppCompatTextView txtPlanName;
        AppCompatTextView txtPlanUsage;
        AppCompatTextView txtPlanDescription;

        RecyclerView mAssociateMachineRecycle;
        AppCompatButton btnEdit;


        AppCompatImageButton btnClose;


        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.rental_plan_update_custome);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        txtPlanType=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plantype);
        txtPlanName=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planname);
        txtPlanUsage=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_planusage);
        txtPlanDescription=(AppCompatTextView)dialog.findViewById(R.id.rental_plan_dailog_plandescription);




        dialog.getWindow().setLayout((int) (getScreenWidth((Activity)context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    @Override
    public int getItemCount() {
        return rentalPlansList.size();
    }


    private void callDeletePlanDialog(int planId,int position) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
        LayoutInflater newinInflater=((AppCompatActivity)context).getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.delete_final);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Are you sure, you want to delete this rental plan?");

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Yes");
        AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
        buttonNegative.setText("No");
        AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callPlanDeleteApi(planId,position);
            }
        });

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void callPlanDeleteApi(int planId,int position) {

        String token= TokenManager.getSessionToken();
        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> deleteCall=apiInterface.deleteOwnerPlan("Bearer "+token,planId);
        deleteCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                    removeItem(position);
                }
                else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired((AppCompatActivity)context);
                    }
                    if (response.code()==404){
                        showSnackbar("Record not Found.");
                    }
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired((AppCompatActivity)context);
                    }

                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                showSnackbar(t.getMessage());
            }
        });
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(((AppCompatActivity)context).getWindow().getDecorView().getRootView(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(context.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void removeItem(int position){
        rentalPlansList.remove(position);
        notifyItemRemoved(position);
        //notifyItemRangeChanged(position, newCategoryMachineList.size());
        notifyDataSetChanged();
    }


    private void showPlanDetailsDialog(RentalPlan rentalPlan) {





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



        dialog.getWindow().setLayout((int) (getScreenWidth((Activity)context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    public  int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

}
