package com.challenge.digbreeds.list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.digbreeds.list.domain.usecase.GetDogsWithBreedsUseCase
import com.challenge.digbreeds.list.domain.usecase.GetUrlImageFromBreedUseCase
import com.challenge.digbreeds.list.presentation.model.ListUiState
import com.challenge.dogbreeds.common.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val getDogsWithBreedsUseCase: GetDogsWithBreedsUseCase,
    private val getUrlImageFromBreedUseCase: GetUrlImageFromBreedUseCase
) : ViewModel() {

    private val _uiState by lazy { mutableStateOf<ListUiState>(ListUiState.Loading) }
    internal val uiState: State<ListUiState> by lazy { _uiState }

    private val imageUpdateFlow = MutableSharedFlow<String>()

    init {
        refreshList()

        viewModelScope.launch {
            imageUpdateFlow.collect{ breedId ->
                fetchUrlImage(breedId)
            }
        }
    }

    fun refreshList() {
        viewModelScope.launch {
            fetchDogs()
        }
    }

    private suspend fun fetchDogs() {
        _uiState.value = ListUiState.Loading

        withContext(Dispatchers.IO){
            when (val result = getDogsWithBreedsUseCase()){
                is Result.Error -> {
                    _uiState.value = ListUiState.Error(result.throwable.message)
                }
                is Result.Success -> {
                    when {
                        result.data.isEmpty() -> {
                            _uiState.value = ListUiState.Empty
                        }
                        else -> {
                            _uiState.value = ListUiState.Result(result.data)
                        }
                    }
                }
            }
        }
    }

    fun enqueueFetchImageUrl(breedId : String) {
        viewModelScope.launch {
            imageUpdateFlow.emit(breedId)
        }
    }

    private suspend fun fetchUrlImage(breedId : String) {
        withContext(Dispatchers.IO) {
            when (val result = getUrlImageFromBreedUseCase(breedId)) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    val uiState = uiState.value
                    if (uiState is ListUiState.Result) {
                        _uiState.value = uiState.copy(
                            dogs = uiState.dogs.map { dog ->
                                if (dog.id == breedId) {
                                    dog.copy(imageUrl = result.data)
                                } else {
                                    dog.copy(subBreeds = dog.subBreeds.map { subBreed ->
                                        if (subBreed.id == breedId) {
                                            subBreed.copy(imageUrl = result.data)
                                        }
                                        else{
                                            subBreed
                                        }
                                    })
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

