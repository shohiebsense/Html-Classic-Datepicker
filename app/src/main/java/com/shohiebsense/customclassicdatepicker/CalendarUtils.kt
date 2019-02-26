package com.shohiebsense.customclassicdatepicker

import kotlinx.coroutines.*
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class CalendarUtils {

    var now = LocalDate.now()

    fun getDayOfMonth() = now.dayOfMonth
    fun getMonth() = now.month
    fun getYear() = now.year

    fun tomorrow() = now.plusDays(1)


    fun getDaysInMonth() : Long {
        var lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth())
        return ChronoUnit.DAYS.between(now, lastDayOfMonth)
    }

    fun getFirstDayUntilToday() : Long {
        var firstDayOfMonth = now.withDayOfMonth(1)
        return ChronoUnit.DAYS.between(firstDayOfMonth, now)
    }


    fun populateYearsSpinner(onShowingYearList : (ArrayList<Int>) -> Unit) {
        GlobalScope.launch {
            // var yearList = async { getYears() }
            var yearList = getYears()
            withContext(Dispatchers.Main){
                onShowingYearList(yearList)
            }
        }
    }

    fun getYears() : ArrayList<Int>{
        var years = arrayListOf<Int>()
        for (i in 1945 .. getYear()) {
            years.add(i)
        }
        return years
    }

    fun populatemonthsSpinner(
        year : Int,
        onShowingMonthList: (Array<out String>) -> Unit){
        GlobalScope.launch{
            var months = getMonths()
            if(year == getYear()){
                months = months.copyOfRange(0, getMonth().value)
            }
            withContext(Dispatchers.Main){
                onShowingMonthList(months)
            }
        }
    }

    fun getMonths() : Array<out String>{
        val symbols = DateFormatSymbols()
        val monthNames = symbols.months
        return monthNames
    }


    fun populateDaysSpinner(year : Int, month: Int = 1, day: Int = 1, onShowingDayList : (ArrayList<Int>) -> Unit){
        GlobalScope.launch {
            var length = getMonthsLengthInDays(year,month)
            if(getMonth().value == month && getYear() == year){
                length = getDayOfMonth()
            }
            var dayList = arrayListOf<Int>()
            for(i in 1 .. length){
                dayList.add(i)
            }
            withContext(Dispatchers.Main){
                onShowingDayList(dayList)
            }
        }
    }

    fun getMonthsLengthInDays(year: Int, month : Int) : Int{
        return LocalDate.of(year, month, 1).lengthOfMonth()
    }


}