package com.example.calendar.ui_component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpacer(value: Int) {
    Spacer(modifier = Modifier.height(value.dp))
}

@Composable
fun HorizontalSpacer(value: Int) {
    Spacer(modifier = Modifier.width(value.dp))
}