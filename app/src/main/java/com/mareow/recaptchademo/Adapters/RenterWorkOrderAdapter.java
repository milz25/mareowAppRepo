package com.mareow.recaptchademo.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.OfferWorkOrderDTO;
import com.mareow.recaptchademo.MainDetailsFragment.WorkOrderCancelFragment;
import com.mareow.recaptchademo.MainDetailsFragment.WorkOrderExtendFragment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.RenterWorkOrderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RenterWorkOrderAdapter extends RecyclerView.Adapter<RenterWorkOrderViewHolder> {

    public static final String MESSAGE_PROGRESS ="Message_Progress" ;

    Context context;
    List<OfferWorkOrder> workOrderList;
    RecyclerViewClickListener listener;
    AppCompatTextView mProgress_text;
    ProgressBar mProgressBar;
    Dialog dialog;
    public RenterWorkOrderAdapter(Context context, List<OfferWorkOrder> workOrderList, RecyclerViewClickListener listener) {
        this.context=context;
        this.workOrderList=workOrderList;
        this.listener=listener;

       // registerReceiver();

    }

    @NonNull
    @Override
    public RenterWorkOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.renter_work_order_adapter,parent,false);
        return new RenterWorkOrderViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RenterWorkOrderViewHolder holder, int position) {
        OfferWorkOrderDTO offerWorkOrderDTO=workOrderList.get(position).getWorkorderDTO();

        holder.mWorkNo.setText("Work Order # "+offerWorkOrderDTO.getWorkOrderNo());
        holder.mWorkDates.setText(Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrderDTO.getWorkStartDate())+" - "+Util.convertYYYYddMMtoDDmmYYYY(offerWorkOrderDTO.getWorkEndDate()));
        holder.mWorkStatus.setText(offerWorkOrderDTO.getWorkOrderStatusMeaning());

       /* if (Constants.USER_ROLE.equals("Owner"))
            holder.mWorkExtend.setVisibility(View.GONE);
*/

        if (offerWorkOrderDTO.getWorkOrderStatusMeaning().equals("WO: Closed")){
            holder.mWorkStatus.setTextColor(context.getResources().getColor(R.color.theme_orange));
          /*  holder.mWorkAgreement.setVisibility(View.GONE);
            holder.mWorkExtend.setVisibility(View.GONE);
            holder.mWorkCancellation.setVisibility(View.GONE);*/

        }else {
            holder.mWorkStatus.setTextColor(context.getResources().getColor(R.color.Blue_Text));
        }


        /*holder.mWorkAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAgreementApi(workOrderList.get(position));
                return;
            }
        });

        holder.mWorkExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWorkOrderExtendApi(workOrderList.get(position));
            }
        });

        holder.mWorkCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWorkOrderCancelApi(workOrderList.get(position));
            }
        });*/
       // holder.setIsRecyclable(false);
    }


    private void callWorkOrderCancelApi(OfferWorkOrder workOrder) {

        Fragment workOrderCancelFragment=new WorkOrderCancelFragment(workOrder);
        FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, workOrderCancelFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }


    private void callWorkOrderExtendApi(OfferWorkOrder workOrder) {

        Fragment workOrderExtendFragment=new WorkOrderExtendFragment(workOrder);
        FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, workOrderExtendFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    @Override
    public int getItemCount() {
        return workOrderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void updateList(List<OfferWorkOrder> newList){

        workOrderList=new ArrayList<>();
        workOrderList.addAll(newList);
        notifyDataSetChanged();

    }

    /*public void callAgreementApi(OfferWorkOrder workOrder){

           ArrayList<OfferWorkOrder> workOrderArrayList=new ArrayList<>();
           workOrderArrayList.add(workOrder);

             Intent intent= new Intent(context, DownloadService.class);
             Bundle bundle = new Bundle();
             bundle.putSerializable("workorder", workOrderArrayList);
             intent.putExtras(bundle);
             context.startService(intent);
             displayAlert();


    }*/

   /* private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MESSAGE_PROGRESS)){

                Download download = intent.getParcelableExtra("download");
                mProgressBar.setProgress(download.getProgress());

                if(download.getProgress() == 100){
                    mProgress_text.setText("File Download Complete");
                    dialog.dismiss();

                } else {
                    mProgress_text.setText(String.format("Downloaded (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));
                }

            }
        }
    };*/

   /* private void displayAlert()
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_download);
        // set the custom dialog components - text, image and button
        mProgressBar=(ProgressBar)dialog.findViewById(R.id.progress);
        mProgress_text= (AppCompatTextView) dialog.findViewById(R.id.progress_text);

        dialog.getWindow().setLayout((int) (getScreenWidth((Activity) context) * .95), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
*/
    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }

   /* private void registerReceiver(){

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }
*/
}
