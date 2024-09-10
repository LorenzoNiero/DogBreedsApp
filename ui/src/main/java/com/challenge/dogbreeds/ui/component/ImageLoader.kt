package com.challenge.dogbreeds.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.challenge.dogbreeds.ui.R

@Composable
fun ImageLoader(url: String, modifier: Modifier = Modifier) {
    val painter: Painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder
                (LocalContext.current).data(data = url).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                error(R.drawable.icon_no_image_24)
                placeholder(R.drawable.icon_image_placeholder_24)
                memoryCachePolicy(CachePolicy.ENABLED) // enable cache ram (optional)
                diskCachePolicy(CachePolicy.ENABLED) // enable cache disk(optional)
            }).build()
        )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
    )
}