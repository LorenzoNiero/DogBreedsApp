package com.challenge.digbreeds.list.mock

import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.common.domain.entity.SubBreed

object DomainMock {

    val dogs = listOf(
        Dog(
            id = "affenpinscher",
            name = "Affenpinscher",
            subBreeds = listOf(),
            imageUrl = null
        ),
        Dog(
            id = "bulldog",
            name = "Bulldog",
            subBreeds = listOf(
                SubBreed(
                    id = "boston",
                    name = "Boston",
                    imageUrl = null
                ),
                SubBreed(
                    id = "english",
                    name = "English",
                    imageUrl = null
                ),
                SubBreed(
                    id = "french",
                    name = "French",
                    imageUrl = null
                )
            ),
            imageUrl = null
        )

    )

    val imageUrl = "https://images.dog.ceo/breeds/affenpinscher/n02110627_233.jpg"

}

