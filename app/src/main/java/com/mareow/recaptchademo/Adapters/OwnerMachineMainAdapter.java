package com.mareow.recaptchademo.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.OwnerAddMachine.UpdateMachineFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.TokenExpiredUtils;
import com.mareow.recaptchademo.ViewHolders.OwnerMachineMainViewHolder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerMachineMainAdapter extends RecyclerView.Adapter<OwnerMachineMainViewHolder> {

    Context context;
    List<RenterMachine> machineListForOwner;
    RecyclerViewClickListener listener;
    ProgressDialog progressDialog;
    public OwnerMachineMainAdapter(Context context, List<RenterMachine> machineListForOwner, RecyclerViewClickListener listener) {
        this.context=context;
        this.machineListForOwner=machineListForOwner;
        this.listener=listener;
        if (this.context!=null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait................");
        }
    }

    @NonNull
    @Override
    public OwnerMachineMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.owner_machine_main_adapter,parent,false);
        return new OwnerMachineMainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerMachineMainViewHolder holder, int position) {

        if (machineListForOwner.size()==1){
            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.cardView.setLayoutParams(layoutParams);
        }else {
            ViewGroup.LayoutParams layoutParams = holder.cardView.getLayoutParams();
            int scale = (int)(context.getResources().getDisplayMetrics().widthPixels *0.85);
            layoutParams.width = (int)scale;
            holder.cardView.setLayoutParams(layoutParams);
        }

        holder.cardViewTwo.setBackgroundResource(R.drawable.card_two_corner);

        holder.txtMachineTitle.setText(machineListForOwner.get(position).getMachineName());
        holder.txtMachineCateSubCate.setText(machineListForOwner.get(position).getCategoryName());
        holder.txtMachineSubcategory.setText(machineListForOwner.get(position).getSubCategoryName());
        holder.txtModel.setText(machineListForOwner.get(position).getModelNo());
        holder.txtManu_Year.setText(machineListForOwner.get(position).getManufacturerYear());
        holder.txtCurrentLocation.setText(machineListForOwner.get(position).getCurrentLocation());
        holder.txtDescription.setText(machineListForOwner.get(position).getMachineDesc());
        holder.txtManufacturer.setText(machineListForOwner.get(position).getManufacturerName());
        holder.txtStatus.setText("("+machineListForOwner.get(position).getAvailabelstatus()+")");

        holder.editMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpdateFragments(machineListForOwner.get(position));
            }
        });

        holder.deleteMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callDeleteMachine(machineListForOwner.get(position).getMachineId(),position);
            }
        });



    }



    private void callUpdateFragments(RenterMachine renterMachine) {

        Constants.MACHINE_UPDATE=true;
        Fragment fragment=new UpdateMachineFragment(renterMachine);


        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();


    }

    @Override
    public int getItemCount() {
        return machineListForOwner.size();
    }


    private void callDeleteMachine(int machineId,int position) {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
        LayoutInflater newinInflater=((AppCompatActivity)context).getLayoutInflater();
        View view = newinInflater.inflate(R.layout.custome_alert_logout, null);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.custom_alertdilaog_image);
        imageView.setImageResource(R.drawable.delete_final);
        AppCompatTextView txtMessage=(AppCompatTextView)view.findViewById(R.id.custom_alertdialog_message);
        txtMessage.setText("Are you sure, you want to delete machine?");

        AppCompatButton buttonPositive=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_positive);
        buttonPositive.setText("Yes");
        AppCompatButton buttonNegative=(AppCompatButton)view.findViewById(R.id.custom_alertdilaog_negative);
        buttonNegative.setText("No");
        AlertDialog dialog=alertDialog.create();

        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callMachineDeleteApi(machineId,position);
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

    private void callMachineDeleteApi(int machineId,int position) {
            String token= TokenManager.getSessionToken();
            if (progressDialog!=null)
            progressDialog.show();
            ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
            Call<StatuTitleMessageResponse> deleteCall=apiInterface.deleteOwnerMachine("Bearer "+token,machineId);
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
        machineListForOwner.remove(position);
        notifyItemRemoved(position);
        //notifyItemRangeChanged(position, newCategoryMachineList.size());
        notifyDataSetChanged();
    }

}
