package com.avtograv.calendarapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.avtograv.calendarapp.R
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


//    override fun setRangePicker() {
//        val dateRangePicker =
//            MaterialDatePicker.Builder.dateRangePicker()
//                .setTitleText("Select dates")
//                .build()
//        dateRangePicker.show(supportFragmentManager, "DateRangePicker")
//    }

    override fun setFromDatePicker() {
        val materialDatePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        materialDatePicker.show(supportFragmentManager, "MaterialDatePicker")

        materialDatePicker.addOnPositiveButtonClickListener {
            Log.d("MaterialDatePicker",
                "Date String = ${materialDatePicker.headerText} :: Date epoch value = $it")
            materialDatePicker.setFragmentResult(
                "fromDatePicker", bundleOf(
                    "bundleKey" to it,
                    " " to materialDatePicker.headerText))
        }
    }

    override fun setToDatePicker() {
        TODO("Not yet implemented")
    }

    override fun setFromTimePicker() {
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTitleText("SELECT YOUR TIMING")
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()
        materialTimePicker.show(supportFragmentManager, "MaterialTimePicker")

        materialTimePicker.addOnPositiveButtonClickListener {
            materialTimePicker.setFragmentResult(
                "fromTimePicker", bundleOf(
                    "hours" to materialTimePicker.hour,
                    "minutes" to materialTimePicker.minute))
        }
    }

    override fun setToTimePicker() {
        TODO("Not yet implemented")
    }
}