package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mareow.recaptchademo.R;

import java.util.List;

public class RenterMachineImagePagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<String> imagePath;

    public RenterMachineImagePagerAdapter(Context context,List<String> imagePath) {
        this.context = context;
        this.imagePath = imagePath;
    }

    @Override
    public int getCount() {
        return imagePath.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.image_slider_view,null);

        AppCompatImageView imageView=(AppCompatImageView)view.findViewById(R.id.image_slider);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.able_to_run_machine)
                .error(R.drawable.able_to_run_machine)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        /*.transform(new CircleTransform(context));*/
        if (!imagePath.get(position).equals("")){
            Glide.with(context).load(imagePath.get(position))
                    .apply(options)
                    .into(imageView);
        }

        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager=(ViewPager)container;
        View view=(View)object;
        viewPager.removeView(view);

    }
}
