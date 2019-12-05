package com.mareow.recaptchademo.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mareow.recaptchademo.Activities.LoginActivity;
import com.mareow.recaptchademo.Activities.SignupActivity;
import com.mareow.recaptchademo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;

public class Util {

    private static final String URL_VERIFY_ON_SERVER = "https://www.google.com/recaptcha/api/siteverify";
    private static final String TAG = "Util";

    RequestQueue queue;
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showDialogInternet(final Context context){

        new AlertDialog.Builder(context)
                .setTitle("Internet")
                .setMessage("Please check internet connection!")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void verifyTokenOnServer(final Context context, final String token){
        queue = Volley.newRequestQueue(context);
        StringRequest strReq = new StringRequest(Request.Method.POST,URL_VERIFY_ON_SERVER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) { Log.d(TAG, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                   // String message = jsonObject.getString("message");

                    if (success) {
                        // Congrats! captcha verified successfully on server
                        // TODO - submit the feedback to your server
                        //Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        if (Constants.LOGIN_OR_SIGNUP.equals("login")){
                            Constants.LOGIN_RECAPTCHA_CHECK=true;
                            LoginActivity.checkBox_IamRobot.setEnabled(false);
                        }else {
                            Constants.SIGNUP_RECAPTCHA_CHECK=true;
                            SignupActivity.checkBoxIamRobot.setEnabled(false);
                        }

                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Json Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
               // params.put("recaptcha-response", token);
                params.put("secret", Constants.SERVER_KEY);
                params.put("response", token);
                return params;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strReq);

       // MyApplication.getInstance().addToRequestQueue(strReq);

    }

    public static String convertYYYYddMMtoDDmmYYYY(String stringDate){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date inputdate = input.parse(stringDate);                 // parse input
            return output.format(inputdate).toString();              // format output
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertYYYYddMMtoMMMdd(String stringDate){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("MMM dd yyyy");
        try {
            Date inputdate = input.parse(stringDate);                 // parse input
            return output.format(inputdate).toString();              // format output
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String convertYYYYddMMtoDay(String stringDate){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd");
        try {
            Date inputdate = input.parse(stringDate);                 // parse input
            return output.format(inputdate).toString();              // format output
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void showFileErrorAlert(Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                //.setTitle("File Format Not Supported")
                 LayoutInflater newinInflater=LayoutInflater.from(context);
                 View view = newinInflater.inflate(R.layout.custom_title_alert_dialog, null);
                 AppCompatTextView titleText=(AppCompatTextView)view.findViewById(R.id.custom_title_text);
                 titleText.setText("File Format Not Supported");
                builder.setCustomTitle(view);
                builder.setMessage("Please select proper file format for this documents!");
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        dialog.dismiss();
                    }
                });
              builder.setIcon(R.drawable.ic_error);
              builder.show();
    }


}
