package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.DataModels.CategoryImage;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.RecyclerViewClickListener;
import com.mareow.recaptchademo.ViewHolders.RenterMainCategoryViewHolder;

import java.util.Arrays;
import java.util.List;

public class RenterMainCategoryAdapter extends RecyclerView.Adapter<RenterMainCategoryViewHolder> {

    Context context;
    List<RenterMachine> categoryList;
    RecyclerViewClickListener listener;
    List<CategoryImage> categoryMap;
    int selectedPosition=-1;
    RequestOptions options;
    boolean [] CHECK_SELECTED;
    public RenterMainCategoryAdapter(Context context, List<RenterMachine> categoryList, List<CategoryImage> categoryMap, RecyclerViewClickListener listener){
      this.context=context;
      this.categoryList=categoryList;
      this.listener=listener;
      this.categoryMap=categoryMap;
        options = new RequestOptions()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.able_to_run_machine)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate();

       // setHasStableIds(true);
        CHECK_SELECTED=new boolean[categoryList.size()];
        Arrays.fill(CHECK_SELECTED,false);

    }
    @NonNull
    @Override
    public RenterMainCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.renter_category_adapter,parent,false);
        return new RenterMainCategoryViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RenterMainCategoryViewHolder holder, int position) {

        for (int i=0;i<categoryMap.size();i++){
            if (categoryMap.get(i).getLookupCode().equals(categoryList.get(position).getCategoryCode())){

                Glide.with(context).load(categoryMap.get(i).getRefFileName())
                        .apply(options)
                        .into(holder.machineImage);

                break;
            }
        }

        holder.mMachineText.setText(categoryList.get(position).getCategoryName());

        if(CHECK_SELECTED[position]){
            //holder.cardView.setBackground(context.getResources().getDrawable(R.drawable.card_category_back));
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
            holder.cardView.setCardElevation(20);
        }
        else{
            holder.cardView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.cardView.setCardElevation(5);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 selectedPosition=position;
                for (int i=0;i<CHECK_SELECTED.length;i++){
                   if (i==position){
                       if (CHECK_SELECTED[i]){
                           CHECK_SELECTED[i]=false;
                       }else {
                           CHECK_SELECTED[i]=true;
                       }
                   }else {
                       CHECK_SELECTED[i]=false;
                   }
                }

                notifyDataSetChanged();
                listener.onClick(v,position);
            }
        });


    }

   /* @Override
    public void onBindViewHolder(@NonNull RenterMainCategoryViewHolder holder, int position, @NonNull List<Object> payloads) {

        RenterMachine renterMachine=categoryList.get(position);

        holder.setActivatedState(mSelectionTracker.isSelected(renterMachine));
        if(!payloads.contains(SelectionTracker.SELECTION_CHANGED_MARKER)) {
            holder.setData(renterMachine);
        }

    }*/

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    /*public class RenterMainCategoryViewHolder extends RecyclerView.ViewHolder implements ViewHolderWithDetails<Long>{
        private View mRootView;
        public AppCompatImageView machineImage;
        public AppCompatTextView mMachineText;
        RecyclerViewClickListener listener;

        public RenterMainCategoryViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mRootView=itemView;
            machineImage = (AppCompatImageView) itemView.findViewById(R.id.renter_category_machine_image);
            mMachineText = (AppCompatTextView) itemView.findViewById(R.id.renter_category_machine_text);
            this.listener = listener;
           // itemView.setOnClickListener(this);
        }

      *//*  @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }*//*

        protected void setData(RenterMachine renterMachine) {

            for (int i=0;i<categoryMap.size();i++){
                if (categoryMap.get(i).getLookupCode().equals(renterMachine.getCategoryCode())){

                    Glide.with(context).load(categoryMap.get(i).getRefFileName())
                            .apply(options)
                            .into(machineImage);

                    break;
                }
            }

            mMachineText.setText(renterMachine.getCategoryName());

        }

        public void setActivatedState(boolean state) {
            if(mRootView != null) {
                mRootView.setActivated(state);
                mRootView.setBackgroundColor(context.getResources().getColor(state ? R.color.theme_orange : android.R.color.holo_blue_dark));
            }
        }

        @Override
        public MachineDetails getItemDetails() {
            return new MachineDetails(getAdapterPosition(),categoryList.get(getAdapterPosition()));
        }
    }

    public interface ViewHolderWithDetails<TItem> {
        ItemDetailsLookup.ItemDetails<TItem> getItemDetails();
    }*/

}
