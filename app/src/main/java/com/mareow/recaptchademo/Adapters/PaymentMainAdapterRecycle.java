package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.ViewHolders.PaymentMainAdapterViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentMainAdapterRecycle extends RecyclerView.Adapter<PaymentMainAdapterViewHolder> {

    Context context;
    List<InvoiceByUser> specifiInvoice;

    public PaymentMainAdapterRecycle(Context context, List<InvoiceByUser> specifiInvoice) {
        this.context=context;
        this.specifiInvoice=specifiInvoice;
    }


    @NonNull
    @Override
    public PaymentMainAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.payment_main_within_adapter,parent,false);
        return new PaymentMainAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMainAdapterViewHolder holder, int position) {

        holder.txtInvoiceNo.setText(specifiInvoice.get(position).getInvoiceNo());
        holder.txtInvoiceDate.setText(convertDate(specifiInvoice.get(position).getInvoiceDate()));

        if (specifiInvoice.get(position).getStatus().equals("INV_PEN")){
            holder.txtInvoiceStatus.setText("Due"+"\n");
        }else if(specifiInvoice.get(position).getStatus().equals("PAY_PAR_REC")){
            holder.txtInvoiceStatus.setText("Paid(Partially)"+"\n");
        }else if(specifiInvoice.get(position).getStatus().equals("INV_REC")){
            holder.txtInvoiceStatus.setText("Paid"+"\n");
        }

    }

    @Override
    public int getItemCount() {
        return specifiInvoice.size();
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
