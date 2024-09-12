package com.challenge.digbreeds.list.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.digbreeds.list.presentation.model.ListUiState
import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.common.domain.entity.DogImageStatus
import com.challenge.dogbreeds.common.domain.entity.StatusImage
import com.challenge.dogbreeds.domain.usecase.GetDogsWithBreedsUseCase
import com.challenge.dogbreeds.domain.usecase.GetUrlImageFromBreedUseCase
import com.challenge.dogbreeds.domain.usecase.ObserveDogsWithBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
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
    private val getUrlImageFromBreedUseCase: GetUrlImageFromBreedUseCase,
    private val observeDogsWithBreedsUseCase: ObserveDogsWithBreedsUseCase
) : ViewModel() {


    val dogsUIState =  observeDogsWithBreedsUseCase().map {
        if (it.isEmpty()) {
            ListUiState.Empty
        } else {
            ListUiState.Result(it)
        }
    }.catch {
        ListUiState.Error("${it.message}")
    }
        .stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ListUiState.Loading
    )
//
//    private val _uiState by lazy { mutableStateOf<ListUiState>(ListUiState.Loading) }
//    private val uiState: State<ListUiState> by lazy { _uiState }

    private val imageUpdateFlow = MutableSharedFlow<String>()

    init {
        loadList()

        viewModelScope.launch {
            imageUpdateFlow.collect{ breedId ->
                fetchUrlImage(breedId)
            }
        }
    }

    fun loadList() {
        viewModelScope.launch {
            fetchDogs()
        }
    }

    private suspend fun fetchDogs() {

        withContext(Dispatchers.IO){
            getDogsWithBreedsUseCase()
//            when (val result = getDogsWithBreedsUseCase()){
//                is Result.Error -> {
//                    _uiState.value = ListUiState.Error(result.throwable.message)
//                }
//                is Result.Success -> {
//                    when {
//                        result.data.isEmpty() -> {
//                            _uiState.value = ListUiState.Empty
//                        }
//                        else -> {
//                            _uiState.value = ListUiState.Result(result.data)
//                        }
//                    }
//                }
//            }
        }
    }

    fun enqueueFetchImageUrl(breedId : String) {
        viewModelScope.launch {
            imageUpdateFlow.emit(breedId)
        }
    }

    private suspend fun fetchUrlImage(breedId : String) {
        withContext(Dispatchers.IO) {
            getUrlImageFromBreedUseCase(breedId)
//            val statusImage = when (val result = getUrlImageFromBreedUseCase(breedId)) {
//                is Result.Error -> {
//                    DogImageStatus(null, StatusImage.ERROR)
//                }
//
//                is Result.Success -> {
//                    DogImageStatus(result.data, StatusImage.SUCCESS)
//                }
//            }
//
//            val uiState = uiState.value
//            if (uiState is ListUiState.Result) {
//                _uiState.value = uiState.copy(
//                    dogs = uiState.dogs.map { dog ->
//                        if (dog.id == breedId) {
//                            dog.copy(image = statusImage)
//                        } else {
//                            dog.copy(subBreeds = dog.subBreeds.map { subBreed ->
//                                if (subBreed.id == breedId) {
//                                    subBreed.copy(image = statusImage)
//                                }
//                                else{
//                                    subBreed
//                                }
//                            })
//                        }
//                    }
//                )
//            }
        }
    }
}

