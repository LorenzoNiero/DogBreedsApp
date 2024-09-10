package com.challenge.digbreeds.list.domain.usecase

import com.challenge.digbreeds.list.domain.repository.DogRepository
import com.challenge.digbreeds.list.mock.DomainMock
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


class GetUrlImageFromBreedUseCaseTest {

    @MockK
    private lateinit var repository: DogRepository

    private lateinit var useCase: GetUrlImageFromBreedUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetUrlImageFromBreedUseCase(repository)
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