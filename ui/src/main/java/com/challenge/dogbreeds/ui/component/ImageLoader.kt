package com.challenge.dogbreeds.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.challenge.dogbreeds.ui.R

@Composable
fun ImageLoader(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.icon_image_placeholder_24),
        error = painterResource(R.drawable.icon_no_image_24),
        contentDescription = stringResource(R.string.image_content_drescription_image),
        contentScale = ContentScale.Inside,
        modifier = modifier
    )
}