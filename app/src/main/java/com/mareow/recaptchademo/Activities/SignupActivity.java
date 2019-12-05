package com.mareow.recaptchademo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mareow.recaptchademo.DataModels.JWTToken;
import com.mareow.recaptchademo.DataModels.LoginResponse;
import com.mareow.recaptchademo.DataModels.NewUser;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.User;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;
import com.mareow.recaptchademo.Utils.CustomDialogForSpinner;
import com.mareow.recaptchademo.Utils.Util;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SignupActivity";
    //AppCompatSpinner roleSpinner;
    TextInputEditText edit_Firstname;
    TextInputEditText edit_Lastname;
    TextInputEditText edit_Username;
    TextInputEditText edit_Password;
    TextInputEditText edit_Email;
    TextInputEditText edit_Mobile;

    AppCompatCheckBox checkBoxSetting;
    AppCompatCheckBox checkBoxnewsLatter;
    AppCompatCheckBox checkBoxAcceptService;
    public static AppCompatCheckBox checkBoxIamRobot;

    AppCompatButton btnCreateAccount;
    ApiInterface apiInterface;

    boolean USER_EXIST=false;
    boolean EMAIL_EXIST=false;

    public static TextInputEditText mRoleSpinner;
    ArrayList<String> roleItems;
    HashMap<String,String> roleMap=new HashMap<>();
    NewUser newUser;
    ProgressDialog progressDialog;

    String token;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            LoginActivity.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            LoginActivity.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait.......");
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        tokenManager=new TokenManager(this);
        initView();
        roleItems=new ArrayList<>();
        callUserRole();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void callUserRole() {
        progressDialog.show();
        ApiInterface apiInterface1=ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call=apiInterface1.getUserRole();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String roleResponse = response.body().string();
                        JSONObject jsonObject=new JSONObject(roleResponse);
                        progressDialog.dismiss();
                        parseJSONObject(jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    //Toast.makeText(SignupActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showSnackbar("Error :"+t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void parseJSONObject(JSONObject roleResponse) {
        for(Iterator<String> iter = roleResponse.keys(); iter.hasNext();) {
            String key = iter.next();
            roleItems.add(key);
            try {
                roleMap.put(key, roleResponse.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {

        mRoleSpinner=(TextInputEditText) findViewById(R.id.signup_role);
       // mRoleSpinner.setOnClickListener(this);
        mRoleSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Constants.SIGNUP=true;
                    Constants.ADD_DAILY_LOG=false;
                    showAlertDialog();
                }
            }
        });

        edit_Firstname=(TextInputEditText)findViewById(R.id.signup_firstname);
        edit_Lastname=(TextInputEditText)findViewById(R.id.signup_lastname);
        edit_Username=(TextInputEditText)findViewById(R.id.signup_username);
        edit_Password=(TextInputEditText)findViewById(R.id.signup_password);
        edit_Email=(TextInputEditText)findViewById(R.id.signup_email);
        edit_Mobile=(TextInputEditText)findViewById(R.id.signup_mobile);


        checkBoxSetting=(AppCompatCheckBox)findViewById(R.id.signup_account_setting);
        checkBoxnewsLatter=(AppCompatCheckBox)findViewById(R.id.signup_newslatter);
        checkBoxAcceptService=(AppCompatCheckBox)findViewById(R.id.signup_term_service);

        SpannableString s1 = new SpannableString("Accept term of service");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //Toast.makeText(LoginActivity.this, "Term and Condition", Toast.LENGTH_SHORT).show();
                String termsData=getHTMLTermandConditionData();
                showTermAndConditionActivity(termsData,"Terms Of Service");
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.Blue_Text));
            }
        };

        s1.setSpan(clickableSpan, 6,s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkBoxAcceptService.setText(s1);
        checkBoxAcceptService.setMovementMethod(LinkMovementMethod.getInstance());
        checkBoxAcceptService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   Constants.ACCEPT_TERM_SERVICE=true;
                }else {
                    Constants.ACCEPT_TERM_SERVICE=false;
                }
            }
        });


        checkBoxIamRobot=(AppCompatCheckBox)findViewById(R.id.signup_iamrobot);
        checkBoxIamRobot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Constants.LOGIN_OR_SIGNUP="signup";
                    callRecaptcha();
                }
            }
        });

        btnCreateAccount=(AppCompatButton)findViewById(R.id.signup_create_account);
        btnCreateAccount.setOnClickListener(this);


    }

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


    private void verifyUserNameAvailabilty() {
        String username=edit_Username.getText().toString();

        Call<StatuTitleMessageResponse> usernameCall=apiInterface.getUserNameValidate(username);
        usernameCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (response.isSuccessful()){
                    edit_Username.setError("username already exist");
                    edit_Username.requestFocus();
                    progressDialog.dismiss();
                }
                else {
                    if (response.code()==404){
                        Gson gson = new GsonBuilder().create();

                        StatuTitleMessageResponse stm=new StatuTitleMessageResponse();
                        try {
                            stm = gson.fromJson(response.errorBody().string(), StatuTitleMessageResponse.class);
                            if (stm.getMessage().equals("User is not exists")){
                               // Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                verifyEmailExist();
                            }
                        } catch (IOException e) {
                            Log.e("Error code:",e.getMessage());
                            progressDialog.dismiss();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
               showSnackbar("Error :"+t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void verifyEmailExist() {
        String email=edit_Email.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_Email.setError("Enter valid email");
            edit_Email.requestFocus();
            progressDialog.dismiss();
            return;
        }
        Call<StatuTitleMessageResponse> emailCall=apiInterface.getUserEmailsValidate(email);
        emailCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    edit_Email.setError("email already exist");
                    edit_Email.requestFocus();
                    progressDialog.dismiss();
                } else {
                    if (response.code() == 404) {
                        Gson gson = new GsonBuilder().create();

                        StatuTitleMessageResponse stm = new StatuTitleMessageResponse();
                        try {
                            stm = gson.fromJson(response.errorBody().string(), StatuTitleMessageResponse.class);
                            if (stm.getMessage().equals("User is not exists")) {
                               registrationUser();
                            }
                        } catch (IOException e) {
                            Log.e("Error code:", e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error "+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void callRecaptcha() {
        SafetyNet.getClient(this).verifyWithRecaptcha(Constants.SITE_KEY)
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {

                        if (!response.getTokenResult().isEmpty()) {
                           // Toast.makeText(SignupActivity.this, "Token :"+response.getTokenResult(), Toast.LENGTH_SHORT).show();
                            Util util=new Util();
                            util.verifyTokenOnServer(SignupActivity.this,response.getTokenResult());

                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.d(TAG, "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                            //Toast.makeText(SignupActivity.this, "Error :"+CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
                        }
                    }
                });
             }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.signup_create_account:
                callIntent();
                break;
           /* case R.id.signup_role:
                Constants.SIGNUP=true;
                Constants.ADD_DAILY_LOG=false;
                showAlertDialog();
                break;*/

        }

    }

    private void showAlertDialog() {
        if (roleItems.size()>0) {
            CustomDialogForSpinner customDialogForSpinner = new CustomDialogForSpinner(this, "Role", roleItems);
            customDialogForSpinner.ShowSpinnerDialog();
        }else {
            //Toast.makeText(this, "No Data Available try again.", Toast.LENGTH_SHORT).show();
            showSnackbar("No Data Available try again.");
        }
    }

    private void callIntent() {
        String firstname=edit_Firstname.getText().toString();
        String lastname=edit_Lastname.getText().toString();
        final String username=edit_Username.getText().toString();
        String password=edit_Password.getText().toString();
        String email=edit_Email.getText().toString();
        String mobile=edit_Mobile.getText().toString();

        String sign=SignupActivity.mRoleSpinner.getText().toString().trim();
        if (sign.equals("Role")){
            mRoleSpinner.setError("Select role type");
            mRoleSpinner.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(firstname)){
            edit_Firstname.setError("Enter first name");
            edit_Firstname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastname)){
            edit_Lastname.setError("Enter last name");
            edit_Lastname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username)){
            edit_Firstname.setError("Enter username");
            edit_Username.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            edit_Password.setError("Enter password");
            edit_Password.requestFocus();
            return;
        }
        if (password.length()<8){
            edit_Password.setError("Password contains atleast 8 characters or more");
            edit_Password.requestFocus();
            return;
        }
        if (!passwordCharValidation(password)){
            edit_Password.setError("Password must contains atleast,1 Special character,1 Number,and 1 Uppercase letter");
            edit_Password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)){
            edit_Email.setError("Enter email");
            edit_Email.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_Email.setError("Enter valid email");
            edit_Email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mobile)){
            edit_Mobile.setError("Enter mobile");
            edit_Mobile.requestFocus();
            return;
        }
        if (mobile.length()<10){
            edit_Mobile.setError("Enter 10 digit mobile number");
            edit_Mobile.requestFocus();
            return;
        }

        if (!Constants.SIGNUP_RECAPTCHA_CHECK){
            Toast.makeText(this, "Please verify Recaptcha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Constants.ACCEPT_TERM_SERVICE){
            Toast.makeText(this, "Accept term and service.", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        verifyUserNameAvailabilty();


        newUser=new NewUser();
        newUser.setFirstName(firstname);
        newUser.setLastName(lastname);
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setMobileNo(mobile);
        newUser.setConfirmPassword("null");
        newUser.setRoleId("0");

        for(int i=0;i<roleMap.size();i++){
            if (roleMap.containsKey(sign)){
                String logicName=roleMap.get(sign);
                newUser.setLogicName(logicName);
            }
        }

    }

    public void registrationUser(){

        Call<StatuTitleMessageResponse> newUserRegistrtionCall=apiInterface.insertRegistration(newUser);
        newUserRegistrtionCall.enqueue(new Callback<StatuTitleMessageResponse>() {
            @Override
            public void onResponse(Call<StatuTitleMessageResponse> call, Response<StatuTitleMessageResponse> response) {
                if (response.isSuccessful()) {
                       // Toast.makeText(SignupActivity.this, "User register successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        createSessionTokenCall();

                }else {
                    if (response.code()==404){
                        progressDialog.dismiss();
                       // Toast.makeText(SignupActivity.this, "Unable to register user", Toast.LENGTH_SHORT).show();
                        showSnackbar("Unable to register user");
                    }
                }
            }
            @Override
            public void onFailure(Call<StatuTitleMessageResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                callRecaptchaSetting();
                progressDialog.dismiss();
            }
        });
    }

    private void createSessionTokenCall() {
        User user=new User();
        user.setUserName(edit_Username.getText().toString());
        user.setPassword(edit_Password.getText().toString());

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
                                LoginResponse loginResponse=response.body();
                                progressDialog.dismiss();
                                Constants.USER_ROLE=loginResponse.getRoleName();
                                tokenManager.clearSession();
                                tokenManager.addUserDetails(loginResponse,token);
                                callNextActivity();
                            }else {
                                edit_Username.setError("Invalid Token");
                                edit_Username.requestFocus();
                                callRecaptchaSetting();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            showSnackbar("Error :"+t.getMessage());
                            callRecaptchaSetting();
                            progressDialog.dismiss();
                        }
                    });
                }else {
                   // Toast.makeText(SignupActivity.this,response.message(), Toast.LENGTH_SHORT).show();
                    edit_Username.setError("Authentication Failed. Username or Password not valid.");
                    edit_Username.requestFocus();
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
    private void callNextActivity(){
        Constants.MY_PROFILE=false;
        Constants.REGISTRATION_DETAIL=true;
        Constants.USER_ROLE=TokenManager.getUserDetailsPreference().getString(Constants.PREF_KEY_ROLE_NAME,null);
        Intent intent=new Intent(this,DetailsSubmissionActivity.class);
        startActivity(intent);
        finish();
    }


    public static boolean passwordCharValidation(String passwordEd) {
        String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(passwordEd);
      /*  if (!passwordEd.matches(".*\\d.*") || !matcher.matches()) {
            return true;
        }
        return false;*/
        return matcher.matches();
     }

    public void callRecaptchaSetting(){
        checkBoxIamRobot.setEnabled(true);
        checkBoxIamRobot.setChecked(false);
        Constants.LOGIN_RECAPTCHA_CHECK=false;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void showSnackbar(String msg){
        Snackbar snackbar= Snackbar.make(getCurrentFocus(),msg,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(this.getResources().getColor(R.color.Category_Platinum));
        TextView textView=snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
