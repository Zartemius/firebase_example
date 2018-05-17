package com.example.artem.firstappwithnotification.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.example.artem.firstappwithnotification.MainActivity;
import com.example.artem.firstappwithnotification.R;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class OrdersMessagingService extends FirebaseMessagingService {

    private static final String TAG = "OrdersMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "ReceivedMessage" + remoteMessage.getFrom());

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Message data" + remoteMessage.getData());

            scheduleJob(remoteMessage.getData());

        }
        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "Message notification" +
                    remoteMessage.getNotification().getBody());

            sendNotification(remoteMessage.getNotification());
        }
    }

    private void scheduleJob(Map <String,String> data){

        Bundle bundle = new Bundle();

        for(Map.Entry<String,String> entry : data.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }

        FirebaseJobDispatcher dispatcher =
                new FirebaseJobDispatcher(new GooglePlayDriver(this));

        Job myJob = dispatcher.newJobBuilder()
                .setService(OrdersJobService.class)
                .setTag("order-job")
                .setExtras(bundle)
                .build();

        dispatcher.schedule(myJob);
    }

    private void sendNotification(RemoteMessage.Notification notification){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(this,"fcm-channel")
                .setSmallIcon(R.drawable.user)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0,notificationBuilder.build());
    }
}
