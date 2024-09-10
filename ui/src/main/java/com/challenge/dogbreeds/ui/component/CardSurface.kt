package com.challenge.dogbreeds.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.challenge.dogbreeds.ui.theme.Dimens

@Composable
fun CardSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(Dimens.small),
        modifier = modifier) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun CardSurfacePreview() {

    Surface(Modifier.width(500.dp)) {
        CardSurface(Modifier.fillMaxWidth(0.3f)) {
            Column {
                Row{
                    Text(text = "prima riga")
                    Text(text = "prima riga")
                }

                Text(text = "seconda riga")

                Text(text = "terza riga")
            }
        }
    }
}