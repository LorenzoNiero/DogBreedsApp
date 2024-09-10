package com.challenge.dogbreeds.common.domain.entity

interface IDog {
    val id: String
    val name: String
    val imageUrl: String?
}

data class Dog(
    override val id: String,
    override val name: String,
    val subBreeds: List<SubBreed>,
    override val imageUrl: String?
) : IDog


