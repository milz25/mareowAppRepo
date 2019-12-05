package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.RenterFragments.ShowAllMachineForRenterFragment;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.Utils.SpacesItemDecoration;
import com.mareow.recaptchademo.ViewHolders.RenterMainSubcategoryViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RenterMainSubcategoryAdapter extends RecyclerView.Adapter<RenterMainSubcategoryViewHolder> {

    GridLayoutManager gridLayoutManager;
    private final int DEFAULT_SPAN=2;
    Context context;
    List<RenterMachine> subCategory;
    List<RenterMachine> machineListForRenter;
    RenterSubwithinSubAdapter subwithinSubAdapter;

    public RenterMainSubcategoryAdapter(Context context, List<RenterMachine> subCategory, List<RenterMachine> machineListForRenter){
      this.context=context;
      this.subCategory=subCategory;
      this.machineListForRenter=machineListForRenter;

    }
    @NonNull
    @Override
    public RenterMainSubcategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.renter_subcategory_adapter,parent,false);
        return new RenterMainSubcategoryViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RenterMainSubcategoryViewHolder holder, int position) {

        List<RenterMachine> newCategoryMachineList=new ArrayList<>();
        for (int i=0;i<machineListForRenter.size();i++){
            if (subCategory.get(position).getSubCategoryName().equals(machineListForRenter.get(i).getSubCategoryName())){
                newCategoryMachineList.add(machineListForRenter.get(i));
            }
        }
        holder.mSubRecyleView.setHasFixedSize(false);
        holder.mSubRecyleView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        holder.mSubRecyleView.addItemDecoration(new SpacesItemDecoration(5));
        gridLayoutManager=new GridLayoutManager(context,DEFAULT_SPAN);
        holder.mSubRecyleView.setLayoutManager(gridLayoutManager);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }
        };

        Collections.sort(newCategoryMachineList, new Comparator<RenterMachine>() {
            @Override
            public int compare(RenterMachine u1, RenterMachine u2) {
                return u1.getMachineName().compareToIgnoreCase(u2.getMachineName());
            }
        });

        subwithinSubAdapter=new RenterSubwithinSubAdapter(context,newCategoryMachineList,listener);
        holder.mSubRecyleView.setAdapter(subwithinSubAdapter);
        if (newCategoryMachineList.size()>4){
            holder.btnShowAll.setText(newCategoryMachineList.get(0).getSubCategoryName()+" (Show All)");
        }else {
            holder.btnShowAll.setText(newCategoryMachineList.get(0).getSubCategoryName()+" (Show All)");
            holder.btnShowAll.setEnabled(false);
            holder.btnShowAll.setTextColor(context.getResources().getColor(R.color.light_gray));
        }

        holder.btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callShowAllMachineFragment(position);
            }
        });

        holder.setIsRecyclable(false);
    }


    @Override
    public int getItemCount() {
        return subCategory.size();
    }

    private void callShowAllMachineFragment(int pos) {

        Constants.CATEGORY_CHECK=false;
        RenterMachine renterMachine=subCategory.get(pos);
        List<RenterMachine> allMachine=new ArrayList<>();

        for (int i=0;i<machineListForRenter.size();i++){
            if (renterMachine.getSubCategoryName().equals(machineListForRenter.get(i).getSubCategoryName())){
                allMachine.add(machineListForRenter.get(i));
            }
        }


        Fragment showAllMachineForRenterFragment = new ShowAllMachineForRenterFragment(allMachine);
        FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_main, showAllMachineForRenterFragment);// give your fragment container id in first parameter
        transaction.addToBackStack("MachineMain");
        transaction.commitAllowingStateLoss();

    }

    public void updateList(List<RenterMachine> subCategory, List<RenterMachine> newList){
        /*if (subwithinSubAdapter!=null){
            subwithinSubAdapter.updateList(newList);
        }*/

        this.subCategory=subCategory;
        this.machineListForRenter=newList;

        notifyDataSetChanged();
    }
}
