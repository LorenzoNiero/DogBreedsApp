package com.challenge.dogbreeds.domain.domain.usecase

import com.challenge.dogbreeds.domain.mock.DomainMock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.domain.usecase.EnqueueFetchImageUrlByBreedIdUseCase
import org.junit.Assert.assertEquals


class GetUrlDogImageStatusFromBreedUseCaseTest {

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
        coEvery { repository.fetchImageUrl(any()) } returns Unit

        // When
        val result = useCase("breedId")

        // Then
        coVerify { repository.fetchImageUrl(any()) }
        assert(result is Result.Success<Unit> )
    }

    @Test
    fun `use case should call repository and return with error`() = runTest {
        // Given
        coEvery { repository.fetchImageUrl(any()) } throws Exception()

        // When
        val response = useCase("breedId")

        // Then
        coVerify { repository.fetchImageUrl(any()) }
        assert(response is Result.Error)
    }
}