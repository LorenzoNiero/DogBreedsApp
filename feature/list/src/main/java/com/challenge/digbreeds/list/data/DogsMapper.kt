package com.challenge.digbreeds.list.data

import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.common.domain.entity.DogImageStatus
import com.challenge.dogbreeds.common.domain.entity.StatusImage
import com.challenge.dogbreeds.common.domain.entity.SubBreed
import com.challenge.dogbreeds.database.model.BreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import com.challenge.dogbreeds.network.data.model.DogsNetwork
import java.util.Locale


fun DogsNetwork.mapToDomainModel(): List<Dog> {
    return this.message.map { it.mapToDomainModel() }
}

private fun Map.Entry<String, List<String>>.mapToDomainModel() : Dog {
    return Dog(
        id = this.key,
        name = this.key.replaceFirstChar { it.uppercase(Locale.getDefault()) },
        image = DogImageStatus(null, StatusImage.NONE),
        subBreeds = this.value.map { subBreed ->
            SubBreed(
                id = subBreed,
                name = subBreed.replaceFirstChar { it.uppercase(Locale.getDefault()) },
                image = DogImageStatus(null, StatusImage.NONE)
            )
        }
    )
}

//fun BreedEntity.asExternalModel() = Dog(
//    id = id,
//    name = name,
//    subBreeds = emptyList(),
//    image = DogImageStatus(urlImage, if(urlImage != null) {StatusImage.SUCCESS } else {StatusImage.NONE})
//)


fun BreedWithSubBreeds.asExternalModel() = Dog(
    id = this.breed.id,
    name = this.breed.name.replaceFirstChar { it.uppercase(Locale.getDefault()) },
    subBreeds = this.subBreeds.map { it.asSubBreedExternalModel() },
    image = DogImageStatus(this.breed.urlImage, if(this.breed.urlImage != null) {
        StatusImage.SUCCESS } else {
        StatusImage.NONE})
)

fun BreedEntity.asSubBreedExternalModel() = SubBreed(
    id = id,
    name = name.replaceFirstChar { it.uppercase(Locale.getDefault()) },
    image = DogImageStatus(urlImage, if(urlImage != null) {
        StatusImage.SUCCESS } else {
        StatusImage.NONE})
)