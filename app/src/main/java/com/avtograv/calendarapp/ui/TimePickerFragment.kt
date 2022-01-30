package com.avtograv.calendarapp.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*


class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(
            activity,
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        setFragmentResult("requestKey",
            bundleOf("bundleHour" to hourOfDay, "bundleMinute" to minute))
    }
}