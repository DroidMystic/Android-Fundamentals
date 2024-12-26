package com.example.permissionhandling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.permissionhandling.ui.theme.PermissionHandlingTheme
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionHandlingTheme {

                val showDialog =
                    mainViewModel.showDialog.collectAsState().value

                val launchAppSettings =
                    mainViewModel.launchAppSettings.collectAsState().value

                val permissionsResultActivityLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { result ->
                        permissions.forEach { permission ->
                            if(result[permission] == false){
                                if(!shouldShowRequestPermissionRationale(permission)){
                                    mainViewModel.updateLaunchAppSettings(true)
                                }
                                mainViewModel.updateShowDialog(true)

                            }                            }

                        }

                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){

                    Button(onClick = {
                        permissions.forEach { permission ->
                            val isGranted = checkSelfPermission(permission) ==
                                    PackageManager.PERMISSION_GRANTED
                            if(!isGranted){
                                if(shouldShowRequestPermissionRationale(permission)){
                                    mainViewModel.updateShowDialog(true)
                                }else{
                                    permissionsResultActivityLauncher.launch(permissions)
                                }
                            }
                        }

                    }){
                        Text(text="Request Permission")
                    }
                }

                if(showDialog){
                    PermissionDialog(
                        onDismiss = {
                            mainViewModel.updateShowDialog(false)
                        },
                        onConfirm ={
                            mainViewModel.updateShowDialog(false)

                            if(launchAppSettings){
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package",packageName,null)
                                ).also{
                                    startActivity(it)
                                }
                                mainViewModel.updateLaunchAppSettings(false)
                            }else{
                                permissionsResultActivityLauncher.launch(permissions)
                        }
                    }
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    onDismiss:() -> Unit,
    onConfirm:() -> Unit
){
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "Confirm")
            }
        },
        title = {
            Text(text = "Camera and Microphone permissions",
                fontWeight = FontWeight.SemiBold
            )
        },
        text= {
            Text(text = "This app needs camera and microphone permissions to work properly"
            )
        }
    )
}
