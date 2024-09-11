package com.challenge.dogbreeds.common.domain.entity

data class DogImageStatus (
    val imageUrl: String?,
    val status: StatusImage = StatusImage.NONE
)

enum class StatusImage {
    NONE,
    SUCCESS,
    ERROR
}