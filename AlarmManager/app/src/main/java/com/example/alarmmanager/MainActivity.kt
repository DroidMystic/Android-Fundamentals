package com.example.alarmmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alarmmanager.ui.theme.AlarmManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val alarmManager = AndroidAlarmManager(this)
        setContent {
            AlarmManagerTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = {
                            alarmManager.scheduleAlarm()
                        }){
                        Text(text = "Start Alarm")
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            alarmManager.cancelAlarm()
                        }){
                        Text(text = "Cancel Alarm")
                    }
                }
            }
        }
    }
}

