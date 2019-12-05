package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.WorkOrderResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.Util;
import com.mareow.recaptchademo.ViewHolders.SuperWorkOrderMainViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SuperWorkOrderMainAdapter extends RecyclerView.Adapter<SuperWorkOrderMainViewHolder> {
    Context context;
    List<WorkOrderResponse> woList;
    RecyclerViewClickListener mListener;
    public SuperWorkOrderMainAdapter(Context context, List<WorkOrderResponse> workOrderList, RecyclerViewClickListener listener) {
        this.context=context;
        this.woList=workOrderList;
        this.mListener=listener;

       // if (Constants.USER_ROLE.equals("Supervisor"))
       // Collections.reverse(workOrderList);
        /*Collections.sort(workOrderList, new Comparator<WorkOrderResponse>() {
            @Override
            public int compare(WorkOrderResponse o1, WorkOrderResponse o2) {
                return 0;
            }
        });*/

    }

    @NonNull
    @Override
    public SuperWorkOrderMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.super_work_order_main_adapter,parent,false);
        return new SuperWorkOrderMainViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperWorkOrderMainViewHolder holder, int position) {


        //holder.mContainer.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        holder.txtWorkOrderId.setText("Work Order "+"# "+woList.get(position).getWorkOrderNo());
        holder.txtWorkOrderDate.setText(Util.convertYYYYddMMtoDDmmYYYY(woList.get(position).getWorkStartDate())+" - "+Util.convertYYYYddMMtoDDmmYYYY(woList.get(position).getWorkEndDate()));
        holder.txtWorkOrderStatus.setText(woList.get(position).getWorkOrderStatus());
        if (woList.get(position).getWorkOrderStatus().equals("WO: Closed")){
            holder.txtWorkOrderStatus.setTextColor(context.getResources().getColor(R.color.theme_orange));
        }else {
            holder.txtWorkOrderStatus.setTextColor(context.getResources().getColor(R.color.Blue_Text));
        }

    }


    @Override
    public int getItemCount() {
        return woList.size();
    }

    public void updateList(List<WorkOrderResponse> newList){

            woList=new ArrayList<>();
            woList.addAll(newList);
            notifyDataSetChanged();

    }

}
