package com.example.calendar.state

import com.example.calendar.model.Date

sealed class HorizontalCalendarState {
    data object Loading : HorizontalCalendarState()
    data class Success(val calendarRow: List<Date>) : HorizontalCalendarState()
}