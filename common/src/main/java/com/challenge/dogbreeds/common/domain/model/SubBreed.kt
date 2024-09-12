package com.challenge.dogbreeds.common.domain.model

data class SubBreed(
    override val id: String,
    override val name: String,
    override val image: DogImageStatus = DogImageStatus(null, StatusImage.NONE),
) :IDog