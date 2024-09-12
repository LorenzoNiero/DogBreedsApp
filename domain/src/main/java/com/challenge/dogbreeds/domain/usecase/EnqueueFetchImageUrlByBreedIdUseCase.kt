package com.challenge.dogbreeds.domain.usecase

import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.common.domain.getResult
import com.challenge.dogbreeds.common.domain.getResultBlocking
import com.challenge.dogbreeds.domain.repository.DogRepository
import javax.inject.Inject

class EnqueueFetchImageUrlByBreedIdUseCase @Inject constructor(
    private val dogsRepository: DogRepository
) {
    operator fun invoke(breedId : String,subBreedId: String?) : Result<Unit> = getResultBlocking {
        dogsRepository.enqueueFetchImageUrlWork(breedId = breedId, subBreedId)
    }
}