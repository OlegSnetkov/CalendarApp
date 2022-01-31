package com.avtograv.calendarapp.ui

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
import java.sql.Date
import java.text.SimpleDateFormat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("fromDateRangePicker") { _, bundle ->
            val eventDateStart = bundle.getLong("fromDateTimestamp")
            val eventStartHumanDateList = listOf(
                SimpleDateFormat("yyyy", Locale.US).format(Date(eventDateStart)),
                SimpleDateFormat("MM", Locale.US).format(Date(eventDateStart)),
                SimpleDateFormat("d", Locale.US).format(Date(eventDateStart))
            )
            val eventDateFinish = bundle.getLong("toDateTimestamp")
            val eventFinishHumanDateList = listOf(
                SimpleDateFormat("yyyy", Locale.US).format(Date(eventDateFinish)),
                SimpleDateFormat("MM", Locale.US).format(Date(eventDateFinish)),
                SimpleDateFormat("d", Locale.US).format(Date(eventDateFinish))
            )
            Log.d("fromDateTimestamp", "FromTime : $eventStartHumanDateList")
            Log.d("toDateTimestamp", "FromTime : $eventFinishHumanDateList")
        }

        setFragmentResultListener("timeEventFrom") { _, bundle ->
            val eventTimeStartList = listOf(
                bundle.getString("FromHour"),
                bundle.getString("FromMinute")
            )
            Log.d("timeEventFrom", "FromTime : $eventTimeStartList")
        }

        setFragmentResultListener("timeEventTo") { _, bundle ->
            val eventTimeFinishList = listOf(
                bundle.getString("ToHour"),
                bundle.getString("ToMinute")
            )
            Log.d("timeEventTo", "ToTime : $eventTimeFinishList")
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

        setupButtons()
    }

    private fun setupButtons() {

        binding.apply {
            btSetRangeDate.setOnClickListener {
                clickListener?.setRangeDatePicker()
            }

            btSetFromTime.setOnClickListener {
                clickListener?.setFromTimePicker()
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
        fun setRangeDatePicker()
        fun setFromTimePicker()
        fun setToTimePicker()
        fun routeCalendarFragment()
    }

    companion object {
        fun create() = AddEventFragment()
    }
}