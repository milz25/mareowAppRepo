package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.RenterSubwithinSubViewHolder;

import java.util.List;

public class RenterSimilarAdapter extends RecyclerView.Adapter<RenterSubwithinSubViewHolder> {


    Context context;
    List<RenterMachine> newCategoryMachineList;
    RecyclerViewClickListener listener;
    public RenterSimilarAdapter(Context context, List<RenterMachine> newCategoryMachineList,RecyclerViewClickListener listener) {
        this.context=context;
        this.newCategoryMachineList=newCategoryMachineList;
        this.listener=listener;
    }



    @NonNull
    @Override
    public RenterSubwithinSubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.similar_recycle_adapter,parent,false);
        return new RenterSubwithinSubViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RenterSubwithinSubViewHolder holder, int position) {

        if (newCategoryMachineList.get(position).getImagePath()!=null){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.progress_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(context).load(newCategoryMachineList.get(position).getImagePath())
                    .apply(options)
                    .into(holder.machineImage);
        }


        holder.machineName.setText(newCategoryMachineList.get(position).getMachineName());
        holder.mMFG.setText(newCategoryMachineList.get(position).getManufacturerName());
        holder.mModel.setText(newCategoryMachineList.get(position).getModelNo());
        holder.mfgYear.setText(newCategoryMachineList.get(position).getManufacturerYear());
        holder.locateAt.setText(newCategoryMachineList.get(position).getCurrentLocation());
        holder.mAvailability.setText(newCategoryMachineList.get(position).getAvailibility());

        if (newCategoryMachineList.get(position).isFavouriteFlg()){
            holder.mFavoarite.setImageResource(R.drawable.ic_bookmark);
        }else {
            holder.mFavoarite.setImageResource(R.drawable.ic_bookmark_border);
        }

        holder.mRating.setText("("+newCategoryMachineList.get(position).getOverallRating()+")");
        if (newCategoryMachineList.get(position).isIsverified()){
            holder.mVerified.setImageResource(R.drawable.ic_verify_true);
        }else {
            holder.mVerified.setImageResource(R.drawable.ic_verify_false);
        }


       // holder.machineDetails.setText(newCategoryMachineList.get(position).getManufacturerName()+"/"+newCategoryMachineList.get(position).getModelNo()+
           //     "\n"+"Manufacturing Year :"+newCategoryMachineList.get(position).getManufacturerYear()+
          //      "\n"+"Current Location :"+newCategoryMachineList.get(position).getCurrentLocation()+
               // "\n"+"("+newCategoryMachineList.get(position).getAvailibility()+")");

        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return newCategoryMachineList.size()/*>4?4:newCategoryMachineList.size()*/;
    }
}
