package com.mareow.recaptchademo.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareow.recaptchademo.Activities.SignupActivity;
import com.mareow.recaptchademo.Adapters.SpinnerRecycleAdapter;
import com.mareow.recaptchademo.R;

import java.util.ArrayList;

public class CustomDialogForSpinner {
    Context context;
    ArrayList<String> listData;
    String title;
    Activity activity;
    public CustomDialogForSpinner(Context context,String title,ArrayList<String> listData){
        this.context=context;
        this.title=title;
        this.listData=listData;
        activity = (Activity) context;
    }


    public void ShowSpinnerDialog(){
        RecyclerView spinnerRecycle;
        AppCompatTextView titleText;

        final Dialog dialog=new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_spinner_dialog);




       /* DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = (int) (metrics.widthPixels*0.7);

        activity.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
*/

        spinnerRecycle=(RecyclerView)dialog.findViewById(R.id.custom_spinner_dialog_recycle);
        titleText=(AppCompatTextView)dialog.findViewById(R.id.custom_spinner_dialog_title);

        titleText.setText(title);
        spinnerRecycle.setHasFixedSize(false);
        spinnerRecycle.setItemAnimator(new DefaultItemAnimator());
        spinnerRecycle.setLayoutManager(new LinearLayoutManager(context));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                dialog.dismiss();
                if (Constants.SIGNUP){
                    SignupActivity.mRoleSpinner.setText(listData.get(position));
                }
                /*if (Constants.ADD_DAILY_LOG){
                    AddDailyActivityLogFragment.mSpinnerStatus.setText(listData.get(position));
                }*/
            }

        };


        SpinnerRecycleAdapter recycleAdapter=new SpinnerRecycleAdapter(context,listData,listener);
        spinnerRecycle.setAdapter(recycleAdapter);
        dialog.show();
    }
}
