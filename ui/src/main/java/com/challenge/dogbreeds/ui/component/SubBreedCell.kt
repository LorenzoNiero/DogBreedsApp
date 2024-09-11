package com.challenge.dogbreeds.ui.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.challenge.dogbreeds.common.domain.entity.SubBreed
import com.challenge.dogbreeds.ui.R
import com.challenge.dogbreeds.ui.theme.Dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun SubBreedCell(
    dog: SubBreed,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    getImageUrl: suspend (String) -> Unit = {},
) {
    Box(modifier = modifier ) {
        var imageLoading by remember { mutableStateOf(false) }
        LaunchedEffect(dog.id) {
            withContext(Dispatchers.IO) {
                if (dog.imageUrl == null && imageLoading.not()) {
                    imageLoading = true
                    try {
                        getImageUrl(dog.id)
                    } catch (e: Exception) {
                        //TODO: show message error
                        Log.e("SubBreedCell", "Error loading image", e)
                    } finally {
                        imageLoading = false
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .padding(Dimens.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(dimensionResource(R.dimen.icon_size_image)),
                contentAlignment = Alignment.Center
            ) {
                if (imageLoading) {
                    CircularProgressIndicator(modifier = Modifier)
                } else {
                    ImageLoader(
                        url = dog.imageUrl ?: "",
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_image))
                    )
                }
            }

            Text(
                dog.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}