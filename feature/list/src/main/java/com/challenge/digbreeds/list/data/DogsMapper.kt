package com.challenge.digbreeds.list.data

import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.network.data.model.DogsNetwork


fun DogsNetwork.mapToDomainModel(): List<Dog> {
    return this.message.map { it.mapToDomainModel() }
}

private fun Map.Entry<String, List<String>>.mapToDomainModel() : Dog {
    return Dog(
        name = this.key,
        imageUrl = null,
        subBreeds = this.value
    )
}
