package com.challenge.digbreeds.list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.challenge.digbreeds.list.R
import com.challenge.digbreeds.list.presentation.model.ListUiState
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.ui.component.DogCell
import com.challenge.dogbreeds.ui.component.TopBar
import com.challenge.dogbreeds.ui.R as R_UI

@Composable
fun ListScreen (
    navController: NavHostController,
    viewModel: ListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    ListContent(
        uiState = uiState,
        onRefresh = { viewModel.refresh() },
        getImageUrl = { viewModel.loadImage(it) }
    )
}

@Composable
private fun ListContent(
    uiState: ListUiState,
    onRefresh: () -> Unit,
    getImageUrl: suspend (String)-> Unit = {}
    )
{
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.app_name),
                onBackClick = null,
                actions = {
                    IconButton(onClick = {
                        onRefresh()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "none"
                        )
                    }
                }
            )
        }) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                ListUiState.Empty -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                        ,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_list_message)
                    )
                }
                is ListUiState.Error -> {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = dimensionResource(R_UI.dimen.padding_list_vertical)),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.error_label, uiState.message ?: "")
                    )
                }
                ListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ListUiState.Result -> {
                    val snapshotStateMapSaver = Saver<SnapshotStateMap<Int, Boolean>, List<Pair<Int,Boolean>>>(
                        save = { snapshotStateMap ->
                            snapshotStateMap.entries.map { it.key to it.value }
                        },
                        restore = { list ->
                            mutableStateMapOf(*list.toTypedArray())
                        }
                    )

                    val isExpandedMap = rememberSaveable (saver =snapshotStateMapSaver) {
                        mutableStateMapOf()
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(
                            vertical =  dimensionResource(R_UI.dimen.padding_list_vertical),
                            horizontal =  dimensionResource(R_UI.dimen.padding_list_horizontal)
                        )
                    ) {
                        uiState.dogs.forEachIndexed{ index, dog ->
                            accordion(
                                dog = dog,
                                isExpanded = isExpandedMap[index] ?: false,
                                onHeaderClick = {
                                    isExpandedMap[index] = !(isExpandedMap[index] ?: true)
                                },
                                getImageUrl = getImageUrl
                            )
                        }
                    }
                }
            }
        }
    }
}

fun LazyListScope.accordion(
    dog: Dog,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit,
    getImageUrl: suspend (String)-> Unit
) {
    item {
        DogCell(
            dog,
            modifier = Modifier.fillMaxWidth(),
            onClick = onHeaderClick,
            getImageUrl = getImageUrl
        )
    }

    if(isExpanded) {
        items(dog.subBreeds) { subBreed ->
            Text(subBreed)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ListScreenPreview() {
    ListContent(
        uiState = ListUiState.Result(listOf(Dog("DogName", listOf("subBreed1", "subBreed2"), null))),
        onRefresh = {}
    )
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun ListScreen_Loading_Preview() {
//    ListContent(
//        uiState = ListUiState.Loading,
//        onRefresh = {}
//    )
//}
//
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun ListScreen_Error_Preview() {
//    ListContent(
//        uiState = ListUiState.Error("message error"),
//        onRefresh = {}
//    )
//}
//
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//private fun ListScreen_Empty_Preview() {
//    ListContent(
//        uiState = ListUiState.Empty,
//        onRefresh = {}
//    )
//}

