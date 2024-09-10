package com.challenge.digbreeds.list.presentation.model

import com.challenge.dogbreeds.common.domain.entity.Dog

internal sealed class ListUiState {
    data object Loading : ListUiState()
    data class Error(
        val message: String?
    ) : ListUiState()
    data class Result(
        val dogs: List<Dog>
    ) : ListUiState()
    data object Empty : ListUiState()
}