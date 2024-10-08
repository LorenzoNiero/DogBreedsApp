package com.challenge.dogbreeds.common.domain.model

data class Dog(
    override val id: String,
    override val name: String,
    val subBreeds: List<SubBreed>,
    override val image: DogImageStatus = DogImageStatus(null, StatusImage.NONE),
) : IBreed


