package com.example.calendar.util

import android.os.Build
import com.example.calendar.model.CalendarPage
import com.example.calendar.model.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Locale

object CalendarUtil {

    private val calendar = Calendar.getInstance()

    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    fun makeCalenderPage(pageCount: Int) = List(pageCount) { pageIndex ->
        val totalMonth = currentMonth + pageIndex
        val adjustedYear = currentYear + (totalMonth - 1) / 12
        val adjustedMonth = if (totalMonth % 12 == 0) 12 else totalMonth % 12
        CalendarPage(adjustedYear, adjustedMonth, makeMonth(adjustedYear, adjustedMonth))
    }


    fun makeMonth(year: Int, month: Int): List<List<Date?>> {
        val daysList = MutableList(5) { MutableList<Date?>(7) { null } }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val firstDayOfMonth = YearMonth.of(year, month).atDay(1)
            var currentDay =
                firstDayOfMonth.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))

            for (week in 0 until 5) {
                for (dayOfWeek in 0 until 7) {
                    val adjustedYear =
                        if (month == 1 && currentDay.monthValue == 12) year - 1 else if (month == 12 && currentDay.monthValue == 1) year + 1 else year
                    if (currentDay.monthValue == month || currentDay.isBefore(firstDayOfMonth) || currentDay.monthValue == (month % 12) + 1) {
                        val dayName =
                            currentDay.dayOfWeek.getDisplayName(
                                TextStyle.SHORT,
                                Locale.getDefault()
                            )
                        daysList[week][dayOfWeek] =
                            Date(
                                adjustedYear,
                                currentDay.monthValue,
                                currentDay.dayOfMonth,
                                dayName
                            )
                    }
                    currentDay = currentDay.plusDays(1)
                }
            }
        } else {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month - 1)
                set(Calendar.DAY_OF_MONTH, 1)
            }

            val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)
            calendar.add(Calendar.DATE, -firstDayOfMonth + Calendar.SUNDAY)

            for (week in 0 until 5) {
                for (dayOfWeek in 0 until 7) {
                    val adjustedYear = calendar.get(Calendar.YEAR)
                    val adjustedMonth =
                        calendar.get(Calendar.MONTH) + 1
                    val dayName =
                        calendar.getDisplayName(
                            Calendar.DAY_OF_WEEK,
                            Calendar.SHORT,
                            Locale.getDefault()
                        )

                    if (adjustedMonth == month || calendar.before(Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, month - 1)
                            set(Calendar.DAY_OF_MONTH, 1)
                        }) || adjustedMonth == (month % 12) + 1) {
                        daysList[week][dayOfWeek] = Date(
                            adjustedYear,
                            adjustedMonth,
                            calendar.get(Calendar.DAY_OF_MONTH),
                            dayName ?: ""
                        )
                    }

                    calendar.add(Calendar.DATE, 1)
                }
            }
        }

        return daysList
    }

    fun makeYear(year: Int): List<Date> {
        val dates = mutableListOf<Date>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (month in 1..12) {
                val monthStart = LocalDate.of(currentYear, month, 1)
                val monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth())

                var currentDate = monthStart
                while (currentDate.isBefore(monthEnd) || currentDate.isEqual(monthEnd)) {
                    dates.add(
                        Date(
                            year = currentDate.year,
                            month = currentDate.monthValue,
                            day = currentDate.dayOfMonth,
                            dayOfWeek = currentDate.dayOfWeek.getDisplayName(
                                TextStyle.FULL,
                                Locale.getDefault()
                            )
                        )
                    )
                    currentDate = currentDate.plusDays(1)
                }
            }
        } else {
            for (month in 1..12) {
                val calendar = Calendar.getInstance()
                calendar.set(year, month - 1, 1)

                val monthEnd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

                for (day in 1..monthEnd) {
                    calendar.set(Calendar.DAY_OF_MONTH, day)

                    val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
                    val dayOfWeek = sdf.format(calendar.time)

                    dates.add(
                        Date(
                            year = calendar.get(Calendar.YEAR),
                            month = calendar.get(Calendar.MONTH) + 1,
                            day = calendar.get(Calendar.DAY_OF_MONTH),
                            dayOfWeek = dayOfWeek
                        )
                    )
                }
            }
        }

        return dates
    }

}
