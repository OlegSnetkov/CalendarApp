package com.avtograv.calendarapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avtograv.calendarapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToCalendarFragment()
        }
    }

    private fun routeToCalendarFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                CalendarFragment.create()
            )
            .commit()
    }

    fun showTimePickerDialog(view: android.view.View) {
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }
}