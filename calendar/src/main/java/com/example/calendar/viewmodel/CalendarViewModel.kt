package com.example.calendar.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.state.CalendarState
import com.example.calendar.util.CalendarUtil
import com.example.calendar.util.Dispatchers
import com.example.calendar.values.notSelected
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CalendarViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<CalendarState>(CalendarState.Loading)
    val uiState get() = _uiState.asStateFlow()

    val selectedIndex = mutableStateOf(notSelected)

    fun loadCalendar(pageCount: Int) = viewModelScope.launch(Dispatchers.defaultDispatcher) {
        _uiState.value = CalendarState.Success(CalendarUtil.makeCalenderPage(pageCount))
    }

}