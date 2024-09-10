package com.challenge.dogbreeds.ui.component;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.challenge.dogbreeds.ui.theme.Dimens
import com.challenge.dogbreeds.ui.theme.DogBreedsTheme


@Composable
fun ValueWithLabel(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    valueStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    ) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            label,
            style = labelStyle,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = Dimens.xSmall)
        )

        Text(
            value,
            style = valueStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_ValueWithLabel(){
    DogBreedsTheme {
        ValueWithLabel("Title", "Value")
    }
}