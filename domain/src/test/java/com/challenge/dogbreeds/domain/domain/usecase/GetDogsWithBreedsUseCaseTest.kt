package com.challenge.dogbreeds.domain.domain.usecase

import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.domain.mock.DomainMock
import com.challenge.dogbreeds.common.domain.entity.Dog
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.challenge.dogbreeds.common.domain.Result
import org.junit.Assert.assertEquals


class GetDogsWithBreedsUseCaseTest {

    @MockK
    private lateinit var repository: com.challenge.dogbreeds.domain.repository.DogRepository

    private lateinit var useCase: com.challenge.dogbreeds.domain.usecase.GetDogsWithBreedsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = com.challenge.dogbreeds.domain.usecase.GetDogsWithBreedsUseCase(repository)
    }

    @Test
    fun `use case should call repository without error`() = runTest {
        // Given
        val dogsList = DomainMock.dogs
        coEvery { repository.fetchAllDogs() } returns dogsList

        // When
        val response = useCase()

        // Then
        coVerify { repository.fetchAllDogs() }
        assert(response is Result.Success<List<Dog>>)
        assertEquals(dogsList, (response as Result.Success<List<Dog>>).data)
    }

    @Test
    fun `use case should call repository and return with error`() = runTest {
        // Given
        coEvery { repository.fetchAllDogs() } throws Exception()

        // When
        val response = useCase()

        // Then
        coVerify { repository.fetchAllDogs() }
        assert(response is Result.Error)
    }
}