package com.example.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calendar.model.Date
import com.example.calendar.util.CalendarUtil


@Composable
fun Week(
    week: List<Date?>,
    weekIndex: Int,
    monthIndex: Int,
    selectedIndex: MutableState<Triple<Int, Int, Int>>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        week.forEachIndexed { dayIndex, day ->
            Day(day, monthIndex, dayIndex, weekIndex, selectedIndex)
        }
    }
}

@Preview
@Composable
fun WeekPreview() {
    val calendar = remember {
        CalendarUtil.makeMonth(2024, 1)
    }
    val selectedIndex = remember { mutableStateOf(Triple(3,0,0)) }
    Column {
        Week(week = calendar[0], weekIndex = 0, monthIndex = 0, selectedIndex = selectedIndex)
    }
}