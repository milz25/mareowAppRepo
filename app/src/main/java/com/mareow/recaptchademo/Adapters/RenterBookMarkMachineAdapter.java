package com.mareow.recaptchademo.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.ViewHolders.RenterSubwithinSubViewHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterBookMarkMachineAdapter extends RecyclerView.Adapter<RenterSubwithinSubViewHolder> {

    Context context;
    List<RenterMachine> newCategoryMachineList;
    RecyclerViewClickListener listener;
    ProgressDialog progressDialog;
    public RenterBookMarkMachineAdapter(Context context, List<RenterMachine> newCategoryMachineList,RecyclerViewClickListener listener) {
        this.context=context;
        this.newCategoryMachineList=newCategoryMachineList;
        this.listener=listener;
        if (context!=null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait.................");
        }

    }


    @NonNull
    @Override
    public RenterSubwithinSubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.renter_sub_within_sub_adapter,parent,false);
        return new RenterSubwithinSubViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RenterSubwithinSubViewHolder holder, int position) {

        if (newCategoryMachineList.get(position).getImagePath()!=null){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.able_to_run_machine)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate();
            /*.transform(new CircleTransform(context));*/

            Glide.with(context).load(newCategoryMachineList.get(position).getImagePath())
                    .apply(options)
                    .into(holder.machineImage);
        }


        holder.machineName.setText(newCategoryMachineList.get(position).getMachineName());
        holder.mMFG.setText(newCategoryMachineList.get(position).getManufacturerName());
        holder.mModel.setText(newCategoryMachineList.get(position).getModelNo());
        holder.mfgYear.setText(newCategoryMachineList.get(position).getManufacturerYear());
        holder.locateAt.setText(newCategoryMachineList.get(position).getCurrentLocation());
        holder.mAvailability.setText(newCategoryMachineList.get(position).getAvailibility());

        holder.mFavoarite.setImageResource(R.drawable.ic_bookmark);
        holder.mFavoarite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBookMarkForMachineAPI(newCategoryMachineList.get(position),position);
            }
        });

        holder.mRating.setText("("+newCategoryMachineList.get(position).getOverallRating()+")");
        if (newCategoryMachineList.get(position).isIsverified()){
            holder.mVerified.setImageResource(R.drawable.ic_verify_true);
        }else {
            holder.mVerified.setImageResource(R.drawable.ic_verify_false);
        }


        // holder.machineDetails.setText(newCategoryMachineList.get(position).getManufacturerName()+"/"+newCategoryMachineList.get(position).getModelNo()+
        //     "\n"+"Manufacturing Year :"+newCategoryMachineList.get(position).getManufacturerYear()+
        //      "\n"+"Current Location :"+newCategoryMachineList.get(position).getCurrentLocation()+
        // "\n"+"("+newCategoryMachineList.get(position).getAvailibility()+")");

         holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return newCategoryMachineList.size()/*>4?4:newCategoryMachineList.size()*/;
    }


    private void callBookMarkForMachineAPI(RenterMachine renterMachine,int position) {
        if (progressDialog!=null)
            progressDialog.show();
        String token= TokenManager.getSessionToken();
        int partyId= TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        int machineId=renterMachine.getMachineId();
        progressDialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<StatuTitleMessageResponse> bookmarkCall=apiInterface.bookMarkFavoriteMachine("Bearer "+token,machineId,partyId);
        bookmarkCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (progressDialog!=null)
                    progressDialog.dismiss();
                if (response.isSuccessful()){
                    showSnackbar(response.body().getMessage());
                    removeItem(position);
                    //Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    if (response.code()==401){
                        TokenExpiredUtils.tokenExpired((Activity) context);
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
                        TokenExpiredUtils.tokenExpired((Activity) context);
                    }
                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                showSnackbar(t.getMessage());
                if (progressDialog!=null)
                    progressDialog.dismiss();
            }
        });
    }

    private void removeItem(int position){
        newCategoryMachineList.remove(position);
        notifyItemRemoved(position);
        //notifyItemRangeChanged(position, newCategoryMachineList.size());
        notifyDataSetChanged();
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(((Activity)context).getCurrentFocus(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(((Activity)context).getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(((Activity)context).getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

}
