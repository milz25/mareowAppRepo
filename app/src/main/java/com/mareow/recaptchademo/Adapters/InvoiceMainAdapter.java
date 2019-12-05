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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.WorkOrderResponse;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.InvoiceMainViewHolder;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceMainAdapter extends RecyclerView.Adapter<InvoiceMainViewHolder> {
    Context context;
    List<OfferWorkOrder> workOrderList;
    List<InvoiceByUser> invoiceByUserList;
    RecyclerViewClickListener listener;
    public InvoiceMainAdapter(Context context, List<OfferWorkOrder> workOrderList, List<InvoiceByUser> invoiceByUserList, RecyclerViewClickListener listener) {
        this.context=context;
        this.workOrderList=workOrderList;
        this.invoiceByUserList=invoiceByUserList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public InvoiceMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.invoice_main_adapter,parent,false);
        return new InvoiceMainViewHolder(itemView,listener);

    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceMainViewHolder holder, int position) {
        OfferWorkOrder workOrder=workOrderList.get(position);

        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###.00");


        holder.editWorkNo.setText(workOrder.getWorkorderDTO().getWorkOrderNo());
        holder.editWorkNo.setInputType(InputType.TYPE_NULL);
        holder.editWorkEstimatedAmount.setText("\u20B9 "+IndianCurrencyFormat.format(workOrder.getWorkOrderAmt()));
        holder.editWorkEstimatedAmount.setInputType(InputType.TYPE_NULL);
        holder.editWorkStartDate.setText(convertDate(workOrder.getWorkorderDTO().getWorkStartDate()));
        holder.editWorkStartDate.setInputType(InputType.TYPE_NULL);
        holder.editWorkEndDate.setText(convertDate(workOrder.getWorkorderDTO().getWorkEndDate()));
        holder.editWorkEndDate.setInputType(InputType.TYPE_NULL);

        if (workOrder.getWorkorderDTO().getWorkOrderStatusMeaning().equals("WO: Closed")){
            holder.btnStatus.setImageResource(R.drawable.wo_closed);
        }else {
            holder.btnStatus.setImageResource(R.drawable.wo_open);
        }
        List<InvoiceByUser> newInvoiceByUserList=new ArrayList<>();
        newInvoiceByUserList.clear();
        for (int i=0;i<invoiceByUserList.size();i++){
           if (invoiceByUserList.get(i).getWorkOrderNo().equals(workOrder.getWorkorderDTO().getWorkOrderNo())){
               newInvoiceByUserList.add(invoiceByUserList.get(i));
           }
        }


        holder.mInvoiceRecyclerView.setHasFixedSize(false);
        holder.mInvoiceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.mInvoiceRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        InvoiceMainInnerAdapter adapter= new InvoiceMainInnerAdapter(context,newInvoiceByUserList);
        holder.mInvoiceRecyclerView.setAdapter(adapter);

        holder.btnWorkDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCustomDialog("Work Order Details",workOrderList.get(position));
            }
        });



    }

    @Override
    public int getItemCount() {
        return workOrderList.size();
    }

    public String convertDate(String date){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

        try {
            Date date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    public void callCustomDialog(String title, OfferWorkOrder data) {
        AppCompatTextView mTitle;
        RecyclerView mPaymentRecyclerView;

        List<String> mHeadingData=new ArrayList<>();
        List<String> mValueData=new ArrayList<>();

        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.payment_custom_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.payment_dailog_title);
        mTitle.setText(data.getWorkorderDTO().getWorkOrderNo());

        mPaymentRecyclerView=(RecyclerView) dialog.findViewById(R.id.payment_dailog_recycle);
        mPaymentRecyclerView.setHasFixedSize(false);
        mPaymentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        mHeadingData.add("Renter");
        mValueData.add(data.getWorkorderDTO().getWorkOrderRenter());
        mHeadingData.add("WO Date");
        mValueData.add(convertDate(data.getWorkorderDTO().getWorkStartDate())+" to "+convertDate(data.getWorkorderDTO().getWorkEndDate()));
        mHeadingData.add("Duration");
        mValueData.add(String.valueOf(data.getWorkorderDTO().getNoOfDays())+" Days"+" & "+String.valueOf(data.getWorkorderDTO().getReqHour()+" Hours"));
        mHeadingData.add("Machine");
        mValueData.add(data.getWorkorderDTO().getMachine().getMachineName());

        mHeadingData.add("Rental Plan");
        mValueData.add(data.getWorkorderDTO().getPlanName()+"("+data.getWorkorderDTO().getPlanUsage()+")");
        mHeadingData.add("Site Location");
        mValueData.add(data.getWorkLocationSite());

        mHeadingData.add("Operator");
        mValueData.add(data.getWorkorderDTO().getOperatorName());

        mHeadingData.add("Supervisor");
        mValueData.add(data.getWorkorderDTO().getSupervisorName());

        mHeadingData.add("Est.Amount");
        mValueData.add("\u20B9 "+data.getWorkOrderAmt());


        if (data.getWorkorderDTO().isGst()){
            mHeadingData.add("( * GST / IGST Amount Included )");
        }else {
            mHeadingData.add("( * GST / IGST Amount Excluded )");
        }

        mValueData.add("");

        PaymentCustomeDialogAdapter customeDialogAdapter=new PaymentCustomeDialogAdapter(context,mHeadingData,mValueData,true);
        mPaymentRecyclerView.setAdapter(customeDialogAdapter);

        btnClose=(AppCompatImageButton) dialog.findViewById(R.id.payment_dailog_close);
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

    public void updateList( List<OfferWorkOrder> newList){

        workOrderList=new ArrayList<>();
        workOrderList.addAll(newList);
        notifyDataSetChanged();

    }


}
