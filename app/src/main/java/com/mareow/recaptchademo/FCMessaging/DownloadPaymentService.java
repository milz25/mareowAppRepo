package com.mareow.recaptchademo.FCMessaging;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.mareow.recaptchademo.Adapters.InvoiceWithinPaymentAdapter;
import com.mareow.recaptchademo.DataModels.Download;
import com.mareow.recaptchademo.DataModels.ReceiptDTO;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class DownloadPaymentService extends IntentService {

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    private int totalFileSize;
    ArrayList<ReceiptDTO> receiptDTOArrayList=new ArrayList<>();
    ProgressDialog pDialog;
    String fileName=null;

    public DownloadPaymentService() {
        super("Download Payment Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW);

            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationBuilder = new NotificationCompat.Builder(this, "id")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setDefaults(0)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());


        Bundle bundle = intent.getExtras();
        receiptDTOArrayList=(ArrayList<ReceiptDTO>) bundle.getSerializable("payment");


        long msTime = System.currentTimeMillis();
        Date dt = new Date(msTime);
        String format = "yyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        fileName="Payment_"+receiptDTOArrayList.get(0).getPaymentReceiptNo()+"_"+sdf.format(dt).toString()+".pdf";

        initDownload();

    }

    private void initDownload(){

        String token=TokenManager.getSessionToken();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> request = apiInterface.downloadPaymentPDF("Bearer "+token,receiptDTOArrayList.get(0).getPaymentId());
      /*  request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    ResponseBody responseBody=response.body();
                }else {
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });*/
        try {
            //ResponseBody responseBody=request.execute().body();
            downloadFile(request.execute().body());
           // writeResponseBodyToDisk(request.execute().body());

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    private void downloadFile(ResponseBody body) throws IOException {

        if (body!=null) {
            int count;
            byte data[] = new byte[1024 * 4];
            long fileSize = body.contentLength();
            InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            OutputStream output = new FileOutputStream(outputFile);
            long total = 0;
            long startTime = System.currentTimeMillis();
            int timeCount = 1;
            while ((count = inputStream.read(data)) != -1) {

                total += count;
                int progress = (int) ((double) (total * 100) / (double) fileSize);

                totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                double current = Math.round(total / (Math.pow(1024, 2)));


                long currentTime = System.currentTimeMillis() - startTime;

                Download download = new Download();
                download.setTotalFileSize(totalFileSize);

                if (currentTime > 1000 * timeCount) {

               /* download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);*/
                    updateNotification(progress);

                    download.setCurrentFileSize((int) current);
                    download.setProgress(progress);
                    sendIntent(download);
                    timeCount++;
                }

                output.write(data, 0, count);
            }
            onDownloadComplete();
            output.flush();
            output.close();
            inputStream.close();
        }else {

            Constants.FAILD_TO_DOWNLOAD=true;
            Download download = new Download();
            download.setProgress(100);
            sendIntent(download);
            notificationManager.cancel(0);
        }

    }

    private void onDownloadComplete(){
        Constants.FAILD_TO_DOWNLOAD=false;
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("File Download Complete");
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendIntent(Download download){
        Intent intent = new Intent(InvoiceWithinPaymentAdapter.MESSAGE_PAYMENT);
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadPaymentService.this).sendBroadcast(intent);
    }

    private void updateNotification(int currentProgress) {

        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentText("Downloaded: " + currentProgress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;
            long total = 0;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(outputFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        onDownloadComplete();
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                    double current = Math.round(total / (Math.pow(1024, 2)));
                    int progress = (int) ((total * 100) / fileSize);
                    total=total+read;
                    fileSizeDownloaded += read;
                    Download download = new Download();
                    download.setTotalFileSize(totalFileSize);
                    download.setCurrentFileSize((int) current);
                    download.setProgress((int)progress);
                    sendIntent(download);

                    // Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}
