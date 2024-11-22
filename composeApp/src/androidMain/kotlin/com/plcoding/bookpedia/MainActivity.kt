package com.plcoding.bookpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import okhttp3.OkHttp
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                engine = remember {
                    io.ktor.client.engine.okhttp.OkHttp.create()
                }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
   // App()
}