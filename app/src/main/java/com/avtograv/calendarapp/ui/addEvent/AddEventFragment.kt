package com.avtograv.calendarapp.ui.addEvent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.avtograv.calendarapp.databinding.FragmentAddEventBinding
import com.avtograv.calendarapp.realm.DatabaseOperations
import com.avtograv.calendarapp.repositories.EventRepositoryImpl
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class AddEventFragment : Fragment() {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private var clickListener: ClickAddEvent? = null

    private lateinit var viewModel: AddEventViewModel

    private var eventDateStartList = mutableListOf<String?>()
    private var eventDateFinishList = mutableListOf<String?>()
    private var eventTimeStartList = mutableListOf<String?>()
    private var eventTimeFinishList = mutableListOf<String?>()

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
            eventDateStartList.addAll(
                listOf(
                    SimpleDateFormat("yyyy", Locale.US).format(Date(eventDateStart)),
                    SimpleDateFormat("MM", Locale.US).format(Date(eventDateStart)),
                    SimpleDateFormat("d", Locale.US).format(Date(eventDateStart))
                )
            )

            val eventDateFinish = bundle.getLong("toDateTimestamp")
            eventDateFinishList.addAll(
                listOf(
                    SimpleDateFormat("yyyy", Locale.US).format(Date(eventDateFinish)),
                    SimpleDateFormat("MM", Locale.US).format(Date(eventDateFinish)),
                    SimpleDateFormat("d", Locale.US).format(Date(eventDateFinish))
                )
            )
        }

        setFragmentResultListener("timeEventFrom") { _, bundle ->
            eventTimeStartList = mutableListOf(
                bundle.getString("FromHour"),
                bundle.getString("FromMinute")
            )
        }

        setFragmentResultListener("timeEventTo") { _, bundle ->
            eventTimeFinishList = mutableListOf(
                bundle.getString("ToHour"),
                bundle.getString("ToMinute")
            )
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

        val viewModelFactory = AddEventViewModelFactory(EventRepositoryImpl(DatabaseOperations()))
        viewModel = ViewModelProvider(
            this, viewModelFactory)[AddEventViewModel::class.java]

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

                val eventTimestampStart = GregorianCalendar(
                    eventDateStartList[0]!!.toInt(),
                    eventDateStartList[1]!!.toInt() - 1,
                    eventDateStartList[2]!!.toInt(),
                    eventTimeStartList[0]!!.toInt(),
                    eventTimeStartList[1]!!.toInt()
                ).timeInMillis

                val eventTimestampFinish = GregorianCalendar(
                    eventDateFinishList[0]!!.toInt(),
                    eventDateFinishList[1]!!.toInt() - 1,
                    eventDateFinishList[2]!!.toInt(),
                    eventTimeFinishList[0]!!.toInt(),
                    eventTimeFinishList[1]!!.toInt()
                ).timeInMillis

                if (viewModel.isValid(editTextNameEvent.text.toString())) {
                    viewModel.addEvent(
                        eventTimestampStart,
                        eventTimestampFinish,
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