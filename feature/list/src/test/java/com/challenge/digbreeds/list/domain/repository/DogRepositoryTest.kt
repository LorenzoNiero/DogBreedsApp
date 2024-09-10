package com.challenge.digbreeds.list.domain.repository

import com.challenge.digbreeds.list.mock.DataMock
import com.challenge.digbreeds.list.mock.DomainMock
import com.challenge.dogbreeds.common.domain.Result
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.network.data.NetworkDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DogRepositoryTest {

    @MockK
    private lateinit var networkDataSource: NetworkDataSource

    private lateinit var repository: DogRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        repository = DogRepositoryImpl(
            networkDataSource
        )
    }


    @Test
    fun `fetchAllDogs should call networkDataSource and return list of dogs with success`() = runTest {
        // Given
        val dogsNetwork = DataMock.dogsNetwork
        coEvery { networkDataSource.fetchDogsWithSubBreeds() } returns dogsNetwork

        // When
        val response = repository.fetchAllDogs()

        // Then
        coVerify { networkDataSource.fetchDogsWithSubBreeds() }
        assert(response is List<Dog>)
        assertEquals(DomainMock.dogs, response)
    }

    @Test
    fun `fetchImageUrl should call networkDataSource and return list of dogs with success`() = runTest {
        // Given
        val imageNetwork = DataMock.imageNetwork
        coEvery { networkDataSource.fetchImageDogRandom(any()) } returns imageNetwork

        // When
        val response = repository.fetchImageUrl("breedId")

        // Then
        coVerify { networkDataSource.fetchImageDogRandom(any()) }
        assert(response is String)
        assertEquals(DomainMock.imageUrl, response)
    }

}