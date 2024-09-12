package com.challenge.dogbreeds.domain.usecase

import com.challenge.dogbreeds.domain.repository.DogRepository
import javax.inject.Inject

class EnqueueFetchImageUrlByBreedIdUseCase @Inject constructor(
    private val dogsRepository: DogRepository
) {
    operator fun invoke(breedId : String) {
        dogsRepository.enqueueFetchImageUrlWork(breedId = breedId)
    }
}