package com.mareow.recaptchademo.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.DataModels.AbleToRunMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.ViewHolders.AbleToRunViewHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbleToRunAdapter extends RecyclerView.Adapter<AbleToRunViewHolder> {

    Context context;
    List<AbleToRunMachine> machineList;
    ProgressDialog progressDialog;
    public AbleToRunAdapter(Context context, List<AbleToRunMachine> ableToRunMachineList) {
        this.context=context;
        this.machineList=ableToRunMachineList;
        if (context!=null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait............");
        }

    }

    @NonNull
    @Override
    public AbleToRunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.able_to_run_adapter,parent,false);
        return new AbleToRunViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AbleToRunViewHolder holder, final int position) {

        if (machineList.size()==1){
            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.cardView.setLayoutParams(layoutParams);
        }else {

            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            int scale = (int)(context.getResources().getDisplayMetrics().widthPixels *0.85);

            //float witdths=context.getResources().getDimension(R.dimen.able_to_run_machine_width);

            layoutParams.width = (int)scale;
            holder.cardView.setLayoutParams(layoutParams);


        }
        final AbleToRunMachine ableToRunMachine=machineList.get(position);

       // holder.mContainer.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));

        if (ableToRunMachine.getModelImage()!=null){


            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.able_to_run_machine)
                    .error(R.drawable.able_to_run_machine)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();

            Glide.with(context).load("http://18.204.165.238:8080/mareow-api/"+ableToRunMachine.getModelImage())
                    .apply(options)
                    .into(holder.machineImage);

          /*  Glide.with(context).load("http://18.204.165.238:8080/mareow-api/"+ableToRunMachine.getModelImage())
                               .placeholder(R.drawable.able_to_run_machine)
                               .into(holder.machineImage);*/
        }else {
            holder.machineImage.setImageResource(R.drawable.able_to_run_machine);
        }
       holder.txtCategory.setText(ableToRunMachine.getCatagoryMeaning());
       holder.txtSubCategory.setText(ableToRunMachine.getSubCategoryMeaning());
       holder.txtManufacture.setText(ableToRunMachine.getManufacturerMeaning());
       holder.txtModelNo.setText(ableToRunMachine.getModelNo());

       if (!TextUtils.isEmpty(ableToRunMachine.getUrl()) && !ableToRunMachine.getUrl().equals("null")){

           SpannableString s1 = new SpannableString(ableToRunMachine.getUrl());
           ClickableSpan clickableSpan = new ClickableSpan() {
               @Override
               public void onClick(View textView) {
                   Uri uriUrl = Uri.parse(ableToRunMachine.getUrl());
                   Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                   context.startActivity(launchBrowser);
               }
               @Override
               public void updateDrawState(TextPaint ds) {
                   super.updateDrawState(ds);
                   ds.setUnderlineText(true);
                   ds.setColor(context.getResources().getColor(android.R.color.holo_blue_dark));
               }
           };
           s1.setSpan(clickableSpan, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
           holder.txtUrl.setText(s1);
           holder.txtUrl.setMovementMethod(LinkMovementMethod.getInstance());

       }else {
           holder.txtUrl.setText("No URL has been given.");
       }


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialogDelete(position);
           }
       });

    }

    @Override
    public int getItemCount() {
        return machineList.size();
    }


    public void updateList(List<AbleToRunMachine> newList){
      machineList=new ArrayList<>();
      machineList.addAll(newList);
       notifyDataSetChanged();
    }

    public void showDialogDelete(final int position){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
           //builder.setTitle("Delete Operator Model");
        LayoutInflater newinInflater=LayoutInflater.from(context);
        View view = newinInflater.inflate(R.layout.custom_title_alert_dialog, null);
        AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.custom_title_text);
        titleText.setText("Delete Machine Model");
        builder.setCustomTitle(view);

           builder.setMessage("Do you want to delete Model?");
           builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                         dialog.dismiss();
                         callDeleteAction(position);

                    }
                });
                // A null listener allows the button to dismiss the dialog and take no further action.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

         AlertDialog dialog=builder.create();
         dialog.show();

        TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
        messageView.setPadding(0,50,0,0);
        messageView.setGravity(Gravity.CENTER);


    }


    public void callDeleteAction(final int position){
        String token= TokenManager.getSessionToken();
        int operatorModelId=machineList.get(position).getOperatorMachineId();

        progressDialog.show();
        final ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> deleteOperatorModelCall=apiInterface.deleteOperatorMachineModel("Bearer "+token,operatorModelId);
        deleteOperatorModelCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    machineList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, machineList.size());
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Enable to Delete Operator Machine Model", Toast.LENGTH_SHORT).show();
                   }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                Toast.makeText(context, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }







}
