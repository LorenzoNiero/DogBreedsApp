package com.challenge.dogbreeds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedsApp()
        }
    }

}

