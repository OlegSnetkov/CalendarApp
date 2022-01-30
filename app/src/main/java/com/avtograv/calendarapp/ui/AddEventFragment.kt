package com.avtograv.calendarapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.avtograv.calendarapp.databinding.FragmentAddEventBinding
import com.avtograv.calendarapp.viewmodels.AddEventViewModel
import java.util.*


class AddEventFragment : Fragment() {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private var clickListener: ClickAddEvent? = null

    private val viewModel: AddEventViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickAddEvent) {
            clickListener = context
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("fromDatePicker") { _, bundle ->
            val eventDateStartTimestamp = bundle.getLong("bundleKey")
            val eventDateStart = bundle.getString(" ")
            binding.btSetFromDate.text = eventDateStart
        }

        setFragmentResultListener("fromTimePicker") { _, bundle ->
//            val eventTimeStartTimestamp = bundle.getLong("bundleKey")
            val eventHourStart = bundle.getInt("hours").toString()
            val eventMinuteStart = bundle.getInt("minutes").toString()
            binding.btSetFromTime.text = "$eventHourStart : $eventMinuteStart"
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timestampStart = GregorianCalendar(2020 + 1900, 0, 28, 20, 7)
        val timestampFinish = GregorianCalendar(2020 + 1900, 0, 28, 20, 7)

        setupButtons()
    }


    private fun setupButtons() {

        binding.apply {
            btSetFromDate.setOnClickListener {
                clickListener?.setFromDatePicker()
            }

            btSetFromTime.setOnClickListener {
                clickListener?.setFromTimePicker()
            }

            btSetToDate.setOnClickListener {
                clickListener?.setToDatePicker()
            }

            btSetToTime.setOnClickListener {
                clickListener?.setToTimePicker()
            }

            buttonAddEvent.setOnClickListener {
                textInputNameEvent.error = ""
                if (viewModel.isValid(editTextNameEvent.text.toString())) {
                    viewModel.addEvent(
                        123,  // TODO
                        321,
                        editTextNameEvent.text.toString(),
                        editTextDescription.text.toString()
                    )
                    clickListener?.routeCalendarFragment()
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        clickListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface ClickAddEvent {
//        fun setRangePicker()
        fun setFromDatePicker()
        fun setToDatePicker()
        fun setFromTimePicker()
        fun setToTimePicker()
        fun routeCalendarFragment()
    }

    companion object {
        fun create() = AddEventFragment()
    }
}