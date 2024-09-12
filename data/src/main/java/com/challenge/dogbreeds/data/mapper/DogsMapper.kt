package com.challenge.dogbreeds.data.mapper

import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.common.domain.entity.DogImageStatus
import com.challenge.dogbreeds.common.domain.entity.StatusImage
import com.challenge.dogbreeds.common.domain.entity.SubBreed
import com.challenge.dogbreeds.database.model.DogBreedEntity
import com.challenge.dogbreeds.database.model.BreedWithSubBreeds
import com.challenge.dogbreeds.database.model.ImageEntity
import com.challenge.dogbreeds.database.model.SubBreedEntity
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

fun BreedWithSubBreeds.asExternalModel() = Dog(
    id = breed.id,
    name = breed.name.replaceFirstChar { it.uppercase(Locale.getDefault()) },
    subBreeds = this.subBreedEntitiys.map { it.asSubBreedExternalModel() },
//    image = DogImageStatus(this.breed.urlImage,
//        when{
//            breed.urlImage != null -> {
//                StatusImage.SUCCESS
//            }
//            breed.statusFailImage == false -> {
//                StatusImage.ERROR
//            }
//            else -> {
//                StatusImage.NONE
//            }
//        })
)

fun SubBreedEntity.asSubBreedExternalModel() = SubBreed(
    id = breadId,
    name = name.replaceFirstChar { it.uppercase(Locale.getDefault()) },
//    image = DogImageStatus(urlImage, when{
//        urlImage != null -> {
//            StatusImage.SUCCESS
//        }
//        statusFailImage == false -> {
//            StatusImage.ERROR
//        }
//        else -> {
//            StatusImage.NONE
//        }
//    })
)

fun ImageEntity.asExternalModel() = DogImageStatus(this.urlImage,
    when{
        this.urlImage != null -> {
            StatusImage.SUCCESS
        }
        this.statusFailImage == true -> {
            StatusImage.ERROR
        }
        else -> {
            StatusImage.NONE
        }
    })