package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.RenterMachineDetailsFragment;
import com.mareow.recaptchademo.ViewHolders.ShowAllMachineViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ShowAllMachineAdapter extends RecyclerView.Adapter<ShowAllMachineViewHolder> {

    Context context;
    List<RenterMachine> machineList;
    public ShowAllMachineAdapter(Context context, List<RenterMachine> machineList){
      this.context=context;
      this.machineList=machineList;
    }

    @NonNull
    @Override
    public ShowAllMachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.renter_sub_within_sub_adapter,parent,false);
        return new ShowAllMachineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllMachineViewHolder holder, int position) {


        if (machineList.get(position).getImagesPath().size()>0){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.able_to_run_machine)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate();

            Glide.with(context).load(machineList.get(position).getImagePath())
                    .apply(options)
                    .into(holder.machineImage);
        }


        holder.machineName.setText(machineList.get(position).getMachineName());

        holder.mMFG.setText(machineList.get(position).getManufacturerName());
        holder.mModel.setText(machineList.get(position).getModelNo());
        holder.mfgYear.setText(machineList.get(position).getManufacturerYear());
        holder.locateAt.setText(machineList.get(position).getCurrentLocation());
        holder.mAvailability.setText(machineList.get(position).getAvailibility());

        if (machineList.get(position).isFavouriteFlg()){
            holder.mFavoarite.setImageResource(R.drawable.ic_bookmark);
        }else {
            holder.mFavoarite.setImageResource(R.drawable.ic_bookmark_border);
        }

        holder.mRating.setText("("+machineList.get(position).getOverallRating()+")");
        if (machineList.get(position).isIsverified()){
            holder.mVerified.setImageResource(R.drawable.ic_verify_true);
        }else {
            holder.mVerified.setImageResource(R.drawable.ic_verify_false);
        }


        holder.btnTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallMachineDetailsFragment(machineList.get(position));
            }
        });
    }

    private void CallMachineDetailsFragment(RenterMachine renterMachine) {

        ArrayList<RenterMachine> machines=new ArrayList<>();
        machines.add(renterMachine);
        RenterMachineDetailsFragment detailsFragment=new RenterMachineDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("newList",machines);
        detailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction =((RenterMainActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container_main, detailsFragment);
        fragmentTransaction.addToBackStack("showAll");
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public int getItemCount() {
        return machineList.size();
    }


    public void updateList(List<RenterMachine> newList) {
        machineList=new ArrayList<>();
        machineList.addAll(newList);
        notifyDataSetChanged();
    }


}
