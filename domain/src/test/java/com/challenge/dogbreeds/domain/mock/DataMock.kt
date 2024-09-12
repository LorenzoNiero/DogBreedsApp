package com.challenge.dogbreeds.domain.mock

import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity
import com.challenge.dogbreeds.network.data.model.DogsNetwork
import com.challenge.dogbreeds.network.data.model.ImageNetwork
import com.challenge.dogbreeds.network.data.model.StatusResponse

object DataMock {
    val dogsNetwork = DogsNetwork(
        message = mapOf(
            "affenpinscher" to listOf(),
            "bulldog" to listOf(
                "boston",
                "english",
                "french"
            )
        ),
        status = StatusResponse.SUCCESS
    )

    val dogsEntity = DogBreedEntity(
        id = "affenpinscher",
        name = "Affenpinscher"
    )

    val subBreedEntity = SubBreedEntity(
        id = "boston",
        parentBreedId = "bulldog",
        name = "Boston"
    )

    val imageNetwork = ImageNetwork(
        message = DomainMock.imageUrl,
        status = StatusResponse.SUCCESS
    )
}