package com.challenge.dogbreeds.domain.usecase

import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.common.domain.getResult
import javax.inject.Inject

class GetDogsWithBreedsUseCase @Inject constructor(
    private val dogsRepository: DogRepository
) {
    suspend operator fun invoke(): Result<Unit> = getResult {
        dogsRepository.fetchAllDogs()
    }
}


