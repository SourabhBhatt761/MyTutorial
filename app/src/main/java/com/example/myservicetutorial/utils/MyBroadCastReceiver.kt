package com.example.myservicetutorial.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myservicetutorial.services.MyForegroundService

class MyBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            //Foreground service
            val intent2 = Intent(p0, MyForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !MyForegroundService.isServiceRunning) {
                p0?.startForegroundService(intent2)
            }

        } else if (p1?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Toast.makeText(p0, "Boot completed", Toast.LENGTH_SHORT).show()

        } else if (p1?.action.equals(Intent.ACTION_REBOOT)) {
            Toast.makeText(p0, "Reboot", Toast.LENGTH_SHORT).show()

        } else if (p1?.action.equals(Intent.ACTION_BATTERY_LOW)) {
            Toast.makeText(p0, "Battery Low", Toast.LENGTH_SHORT).show()

        } else if (p1?.action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            Toast.makeText(p0, "Air plane", Toast.LENGTH_SHORT).show()

        } else if (p1?.action.equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(p0, "Charging", Toast.LENGTH_SHORT).show()

        } else if (p1?.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(p0, "Charging removed", Toast.LENGTH_SHORT).show()

        }
    }
}


