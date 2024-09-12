package com.challenge.dogbreeds.domain.mock

import com.challenge.dogbreeds.network.data.model.DogsNetwork
import com.challenge.dogbreeds.network.data.model.ImageNetwork

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
        status = "success"
    )

    val imageNetwork = ImageNetwork(
        message = DomainMock.imageUrl,
        status = "success"
    )
}