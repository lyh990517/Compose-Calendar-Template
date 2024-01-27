package com.example.calendar

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calendar.model.Date
import com.example.calendar.state.HorizontalCalendarState
import com.example.calendar.ui_component.Day
import com.example.calendar.util.CalendarUtil.currentDay
import com.example.calendar.util.CalendarUtil.currentDayOfWeek
import com.example.calendar.util.CalendarUtil.currentMonth
import com.example.calendar.util.CalendarUtil.currentYear
import com.example.calendar.values.dayOfWeekList
import com.example.calendar.values.notSelected
import com.example.calendar.viewmodel.HorizontalCalendarViewModel

@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    viewModel: HorizontalCalendarViewModel = HorizontalCalendarViewModel(),
    yearCount: Int = 1,
    onSelect: (Date) -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadHorizontalCalendar(yearCount)
    }
    when (uiState.value) {
        HorizontalCalendarState.Loading -> {
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is HorizontalCalendarState.Success -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val calendarPages =
                    remember { (uiState.value as HorizontalCalendarState.Success).calendarRow }
                val currentDate = remember {
                    mutableStateOf(
                        Date(
                            currentYear,
                            currentMonth,
                            currentDay,
                            dayOfWeekList[currentDayOfWeek - 1]
                        )
                    )
                }
                DateView { currentDate.value }
                LazyRow (Modifier.padding(vertical = 20.dp)){
                    itemsIndexed(calendarPages) { dayIndex, day ->
                        Day(
                            date = day,
                            dayIndex = dayIndex,
                            weekIndex = -1,
                            monthIndex = -1,
                            selectedIndex = viewModel.selectedIndex
                        )
                    }
                }
                Button(onClick = {
                    if (viewModel.selectedIndex.value != notSelected) {
                        onSelect(calendarPages[viewModel.selectedIndex.value.third])
                        currentDate.value = calendarPages[viewModel.selectedIndex.value.third]
                    } else {
                        Toast.makeText(context, "Select Date First!!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Select")
                }
            }
        }
    }
}

@Composable
fun DateView(date: () -> Date) {
    Text(text = "${date().year} . ${date().month} . ${date().day} . ${date().dayOfWeek}")
}
