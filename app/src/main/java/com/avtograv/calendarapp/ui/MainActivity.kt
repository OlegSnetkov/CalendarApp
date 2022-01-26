package com.avtograv.calendarapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avtograv.calendarapp.R
import androidx.lifecycle.ViewModelProvider





class MainActivity : AppCompatActivity(), AddEventFragment.ClickAddEvent,
    CalendarFragment.OnAddEvent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToCalendarFragment()
        }
    }

    private fun showTimePickerDialog(view: android.view.View) {
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }

    private fun showDatePickerDialog(view: android.view.View) {
        DatePickerFragment().show(supportFragmentManager, "datePicker")
    }

    override fun setTimePicker() {
        showTimePickerDialog(android.view.View(this))
    }

    override fun setDatePicker() {
        showDatePickerDialog(android.view.View(this))
    }

    override fun routeCalendarFragment() {
        routeToCalendarFragment()
    }

    private fun routeToCalendarFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                CalendarFragment.create(),
                CalendarFragment::class.java.simpleName
            )
            .commit()
    }

    override fun routeAddEventFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                AddEventFragment.create(),
                AddEventFragment::class.java.simpleName
            )
            .commit()
    }
}