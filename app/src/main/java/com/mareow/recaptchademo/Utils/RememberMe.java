package com.mareow.recaptchademo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class RememberMe {

    private static SharedPreferences rememberMePreferences;
    private static SharedPreferences.Editor editor;
    private Context context;

    public RememberMe(Context context){
        this.context=context;
        rememberMePreferences=context.getSharedPreferences(Constants.PREF_REMEMBER_ME,Context.MODE_PRIVATE);
        editor=rememberMePreferences.edit();
    }


    public static void clearRemember(){
        rememberMePreferences.edit().clear().apply();
    }

    public static void rememberMe(String username, String password){
        editor.putString(Constants.PREF_REMEMBER_USERNAME,username);
        editor.putString(Constants.PREF_REMEMBER_PASSWORD,password);
        editor.apply();
    }

    public static SharedPreferences getRememberPreference(){
        return rememberMePreferences;
    }

}
