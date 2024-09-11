package com.challenge.dogbreeds.network.data

import com.challenge.dogbreeds.network.data.model.DogsNetwork

object ApiMock {
    val breedsNetwork = DogsNetwork(
        message = mapOf(
            "affenpinscher" to listOf(),
            "australian" to listOf(
                "kelpie",
                "shepherd"
            )
        ),
        status = "success"
    )
}