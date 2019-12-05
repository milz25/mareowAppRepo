package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.DataModels.GridItem;
import com.mareow.recaptchademo.DataModels.Item;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.ViewHolders.Holder;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<Holder> {

    private final int mDefaultSpanCount;
    private List<Item> mItemList=new ArrayList<>();
    int[] textureArrayWin = {
            R.drawable.wo_open_final,
            R.drawable.wo_close_final,
            R.drawable.logged_hour_final,
            R.drawable.kms_runs_final,
    };
    Context context;
    public GridAdapter(Context context, List<Item> itemList, GridLayoutManager gridLayoutManager, int defaultSpanCount) {
        mItemList.clear();
        mItemList = itemList;
        mDefaultSpanCount = defaultSpanCount;
        this.context=context;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isHeaderType(position) ? mDefaultSpanCount : 1;
            }
        });
    }

    private boolean isHeaderType(int position) {
        return mItemList.get(position).getItemType() == Item.HEADER_ITEM_TYPE ? true : false;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view;

        if(viewType == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dash_list_header, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dash_board_adapter, viewGroup, false);
        }

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if(isHeaderType(position)) {
            bindHeaderItem(holder, position);
        } else {
            bindGridItem(holder, position);
        }
    }

    /**
     * This method is used to bind grid item value
     *
     * @param holder
     * @param position
     */
    private void bindGridItem(Holder holder, int position) {

        View container = holder.itemView;

        AppCompatTextView title = (AppCompatTextView) container.findViewById(R.id.dashboard_adapter_machine_name);
        AppCompatTextView count = (AppCompatTextView) container.findViewById(R.id.dashboard_adapter_machine_Count);
        AppCompatImageView imageView=(AppCompatImageView)container.findViewById(R.id.dashboard_adapter_machine_image);

        final GridItem item = (GridItem) mItemList.get(position);


        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate();


        count.setText(item.getPosition());

        if (Constants.USER_ROLE.equals("Supervisor")){
            Glide.with(context).load(getImage(item.getmImagePath()))
                    .apply(options)
                    .into(imageView);
            title.setText(item.getItemTitle());

        }
        if (Constants.USER_ROLE.equals("Operator")){
            if (position<4){

                Glide.with(context).load(getImage(item.getmImagePath()))
                        .apply(options)
                        .into(imageView);
                //imageView.setImageResource(textureArrayWin[position]);
                title.setText(item.getItemTitle());
            }
            if (position>3) {
                    String[] arrrayString = item.getItemTitle().split(":");
                    title.setText(arrrayString[0] + "\n" + arrrayString[1] + "\n" + arrrayString[2]);
                    Glide.with(context).load(item.getmImagePath())
                            .apply(options)
                            .into(imageView);
                }

        }
        if (Constants.USER_ROLE.equals("Renter")){

            Glide.with(context).load(getImage(item.getmImagePath()))
                    .apply(options)
                    .into(imageView);
            title.setText(item.getItemTitle());

        }


        /*if (position<4){

            Glide.with(context).load(getImage(item.getmImagePath()))
                    .apply(options)
                    .into(imageView);
            //imageView.setImageResource(textureArrayWin[position]);
        }
        if (Constants.USER_ROLE.equals("Operator")){
            if (position>3) {
                String[] arrrayString = item.getItemTitle().split(":");
                title.setText(arrrayString[0] + "\n" + arrrayString[1] + "\n" + arrrayString[2]);

                Glide.with(context).load(item.getmImagePath())
                        .apply(options)
                        .into(imageView);
            }else {
                title.setText(item.getItemTitle());
            }
        }else {
            title.setText(item.getItemTitle());
        }*/


      /*  container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "You clicked on the " + item.getItemTitle() + " at Position -> " + item.getPosition() + "!", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    /**
     * This method is used to bind the header with the corresponding item position information
     *
     * @param holder
     * @param position
     */
    private void bindHeaderItem(Holder holder, int position) {
        AppCompatTextView title = (AppCompatTextView) holder.itemView.findViewById(R.id.dash_list_item_header);
        title.setText(mItemList.get(position).getItemTitle());
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position).getItemType() == Item.HEADER_ITEM_TYPE ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /**
     * This method is used to add an item into the recyclerview list
     *
     * @param item
     */
    public void addItem(Item item) {
        mItemList.add(item);
        notifyDataSetChanged();
    }

    /**
     * This method is used to remove items from the list
     *
     * @param item {@link Item}
     */
    public void removeItem(Item item) {
        mItemList.remove(item);
        notifyDataSetChanged();
    }

    public int getImage(String imageName) {
        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }
}
