package com.challenge.dogbreeds.detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.challenge.dogbreeds.ui.component.TopBar

@Composable
fun DetailScreen (
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    DetailContent(
        title = viewModel.breedId,
        navController = navController
    )
}

@Composable
fun DetailContent(
    title: String?,
    navController: NavHostController? = null
) {
    Scaffold(
        topBar = {
            TopBar(
                title = title ?: "n.d.",
                onBackClick = {
                    navController?.popBackStack()
                }
            )
        }) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}
