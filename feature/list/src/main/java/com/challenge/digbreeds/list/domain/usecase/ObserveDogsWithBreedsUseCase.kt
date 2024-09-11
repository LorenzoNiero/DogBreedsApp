package com.challenge.digbreeds.list.domain.usecase

import com.challenge.digbreeds.list.domain.repository.DogRepository
import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.common.domain.getResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDogsWithBreedsUseCase @Inject constructor(
    private val dogsRepository: DogRepository
) {
    operator fun invoke(): Flow<List<Dog>> = dogsRepository.observeAllDogs()

}


