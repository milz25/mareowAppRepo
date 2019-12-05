package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.ViewHolders.PaymentCustomeDialogViewHolder;

import java.util.List;

public class PaymentCustomeDialogAdapter extends RecyclerView.Adapter<PaymentCustomeDialogViewHolder> {

    Context context;
    List<String> mHeadingData;
    List<String> mValueData;
    boolean b;
    public PaymentCustomeDialogAdapter(Context context, List<String> mHeadingData, List<String> mValueData, boolean b) {

        this.context=context;
        this.mHeadingData=mHeadingData;
        this.mValueData=mValueData;
        this.b=b;
    }

    @NonNull
    @Override
    public PaymentCustomeDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.payment_custom_dialog_adapter,parent,false);
        return new PaymentCustomeDialogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentCustomeDialogViewHolder holder, int position) {


        if (position==mHeadingData.size()-1){
            if (b==true){
                holder.txtHeading.setTextSize(8);
                holder.txtValue.setTextSize(8);

                String gstText=mHeadingData.get(position);
                SpannableString ss3=  new SpannableString(gstText);
                //ss3.setSpan(new AbsoluteSizeSpan(getActivity().getResources().getDimensionPixelSize(R.dimen.text2),true), 0,GstandIGst.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE); // set size
                //ss3.setSpan(new RelativeSizeSpan(0.5f), 0,gstText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss3.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.theme_orange)), 0, gstText.length(), 0);

                holder.txtHeading.setText(ss3);
                holder.txtValue.setText(mValueData.get(position));
            }
            holder.txtHeading.append("\n");
            holder.txtValue.append("\n");

        }else {

            holder.txtHeading.setText(mHeadingData.get(position));
            holder.txtValue.setText(mValueData.get(position));

        }

    }

    @Override
    public int getItemCount() {
        return mHeadingData.size();
    }

}
