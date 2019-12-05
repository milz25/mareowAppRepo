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
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.ViewHolders.MainDashBoardViewHolder;

import java.util.List;

public class MainDashBoardAdapter extends RecyclerView.Adapter<MainDashBoardViewHolder> {

    Context context;
    List<String> dataKeyInteger;
    List<String> dataKeyString;
    List<String> dataKeyImages;
    RequestOptions options;
    public MainDashBoardAdapter(Context context, List<String> dataKeyInteger, List<String> dataKeyString, List<String> dataKeyImages) {
        this.context=context;
        this.dataKeyInteger=dataKeyInteger;
        this.dataKeyString=dataKeyString;
        this.dataKeyImages=dataKeyImages;
        options = new RequestOptions()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.category_defualt)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate();

    }

    @NonNull
    @Override
    public MainDashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.dash_board_adapter,parent,false);
        return new MainDashBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainDashBoardViewHolder holder, int position) {

        holder.count.setText(dataKeyInteger.get(position));

        if (Constants.USER_ROLE.equals("Operator")){
            if (position<=5){
                Glide.with(context).load(getImage(dataKeyImages.get(position)))
                        .apply(options)
                        .into(holder.imageView);
                holder.title.setText(dataKeyString.get(position));
            }
            if (position>5) {
                String[] arrrayString = dataKeyString.get(position).split(":");
                holder.title.setText(arrrayString[0] + "\n" + arrrayString[1] + "\n" + arrrayString[2]);
                Glide.with(context).load(dataKeyImages.get(position))
                        .apply(options)
                        .into(holder.imageView);
            }

        }

        if (Constants.USER_ROLE.equals("Supervisor")){
            Glide.with(context).load(getImage(dataKeyImages.get(position)))
                    .apply(options)
                    .into(holder.imageView);
            holder.title.setText(dataKeyString.get(position));
        }

        if (Constants.USER_ROLE.equals("Renter")){

            Glide.with(context).load(getImage(dataKeyImages.get(position)))
                    .apply(options)
                    .into(holder.imageView);
            holder.title.setText(dataKeyString.get(position));

        }
        if (Constants.USER_ROLE.equals("Owner")){
            if (position<=8){
                Glide.with(context).load(getImage(dataKeyImages.get(position)))
                        .apply(options)
                        .into(holder.imageView);
                holder.title.setText(dataKeyString.get(position));
            }else {

                String[] arrrayString = dataKeyString.get(position).split(":");
                holder.title.setText(arrrayString[0] + "\n" + arrrayString[1] + "\n" + arrrayString[2]);
                Glide.with(context).load(dataKeyImages.get(position))
                        .apply(options)
                        .into(holder.imageView);
            }


        }

    }

    @Override
    public int getItemCount() {
        return dataKeyInteger.size();
    }

    public int getImage(String imageName) {
        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }
}
