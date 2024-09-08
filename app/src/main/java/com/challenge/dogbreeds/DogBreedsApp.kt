package com.challenge.dogbreeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.challenge.dogbreeds.ui.theme.DogBreedsTheme

@Composable
fun DogBreedsApp() {
    DogBreedsTheme {

        val navController = rememberNavController()
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Text(text = "Hello")

        }
    }

}