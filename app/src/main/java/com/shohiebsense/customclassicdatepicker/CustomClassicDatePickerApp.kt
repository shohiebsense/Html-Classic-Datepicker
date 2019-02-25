package com.shohiebsense.customclassicdatepicker

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class CustomClassicDatePickerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this) //tolazy to create App class

    }
}