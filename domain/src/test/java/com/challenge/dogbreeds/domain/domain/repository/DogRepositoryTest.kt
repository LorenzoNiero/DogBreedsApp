package com.challenge.dogbreeds.domain.domain.repository

import androidx.work.WorkManager
import com.challenge.dogbreeds.domain.mock.DataMock
import com.challenge.dogbreeds.data.datasource.BreedLocalDataSource
import com.challenge.dogbreeds.domain.repository.DogRepository
import com.challenge.dogbreeds.domain.repository.DogRepositoryImpl
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

    @MockK
    private lateinit var localDataSource: BreedLocalDataSource

    @MockK
    private lateinit var workManager: WorkManager

    private lateinit var repository: DogRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        repository = DogRepositoryImpl(
            localDataSource,
            networkDataSource,
            workManager
        )
    }


    @Test
    fun `fetchAllDogs should call networkDataSource and return list of dogs with success`() = runTest {
        // Given
        val dogsNetwork = DataMock.dogsNetwork
        coEvery { networkDataSource.fetchDogsWithSubBreeds() } returns dogsNetwork

        // When
        repository.fetchAllDogs()

        // Then
        coVerify { networkDataSource.fetchDogsWithSubBreeds() }
    }

    @Test
    fun `fetchImageUrl by breedId should call networkDataSource and update entity DogBreed with success`() = runTest {
        // Given
        val imageNetwork = DataMock.imageNetwork
        coEvery { networkDataSource.fetchImageDogRandom(any()) } returns imageNetwork
        coEvery { localDataSource.getDogBreed(any()) } returns DataMock.dogsEntity

        // When
        val response = repository.fetchImageUrl("breedId", null)

        // Then
        coVerify { networkDataSource.fetchImageDogRandom(any()) }
        coVerify { localDataSource.insertDogBreed(any()) }
        assertEquals(Unit, response)
    }

    @Test
    fun `fetchImageUrl with subBreed should call networkDataSource and update entity DogBreed with success`() = runTest {
        // Given
        val imageNetwork = DataMock.imageNetwork
        coEvery { networkDataSource.fetchImageDogRandom(any()) } returns imageNetwork
        coEvery { localDataSource.getSubBreed(any(), any()) } returns DataMock.subBreedEntity

        // When
        val response = repository.fetchImageUrl("breedId", "subBreedId")

        // Then
        coVerify { networkDataSource.fetchImageSubBreedRandom(any(), any()) }
        coVerify { localDataSource.insertSubBreed(any()) }
        assertEquals(Unit, response)
    }

}