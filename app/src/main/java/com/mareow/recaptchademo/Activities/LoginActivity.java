package com.mareow.recaptchademo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mareow.recaptchademo.DataModels.JWTToken;
import com.mareow.recaptchademo.DataModels.LoginResponse;
import com.mareow.recaptchademo.DataModels.User;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.AppUpdateChecker;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.RememberMe;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import am.appwise.components.ni.NoInternetDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG ="LoginActivity" ;
    TextInputEditText edit_UserName;
    TextInputEditText edit_Password;
    AppCompatButton btnLogin;
    LinearLayout btnFrameLogin;
    AppCompatTextView btnSignup;
    AppCompatTextView btnForgotLogin;
    AppCompatTextView txtHyperlink;
    AppCompatCheckBox checkBox_RememberMe;
    AppCompatImageView mUserImage;
    AppCompatImageView mPasswordImage;


    public static AppCompatCheckBox checkBox_IamRobot;
    String token;
    TokenManager tokenManager;
    ProgressDialog progressDialog;
    RememberMe rememberMe;
    NoInternetDialog noInternetDialog;
    private static final String URL_VERIFY_ON_SERVER = "https://testbud.in/ultracare/google-recaptcha-verfication.php";

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (EasyPermissions.hasPermissions(this,perms)){

            }else {
                EasyPermissions.requestPermissions(this,"Please allow this permission for proper functionality.",10,perms);
            }
        }



        noInternetDialog = new NoInternetDialog.Builder(this)
                .setBgGradientStart(this.getResources().getColor(R.color.primary_white)) // Start color for background gradient
                .setBgGradientCenter(this.getResources().getColor(R.color.colorPrimary)) // Center color for background gradient
                .setBgGradientEnd(this.getResources().getColor(R.color.colorPrimaryDark)) // End color for background gradient// Background gradient orientation (possible values see below)// Type of background gradient (possible values see below)
               // .setDialogRadius() // Set custom radius for background gradient
               // .setTitleTypeface() // Set custom typeface for title text
               // .setMessageTypeface() // Set custom typeface for message text
                .setButtonColor(this.getResources().getColor(R.color.theme_orange)) // Set custom color for dialog buttons
                .setButtonTextColor(this.getResources().getColor(R.color.colorPrimary)) // Set custom text color for dialog buttons
                .setButtonIconsColor(this.getResources().getColor(R.color.colorPrimary)) // Set custom color for icons of dialog buttons
                .setWifiLoaderColor(this.getResources().getColor(R.color.theme_orange)) // Set custom color for wifi loader
                //.setConnectionCallback() // Set a Callback for network status
                .setCancelable(false) // Set cancelable status for dialog
                .build();


        if (this != null && !isDestroyed()){

            AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
            appUpdateChecker.checkForUpdate(false);
        }



        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        rememberMe=new RememberMe(this);
        tokenManager=new TokenManager(this);


        if (TokenManager.getSessionToken()!=null){

            Constants.USER_ROLE=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null);

            if (Constants.USER_ROLE.equals("Renter")){
                callRenterMainActivity();
                return;
            }else if(Constants.USER_ROLE.equals("Owner")){
                callOwnerMainActivity();
                return;
            }else {
                callMainActivityIntent();
                return;
            }

        }

        initView();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if (rememberMe.getRememberPreference().getString(Constants.PREF_REMEMBER_USERNAME,null)!=null){
            edit_UserName.setText(rememberMe.getRememberPreference().getString(Constants.PREF_REMEMBER_USERNAME,null));
            edit_Password.setText(rememberMe.getRememberPreference().getString(Constants.PREF_REMEMBER_PASSWORD,null));
        }


    }

    private void initView(){
        edit_UserName=(TextInputEditText) findViewById(R.id.login_username);
        edit_Password=(TextInputEditText) findViewById(R.id.login_password);


        btnFrameLogin=(LinearLayout) findViewById(R.id.login_frame_login);
        btnLogin=(AppCompatButton) findViewById(R.id.login_btn_login);
        btnSignup=(AppCompatTextView) findViewById(R.id.login_signup);
        btnForgotLogin=(AppCompatTextView)findViewById(R.id.login_forgot_login);
        checkBox_IamRobot=(AppCompatCheckBox) findViewById(R.id.login_iamrobot);
        checkBox_RememberMe=(AppCompatCheckBox) findViewById(R.id.login_remeber_me);

        mUserImage=(AppCompatImageView)findViewById(R.id.login_user_image);
        mPasswordImage=(AppCompatImageView)findViewById(R.id.login_password_image);

        edit_UserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mUserImage.setImageResource(R.drawable.user_blue);
                }else {
                    mUserImage.setImageResource(R.drawable.user);
                }
            }
        });
        edit_Password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mPasswordImage.setImageResource(R.drawable.password_blue);
                }else {
                    mPasswordImage.setImageResource(R.drawable.password);
                }
            }
        });



        txtHyperlink=(AppCompatTextView)findViewById(R.id.login_hyperlink);

        SpannableString s1 = new SpannableString("By Continuing, you accept the Terms & Conditions and Privacy Policy of mareow.");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
               //Toast.makeText(LoginActivity.this, "Term and Condition", Toast.LENGTH_SHORT).show();
                String termsData=getHTMLTermandConditionData();
                showTermAndConditionActivity(termsData,"Terms and Condition");
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.Blue_Text));
            }
        };
        s1.setSpan(clickableSpan, 30, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
               // Toast.makeText(LoginActivity.this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                String termsData=getHTMLPrivateAndPolicyData();
                showTermAndConditionActivity(termsData,"Privacy Policy");

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.Blue_Text));
            }
        };
        s1.setSpan(clickableSpan2, 53, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtHyperlink.setText(s1);
        txtHyperlink.setMovementMethod(LinkMovementMethod.getInstance());

        checkBox_RememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   Constants.LOGIN_REMEBER_ME=true;
                }else {
                    Constants.LOGIN_REMEBER_ME=false;
                }
            }
        });

        if (checkBox_RememberMe.isChecked()){
            Constants.LOGIN_REMEBER_ME=true;
        }else {
            Constants.LOGIN_REMEBER_ME=false;
          }

        btnFrameLogin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnForgotLogin.setOnClickListener(this);

        checkBox_IamRobot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Constants.LOGIN_OR_SIGNUP="login";
                    callReCaptCha();
                }
            }
        });



    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                CallRetrofit();
                break;
            case R.id.login_frame_login:
                CallRetrofit();
                break;
            case R.id.login_forgot_login:
               // Toast.makeText(this, "Forgot", Toast.LENGTH_SHORT).show();
                callForgotPasswordIntent();
                break;
            case R.id.login_signup:
              //  Toast.makeText(this, "Signup", Toast.LENGTH_SHORT).show();
                callSignupIntent();
                break;

        }
    }

    private void callForgotPasswordIntent() {
        Intent intent=new Intent(this,ForgotPasswordActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void callSignupIntent() {
        Intent intent=new Intent(this,SignupActivity.class);
        startActivity(intent);
        finish();
    }

    private void callReCaptCha() {
        SafetyNet.getClient(this).verifyWithRecaptcha(Constants.SITE_KEY)
                 .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                     @Override
                     public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {

                         if (!response.getTokenResult().isEmpty()) {
                            // Toast.makeText(LoginActivity.this, "Token :"+response.getTokenResult(), Toast.LENGTH_SHORT).show();
                             Util util=new Util();
                             util.verifyTokenOnServer(LoginActivity.this,response.getTokenResult());

                         }
                     }
                 })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.d(TAG, "Error message: " + CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                            //Toast.makeText(LoginActivity.this, "Error :"+CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
                            //Toast.makeText(LoginActivity.this, " Unknown Error :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
           }


           private void CallRetrofit(){

               final String username=edit_UserName.getText().toString().trim();
               final String password=edit_Password.getText().toString().trim();

               if (TextUtils.isEmpty(username)){
                   edit_UserName.setError("Enter username");
                   edit_UserName.requestFocus();
                   return;
               }
               if (TextUtils.isEmpty(password)){
                   edit_Password.setError("Enter password");
                   edit_Password.requestFocus();
                   return;
               }
               if (!Constants.LOGIN_RECAPTCHA_CHECK){
                   //Toast.makeText(this, "Please verify reCAPTCHA", Toast.LENGTH_SHORT).show();
                   showSnackbar("Please verify reCAPTCHA");
                   return;
               }
               User user=new User();
               user.setUserName(username);
               user.setPassword(password);

               progressDialog.show();

               final ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
               Call<JWTToken> tokenCall=apiInterface.getJWT(user);
               tokenCall.enqueue(new Callback<JWTToken>() {
                   @Override
                   public void onResponse(Call<JWTToken> call, Response<JWTToken> response) {
                       if (response.isSuccessful()){
                           JWTToken jwtToken=response.body();
                           token=jwtToken.getToken().toString();
                           Call<LoginResponse> getDetailsCall=apiInterface.getUserDetails("Bearer "+token);
                           getDetailsCall.enqueue(new Callback<LoginResponse>() {
                               @Override
                               public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                   if (response.isSuccessful()){
                                       if (Constants.LOGIN_REMEBER_ME){
                                           rememberMe.rememberMe(username,password);
                                       }else {
                                           rememberMe.clearRemember();
                                       }
                                       LoginResponse loginResponse=response.body();
                                       progressDialog.dismiss();
                                       Constants.USER_ROLE=loginResponse.getRoleName();
                                       //Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                       tokenManager.addUserDetails(loginResponse,token);

                                       if (Constants.USER_ROLE.equals("Renter")){
                                           callRenterMainActivity();
                                       }else if(Constants.USER_ROLE.equals("Owner")){
                                           callOwnerMainActivity();
                                       }else {
                                           callMainActivityIntent();
                                       }

                                   }else {
                                      // Toast.makeText(LoginActivity.this,response.message(), Toast.LENGTH_SHORT).show();
                                       edit_UserName.setError("Invalid Token");
                                       edit_UserName.requestFocus();
                                       callRecaptchaSetting();
                                       progressDialog.dismiss();
                                   }
                               }

                               @Override
                               public void onFailure(Call<LoginResponse> call, Throwable t) {
                                  // Toast.makeText(LoginActivity.this,"Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                   callRecaptchaSetting();
                                   progressDialog.dismiss();
                               }
                           });
                       }else {
                           //Toast.makeText(LoginActivity.this,response.message(), Toast.LENGTH_SHORT).show();
                           edit_UserName.setError("Authentication Failed. Username or Password not valid.");
                           edit_UserName.requestFocus();
                           callRecaptchaSetting();
                           progressDialog.dismiss();
                       }
                   }

                   @Override
                   public void onFailure(Call<JWTToken> call, Throwable t) {
                       showSnackbar("Error :"+t.getMessage());
                       callRecaptchaSetting();
                       progressDialog.dismiss();
                   }
               });

           }

    private void callMainActivityIntent() {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void callRecaptchaSetting(){
        checkBox_IamRobot.setEnabled(true);
        checkBox_IamRobot.setChecked(false);
        Constants.LOGIN_RECAPTCHA_CHECK=false;
    }


    public void showTermAndConditionActivity(String data, String title){

      /*Intent intent=new Intent(this,TermAndConditionActivity.class);
      startActivity(intent);
      this.finish();*/

        AppCompatTextView mTitle;
        WebView mWebViewData;
        AppCompatImageButton btnClose;

        final Dialog dialog=new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.term_and_condition_popup_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTitle=(AppCompatTextView)dialog.findViewById(R.id.RBD_popdialog_title);
        mTitle.setText(title);
        mWebViewData=(WebView)dialog.findViewById(R.id.RBD_popdialog_web_data);

      //  mWebViewData.setWebViewClient(new WebViewClient());

       /* final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);
        mWebViewData.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
*/
        mWebViewData.getSettings().setJavaScriptEnabled(true);

        String justifyTag = "<html><body style='text-align:justify;'>%s</body></html>";
        String dataString = String.format(Locale.US, justifyTag,data);
        mWebViewData.loadData(dataString, "text/html", "UTF-8");


        btnClose=(AppCompatImageButton)dialog.findViewById(R.id.RBD_popdialog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();


    }

   public void  callRenterMainActivity(){
       Intent intent =new Intent(this,RenterMainActivity.class);
       startActivity(intent);
       finish();
    }

    private void callOwnerMainActivity() {
        Intent intent =new Intent(this,OwnerMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }



    /*public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast){
          //  Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void openAndroidDialog(){
          //  Toast.makeText(mContext, "Script", Toast.LENGTH_SHORT).show();
        }

    }*/


    private String getHTMLTermandConditionData() {
        StringBuilder html = new StringBuilder();
        try {
            AssetManager assetManager = getAssets();

            InputStream input = assetManager.open("mareowgenerictermsandcondition.jsp");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }
            br.close();
        } catch (Exception e) {
            //Handle the exception here
        }

        return html.toString();
    }

    private String getHTMLPrivateAndPolicyData() {
        StringBuilder html = new StringBuilder();
        try {
            AssetManager assetManager = getAssets();

            InputStream input = assetManager.open("mareowprivacypolicy.jsp");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = br.readLine()) != null) {
                html.append(line);
            }
            br.close();
        } catch (Exception e) {
            //Handle the exception here
        }

        return html.toString();
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getCurrentFocus(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(this.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED &&grantResults[2]==PackageManager.PERMISSION_GRANTED
                    && grantResults[3]==PackageManager.PERMISSION_GRANTED &&grantResults[4]==PackageManager.PERMISSION_GRANTED){

            }else {
                EasyPermissions.requestPermissions(
                        new PermissionRequest.Builder(this,10,perms)
                                .setRationale("Please allow this permission for proper functionality.")
                                .setPositiveButtonText("Ok")
                                .build());
            }
        }
    }

}
