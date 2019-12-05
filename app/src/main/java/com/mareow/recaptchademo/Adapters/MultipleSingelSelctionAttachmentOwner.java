package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.MultiSelectionForAttachmentViewHolder;
import com.mareow.recaptchademo.ViewHolders.MultipleSingelSelctionAttachmentOwnerViewHolder;

import java.util.ArrayList;

public class MultipleSingelSelctionAttachmentOwner extends RecyclerView.Adapter<MultipleSingelSelctionAttachmentOwnerViewHolder> {

    Context context;
    ArrayList<String> listData;
    RecyclerViewClickListener mListener;
    boolean[] checkedItems;

    public MultipleSingelSelctionAttachmentOwner(Context context, ArrayList<String> listData, RecyclerViewClickListener listener, boolean[] checkedItems) {
        this.context=context;
        this.listData=listData;
        this.mListener=listener;
        this.checkedItems=checkedItems;
    }

    @NonNull
    @Override
    public MultipleSingelSelctionAttachmentOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.singel_selection_dialog,parent,false);
        return new MultipleSingelSelctionAttachmentOwnerViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleSingelSelctionAttachmentOwnerViewHolder holder, int position) {

        holder.radioButtonItem.setText(listData.get(position));

        if (checkedItems[position]){
            holder.radioButtonItem.setChecked(true);
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
