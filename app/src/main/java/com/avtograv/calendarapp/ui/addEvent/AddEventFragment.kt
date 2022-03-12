package com.avtograv.calendarapp.ui.addEvent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.avtograv.calendarapp.R
import com.avtograv.calendarapp.databinding.FragmentAddEventBinding
import com.avtograv.calendarapp.realm.RealmOperations
import com.avtograv.calendarapp.repository.EventDataStatus
import com.avtograv.calendarapp.repository.EventRepositoryImpl
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class AddEventFragment : Fragment() {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private var clickListener: ClickAddEvent? = null

    private lateinit var viewModel: AddEventViewModel

    private var eventStartDateList = mutableListOf<String?>()
    private var eventEndDateList = mutableListOf<String?>()
    private var eventStartTimeList = mutableListOf<String?>()
    private var eventEndTimeList = mutableListOf<String?>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClickAddEvent) {
            clickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("fromDateRangePicker") { _, bundle ->

            val eventStartDateTimestamp = bundle.getLong("fromDateTimestamp")
            eventStartDateList.addAll(
                listOf(
                    SimpleDateFormat("yyyy", Locale.US).format(Date(eventStartDateTimestamp)),
                    SimpleDateFormat("MM", Locale.US).format(Date(eventStartDateTimestamp)),
                    SimpleDateFormat("d", Locale.US).format(Date(eventStartDateTimestamp))
                )
            )

            val eventEndDateTimestamp = bundle.getLong("toDateTimestamp")
            eventEndDateList.addAll(
                listOf(
                    SimpleDateFormat("yyyy", Locale.US).format(Date(eventEndDateTimestamp)),
                    SimpleDateFormat("MM", Locale.US).format(Date(eventEndDateTimestamp)),
                    SimpleDateFormat("d", Locale.US).format(Date(eventEndDateTimestamp))
                )
            )
        }

        setFragmentResultListener("timeEventFrom") { _, bundle ->
            eventStartTimeList = mutableListOf(
                bundle.getString("FromHour"),
                bundle.getString("FromMinute")
            )
        }

        setFragmentResultListener("timeEventTo") { _, bundle ->
            eventEndTimeList = mutableListOf(
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

        viewModelSetup()
        buttonsSetup()
    }

    private fun viewModelSetup() {
        val dbRealm = RealmOperations()
        val repository = EventRepositoryImpl(dbRealm)
        val viewModelFactory = AddEventViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddEventViewModel::class.java]
        viewModel.addEventDataStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                EventDataStatus.Loading -> Toast.makeText(requireContext(),
                    getString(R.string.adding_event),
                    Toast.LENGTH_SHORT).show()
                EventDataStatus.Added -> Toast.makeText(requireContext(),
                    getString(R.string.event_added),
                    Toast.LENGTH_SHORT).show()
                else -> throw IllegalArgumentException()
            }
        }
    }

    private fun buttonsSetup() {
        binding.apply {
            buttonSetRangeDate.setOnClickListener {
                clickListener?.setRangeDatePicker()
            }

            buttonSetStartTimeEvent.setOnClickListener {
                clickListener?.setFromTimePicker()
            }

            buttonSetFinishTimeEvent.setOnClickListener {
                clickListener?.setToTimePicker()
            }

            buttonAddNewEvent.apply {
                setOnClickListener {
                    addNewEvent(editTextNameEvent, editTextDescription)
                    clickListener?.routeCalendarFragment()
                }
            }
        }
    }

    private fun addNewEvent(nameEvent: EditText, descriptionEvent: EditText) {
        val eventStartTime = GregorianCalendar(
            eventStartDateList[0]!!.toInt(),
            eventStartDateList[1]!!.toInt() - 1,
            eventStartDateList[2]!!.toInt(),
            eventStartTimeList[0]!!.toInt(),
            eventStartTimeList[1]!!.toInt()
        ).timeInMillis

        val eventFinishTime = GregorianCalendar(
            eventEndDateList[0]!!.toInt(),
            eventEndDateList[1]!!.toInt() - 1,
            eventEndDateList[2]!!.toInt(),
            eventEndTimeList[0]!!.toInt(),
            eventEndTimeList[1]!!.toInt()
        ).timeInMillis

        if (viewModel.isValid(eventStartTime, eventFinishTime, nameEvent.text.toString())) {
            binding.buttonAddNewEvent.isEnabled = true
            viewModel.addEvent(eventStartTime,
                eventFinishTime,
                nameEvent.text.toString(),
                descriptionEvent.text.toString()
            )
        } else binding.buttonAddNewEvent.isEnabled = false
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