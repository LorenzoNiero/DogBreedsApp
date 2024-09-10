package com.challenge.dogbreeds.common.domain.entity

data class Dog(
    val name: String,
    val subBreeds: List<String>,
    val imageUrl: String?
)
