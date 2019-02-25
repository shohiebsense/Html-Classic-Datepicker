package com.shohiebsense.customclassicdatepicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var  calendarUtils = CalendarUtils()
    var selecteBirthYear = calendarUtils.getYear()
    var selectedBirthMonth = calendarUtils.getMonth().value
    var selecteBirthDay = calendarUtils.getDayOfMonth()
    var birthDateAPIFormat = ""
    var onUserInteracting = false
    var isInitDayIsExecuted = false
    var yearList = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBirthDateFormat()
        initSpinner()
    }

    fun setBirthDateFormat(){
        val selectedMonthStr = if (selectedBirthMonth < 9) "0${selectedBirthMonth}" else "${selectedBirthMonth}"
        val selectedDayStr = if (selecteBirthDay < 10) "0$selecteBirthDay" else "$selecteBirthDay"
        birthDateAPIFormat = "$selecteBirthYear-$selectedMonthStr-$selectedDayStr"
        Toast.makeText(this,"the your birthday is $birthDateAPIFormat", Toast.LENGTH_SHORT).show()
    }

    fun initSpinner(){
        calendarUtils.populateYearsSpinner {yearList ->
            this.yearList.clear()
            this.yearList.addAll(yearList)
            let {
                val arrayAdapter = ArrayAdapter(it, R.layout.item_dropdown_date_spinner,yearList)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_year.adapter = arrayAdapter
                if(!onUserInteracting){
                    spinner_year.setSelection(yearList.size-1)
                }
                spinner_year.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if(!onUserInteracting) return
                        selecteBirthYear = yearList[position]
                        setBirthDateFormat()
                        populateDays()
                    }
                }
            }
        }

        calendarUtils.populatemonthsSpinner(selecteBirthYear) {monthList ->
            let {
                val arrayAdapter = ArrayAdapter(it, R.layout.item_dropdown_date_spinner,monthList)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_month.adapter = arrayAdapter
            }
            if(!onUserInteracting){
                spinner_month.setSelection(selectedBirthMonth-1)
            }
            spinner_month.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(!onUserInteracting) return
                    selectedBirthMonth = position+1
                    setBirthDateFormat()
                    populateDays()
                }
            }
        }
        populateDays()
    }

    fun populateDays(){
        calendarUtils.populateDaysSpinner(selecteBirthYear, selectedBirthMonth+1, selecteBirthDay+1){
            val daysAdapter = ArrayAdapter(this, R.layout.item_dropdown_date_spinner, it)
            daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_day.adapter = daysAdapter
            spinner_day.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(!onUserInteracting){
                        return
                    }
                    selecteBirthDay = position+1
                    setBirthDateFormat()
                }
            }
            if(!isInitDayIsExecuted){
                spinner_day.setSelection(selecteBirthDay-1)
                isInitDayIsExecuted = true
            }
        }
    }

    fun initFirstSelection(){
        spinner_day.setSelection(selecteBirthDay)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        onUserInteracting = true
    }
}
