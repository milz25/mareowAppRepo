package com.mareow.recaptchademo.FCMessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifyServiceReceiver extends BroadcastReceiver {

    int idAplication;
    @Override
    public void onReceive(Context context, Intent intent) {
        //idAplication = intent.getIntExtra(Constants., idAplication);
         /*if (intent.getAction().equals(Constants.NOTIFICATION_ACTION)){
             createNotification(context, "some title", "some message text", "some sticker");
         }*/

       // Log.e("TESTING", "the id is " + String.valueOf(idAplication));
        context.startService(new Intent(context, NotifyService.class));
     /*   if (intent.getAction().equals(Constants.NOTIFICATION_ACTION)){

        }*/

    }


   /* public void createNotification(Context context, String title, String msgText, String sticker)
    {

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 *//* Request code *//*, intent,PendingIntent.FLAG_ONE_SHOT);

        String channelId = context.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle(context.getString(R.string.fcm_message))
                        .setContentText(msgText)
                        .setTicker(sticker)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/

}
