package com.example.myTutorials.services

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.myTutorials.R
import com.example.myTutorials.databinding.ActivityMyServiceBinding
import com.example.myTutorials.broadcastReceiver.MyBroadCastReceiver

class MyServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (MyForegroundService.isServiceRunning) {
            binding.serviceText.text = getString(R.string.service_is_running)
            binding.fgServiceButton.isEnabled = false
        } else {
            binding.serviceText.text = getString(R.string.welcome)
            binding.fgServiceButton.isEnabled = true
        }

        clickListener()
    }


    private fun clickListener() {

        //foreground service
        binding.fgServiceButton.setOnClickListener {
            if (MyForegroundService.isServiceRunning) {
                stopMyService()
            } else {
                startMyService()
            }
        }

        //Background service
        binding.bgServiceBtn.setOnClickListener {
            val intent1 = Intent(this, MyBackgroundService::class.java)
            startService(intent1)
            binding.bgServiceBtn.isEnabled = false
        }


        //stop service
        binding.stopServiceBtn.setOnClickListener {
            stopMyService()
        }
    }

    private fun stopMyService() {
        binding.serviceText.text = getString(R.string.welcome)
        binding.fgServiceButton.isEnabled = true
        binding.bgServiceBtn.isEnabled = true

        stopService(Intent(this, MyForegroundService::class.java))
        stopService(Intent(this, MyBackgroundService::class.java))
    }

    private fun startMyService() {

        //Foreground service
        val intent2 = Intent(this, MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !MyForegroundService.isServiceRunning) {
            startForegroundService(intent2)
        }

        binding.serviceText.text = getString(R.string.service_is_running)
        binding.fgServiceButton.isEnabled = false
    }
}