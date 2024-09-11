package com.challenge.dogbreeds.network.data

import com.challenge.dogbreeds.network.api.DogApi
import com.challenge.dogbreeds.network.di.NetworkModule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class DogApiTest {

    private val mockWebServer = MockWebServer()
    private lateinit var client: OkHttpClient
    private lateinit var api: DogApi

    private val moshi = NetworkModule.parser()

    @Before
    fun setup() {
        mockWebServer.start()

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    moshi
                )
            )
            .build()
            .create(DogApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch a list of breeds and sub breeds correctly giving success response`() = runBlocking {
        // given
        val configurationResponseModel = ApiMock.breedsNetwork
        val asset = JvmUnitTestFakeAssetManager
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(asset.readFileWithNewLineFromResources("get_list_success_response.json"))

        // when
        mockWebServer.enqueue(mockResponse)
        val response = api.getAllBreeds()

        // then
        assertEquals(configurationResponseModel, response)
    }
}
