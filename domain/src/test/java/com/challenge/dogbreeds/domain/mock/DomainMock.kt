package com.challenge.dogbreeds.domain.mock

import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.common.domain.entity.SubBreed

object DomainMock {

    val dogs = listOf(
        Dog(
            id = "affenpinscher",
            name = "Affenpinscher",
            subBreeds = listOf(),
            image = null
        ),
        Dog(
            id = "bulldog",
            name = "Bulldog",
            subBreeds = listOf(
                SubBreed(
                    id = "boston",
                    name = "Boston",
                    image = null
                ),
                SubBreed(
                    id = "english",
                    name = "English",
                    image = null
                ),
                SubBreed(
                    id = "french",
                    name = "French",
                    image = null
                )
            ),
            image = null
        )

    )

    val imageUrl = "https://images.dog.ceo/breeds/affenpinscher/n02110627_233.jpg"

}

