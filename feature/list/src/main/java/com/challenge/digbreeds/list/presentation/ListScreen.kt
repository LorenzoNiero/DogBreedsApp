package com.challenge.digbreeds.list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.challenge.digbreeds.list.R
import com.challenge.dogbreeds.ui.component.TopBar


@Composable
fun ListScreen (
    navController: NavHostController,
    viewModel: ListViewModel = hiltViewModel()
) {
    ListContent()
}

@Composable
private fun ListContent() {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.app_name),
                onBackClick = null,
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "none"
                        )
                    }
                }
            )
        }) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ListScreenPreview() {
    ListContent()
}

