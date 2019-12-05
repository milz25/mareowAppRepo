package com.mareow.recaptchademo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.mareow.recaptchademo.R;

import java.util.ArrayList;

public class CustomListPopupWindowAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> listData;
    LayoutInflater inflater;
    public CustomListPopupWindowAdapter(Context context, ArrayList<String> listData){
        this.context=context;
        this.listData=listData;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.poplist_custom_layout, parent, false);
        }


        AppCompatTextView textView=(AppCompatTextView) listItemView.findViewById(R.id.listpopwindow_item);
        textView.setText(listData.get(position));
        return listItemView;
    }

}
