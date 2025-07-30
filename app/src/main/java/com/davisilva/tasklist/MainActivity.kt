package com.davisilva.tasklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.davisilva.tasklist.navigation.TaskNavHost
import com.davisilva.tasklist.ui.theme.TaskListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier.safeDrawingPadding()
            ){
                TaskListTheme {
                    TaskNavHost()
                }
            }
        }
    }
}

