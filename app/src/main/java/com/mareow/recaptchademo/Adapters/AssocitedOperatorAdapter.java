package com.mareow.recaptchademo.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.DataModels.AssociatedOperator;
import com.mareow.recaptchademo.DataModels.OperatorMachine;
import com.mareow.recaptchademo.DataModels.OperatorWorkOrder;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.CircleTransform;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.ViewHolders.AssocitedOperatorViewHolder;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssocitedOperatorAdapter extends RecyclerView.Adapter<AssocitedOperatorViewHolder> {
    Context context;
    List<AssociatedOperator> associatedOperatorList;
    public List<OperatorWorkOrder> workOrderResponseList=new ArrayList<>();
    public List<OperatorMachine>  workOrderMachineList=new ArrayList<>();

    ProgressDialog progressDialog;
    public AssocitedOperatorAdapter(Context context, List<AssociatedOperator> associatedOperatorList) {
        this.context=context;
        this.associatedOperatorList=associatedOperatorList;
        if (context!=null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait............");
        }
    }

    @NonNull
    @Override
    public AssocitedOperatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.associted_operator_adapter,parent,false);
        return new AssocitedOperatorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssocitedOperatorViewHolder holder, int position) {

        AssociatedOperator operator=associatedOperatorList.get(position);

        if (associatedOperatorList.size()==1){
            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.cardView.setLayoutParams(layoutParams);
        }else {

            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            int scale = (int)(context.getResources().getDisplayMetrics().widthPixels *0.85);
            layoutParams.width = (int)scale;
            holder.cardView.setLayoutParams(layoutParams);

        }


        if (operator.getOptUserCategory().equals("User Category: Blue")){
            holder.mOperatorIconmask.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.category_bule));
        }else if (operator.getOptUserCategory().equals("User Category: Platinum")){
            holder.mOperatorIconmask.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.category_platinum));
        }else if (operator.getOptUserCategory().equals("User Category: Gold")){
            holder.mOperatorIconmask.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.category_gold));
        }else if (operator.getOptUserCategory().equals("User Category: Silver")){
            holder.mOperatorIconmask.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.category_silver));
        }else if (operator.getOptUserCategory().equals("User Category: Diamond")){
            holder.mOperatorIconmask.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.category_diamond));
        }

         /*if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("BLUE")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_bule));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLATINUM") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("PLAT")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_platinum));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("GOLD")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_gold));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SILVER") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("SLIVE")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_silver));
        }else if (TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAMOND") || TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_PARTY_CATEGORY,null).equals("DIAM")){
            mIconMask.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.category_diamond));
        }*/


        if (operator.getOperatorImage()!=null){

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .transform(new CircleTransform(context));

            Glide.with(context).load(operator.getOperatorImage())
                    .apply(options)
                    .into(holder.mOperatorIcon);


        }
        if (operator.isIsverifiedOpt()){
            holder.mOperatorVerfied.setImageResource(R.drawable.ic_verify_true);
        }else {
            holder.mOperatorVerfied.setImageResource(R.drawable.ic_verify_false);
        }

        holder.mOperatorName.setText(operator.getOperatorName());
        holder.mOperatorAssociationText.setText(operator.getAssociatedSinceStr());

       /* String name=operator.getOperatorName();
        SpannableString ss1=  new SpannableString(name);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
        ss1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), 0, name.length(), 0);// set color

        String associatedSince=operator.getAssociatedSinceStr();
        SpannableString ss2=  new SpannableString(associatedSince);
        ss2.setSpan(new RelativeSizeSpan(0.7f), 0,associatedSince.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.theme_orange)), 0, associatedSince.length(), 0);

        SpannableStringBuilder builder=new SpannableStringBuilder();
        builder.append(ss1+"\n");
        builder.append(ss2);

        holder.mOperatorName.setText(builder);
*/
        holder.editAboutMySelf.setText(operator.getAboutOperator());
        holder.editAboutMySelf.setInputType(InputType.TYPE_NULL);
        holder.editAbilitytoRun.setText(operator.getAbilitytoRunMachine());
        holder.editAbilitytoRun.setInputType(InputType.TYPE_NULL);
        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");
        holder.editBasicRate.setText("\u20B9 "+IndianCurrencyFormat.format(operator.getRate()));
        holder.editBasicRate.setInputType(InputType.TYPE_NULL);

        if (operator.getAccomodation().equals("No")){
            holder.mSwitchAccomodation.setChecked(false);
        }else {
            holder.mSwitchAccomodation.setChecked(true);
        }

        holder.mSwitchAccomodation.setKeyListener(null);
        if (operator.getTransportation().equals("No")){
            holder.mSwitchTransportation.setChecked(false);
        }else {
            holder.mSwitchTransportation.setChecked(true);
        }

        holder.mSwitchTransportation.setKeyListener(null);
        if (operator.getAccomodation().equals("No")){
            holder.mSwitchFood.setChecked(false);
        }else {
            holder.mSwitchFood.setChecked(true);
        }

        holder.mSwitchFood.setKeyListener(null);


        holder.mOperatorWorkOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWorkOrderApiForOperator(associatedOperatorList.get(position));
            }
        });


        holder.mOperatorDeassociation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeAssoicationDialog(associatedOperatorList.get(position));
            }
        });

    }

    private void showDeAssoicationDialog(AssociatedOperator associatedOperator) {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
        LayoutInflater newinInflater=((AppCompatActivity)context).getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.association_dialog);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Please confirm, do you want to stop associating with Operator "+associatedOperator.getOperatorName()+"?");

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Yes");
        AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
        buttonNegative.setText("No");
        AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callDeassoicationApi(associatedOperator);
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


    @Override
    public int getItemCount() {
        return associatedOperatorList.size();
    }

    private void callWorkOrderApiForOperator(AssociatedOperator associatedOperator) {
        if (progressDialog!=null){
            progressDialog.show();
        }
        String token=TokenManager.getSessionToken();
        int operatorId=associatedOperator.getOperatorId();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<OperatorWorkOrder>> operatorWorkOrder=apiInterface.getSpecificOperatorWorkOrder("Bearer "+token,operatorId);
        operatorWorkOrder.enqueue(new Callback<List<OperatorWorkOrder>>() {
            @Override
            public void onResponse(Call<List<OperatorWorkOrder>> call, Response<List<OperatorWorkOrder>> response) {
                if (progressDialog!=null)
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    workOrderResponseList=response.body();
                    showWorkOrderDialogs(workOrderResponseList);
                }else {
                    if(response.code()==401){
                        TokenExpiredUtils.tokenExpired((Activity) context);
                    }
                    if (response.code()==404){
                        Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                    }
                    if (response.code()==403){
                        TokenExpiredUtils.tokenExpired((Activity) context);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OperatorWorkOrder>> call, Throwable t) {
                Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
                if (progressDialog!=null)
                progressDialog.dismiss();
            }
        });
    }

    private void showWorkOrderDialogs(List<OperatorWorkOrder> workOrderResponseList) {

        //AppCompatTextView mTitle;
        RecyclerView mWorkOrderRecyclerView;

        AppCompatImageButton btnClose;

        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.operator_workorder_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

      /*  mTitle = (AppCompatTextView) dialog.findViewById(R.id.payment_dailog_title);
        mTitle.setText(data.getWorkorderDTO().getWorkOrderNo());*/

        mWorkOrderRecyclerView = (RecyclerView) dialog.findViewById(R.id.operator_workorder_dailog_recycle);
        mWorkOrderRecyclerView.setHasFixedSize(false);
        mWorkOrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mWorkOrderRecyclerView.setLayoutManager(new LinearLayoutManager(context));



        OperatorWorkOrderAdapter customeDialogAdapter = new OperatorWorkOrderAdapter(context, workOrderResponseList);
        mWorkOrderRecyclerView.setAdapter(customeDialogAdapter);

        btnClose = (AppCompatImageButton) dialog.findViewById(R.id.operator_workorder_dailog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout((int) (getScreenWidth((Activity) context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

    }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

    private void callDeassoicationApi(AssociatedOperator associatedOperator) {

        String token= TokenManager.getSessionToken();
        if (progressDialog!=null)
            progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> deleteCall=apiInterface.ownerDeassociationwithOperator("Bearer "+token,associatedOperator.getPartyOperatorId());
        deleteCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                    //removeItem(position);
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



}
