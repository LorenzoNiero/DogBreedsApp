package com.challenge.dogbreeds.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.ui.R
import com.challenge.dogbreeds.ui.theme.Dimens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DogCell(
    dog: Dog,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    getImageUrl: suspend (String)-> Unit = {}
) {
    CardSurface(modifier = modifier ) {
        var imageLoading by remember { mutableStateOf(false) }
        LaunchedEffect(dog) {
            withContext(Dispatchers.IO) {
                if (dog.imageUrl == null && imageLoading.not()) {
                    imageLoading = true
                    try {
                        getImageUrl(dog.name)
                    } catch (e: Exception) {
                        //todo shoe error
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
            horizontalArrangement = Arrangement.spacedBy(Dimens.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(dimensionResource(R.dimen.icon_size_image)),
                contentAlignment = Alignment.Center
            ) {
                if (imageLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(start = 8.dp))
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
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview()
@Composable
fun CustomerCardPreview() {
    Surface(Modifier.width(900.dp)) {
        DogCell(dog = Dog("DogName", emptyList(), null))
    }
}


