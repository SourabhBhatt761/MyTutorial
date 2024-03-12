package com.example.myTutorials.services

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast

class MyBackgroundService : Service() {

    private val TAG = "MyBackgroundService"

    companion object {
        var isServiceRunning : Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        isServiceRunning = true;
    }

    //runs whenever we call startService() or startForegroundService()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread {
            while (isServiceRunning) {
                Log.e(TAG, "Service is running...")
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this, "BG service toast", Toast.LENGTH_SHORT).show()
                }

                try {
                    Thread.sleep(4000)
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

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false;
    }
}