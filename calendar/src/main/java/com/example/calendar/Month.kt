package com.example.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
fun Month(
    month: List<List<Date?>>,
    selectedIndex: MutableState<Triple<Int, Int, Int>>,
    monthIndex: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .selectableGroup()
    ) {
        DaysOfWeek(Modifier.weight(0.5f))
        month.forEachIndexed { weekIndex, week ->
            Week(week, monthIndex, weekIndex, selectedIndex)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun MonthPreview() {
    val selectedIndex = remember { mutableStateOf(Triple(-1,-1,-1)) }
    Month(month = CalendarUtil.makeMonth(2024, 1), selectedIndex, 0)
}