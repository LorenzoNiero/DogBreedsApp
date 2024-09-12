package com.challenge.dogbreeds.domain.domain.usecase

import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.domain.usecase.ObserveDogsWithBreedsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class ObserveDogsWithBreedsUseCaseTest {
    @MockK
    private lateinit var repository: DogRepository

    private lateinit var useCase: ObserveDogsWithBreedsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = ObserveDogsWithBreedsUseCase(repository)
    }

    @Test
    fun `use case should call repository without error`() = runTest {
        // Given
        coEvery { repository.observeAllDogs() } returns flowOf( listOf() )

        // When
        val result = useCase()

        // Then
        coVerify { repository.observeAllDogs() }
        assert(result is Flow<List<Dog>>)
    }
}