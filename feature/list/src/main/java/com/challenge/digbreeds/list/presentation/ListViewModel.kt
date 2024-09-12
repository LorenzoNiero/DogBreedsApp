package com.challenge.digbreeds.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.digbreeds.list.presentation.model.ListUiState
import com.challenge.dogbreeds.domain.usecase.EnqueueFetchImageUrlByBreedIdUseCase
import com.challenge.dogbreeds.domain.usecase.GetDogsWithBreedsUseCase
import com.challenge.dogbreeds.domain.usecase.ObserveDogsWithBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val getDogsWithBreedsUseCase: GetDogsWithBreedsUseCase,
    private val enqueueFetchImageUrlByBreedIdUseCase: EnqueueFetchImageUrlByBreedIdUseCase,
    observeDogsWithBreedsUseCase: ObserveDogsWithBreedsUseCase
) : ViewModel() {


    val dogsUIState = observeDogsWithBreedsUseCase().map {
        if (it.isEmpty()) {
            ListUiState.Empty
        } else {
            ListUiState.Result(it)
        }
    }.catch {
        ListUiState.Error("${it.message}")
    }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = ListUiState.Loading
    )

    init {
        loadList()
    }

    fun loadList() {
        viewModelScope.launch {
            fetchDogs()
        }
    }

    private suspend fun fetchDogs() {
        withContext(Dispatchers.IO) {
            getDogsWithBreedsUseCase()
        }
    }

    fun enqueueFetchImageUrl(breedId: String) {
        viewModelScope.launch {
            enqueueFetchImageUrlByBreedIdUseCase(breedId)
        }
    }

}

