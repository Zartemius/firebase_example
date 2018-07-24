package com.example.artem.firstappwithnotification.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.data.FireBaseDataBase
import com.example.artem.firstappwithnotification.mainscreen.MainScreen
import com.example.artem.firstappwithnotification.viewmodels.OrderScreenViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class OrderMessagingService: FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage!!.data.isNotEmpty()) {
            Log.d(TAG, "Message data" + remoteMessage.data)
        }

        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message data" + remoteMessage.notification!!.body)
            createAndSendNotificationToUser(remoteMessage.notification!!)

            FireBaseDataBase.loadOrders(OrderScreenViewModel.orders)
        }
    }

    private fun createAndSendNotificationToUser(notification:RemoteMessage.Notification){
        val intentMainScreen:Intent = Intent(this,MainScreen::class.java)

        intentMainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent:PendingIntent = PendingIntent.getActivity(
                this,0,intentMainScreen,PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder: NotificationCompat.Builder
                = NotificationCompat.Builder(this,"fcm-channel")
                .setSmallIcon(R.drawable.wrench)
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        val notificationManager:NotificationManager
                = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0,notificationBuilder.build())
    }
}