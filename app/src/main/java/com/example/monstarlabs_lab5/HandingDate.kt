package com.example.monstarlabs_lab5

import android.util.Log
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

object HandingDate {
    var months = mutableListOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    var monthsText = mutableListOf<String>("January","February","March","April","May","June","July","August","September","October","November","December")
    var week = mutableListOf<String>("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val TOTAL_MONTH = 2400
    public var dateFocus:String = ""
    public var monthGlobal: Int = SimpleDateFormat("MM").format(Calendar.getInstance().time).toInt()
    public var yearGlobal: Int = SimpleDateFormat("yyyy").format(Calendar.getInstance().time).toInt()
    public fun getCalendarByYear(year: Int): MutableList<Date> {
        var dates: MutableList<Date> = mutableListOf()

        for (m in months) {
            when (m) {
                0, 2, 4, 6, 7, 9, 11 -> {
                    for (d in 1..31) {
                        var calendar = Calendar.getInstance()
                        calendar.set(Calendar.DAY_OF_MONTH, d)
                        calendar.set(Calendar.MONTH, m)
                        calendar.set(Calendar.YEAR, year)
                        var day = calendar.time
                        dates.add(day)
                    }
                }
                1 -> {
                    when {
                        year % 4 == 0 -> {
                            for (d in 1..29) {
                                var calendar = Calendar.getInstance()
                                calendar.set(Calendar.DAY_OF_MONTH, d)
                                calendar.set(Calendar.MONTH, m)
                                calendar.set(Calendar.YEAR, year)
                                var day = calendar.time
                                dates.add(day)
                            }
                        }
                        else -> {
                            for (d in 1..28) {
                                var calendar = Calendar.getInstance()
                                calendar.set(Calendar.DAY_OF_MONTH, d)
                                calendar.set(Calendar.MONTH, m)
                                calendar.set(Calendar.YEAR, year)
                                var day = calendar.time
                                dates.add(day)
                            }
                        }
                    }
                }
                else -> {
                    for (d in 1..30) {
                        var calendar = Calendar.getInstance()
                        calendar.set(Calendar.DAY_OF_MONTH, d)
                        calendar.set(Calendar.MONTH, m)
                        calendar.set(Calendar.YEAR, year)
                        var day = calendar.time
                        dates.add(day)
                    }
                }
            }
        }

        return dates
    }

    public fun getDaysByMonth(month: String, calender: MutableList<Date>): MutableList<Date> {
        var dates: MutableList<Date> = mutableListOf()

        for (date in calender) {
            if (month == SimpleDateFormat("MM").format(date)) {
                dates.add(date)
            }
        }

        return dates
    }

    public fun MutableList<Date>.sortListDateMatchesWeek(pos: Int): MutableList<Date> {
        var listDate: MutableList<Date> = mutableListOf()
        listDate.addAll(this)
        var yearOfList: Int = SimpleDateFormat("yyyy").format(listDate[0]).toInt()
        var monthOfList = SimpleDateFormat("MM").format(listDate[0]).toInt()
        val dates: MutableList<Date> = getCalendarByYear(yearOfList)

        var datesByMonthBefore: MutableList<Date> = when {
            monthOfList == 1 -> {
                val datesOtherYear: MutableList<Date> = getCalendarByYear(yearOfList - 1)
                getDaysByMonth("12", datesOtherYear)
            }
            monthOfList > 10 -> getDaysByMonth("${monthOfList - 1}", dates)
            else -> getDaysByMonth("0${monthOfList - 1}", dates)
        }


        var datesByMonthAfter: MutableList<Date> = when {
            monthOfList < 9 -> getDaysByMonth("0${monthOfList + 1}", dates)
            monthOfList == 12 -> {
                val datesOtherYear: MutableList<Date> = getCalendarByYear(yearOfList + 1)
                getDaysByMonth("01", datesOtherYear)
            }
            else -> getDaysByMonth("${monthOfList + 1}", dates)
        }

        for ((index, i) in (datesByMonthBefore.size - pos until datesByMonthBefore.size).withIndex()) {
            listDate.add(index, datesByMonthBefore[i])
        }
        if (listDate.size < 42) {
            for (date in datesByMonthAfter) {
                if (listDate.size == 42) {
                    break
                } else {
                    listDate.add(date)
                }
            }
        }
        return listDate
    }
}