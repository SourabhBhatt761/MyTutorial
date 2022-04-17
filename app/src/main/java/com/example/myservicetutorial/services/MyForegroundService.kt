package com.example.myservicetutorial.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myservicetutorial.MainActivity
import com.example.myservicetutorial.R

class MyForegroundService : Service() {

    private val CHANNEL_ID = "channelId"
    private val CHANNEL_Name = "channelName"
    private val TAG = "MyForegroundService"


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread {
            while (true) {
                Log.e(TAG, "Service is running...")
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        createNotificationChannel()

        startForeground(1111, notification())

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return  null;
    }



    //to register the notification
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_Name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "hello there people"
                enableVibration(true)
                lightColor = Color.GREEN
                enableLights(true)
            }

            // Register the channel with the system
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }



    private fun notification() : Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Example")
            //text will be displayed only of one line ,if setStyle is there this won't be shown
            .setContentText("sample text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            //text can be displayed of multiple lines
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("this is big text following the second line as well let's see what happens")
            )
            //sets the notification to top if two notifications comes at the same time and the other one has low priority
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
//            .setContentIntent(pI)
            //removes the notification  when the user taps it
            .setAutoCancel(true)
            .build()
    }
}