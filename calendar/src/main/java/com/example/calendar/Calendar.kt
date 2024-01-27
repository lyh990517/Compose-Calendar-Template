package com.example.calendar

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendar.model.Date
import com.example.calendar.state.CalendarState
import com.example.calendar.ui_component.CalendarControlView
import com.example.calendar.ui_component.DefaultRoundedButton
import com.example.calendar.ui_component.Month
import com.example.calendar.ui_component.VerticalSpacer
import com.example.calendar.util.CalendarUtil
import com.example.calendar.values.notSelected
import com.example.calendar.viewmodel.CalendarViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    viewModel: CalendarViewModel = CalendarViewModel(),
    pageCount: Int = 12,
    onSelect: (Date) -> Unit
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCalendar(pageCount)
    }
    when (uiState.value) {
        CalendarState.Loading -> {
            Box(modifier.fillMaxSize()) {
                Text(text = "Loading")
            }
        }

        is CalendarState.Success -> {
            CalendarContent(
                uiState,
                modifier,
                scope,
                pageCount,
                viewModel,
                context,
                onSelect
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun CalendarContent(
    uiState: State<CalendarState>,
    modifier: Modifier,
    scope: CoroutineScope,
    pageCount: Int,
    viewModel: CalendarViewModel,
    context: Context,
    onSelect: (Date) -> Unit
) {
    val calendarPages = remember { (uiState.value as CalendarState.Success).calendarPage }
    val pagerState = rememberPagerState { pageCount }
    LaunchedEffect(Unit) {
        pagerState.animateScrollToPage(CalendarUtil.currentMonth - 1)
    }
    Column(
        modifier
            .background(Color.White)
    ) {
        CalendarControlView(
            Modifier.testTag("Calendar"),
            calendarPages[pagerState.currentPage].year,
            calendarPages[pagerState.currentPage].month,
            onPrev = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        (pagerState.currentPage - 1).coerceAtLeast(
                            0
                        )
                    )
                }
            },
            onNext = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        (pagerState.currentPage + 1).coerceAtMost(
                            pageCount
                        )
                    )
                }
            },
            isControllable = true
        )
        VerticalSpacer(value = 10)
        HorizontalPager(
            modifier = modifier
                .weight(1f)
                .testTag("pager"), state = pagerState
        ) { monthIndex ->
            Month(
                calendarPages[monthIndex].calendar,
                viewModel.selectedIndex,
                monthIndex
            )
        }
        DefaultRoundedButton(
            modifier = Modifier.testTag("select"),
            cornerRadius = 32.dp,
            buttonText = "Select",
            buttonColor = Color(0xFF212A3A)
        ) {
            if (viewModel.selectedIndex.value == notSelected) {
                Toast.makeText(context, "Select Date First!!", Toast.LENGTH_SHORT).show()
            } else {
                val (month, week, day) = viewModel.selectedIndex.value
                onSelect(calendarPages[month].calendar[day][week]!!)
            }
        }
    }
}

@Preview
@Composable
fun CalendarContentPreview() {
    val viewModel = CalendarViewModel()
    viewModel.loadCalendar(12)
    Calendar(viewModel = viewModel, modifier = Modifier.height(400.dp)) {

    }
}