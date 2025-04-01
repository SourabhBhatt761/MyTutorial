package com.example.myTutorials

import android.app.SearchManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myTutorials.bottomNav.BottomNavigationActivity
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
        Log.i("ActivityA","onCreate() called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("ActivityA","onRestart() called")
    }

    override fun onStart() {
        super.onStart()
        Log.i("ActivityA","onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ActivityA","onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("ActivityA","onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ActivityA","onStop() called")
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

//            val webIntent = Intent(Intent.ACTION_WEB_SEARCH);
//            webIntent.putExtra(SearchManager.QUERY, "iShowSpeed");
//            startActivity(webIntent);

            startActivity(Intent(this,WebViewActivity::class.java))
        }

        binding.bottomNavBtn.setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
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
        Log.i("ActivityA","onDestroy() called")
    }

}


//      Output for Activity Lifecycle
//
//      ActivityA             onCreate() called
//		ActivityA             onStart() called
//		ActivityA             onResume() called
//
//			button clicked
//		ActivityA             onPause() called
//		ActivityB             onCreate() called
//		ActivityB             onStart() called
//		ActivityB             onResume() called
//		ActivityA             onStop() called
//
//			back button pressed
//		ActivityB             onPause() called
//		ActivityA             onRestart() called
//		ActivityA             onStart() called
//		ActivityA             onResume() called
//		ActivityB             onStop() called
//		ActivityB             onDestroy() called