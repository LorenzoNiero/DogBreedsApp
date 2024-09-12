package com.challenge.dogbreeds.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.challenge.dogbreeds.ui.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    state: SavedStateHandle
) : ViewModel() {
    val breedId: String? = NavigationItem.Detail.getBreedId(state)

}