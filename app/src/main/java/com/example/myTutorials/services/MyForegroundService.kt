package com.example.myTutorials.services

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myTutorials.MainActivity
import com.example.myTutorials.R

class MyForegroundService : Service() {

    private val CHANNEL_ID = "channelId"
    private val CHANNEL_Name = "channelName"
    private val TAG = "MyForegroundService"
    private lateinit var pendingIntent : PendingIntent

    //in java we use  `static` , in kotlin we use `companion object`
    //public static boolean isServiceRunning = true
    companion object {
        var isServiceRunning : Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        isServiceRunning = true
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread {
            while (isServiceRunning) {
                Log.e(TAG, "Service is running...")
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()


        //creating pending intent, so on click of the notification we can open the mainActivity class
        val myIntent = Intent(this,MainActivity::class.java)
        pendingIntent = PendingIntent.getActivity(this,1001,myIntent,PendingIntent.FLAG_IMMUTABLE)
        createNotificationChannel()

        startForeground(1111, notification())

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return  null
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


            //both the methods below are correct.
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

            // Register the channel with the system
//            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
        }
    }



    private fun notification() : Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notification Title")
            //text will be displayed only of one line ,if setStyle is there this won't be shown
            .setContentText("Notification text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_launcher_foreground))
            //text can be displayed of multiple lines
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("this is big text following the second line as well let's see what happens")
            )
            //sets the notification to top if two notifications comes at the same time and the other one has low priority
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            //removes the notification  when the user taps it
            //not applicable for services as notifications will not disappear on click
//            .setAutoCancel(true)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
    }
}