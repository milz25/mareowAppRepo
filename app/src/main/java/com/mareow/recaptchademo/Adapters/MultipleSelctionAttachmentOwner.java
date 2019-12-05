package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.MachineAttachment;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.MultiSelectionForAttachmentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MultipleSelctionAttachmentOwner extends RecyclerView.Adapter<MultiSelectionForAttachmentViewHolder> {

    Context context;
    ArrayList<String> listData;
    RecyclerViewClickListener mListener;
    boolean[] checkedItems;

    public MultipleSelctionAttachmentOwner(Context context, ArrayList<String> listData, RecyclerViewClickListener listener, boolean[] checkedItems) {
        this.context=context;
        this.listData=listData;
        this.mListener=listener;
        this.checkedItems=checkedItems;
    }

    @NonNull
    @Override
    public MultiSelectionForAttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.multi_selection_adapter,parent,false);
        return new MultiSelectionForAttachmentViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiSelectionForAttachmentViewHolder holder, int position) {

        holder.checkBoxItem.setText(listData.get(position));

        if (checkedItems[position]){
            holder.checkBoxItem.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
