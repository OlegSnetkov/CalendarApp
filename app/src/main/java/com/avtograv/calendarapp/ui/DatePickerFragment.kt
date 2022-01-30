package com.avtograv.calendarapp.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.avtograv.calendarapp.R
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            this,
            year,
            monthOfYear,
            dayOfMonth
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val result = "$year : $month :$dayOfMonth"
        setFragmentResult("requestKey", bundleOf("bundleKey" to result))
    }
}