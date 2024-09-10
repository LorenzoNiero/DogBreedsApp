package com.challenge.digbreeds.list.mock

import com.challenge.dogbreeds.common.domain.entity.Dog

object DomainMock {

    val dogs = listOf(
        Dog(
            id = "affenpinscher",
            name = "Affenpinscher",
            subBreeds = listOf(),
            imageUrl = null
        ),
        Dog(
            id = "african",
            name = "African",
            subBreeds = listOf(),
            imageUrl = null
        )

    )

    val imageUrl = "https://images.dog.ceo/breeds/affenpinscher/n02110627_233.jpg"

}