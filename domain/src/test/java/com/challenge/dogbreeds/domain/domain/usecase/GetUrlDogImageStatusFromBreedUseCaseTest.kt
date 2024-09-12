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
import org.junit.Assert.assertEquals


class GetUrlDogImageStatusFromBreedUseCaseTest {

    @MockK
    private lateinit var repository: com.challenge.dogbreeds.domain.repository.DogRepository

    private lateinit var useCase: com.challenge.dogbreeds.domain.usecase.EnqueueFetchImageUrlByBreedIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = com.challenge.dogbreeds.domain.usecase.EnqueueFetchImageUrlByBreedIdUseCase(repository)
    }

    @Test
    fun `use case should call repository without error`() = runTest {
        // Given
        coEvery { repository.fetchImageUrl(any()) } returns DomainMock.imageUrl

        // When
        val response = useCase("breedId")

        // Then
        coVerify { repository.fetchImageUrl(any()) }
        assert(response is Result.Success<String>)
        assertEquals(DomainMock.imageUrl, (response as Result.Success<String>).data)
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