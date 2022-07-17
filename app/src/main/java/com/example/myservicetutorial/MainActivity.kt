package com.example.myservicetutorial

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myservicetutorial.databinding.ActivityMainBinding
import com.example.myservicetutorial.services.MyForegroundService
import com.example.myservicetutorial.utils.MyBroadCastReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myBroadCastReceiver: MyBroadCastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (MyForegroundService.isServiceRunning){
            binding.serviceText.text = getString(R.string.service_is_running)
            binding.serviceButton.text = getString(R.string.stop_service)
        }else{
            binding.serviceText.text = getString(R.string.welcome)
            binding.serviceButton.text = getString(R.string.start_service)
        }


        registerMyReceiver()

        clickListener()
    }

    private fun registerMyReceiver() {
        myBroadCastReceiver = MyBroadCastReceiver()

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(myBroadCastReceiver,it)
        }
        IntentFilter(Intent.ACTION_REBOOT).also {
            registerReceiver(myBroadCastReceiver,it)
        }
        IntentFilter(Intent.ACTION_BOOT_COMPLETED).also {
            registerReceiver(myBroadCastReceiver,it)
        }
        IntentFilter(Intent.ACTION_BATTERY_LOW).also {
            registerReceiver(myBroadCastReceiver,it)
        }
        IntentFilter(Intent.ACTION_POWER_DISCONNECTED).also {
            registerReceiver(myBroadCastReceiver,it)
        }
        IntentFilter(Intent.ACTION_POWER_CONNECTED).also {
            registerReceiver(myBroadCastReceiver,it)
        }
    }

    private fun clickListener() {

        binding.serviceButton.setOnClickListener {
            if (MyForegroundService.isServiceRunning){
                stopMyService()
            }else{
                startMyService()
            }
        }

    }

    private fun stopMyService() {
        binding.serviceText.text = getString(R.string.welcome)
        binding.serviceButton.text = getString(R.string.start_service)

        stopService(Intent(this,MyForegroundService::class.java))

    }

    private fun startMyService() {
        //Background service
//        val intent1 = Intent(this,MyBackgroundService::class.java)
//        startService(intent1)

        //Foreground service
        val intent2 = Intent(this,MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !MyForegroundService.isServiceRunning) {
            startForegroundService(intent2)
        }

        binding.serviceText.text = getString(R.string.service_is_running)
        binding.serviceButton.text = getString(R.string.stop_service)

    }
}