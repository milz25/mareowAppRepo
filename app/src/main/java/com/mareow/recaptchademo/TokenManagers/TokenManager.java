package com.mareow.recaptchademo.TokenManagers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.mareow.recaptchademo.DataModels.AccountSetting;
import com.mareow.recaptchademo.DataModels.LoginResponse;
import com.mareow.recaptchademo.DataModels.ProfileData;
import com.mareow.recaptchademo.FragmentUserDetails.AddressFragment;
import com.mareow.recaptchademo.FragmentUserDetails.BankDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.GeneralDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.GovernmentIdFragment;
import com.mareow.recaptchademo.FragmentUserDetails.OperatorchargeDetailsFragment;
import com.mareow.recaptchademo.FragmentUserDetails.ReferDetailsFragment;
import com.mareow.recaptchademo.Utils.Constants;

import java.util.HashSet;
import java.util.Set;

public class TokenManager {

    private static SharedPreferences sessionPreferences;
    private static SharedPreferences.Editor editor;
    private Context context;
    private static ProgressDialog progressDialog;

    public TokenManager(Context context){
        this.context=context;
        sessionPreferences=context.getSharedPreferences(Constants.PREF_SESSSION_NAME,Context.MODE_PRIVATE);
        editor=sessionPreferences.edit();
    }

    public static String getSessionToken(){
        String token=sessionPreferences.getString(Constants.PREF_KEY_TOKEN,null);
        return token;
    }

    public static void clearSession(){
        sessionPreferences.edit().clear().apply();

        GeneralDetailsFragment.firstName=null;
        GeneralDetailsFragment.lastName=null;
        GeneralDetailsFragment.mAboutYourSelf=null;

        OperatorchargeDetailsFragment.OperatorWorkAssociation=null;
        OperatorchargeDetailsFragment.certificateToRunPath=null;
        OperatorchargeDetailsFragment.credentialsForAgencyPath=null;
        OperatorchargeDetailsFragment.operatorAmount=null;
        OperatorchargeDetailsFragment.AMOUNTTYPE=false;
        OperatorchargeDetailsFragment.ACCOMODATION=false;
        OperatorchargeDetailsFragment.TRASPORTATION=false;
        OperatorchargeDetailsFragment.FOOD=false;


        AddressFragment.mAddress1=null;
        AddressFragment.mAddress2=null;
        AddressFragment.mAddress3=null;
        AddressFragment.mAddress4=null;
        AddressFragment.mPincode=null;
        AddressFragment.mCity=null;
        AddressFragment.mState=null;
        AddressFragment.mCountry=null;


        BankDetailsFragment.mAccountHolder=null;
        BankDetailsFragment.mPayableAt=null;
        BankDetailsFragment.mBank=null;
        BankDetailsFragment.mAccountNo=null;
        BankDetailsFragment.mAccountType=null;
        BankDetailsFragment.mIFSCCode=null;
        BankDetailsFragment.mPaytmAccountNo=null;

        GovernmentIdFragment.govId=null;
        GovernmentIdFragment.govProofPath=null;
        //GovernmentIdFragment.gstNo=mUSerProfileDataList.get;
        //  GovernmentIdFragment.gstProofDocPath=null;


        ReferDetailsFragment.mRefer_Type=null;
        ReferDetailsFragment.mRefer_By=null;
        ReferDetailsFragment.mRefer_MobileNo=null;

        //dissmisProgress();

    }

    public static void addUserDetails(LoginResponse loginResponse,String token){
        Set<String> set = new HashSet<String>();
        set.addAll(loginResponse.getSegment());
        editor.putString(Constants.PREF_KEY_TOKEN,token);
        editor.putInt(Constants.PREF_KEY_USERID,loginResponse.getUserId());
        editor.putString(Constants.PREF_KEY_USERNAME,loginResponse.getUserName());
        editor.putString(Constants.PREF_KEY_FIRST_NAME,loginResponse.getFirstName());
        editor.putString(Constants.PREF_KEY_LASTNAME,loginResponse.getLastName());
        editor.putString(Constants.PREF_KEY_EMAIL,loginResponse.getEmail());
        editor.putString(Constants.PREF_KEY_MOBILE_NO,loginResponse.getMobileNo());
        editor.putString(Constants.PREF_KEY_REFER_BY,String.valueOf(loginResponse.getReferBy()));
        editor.putString(Constants.PREF_KEY_USER_IMAGE,loginResponse.getUserImage());

        editor.putString(Constants.PREF_KEY_ROLE_ID,String.valueOf(loginResponse.getRoleId()));
        editor.putString(Constants.PREF_KEY_ROLE_NAME,loginResponse.getRoleName());
        editor.putString(Constants.PREF_KEY_ROLE_LOGIC,loginResponse.getRoleLogic());
        editor.putInt(Constants.PREF_KEY_PARTY_ID,loginResponse.getPartyId());
        editor.putString(Constants.PREF_KEY_PARTY_NAME,loginResponse.getPartyName());
        editor.putString(Constants.PREF_KEY_PARTY_CATEGORY,loginResponse.getPartyCategoty());

        editor.putBoolean(Constants.PREF_KEY_IS_VERIFIED,loginResponse.isVerified());
        editor.putBoolean(Constants.PREF_KEY_IS_EMAIL_FLAG,loginResponse.isEmailFlg());
        editor.putBoolean(Constants.PREF_KEY_IS_NOTI_FLAG,loginResponse.isNotificationFlg());
        editor.putBoolean(Constants.PREF_KEY_IS_CHAT_FLAG,loginResponse.isChatFlg());
        editor.putBoolean(Constants.PREF_KEY_IS_LOCATION_FLAG,loginResponse.isLocationFlg());
        editor.putStringSet(Constants.PERFERED_SEGEMNT,set);

        editor.apply();
    }
    public static SharedPreferences getUserDetailsPreference(){
        return sessionPreferences;
    }


    public static void updateUuserDetails(ProfileData profileData){

        editor.putString(Constants.PREF_KEY_USERNAME,profileData.getUserName());
        editor.putString(Constants.PREF_KEY_FIRST_NAME,profileData.getFirstName());
        editor.putString(Constants.PREF_KEY_LASTNAME,profileData.getLastName());
        editor.putString(Constants.PREF_KEY_EMAIL,profileData.getEmail());
        editor.putString(Constants.PREF_KEY_MOBILE_NO,profileData.getMobileNo());
        editor.putString(Constants.PREF_KEY_REFER_BY,profileData.getReferBy());
        editor.putString(Constants.PREF_KEY_USER_IMAGE,profileData.getUserImagePath());

        editor.apply();
    }

    public static void updateAccountSetting(AccountSetting accountSetting){

        editor.putBoolean(Constants.PREF_KEY_IS_EMAIL_FLAG,accountSetting.isEmailFlg());
        editor.putBoolean(Constants.PREF_KEY_IS_NOTI_FLAG,accountSetting.isNotificationFlg());
        editor.putBoolean(Constants.PREF_KEY_IS_CHAT_FLAG,accountSetting.isChatFlg());
        editor.putBoolean(Constants.PREF_KEY_IS_LOCATION_FLAG,accountSetting.isLocationFlg());
        editor.apply();
    }

    public static void showProgressDialog(Context context){

        if (progressDialog==null){

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...........");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        if (context!=null){
            progressDialog.show();
        }

    }

    public static void dissmisProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
