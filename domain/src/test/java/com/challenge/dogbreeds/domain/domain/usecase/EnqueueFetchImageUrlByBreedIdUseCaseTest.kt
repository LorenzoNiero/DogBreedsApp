package com.challenge.dogbreeds.domain.domain.usecase

import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.domain.usecase.EnqueueFetchImageUrlByBreedIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class EnqueueFetchImageUrlByBreedIdUseCaseTest {

    @MockK
    private lateinit var repository: DogRepository

    private lateinit var useCase: EnqueueFetchImageUrlByBreedIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = EnqueueFetchImageUrlByBreedIdUseCase(repository)
    }

    @Test
    fun `use case should call repository without error`() = runTest {
        // Given
        coEvery { repository.enqueueFetchImageUrlWork(any(), any()) } returns Unit

        // When
        val result = useCase("breedId", null)

        // Then
        coVerify { repository.enqueueFetchImageUrlWork(any(), any()) }
        assert(result is Result.Success<Unit> )
    }

    @Test
    fun `use case should call repository and return with error`() = runTest {
        // Given
        coEvery { repository.enqueueFetchImageUrlWork(any(), any()) } throws Exception()

        // When
        val response = useCase("breedId", null)

        // Then
        coVerify { repository.enqueueFetchImageUrlWork(any(), any()) }
        assert(response is Result.Error)
    }
}

