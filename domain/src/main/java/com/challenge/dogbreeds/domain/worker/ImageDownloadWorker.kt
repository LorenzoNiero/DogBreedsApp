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
        val breedId = inputData.getString(KEY_BREAD_ID_STRING) ?: return Result.failure()
        val subBreedId = runCatching {
            inputData.getString(KEY_SUB_BREAD_ID_STRING)
        }.getOrElse { null }

        return try {
            dogRepository.fetchImageUrl(breedId, subBreedId)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object{
        const val KEY_BREAD_ID_STRING = "breedId"
        const val KEY_SUB_BREAD_ID_STRING = "subBreedId"
    }
}