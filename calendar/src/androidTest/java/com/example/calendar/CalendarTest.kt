package com.example.calendar

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.example.calendar.model.Date
import com.example.calendar.util.CalendarUtil.currentDay
import com.example.calendar.util.CalendarUtil.currentDayOfWeek
import com.example.calendar.util.CalendarUtil.currentMonth
import com.example.calendar.util.CalendarUtil.currentYear
import com.example.calendar.values.dayOfWeekList
import com.example.calendar.values.notSelected
import com.example.calendar.viewmodel.CalendarViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalendarTest {

    @get:Rule
    val composeRule = createComposeRule()
    private val viewModel = CalendarViewModel()
    private val currentDate = Date(
        currentYear,
        currentMonth,
        currentDay,
        dayOfWeekList[currentDayOfWeek - 1]
    )

    @Before
    fun setUp() {
        composeRule.setContent {
            Calendar(viewModel = viewModel) {

            }
        }
        composeRule.onRoot().printToLog("TAG")
    }

    @Test
    fun `calendar_should_shows`() {
        composeRule.onNodeWithTag("Calendar").assertIsDisplayed()
        Thread.sleep(1000)
    }

    @Test
    fun `when_day_clicks_selected_index_in_viewModel_should_not_equals_to_notSelected`() {
        composeRule.onNodeWithTag("$currentDate", true).assertIsDisplayed()
        composeRule.onNodeWithTag("$currentDate", true).performClick()
        println("selectedIndex : ${viewModel.selectedIndex.value}")
        assert(notSelected != viewModel.selectedIndex.value)
    }

    @Test
    fun `current_date_should_exact`() {
        composeRule.onNodeWithTag("date").assert(hasText("$currentYear . $currentMonth"))
    }

    @Test
    fun `when_next_button_clicks_go_to_next_month`() {
        val defaultPageCount = 12

        composeRule.onNodeWithTag("date").assert(hasText("$currentYear . $currentMonth"))

        repeat(defaultPageCount){
            composeRule.onNodeWithTag("next", true).performClick()
            composeRule.onNodeWithTag("date",true).assert(hasText("$currentYear . ${currentMonth + 1}"))
        }

        composeRule.onNodeWithTag("next", true).performClick()
        composeRule.onNodeWithTag("date",true).assert(hasText("$currentYear . $currentMonth"))
    }

    @Test
    fun `when_prev_button_clicks_go_to_previous_month`() {
        composeRule.onNodeWithTag("date").assert(hasText("$currentYear . $currentMonth"))
        composeRule.onNodeWithTag("prev", true).performClick()
        if (currentMonth == 1) {
            composeRule.onNodeWithTag("date").assert(hasText("$currentYear . $currentMonth"))
        } else {
            composeRule.onNodeWithTag("date").assert(hasText("$currentYear . ${currentMonth - 1}"))
        }
    }
}