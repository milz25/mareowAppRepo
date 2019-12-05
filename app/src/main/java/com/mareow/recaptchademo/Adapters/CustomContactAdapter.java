package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.ContactModel;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.ViewHolders.CustomContactViewHolder;

import java.util.List;

public class CustomContactAdapter extends RecyclerView.Adapter<CustomContactViewHolder> {

    Context context;
    List<ContactModel> contactModelList;
    public CustomContactAdapter(Context context, List<ContactModel> contactModelList) {
        this.context=context;
        this.contactModelList=contactModelList;
    }

    @NonNull
    @Override
    public CustomContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.custom_contact_adapter,parent,false);
        return new CustomContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomContactViewHolder holder, int position) {
        ContactModel contactModel=contactModelList.get(position);

        holder.txtName.setText(contactModel.getName());
        holder.txtPhoneNumber.setText(contactModel.getMobileNumber());
        holder.txtEmail.setText(contactModel.getEmail());
    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }
}
