package com.challenge.dogbreeds.ui.component.cell

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.challenge.dogbreeds.common.domain.model.SubBreed


@Composable
fun SubBreedCell(
    dog: SubBreed,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    getImageUrl: (String) -> Unit = {},
) {
    Box(modifier = modifier ) {
        DogContentCell(dog, getImageUrl, onClick, null, null)
    }
}