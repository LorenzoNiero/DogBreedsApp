package com.challenge.digbreeds.list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.digbreeds.list.presentation.model.ListUiState
import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.domain.usecase.EnqueueFetchImageUrlByBreedIdUseCase
import com.challenge.dogbreeds.domain.usecase.GetDogsWithBreedsUseCase
import com.challenge.dogbreeds.domain.usecase.ObserveDogsWithBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
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

    private val _errorMessageUiState by lazy { mutableStateOf<String?>(null) }
    internal val errorMessageUiState: State<String?> by lazy { _errorMessageUiState }

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
            when (val result = getDogsWithBreedsUseCase()){
                is Result.Error -> {
                    _errorMessageUiState.value = result.throwable.message
                }
                is Result.Success -> {
                    _errorMessageUiState.value = null
                }
            }
        }
    }

    fun enqueueFetchImageUrl(breedId: String, subBreedId : String?) {
        viewModelScope.launch {
            enqueueFetchImageUrlByBreedIdUseCase(breedId,subBreedId)
        }
    }

}

