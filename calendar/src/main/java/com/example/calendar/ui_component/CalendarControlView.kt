package com.example.calendar.ui_component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CalendarControlView(
    modifier: Modifier = Modifier,
    year: Int,
    month: Int,
    onPrev: () -> Unit = {},
    onNext: () -> Unit = {},
    isControllable: Boolean = false
) {
    Row(modifier) {
        Text(modifier = Modifier.testTag("date"), text = "$year . $month", color = Color.Black)
        HorizontalSpacer(value = 10)
        if (isControllable) {
            Icon(
                modifier = Modifier
                    .testTag("prev")
                    .clickable { onPrev() },
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "key"
            )
            HorizontalSpacer(value = 10)
            Icon(
                modifier = Modifier
                    .testTag("next")
                    .clickable { onNext() },
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "key"
            )
        }
    }
}

@Preview
@Composable
fun CalendarControlViewPreview() {
    CalendarControlView(
        modifier = Modifier.fillMaxWidth(),
        year = 2024,
        month = 1,
        onNext = {}, onPrev = {}
    )
}