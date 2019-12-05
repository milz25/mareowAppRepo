package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.PaymentMainViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentMainAdapter extends RecyclerView.Adapter<PaymentMainViewHolder> {

    Context context;
    List<OfferWorkOrder> workOrderList;
    List<InvoiceByUser> invoiceByUserList;
    RecyclerViewClickListener listener;

    public PaymentMainAdapter(Context context, List<OfferWorkOrder> workOrderList, List<InvoiceByUser> invoiceByUserList, RecyclerViewClickListener listener) {
        this.context=context;
        this.workOrderList=workOrderList;
        this.invoiceByUserList=invoiceByUserList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public PaymentMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.payment_main_adapter,parent,false);
        return new PaymentMainViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMainViewHolder holder, int position) {

        OfferWorkOrder workOrder=workOrderList.get(position);

        holder.mWorkNo.setText("Work Order # "+workOrder.getWorkorderDTO().getWorkOrderNo());

        List<InvoiceByUser> specifiInvoice=new ArrayList<>();
        specifiInvoice.clear();
        for (InvoiceByUser invoice:invoiceByUserList){
            if (workOrder.getWorkorderDTO().getWorkOrderNo().equals(invoice.getWorkOrderNo())){
               specifiInvoice.add(invoice);
            }
        }

       /* holder.mRecycleView.setHasFixedSize(false);
        holder.mRecycleView.setItemAnimator(new DefaultItemAnimator());
        holder.mRecycleView.addItemDecoration(new SpacesItemDecoration(0));
        holder.mRecycleView.setLayoutManager(new LinearLayoutManager(context));

        if (specifiInvoice.size()>0){
            PaymentMainAdapterRecycle adapterRecycle=new PaymentMainAdapterRecycle(context,specifiInvoice);
            holder.mRecycleView.setAdapter(adapterRecycle);
        }*/

        if (specifiInvoice.size()>0){
            for (int i=0;i<specifiInvoice.size();i++){
                holder.mInvoiceNo.append(specifiInvoice.get(i).getInvoiceNo()+"\n");
                holder.mDate.append(convertDate(specifiInvoice.get(i).getInvoiceDate())+"\n");

                if (specifiInvoice.get(i).getStatus()!=null){

                    if (specifiInvoice.get(i).getStatus().equals("INV_PEN")){
                        holder.mStatus.append("Due"+"\n");
                    }else if(specifiInvoice.get(i).getStatus().equals("INV_PAR_REC") ||specifiInvoice.get(i).getStatus().equals("PAY_PAR_REC")){
                        holder.mStatus.append("Paid(Partially)"+"\n");
                    }else if(specifiInvoice.get(i).getStatus().equals("INV_REC")){
                        holder.mStatus.append("Paid"+"\n");
                    }
                }

            }
        }

       holder.setIsRecyclable(false);

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




    public void updateList(List<OfferWorkOrder> newList, List<InvoiceByUser> resultList){
        workOrderList=new ArrayList<>();
        invoiceByUserList=new ArrayList<>();
        workOrderList.addAll(newList);
        invoiceByUserList.addAll(resultList);
        notifyDataSetChanged();
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


}
