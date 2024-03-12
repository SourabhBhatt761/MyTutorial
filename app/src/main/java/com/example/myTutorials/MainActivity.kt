package com.example.myTutorials

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myTutorials.databinding.ActivityMainBinding
import com.example.myTutorials.services.MyForegroundService
import com.example.myTutorials.services.MyServiceActivity
import com.example.myTutorials.utils.MyBroadCastReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickListener()
    }

    private fun clickListener() {

        binding.serviceBtn.setOnClickListener {
            startActivity(Intent(this, MyServiceActivity::class.java))
        }
    }

}