package com.example.myservicetutorial

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myservicetutorial.services.MyBackgroundService
import com.example.myservicetutorial.services.MyForegroundService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Background service
//        val intent1 = Intent(this,MyBackgroundService::class.java)
//        startService(intent1)

        //Foreground service
        val intent2 = Intent(this,MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent2)
        }
    }
}