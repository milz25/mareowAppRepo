package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.AbleToRunMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.MultipleSelectionViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MultipleSelectionAdapter extends RecyclerView.Adapter<MultipleSelectionViewHolder> {

    Context context;
    ArrayList<String> listData;
    RecyclerViewClickListener mListener;
    boolean[] checkedItems;
    List<AbleToRunMachine> ableToRunMachineList;
    public MultipleSelectionAdapter(Context context, ArrayList<String> listData, RecyclerViewClickListener listener, boolean[] checkedItems, List<AbleToRunMachine> ableToRunMachineList) {
        this.context=context;
        this.listData=listData;
        this.mListener=listener;
        //this.checkedItems=new boolean[checkedItems.length];
        this.checkedItems=checkedItems;
        this.ableToRunMachineList=ableToRunMachineList;
    }

    @NonNull
    @Override
    public MultipleSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.multi_selection_adapter,parent,false);
        return new MultipleSelectionViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleSelectionViewHolder holder, final int position) {
        holder.checkBoxItem.setText(listData.get(position));
        if (checkedItems[position]){
            holder.checkBoxItem.setChecked(true);
            if (ableToRunMachineList.size()>0){
                for (int i=0;i<ableToRunMachineList.size();i++){
                    if (listData.get(position).equals(ableToRunMachineList.get(i).getSegmentMeaning())){
                        holder.checkBoxItem.setEnabled(false);
                        holder.checkBoxItem.setTextColor(context.getResources().getColor(android.R.color.black));
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
