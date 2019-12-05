package com.mareow.recaptchademo.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import android.app.AlertDialog;

public class AppUpdateChecker {


    private Activity activity;
    public AppUpdateChecker(Activity activity) {
        this.activity = activity;
    }
    //current version of app installed in the device
    private String getCurrentVersion(){
        PackageManager pm = activity.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(activity.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return pInfo.versionName;
    }
    private class GetLatestVersion extends AsyncTask<String, String, String> {
        private String latestVersion;
        private ProgressDialog progressDialog;
        private boolean manualCheck;
        GetLatestVersion(boolean manualCheck) {
            this.manualCheck = manualCheck;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (manualCheck)
            {
                if (progressDialog!=null)
                {
                    if (progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }
            }
            String currentVersion = getCurrentVersion();
            //If the versions are not the same
            if(!currentVersion.equals(latestVersion)&&latestVersion!=null){
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("An Update is Available");
                builder.setMessage("Its better to update now");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Click button action
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+activity.getPackageName())));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel button action
                    }
                });
                builder.setCancelable(false);
                if (!activity.isFinishing()){
                    builder.show();
                }

            }else {
                if (manualCheck) {
                    Toast.makeText(activity, "No Update Available", Toast.LENGTH_SHORT).show();
                }
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (manualCheck) {
                progressDialog=new ProgressDialog(activity);
                progressDialog.setMessage("Checking For Update.....");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            try {
               /* //It retrieves the latest version by scraping the content of current version from play store at runtime
                latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();

                Document jsonObject=Jsoup.connect("https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                latestVersion=jsonObject.nodeName();
                return latestVersion;*/
                    Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "&hl=en")
                            .timeout(30000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get();

                if(document != null) {
                    if(document.select(".hAyfc .htlgb").get(5) != null) {
                        latestVersion = document.select(".hAyfc .htlgb").get(5).ownText();
                    } else {
                        latestVersion = "0.1";
                    }
                }

                    /*if (document != null) {
                        Elements element = document.getElementsContainingOwnText("Current Version");
                        for (Element ele : element) {
                            if (ele.siblingElements() != null) {
                                Elements sibElemets = ele.siblingElements();
                                for (Element sibElemet : sibElemets) {
                                    latestVersion = sibElemet.text();
                                }
                            }
                        }
                    }
*/
                    return latestVersion;
            } catch (Exception e) {
                return latestVersion;
            }

        }
    }
    public void checkForUpdate(boolean manualCheck)
    {
        new GetLatestVersion(manualCheck).execute();
    }
}

