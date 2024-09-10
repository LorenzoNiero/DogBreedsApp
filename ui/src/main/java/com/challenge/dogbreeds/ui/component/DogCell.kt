package com.challenge.dogbreeds.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.ui.R
import com.challenge.dogbreeds.ui.theme.Dimens

@Composable
fun DogCell(
    dog: Dog,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    CardSurface(modifier = modifier ) {

        Row(
            modifier = Modifier.clickable {
                onClick()
            }.padding(Dimens.small),
            horizontalArrangement = Arrangement.spacedBy(Dimens.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageLoader(
                url = dog.imageUrl ?: "",
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_image))
            )

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
        DogCell(dog = Dog("DogName", emptyList(), null,))
    }
}


