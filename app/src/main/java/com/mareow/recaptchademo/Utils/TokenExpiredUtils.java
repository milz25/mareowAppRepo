package com.mareow.recaptchademo.Utils;

import android.app.Activity;
import android.content.Intent;

import com.mareow.recaptchademo.Activities.LoginActivity;
import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.TokenManagers.TokenManager;

public class TokenExpiredUtils {


    public static void tokenExpired(Activity activity){
        TokenManager.clearSession();

        Intent intent;
        if (Constants.USER_ROLE.equals("Renter")){
            intent=new Intent((RenterMainActivity)activity, LoginActivity.class);
        }else if(Constants.USER_ROLE.equals("Owner")){
            intent=new Intent((OwnerMainActivity)activity, LoginActivity.class);
        }else {
            intent=new Intent((MainActivity)activity, LoginActivity.class);
        }


//        activity.unregisterReceiver(new NotifyServiceReceiver());
      //  activity.stopService(new Intent(activity, NotifyService.class));
        activity.startActivity(intent);
        activity.finish();
        return;
    }
}
