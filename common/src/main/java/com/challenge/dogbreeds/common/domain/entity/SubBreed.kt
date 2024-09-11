package com.challenge.dogbreeds.common.domain.entity

data class SubBreed(
    override val id: String,
    override val name: String,
    override val image: DogImageStatus = DogImageStatus(null, StatusImage.NONE),
) :IDog