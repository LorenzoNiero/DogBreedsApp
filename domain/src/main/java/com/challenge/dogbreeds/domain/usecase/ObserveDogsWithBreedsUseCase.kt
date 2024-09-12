package com.challenge.dogbreeds.domain.usecase

import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.common.domain.model.Dog
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDogsWithBreedsUseCase @Inject constructor(
    private val dogsRepository: DogRepository
) {
    operator fun invoke(): Flow<List<Dog>> = dogsRepository.observeAllDogs()
}


