package com.example.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi

class AlarmReceiver: BroadcastReceiver() {
    @OptIn(UnstableApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "Alarm received")

    }

}