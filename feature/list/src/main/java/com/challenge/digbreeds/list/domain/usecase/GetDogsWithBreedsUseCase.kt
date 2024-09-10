package com.challenge.digbreeds.list.domain.usecase

import com.challenge.digbreeds.list.domain.entity.Dog
import com.challenge.digbreeds.list.domain.repository.DogRepository
import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.common.domain.getResult
import javax.inject.Inject

class GetDogsWithBreedsUseCase @Inject constructor(
    private val dogsRepository: DogRepository
) {
    suspend operator fun invoke(): Result<List<Dog>> = getResult {
        dogsRepository.getAllDogs()
    }
}
