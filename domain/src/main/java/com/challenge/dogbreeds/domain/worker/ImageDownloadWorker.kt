package com.challenge.dogbreeds.domain.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.WorkerParameters
import com.challenge.dogbreeds.domain.repository.DogRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

val WorkConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

@HiltWorker
class ImageDownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dogRepository: DogRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val breedId = inputData.getString(KEY_ID_STRING) ?: return Result.failure()

        return try {
            dogRepository.fetchImageUrl(breedId)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object{
        val KEY_ID_STRING = "breedId"
    }
}