package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.SpinnerRecycleViewHoler;

import java.util.ArrayList;

public class SpinnerRecycleAdapter extends RecyclerView.Adapter<SpinnerRecycleViewHoler> {

    ArrayList<String> listData;
    Context context;
    private RecyclerViewClickListener mListener;

    public SpinnerRecycleAdapter(Context context,ArrayList<String> listData,RecyclerViewClickListener listener) {
        this.context=context;
        this.listData=listData;
         mListener = listener;

    }

    @NonNull
    @Override
    public SpinnerRecycleViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.spinner_dialog_recyle_adapter,parent,false);
        return new SpinnerRecycleViewHoler(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerRecycleViewHoler holder, final int position) {
        holder.txtItem.setText(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
