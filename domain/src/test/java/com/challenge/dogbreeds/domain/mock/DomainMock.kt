package com.challenge.dogbreeds.domain.mock

import com.challenge.dogbreeds.common.domain.model.Dog
import com.challenge.dogbreeds.common.domain.model.SubBreed

object DomainMock {

    val dogs = listOf(
        Dog(
            id = "affenpinscher",
            name = "Affenpinscher",
            subBreeds = listOf(),
        ),
        Dog(
            id = "bulldog",
            name = "Bulldog",
            subBreeds = listOf(
                SubBreed(
                    id = "boston",
                    name = "Boston",
                ),
                SubBreed(
                    id = "english",
                    name = "English",
                ),
                SubBreed(
                    id = "french",
                    name = "French",
                )
            ),
        )

    )

    val imageUrl = "https://images.dog.ceo/breeds/affenpinscher/n02110627_233.jpg"

}

