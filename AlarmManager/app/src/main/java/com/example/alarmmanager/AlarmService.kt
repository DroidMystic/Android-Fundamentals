package com.example.alarmmanager

import android.content.Intent
import android.os.IBinder
import android.app.Service
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi

class AlarmService: Service() {
    override fun onBind(intent: Intent?): IBinder?{
        return null
    }

    @OptIn(UnstableApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AlarmReceiver", "Alarm received")
        return super.onStartCommand(intent, flags, startId)
    }
}