package com.example.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendar.model.Date
import com.example.calendar.values.calendarDateSize
import com.example.calendar.values.dayOfWeekToCalendarDay
import java.util.Calendar

@Composable
fun Day(
    date: Date?,
    dayIndex: Int,
    weekIndex: Int,
    monthIndex: Int,
    selectedIndex: MutableState<Triple<Int, Int, Int>>
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .selectable(
                selected = selectedIndex.value == Triple(monthIndex, weekIndex, dayIndex),
                onClick = {
                    if (selectedIndex.value == Triple(monthIndex, weekIndex, dayIndex))
                        selectedIndex.value = Triple(-1, -1, -1)
                    else {
                        selectedIndex.value = Triple(monthIndex, weekIndex, dayIndex)
                    }
                }
            )
            .background(if (selectedIndex.value == Triple(monthIndex, weekIndex, dayIndex)) Color.Black else Color.White)
            .size(calendarDateSize)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(4.dp),
            text = "${date?.day}",
            color = when {
                selectedIndex.value == Triple(monthIndex, weekIndex, dayIndex) -> Color.White
                dayOfWeekToCalendarDay[date?.dayOfWeek] == Calendar.SUNDAY -> Color.Red
                dayOfWeekToCalendarDay[date?.dayOfWeek] == Calendar.SATURDAY -> Color.Blue
                else -> Color.Black
            }
        )
    }
}

@Preview
@Composable
fun DaySelectedPreview() {
    val selectedIndex = remember { mutableStateOf(Triple(0,0,0)) }
    Day(
        date = Date(2024, 1, 24, "목"),
        dayIndex = 0,
        weekIndex = 0,
        monthIndex = 0,
        selectedIndex = selectedIndex,
    )
}

@Preview
@Composable
fun DayNotSelectedPreview() {
    val selectedIndex = remember { mutableStateOf(Triple(-1,-1,-1)) }
    Day(
        date = Date(2024, 1, 24, "목"),
        dayIndex = 0,
        weekIndex = 0,
        monthIndex = 0,
        selectedIndex = selectedIndex,
    )
}