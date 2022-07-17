package com.example.myservicetutorial.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyBackgroundService : Service() {

    private val TAG = "MyBackgroundService"

    //runs whenever we call startService() or startForegroundService()
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

        return super.onStartCommand(intent, flags, startId)
    }




    override fun onBind(p0: Intent?): IBinder? {
     return null;
    }


}