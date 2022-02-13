package com.avtograv.calendarapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.avtograv.calendarapp.R
import com.avtograv.calendarapp.ui.aboutEventFragment.DescriptionEventFragment
import com.avtograv.calendarapp.ui.addEventFragment.AddEventFragment
import com.avtograv.calendarapp.ui.calendarFragment.CalendarFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat


class MainActivity : AppCompatActivity(), AddEventFragment.ClickAddEvent,
    CalendarFragment.OnAddEvent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            routeToCalendarFragment()
        }
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
            .addToBackStack("routeToCalendarFragment")
            .commit()
    }

    override fun routeAddEventFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                AddEventFragment.create(),
                AddEventFragment::class.java.simpleName
            )
            .addToBackStack("routeAddEventFragment")
            .commit()
    }

    override fun routeEventAboutFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                DescriptionEventFragment.create(),
                DescriptionEventFragment::class.java.simpleName,
            )
            .addToBackStack("routeEventAboutFragment")
            .commit()
    }

    override fun setRangeDatePicker() {
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build()
        dateRangePicker.show(supportFragmentManager, "DateRangePicker")

        dateRangePicker.addOnPositiveButtonClickListener {
            dateRangePicker.setFragmentResult(
                "fromDateRangePicker", bundleOf(
                    "fromDateTimestamp" to it.first,
                    "toDateTimestamp" to it.second)
            )
        }
    }

    override fun setFromTimePicker() {
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTitleText("SELECT YOUR TIMING")
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()
        materialTimePicker.show(supportFragmentManager, "MaterialFromTimePicker")

        materialTimePicker.addOnPositiveButtonClickListener {
            materialTimePicker.setFragmentResult(
                "timeEventFrom", bundleOf(
                    "FromHour" to materialTimePicker.hour.toString(),
                    "FromMinute" to materialTimePicker.minute.toString()
                )
            )
        }
    }

    override fun setToTimePicker() {
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTitleText("SELECT YOUR TIMING")
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()
        materialTimePicker.show(supportFragmentManager, "MaterialToTimePicker")

        materialTimePicker.addOnPositiveButtonClickListener {
            materialTimePicker.setFragmentResult(
                "timeEventTo", bundleOf(
                    "ToHour" to materialTimePicker.hour.toString(),
                    "ToMinute" to materialTimePicker.minute.toString()
                )
            )
        }
    }
}