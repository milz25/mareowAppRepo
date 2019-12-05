package com.mareow.recaptchademo.FCMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.OwnerMainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Utils.Constants;

import java.util.Timer;
import java.util.TimerTask;


public class MyJobIntentService extends JobIntentService {


    final Handler mHandler = new Handler();
    private final int UPDATE_INTERVAL = 1000*60;

    private static final String TAG = "MyJobIntentService";
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 2;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, MyJobIntentService.class, JOB_ID, intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //showToast("Job Execution Started");
        Toast.makeText(this, "Job Started", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        startTimer();
    }


    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000*60, UPDATE_INTERVAL); //
    }


    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                // Log.i("in timer", "in timer ++++  "+ (counter++));
                createNotification("some title", "some message text", "some sticker");
            }
        };
    }



    public void createNotification(String title, String msgText, String sticker)
    {
        Intent intent;
        if (Constants.USER_ROLE.equals("Renter")){
            intent = new Intent(this, RenterMainActivity.class);
        }else if(Constants.USER_ROLE.equals("Owner")){
            intent = new Intent(this, OwnerMainActivity.class);
        }else {
            intent = new Intent(this, MainActivity.class);
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,PendingIntent.FLAG_ONE_SHOT);

        String channelId = this.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(this.getString(R.string.fcm_message))
                .setContentText(msgText)
                .setTicker(sticker)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
