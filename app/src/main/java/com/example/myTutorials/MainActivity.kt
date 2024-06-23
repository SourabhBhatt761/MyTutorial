package com.example.myTutorials

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myTutorials.broadcastReceiver.MyBroadCastReceiver
import com.example.myTutorials.databinding.ActivityMainBinding
import com.example.myTutorials.services.MyServiceActivity
import com.example.myTutorials.webview.WebViewActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myBroadCastReceiver: MyBroadCastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        clickListener()
    }

    private fun initUI() {
        myBroadCastReceiver = MyBroadCastReceiver()
    }

    private fun clickListener() {

        binding.serviceBtn.setOnClickListener {
            startActivity(Intent(this, MyServiceActivity::class.java))
        }

        binding.broadcastReceiver.setOnClickListener {
            Toast.makeText(this,"Broadcast receivers attached !!",Toast.LENGTH_LONG).show()
            registerMyReceivers()
        }

        binding.webViewBtn.setOnClickListener {
            startActivity(Intent(this,WebViewActivity::class.java))
        }
    }

    private fun registerMyReceivers() {

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            ContextCompat.registerReceiver(
                this,
                myBroadCastReceiver,
                it,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
        IntentFilter(Intent.ACTION_REBOOT).also {
            ContextCompat.registerReceiver(
                this,
                myBroadCastReceiver,
                it,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
        IntentFilter(Intent.ACTION_BOOT_COMPLETED).also {
            ContextCompat.registerReceiver(
                this,
                myBroadCastReceiver,
                it,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
        IntentFilter(Intent.ACTION_BATTERY_LOW).also {
            ContextCompat.registerReceiver(
                this,
                myBroadCastReceiver,
                it,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
        IntentFilter(Intent.ACTION_POWER_DISCONNECTED).also {
            ContextCompat.registerReceiver(
                this,
                myBroadCastReceiver,
                it,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
        IntentFilter(Intent.ACTION_POWER_CONNECTED).also {
            ContextCompat.registerReceiver(
                this,
                myBroadCastReceiver,
                it,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadCastReceiver)
    }

}