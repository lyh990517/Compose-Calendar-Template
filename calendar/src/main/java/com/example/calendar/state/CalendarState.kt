package com.example.calendar.state

import com.example.calendar.model.CalendarPage

sealed class CalendarState {
    data object Loading : CalendarState()
    data class Success(val calendarPage: List<CalendarPage>) : CalendarState()
}