package com.challenge.dogbreeds.ui.component.cell

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import com.challenge.dogbreeds.common.domain.entity.IDog
import com.challenge.dogbreeds.common.domain.entity.StatusImage
import com.challenge.dogbreeds.ui.R
import com.challenge.dogbreeds.ui.component.ImageLoader
import com.challenge.dogbreeds.ui.theme.Dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
internal fun DogContentCell(
    dog: IDog,
    getImageUrl: (String) -> Unit,
    onClick: () -> Unit,
    isExpanded: Boolean? = null,
    onClickIcon: (() -> Unit)? = null
) {
    var imageLoading by remember { mutableStateOf(false) }

    LaunchedEffect(dog) {
        withContext(Dispatchers.IO) {
            if (dog.image.imageUrl == null && dog.image.status == StatusImage.NONE && imageLoading.not()) {
                imageLoading = true
                try {
                    getImageUrl(dog.id)
                } catch (e: Exception) {
                    //TODO: show error
                    Log.e("DogContentDogContentCell", "Error loading image", e)
                }
            } else {
                if (dog.image.status != StatusImage.NONE) {
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
                    url = dog.image.imageUrl ?: "",
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size_image))
                )
            }
        }

        Text(
            dog.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
//                modifier = Modifier.weight(1.0f)
        )

        isExpanded?.let {
            IconButton({
                onClickIcon?.invoke()
            }) {
                Icon(
                    painter = painterResource(
                        if (isExpanded) {
                            R.drawable.icon_expand_less_24
                        } else {
                            R.drawable.icon_expand_more_24
                        }
                    ),
                    contentDescription = "icon"
                )
            }

        }
    }
}