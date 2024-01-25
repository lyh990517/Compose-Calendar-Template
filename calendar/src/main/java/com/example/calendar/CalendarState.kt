package com.example.calendar

import com.example.calendar.model.CalendarPage

sealed class CalendarState {
    data object Loading : CalendarState()
    data class Success(val calendarPage: List<CalendarPage>) : CalendarState()
}