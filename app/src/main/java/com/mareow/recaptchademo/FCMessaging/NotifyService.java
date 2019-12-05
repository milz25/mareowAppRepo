package com.mareow.recaptchademo.FCMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.os.Handler;

import com.mareow.recaptchademo.Activities.MainActivity;
import com.mareow.recaptchademo.Activities.RenterMainActivity;
import com.mareow.recaptchademo.DataModels.Notification;
import com.mareow.recaptchademo.R;
import com.mareow.recaptchademo.Retrofit.ApiClient;
import com.mareow.recaptchademo.Retrofit.ApiInterface;
import com.mareow.recaptchademo.TokenManagers.TokenManager;
import com.mareow.recaptchademo.Utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyService extends Service {
    NotifyServiceReceiver notifyServiceReceiver;

   // private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    protected Toast mToast;
    List<Notification> notificationList=new ArrayList<>();
    private final int UPDATE_INTERVAL = 2000*60;

    Context context;
    public NotifyService(Context context) {
        super();
        this.context=context;
    }

    public NotifyService(){

    }

   /* public class LocalBinder extends Binder {
        public NotifyService getService() {
            return NotifyService .this;
        }
    }*/

    @Override
    public void onCreate() {

      /*  notifyServiceReceiver = new NotifyServiceReceiver();

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Constants.NOTIFICATION_ACTION);
        this.registerReceiver(notifyServiceReceiver,intentFilter);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

           //startForeground(1,);
        }

        super.onCreate();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

      /*  handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               // callGetNotificationApi();
                Intent intent=new Intent();
                intent.setAction(Constants.NOTIFICATION_ACTION);
                sendBroadcast(intent);
            }
        },UPDATE_INTERVAL);*/

        super.onStartCommand(intent,flags,startId);
        startTimer();
        return START_NOT_STICKY;

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
        timer.schedule(timerTask, 0, UPDATE_INTERVAL); //
    }


    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
               // Log.i("in timer", "in timer ++++  "+ (counter++));
                createNotification(context, "some title", "some message text", "some sticker");
            }
        };
    }


    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void callGetNotificationApi() {
        String token= TokenManager.getSessionToken();
        int partyId=TokenManager.getUserDetailsPreference().getInt(Constants.PREF_KEY_PARTY_ID,0);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<List<Notification>> callnoti=apiInterface.getNotificationList("Bearer "+token,partyId,20);
        callnoti.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {

                if (response.isSuccessful()){
                    notificationList=response.body();

                    Notification newNotification=new Notification();
                    for (int i=0;i<notificationList.size();i++){
                        if (notificationList.get(i).getRoleLogic().equals("Renter")){
                            newNotification=notificationList.get(i);
                            Intent intent=new Intent();
                            intent.setAction(Constants.NOTIFICATION_ACTION);
                            sendBroadcast(intent);

                        }
                    }

                }else {
                    Toast.makeText(NotifyService.this, "No Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(NotifyService.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i("EXIT", "ondestroy!");
       /* Intent intent=new Intent();
        intent.setAction(Constants.NOTIFICATION_ACTION);*/
        Intent broadcastIntent = new Intent(this, NotifyServiceReceiver.class);
        sendBroadcast(broadcastIntent);
       // sendBroadcast(intent);
        stoptimertask();
    }



    public void createNotification(Context context, String title, String msgText, String sticker)
    {
        Intent intent;
        if (Constants.USER_ROLE.equals("Renter")){
            intent = new Intent(this, RenterMainActivity.class);
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
