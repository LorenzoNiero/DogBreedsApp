package com.challenge.dogbreeds.ui.component.cell

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.challenge.dogbreeds.common.domain.entity.Dog
import com.challenge.dogbreeds.ui.component.CardSurface

@Composable
fun DogCell(
    dog: Dog,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    getImageUrl: (String) -> Unit = {},
    onClickIcon: () -> Unit = {},
    isExpanded: Boolean?
) {
    CardSurface(modifier = modifier ) {
        DogContentCell(dog, getImageUrl, onClick, isExpanded, onClickIcon)
    }
}

@Preview()
@Composable
fun CustomerCardPreview() {
    Surface(Modifier.width(900.dp)) {
        DogCell(dog = Dog("id","DogName", emptyList()), isExpanded = null)
    }
}


